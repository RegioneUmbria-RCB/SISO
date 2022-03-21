package it.webred.rulengine.brick.loadDwh.load.redditi.r2011;

import it.webred.rulengine.brick.loadDwh.load.redditi.r2011.RedditiEnv;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class RedditiFiles<T extends RedditiEnv> extends ImportFilesFlat<T> {

	public RedditiFiles(T env) {
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
		
		//viene restituita la riga per intero
		campi.add(currentLine);
		
		return campi;
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
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {
		// TODO Auto-generated method stub
		
	}

}


