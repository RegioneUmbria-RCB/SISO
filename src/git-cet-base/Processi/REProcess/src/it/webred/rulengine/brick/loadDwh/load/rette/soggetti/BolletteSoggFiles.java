package it.webred.rulengine.brick.loadDwh.load.rette.soggetti;

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
import java.util.Map;

import org.w3c.dom.Element;

import it.webred.rulengine.brick.loadDwh.load.gas.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesXML;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.FileUtils;
import it.webred.utils.StringUtils;

public class BolletteSoggFiles<T extends BolletteSoggEnv> extends ImportFilesXML<T> {

	private java.util.Date dataExport = null;

	public BolletteSoggFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {
		
		return null;
	}

	
	@Override
	public Timestamp getDataExport() throws RulEngineException {
		if (dataExport!=null)
			return new Timestamp(dataExport.getTime());
		else
			return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	/* (non-Javadoc)
	 * Vado a scompattare la fornitura del GAS
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles#preProcesing(java.sql.Connection)
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
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_RTT_SOGG_BOLLETTE_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE esiste già : OK , BENE");
		}
		finally {
			try {
				if (st!=null)
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
				
		if(!file.startsWith("SOG"))
			return false;
		
		int idx = file.indexOf("_");
		String dataAS = file.substring(idx +3, file.length() -4);
		
		if(new File(cartellaDati + "TERMINATO_" + dataAS + ".OK").exists()){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			idx = file.lastIndexOf("_");
			String sData = file.substring(idx +1, file.length() -10);
			try {
				dataExport = sdf.parse(sData);
			} catch (ParseException e) {
				log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
			}
			return true;
		}
		
		return false;
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return "BOLLETTE_SOGG";
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return false;
	}

	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNodeName() throws RulEngineException {
		return "/ListaSoggetto/Soggetto";
	}

	@Override
	public List<String> getNodeToExclude() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubNode() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSubNodeOneValue() throws RulEngineException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void postElaborazione(String file, List<String> fileDaElaborare, String cartellaFiles) {

		//se trovo un altro file con la stessa data la chain RETTE non è ancora finita
		//altrimenti sposto anche il file TERMINATO su ELABORATI
		
		int idx = file.lastIndexOf("_");
		String data = file.substring(idx +1, file.length() -4);
		
		boolean altroFileRetteFound = false;
		String fileTerminato = "";
		for(String altroFile: fileDaElaborare){
			if(altroFile.contains(data) && !altroFile.contains("TERMINATO_") && !altroFile.contains("SOG"))
				altroFileRetteFound = true;
			if(altroFile.contains("TERMINATO_" + data))
				fileTerminato = altroFile;
		}
		
		if(!altroFileRetteFound && !fileTerminato.equals("")){
			new File(cartellaFiles+fileTerminato).renameTo(new File(cartellaFiles+"ELABORATI/"+fileTerminato));
		}
		
	}

	@Override
	public boolean elabPersonalizzata() throws RulEngineException {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void elabCurrentNode(org.dom4j.Element element,
			HashMap<String, String> hmCampi, String tempTable,
			Timestamp dataExport, boolean lettoQualcosa, List<String> errori)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Map<String, String> getNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}
}
