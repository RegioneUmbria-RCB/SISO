package it.webred.rulengine.brick.loadDwh.load.demografia.milano;

import it.webred.rulengine.brick.loadDwh.load.demografia.dto.MetricheTracciato;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class DemogAnagrafeMilanoFiles <T extends DemogAnagrafeMilanoEnv> extends ImportFilesFlat<T> {

	public DemogAnagrafeMilanoFiles(T env) {
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
		
		List<MetricheTracciato> mmtt = super.env.metricheTracciato;
		for(MetricheTracciato mt: mmtt) {
			Integer endIndex = mt.getStart()+mt.getEnd();
			String field = null;
			try {
				field = currentLine.substring(mt.getStart(),endIndex);
			} catch (StringIndexOutOfBoundsException exc) {
				//caso particolare:
				//possono esserci righe troncate alla fine del penultimo campo, intendendo l'ultimo = ""
				field = "";
			}
			campi.add(field.trim());
		}
		
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		file = file.replaceAll(".TXT", "");
		file = file.replaceAll(".txt", "");
		file = file.substring(file.length()-6,file.length());
		file = "20"+file; 
			
			try {
				env.saveFornitura(sdf.parse(file), file);
			} catch (ParseException e) {	
			}
		
	}

}
