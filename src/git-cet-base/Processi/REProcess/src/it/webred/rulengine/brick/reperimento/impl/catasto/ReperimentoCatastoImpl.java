package it.webred.rulengine.brick.reperimento.impl.catasto;

import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.reperimento.AbstractReperimentoCommand;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.io.File;
import java.sql.Connection;
import java.util.Properties;

import com.abaco.sigmater.download.FileDownloader;
import com.abaco.sigmater.download.exceptions.DuplicatedFileException;
import com.abaco.sigmater.download.exceptions.FornituraNotFoundException;
import com.abaco.sigmater.download.exceptions.InvalidSmimeException;
import com.abaco.sigmater.download.exceptions.PathAlreadyExistsException;
import com.abaco.sigmater.download.exceptions.ZipWithFoldersException;

public class ReperimentoCatastoImpl extends AbstractReperimentoCommand implements Rule {

	//nome della proprietà di configurazione
	public static final String DIR_FILE_KEY = "dir.files";
	
	public ReperimentoCatastoImpl(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CommandAck doReperimento(String belfiore, Long idFonte, String reProcessId, Context ctx) throws CommandException {
		log.info("Reperimento Catasto da STAGING...");
		
		CommandAck ack = null;
		
		//recupero del path locale dell ente/fd
		ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune(DIR_FILE_KEY, belfiore, idFonte.toString());
		log.info("Directory reperimento file: "+param.getValueConf());
		
		//controllo presenza fd
		File f = new File(param.getValueConf());
		if(f.exists()) {
			if(f.isDirectory()) {
				
				
				//Richiamo la procedura di abaco che estrae i file dal db e salva i file.
				
				Connection conn = ctx.getConnection("STAGING_"+belfiore);
			
				 FileDownloader fd = new FileDownloader(conn);
				 String message = null;
				 try
				 {		
				        fd.process(belfiore, param.getValueConf());
				 
				
						File[] ff = f.listFiles();
						if(ff.length == 0) {
							ack = new NotFoundAck("La directory non contiene file di fonte dati");
						}
						else {
							//controllo se tra gli oggetti file elencati c'è almeno un file di fonte dati
							boolean fdExist = false;
							for(File currF: ff) {
								if(currF.isFile()) {
									fdExist = true;
									break;
								}
							}
							
							if(fdExist) 
								ack = new ApplicationAck("Fonte dati presente");
							else 
								ack = new NotFoundAck("La directory non contiene file di fonte dati");
							
						}
				
				 }catch (DuplicatedFileException e){
					 
					 message = "File duplicati all'interno dei file .zip";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					 
				 }catch (FornituraNotFoundException e){
					 
					 message = "Nessun nuovo file disponibile per il download";
					 log.error(message, e);
					 
					 ack = new NotFoundAck(message); 
					 
				 }catch (java.sql.SQLException e){
					 
					 message = "Eccezioni di accesso al db";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					 
				 }catch (javax.xml.bind.JAXBException e){
					 
					 message = "Errori di parsing del file indice del file repository (index.xml)";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
				 
				 }catch (java.text.ParseException e){
					 
					 message = "Errori di parsing del file indice del file repository (index.xml).";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					 
				 }catch (PathAlreadyExistsException e){
					 
					 message = "Cartelle già esistenti nel file repository";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					 
				 }catch (ZipWithFoldersException e){
					 
					 message = "File zip contenenti percorsi";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					
				 }catch (InvalidSmimeException e){
					 
					 message = "Non è possibile estrarre il file .xml dal file .smime";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					 
				 }catch (java.io.IOException e){
					 
					 message = "Errore generico di accesso al file system";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					 
				 }catch (Exception e){
					 
					 message = "Errore generico di esecuzione";
					 log.error(message, e);
					 
					 ack = new ErrorAck(message);
					 
				 }
				
			}
			else {
				ack = new ErrorAck(DIR_FILE_KEY+" non è una directory valida");
			}
		}
		else {
			ack = new ErrorAck("Directory non esiste");
		}
				
		return ack;
	}

}
