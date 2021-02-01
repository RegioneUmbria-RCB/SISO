package it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.mav;

import it.webred.ct.data.access.basic.common.dto.KeyValueDTO;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportFactory;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions.TroppiErroriImportFileException;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TSMavFiles<T extends TSMavEnv> extends ImportFiles<T> {
	
	private SimpleDateFormat sdfId = new SimpleDateFormat("yyyyMMdd hhmmss");
	private SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMyyyy");
	//QUERY
	private PreparedStatement pstmtFlu = null;
	private PreparedStatement pstmtDisp = null;
	private PreparedStatement pstmtPag = null;
	private PreparedStatement pstmtProm = null;
	private PreparedStatement pstmtSogg = null;
	//DATA
	private boolean mavBegin = false;
	private String idFlusso;
	private List dataFlusso;
	private List dataDisposizione;
	private List dataSoggetto;
	//NULL TYPE
	private KeyValueDTO nullBD = new KeyValueDTO(String.valueOf(java.sql.Types.INTEGER), null);
	private KeyValueDTO nullS = new KeyValueDTO(String.valueOf(java.sql.Types.VARCHAR), null);
	private KeyValueDTO nullD = new KeyValueDTO(String.valueOf(java.sql.Types.DATE), null);
	
	public TSMavFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {
		List<String> campi = new ArrayList<String>();
		
		if(currentLine.length() == 120){
			String record = currentLine.substring(1,3);
			try {
				
				if("IM".equals(record)){
					elaboraRecordTesta(currentLine, tipoRecord);
					mavBegin = true;
				}
				else if("14".equals(record) && mavBegin)
					elaboraRecord14(currentLine, tipoRecord);
				else if("20".equals(record) && mavBegin)
					elaboraRecord20(currentLine, tipoRecord);
				else if("30".equals(record) && mavBegin)
					elaboraRecord30(currentLine, tipoRecord);
				else if("40".equals(record) && mavBegin)
					elaboraRecord40(currentLine, tipoRecord);
				else if("51".equals(record) && mavBegin)
					elaboraRecord51(currentLine, tipoRecord);
				else if("50".equals(record) && mavBegin)
					elaboraRecord50(currentLine, tipoRecord);
				else if("59".equals(record) && mavBegin)
					elaboraRecord59(currentLine, tipoRecord);
				else if("10".equals(record) && mavBegin)
					elaboraRecord10(currentLine, tipoRecord);
				else if("EF".equals(record) && mavBegin){
					elaboraRecordCoda(currentLine, tipoRecord);
					mavBegin = false;
				}
				
			} catch (Exception e) {
				if(e.getMessage().startsWith("ORA-00001")){
					log.info("_______DUPLICATO RECORD OK: " + currentLine);
				}
				else {
					log.error("_______ERRORE DURANTE L'ELABORAZIONE IN RECORD: " + currentLine);
					log.error("_______ERRORE MESSAGGIO: " + e.getMessage());
					throw new RulEngineException(e.getMessage());
				}
			}
		}
		return campi;
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sData[] = file.split("_");
		int i;
		for(i = 0; i<sData.length; i++){
			if(sData[i].length() == 4)
				break;
		}
		
		try {
			env.saveFornitura(sdf.parse(sData[i]+sData[i+1]+sData[i+2]), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}

	protected void procesingFile(String file, String cartellaDati) throws RulEngineException {
		boolean gestisciTmp = false;
		boolean disabilitaStorico = false;
		if (env.getEnteSorgente().isInReplace())
			gestisciTmp = true;

		if (env.getEnteSorgente().isDisabilitaStorico())
			disabilitaStorico = true;
				
		try {

			String sql = setInsertSql(env.tableFlusso, 30);
			pstmtFlu = con.prepareStatement(sql);
			sql = setInsertSql(env.tableDisposizione, 35);
			pstmtDisp = con.prepareStatement(sql);
			sql = setInsertSql(env.tablePagamento, 18);
			pstmtPag = con.prepareStatement(sql);
			sql = setInsertSql(env.tableSoggetto, 29);
			pstmtSogg = con.prepareStatement(sql);
			sql = setInsertSql(env.tablePromemoria, 23);
			pstmtProm = con.prepareStatement(sql);

		} catch (SQLException e) {
			throw new RulEngineException("Problema in creazione Statement ",e);
		}
		
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		ConcreteImport ci = ConcreteImportFactory.getConcreteImport(this);
		tabs = ci.getTabelleAndTipiRecord();
		
		for (String key : tabs.keySet())
		{
				String tr = tabs.get(key);
				log.info("CARICO " + file);
				try {
					leggiFile(file, cartellaDati, key, tr, getDataExport());
				} catch (Exception e) {
					throw new RulEngineException("Problema in lettura del file " + file + " tr=" + tr,e);
				}
		}		
		ci.postLetturaFileAndFilter(cartellaDati, file, gestisciTmp);

		try {
			if (pstmtFlu != null)
				pstmtFlu.close();
			if (pstmtDisp != null)
				pstmtDisp.close();
			if (pstmtPag != null)
				pstmtPag.close();
			if (pstmtSogg != null)
				pstmtSogg.close();
			if (pstmtProm != null)
				pstmtProm.close();
		} catch (SQLException e) {
			throw new RulEngineException("Problema in chiusura connessione",e);
		}
		
	}
	
	protected boolean leggiFile(String file, String cartellaDati, String tempTable , String tipoRecord,Timestamp dataExport) throws Exception {
		
		BufferedReader fileIn = null;
		List<String> errori = new ArrayList<String>();
		boolean lettoqualcosa = false;
		try {
			if(new File(cartellaDati+"ELABORATI/"+file).exists())
			{
				log.debug("Scartato file perche già elaborato " + file);
				RAbNormal abn = new RAbNormal();
				abn.setMessage("Scartato file perche già elaborato " + file);
				abn.setMessageDate(new Timestamp(new Date().getTime() ));
				env.getAbnormals().add(abn);
				log.info("APERTURA FILE" + cartellaDati + file);
				new File(cartellaDati+file).delete();
				return false;
			}
			//fileIn = new BufferedReader(new FileReader(cartellaDati+file));
			fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(cartellaDati+file), "UTF8"));
			
			String currentLine=null;
			int riga = 1;
			int cont = 1;
			String insertSql = null;
			while ((currentLine = fileIn.readLine()) != null)
			{
					
				// traccia file forniture
				if (riga==1) {
					//controllo se è mav e non rendicontazione
					String record = currentLine.substring(1,3);
					try {
						tracciaFornitura(file, cartellaDati, currentLine);
					} catch (Exception e ) {
					}				
				}
				
				lettoqualcosa = true;
				try {
					getValoriFromLine(tipoRecord,currentLine);
				} catch (Exception e ) {
					errori.add(currentLine);
				}
				
				riga++;
				cont++;
				if(cont > 10000){
					cont = 1;
					con.commit();
				}
			}
	
			return lettoqualcosa;
		}catch (Exception e) {
			log.error("!",e);
			return false;
		}finally {
			if (fileIn!=null)
				fileIn.close();
			if (errori.size()>0) {
				PrintWriter  pw = new PrintWriter (cartellaDati+"ELABORATI/"+file+".err");
				for (int ii = 0; ii < errori.size(); ii++)
				{
					pw.println(errori.get(ii));
				}
				pw.close();
				throw new RulEngineException("Errore di inserimento ! Prodotto file " + file+".err");
				
			}
		}
		
	}

	private String setInsertSql(String table, int numCols) throws SQLException{
		
		String param = "";
		for(int i = 0; i < numCols; i++)
			param += ",?";
		String sql = "insert into "
				+ table
				+ " values (" + (!"".equals(param)?param.substring(1):"") + ")";
		
		return sql;
	}
	
	private void elaboraRecordTesta(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		idFlusso = currentLine.substring(3, 19);
		dataFlusso = new ArrayList();
		dataFlusso.add(currentLine.substring(1, 3));//tipo record
		dataFlusso.add(currentLine.substring(3, 8));//mittente
		dataFlusso.add(currentLine.substring(8, 13));//ricevente
		dataFlusso.add(nullD);
		String data = currentLine.substring(13, 19);
		String anno = "20" + data.substring(4,6);
		data = data.substring(0,4) + anno;
		dataFlusso.add(sdfDate.parseObject(data));//data crezione
		dataFlusso.add(nullS);
		dataFlusso.add(currentLine.substring(19, 39));//nome supporto
		dataFlusso.add(currentLine.substring(39, 45));//campo azienda
		dataFlusso.add(currentLine.substring(104, 111));//qualificatore flusso
		dataFlusso.add(nullBD);
		dataFlusso.add(currentLine.substring(113, 114));

	}
	
	private void elaboraRecordCoda(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		String numDisp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(45, 52));
		dataFlusso.add(numDisp != null? new BigDecimal(numDisp):nullBD);
		String totneg = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(52, 67));
		dataFlusso.add(totneg != null? new BigDecimal(totneg):nullBD);
		String totpos = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(67, 82));
		dataFlusso.add(totpos != null? new BigDecimal(totpos):nullBD);
		dataFlusso.add(1);
		dataFlusso.add(0);
		
		salvaInDb(pstmtFlu, dataFlusso, idFlusso);
		
	}
	
	private void elaboraRecord14(String currentLine, String tipoRecord) throws ParseException{
		
		dataDisposizione = new ArrayList();
		dataDisposizione.add(idFlusso);
		dataDisposizione.add(currentLine.substring(3, 10));//prog
		String data = currentLine.substring(22, 28);
		String anno = "20" + data.substring(4,6);
		data = data.substring(0,4) + anno;
		dataDisposizione.add(sdfDate.parseObject(data));//data scad pagam
		dataDisposizione.add(nullD);
		dataDisposizione.add(currentLine.substring(28, 33));//causale
		dataDisposizione.add(currentLine.substring(46, 47) + currentLine.substring(33, 46));//importo
		String abi = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(47, 52));
		dataDisposizione.add(abi != null? new BigDecimal(abi):nullBD);
		String cab = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(52, 57));
		dataDisposizione.add(cab != null? new BigDecimal(cab):nullBD);
		abi = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(69, 74));
		dataDisposizione.add(abi != null? new BigDecimal(abi):nullBD);
		cab = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(74, 79));
		dataDisposizione.add(cab != null? new BigDecimal(cab):nullBD);
		dataDisposizione.add(currentLine.substring(79, 91));//conto
		dataDisposizione.add(nullS);
		dataDisposizione.add(currentLine.substring(91, 96));//codice azienda
		String tipo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(96, 97));
		dataDisposizione.add(tipo != null? new BigDecimal(tipo):nullBD);
		dataDisposizione.add(currentLine.substring(97, 113));//cliente debitore
		dataDisposizione.add(currentLine.substring(119, 120));//cod divisa
		
	}
	
	private void elaboraRecord51(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		dataDisposizione.add(currentLine.substring(74, 86));//cod identificativo
		String importo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(86, 91));
		dataDisposizione.add(importo != null? new BigDecimal(importo):nullBD);//importo spese comm
		String data = currentLine.substring(91, 97);
		String anno = "20" + data.substring(4,6);
		data = data.substring(0,4) + anno;
		dataDisposizione.add(sdfDate.parseObject(data));//data valuta
		dataDisposizione.add(currentLine.substring(97, 109));//riferimento riepilogativo
		data = currentLine.substring(109, 115);
		anno = "20" + data.substring(4,6);
		data = data.substring(0,4) + anno;
		dataDisposizione.add(sdfDate.parseObject(data));//data pagamento
		
		salvaInDb(pstmtDisp, dataDisposizione, idFlusso + dataDisposizione.get(1));
		
	}
	
	private void elaboraRecord20(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List listaDati = new ArrayList();
		listaDati.add(idFlusso);
		listaDati.add(currentLine.substring(3, 10));//prog
		listaDati.add(currentLine.substring(10, 34));//1 segmento
		listaDati.add(currentLine.substring(34, 58));//2 segmento
		listaDati.add(currentLine.substring(58, 82));//3 segmento
		listaDati.add(currentLine.substring(82, 106));//4 segmento
		listaDati.add(nullS);
		listaDati.add(nullS);
		listaDati.add(nullS);
		listaDati.add(nullBD);
		listaDati.add(nullS);
		listaDati.add(nullS);
		listaDati.add(nullS);
		listaDati.add(1);
		listaDati.add(0);
		
		salvaInDb(pstmtSogg, listaDati, getHash(idFlusso + listaDati.get(1) + "1"));
		
	}
	
	private void elaboraRecord30(String currentLine, String tipoRecord) throws ParseException{
		
		dataSoggetto = new ArrayList();
		dataSoggetto.add(idFlusso);
		dataSoggetto.add(currentLine.substring(3, 10));//prog
		dataSoggetto.add(currentLine.substring(10, 40));//1 segmento
		dataSoggetto.add(currentLine.substring(40, 70));//2 segmento
		dataSoggetto.add(nullS);
		dataSoggetto.add(nullS);
		dataSoggetto.add(currentLine.substring(70, 86));//cod fisc	
		dataSoggetto.add(nullS);
		
	}
	
	private void elaboraRecord40(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		dataSoggetto.add(currentLine.substring(10, 40));//indirizzo
		String cap = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(40, 45));
		dataSoggetto.add(cap != null? new BigDecimal(cap):nullBD);//cap
		dataSoggetto.add(currentLine.substring(45, 68));//comune
		dataSoggetto.add(currentLine.substring(68, 70));//provincia
		dataSoggetto.add(currentLine.substring(98, 100));//cod paese
		dataSoggetto.add(0);
		dataSoggetto.add(1);
		
		salvaInDb(pstmtSogg, dataSoggetto, getHash(idFlusso + dataSoggetto.get(1) + "0"));
		
	}
	
	private void elaboraRecord50(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List listaDati = new ArrayList();
		listaDati.add(idFlusso);
		listaDati.add(currentLine.substring(3, 10));//progr
		listaDati.add(currentLine.substring(10, 50));//segmento
		listaDati.add(nullBD);
		
		salvaInDb(pstmtPag, listaDati, getHash(idFlusso + listaDati.get(1) + listaDati.get(2)));
		
		listaDati = new ArrayList();
		listaDati.add(idFlusso);
		listaDati.add(currentLine.substring(3, 10));//progr
		listaDati.add(currentLine.substring(50, 90));//segmento
		listaDati.add(nullBD);
		
		salvaInDb(pstmtPag, listaDati, getHash(idFlusso + listaDati.get(1) + listaDati.get(2)));
		
	}
	
	private void elaboraRecord59(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List listaDati = new ArrayList();
		listaDati.add(idFlusso);
		listaDati.add(currentLine.substring(3, 10));//progr
		listaDati.add(currentLine.substring(10, 65));//segmento
		listaDati.add(nullBD);
		
		salvaInDb(pstmtPag, listaDati, getHash(idFlusso + listaDati.get(1) + listaDati.get(2)));
		
		listaDati = new ArrayList();
		listaDati.add(idFlusso);
		listaDati.add(currentLine.substring(3, 10));//progr
		listaDati.add(currentLine.substring(65, 120));//segmento
		listaDati.add(nullBD);
		
		salvaInDb(pstmtPag, listaDati, getHash(idFlusso + listaDati.get(1) + listaDati.get(2)));
		
	}
	
	private void elaboraRecord10(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List listaDati = new ArrayList();
		listaDati.add(idFlusso);
		listaDati.add(currentLine.substring(3, 10));//prog
		String data = currentLine.substring(10, 16);
		String anno = "20" + data.substring(4,6);
		data = data.substring(0,4) + anno;
		listaDati.add(sdfDate.parseObject(data));//data crezione
		data = currentLine.substring(16, 22);
		anno = "20" + data.substring(4,6);
		data = data.substring(0,4) + anno;
		listaDati.add(sdfDate.parseObject(data));//data valuta 
		String causale = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(28, 33));
		listaDati.add(causale != null? new BigDecimal(causale):null);//causale
		listaDati.add(currentLine.substring(46, 47) + currentLine.substring(33, 46));//importo
		listaDati.add(currentLine.substring(47, 48));//cod divisa
		listaDati.add(currentLine.substring(57, 69));//cod riferimento
		listaDati.add(currentLine.substring(79, 91));//conto creditore
		
		salvaInDb(pstmtProm, listaDati, idFlusso + listaDati.get(1));
		
	}
	
	private PreparedStatement setDefaultParameters(PreparedStatement pstm, int lastParam, String idOrig, String hash) throws SQLException{
		
		String idExt = idOrig + "   " + ctx.getIdFonte();
		String id = sdfId.format(new Date()) + StringUtils.padding(idExt, 55, ' ', true) ;
		
		pstm.setString(1, id);
		pstm.setString(2, idExt);
		pstm.setString(3, idOrig);
		pstm.setLong(4, ctx.getIdFonte());
		
		pstm.setDate(++lastParam, new java.sql.Date(new Date().getTime()));
		pstm.setDate(++lastParam, null);
		pstm.setDate(++lastParam, new java.sql.Date(new Date().getTime()));
		pstm.setDate(++lastParam, new java.sql.Date(new Date().getTime()));
		pstm.setDate(++lastParam, null);
		pstm.setString(++lastParam, hash);
		pstm.setDate(++lastParam, null);
		pstm.setDate(++lastParam, null);
		pstm.setLong(++lastParam, new Long(0));
		pstm.setString(++lastParam, ctx.getProcessID());
		
		return pstm;
	}
	
	private void salvaInDb(PreparedStatement pstmt, List<Object> listaDati, String idOrig) throws SQLException {
			
		int i = 5;
		pstmt.clearParameters();
		for(Object obj: listaDati){
			if(obj.getClass().getName().equals("it.webred.ct.data.access.basic.common.dto.KeyValueDTO")){
				KeyValueDTO kv = (KeyValueDTO) obj;
				pstmt.setNull(i, new Integer(kv.getKey()));
			}
			if(obj.getClass() == java.lang.String.class){
				String s = ((String) obj).trim();
				pstmt.setString(i, s);
			}
			else if(obj.getClass() == java.lang.Integer.class)
				pstmt.setBigDecimal(i, new BigDecimal((Integer) obj));
			else if(obj.getClass() == java.lang.Long.class)
				pstmt.setBigDecimal(i, new BigDecimal((Long) obj));
			else if(obj.getClass().getName().equals("java.math.BigDecimal"))
				pstmt.setBigDecimal(i, (BigDecimal) obj);
			else if(obj.getClass().getName().equals("java.util.Date")
					|| obj.getClass().getSuperclass().getName().equals("java.util.Date"))
				pstmt.setDate(i, obj!=null?new java.sql.Date(((Date)obj).getTime()):null);
			i++;
		}
		setDefaultParameters(pstmt, --i, idOrig.trim(), getHash(idOrig.trim()));
		pstmt.executeUpdate();

	}
	
	private String getHash(String value){
		MessageDigest md = null;
		String hash = null;
		
		try{
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e){
			log.error(e);
		}
		
		if (value != null) {
			md.update(value.getBytes());
	
			byte[] b = md.digest();
			hash = new String(StringUtils.toHexString(b));	
		}
		
		return hash;
	}
	
	@Override
	public boolean filtroFile(String file, List<String> fileDaElaborare, String cartellaDati) {	
		BufferedReader fileIn = null;
		try {
			
			fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(cartellaDati+file), "UTF8"));
			String testa = fileIn.readLine();
			String record = testa.substring(1,3);
			
			if("IM".equals(record))
				return true;
			
		} catch (Exception e) {
			log.error("_______ERRORE FILTRO FILE: " + file,e);
		}finally{
			if (fileIn!=null)
				try {
					fileIn.close();
				} catch (IOException e) {
					log.error("_______ERRORE CHIUSURA BUFFERED READER",e);
				}
		}
		return false;
	}
	
	@Override
	public void postElaborazioneAction(String file, List<String> fileDaElaborare, String cartellaFiles) {

		// sposto il file su ELABORATI
		new File(cartellaFiles+file).renameTo(new File(cartellaFiles+"ELABORATI/"+file));
		
	}
}

