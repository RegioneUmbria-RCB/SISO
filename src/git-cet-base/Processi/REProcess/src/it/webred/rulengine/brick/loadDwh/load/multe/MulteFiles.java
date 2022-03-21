package it.webred.rulengine.brick.loadDwh.load.multe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.rulengine.Command;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.dto.ConfigurazioneEnte;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;
import it.webred.rulengine.entrypoint.impl.JellyLauncher;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.CommandFactory;
import it.webred.utils.FileUtils;
import it.webred.utils.StringUtils;

public class MulteFiles<T extends MulteEnv> extends ImportFilesFlat<T> {

	
	private java.util.Date dataExport = null;
	
	public MulteFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {

		// vengono restituiti tutti i campi rispetto al separatore ';'
		List<String> campi = new ArrayList<String>();

		// aggiungo tutti tranne ultimo che è il newline
		String[] arr = currentLine.split(";", -1);
		
		//gestisco il num verbale se è "V/197310/S/0" 
		String[] ids = arr[1].split("/", -1);
		if(ids.length == 4 && arr.length < 32){
			String[] arrNew = new String[arr.length+3];
			arrNew[0] = arr[0];
			arrNew[1] = ids[0];
			arrNew[2] = ids[1];
			arrNew[3] = ids[2];
			arrNew[4] = ids[3];
			for(int i = 2; i<arr.length; i++){
				arrNew[i+3] = arr[i];
			}
			arr = arrNew;
		}
		
		for (int i = 0; i < (arr.length - 1); i++) {
			campi.add(arr[i]);
		}
		return campi;
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		if (dataExport!=null)
			return new Timestamp(dataExport.getTime());
		else
			return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	/*
	 * (non-Javadoc) Vado a scompattare la fornitura del GAS
	 * 
	 * @see
	 * it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles
	 * #preProcesing(java.sql.Connection)
	 */
	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		// vado a scompattare la fornitura del GAS, se trovo zip

		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTable1_0);
		} catch (SQLException e1) {
			log.warn("Tabella esiste già : OK , BENE");
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_TRFF_MULTE_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE esiste già : OK , BENE");
		} finally {
			try {
				if (st != null)
					st.close();
			} catch (SQLException e1) {
			}
		}

	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);

	}

	@Override
	public boolean filtroFile(String file, List<String> fileDaElaborare, String cartellaDati) {

		if (file.endsWith("txt")) {
			
			String belfiore = ctx.getBelfiore();
			Long idFonte = ctx.getIdFonte();
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setComune(belfiore);
			criteria.setKey("warningtime.filelock");
			AmKeyValueExt amkvext = cdm.getAmKeyValueExt(criteria);
			Integer lockFileTimeWarning = amkvext != null? Integer.parseInt(amkvext.getValueConf()): null;

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
			String nomeFile = file.substring(0, file.length() - 4);
			String dataFile = file.substring(file.length() - 16, file.length() - 4);

			boolean daElaborare = true;
			if(new File(cartellaDati + nomeFile + ".lck").exists() || new File(cartellaDati + nomeFile + ".LCK").exists()){
				log.info("file: " + file + " locked");
				daElaborare = false;
			}

			//se il file lock è in stallo genero anomalia
			if(!daElaborare && lockFileTimeWarning != null){
				Calendar now = Calendar.getInstance();
				Calendar dataLock = Calendar.getInstance();
				try {
					dataLock.setTime((sdf.parse(dataFile)));
					dataLock.add(Calendar.MINUTE, lockFileTimeWarning.intValue());
					if(now.after(dataLock)){
						
						//MANDA MAIL
						//Recupero i parametri e li setto come input per la regola
						criteria.setKey("nome.comando.sendemail");
						criteria.setComune(null);
						amkvext = cdm.getAmKeyValueExt(criteria);
						String nomeComandoSendEmail = amkvext != null? amkvext.getValueConf(): null;
						criteria.setComune(belfiore);
						criteria.setKey("mail.server");
						amkvext = cdm.getAmKeyValueExt(criteria);
						String server = amkvext != null? amkvext.getValueConf(): null;
						criteria.setKey("mail.server.port");
						amkvext = cdm.getAmKeyValueExt(criteria);
						String port = amkvext != null? amkvext.getValueConf(): null;
						criteria.setKey("email.admin");
						amkvext = cdm.getAmKeyValueExt(criteria);
						String dest = amkvext != null? amkvext.getValueConf(): null;
						criteria.setKey("email.controller");
						criteria.setInstance("Controller");
						amkvext = cdm.getAmKeyValueExt(criteria);
						String mitt = amkvext != null? amkvext.getValueConf(): null;
						String mess = "Messaggio Automatico del Sistema CONTROLLER, scatenato durante l'acquisizione della Fonte Dati: " + getProvenienzaDefault();
						mess += "<br><br>";
						mess += "Si avverte che il file di Lock: " + cartellaDati + nomeFile + ".lck è bloccato da più di " + lockFileTimeWarning + " min.";
						mess += "<br><br>";
						mess += "Buona Giornata";

						HashMap map = new HashMap();
						map.put("SERVER", server);
						map.put("PORT", port);
						map.put("MITTENTE", mitt);
						map.put("DESTINATARIO", dest);
						map.put("SOGGETTO", "CONTROLLER: File Lock: " + cartellaDati + nomeFile + ".lck Bloccato");
						map.put("MESSAGGIO", mess);
						
						log.info("file: " + nomeFile + ".lck bloccato da più di " + lockFileTimeWarning.toString() + " min. Invio Email ad: " + dest);
						
						ConfigurazioneEnte cEnte = new ConfigurazioneEnte(new ArrayList(), new ArrayList());
						JellyLauncher launch = new JellyLauncher(belfiore, idFonte, cEnte);
						launch.executeCommand(nomeComandoSendEmail, map, ctx.getProcessID());
					}
					
				} catch (ParseException e) {
					log.error("Errore nel Parsing: ", e);
				} catch (CommandException e) {
					log.error("Errore nel Command: ", e);
				} catch (RulEngineException e) {
					log.error("Errore nel Command: ", e);
				}
			}
			
			return daElaborare;
		}
		return false;
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return "MULTE";
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return false;
	}

	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		dataExport = estraiDataFornitura(file, "yyyyMMddHHmm");
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

}
