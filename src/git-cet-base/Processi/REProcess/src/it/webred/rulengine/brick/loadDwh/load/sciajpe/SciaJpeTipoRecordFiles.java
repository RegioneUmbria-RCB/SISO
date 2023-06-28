package it.webred.rulengine.brick.loadDwh.load.sciajpe;

import it.webred.rulengine.brick.loadDwh.load.sciajpe.bean.Testata;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.DateFormat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.Calendar;

public class SciaJpeTipoRecordFiles<T extends SciaJpeTipoRecordEnv<Testata>> extends ImportFilesWithTipoRecord<T> {
	
	public static final int NUM_CAMPI = 26;
	
	private String myCurrentLine;
	private boolean inStr = false;
	private boolean escape = false;
	
	public SciaJpeTipoRecordFiles(T env) {
		super(env);
	}//-------------------------------------------------------------------------
	
	public String getTipoRecordFromLine(String currentLine)	throws RulEngineException {
		return "";
	}//-------------------------------------------------------------------------

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine) throws RulEngineException {		
		//per lettura formato csv
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < currentLine.toCharArray().length; i++) {
			char car = currentLine.toCharArray()[i];
			if (car == '\"') {
				if (!inStr) {
					inStr = true;
				} else {
					if ((i == currentLine.toCharArray().length - 1 || currentLine.toCharArray()[i + 1] == ',') && !escape) {
						inStr = false;
					} else if (i == currentLine.toCharArray().length - 1 || currentLine.toCharArray()[i + 1] == '\"') {
						escape = !escape;
						if (!escape) {
							sb.append(car);
						}
					} else {
						escape = false;
						sb.append(car);
					}
				}
			} else if (car == ',' && !inStr) {
				sb.append("§@§");
			} else {
				sb.append(car);
			}
		}
		currentLine = sb.toString();
		
		//per concatenazione righe se c'è un a capo interno ad un valore
		if (myCurrentLine == null) {
			myCurrentLine = "";
		}
		myCurrentLine += ((myCurrentLine.equals("") ? "" : "\r\n") +
						currentLine);
		
		String[] values = myCurrentLine.split("§@§", -1);
		String[] ret = new String[values.length];
		int idx = 0;
		for (String value : values) {
			ret[idx] = value;
			idx++;
		}
		
		if (ret.length < NUM_CAMPI) {
			return new ArrayList<String>();
		}
		
		myCurrentLine = "";
		inStr = false;
		escape = false;
		return Arrays.asList(ret);		
	}//-------------------------------------------------------------------------

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		String dt = env.getTestata().getData();
		Date t = null;
		try {
			t = it.webred.utils.DateFormat.stringToDate(dt, "yyyyMMdd");
		} catch (Exception e) {
			t = new Date(Calendar.getInstance().getTime().getTime());
		}
		return new Timestamp(t.getTime());
	}//-------------------------------------------------------------------------


	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		 
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTableUNO);
		} catch (SQLException e1) {
			log.warn("Tabella esiste gia�: OK, BENE");
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
			st.execute(env.RE_SCIA_JPE_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE esiste gia�: OK, BENE");
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
	public it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata getTestata(String file) throws RulEngineException {
		Testata t = new Testata();
		try {
			t.setData(DateFormat.dateToString(new Date(Calendar.getInstance().getTime().getTime()), "yyyyMMdd"));
			return t;	
		} catch (Exception e) {
			log.error("Errore cercando di leggere la testata del file",e);
			throw new RulEngineException("Errore cercando di leggere la testata del file",e);
		}
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);		
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return "SCIA_JPE";
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return true;
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)	throws RulEngineException {
		// TODO Auto-generated method stub		
	}
	
}
