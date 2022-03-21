package it.webred.rulengine.brick.loadDwh.load.acqua.milano;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesExcel;
import it.webred.rulengine.exception.RulEngineException;

public class AcquaMilanoFiles<T extends AcquaMilanoEnv> extends ImportFilesExcel<T> {
	
	public AcquaMilanoFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<String, String> elabCurrentRow(int rowNum, List<String> campi)
			throws RulEngineException {

		HashMap<String, String> hmCampi = new HashMap<String, String>();
		
		if(rowNum != 1){
			
			try{
				hmCampi.put("tipo_contratto", campi.get(0));
				hmCampi.put("cod_servizio", campi.get(1) + campi.get(2));
				hmCampi.put("rag_soc_ubicazione", campi.get(3));
				hmCampi.put("stacco", campi.get(4));
				hmCampi.put("giro", campi.get(5));
				hmCampi.put("comune_ubicazione", campi.get(6));
				hmCampi.put("cap_ubicazione", campi.get(8));
				hmCampi.put("via_ubicazione", campi.get(9));
				hmCampi.put("civico_ubicazione", campi.get(10) + ((campi.get(11)!=null&&campi.get(11).length()>0)?"/"+campi.get(11):""));
				hmCampi.put("tipologia", campi.get(12));
				hmCampi.put("cod_fiscale", (campi.get(27)!=null && campi.get(13).length()==16)?campi.get(13):campi.get(27));
				String partIva = (campi.get(14) != null && campi.get(14).length()>2)?campi.get(14):campi.get(26);
				partIva = partIva==null || partIva.length()<2?"":partIva;
				hmCampi.put("part_iva", partIva);
				hmCampi.put("denominazione", campi.get(16));
				hmCampi.put("comune_residenza", campi.get(17));
				hmCampi.put("pr_residenza", campi.get(18));
				hmCampi.put("cap_residenza", campi.get(19));
				hmCampi.put("via_residenza", campi.get(20));
				hmCampi.put("civico_residenza", campi.get(21) + ((campi.get(22)!=null&&campi.get(22).length()>0)?"/"+campi.get(22):""));
				hmCampi.put("telefono", (campi.get(23) != null?campi.get(23):"") + (campi.get(24) != null && campi.get(24).length()>2?campi.get(24):""));
				hmCampi.put("fax_email", campi.get(25));
			}catch (Exception e) {
				log.error("Errore di lettura campi da XLSX ", e);
			}
			
		}else return null;
		
		return hmCampi;
		
	}


	@Override
	public void postElaborazione(String file, List<String> fileDaElaborare,
			String cartellaFiles) {
		// TODO Auto-generated method stub
		
	}


	@Override
	protected void preProcesingFile(String file) throws RulEngineException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		// TODO Auto-generated method stub
		
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
	public Timestamp getDataExport() throws RulEngineException {
		return new Timestamp(Calendar.getInstance().getTimeInMillis());
	}


	@Override
	public List<String> getValoriFromLine(String tipoRecord, String currentLine)
			throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)
			throws RulEngineException {

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		int ext = file.lastIndexOf(".");
		int inidata = ext - 7;
		String data = file.substring(inidata, ext);
		data = data.substring(0,4) + "20" + data.substring(5);
		
		try {
			env.saveFornitura(sdf.parse(data), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}

	@Override
	public int numeroColonne() {
		return 28;
	}

}
