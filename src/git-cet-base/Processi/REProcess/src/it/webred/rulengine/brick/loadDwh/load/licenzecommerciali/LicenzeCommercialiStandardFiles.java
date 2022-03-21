package it.webred.rulengine.brick.loadDwh.load.licenzecommerciali;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportStandardFiles;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.exception.RulEngineException;

public class LicenzeCommercialiStandardFiles<T extends LicenzeCommercialiTipoRecordEnv<TestataStandardFileBean>> extends ImportStandardFiles<T> {
	
	public LicenzeCommercialiStandardFiles(T env) {
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
	 * Vado a scompattare la fornitura delle LICENZE COMMERCIALI
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles#preProcesing(java.sql.Connection)
	 */
	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		// vado a scompattare la fornitura delle LICENZE COMMERCIALI, se trovo zip
		
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTableA);
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
			st.execute(env.RE_LICENZECOMMERCIALI_A_IDX);
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
			st.execute(env.RE_LICENZECOMMERCIALI_B_IDX);
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
		return "LICENZECOMMERCIALI";
	}

	@Override
	public void preProcesingSpec(Connection con) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sData = file.substring(20, 28);
		
		try {
			env.saveFornitura(sdf.parse(sData), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}
	
}

