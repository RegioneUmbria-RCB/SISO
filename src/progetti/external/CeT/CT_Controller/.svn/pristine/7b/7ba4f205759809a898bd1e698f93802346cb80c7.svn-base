package it.webred.ct.rulengine.queue.job.thread;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.queue.job.thread.exception.QueueJobThreadException;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.QueueService;
import it.webred.ct.rulengine.service.bean.SchedulerTimeService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.utilities.ProcessUtilitiesService;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RunningAck;
import it.webred.rulengine.dto.ConfigurazioneEnte;
import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.entrypoint.impl.JellyLauncher;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;



/**
 * Thread di lancio catena jelly tramite Launcher
 * 
 */
public class QueueJobThread implements Runnable {
	private Logger log = Logger.getLogger(QueueJobThread.class.getName());
	
	private VerificaInitProcessService verificaInitProcessService;
	private MainControllerService mainControllerService;
	private QueueService queueService;
	private ComuneService comuneService; 
	private ParameterService parameterService;
	private SchedulerTimeService schedulerTimeService;
	private ProcessUtilitiesService processUtilitiesService;
	
	private Task task;
	
	private Long idSched;
	
	public QueueJobThread(Task task) {
		super();
		this.task = task;
		
		verificaInitProcessService = 
				(VerificaInitProcessService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "VerificaInitProcessServiceBean");
			
		mainControllerService = 
				(MainControllerService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
		
		queueService = 
			(QueueService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "QueueServiceBean");
		
		parameterService = 
				(ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			
		comuneService = 
				(ComuneService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
		
		schedulerTimeService = 
			(SchedulerTimeService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "SchedulerTimeServiceBean");
		
		processUtilitiesService = 
				(ProcessUtilitiesService)ServiceLocator.getInstance().getService("CT_Controller" ,"CT_Controller_EJB", "ProcessUtilitiesServiceBean");
	}

	public void run() {
		log.info("#### [THREAD] Inizio analisi ed esecuzione Job");
		
		try {
			log.debug("Verifiche pre esecuzione processo");
			
			RCoda rCoda = (RCoda)task.getFreeObj();
			this.verificaPreEsecuzione(rCoda);
			
			if(!task.isProcessable()) {				
				log.info("Refresh informazioni job in coda: JOB RESUMABLE");
				rCoda.setInizioEsecuzione(null);
				queueService.saveOrUpdateProcess(rCoda);
				throw new QueueJobThreadException(task.getStatus(),null);
			}
			else {
				String processId = task.getTaskId()+"@"+rCoda.getIstante();
				log.debug("Process ID: "+processId);
				task.setProcessId(processId);
				
				//recupero stato iniziale dell'operazione corrente
				RAnagStati stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "I");
				
				//aggiornamento stato iniziale operazione
				processUtilitiesService.aggiornamentoMonitor(rCoda.getRCommand(),task,stato);
				//aggiornamento traccia stati
				//this.aggiornamentoTracciaStato(task, stato);
				
				//recupero istanza scheduler chiamante
				RSchedulerTime rst =  schedulerTimeService.getSchedulerProcess(rCoda.getJobname());
				this.idSched = rst.getId();
				
				log.debug("Ente: "+task.getBelfiore());
				execute(rCoda.getRCommand());
			}
			
		}catch(QueueJobThreadException qe) {
			log.error(qe.getMessage());
		}catch(Exception ex) {
			log.error("Thread Job in eccezione: "+ex.getMessage(),ex);
		}
	}
	
	
	
	
	private void execute(RCommand rCommand) throws Exception {
		RAnagStati stato = null;
		
		try {
			log.debug("QueueJobThreads.execute - ID FONTE DATI: "+task.getIdFonte());
			
			if(rCommand.getSystemCommand().intValue() == 3) {
				throw new QueueJobThreadException("Processo DUMMY, impostazione stato finale",
						rCommand.getRCommandType().getId());
			}
			
			//recupero parametri delle nete
			log.debug("Recupero configurazione ente");
			List parametriConfigurazioneEnte = this.getEnteConfig(task.getBelfiore());
			List enteFonteDati = this.getEnteFonteDati(task.getBelfiore());
			ConfigurazioneEnte configurazioneEnte = 
				new ConfigurazioneEnte(parametriConfigurazioneEnte,enteFonteDati);
			
			//impostazione id processo cheduler chiamante
			configurazioneEnte.setIdSched(this.idSched);
			
			//il launcher ritorna un Ack
			Launcher l = new JellyLauncher(task.getBelfiore(),task.getIdFonte(),configurazioneEnte);
			CommandAck cAck = l.executeCommand(rCommand.getCodCommand(), task.getProcessId());
			//CommandAck cAck = new ApplicationAck("Test: esito ok");
			
			task.setStatus("Terminato");
			task.setEndTime(new Date());
			log.debug("Task terminato, controllare eventuali segnalazioni");
			
			//aggiornamento coda
			RCoda rCoda = (RCoda)task.getFreeObj(); 
			
			if(cAck instanceof ApplicationAck) {
				//recupero stato finale dell'operazione corrente
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "F");
				queueService.deleteProcess(rCoda);
				log.debug("Job "+rCoda.getJobname()+" rimosso dalla coda [esito positivo]");
			}
			else if(cAck instanceof RunningAck) {
				stato = null;
			}
			else if(cAck instanceof NotFoundAck) {
				log.debug("Ack  con segnalazioni!!!");
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "W");
				queueService.deleteProcess(rCoda);
				log.debug("Job "+rCoda.getJobname()+" rimosso dalla coda [esito positivo con segnalazione]");
			}
			else {
				//esito ko
				log.debug("Ack negativo !!!");
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "E");
				queueService.deleteProcess(rCoda);
				log.debug("Job "+rCoda.getJobname()+" rimosso dalla coda [esito negativo]");
			}
			
		}catch(QueueJobThreadException qe) {
			task.setStatus("Processo DUMMY eseguito");
			//gestione processo DUMMY
			
			stato = mainControllerService.getRAnagStato(qe.getCommandType(), "F");
		}catch(Exception ex) {
			task.setStatus("Errore applicativo imprevisto !!");
			stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "E");
			log.error("Thread Job in eccezione: "+ex.getMessage(),ex);
		}
		finally {
			try {
				//se il porcesso è "not allowed" non aggiorno tabelle
				if(task.isProcessable() && stato != null) {
				
					//aggiornamento stato finale operazione
					//aggiornamento traccia stati
					//aggiornamento traccia date
					//interrogazione registro eventi collegati
					processUtilitiesService.aggiornaEsito(rCommand, task, stato);
					
				}
			}catch(Exception e) {
				log.error("Errore aggiornamento monitor: "+e.getMessage(),e);
			}
		}
	}
	
	 
	private void verificaPreEsecuzione(RCoda rCoda) throws Exception {
		
		RCommand rCommand = rCoda.getRCommand();
		
		//copia oggetto task per la verifica
		Task taskVerifica = task.getCopy();
		taskVerifica.setFreeObj(rCommand);
		
		switch(rCommand.getRCommandType().getId().intValue()) {
			case 10:
			case 20: {
				log.debug("Analisi comando di reperimento/acquisizione");
				task.setTaskId(task.getBelfiore()+"::"+task.getIdFonte().intValue());
				//controlli incrociati su processo chiamato
				taskVerifica = verificaInitProcessService.verificaInizialeProcesso(taskVerifica);
				break;
			}
			case 31:
			case 32:
			case 33:
			case 34: 
			case 35:
			case 36:
			case 37:{
				log.debug("Analisi comando di trattamento");
				task.setTaskId(task.getBelfiore()+"::"+rCommand.getCodCommand());
				//controlli incrociati su processo chiamato
				taskVerifica = verificaInitProcessService.verificaInizialeProcessiTrattamento(taskVerifica);
				break;
			}
			default: {
				//do nothing
				break;
			}
		}
		
		task.setProcessable(taskVerifica.isProcessable());
		task.setStatus(taskVerifica.getStatus());
		
	}
	
	private List getEnteConfig(String belfiore) throws Exception {
		return parameterService.getAmKeyValueExtByComune(belfiore);
	}
	
	private List getEnteFonteDati(String belfiore) throws Exception {
		return comuneService.getListaFonteByComune(belfiore);
	}
}
