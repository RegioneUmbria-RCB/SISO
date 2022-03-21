package it.webred.rulengine.brick.loadDwh.load.pubblicita;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class PubblicitaStandardFiles <T extends PubblicitaTipoRecordEnv<TestataStandardFileBean>> extends ImportStandardFiles<T> {
	
	public PubblicitaStandardFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}
	
	public String getTipoRecordFromLine(String currentLine)
	throws RulEngineException {
		return currentLine.substring(0,1);
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine) throws RulEngineException {
		List<String> campi = null;
		if ("A".equals(tipoRecord) || "B".equals(tipoRecord) || "9".equals(tipoRecord)) {
			campi = Arrays.asList(currentLine.split("\\|", -1));
		} else
			throw new RulEngineException("Tipo record non gestito:" + tipoRecord);
		
		return campi;
	}

	/* (non-Javadoc)
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles#preProcesing(java.sql.Connection)
	 */
	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		
		Statement st = null;
		try {
			st = con.createStatement();
			String sql = env.createTableA;
			st.execute(sql);
		} catch (SQLException e1) {
			log.warn("Tabella A esiste già: OK, BENE");
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
			st.execute(env.createTableB);
		} catch (SQLException e1) {
			log.warn("Tabella B esiste già: OK, BENE");
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
			st.execute(env.RE_PUBBLICITA_A_IDX);
		} catch (SQLException e1) {
			log.warn("Indice A esiste già: OK, BENE");
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
			st.execute(env.RE_PUBBLICITA_B_IDX);
		} catch (SQLException e1) {
			log.warn("Indice B esiste già: OK, BENE");
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
		return super.getTestata(file);
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		System.out.println("PubblicitaStandardFiles : PUBBL");
		return "PUBBL";
	}

	@Override
	public void preProcesingSpec(Connection con) throws RulEngineException {
		// TODO Auto-generated method stub		
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}
	
}


