package it.webred.rulengine.brick.loadDwh.load.acqua.spoleto;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesExcel;
import it.webred.rulengine.exception.RulEngineException;

public class AcquaSpoletoFiles<T extends AcquaSpoletoEnv> extends ImportFilesExcel<T> {
	
	public AcquaSpoletoFiles(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	@Override
	public HashMap<String, String> elabCurrentRow(int rowNum, List<String> campi)
			throws RulEngineException {

		HashMap<String, String> hmCampi = new HashMap<String, String>();
		
		if(rowNum != 1){
			
			try{
				hmCampi.put("cod_servizio", campi.get(0));
				hmCampi.put("rag_soc_ubicazione", campi.get(1));
				if(campi.get(3)!= null){
					String denom = campi.get(11);
					if(denom != null){
						int ext = denom.indexOf(" ");
						hmCampi.put("cognome", denom.substring(0, ext).trim());
						hmCampi.put("nome", denom.substring(ext).trim());
					}
				}else{
					hmCampi.put("denominazione", campi.get(11)!=null?campi.get(11):"");
				}
				hmCampi.put("part_iva", campi.get(2)!=null?campi.get(2):"");
				hmCampi.put("cod_fiscale", campi.get(3)!=null?campi.get(3):"");
				hmCampi.put("consumo_medio", campi.get(4)!=null?campi.get(4):"");
				hmCampi.put("descr_categoria", campi.get(6)!=null?campi.get(6):"");
				hmCampi.put("via_ubicazione", campi.get(8)!=null?campi.get(8):"");
				hmCampi.put("civico_ubicazione", campi.get(9) + ((campi.get(10)!=null && campi.get(10).trim().length()>0)?"/"+campi.get(10):""));
				hmCampi.put("via_residenza", campi.get(12)!=null?campi.get(12):"");
				hmCampi.put("civico_residenza", campi.get(13) + ((campi.get(14)!=null && campi.get(14).trim().length()>0)?"/"+campi.get(14):""));
				hmCampi.put("cap_residenza", campi.get(16)!=null?campi.get(16):"");
				hmCampi.put("pr_residenza", campi.get(17)!=null?campi.get(17):"");
				hmCampi.put("foglio", campi.get(19)!=null?campi.get(19):"");
				hmCampi.put("particella", campi.get(20)!=null?campi.get(20):"");
				hmCampi.put("subalterno", campi.get(21)!=null?campi.get(21):"");

			}catch (Exception e) {
				log.error("Errore di lettura campi da XLS ", e);
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

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		int ext = file.lastIndexOf(".");
		int inidata = ext - 8;
		String data = file.substring(inidata, ext);
		
		try {
			env.saveFornitura(sdf.parse(data), file);
		} catch (ParseException e) {	
			log.debug("_______ ! Errore su parsing data Tracciamento fornitura: " + file);
		}
		
	}

	@Override
	public int numeroColonne() {
		return 22;
	}

}
