package it.webred.rulengine.brick.loadDwh.load.versamenti.poste.tarsu;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportFactory;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions.TroppiErroriImportFileException;
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

public class VersPosteFiles<T extends VersPosteEnv> extends ImportFiles<T> {

	private SimpleDateFormat sdfId = new SimpleDateFormat("yyyyMMdd hhmmss");
	
	//QUERY
	private PreparedStatement pstmtRiga = null;
	private PreparedStatement pstmtRiepilogo = null;
	private PreparedStatement pstmtVersamento = null;
	
	//DATI GENERALI
	private Properties Properties = null;
	
	public VersPosteFiles(T env) {
		super(env);
		boolean existRedditiProp = loadProperties("BP");
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
	public List<String> getValoriFromLine(String tipoRecord, String currentLine) throws RulEngineException {
		List<String> campi = new ArrayList<String>();
		
		// recupero dati frontespizio
		tipoRecord = currentLine.substring(21, 22).equals("999") ? "R" : "V";
		//elaboraRiga(currentLine, tipoRecord);
		if (!"R".equals(tipoRecord)) 
			elaboraVersamento(currentLine, tipoRecord);

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
	public void tracciaFornitura(String file, String cartellaDati, String line) throws RulEngineException {
		env.saveFornitura(new Date(), file);
	}

	protected void procesingFile(String file, String cartellaDati) throws RulEngineException {
		boolean gestisciTmp = false;
		boolean disabilitaStorico = false;
		if (env.getEnteSorgente().isInReplace())
			gestisciTmp = true;

		if (env.getEnteSorgente().isDisabilitaStorico())
			disabilitaStorico = true;
		
		try {

			String sqlRiga = "insert into "
					+ env.tableRiga
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtRiga = con.prepareStatement(sqlRiga);
			
			String sqlRiepilogo = "insert into "
					+ env.tableRiepilogo
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtRiepilogo = con.prepareStatement(sqlRiepilogo);

			String sqlVer = "insert into "
					+ env.tableVersamento
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtVersamento = con.prepareStatement(sqlVer);

			
			
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
			if (pstmtRiga != null)
				pstmtRiga.close();
			if (pstmtVersamento != null)
				pstmtVersamento.close();
			if (pstmtRiepilogo != null)
				pstmtRiepilogo.close();
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
			fileIn = new BufferedReader(new InputStreamReader(new FileInputStream(cartellaDati+file), "UTF8"));
			
			String currentLine=null;
			int riga = 1;
			int cont = 1;
			String insertSql = null;
			

			String[] arr = file.split("\\.");
			boolean existRedditiProp = loadProperties("BP");
			
			if(existRedditiProp) {
				
				while ((currentLine = fileIn.readLine()) != null)
				{
					
					if(!"".equals(currentLine)){
						// traccia file forniture
						if (riga==1) {
							try {
								tracciaFornitura(file, cartellaDati, currentLine);
							} catch (Exception e ) {
							}				
							
						}
						
						lettoqualcosa = true;
						try{
							getValoriFromLine(tipoRecord,currentLine);
						}catch (Exception e) {
							log.error("ERRORE currentline="+currentLine, e);
							errori.add(currentLine);
						}  
						
						if (errori.size()>100) {
							try {
								con.rollback();
							} catch (SQLException e) {
							}
							String errmsg = "USCITA FORZATA NELLA LETTURA DEL FILE " + file+" : ci sono troppi ERRORI (>100)";
							errmsg += " - EFFETTUATO ROLLBACK DELLA CONNESSIONE (TABELLA " + tempTable + ")";
							errori.add(errmsg);
							log.error(errmsg);
							throw new RulEngineException(errmsg);
						}
						
						riga++;
						/*cont++;
						if(cont > 10000){
							cont = 1;
							con.commit();
						}*/
					}
				}
				
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
				throw new TroppiErroriImportFileException("Errore di inserimento ! Prodotto file " + file+".err");
			
			}
		}
		
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
	
	private void salvaRiga(String tipoRecord, String riga) {
		
		if(riga != null && !"".equals(riga.trim())){
			try {
	
				pstmtRiga.clearParameters();
				pstmtRiga.setString(5, tipoRecord);
				pstmtRiga.setString(6, riga);

				setDefaultParameters(pstmtRiga, 6, getHash(riga), getHash(riga));
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
		salvaRiga(tipoRecord, currentLine);
	}
	
	private void elaboraVersamento(String currentLine, String tipoRecord) {
		SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
		String codCliente = getPropertySubstring(currentLine, "codCliente");
		
		try {
				String progCaricamento = getPropertySubstring(currentLine, "progCaricamento");
				String progSelezione = getPropertySubstring(currentLine, "progSelezione");
				String ccBeneficiario = getPropertySubstring(currentLine, "ccBeneficiario");
				
				String dataAccettazione = "20"+getPropertySubstring(currentLine, "dataAccettazione");
				Date dtAccettazione = SDF.parse(dataAccettazione);
						
				String tipoDocumento = getPropertySubstring(currentLine, "tipoDocumento");
				String importo = getPropertySubstring(currentLine, "importo");
				
				String ufficio = getPropertySubstring(currentLine, "ufficio");
				String divisa = getPropertySubstring(currentLine, "divisa");
				String dataAccredito = "20"+ getPropertySubstring(currentLine, "dataAccredito");
				Date dtAccredito = SDF.parse(dataAccredito);
				
				String cin = org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine, "cin"));
				String tipoAccettazione = org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine, "tipoAccettazione"));
				String sostitutivo = org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine, "sostitutivo"));
			
				pstmtVersamento.clearParameters();
				pstmtVersamento.setString(5, cleanLeftPad(progCaricamento, '0'));
				pstmtVersamento.setString(6, cleanLeftPad(progSelezione, '0'));
				pstmtVersamento.setString(7, ccBeneficiario);
				pstmtVersamento.setDate(8, new java.sql.Date(dtAccettazione.getTime()));
				pstmtVersamento.setString(9, tipoDocumento);
				pstmtVersamento.setString(10, importo);
				pstmtVersamento.setString(11, ufficio);
				pstmtVersamento.setString(12, divisa);
				pstmtVersamento.setDate(13, new java.sql.Date(dtAccredito.getTime()));
				pstmtVersamento.setString(14, codCliente);
				pstmtVersamento.setString(15, cin);
				pstmtVersamento.setString(16, tipoAccettazione);
				pstmtVersamento.setString(17, sostitutivo);
				 
				String idOrig = codCliente;
				setDefaultParameters(pstmtVersamento, 17, idOrig, getHash(idOrig));
				pstmtVersamento.executeUpdate();
	
			}catch(ParseException pe){
				log.error("___ERRORE di conversione data: "+codCliente,pe);
			}catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}else {
					log.error("________ERRORE IN ELEBORAZIONE: " + codCliente);
					log.error("Errore: ", e);
					
				}
			}
	}
	
	
	
	private String getPropertySubstring(String currentLine, String propName) {
		try {
			String start = Properties.getProperty( propName + ".start");
			if(start != null && !"".equals(start)){
				String end = Properties.getProperty(propName + ".end");
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
	
	private boolean loadProperties(String filename) {
		String fileName = filename + ".properties";
		Properties = new Properties();
		try {
			Properties.load(getClass().getResourceAsStream(fileName));
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	private String getProperties(String propName) {
		try {
			String p = Properties.getProperty(propName);
		return p;
		} catch (Exception e) {
			return null;
		}
	}
	
	private String cleanLeftPad(String s, char pad) {
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == pad)
				s = s.substring(1);
				
		}
		return s;
	}
	
	protected void postElaborazioneAction(String file,List<String> fileDaElaborare, String cartellaFiles) {
		
		log.debug("PostElaborazioneAction....");
		
	}
	
	
}

