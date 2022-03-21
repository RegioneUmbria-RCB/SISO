package it.webred.rulengine.brick.loadDwh.load.tassasoggiorno.rnd;

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
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TSRndFiles<T extends TSRndEnv> extends ImportFiles<T> {
	
	private SimpleDateFormat sdfId = new SimpleDateFormat("yyyyMMdd hhmmss");
	private SimpleDateFormat sdfDate = new SimpleDateFormat("ddMMyyyy");
	private NumberFormat format = NumberFormat.getInstance(Locale.ITALY);
	//QUERY
	private PreparedStatement pstmtFlu = null;
	private PreparedStatement pstmtBon = null;
	private PreparedStatement pstmtLiquid = null;
	private PreparedStatement pstmtMov = null;
	private PreparedStatement pstmtMovInfo = null;
	private PreparedStatement pstmtPrat = null;
	private PreparedStatement pstmtSal = null;
	//DATA
	private String currentTesta;
	private String idFlusso;
	private List dataFlusso;
	//NULL TYPE
	private KeyValueDTO nullBD = new KeyValueDTO(String.valueOf(java.sql.Types.INTEGER), null);
	private KeyValueDTO nullS = new KeyValueDTO(String.valueOf(java.sql.Types.VARCHAR), null);
	private KeyValueDTO nullD = new KeyValueDTO(String.valueOf(java.sql.Types.DATE), null);
	
	public TSRndFiles(T env) {
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
				
				if("RH".equals(record) || "EC".equals(record) || "RA".equals(record) || "DT".equals(record) || "RP".equals(record)){
					currentTesta = record;
					elaboraRecordTesta(currentLine, record);
				}
				else if("EF".equals(record))
					elaboraRecordCoda(currentLine, record);
				else if("61".equals(record)
						|| ("RH".equals(currentTesta) && "64".equals(record))
						|| ("EC".equals(currentTesta) && "64".equals(record))
						|| ("RA".equals(currentTesta) && "65".equals(record))
						|| ("RP".equals(currentTesta) && "64".equals(record))
						|| "10".equals(record))
					elaboraRecordSaldo(currentLine, record);
				else if(("RH".equals(currentTesta) && "62".equals(record))
						|| ("EC".equals(currentTesta) && "62".equals(record))
						|| ("RA".equals(currentTesta) && "63".equals(record))
						|| ("RP".equals(currentTesta) && "62".equals(record))
						|| "20".equals(record))
					elaboraRecordMovimento(currentLine, record);
				else if(("RH".equals(currentTesta) && "63".equals(record))
						|| ("EC".equals(currentTesta) && "63".equals(record))
						|| ("RA".equals(currentTesta) && "64".equals(record))
						|| ("RP".equals(currentTesta) && "63".equals(record)))
					elaboraRecordInfoMovimento(currentLine, record);
				else if(("RH".equals(currentTesta) && "65".equals(record)))
					elaboraRecordLiquidita(currentLine, record);
				else if(("RA".equals(currentTesta) && "62".equals(record)))
					elaboraRecordPratica(currentLine, record);
				
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
			sql = setInsertSql(env.tableLiquidita, 31);
			pstmtLiquid = con.prepareStatement(sql);
			sql = setInsertSql(env.tableMovimento, 35);
			pstmtMov = con.prepareStatement(sql);
			sql = setInsertSql(env.tableMovimentoInfo, 42);
			pstmtMovInfo = con.prepareStatement(sql);
			sql = setInsertSql(env.tablePratica, 25);
			pstmtPrat = con.prepareStatement(sql);
			sql = setInsertSql(env.tableSaldo, 35);
			pstmtSal = con.prepareStatement(sql);

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
			if (pstmtLiquid != null)
				pstmtLiquid.close();
			if (pstmtMov != null)
				pstmtMov.close();
			if (pstmtMovInfo != null)
				pstmtMovInfo.close();
			if (pstmtPrat != null)
				pstmtPrat.close();
			if (pstmtSal != null)
				pstmtSal.close();
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
		
		idFlusso = currentLine.substring(3, 39).trim();
		dataFlusso = new ArrayList();
		dataFlusso.add(currentLine.substring(1, 3));//tipo record
		dataFlusso.add(currentLine.substring(3, 8));//mittente
		dataFlusso.add(currentLine.substring(8, 13));//ricevente
		dataFlusso.add(nullD);//data rendicontazione
		String data = currentLine.substring(13, 19);
		String anno = "20" + data.substring(4,6);
		data = data.substring(0,4) + anno;
		dataFlusso.add(sdfDate.parseObject(data));//data crezione
		dataFlusso.add(nullS);//cod azienda
		dataFlusso.add(currentLine.substring(19, 39));//nome supporto
		dataFlusso.add(currentLine.substring(39, 45));//campo azienda
		dataFlusso.add(nullS);//qualificatore flusso
		dataFlusso.add(nullBD);//prog
		dataFlusso.add(nullS);//cod divisa

	}
	
	private void elaboraRecordCoda(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		String numDisp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(45, 52));
		dataFlusso.add(numDisp != null? new BigDecimal(numDisp):nullBD);//num rendicontazioni
		dataFlusso.add(nullBD);//tot importo negativo
		dataFlusso.add(nullBD);//tot importo positivo
		dataFlusso.add(0);//flg mav
		dataFlusso.add(1);//flg bonifico
		
		salvaInDb(pstmtFlu, dataFlusso, idFlusso);
		
	}
	
	private void elaboraRecordSaldo(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		boolean iniziale = false;
		boolean finale = false;
		boolean titolo = false;
		List dataSaldo = new ArrayList();
		dataSaldo.add(idFlusso);
		String prog = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(3, 10));
		dataSaldo.add(prog != null? new BigDecimal(prog):nullBD);//prog
		if("61".equals(tipoRecord)){
			dataSaldo.add("INIZIALE");//tipo saldo
			iniziale = true;
		}
		else if("64".equals(tipoRecord)  || "65".equals(tipoRecord)){
			dataSaldo.add("FINALE");//tipo saldo
			finale = true;
		}
		else if("10".equals(tipoRecord)){
			dataSaldo.add("TITOLO");//tipo saldo
			titolo = true;
		}
		
		if(iniziale){
			String abi = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(23, 28));
			dataSaldo.add(abi != null? new BigDecimal(abi):nullBD);//abi
			String causale = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(28, 33));
			dataSaldo.add(causale != null? new BigDecimal(causale):nullBD);//causale
			dataSaldo.add(currentLine.substring(33, 49));//descrizione
			dataSaldo.add(currentLine.substring(49, 51));//tipo conto
			dataSaldo.add(currentLine.substring(51, 74));//coord bancarie
			dataSaldo.add(currentLine.substring(74, 77));//codice divisa
			String data = currentLine.substring(77, 83);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataSaldo.add(sdfDate.parseObject(data));//data contabile
			dataSaldo.add(currentLine.substring(83, 84));//segno cont
			String sal = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(84, 99));
			dataSaldo.add(sal != null? new BigDecimal(format.parse(sal).doubleValue()):nullBD);//saldo cont
			dataSaldo.add(nullBD);//segno liquido
			dataSaldo.add(nullBD);//saldo liquido
			dataSaldo.add(currentLine.substring(99, 103));//iban
			dataSaldo.add(nullS);//isin
			dataSaldo.add(nullD);//data scadenza
			dataSaldo.add(nullD);//data godimento prima
			dataSaldo.add(nullD);//data godimento seconda
			dataSaldo.add(nullD);//data godimento terza
			dataSaldo.add(nullD);//data godimento quarta
		}
		
		if(finale){
			dataSaldo.add(nullS);//abi
			dataSaldo.add(nullS);//causale
			dataSaldo.add(nullS);//descrizione
			dataSaldo.add(nullS);//tipo conto
			dataSaldo.add(nullS);//coord bancarie
			dataSaldo.add(currentLine.substring(10, 13));//codice divisa
			String data = currentLine.substring(13, 19);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataSaldo.add(sdfDate.parseObject(data));//data contabile
			dataSaldo.add(currentLine.substring(19, 20));//segno cont
			String sal = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(20, 35));
			dataSaldo.add(sal != null? new BigDecimal(format.parse(sal).doubleValue()):nullBD);//saldo cont
			dataSaldo.add(currentLine.substring(35, 36));//segno liquido
			String sall = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(36, 51));
			dataSaldo.add(sall != null? new BigDecimal(format.parse(sall).doubleValue()):nullBD);//saldo liquido
			dataSaldo.add(nullS);//iban
			dataSaldo.add(nullS);//isin
			dataSaldo.add(nullD);//data scadenza
			dataSaldo.add(nullD);//data godimento prima
			dataSaldo.add(nullD);//data godimento seconda
			dataSaldo.add(nullD);//data godimento terza
			dataSaldo.add(nullD);//data godimento quarta
		}
		
		if(titolo){
			dataSaldo.add(nullBD);//abi
			String causale = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(28, 33));
			dataSaldo.add(causale != null? new BigDecimal(causale):nullBD);//causale
			dataSaldo.add(currentLine.substring(45, 65));//descrizione
			dataSaldo.add(nullS);//tipo conto
			dataSaldo.add(currentLine.substring(68, 91));//coord bancarie
			dataSaldo.add(currentLine.substring(65, 68));//codice divisa
			String data = currentLine.substring(91, 97);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataSaldo.add(sdfDate.parseObject(data));//data contabile
			dataSaldo.add(nullS);//segno cont
			String sal = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 28));
			dataSaldo.add(sal != null? new BigDecimal(format.parse(sal).doubleValue()):nullBD);//saldo attuale
			dataSaldo.add(nullBD);//segno liquido
			dataSaldo.add(nullBD);//saldo liquido
			dataSaldo.add(nullS);//iban
			dataSaldo.add(currentLine.substring(33, 45));//isin
			data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(97, 103));
			if(data != null){
				anno = "20" + data.substring(4,6);
				data = data.substring(0,4) + anno;
				dataSaldo.add(sdfDate.parseObject(data));//data scadenza
			}else dataSaldo.add(nullD);//data scadenza
			data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(103, 107));
			if(data != null){
				data = data + anno;
				dataSaldo.add(sdfDate.parseObject(data));//data godimento prima
			} else dataSaldo.add(nullD);//data godimento prima
			data = currentLine.substring(107, 111);
			data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(103, 107));
			if(data != null){
				data = data + anno;
				dataSaldo.add(sdfDate.parseObject(data));//data godimento sec
			} else dataSaldo.add(nullD);//data godimento sec
			data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(111, 115));
			if(data != null){
				data = data + anno;
				dataSaldo.add(sdfDate.parseObject(data));//data godimento ter
			} else dataSaldo.add(nullD);//data godimento ter
			data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(115, 119));
			if(data != null){
				data = data + anno;
				dataSaldo.add(sdfDate.parseObject(data));//data godimento quar
			} else dataSaldo.add(nullD);//data godimento quar
		}
		
		salvaInDb(pstmtSal, dataSaldo, idFlusso + dataSaldo.get(1) + dataSaldo.get(2));
		
	}
	
	private void elaboraRecordMovimento(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List dataMov = new ArrayList();
		dataMov.add(idFlusso);
		String prog = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(3, 10));
		String prog_pra = null;
		dataMov.add(prog != null? new BigDecimal(prog):nullBD);//prog
		
		if("RA".equals(currentTesta)){
			String prog_mov = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(22, 25));
			dataMov.add(prog_mov != null? new BigDecimal(prog_mov):nullBD);//prog mov
			prog_pra = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 22));
			dataMov.add(prog_pra != null? new BigDecimal(prog_pra):nullBD);//prog pratica
			String data = currentLine.substring(25, 31);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataMov.add(sdfDate.parseObject(data));//data valuta
			data = currentLine.substring(31, 37);
			anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataMov.add(sdfDate.parseObject(data));//data reg contabile
			dataMov.add(currentLine.substring(37, 38));//segno
			String imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(38, 53));
			dataMov.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//importo
			dataMov.add(currentLine.substring(53, 55));//causale cbi
			dataMov.add(currentLine.substring(55, 57));//causale interna
			dataMov.add(nullS);//numero assegno
			dataMov.add(currentLine.substring(57, 73));//rif banca
			dataMov.add(nullS);//tipo rif cliente
			dataMov.add(currentLine.substring(73, 82));//rif cliente
			dataMov.add(currentLine.substring(82, 120));//descr
			dataMov.add(nullS);//isin
			dataMov.add(nullS);//cod divisa
			dataMov.add(nullS);//quantita
			dataMov.add(nullS);//cab
			dataMov.add(nullS);//num dossier
			dataMov.add(nullS);//num rif
		}
		
		else if("DT".equals(currentTesta)){
			dataMov.add(nullBD);//prog mov
			dataMov.add(nullBD);//prog pratica
			dataMov.add(nullD);//data valuta
			String data = currentLine.substring(64, 70);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataMov.add(sdfDate.parseObject(data));//data mov
			dataMov.add(currentLine.substring(63, 64));//segno
			dataMov.add(nullBD);//importo
			dataMov.add(nullS);//causale cbi
			dataMov.add(currentLine.substring(70, 74));//causale mov
			dataMov.add(nullS);//numero assegno
			dataMov.add(nullS);//rif banca
			dataMov.add(nullS);//tipo rif cliente
			dataMov.add(nullS);//rif cliente
			dataMov.add(currentLine.substring(22, 42));//descr
			dataMov.add(currentLine.substring(10, 22));//isin
			dataMov.add(currentLine.substring(42, 45));//cod divisa
			dataMov.add(currentLine.substring(45, 63));//quantita
			String cab = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(74, 79));
			dataMov.add(cab != null? new BigDecimal(cab):nullBD);//cab
			dataMov.add(currentLine.substring(79, 92));//num dossier
			dataMov.add(currentLine.substring(92, 110));//num rif
		}
		
		else if("RP".equals(currentTesta)){
			String prog_mov = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 13));
			dataMov.add(prog_mov != null? new BigDecimal(prog_mov):nullBD);//prog mov
			dataMov.add(nullBD);//prog pratica
			String data = currentLine.substring(13, 19);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataMov.add(sdfDate.parseObject(data));//data valuta
			data = currentLine.substring(19, 25);
			anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataMov.add(sdfDate.parseObject(data));//data reg contabile
			dataMov.add(currentLine.substring(25, 26));//segno
			String imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(26, 41));
			dataMov.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//importo
			dataMov.add(currentLine.substring(41, 43));//causale cbi
			dataMov.add(currentLine.substring(43, 45));//causale interna
			dataMov.add(nullS);//numero assegno
			dataMov.add(currentLine.substring(61, 77));//rif banca
			dataMov.add(nullS);//tipo rif cliente
			dataMov.add(currentLine.substring(77, 86));//rif cliente
			dataMov.add(currentLine.substring(86, 120));//descr
			dataMov.add(nullS);//isin
			dataMov.add(nullS);//cod divisa
			dataMov.add(nullS);//quantita
			dataMov.add(nullS);//cab
			dataMov.add(nullS);//num dossier
			dataMov.add(nullS);//num rif
		}
		
		else {
			String prog_mov = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 13));
			dataMov.add(prog_mov != null? new BigDecimal(prog_mov):nullBD);//prog mov
			dataMov.add(nullBD);//prog pratica
			String data = currentLine.substring(13, 19);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataMov.add(sdfDate.parseObject(data));//data valuta
			data = currentLine.substring(19, 25);
			anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			dataMov.add(sdfDate.parseObject(data));//data reg contabile
			dataMov.add(currentLine.substring(25, 26));//segno
			String imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(26, 41));
			dataMov.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//importo
			dataMov.add(currentLine.substring(41, 43));//causale cbi
			dataMov.add(currentLine.substring(43, 45));//causale interna
			dataMov.add(currentLine.substring(45, 61));//numero assegno
			dataMov.add(currentLine.substring(61, 77));//rif banca
			dataMov.add(currentLine.substring(77, 86));//tipo rif cliente
			dataMov.add(currentLine.substring(86, 120));//rif cliente
			dataMov.add(nullS);//descr
			dataMov.add(nullS);//isin
			dataMov.add(nullS);//cod divisa
			dataMov.add(nullS);//quantita
			dataMov.add(nullS);//cab
			dataMov.add(nullS);//num dossier
			dataMov.add(nullS);//num rif
		}
		
		salvaInDb(pstmtMov, dataMov, idFlusso + dataMov.get(1) + (prog_pra == null?"":dataMov.get(3)) + dataMov.get(2));
		
	}
	
	private void elaboraRecordInfoMovimento(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List listaDati = new ArrayList();
		listaDati.add(idFlusso);
		String prog = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(3, 10));
		listaDati.add(prog != null? new BigDecimal(prog):nullBD);//prog
		
		if("RA".equals(currentTesta)){
			String prog_mov = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(13, 16));
			listaDati.add(prog_mov != null? new BigDecimal(prog_mov):nullBD);//prog mov
			String prog_pra = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 13));
			listaDati.add(prog_pra != null? new BigDecimal(prog_pra):nullBD);//prog pratica
			listaDati.add(nullS);//flg struttura
			listaDati.add(nullS);//ide rapporto
			listaDati.add(nullS);//data ordine
			listaDati.add(nullS);//codifica fisc
			listaDati.add(nullS);//descr ordinante
			listaDati.add(nullS);//importo orig
			listaDati.add(nullS);//cod divisa orig
			listaDati.add(nullS);//importo regol
			listaDati.add(nullS);//cod divisa regol
			listaDati.add(nullS);//importo neg
			listaDati.add(nullS);//cod divisa neg
			listaDati.add(nullS);//tasso cambio
			listaDati.add(nullS);//commissioni
			listaDati.add(nullS);//spese
			listaDati.add(nullS);//benfic
			listaDati.add(nullS);//motivo
			listaDati.add(currentLine.substring(16, 120));//descr
			listaDati.add(nullS);//ide msg
			listaDati.add(nullS);//ide end
			listaDati.add(nullS);//info riconciliazione
			listaDati.add(nullS);//data creazione
			listaDati.add(nullS);//nome supp
			listaDati.add(nullS);//num disp
			listaDati.add(nullS);//importo tot disp
		}else if("RP".equals(currentTesta)){
			String prog_mov = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 13));
			listaDati.add(prog_mov != null? new BigDecimal(prog_mov):nullBD);//prog mov
			listaDati.add(nullS);//prog pratica
			listaDati.add(nullS);//flg struttura
			listaDati.add(nullS);//ide rapporto
			listaDati.add(nullS);//data ordine
			listaDati.add(nullS);//codifica fisc
			listaDati.add(nullS);//descr ordinante
			listaDati.add(nullS);//importo orig
			listaDati.add(nullS);//cod divisa orig
			listaDati.add(nullS);//importo regol
			listaDati.add(nullS);//cod divisa regol
			listaDati.add(nullS);//importo neg
			listaDati.add(nullS);//cod divisa neg
			listaDati.add(nullS);//tasso cambio
			listaDati.add(nullS);//commissioni
			listaDati.add(nullS);//spese
			listaDati.add(nullS);//benfic
			listaDati.add(nullS);//motivo
			listaDati.add(currentLine.substring(60, 120));//descr
			listaDati.add(nullS);//ide msg
			listaDati.add(nullS);//ide end
			listaDati.add(nullS);//info riconciliazione
			String data = currentLine.substring(13, 19);
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(data != null?sdfDate.parseObject(data):nullD);//data creazione
			listaDati.add(currentLine.substring(19, 39));//nome supp
			String disp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(39, 46));
			listaDati.add(disp != null? new BigDecimal(disp):nullBD);//num disp
			String imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(46, 60));
			listaDati.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//importo tot disp
		}else {
			String prog_mov = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 13));
			listaDati.add(prog_mov != null? new BigDecimal(prog_mov):nullBD);//prog mov
			listaDati.add(nullS);//prog pratica
			String flg_struttura = currentLine.substring(13, 16); 
			listaDati.add(flg_struttura);//flg struttura
			
			if("KKK".equals(flg_struttura)){
				listaDati.add(currentLine.substring(16, 39));//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(nullS);//descr ordinante
				listaDati.add(nullS);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullS);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullS);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullS);//tasso cambio
				listaDati.add(nullS);//commissioni
				listaDati.add(nullS);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(nullS);//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(nullS);//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else if("YYY".equals(flg_struttura)) {
				listaDati.add(nullS);//ide rapporto
				String data = currentLine.substring(16, 24);
				listaDati.add(sdfDate.parseObject(data));//data ordine
				listaDati.add(currentLine.substring(24, 40));//codifica fisc
				listaDati.add(currentLine.substring(40, 120));//descr ordinante
				listaDati.add(nullS);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullS);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullS);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullS);//tasso cambio
				listaDati.add(nullS);//commissioni
				listaDati.add(nullS);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(nullS);//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(nullS);//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else if("ZZ1".equals(flg_struttura)) {
				listaDati.add(nullS);//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(nullS);//descr ordinante
				String imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(16, 34));
				listaDati.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//importo orig
				listaDati.add(currentLine.substring(34, 37));//cod divisa orig
				imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(37, 55));
				listaDati.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//importo regol
				listaDati.add(currentLine.substring(55, 58));//cod divisa regol
				imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(58, 76));
				listaDati.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//importo neg
				listaDati.add(currentLine.substring(76, 79));//cod divisa neg
				imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(79, 91));
				listaDati.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//tasso cambio
				imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(91, 104));
				listaDati.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//commissioni
				imp = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(104, 117));
				listaDati.add(imp != null? new BigDecimal(format.parse(imp).doubleValue()):nullBD);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(nullS);//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(nullS);//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else if("ZZ2".equals(flg_struttura)) {
				listaDati.add(nullS);//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(currentLine.substring(16, 120));//descr ordinante
				listaDati.add(nullBD);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullBD);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullBD);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullBD);//tasso cambio
				listaDati.add(nullBD);//commissioni
				listaDati.add(nullBD);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(nullS);//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(nullS);//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else if("ZZ3".equals(flg_struttura)) {
				listaDati.add(nullS);//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(nullS);//descr ordinante
				listaDati.add(nullBD);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullBD);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullBD);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullBD);//tasso cambio
				listaDati.add(nullBD);//commissioni
				listaDati.add(nullBD);//spese
				listaDati.add(currentLine.substring(16, 66));//benfic
				listaDati.add(currentLine.substring(66, 120));//motivo
				listaDati.add(nullS);//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(nullS);//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else if("ID1".equals(flg_struttura)) {
				listaDati.add(nullS);//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(nullS);//descr ordinante
				listaDati.add(nullBD);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullBD);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullBD);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullBD);//tasso cambio
				listaDati.add(nullBD);//commissioni
				listaDati.add(nullBD);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(nullS);//descr
				listaDati.add(currentLine.substring(16, 51));//ide msg
				listaDati.add(currentLine.substring(51, 86));//ide end
				listaDati.add(nullS);//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else if("RI1".equals(flg_struttura)) {
				listaDati.add(nullS);//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(nullS);//descr ordinante
				listaDati.add(nullBD);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullBD);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullBD);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullBD);//tasso cambio
				listaDati.add(nullBD);//commissioni
				listaDati.add(nullBD);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(nullS);//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(currentLine.substring(16, 120));//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else if("RI2".equals(flg_struttura)) {
				listaDati.add(nullS);//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(nullS);//descr ordinante
				listaDati.add(nullBD);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullBD);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullBD);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullBD);//tasso cambio
				listaDati.add(nullBD);//commissioni
				listaDati.add(nullBD);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(nullS);//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(currentLine.substring(16, 52));//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}else {
				listaDati.add(nullS);//ide rapporto
				listaDati.add(nullS);//data ordine
				listaDati.add(nullS);//codifica fisc
				listaDati.add(nullS);//descr ordinante
				listaDati.add(nullBD);//importo orig
				listaDati.add(nullS);//cod divisa orig
				listaDati.add(nullBD);//importo regol
				listaDati.add(nullS);//cod divisa regol
				listaDati.add(nullBD);//importo neg
				listaDati.add(nullS);//cod divisa neg
				listaDati.add(nullBD);//tasso cambio
				listaDati.add(nullBD);//commissioni
				listaDati.add(nullBD);//spese
				listaDati.add(nullS);//benfic
				listaDati.add(nullS);//motivo
				listaDati.add(currentLine.substring(13, 120));//descr
				listaDati.add(nullS);//ide msg
				listaDati.add(nullS);//ide end
				listaDati.add(nullS);//info riconciliazione
				listaDati.add(nullS);//data creazione
				listaDati.add(nullS);//nome supp
				listaDati.add(nullS);//num disp
				listaDati.add(nullS);//importo tot disp
			}
		}
		
		salvaInDb(pstmtMovInfo, listaDati, getHash(idFlusso + listaDati.get(1) + listaDati.get(2) + listaDati.get(3) + listaDati.get(4) + listaDati.get(20)));
	}
	
	private void elaboraRecordPratica(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List listaDati = new ArrayList();
		listaDati.add(idFlusso);
		String prog = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(3, 10));
		listaDati.add(prog != null? new BigDecimal(prog):nullBD);//prog
		prog = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 13));
		listaDati.add(prog != null? new BigDecimal(prog):nullBD);//prog prat
		String data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(13, 19));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//data acc
		}else listaDati.add(nullD);//data acc
		data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(19, 25));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//data scad
		}else listaDati.add(nullD);//data scad
		String tasso = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(25, 33));
		listaDati.add(tasso != null? new BigDecimal(format.parse(tasso).doubleValue()):nullBD);//tasso
		data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(33, 39));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//data scad tasso
		}else listaDati.add(nullD);//data scad tasso
		String num = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(39, 51));
		listaDati.add(prog != null? new BigDecimal(prog):nullBD);//numero
		listaDati.add(currentLine.substring(51, 52));//tipo
		String saldo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(53, 68));
		listaDati.add(saldo != null? new BigDecimal(format.parse(saldo).doubleValue()):nullBD);//saldo ini
		saldo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(69, 84));
		listaDati.add(saldo != null? new BigDecimal(format.parse(saldo).doubleValue()):nullBD);//saldo fin
		
		salvaInDb(pstmtPrat, listaDati, idFlusso + listaDati.get(1));
	}
	
	private void elaboraRecordLiquidita(String currentLine, String tipoRecord) throws ParseException, SQLException{
		
		List listaDati = new ArrayList();
		listaDati.add(idFlusso);
		listaDati.add(currentLine.substring(3, 10));//progr
		String data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(10, 16));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//primo data
		}else listaDati.add(nullD);//primo data
		listaDati.add(currentLine.substring(16, 17));//primo segno
		String saldo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(17, 32));
		listaDati.add(saldo != null? new BigDecimal(format.parse(saldo).doubleValue()):nullBD);//primo saldo
		data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(32, 38));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//secondo data
		}else listaDati.add(nullD);//secondo data
		listaDati.add(currentLine.substring(38, 39));//secondo segno
		saldo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(39, 54));
		listaDati.add(saldo != null? new BigDecimal(format.parse(saldo).doubleValue()):nullBD);//secondo saldo
		data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(54, 60));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//terzo data
		}else listaDati.add(nullD);//terzo data
		listaDati.add(currentLine.substring(60, 61));//terzo segno
		saldo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(61, 76));
		listaDati.add(saldo != null? new BigDecimal(format.parse(saldo).doubleValue()):nullBD);//terzo saldo
		data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(76, 82));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//quarto data
		}else listaDati.add(nullD);//quarto data
		listaDati.add(currentLine.substring(82, 83));//quarto segno
		saldo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(83, 98));
		listaDati.add(saldo != null? new BigDecimal(format.parse(saldo).doubleValue()):nullBD);//quarto saldo
		data = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(98, 104));
		if(data != null){
			String anno = "20" + data.substring(4,6);
			data = data.substring(0,4) + anno;
			listaDati.add(sdfDate.parseObject(data));//quinto data
		}else listaDati.add(nullD);//quinto data
		listaDati.add(currentLine.substring(104, 105));//quinto segno
		saldo = org.apache.commons.lang.StringUtils.trimToNull(currentLine.substring(105, 120));
		listaDati.add(saldo != null? new BigDecimal(format.parse(saldo).doubleValue()):nullBD);//quinto saldo
		
		salvaInDb(pstmtPrat, listaDati, idFlusso + listaDati.get(1) + listaDati.get(2));
		
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
			
			if("RH".equals(record)
					|| "EC".equals(record)
					|| "RA".equals(record)
					|| "DT".equals(record)
					|| "RP".equals(record))
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

