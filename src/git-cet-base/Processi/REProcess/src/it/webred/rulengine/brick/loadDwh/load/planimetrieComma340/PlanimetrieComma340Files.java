package it.webred.rulengine.brick.loadDwh.load.planimetrieComma340;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.exception.RulEngineException;

public class PlanimetrieComma340Files<T extends PlanimetrieComma340Env> extends ImportFilesFlat<T> {
	
	public PlanimetrieComma340Files(T env) {
		super(env); 
		// TODO Auto-generated constructor stub
	}
	



	/* (non-Javadoc)
	 * Vado a scompattare la fornitura delle LICENZE COMMERCIALI
	 * @see it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFiles#preProcesing(java.sql.Connection)
	 */
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
		
		// CANCELLO LA TABELLA PRIMA DI INSERIRE TUTTI I DATI - VADO DUNQUE SEMPRE IN SOSTITUZIONE
		
		try {
			st = con.createStatement();
			st.execute(env.deleteALL);
		} catch (SQLException e1) {
			log.error("Problemi durante lo svuotamento della tabella");
			throw new RulEngineException("Errore di svuotamento tabella elenco file planimetrie");
		}finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
	
		try {
			st = con.createStatement();
			st.execute(env.RE_PLANIMETRIE_COMMA340_A_IDX);
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
		
	
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine) {
		List<String> campi = null;
		campi = Arrays.asList(currentLine.split("\\|"));
		
		return campi;
	
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
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return false;
	}




	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}



	

	





	
}

