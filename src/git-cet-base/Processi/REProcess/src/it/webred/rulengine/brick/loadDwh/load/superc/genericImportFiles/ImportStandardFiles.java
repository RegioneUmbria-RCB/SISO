package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;
import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportFactory;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.TestataStandardFileBean;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvStandardFiles;
import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.bean.GenericTuples;
import it.webred.rulengine.type.def.TypeFactory;

/**
 * @author MARCO
 *
 * @param <T>
 */
public abstract class ImportStandardFiles<T extends EnvStandardFiles<TestataStandardFileBean>> extends ImportFilesWithTipoRecord<T> {




	public ImportStandardFiles(T env) {
		super(env);
	}

	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return true;
	}

	
	public String getTipoRecordFromLine(String currentLine)
			throws RulEngineException {
		List<String> campi =  Arrays.asList(currentLine.split("\\|", -1));
		return campi.get(0);
	}
	
	@Override
	public List<String> getValoriFromLine(String tipoRecord,String currentLine) throws RulEngineException {
		List<String> campi=null;
		if (tipoRecord!=null)
			campi = Arrays.asList(currentLine.substring(currentLine.indexOf("|")+1).split("\\|", -1));
		else
			campi = Arrays.asList(currentLine.split("\\|", -1));
		return campi;
	}
	
	public void sortFiles(List<String> files) {
		Collections.sort(files);
	}
	
	public Timestamp getDataExport() throws RulEngineException {
		return env.getTestata().getDataExport();
	}
	
	public Testata getTestata(String file) throws RulEngineException {
		BufferedReader fileIn = null;
		String versioneTracciato=null;
		String 	provenienzaDato=null;

		
		Timestamp dataExport=new Timestamp(new java.util.Date().getTime());
		
		try {
			try {
				
					fileIn = new BufferedReader(new FileReader(this.percorsoFiles+file));
					String currentLine=null;
					while ((currentLine = fileIn.readLine()) != null)
					{
						List<String> valori = Arrays.asList(currentLine.split("\\|", -1));
						String dtExport = valori.get(3);
						log.debug("dtexport="+ dtExport);
						if (dtExport!=null && dtExport.length()<=10)
							dtExport = dtExport + " 00:00";
						dataExport = (Timestamp)TypeFactory.getType(dtExport, "java.sql.Timestamp");
						try {
							String versione = valori.get(4);
							log.debug("versione="+ versione);
							versioneTracciato = versione;
						} catch (Exception e) {
							versioneTracciato = null;
						}
						try { 
							String provenienza = valori.get(5);
							log.debug("provenienza="+ provenienza);
							provenienzaDato = provenienza;
						} catch (Exception e) {
							provenienzaDato = null;
						}

						break;
					}
				
			} catch (Exception e) {
				log.error("Errore cercando di recuperare la data di elaborazione del file",e);
				throw new RulEngineException("Errore cercando di recuperare la data di elaborazione del file",e);
			} finally {
				if (fileIn!=null)
					fileIn.close();
			}

			return new TestataStandardFileBean(dataExport, versioneTracciato, provenienzaDato==null?getProvenienzaDefault():provenienzaDato);

		} catch (Exception e) {
			log.error("Errore cercando di recuperare la data di elaborazione del file",e);
			throw new RulEngineException("Errore cercando di recuperare la data di elaborazione del file",e);
		}
		

		
	}


	/**
	 * @return versione del tracciato , analizzata attraverso il metodo preProcessing
	 * @throws RulEngineException
	 */
	public String getVersioneTracciato() throws RulEngineException {
		return env.getTestata().getVersioneTracciato();

	}

	public  abstract void preProcesingSpec(Connection con) throws RulEngineException;

	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		this.preProcesingSpec(con);
		
	}
	


	

	
}
