package it.webred.rulengine.brick.loadDwh.load.cnc.statoriscossione;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;


import it.webred.rulengine.brick.loadDwh.load.cnc.CNCParseException;
import it.webred.rulengine.brick.loadDwh.load.cnc.RecordParserBuilder;
import it.webred.rulengine.brick.loadDwh.load.cnc.flusso290.bean.CNCTestata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;

public class FlussoStatoRiscTipoRecordFiles <T extends FlussoStatoRiscTipoRecordEnv<CNCTestata>> extends ImportFilesWithTipoRecord<T> {

	private long rowId = 0;
	
	public FlussoStatoRiscTipoRecordFiles(T env) {
		super(env);
	}


	@Override
	public it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata getTestata(
			String file) throws RulEngineException {
		
		return null;
	}

	@Override
	public String getTipoRecordFromLine(String currentLine)
			throws RulEngineException {
		
		if (currentLine == null || currentLine.trim().equals(""))
			return null;
		
		String recordKey = currentLine.substring(0,3);
		//System.out.println("Chiave ["+recordKey+"]");
		return recordKey;
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		// Da rifare 
		java.util.Date d = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String d1 = sdf.format(d);
		Date t = it.webred.utils.DateFormat.stringToDate(d1, "yyyyMMdd");
		return new Timestamp(t.getTime());	
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {		
		return "CNC-Flusso Stato riscossione";
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {
		
		if (currentLine == null || currentLine.equals(""))
			return null;
		
		RecordParserBuilder rpb = RecordParserBuilder.getInstance();
		it.webred.rulengine.brick.loadDwh.load.cnc.RecordParser rp = rpb.getRecordParser(getTipoRecordFromLine(currentLine));
		
		if (rp == null)
			return null;
		
		try {
			rp.parseLine(currentLine);
			rp.finishParseJob();
		}
		catch(CNCParseException cnce) {
			throw new RulEngineException("", cnce);
		}
	
		List<String> result = rp.getCNCRecord();
		result.add(String.valueOf(rowId++));
		return result;
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {		
		return false;
	}

	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		createTable(env.createTableFR00C, con);
		createTable(env.createTableFR99C, con);
		createTable(env.createTableFR0, con);
		createTable(env.createTableFR3, con);
		createTable(env.createTableFR4, con);
		createTable(env.createTableFR5, con);
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);		
	} 
	
	private void createTable(String tableName, Connection con) {
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(tableName);
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
	}


	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

}
