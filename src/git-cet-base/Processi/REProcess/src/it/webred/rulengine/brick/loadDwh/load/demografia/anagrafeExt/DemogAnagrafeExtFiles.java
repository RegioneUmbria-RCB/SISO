package it.webred.rulengine.brick.loadDwh.load.demografia.anagrafeExt;

import it.webred.rulengine.brick.loadDwh.load.demografia.anagrafeExt.DemogAnagrafeExtEnv;
import it.webred.rulengine.brick.loadDwh.load.demografia.dto.MetricheTracciato;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.utils.StringUtils;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DemogAnagrafeExtFiles<T extends DemogAnagrafeExtEnv> extends ImportFilesFlat<T> {

	private Date dataExport = null;
	private String dataProg = null;
	
	public DemogAnagrafeExtFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		if (dataExport!=null)
			return new Timestamp(dataExport.getTime());
		else
			return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine) throws RulEngineException {

		List<String> campi = new ArrayList<String>();
		
		List<MetricheTracciato> mmtt = super.env.metricheTracciato;
		for(MetricheTracciato mt: mmtt) {
			Integer endIndex = mt.getStart()+mt.getEnd();
			while (currentLine.length() < endIndex) {
				currentLine += " ";
			}
			String field = currentLine.substring(mt.getStart(),endIndex);
			campi.add(field.trim());
		}
		
		//imposto il progressivo con la data del file
		if (dataProg == null) dataProg = "";
		dataProg = StringUtils.padding(dataProg, 14, '0', false);
		String prog = campi.get(campi.size() - 1);
		campi.set(campi.size() - 1, dataProg + prog);
		
		//valore fisso per il campo FLAG_INVIATO_ANASAN in questa fase
		campi.add("0");
		
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
		dataExport = estraiDataFornitura(file,null,null);
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);
	}

	
	private Date estraiDataFornitura(String file, String cartellaDati, String line)
			throws RulEngineException{
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String[] arr = file.split("_");
			String data= null;
			if(arr != null && arr.length > 2) 
				data = arr[3].substring(0,8);
			else
				return null;

			return sdf.parse(data);
		} catch (Exception e) {
			throw new RulEngineException("Impossibile estrarre la data fornitura dal file " + file,e);
		}
	}
	
	private String estraiDataProg(String file) throws RulEngineException {
		try {
			String[] arr = file.split("_");
			String data = null;
			if(arr != null && arr.length > 3) {
				String[] dataArr = arr[3].split("\\.");
				if(dataArr != null && dataArr.length > 1) {
					data = dataArr[0];
					try {
						Long.parseLong(data);
					} catch (Exception e) {
						return null;
					}
				}
			}
			else
				return null;

			return data;
		} catch (Exception e) {
			throw new RulEngineException("Impossibile estrarre la data per il progressivo dal file " + file,e);
		}
	}
		
	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException{
		
			env.saveFornitura(estraiDataFornitura(file,cartellaDati,line), file);
			
			dataProg = estraiDataProg(file);
		
	}

}

