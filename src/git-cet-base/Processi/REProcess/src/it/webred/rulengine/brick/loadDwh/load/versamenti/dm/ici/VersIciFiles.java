package it.webred.rulengine.brick.loadDwh.load.versamenti.dm.ici;

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

public class VersIciFiles<T extends VersIciEnv> extends ImportFiles<T> {

	private SimpleDateFormat sdfId = new SimpleDateFormat("yyyyMMdd hhmmss");

	private String annoRiferimento = null;
	private String dataScadenza = null;
	private String progInvio = null;
	
	//QUERY
	private PreparedStatement pstmtRiga = null;
	private PreparedStatement pstmtVersamento = null;
	private PreparedStatement pstmtAnagrafica = null;
	private PreparedStatement pstmtViolazione = null;
	
	//DATI GENERALI
	private Properties Properties = null;
	
	//FLAG
	private String belfiore;
	
	public VersIciFiles(T env) {
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
		if(currentLine.startsWith("ICI0") || currentLine.startsWith("ICI9"))
			tipoRecord = currentLine.substring(0, 4);
		else
			tipoRecord = currentLine.substring(25, 26);
		
		elaboraRiga(currentLine, tipoRecord);
		if("3".equals(tipoRecord))
			elaboraContabile(currentLine, tipoRecord);
		else if("4".equals(tipoRecord))
			elaboraAnagraficaSoggetto(currentLine,tipoRecord);
		else if("5".equals(tipoRecord))
			elaboraAnagraficaSocieta(currentLine,tipoRecord);
		else if("6".equals(tipoRecord))
			elaboraViolazione(currentLine,tipoRecord);


		return campi;
	}

