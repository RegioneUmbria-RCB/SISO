package it.webred.rulengine.brick.loadDwh.load.redditi.analitici;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportFactory;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import java.util.Properties;


import org.apache.commons.dbutils.DbUtils;
import java.sql.Types;


public class RedditiAnFiles<T extends RedditiAnEnv> extends ImportFiles<T> {

	private SimpleDateFormat sdfId = new SimpleDateFormat("yyyyMMdd hhmmss");
	//FLAG PERSONA
	private static final String flgPersonaDichiarante = "D";
	private static final String flgPersonaConiuge = "C";
	private static final String flgPersonaRappresentante = "R";
	private static final String flgPersonaSostituto = "S";
	//QUERY
	private PreparedStatement pstmtRiga = null;
	private PreparedStatement pstmtAtt = null;
	private PreparedStatement pstmtAna = null;
	private PreparedStatement pstmtDesc = null;
	private PreparedStatement pstmtDom = null;
	private PreparedStatement pstmtRed = null;
	private PreparedStatement pstmtTrasc = null;
	private PreparedStatement pstmtRA = null;
	private PreparedStatement pstmtRB = null;
	//DATI GENERALI
	private Properties RedProperties = null;
	private String annoImposta;
	private String ideTelematico = null;
	private String modello = null;
	private String codFiscDich = null;
	private String codFiscSost = null;
	private String codiceQuadro = null;
	private String valore = null;
	private String descrizione = null;
	//FLAG
	private String belfiore;
	private boolean comuneOk = false;
	private boolean filtroComune = false;
	
	//QUADRO RA
	private Properties RAProperties = null; 
	private boolean readRA;
	private Map<String, String> mapDatiRA = new HashMap<String, String>();
	
	//QUADRO RB
	private Properties RBProperties = null; 
	private boolean readRB;
	private String codFisc = null;
	private Map<String, String> mapDatiRB = new HashMap<String, String>();
	
	public RedditiAnFiles(T env) {
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
		
		// recupero dati frontespizio
		modello = currentLine.substring(11, 12);
		ideTelematico = currentLine.substring(30, 55);
		tipoRecord = currentLine.substring(21, 22);
		
		if (!"9".equals(tipoRecord)) {
			elaboraRiga(currentLine, tipoRecord);
		}
		
		if ("A".equals(tipoRecord)
				|| "B".equals(tipoRecord)
				|| "C".equals(tipoRecord)
				|| "D".equals(tipoRecord)
				|| "E".equals(tipoRecord)
				|| "F".equals(tipoRecord)
				|| "H".equals(tipoRecord)
				|| "I".equals(tipoRecord)
				|| "O".equals(tipoRecord)) {
			elaboraFrontespizio(currentLine, tipoRecord);
			
		}

		if(comuneOk){
			// salvo dati
			salvaAttivita(ideTelematico,
					codFiscDich, codFiscSost, annoImposta);
	
			// recupero e salvo redditi
			if ("R".equals(tipoRecord)
							|| "Q".equals(tipoRecord)
							|| "V".equals(tipoRecord)
							|| "U".equals(tipoRecord) 
							|| "P".equals(tipoRecord)) {
	
				String rigaRedditi = "";
				if ("R".equals(tipoRecord)
						|| "Q".equals(tipoRecord)
						|| "V".equals(tipoRecord))
					rigaRedditi = currentLine.substring(81);
				else
					rigaRedditi = currentLine.substring(82);
	
				// tolgo il tipo record
				rigaRedditi = rigaRedditi.substring(1);
				String[] tokens = rigaRedditi.split("(?<=\\G.{"
						+ 32 + "})");
				for (int j = 1; j < tokens.length; j++) {
	
					String valoriQuadro = tokens[j];
					if (valoriQuadro.length() == 32) {
						codiceQuadro = valoriQuadro.substring(0, 13).trim();
						String flgValuta = valoriQuadro.substring(13, 14);
						valore = valoriQuadro.substring(14);
	
						if (codiceQuadro.length() == 13) {
							//Commento temporaneamente per Stefano(a Milano lo spazio richiesto su DB è troppo elevato)
							/*
							 * 
							 salvaReddito(
									ideTelematico, codFiscDich,
									codiceQuadro, valore, annoImposta);
							*/
							salvaTrascodifica(
									annoImposta, codiceQuadro,
									descrizione, modello,
									flgValuta);
						}
	
						codiceQuadro = null;
						valore = null;
					}
	
				}
			}
		}
		
		//Recupero Quadro RB
		if(readRB)
			elaboraQuadroRB(currentLine, tipoRecord);
		
		if(readRA)
			elaboraQuadroRA(currentLine,tipoRecord);
		
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
		// TODO Auto-generated method stub
		
	}

