package it.webred.rulengine.brick.loadDwh.load.locazioni.loca;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesFlat;
import it.webred.rulengine.exception.RulEngineException;

public class LocazioniFiles<T extends LocazioniEnv>  extends ImportFilesFlat<T> {

	public LocazioniFiles(T env) {
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String sData = "";
		String sMeseGiornoIni = "0101";
		String sMeseGiornoFin="1231";
		if(file.startsWith("ACCLOC"))
			sData = file.substring(13, 17);
		else if(file.startsWith("CONLOC"))
			sData = file.substring(18, 22);
		else{
			sData = file.substring(14, 18);
			String trimestre = file.substring(18, 21);
			if("090".equals(trimestre)){
				sMeseGiornoFin = "0331";
			}else if("181".equals(trimestre)){
				sMeseGiornoIni = "0401";
				sMeseGiornoFin = "0630";
			}else if("273".equals(trimestre)){
				sMeseGiornoIni = "0701";
				sMeseGiornoFin = "0930";
			}else
				sMeseGiornoIni = "1001";
		}
		
		try {
			env.saveFornitura(sdf.parse(sData + sMeseGiornoIni), file);
			env.saveFornitura(sdf.parse(sData + sMeseGiornoFin), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}

}