	private void elaboraViolazione(String currentLine, String tipoRecord) {
		
				SimpleDateFormat SDF1 = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat SDF2 = new SimpleDateFormat("ddMMyyyy");
		
				String numQuietanza = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "numQuietanza"),'0');
				String prog = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "prog"),'0');
				
				String idOrig = annoRiferimento+"|"+dataScadenza+"|"+progInvio+"|"+numQuietanza +"|"+prog;
				try {
						String codConcessione = cleanLeftPad(getPropertySubstring(currentLine, tipoRecord, "codConcessione"),'0');
						String codEnte = getPropertySubstring(currentLine,tipoRecord, "codEnte");
						
						String dataVersamento = getPropertySubstring(currentLine,tipoRecord, "dataVersamento");
						Date dtVersamento = SDF1.parse(dataVersamento);
						
						String cf =  org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine,tipoRecord, "cf"));
						String nRifQuietanza = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "nRifQuietanza"),'0');
						Double importoVersato = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "importoVersato"))/100;
						Double imposta = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "imposta"))/100;
						Double soprattassa = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "soprattassa"))/100;
						Double penaPecuniaria = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "penaPecuniaria"))/100;
						Double interessi = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "interessi"))/100;
						
						String flagQuadratura = getPropertySubstring(currentLine,tipoRecord, "flagQuadratura");
						String flagReperibilita = getPropertySubstring(currentLine,tipoRecord, "flagReperibilita");
								
						String tipoVersamento = getPropertySubstring(currentLine,tipoRecord, "tipoVersamento");
						
						String dataRegistrazione = getPropertySubstring(currentLine,tipoRecord, "dataRegistrazione");
						Date dtRegistrazione = null;
						try{
							if(!"00000000".equals(dataRegistrazione))
								dtRegistrazione = SDF1.parse(dataRegistrazione);
						}catch(Exception e){}
						
						String flagCompetenza = getPropertySubstring(currentLine,tipoRecord, "flagCompetenza");
						String comune = getPropertySubstring(currentLine,tipoRecord, "comune");
						String cap = getPropertySubstring(currentLine,tipoRecord, "cap");
						
						String flagIdentificazione = getPropertySubstring(currentLine,tipoRecord, "flagIdentificazione");
						String tipoImposta = getPropertySubstring(currentLine,tipoRecord, "tipoImposta");
						//String annoImposta = getPropertySubstring(currentLine,tipoRecord, "annoImposta");
						String numProvvLiquidazione = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "numProvvLiquidazione"),'0');
						String dataProvvLiquidazione = getPropertySubstring(currentLine,tipoRecord, "dataProvvLiquidazione");
						Date dtProvvLiquidazione = null;
						try{
							if(!"00000000".equals(dataProvvLiquidazione))
								dtProvvLiquidazione = SDF2.parse(dataProvvLiquidazione);
						}catch(Exception e){}
				
					
						pstmtViolazione.clearParameters();
						int index = 4;
						pstmtViolazione.setString(++index, codConcessione);
						pstmtViolazione.setString(++index, codEnte);
						pstmtViolazione.setString(++index, numQuietanza);
						pstmtViolazione.setInt(++index, Integer.parseInt(prog));
						pstmtViolazione.setDate(++index, new java.sql.Date(dtVersamento.getTime()));
						pstmtViolazione.setString(++index, cf);
						pstmtViolazione.setString(++index, nRifQuietanza);
						pstmtViolazione.setDouble(++index, importoVersato);
						pstmtViolazione.setDouble(++index, imposta);
						pstmtViolazione.setDouble(++index, soprattassa);
						pstmtViolazione.setDouble(++index, penaPecuniaria);
						pstmtViolazione.setDouble(++index, interessi);
						pstmtViolazione.setString(++index, flagQuadratura);
						pstmtViolazione.setString(++index, flagReperibilita);
						pstmtViolazione.setString(++index, tipoVersamento);
						pstmtViolazione.setDate(++index, dtRegistrazione!=null ? new java.sql.Date(dtRegistrazione.getTime()):null);
						pstmtViolazione.setString(++index, flagCompetenza);
						pstmtViolazione.setString(++index, comune);
						pstmtViolazione.setString(++index, cap);
						pstmtViolazione.setString(++index, flagIdentificazione);
						pstmtViolazione.setString(++index, tipoImposta);
						pstmtViolazione.setString(++index, numProvvLiquidazione);
						pstmtViolazione.setDate(++index, dtProvvLiquidazione!=null ? new java.sql.Date(dtProvvLiquidazione.getTime()):null);
						
						setDefaultParameters(pstmtViolazione, index, idOrig, getHash(idOrig));
						pstmtViolazione.executeUpdate();
			
					}catch(ParseException pe){
						log.error("___ERRORE di conversione data: "+idOrig,pe);
					}catch (SQLException e) {
						if(e.getMessage().startsWith("ORA-00001")){
						}
						else {
							log.error("________ERRORE IN ELEBORAZIONE: " + idOrig);
							log.error("Errore: ", e);
						}
					}
	}

	private void salvaAnagrafica(String idOrig, String idVers,String codConcessione, String codEnte, 
			String numQuietanza, String prog,
			String tipoSoggetto, String cognome, String nome, String denom, String comune){
		
		
		String idExtV =  idVers + "   " + ctx.getIdFonte();
		
		try {
			
			pstmtAnagrafica.clearParameters();
			int index = 4;
			pstmtAnagrafica.setString(++index, idExtV);
			pstmtAnagrafica.setString(++index, codConcessione);
			pstmtAnagrafica.setString(++index, codEnte);
			pstmtAnagrafica.setString(++index, numQuietanza);
			pstmtAnagrafica.setInt(++index, Integer.parseInt(prog));
			pstmtAnagrafica.setString(++index, tipoSoggetto);
			pstmtAnagrafica.setString(++index, cognome);
			pstmtAnagrafica.setString(++index, nome);
			pstmtAnagrafica.setString(++index, denom);
			pstmtAnagrafica.setString(++index, comune);
			
			setDefaultParameters(pstmtAnagrafica, index, idOrig, getHash(idOrig));
			pstmtAnagrafica.executeUpdate();

		}catch (SQLException e) {
			if(e.getMessage().startsWith("ORA-00001")){
			}
			else {
				log.error("________ERRORE IN ELEBORAZIONE: " + idOrig);
				log.error("Errore: ", e);
			}
		}
		
	}
	
	private void elaboraAnagraficaSocieta(String currentLine, String tipoRecord) {
		
		String numQuietanza = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "numQuietanza"),'0');
		String prog = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "prog"),'0');
		String tipoSoggetto = "P";
		
		String idVers = annoRiferimento+"|"+dataScadenza+"|"+progInvio+"|"+numQuietanza +"|"+prog;
		String idOrig = idVers+"|"+tipoSoggetto;
		
		String codConcessione = cleanLeftPad(getPropertySubstring(currentLine, tipoRecord, "codConcessione"),'0');
		String codEnte = getPropertySubstring(currentLine,tipoRecord, "codEnte");
		
		String denom=org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine,tipoRecord, "denominazione"));
		String comune = org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine,tipoRecord, "comune"));
		
		this.salvaAnagrafica(idOrig, idVers, codConcessione, codEnte, numQuietanza, prog, tipoSoggetto, null, null, denom, comune);
	}

	private void elaboraAnagraficaSoggetto(String currentLine, String tipoRecord) {
		String numQuietanza = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "numQuietanza"),'0');
		String prog = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "prog"),'0');
		String tipoSoggetto = "G";
		
		String idVers = annoRiferimento+"|"+dataScadenza+"|"+progInvio+"|"+numQuietanza +"|"+prog;
		String idOrig = idVers+"|"+tipoSoggetto;
		
		String codConcessione = cleanLeftPad(getPropertySubstring(currentLine, tipoRecord, "codConcessione"),'0');
		String codEnte = getPropertySubstring(currentLine,tipoRecord, "codEnte");
		
		String cognome=org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine,tipoRecord, "cognome"));
		String nome =   org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine,tipoRecord, "nome"));
		String comune = org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine,tipoRecord, "comune"));
		
		this.salvaAnagrafica(idOrig, idVers, codConcessione, codEnte, numQuietanza, prog, tipoSoggetto, cognome, nome, null, comune);
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
		 annoRiferimento = line.substring(7,11);
		 dataScadenza = null;
		 progInvio = null;
		if(line.substring(11).startsWith("20")){
			dataScadenza = line.substring(11,19);
			progInvio = line.substring(19, 21);
		}else{
			dataScadenza = "20" + line.substring(11,17);
			progInvio = line.substring(17, 19);
		}
		
		SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
		try {
			env.saveFornitura(SDF.parse(dataScadenza), file);
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
		
		belfiore = ctx.getBelfiore();
		
		try {

			String sqlRiga = "insert into "
					+ env.tableRiga
					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmtRiga = con.prepareStatement(sqlRiga);
			
			String sqlAnagrafica = "insert into "
					+ env.tableAnagrafica
					+ " values (";
					for(int i=0; i<24-1; i++)
						sqlAnagrafica+="?,";
					sqlAnagrafica+="?)";
			pstmtAnagrafica = con.prepareStatement(sqlAnagrafica);
			
			String sqlVers = "insert into "
					+ env.tableContabile
					+ " values (";
			for(int i=0; i<39-1; i++)
				sqlVers+="?,";
			sqlVers+="?)";
			pstmtVersamento = con.prepareStatement(sqlVers);

			String sqlViolazione = "insert into "
					+ env.tableViolazione
					+ " values (";
			for(int i=0; i<37-1; i++)
				sqlViolazione+="?,";
			sqlViolazione+="?)";
			pstmtViolazione = con.prepareStatement(sqlViolazione);

			
			
		} catch (SQLException e) {
			throw new RulEngineException("Problema in creazione Statement ",e);
		}
		
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		ConcreteImport ci = ConcreteImportFactory.getConcreteImport(this);
		tabs = ci.getTabelleAndTipiRecord();
		
		//controllo se il db fa riferimento ad un comune
		//e quindi filtro il domicilio fiscale o se è generale
		
		
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
			if (pstmtAnagrafica != null)
				pstmtAnagrafica.close();
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
			
			//controllo Redditi: se il file properties che definisce modello e anno
			//non esiste lo salto, stessa cosa per il quadro RB
			String[] arr = file.split("\\.");
			boolean existRedditiProp = loadProperties("DM");
			
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
							
						}else{
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
	
	private void elaboraContabile(String currentLine, String tipoRecord){
		SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
		
		String numQuietanza = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "numQuietanza"),'0');
		String prog = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "prog"),'0');
		
		String idOrig = annoRiferimento+"|"+dataScadenza+"|"+progInvio+"|"+numQuietanza +"|"+prog;
		try {
				String codConcessione = cleanLeftPad(getPropertySubstring(currentLine, tipoRecord, "codConcessione"),'0');
				String codEnte = getPropertySubstring(currentLine,tipoRecord, "codEnte");
				
				String dataVersamento = getPropertySubstring(currentLine,tipoRecord, "dataVersamento");
				Date dtVersamento = SDF.parse(dataVersamento);
				
				String cf =  org.apache.commons.lang.StringUtils.trimToNull(getPropertySubstring(currentLine,tipoRecord, "cf"));
				String nRifQuietanza = cleanLeftPad(getPropertySubstring(currentLine,tipoRecord, "nRifQuietanza"),'0');
				Double importoVersato = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "importoVersato"))/100;
				Double importoTerr = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "importoTerr"))/100;
				Double importoAreeFabb = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "importoAreeFabb"))/100;
				Double importoAbP = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "importoAbP"))/100;
				Double importoFabb = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "importoFabb"))/100;
				Double importoDetr = Double.parseDouble(getPropertySubstring(currentLine,tipoRecord, "importoDetr"))/100;
				
				String flagQuadratura = getPropertySubstring(currentLine,tipoRecord, "flagQuadratura");
				String flagReperibilita = getPropertySubstring(currentLine,tipoRecord, "flagReperibilita");
						
				String tipoVersamento = getPropertySubstring(currentLine,tipoRecord, "tipoVersamento");
				
				String dataRegistrazione = getPropertySubstring(currentLine,tipoRecord, "dataRegistrazione");
				Date dtRegistrazione = null;
				try{
					if(!"00000000".equals(dataRegistrazione))
						dtRegistrazione = SDF.parse(dataRegistrazione);
				}catch(Exception e){}
				
				String flagCompetenza = getPropertySubstring(currentLine,tipoRecord, "flagCompetenza");
				String comune = getPropertySubstring(currentLine,tipoRecord, "comune");
				String cap = getPropertySubstring(currentLine,tipoRecord, "cap");
				Integer numFabb = Integer.parseInt(getPropertySubstring(currentLine,tipoRecord, "numFabb"));
				String flagSaldoAcconto = getPropertySubstring(currentLine,tipoRecord, "flagSaldoAcconto");
				String flagIdentificazione = getPropertySubstring(currentLine,tipoRecord, "flagIdentificazione");
				String annoImposta = getPropertySubstring(currentLine,tipoRecord, "annoImposta");
				String flagRavvedimento = getPropertySubstring(currentLine,tipoRecord, "flagRavvedimento");
		
			
				pstmtVersamento.clearParameters();
				int index = 4;
				pstmtVersamento.setString(++index, codConcessione);
				pstmtVersamento.setString(++index, codEnte);
				pstmtVersamento.setString(++index, numQuietanza);
				pstmtVersamento.setInt(++index, Integer.parseInt(prog));
				pstmtVersamento.setDate(++index, new java.sql.Date(dtVersamento.getTime()));
				pstmtVersamento.setString(++index, cf);
				pstmtVersamento.setString(++index, nRifQuietanza);
				pstmtVersamento.setDouble(++index, importoVersato);
				pstmtVersamento.setDouble(++index, importoTerr);
				pstmtVersamento.setDouble(++index, importoAreeFabb);
				pstmtVersamento.setDouble(++index, importoAbP);
				pstmtVersamento.setDouble(++index, importoFabb);
				pstmtVersamento.setDouble(++index, importoDetr);
				pstmtVersamento.setString(++index, flagQuadratura);
				pstmtVersamento.setString(++index, flagReperibilita);
				pstmtVersamento.setString(++index, tipoVersamento);
				pstmtVersamento.setDate(++index, dtRegistrazione!=null ? new java.sql.Date(dtRegistrazione.getTime()):null);
				pstmtVersamento.setString(++index, flagCompetenza);
				pstmtVersamento.setString(++index, comune);
				pstmtVersamento.setString(++index, cap);
				pstmtVersamento.setInt(++index, numFabb);
				pstmtVersamento.setString(++index, flagSaldoAcconto);
				pstmtVersamento.setString(++index, flagIdentificazione);
				pstmtVersamento.setString(++index, annoImposta);
				pstmtVersamento.setString(++index, flagRavvedimento);
				
				
				setDefaultParameters(pstmtVersamento, index, idOrig, getHash(idOrig));
				pstmtVersamento.executeUpdate();
	
			}catch(ParseException pe){
				log.error("___ERRORE di conversione data: "+idOrig,pe);
			}catch (SQLException e) {
				if(e.getMessage().startsWith("ORA-00001")){
				}
				else {
					log.error("________ERRORE IN ELEBORAZIONE: " + idOrig);
					log.error("Errore: ", e);
				}
			}
	}
	
	
	
	private String getPropertySubstring(String currentLine, String tipoRecord, String propName) {
		try {
			String start = Properties.getProperty( tipoRecord + "."+ propName + ".start");
			if(start != null && !"".equals(start)){
				String end = Properties.getProperty(tipoRecord + "."+ propName + ".end");
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