	protected void procesingFile(String file, String cartellaDati) throws RulEngineException {
		boolean gestisciTmp = false;
		boolean disabilitaStorico = false;
		if (env.getEnteSorgente().isInReplace())
			gestisciTmp = true;

		if (env.getEnteSorgente().isDisabilitaStorico())
			disabilitaStorico = true;
		
		belfiore = ctx.getBelfiore();
		
		try {

			String sqlRiga = "insert into "
					+ env.tableRiga
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtRiga = con.prepareStatement(sqlRiga);
			
			String sqlAtt = "insert into "
					+ env.tableAttivita
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtAtt = con.prepareStatement(sqlAtt);

			String sqlAna = "insert into "
					+ env.tableAnagrafici
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtAna = con.prepareStatement(sqlAna);

			String sqlDesc = "insert into "
					+ env.tableDescrizione
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtDesc = con.prepareStatement(sqlDesc);

			String sqlDom = "insert into "
					+ env.tableDomicilio
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtDom = con.prepareStatement(sqlDom);

			String sqlRed = "insert into "
					+ env.tableRedditi
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtRed = con.prepareStatement(sqlRed);

			String sqlTr = "insert into "
					+ env.tableTrascodifica
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtTrasc = con.prepareStatement(sqlTr);

			pstmtRB = con.prepareStatement(this.getSqlRB());
			pstmtRA = con.prepareStatement(this.getSqlRA());
			
		} catch (SQLException e) {
			throw new RulEngineException("Problema in creazione Statement ",e);
		}
		
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		ConcreteImport ci = ConcreteImportFactory.getConcreteImport(this);
		tabs = ci.getTabelleAndTipiRecord();
		
		//controllo se il db fa riferimento ad un comune
		//e quindi filtro il domicilio fiscale o se è generale
		if(checkBelfiore(belfiore))
			filtroComune =true;
		
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
			if (pstmtAna != null)
				pstmtAna.close();
			if (pstmtAtt != null)
				pstmtAtt.close();
			if (pstmtDesc != null)
				pstmtDesc.close();
			if (pstmtDom != null)
				pstmtDom.close();
			if (pstmtRed != null)
				pstmtRed.close();
			if (pstmtTrasc != null)
				pstmtTrasc.close();
			if (pstmtRB != null)
				pstmtRB.close();
			if (pstmtRA != null)
				pstmtRA.close();
		} catch (SQLException e) {
			throw new RulEngineException("Problema in chiusura connessione",e);
		}
		
	}
	
	private String getSqlRA(){
		String sqlRa = "insert into "
				+ env.tableRA
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return sqlRa;
	}
	
	
	private String getSqlRB(){
		String sqlRb = "insert into "
				+ env.tableRB
				+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return sqlRb;
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
			fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(cartellaDati+file), "UTF8"));
			
			String currentLine=null;
			int riga = 1;
			int cont = 1;
			String insertSql = null;
			
			//controllo Redditi: se il file properties che definisce modello e anno
			//non esiste lo salto, stessa cosa per il quadro RB
			String[] arr = file.split("\\.");
			boolean existRedditiProp = loadRedProperties(arr[2]);
			
			if(existRedditiProp) {
				boolean existRAProp = loadRAProperties("quadroRA_" + arr[2]);
				readRA = true;
				if(!existRAProp)
					readRA = false;
				
				boolean existRBProp = loadRBProperties("quadroRB_" + arr[2]);
				readRB = true;
				if(!existRBProp)
					readRB = false;
			
				
				while ((currentLine = fileIn.readLine()) != null)
				{
					
					if(!"".equals(currentLine)){
						// traccia file forniture
						if (riga==1) {
							try {
								annoImposta = currentLine.substring(39, 43);
								tracciaFornitura(file, cartellaDati, currentLine);
							} catch (Exception e ) {
							}				
							
						}else {
							lettoqualcosa = true;
							getValoriFromLine(tipoRecord,currentLine);
						}

						riga++;
						cont++;
						if(cont > 10000){
							cont = 1;
							con.commit();
							log.debug("Istante di esecuzione commit ogni 10000 righe metodo leggiFile: " + new Date(System.currentTimeMillis()) );
						}
					}
				}
			}else{
				
				log.debug("File PROPERTIES di Base NON trovati!!! ");
			}
			
			return lettoqualcosa;
			
		} finally {
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

	private int setDefaultParameters(PreparedStatement pstm, int lastParam, String idOrig, String hash) throws SQLException{
		
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
		
		return lastParam;
	}
	
	private void salvaRiga(String modello, String ideTelematico, String tipoRecord, String riga) {
		
		if(riga != null && !"".equals(riga.trim())){
			try {
	
				pstmtRiga.clearParameters();
				pstmtRiga.setString(5, ideTelematico);
				pstmtRiga.setString(6, modello);
				pstmtRiga.setString(7, tipoRecord);
				pstmtRiga.setString(8, riga);

				setDefaultParameters(pstmtRiga, 8, getHash(riga), getHash(riga));
				pstmtRiga.executeUpdate();
	
			} catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + riga );
					log.error("Errore: ", e);
				}
			}
		}
	}
	
	private void salvaAttivita(String ide_tel, String codFisc,
			String cfSostitutoImposta, String annoImposta) {
		
		if(codFisc != null && !"".equals(codFisc.trim())){
			try {
	
				String re = null;
				String rf = null;
				String rg = null;
				
				if(cfSostitutoImposta != null && cfSostitutoImposta.length()>12){
					re = cfSostitutoImposta.substring(0, 6);
					rf = cfSostitutoImposta.substring(6, 12);
					rg = cfSostitutoImposta.substring(12);
				}
	
				pstmtAtt.clearParameters();
				pstmtAtt.setString(5, ide_tel);
				pstmtAtt.setString(6, codFisc);
				pstmtAtt.setString(7, org.apache.commons.lang.StringUtils.trimToNull(re));
				pstmtAtt.setString(8, org.apache.commons.lang.StringUtils.trimToNull(rf));
				pstmtAtt.setString(9, org.apache.commons.lang.StringUtils.trimToNull(rg));
				pstmtAtt.setString(10, org.apache.commons.lang.StringUtils.trimToNull(cfSostitutoImposta));
				pstmtAtt.setString(11, org.apache.commons.lang.StringUtils.trimToNull(annoImposta));
				
				String idOrig = ide_tel + " " + codFisc;
				setDefaultParameters(pstmtAtt, 11, idOrig, getHash(idOrig));
				pstmtAtt.executeUpdate();
	
			} catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + ide_tel + " " + codFisc);
					log.error("Errore: ", e);
				}
			}
		}
	}
	
	private void salvaDatiAnagrafici(
			String ide_tel, String codFisc, String flgPersona, String cognome,
			String nome, String comuneNascita, String provinciaNascita,String dataNascita,
			String sesso, String statoCivile, String denominazione, String naturaG,
			String situazione, String stato, String onlus, String settoreOnlus,
			String belfioreRes, String comuneRes, String provinciaRes, String capRes, String indirizzoRes,
			String telefono, String cellulare, String email,
			String annoImposta) {
		
		if(codFisc != null && !"".equals(codFisc.trim())){
			try {
	
				SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat  sdf2 = new SimpleDateFormat("ddMMyyyy");
				pstmtAna.clearParameters();
				pstmtAna.setString(5, ide_tel);
				pstmtAna.setString(6, codFisc);
				pstmtAna.setString(7, org.apache.commons.lang.StringUtils.trimToNull(flgPersona));
				pstmtAna.setString(8, org.apache.commons.lang.StringUtils.trimToNull(cognome));
				pstmtAna.setString(9, org.apache.commons.lang.StringUtils.trimToNull(nome));
				pstmtAna.setString(10, org.apache.commons.lang.StringUtils.trimToNull(comuneNascita));
				pstmtAna.setString(11, org.apache.commons.lang.StringUtils.trimToNull(provinciaNascita));
				Date data = null;
				if(dataNascita!=null && !"".equals(dataNascita.trim())){
					try {
						data = sdf.parse(dataNascita);
					} catch (ParseException e) {
						try {
							data = sdf2.parse(dataNascita);
						} catch (ParseException e1) {
							log.error(e1);
						}
					}
				}
				pstmtAna.setDate(12, data!=null?new java.sql.Date(data.getTime()):null);
				pstmtAna.setString(13, sesso);
				pstmtAna.setBigDecimal(14, org.apache.commons.lang.StringUtils.trimToNull(statoCivile) != null ? new BigDecimal(statoCivile): null);
				pstmtAna.setString(15, denominazione);
				pstmtAna.setBigDecimal(16, org.apache.commons.lang.StringUtils.trimToNull(naturaG) != null ? new BigDecimal(naturaG): null);
				pstmtAna.setBigDecimal(17,org.apache.commons.lang.StringUtils.trimToNull(situazione) != null ? new BigDecimal(situazione): null);
				pstmtAna.setBigDecimal(18, org.apache.commons.lang.StringUtils.trimToNull(stato) != null ? new BigDecimal(stato): null);
				pstmtAna.setBigDecimal(19, org.apache.commons.lang.StringUtils.trimToNull(onlus) != null ? new BigDecimal(onlus): null);
				pstmtAna.setBigDecimal(20, org.apache.commons.lang.StringUtils.trimToNull(settoreOnlus) != null ? new BigDecimal(settoreOnlus): null);
				pstmtAna.setString(21, belfioreRes);
				pstmtAna.setString(22, comuneRes);
				pstmtAna.setString(23, provinciaRes);
				pstmtAna.setString(24, capRes);
				pstmtAna.setString(25, indirizzoRes);
				pstmtAna.setString(26, org.apache.commons.lang.StringUtils.trimToNull(telefono));
				pstmtAna.setString(27, org.apache.commons.lang.StringUtils.trimToNull(cellulare));
				pstmtAna.setString(28, org.apache.commons.lang.StringUtils.trimToNull(email));
				pstmtAna.setString(29, annoImposta);
				
				String idOrig = ide_tel + " " + codFisc;
				setDefaultParameters(pstmtAna, 29, idOrig, getHash(idOrig));
				pstmtAna.executeUpdate();
	
			} catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + ide_tel + " " + codFisc + " " + dataNascita + " " + denominazione);
					log.error("Errore: ", e);
				}
			}
		}
	}

	private void salvaDescrizione(
			String ide_tel, String codFisc, String tipoModello,
			String dicCorrettiva, String dichIntegrativa, String statoDich,
			String annoImposta) {
		
		if(codFisc != null && !"".equals(codFisc.trim())){
			try {
	
				pstmtDesc.clearParameters();
				pstmtDesc.setString(5, ide_tel);
				pstmtDesc.setString(6, codFisc);
				pstmtDesc.setString(7, tipoModello);
				pstmtDesc.setString(8, org.apache.commons.lang.StringUtils.trimToNull(dicCorrettiva));
				pstmtDesc.setString(9, org.apache.commons.lang.StringUtils.trimToNull(dichIntegrativa));
				pstmtDesc.setString(10, org.apache.commons.lang.StringUtils.trimToNull(statoDich));
				pstmtDesc.setString(11, annoImposta);
				
				String idOrig = ide_tel + " " + codFisc + " " + tipoModello;
				setDefaultParameters(pstmtDesc, 11, idOrig, getHash(idOrig));
				pstmtDesc.executeUpdate();
	
			} catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + ide_tel + " " + codFisc);
					log.error("Errore: ", e);
				}
			}
		}
	}

	private void salvaDomicilioFiscale(
			String ide_tel, String codFisc,
			String belfioreDom1, String comune1, String provincia1,
			String belfioreDom2, String comune2, String provincia2,
			String belfioreDom3, String comune3, String provincia3,
			String belfioreDomAttuale, String comuneAttuale, String provinciaAttuale,
			String indirizzoAttuale, String capAttuale, String annoImposta) {
		
		if(comuneAttuale != null && !"".equals(comuneAttuale.trim())){
			try {
	
				pstmtDom.clearParameters();
				pstmtDom.setString(5, ide_tel);
				pstmtDom.setString(6, codFisc);
				pstmtDom.setString(7, org.apache.commons.lang.StringUtils.trimToNull(belfioreDom1));
				pstmtDom.setString(8, org.apache.commons.lang.StringUtils.trimToNull(comune1));
				pstmtDom.setString(9, org.apache.commons.lang.StringUtils.trimToNull(provincia1));
				pstmtDom.setString(10, org.apache.commons.lang.StringUtils.trimToNull(belfioreDom2));
				pstmtDom.setString(11, org.apache.commons.lang.StringUtils.trimToNull(comune2));
				pstmtDom.setString(12, org.apache.commons.lang.StringUtils.trimToNull(provincia2));
				pstmtDom.setString(13, org.apache.commons.lang.StringUtils.trimToNull(belfioreDom3));
				pstmtDom.setString(14, org.apache.commons.lang.StringUtils.trimToNull(comune3));
				pstmtDom.setString(15, org.apache.commons.lang.StringUtils.trimToNull(provincia3));
				pstmtDom.setString(16, org.apache.commons.lang.StringUtils.trimToNull(belfioreDomAttuale));
				pstmtDom.setString(17, org.apache.commons.lang.StringUtils.trimToNull(comuneAttuale));
				pstmtDom.setString(18, org.apache.commons.lang.StringUtils.trimToNull(provinciaAttuale));
				pstmtDom.setString(19, org.apache.commons.lang.StringUtils.trimToNull(indirizzoAttuale));
				pstmtDom.setString(20, org.apache.commons.lang.StringUtils.trimToNull(capAttuale));
				pstmtDom.setString(21, org.apache.commons.lang.StringUtils.trimToNull(annoImposta));
				
				String idOrig = ide_tel + " " + codFisc;
				setDefaultParameters(pstmtDom, 21, idOrig, getHash(idOrig));
				pstmtDom.executeUpdate();
	
			} catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + ide_tel + " " + codFisc);
					log.error("Errore: ", e);
				}
			}
		}
	}

	private void salvaReddito(
			String ide_tel, String codFisc, String codiceQuadro, String valore,
			String anno) {
		
		if(codFisc != null && !"".equals(codFisc.trim())){
			try {
	
				pstmtRed.clearParameters();
				pstmtRed.setString(5, ide_tel);
				pstmtRed.setString(6, codFisc);
				pstmtRed.setString(7, org.apache.commons.lang.StringUtils.trimToNull(codiceQuadro));
				pstmtRed.setString(8, org.apache.commons.lang.StringUtils.trimToNull(valore));
				pstmtRed.setString(9, org.apache.commons.lang.StringUtils.trimToNull(anno));
				
				String idOrig = ide_tel + " " + codFisc + " " + codiceQuadro + " " + anno;
				setDefaultParameters(pstmtRed, 9, idOrig, getHash(idOrig));
				pstmtRed.executeUpdate();
	
			} catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + ide_tel + " " + codFisc);
					log.error("Errore: ", e);
				}
			}
		}
	}
	
	private void salvaTrascodifica(
			String annoImposta, String codiceQuadro, String descrizione,
			String tipoModello, String flgValuta) {
		
		if(codiceQuadro != null && !"".equals(codiceQuadro.trim())){
			try {
				pstmtTrasc.clearParameters();
				pstmtTrasc.setString(5, annoImposta);
				pstmtTrasc.setString(6, codiceQuadro);
				pstmtTrasc.setString(7, org.apache.commons.lang.StringUtils.trimToNull(descrizione));
				pstmtTrasc.setString(8, org.apache.commons.lang.StringUtils.trimToNull(tipoModello));
				pstmtTrasc.setString(9, org.apache.commons.lang.StringUtils.trimToNull(flgValuta));
				
				String idOrig = annoImposta + " " + codiceQuadro + " " + tipoModello;
				setDefaultParameters(pstmtTrasc, 9, idOrig, getHash(idOrig));
				pstmtTrasc.executeUpdate();
	
			} catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + annoImposta + " " + codiceQuadro);
					log.error("Errore: ", e);
				}
			}
		}
	}
	
	private void salvaFabbricato( String ideTel, String codFisc, String tipoModello, String num, Map<String, String> mapDati, String modulo){
		
		if(num!=null && modulo!=null && containsOnlyNumbers(mapDati.get("giorni"))) {
		try {
			Integer riga = new Integer(num);
			//Inserisco solo i righi RB1...RB8 (gli altri, es. in UNICO sono informazioni diverse)
			if(riga<=8){
				String comune = mapDati.get("comune");
				String idOrig = getHash(annoImposta+" "+tipoModello+" "+ideTel+" "+codFisc + " " + num + " " + modulo);
			
				pstmtRB.clearParameters();
				pstmtRB.setString(5, ideTel);
				pstmtRB.setString(6, codFisc);
				pstmtRB.setString(7, tipoModello);
				pstmtRB.setString(8, num);
				pstmtRB.setString(9, modulo);
				pstmtRB.setString(10, mapDati.get("rendita"));
				pstmtRB.setString(11, mapDati.get("utilizzo"));
				pstmtRB.setString(12, mapDati.get("giorni"));
				pstmtRB.setString(13, mapDati.get("possesso"));
				pstmtRB.setString(14, mapDati.get("canloc"));
				pstmtRB.setString(15, mapDati.get("casipart"));
				pstmtRB.setString(16, mapDati.get("cont"));
				pstmtRB.setString(17, mapDati.get("imponibile"));
				pstmtRB.setString(18, mapDati.get("aquila")); // Campo dichiarazioni anno imposta 2010
				pstmtRB.setString(19, mapDati.get("comune"));
				pstmtRB.setString(20, mapDati.get("ici"));
				pstmtRB.setString(21, annoImposta);
				
				/*Filtro comune rimosso:i record di tipo continuazione potrebbero non avere il comune valorizzato*/
				/*Caricare tutti i fabbricati*/
				
			//	if(!filtroComune || checkDomicilio(belfiore, comune) ){
				int lastParams = setDefaultParameters(pstmtRB, 21, idOrig, getHash(idOrig));

				pstmtRB.setString(++lastParams, mapDati.get("codCanone"));
				pstmtRB.setString(++lastParams, mapDati.get("cedolareSecca"));
				pstmtRB.setString(++lastParams, mapDati.get("impTasOrd"));
				pstmtRB.setString(++lastParams, mapDati.get("impCedSec21"));
				pstmtRB.setString(++lastParams, mapDati.get("impCedSec19"));
				pstmtRB.setString(++lastParams, mapDati.get("eseIMU"));
				pstmtRB.setString(++lastParams, mapDati.get("abitazionePrincipale"));
				pstmtRB.setString(++lastParams, mapDati.get("immobiliNonLocati"));
				pstmtRB.setString(++lastParams, mapDati.get("abitazionePrincipaleNonIMU"));
				
				pstmtRB.executeUpdate();
				
			//	}
			}
			
		}catch(NumberFormatException nfe){
			//log.warn("Errore: ", nfe);
		}catch (SQLException e) {
			log.error("Errore: ", e);
		}
		}
	}
	
	
	
	private void salvaTerreno( String ideTel, String codFisc, String tipoModello, String num, Map<String, String> mapDati, String modulo){
		
		if(num!=null && modulo!=null && containsOnlyNumbers(mapDati.get("giorni"))) {
			String idOrig = getHash(annoImposta+" "+tipoModello+" "+ideTel+" "+codFisc + " " + num + " " + modulo);
		try {
			Integer riga = new Integer(num);
			
			pstmtRA.clearParameters();
			pstmtRA.setString(5, ideTel);
			pstmtRA.setString(6, codFisc);
			pstmtRA.setString(7, tipoModello);
			pstmtRA.setString(8, num);
			pstmtRA.setString(9, modulo);
			pstmtRA.setString(10, mapDati.get("dominicale"));
			pstmtRA.setString(11, mapDati.get("titolo"));
			pstmtRA.setString(12, mapDati.get("agrario"));
			pstmtRA.setString(13, mapDati.get("giorni"));
			pstmtRA.setString(14, mapDati.get("possesso"));
			pstmtRA.setString(15, mapDati.get("canloc"));
			pstmtRA.setString(16, mapDati.get("casipart"));
			pstmtRA.setString(17, mapDati.get("cont"));
			pstmtRA.setString(18, mapDati.get("domimponibile"));
			pstmtRA.setString(19, mapDati.get("agrimponibile"));
			pstmtRA.setString(20, annoImposta);
			
			int lastParams = setDefaultParameters(pstmtRA, 20, idOrig, getHash(idOrig));
			
			pstmtRA.setString(++lastParams, mapDati.get("eseIMU"));
			pstmtRA.setString(++lastParams, mapDati.get("colDirIAP"));
			pstmtRA.setString(++lastParams, mapDati.get("domNonImponibile"));

			pstmtRA.executeUpdate();
			
		}catch(NumberFormatException nfe){
			//log.warn("Errore: ", nfe);
		}catch (SQLException e) {
			log.error("idOrig:"+idOrig+"-Errore: ", e);
		}
		}
	}
	
	private String getHash(String value){
		MessageDigest md = null;
		String hash = null;
		
		try{
			md = MessageDigest.getInstance("SHA");
		}
		catch (NoSuchAlgorithmException e){
			log.error("Errore: ", e);
		}
		
		if (value != null) {
			md.update(value.getBytes());
	
			byte[] b = md.digest();
			hash = new String(StringUtils.toHexString(b));	
		}
		
		return hash;
	}

	private void elaboraRiga(String currentLine, String tipoRecord){
		salvaRiga(modello, ideTelematico, tipoRecord, currentLine);
	}
	
	private void elaboraFrontespizio(String currentLine, String tipoRecord){
		
		String dom1 = getPropertySubstring(currentLine, tipoRecord, "dom1");
		String dom2 = getPropertySubstring(currentLine, tipoRecord, "dom2");
		String dom3 = getPropertySubstring(currentLine, tipoRecord, "dom3");
		String dom4 = getPropertySubstring(currentLine, tipoRecord, "dom4");
		String dom5 = getPropertySubstring(currentLine, tipoRecord, "dom5");
		String dom6 = getPropertySubstring(currentLine, tipoRecord, "dom6");
		
		/*15-11-2013 Rimoso filtro: caricare tutta la fornitura*/
	//	if(!filtroComune || checkDomicilio(belfiore, dom1, dom2, dom3, dom4, dom5, dom6)){
			comuneOk=true;
			
			elaboraDatiAnagrafici(currentLine, tipoRecord, flgPersonaDichiarante);
			elaboraDatiAnagrafici(currentLine, tipoRecord, flgPersonaConiuge);
			elaboraDatiAnagrafici(currentLine, tipoRecord, flgPersonaRappresentante);
			elaboraDatiAnagrafici(currentLine, tipoRecord, flgPersonaSostituto);
			
			String dicCorrettiva = getPropertySubstring(currentLine, tipoRecord, "dichcorrettiva");
			String dicIntegrativa = getPropertySubstring(currentLine, tipoRecord, "dichintegrativa");
			String statoDic = getPropertySubstring(currentLine, tipoRecord, "statodich");
			salvaDescrizione(ideTelematico,
					codFiscDich, modello, dicCorrettiva,
					dicIntegrativa, statoDic, annoImposta);
			
			elaboraDatiDomicilio(currentLine, tipoRecord, flgPersonaDichiarante);
			elaboraDatiDomicilio(currentLine, tipoRecord, flgPersonaConiuge);
		
	//	}else comuneOk=false;
	}
	
	private void elaboraDatiAnagrafici(String currentLine, String tipoRecord, String flgPersona){
		
		String codFisc = getPropertySubstring(currentLine, tipoRecord, flgPersona +".codfisc");
		if("D".equals(flgPersona))
			codFiscDich = codFisc;
		if("S".equals(flgPersona))
			codFiscSost = codFisc;
		
		String cognome = getPropertySubstring(currentLine, tipoRecord, flgPersona +".cognome");
		String denominazione = getPropertySubstring(currentLine, tipoRecord, flgPersona +".denominazione");

		if(codFisc!=null 
				&& ("D".equals(flgPersona) || !codFisc.equals(codFiscDich))
				&& (cognome != null || denominazione != null)){
			String nome = getPropertySubstring(currentLine, tipoRecord, flgPersona +".nome");
			String sesso = getPropertySubstring(currentLine, tipoRecord, flgPersona +".sesso");
			String dataNascita = getPropertySubstring(currentLine, tipoRecord, flgPersona +".datan");
			String comuneNascita = getPropertySubstring(currentLine, tipoRecord, flgPersona +".comunen");
			String provinciaNascita = getPropertySubstring(currentLine, tipoRecord, flgPersona +".provincian");
			String statoCivile = getPropertySubstring(currentLine, tipoRecord, flgPersona +".statocivile");
			String naturaG = getPropertySubstring(currentLine, tipoRecord, flgPersona +".naturag");
			String situazione = getPropertySubstring(currentLine, tipoRecord, flgPersona +".situazione");
			String statoDic = getPropertySubstring(currentLine, tipoRecord, flgPersona +".statodic");
			String onlus = getPropertySubstring(currentLine, tipoRecord, flgPersona +".onlus");
			String settoreOnlus = getPropertySubstring(currentLine, tipoRecord, flgPersona +".settoreonlus");
			String comuneResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".comuneresatt");
			String provinciaResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".provinciaresatt");
			String capResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".capresatt");
			String indirizzoResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".indirizzoresatt");
			String belfioreResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".belfioreresatt");
			String telefono = getPropertySubstring(currentLine, tipoRecord, flgPersona +".tel");
			String cellulare = getPropertySubstring(currentLine, tipoRecord, flgPersona +".cell");
			String email = getPropertySubstring(currentLine, tipoRecord, flgPersona +".email");
			salvaDatiAnagrafici(ideTelematico, codFisc, flgPersona,
					cognome, nome, comuneNascita, provinciaNascita, dataNascita,
					sesso, statoCivile, denominazione, naturaG, situazione, statoDic,
					onlus, settoreOnlus, belfioreResAttuale, comuneResAttuale, provinciaResAttuale, capResAttuale, indirizzoResAttuale,
					telefono, cellulare, email, annoImposta);
		}
	}
	
	private void elaboraDatiDomicilio(String currentLine, String tipoRecord, String flgPersona){
		
		String comuneResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".comuneresattuale");
		
		if(comuneResAttuale != null){
			String provinciaResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".provinciaresattuale");
			String capResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".capresattuale");
			String indirizzoResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".indirizzoresattuale");
			String belfioreResAttuale = getPropertySubstring(currentLine, tipoRecord, flgPersona +".belfioreresattuale");
			String comuneRes1 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".comuneres1");
			String provinciaRes1 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".provinciares1");
			String belfioreRes1 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".belfioreres1");
			String comuneRes2 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".comuneres2");
			String provinciaRes2 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".provinciares2");
			String belfioreRes2 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".belfioreres2");
			String comuneRes3 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".comuneres3");
			String provinciaRes3 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".provinciares3");
			String belfioreRes3 = getPropertySubstring(currentLine, tipoRecord, flgPersona +".belfioreres3");
			
			salvaDomicilioFiscale(ideTelematico, codFiscDich, 
					belfioreRes1, comuneRes1, provinciaRes1,
					belfioreRes2, comuneRes2, provinciaRes2,
					belfioreRes3, comuneRes3, provinciaRes3,
					belfioreResAttuale, comuneResAttuale, provinciaResAttuale,indirizzoResAttuale, capResAttuale, annoImposta);
		}
	}
	
	private void elaboraQuadroRB(String currentLine, String tipoRecord){
		
		String fabbCorrenteNum = null;
		String fabbModNum = null;
		
		String fabbUltimoNum = null;
		String fabbUltimoMod = null;
		
		String cfStart = getRBProperty(tipoRecord + ".codfisc.start");
		if(cfStart != null){
			int cfEnd = Integer.valueOf(getRBProperty(tipoRecord + ".codfisc.end"));
			codFisc = currentLine.substring(Integer.valueOf(cfStart), cfEnd);
		}
	
		int rigaFStart = Integer.valueOf(getRBProperty("rigafabb.start"));
		String rigaFabbricati = currentLine.substring(rigaFStart);
		String rigaFStartWith1 = getRBProperty("rigafabb.startwith1");
		String rigaFStartWith2 = getRBProperty("rigafabb.startwith2");
		String rigaFStartWith3 = getRBProperty("rigafabb.startwith3");
		if(rigaFabbricati.startsWith(rigaFStartWith1) 
				|| rigaFabbricati.startsWith(rigaFStartWith2)
				|| rigaFabbricati.startsWith(rigaFStartWith3)){
			
			String split = getRBProperty("rigafabb.split");
			String[] rb = rigaFabbricati.split(split);
			for(int j = 1; j<rb.length; j++){
				try{
					fabbCorrenteNum = rb[j].length()>2?rb[j].substring(0,3):rb[j];
					String datoNum = rb[j].length()>5?rb[j].substring(3,6):rb[j];
					fabbModNum = rb[j].length()>10?rb[j].substring(6,11):rb[j];
					if(fabbCorrenteNum.startsWith("0") && datoNum.startsWith("0")){
						Integer datoCorrenteNum = new Integer(datoNum);
												
						if(j>1){
							 fabbUltimoNum = rb[j-1].substring(0,3);
							 fabbUltimoMod = rb[j-1].substring(6,11);
							if(!fabbCorrenteNum.equals(fabbUltimoNum) || !fabbModNum.equals(fabbUltimoMod)){
								salvaFabbricato(ideTelematico, codFisc, modello, fabbUltimoNum, mapDatiRB, fabbUltimoMod);
								mapDatiRB = new HashMap<String, String>();
							}
						}
						
						//mappatura dati
						String data = rb[j].length()>30? rb[j].substring(12, 30): rb[j].substring(12);
						while (data.startsWith("+") || data.startsWith("0"))
							data = data.substring(1);
						
						String col = getRBProperty("col." + datoCorrenteNum);
						if(col != null){
							if("possesso".equals(col) && containsOnlyNumbers(data))
								data = String.valueOf((new Double(data).doubleValue()/100000));
							mapDatiRB.put(col, data.trim());
						}
						
					}
					
					}catch(Exception e){
						log.debug("elaboraQuadroRB - Riga Fabbricato:"+rigaFabbricati);
						log.debug("elaboraQuadroRB - Elaborazione Split (cod."+split+") riga Fabbricato in errore (non bloccante):" +rb[j]);
						log.debug("elaboraQuadroRB - "+e.getMessage());
					}
				}
					
					salvaFabbricato(ideTelematico, codFisc, modello, fabbCorrenteNum, mapDatiRB, fabbModNum);
					mapDatiRB = new HashMap<String, String>();
					
		}
	}
	
	private void elaboraQuadroRA(String currentLine, String tipoRecord){
		
		
		String terrCorrenteNum = null;
		String terrModNum=null;
		
		String terrUltimoNum = null;
		String terrUltimoMod = null;
		
		String cfStart = getRAProperty(tipoRecord + ".codfisc.start");
		if(cfStart != null){
			int cfEnd = Integer.valueOf(getRAProperty(tipoRecord + ".codfisc.end"));
			codFisc = currentLine.substring(Integer.valueOf(cfStart), cfEnd);
		}
		
		int rigaFStart = Integer.valueOf(getRAProperty("rigaterr.start"));
		String rigaTerreni = currentLine.substring(rigaFStart);
		String rigaFStartWith1 = getRAProperty("rigaterr.startwith1");
		String rigaFStartWith2 = getRAProperty("rigaterr.startwith2");
		String rigaFStartWith3 = getRAProperty("rigaterr.startwith3");
		if(rigaTerreni.startsWith(rigaFStartWith1) 
				|| rigaTerreni.startsWith(rigaFStartWith2)
				|| rigaTerreni.startsWith(rigaFStartWith3)){
			
			String split = getRAProperty("rigaterr.split");
			String[] rb = rigaTerreni.split(split);
			for(int j = 1; j<rb.length; j++){
				try{
					terrCorrenteNum = rb[j].length()>2?rb[j].substring(0,3):rb[j];
					String datoNum = rb[j].length()>5?rb[j].substring(3,6):rb[j];
					terrModNum =rb[j].length()>10?rb[j].substring(6,11):rb[j];
					if(terrCorrenteNum.startsWith("0") && datoNum.startsWith("0")){
						Integer datoCorrenteNum = new Integer(datoNum);
						
						if(j>1){
							terrUltimoNum = rb[j-1].substring(0,3);
							terrUltimoMod = rb[j-1].substring(6,11);
							if(!terrCorrenteNum.equals(terrUltimoNum) || !terrModNum.equals(terrUltimoMod)){
								salvaTerreno(ideTelematico, codFisc, modello, terrUltimoNum, mapDatiRA,terrUltimoMod);
								mapDatiRA = new HashMap<String, String>();
							}
						}
						
						//mappatura dati
						String data = rb[j].length()>30? rb[j].substring(12, 30): rb[j].substring(12);
						while (data.startsWith("+") || data.startsWith("0"))
							data = data.substring(1);
						
						String col = getRAProperty("col." + datoCorrenteNum);
						if(col != null){
							if("possesso".equals(col) && containsOnlyNumbers(data))
								data = String.valueOf((new Double(data).doubleValue()/100000));
							mapDatiRA.put(col, data.trim());
						}
						
					}
					
					}catch(Exception e){
						log.debug("elaboraQuadroRA - Riga Terreno:"+rigaTerreni);
						log.debug("elaboraQuadroRA - Elaborazione Split (cod."+split+") riga Terreno in errore (non bloccante):" +rb[j]);
						log.debug("elaboraQuadroRA - "+e.getMessage());
					}
					
				}
					salvaTerreno(ideTelematico, codFisc, modello, terrCorrenteNum, mapDatiRA,terrModNum);
					mapDatiRA = new HashMap<String, String>();
		}
	}
	
	private boolean loadRedProperties(String filename) {
		String fileName = filename + ".properties";
		RedProperties = new Properties();
		try {
			RedProperties.load(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private String getPropertySubstring(String currentLine, String tipoRecord, String propName) {
		try {
			String start = RedProperties.getProperty(tipoRecord + "." + propName + ".start");
			if(start != null && !"".equals(start)){
				String end = RedProperties.getProperty(tipoRecord + "." + propName + ".end");
				String ret = currentLine.substring(Integer.valueOf(start), Integer.valueOf(end));
				return org.apache.commons.lang.StringUtils.trimToNull(ret);
			}
		} catch (Exception e) {}
		return null;
	}
	
	private boolean checkBelfiore(String belfiore){
		
		if(belfiore.length() == 4){
			if(!(belfiore.substring(0,1).matches("[a-zA-Z]")))
				return false;
			for (int i = 1; i < belfiore.length(); i++) {
				if (!Character.isDigit(belfiore.charAt(i))){
					return false;
				}
			}
			return true;
		}else return false;
	}
	
	private boolean checkDomicilio(String belfioreDB, String... dom){
		
		boolean ret = false;
		for (String n : dom) {
			if(belfioreDB.equals(n))
				ret = true;
				break;
		}
		return ret;
	}
	
	private boolean containsOnlyNumbers(String str) {
		if(str !=null){
		    for (int i = 0; i < str.length(); i++) {
		      if (!Character.isDigit(str.charAt(i)))
		        return false;
		    }
		}
		return true;
	}
	
	private boolean loadRBProperties(String filename) {
		String fileName = filename + ".properties";
		RBProperties = new Properties();
		try {
			RBProperties.load(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private boolean loadRAProperties(String filename) {
		String fileName = filename + ".properties";
		RAProperties = new Properties();
		try {
			RAProperties.load(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private String getRBProperty(String propName) {
		try {
			String p = RBProperties.getProperty(propName);
		return p;
		} catch (Exception e) {
			return null;
		}
	}
	
	private String getRAProperty(String propName) {
		try {
			String p = RAProperties.getProperty(propName);
		return p;
		} catch (Exception e) {
			return null;
		}
	}
	
	protected void postElaborazioneAction(String file,List<String> fileDaElaborare, String cartellaFiles) {
		
		log.debug("PostElaborazioneAction....");
		
		//Riunisco in un solo record i fabbricati che provenendo da righe diverse (moduli multipli) sono stati riportati su record diversi con id duplicato 
		this.mergeFabbDuplicati();
		this.mergeTerrDuplicati();
		
	}
	
	private boolean elaboraDato(String nomeDato, String campoRs, HashMap<String,String> mapDati){
		boolean merge = true;
		if(mapDati.get(nomeDato)==null && campoRs!=null)
			mapDati.put(nomeDato, campoRs);
		else if(mapDati.get(nomeDato)!=null && campoRs!=null && !mapDati.get(nomeDato).equals(campoRs))
			merge = false;
		
		return merge;
	}
	
	private void mergeTerrDuplicati(){
		Statement st1=null;
		Statement st2 = null;
		ResultSet rs1 = null;
		List<String> lstId = new ArrayList<String>();
		HashMap<String,String> mapDati = new HashMap<String,String>();
		
		log.debug("Merge Terreni duplicati in corso...");
		try{
			st1 = con.createStatement();
			st2 = con.createStatement();
			pstmtRA = con.prepareStatement(this.getSqlRA());
			
			rs1 = st1.executeQuery("select distinct id_ext from " + env.tableRA +" group by id_ext having count(id_ext)>1");
			while(rs1.next())
				lstId.add(rs1.getString("id_ext"));
			
			log.debug("Trovati "+lstId.size()+" terreni duplicati.");
			
			for(String id : lstId){
				rs1=st1.executeQuery("select * from " + env.tableRA +" where id_ext = '"+id+"'");
				String ideTel=null;
				String tipoModello = null;
				String codFisc = null;
				String num = null;
				String modulo = null;
				
				boolean merge = true;
				while(rs1.next()){
					
					ideTel = rs1.getString("ide_telematico");
					codFisc = rs1.getString("codice_fiscale");
					num= rs1.getString("num_terr");
					modulo = rs1.getString("modulo");
					tipoModello = rs1.getString("tipo_modello");
					
					merge = merge && this.elaboraDato("dominicale", rs1.getString("red_dominicale"), mapDati);
					merge = merge && this.elaboraDato("titolo", rs1.getString("titolo"), mapDati);
					merge = merge && this.elaboraDato("agrario", rs1.getString("red_agrario"), mapDati);
					merge = merge && this.elaboraDato("giorni", rs1.getString("giorni"), mapDati);
					merge = merge && this.elaboraDato("possesso", rs1.getString("possesso"), mapDati);
					merge = merge && this.elaboraDato("canloc", rs1.getString("canone_aff"), mapDati);
					merge = merge && this.elaboraDato("casipart", rs1.getString("casi_part"), mapDati);
					merge = merge && this.elaboraDato("cont", rs1.getString("continuazione"), mapDati);
					merge = merge && this.elaboraDato("domimponibile", rs1.getString("dom_imponibile"), mapDati);
					merge = merge && this.elaboraDato("agrimponibile", rs1.getString("agr_imponibile"), mapDati);
				
				}
				
				if(merge){
					//Rimuovo le righe
					String qry0 = "delete from " + env.tableRA +" where id_ext = '"+id+"'";
					st2.executeUpdate(qry0);
					con.commit();
					log.debug(qry0);
					//Inserisco la nuova riga fusa	
					this.salvaTerreno(ideTel, codFisc, tipoModello, num, mapDati, modulo);
					con.commit();
					log.debug("Istante di esecuzione commit del metodo salvaTerreno: " + new Date(System.currentTimeMillis()));
				}
				mapDati = new HashMap<String,String>();
			}
			
			log.debug("Merge Terreni duplicati concluso!");
			
		}catch(Exception e){
			log.error("Errore: ", e);
		}finally{
			try {
				DbUtils.close(rs1);
				DbUtils.close(st1);
				DbUtils.close(st2);
				DbUtils.close(pstmtRA);
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}
	
	private void mergeFabbDuplicati(){
		Statement st1=null;
		Statement st2 = null;
		ResultSet rs1 = null;
		List<String> lstId = new ArrayList<String>();
		HashMap<String,String> mapDati = new HashMap<String,String>();
		
		log.debug("Merge Fabbricati duplicati in corso...");
		
		try{
			st1 = con.createStatement();
			st2 = con.createStatement();
			pstmtRB = con.prepareStatement(this.getSqlRB());
			
			rs1 = st1.executeQuery("select distinct id_ext from " + env.tableRB +" group by id_ext having count(id_ext)>1");
			while(rs1.next())
				lstId.add(rs1.getString("id_ext"));
			
			log.debug("Trovati "+lstId.size()+" fabbricati duplicati.");
			
			for(String id : lstId){
				rs1=st1.executeQuery("select * from " + env.tableRB +" where id_ext = '"+id+"'");
				String ideTel=null;
				String tipoModello = null;
				String codFisc = null;
				String num = null;
				String modulo = null;
				
				boolean merge = true;
				while(rs1.next()){
					
					ideTel = rs1.getString("ide_telematico");
					codFisc = rs1.getString("codice_fiscale");
					num= rs1.getString("num_fabb");
					modulo = rs1.getString("modulo");
					tipoModello = rs1.getString("tipo_modello");
					
					merge = merge && this.elaboraDato("rendita", rs1.getString("rendita"), mapDati);
					merge = merge && this.elaboraDato("utilizzo", rs1.getString("utilizzo"), mapDati);
					merge = merge && this.elaboraDato("giorni", rs1.getString("giorni"), mapDati);
					merge = merge && this.elaboraDato("possesso", rs1.getString("possesso"), mapDati);
					merge = merge && this.elaboraDato("canloc", rs1.getString("canone_loc"), mapDati);
					merge = merge && this.elaboraDato("casipart", rs1.getString("casi_part"), mapDati);
					merge = merge && this.elaboraDato("cont", rs1.getString("continuazione"), mapDati);
					merge = merge && this.elaboraDato("imponibile", rs1.getString("imponibile"), mapDati);
					merge = merge && this.elaboraDato("comune", rs1.getString("comune"), mapDati);
					merge = merge && this.elaboraDato("ici", rs1.getString("ici"), mapDati);
					merge = merge && this.elaboraDato("aquila", rs1.getString("cedolare_aq"), mapDati);
				
				}
				
				if(merge){
					//Rimuovo le righe
					String qry1 = "delete from " + env.tableRB +" where id_ext = '"+id+"'";
					st2.executeUpdate(qry1);
					con.commit();
					log.debug(qry1);
					//Inserisco la nuova riga fusa	
					this.salvaFabbricato(ideTel, codFisc, tipoModello, num, mapDati, modulo);
					con.commit();
					log.debug("Istante di esecuzione commit del metodo salvaFabbricato: " + new Date(System.currentTimeMillis()));
				}
				mapDati = new HashMap<String,String>();
			}
			
			log.debug("Merge Fabbricati duplicati concluso!");
			
		}catch(Exception e){
			log.error("Errore: ", e);
		}finally{
			try {
				DbUtils.close(rs1);
				DbUtils.close(st1);
				DbUtils.close(st2);
				DbUtils.close(pstmtRB);
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}
	
	
	
}

