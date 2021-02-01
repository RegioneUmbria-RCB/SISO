package it.webred.ct.rulengine.job;

import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.service.bean.QueueService;
import it.webred.rulengine.ServiceLocator;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * La classe rappresenta il Job di un processo (o comando) schedulato 
 * lanciato dallo schedulatore, e si occupa di inserirsi in coda secondo
 * i tempi di esecuzioni configurati.
 */
public class REJob implements Job {
	
	private Logger log = Logger.getLogger(REJob.class.getName());
	
	private QueueService queueService;
	
	
	/**
	 * Il metodo inserisce l'istanza del Job in coda
	 * 
	 */
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		
		log.debug("[JOB] Registrazione Job in coda");
		
		try {
			String instName = ctx.getJobDetail().getKey().getName();
			log.debug("Nome istanza: "+instName);
			String instGroup = ctx.getJobDetail().getKey().getGroup();
    	    log.debug("Gruppo istanza: "+instGroup);
    	    
    	    JobDataMap dataMap = ctx.getJobDetail().getJobDataMap();
    	    String belfiore = dataMap.getString("belfiore");
    	    log.debug("Ente: "+belfiore);
    	    RCommand rCommand = (RCommand)dataMap.get("rCommand");
    	    log.debug("Comando da inserire nella coda: "+rCommand.getCodCommand());
    	    Long istante = 
    	    	Calendar.getInstance().getTimeInMillis() + (new Double(Math.random() * 10).intValue());
    	    log.debug("Istante di esecuzione "+istante);
    	    
    	    log.debug("Preparazione inserimento processo nella coda di esecuzione");
    	    RCoda rCoda = new RCoda();
    	    
    	    rCoda.setIstante(istante);
    	    rCoda.setBelfiore(belfiore);
    	    rCoda.setJobname(instName);
    	    rCoda.setMaxTentativi(dataMap.getLong("maxtentativi"));
    	    rCoda.setRCommand(rCommand);
    	    
    	    log.debug("Inserimento processo in coda");
    	    queueService = (QueueService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "QueueServiceBean");
    	    queueService.saveProcess(rCoda);
    	    
    	    log.debug("Job inserito in coda");
    	    
		}catch(Exception e ) {
			log.error("Eccezione: "+e.getMessage(),e);
		}
	}

}
