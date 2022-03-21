package it.webred.rulengine.brick.reperimento.impl.suap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.reperimento.AbstractReperimentoCommand;
import it.webred.rulengine.brick.reperimento.impl.suap.utils.SuapFileReader;
import it.webred.rulengine.brick.reperimento.impl.suap.utils.SuapFileWriter;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;


public class ReperimentoSuapImpl extends AbstractReperimentoCommand implements Rule {
	
	//nome delle prprietà di configurazione
	public static final String DIR_FILE_KEY = "dir.files";
	public static final String DIRS_FILE_KEY = "dirs.files";
	
	//nome del file temporaneo per prprietà ente
	public static final String ENTE_FILENAME = "enteFonte.properties";
	
	//chiavi prprietà ente
	public static final String CODENT = "CODENT";
	public static final String DESCRIZIONE = "DESCRIZIONE";
	public static final String FONTE = "FONTE";
	
	
	public ReperimentoSuapImpl(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}

	@Override
	public CommandAck doReperimento(String belfiore, Long idFonte, String reProcessId, Context ctx) throws CommandException {
		log.info("Reperimento concessioni edilizie SUAP...");
		
		CommandAck ack = null;
		Connection conn = null;		
		String message = null;
		
		try {
			conn = RulesConnection.getConnection("DWH_" + belfiore);
			//recupero del path locale dell'ente/fd
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune(DIR_FILE_KEY, belfiore, idFonte.toString());			
			String dirFiles = param.getValueConf();
			AmKeyValueExt paramSuap = cdm.getAmKeyValueExtByKeyFonteComune(DIRS_FILE_KEY, belfiore, idFonte.toString());
			String dirsFiles = paramSuap.getValueConf();
			if (dirsFiles == null || dirsFiles.equals("") || dirFiles == null || dirFiles.equals("")) {
				message = "Fonte dati non presente o non configurata correttamente";
				log.error(message);
				ack = new ErrorAck(message);
				return ack;
			}			
					
			ack = new ApplicationAck("Fonte dati presente");
			
			try {
				writeTempEnteFonte(conn, dirFiles, belfiore, idFonte);
				HashMap<String, String> errs = readFiles(dirFiles, dirsFiles);
				SuapFileWriter.writeFile(dirFiles);
				writeErrsFile(dirFiles, errs);
				SuapFileReader.deleteFolder(new File(dirFiles + File.separator + SuapFileReader.PROPERTIES_FOLDER));				
			} catch (Exception e) {					 
				message = "Errore di esecuzione del reperimento dati SUAP";
				log.error(message, e);
				ack = new ErrorAck(message);					 
			}
			
		} catch (Exception e){			 
			message = "Errore di esecuzione";
			log.error(message, e);
			ack = new ErrorAck(message);			 
		} finally {
			try	{
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}catch (SQLException e)	{
				log.debug(e);
			}
		}

		return ack;
	}
	
	private void writeTempEnteFonte(Connection conn, String dir, String belfiore, Long idFonte) throws Exception {
		belfiore = belfiore.toUpperCase();
		String desEnte = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("SELECT DESCRIZIONE FROM SIT_ENTE WHERE CODENT = ?");
			pstmt.setString(1, belfiore);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				desEnte = rs.getString("DESCRIZIONE").toUpperCase();
			}
			
			String fileTo = dir + ENTE_FILENAME;
			Properties props = new Properties();
			props.put(CODENT, belfiore);
			props.put(DESCRIZIONE, desEnte);
			props.put(FONTE, "" + idFonte.intValue());
			OutputStream os = new FileOutputStream(fileTo);
			props.store(os, null);
			os.close();			
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (pstmt!=null)
					pstmt.close();
			} catch (SQLException e1) {
			}
		}
	}
	
	private HashMap<String, String> readFiles(String dirFiles, String dirsFiles) throws Exception {
		HashMap<String, String> errs = new HashMap<String, String>();		
		//ricerca delle cartelle SUAP nei percorsi specifici (prprietà dirs.files)
		String[] arrDirsFiles = dirsFiles.split("\\|", -1);
		int idx = 0;
		for (String dirFilesSuap : arrDirsFiles) {
			idx++; //base 1
			if (!SuapFileReader.setFileSeparator(dirFilesSuap).endsWith(File.separator)) {
				dirFilesSuap += File.separator;
			}
			File fSuap = new File(dirFilesSuap);			
			if(!fSuap.exists() || !fSuap.isDirectory()) {
				continue;
			}
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name)
				{
					boolean ok = false;
					File f = new File(dir, name);
					if (f.exists() && f.isDirectory() && name.indexOf("_") > -1) {
						String idStr = name.substring(0, name.indexOf("_"));
						long id = -1;
						try {
							id = Long.parseLong(idStr);
						} catch (Exception e) {}
						ok = (id > 0);
					}					
					return ok;
				}
			};
			String[] folderList = fSuap.list(filter);
			if (folderList != null) {				
				for (String folderName : folderList) {
					String id = folderName.substring(0, folderName.indexOf("_")) + "_" + idx;
					//ricerca e lettura dei file xml
					SuapFileReader.readFiles(dirFiles, dirFilesSuap + folderName, id, errs);
				}
			}			
		}
		return errs;
	}
	
	private void writeErrsFile(String dirFiles, HashMap<String, String> errs) throws Exception {
		if (errs != null && errs.size() > 0) {
			//scrittura file properties degli errori
			Properties props = new Properties();
			Iterator<String> it = errs.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				String value = errs.get(key);
				props.put(key, value);
			}
			String dataOra = "";
			Calendar cal = new GregorianCalendar();
			dataOra = cal.get(Calendar.YEAR) +
					"_" + 
					((cal.get(Calendar.MONTH) + 1) < 10 ? "0" : "") +
					(cal.get(Calendar.MONTH) + 1) +
					"_" +
					(cal.get(Calendar.DAY_OF_MONTH) < 10 ? "0" : "") +
					cal.get(Calendar.DAY_OF_MONTH) +
					"_" +
					(cal.get(Calendar.HOUR_OF_DAY) < 10 ? "0" : "") +
					cal.get(Calendar.HOUR_OF_DAY) +
					"_" +
					(cal.get(Calendar.MINUTE) < 10 ? "0" : "") +
					cal.get(Calendar.MINUTE) +
					"_" +
					(cal.get(Calendar.SECOND) < 10 ? "0" : "") +
					cal.get(Calendar.SECOND) +
					"_" +
					(cal.get(Calendar.MILLISECOND) < 10 ? "00" : (cal.get(Calendar.MILLISECOND) < 100 ? "0" : "")) +
					cal.get(Calendar.MILLISECOND) +
					"_";
			dirFiles += "ELABORATI" + File.separator;
			if (!new File(dirFiles).exists()) {
				new File(dirFiles).mkdir();
			}
			File fileTo = new File(dirFiles + "ERRS_" + dataOra + "SUAP.properties");
			OutputStream os = new FileOutputStream(fileTo);
			props.store(os, null);
			os.flush();
			os.close();
		}
	}	

}

