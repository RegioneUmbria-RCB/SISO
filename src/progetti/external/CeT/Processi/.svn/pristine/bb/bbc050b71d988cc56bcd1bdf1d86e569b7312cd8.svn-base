package it.webred.rulengine.brick.loadDwh.load.concessioni;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImport;
import it.webred.rulengine.exception.RulEngineException;

public class ConcessioniStandardFiles<T extends ConcessioniStandardFilesEnv<TestataStandardFileBean>> extends ImportStandardFiles<T> {
	
	private static final String C = "C";

	public ConcessioniStandardFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void preProcesingSpec(Connection con) throws RulEngineException {

		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTableA);
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
			st.execute(env.createTableB);
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
			st.execute(env.createTableC);
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
			st.execute(env.createTableD);
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
			st.execute(env.createTableE);
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
			st.execute(env.RE_CONCESSIONI_A_IDX);
		} catch (SQLException e1) {
			log.warn("indice esiste già : OK , BENE");
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
			st.execute(env.RE_CONCESSIONI_B_IDX);
		} catch (SQLException e1) {
			log.warn("indice esiste già : OK , BENE");
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
			st.execute(env.RE_CONCESSIONI_C_IDX);
		} catch (SQLException e1) {
			log.warn("indice esiste già : OK , BENE");
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
			st.execute(env.RE_CONCESSIONI_D_IDX);
		} catch (SQLException e1) {
			log.warn("indice esiste già : OK , BENE");
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
			st.execute(env.RE_CONCESSIONI_E_IDX);
		} catch (SQLException e1) {
			log.warn("indice esiste già : OK , BENE");
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
	public String getProvenienzaDefault() throws RulEngineException {
		return C;
	}

	@Override
	public Testata getTestata(String file) throws RulEngineException {
		return super.getTestata(file);
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sData = file.substring(17, 25);
		
		try {
			env.saveFornitura(sdf.parse(sData), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}

	@Override
	protected String getCharsetName(String fileName) {
    	if (fileName != null && fileName.toUpperCase().startsWith("CONCESSIONI_SUAP")) {
    		return "UTF-8";
    	}
		return null;
    }

}
