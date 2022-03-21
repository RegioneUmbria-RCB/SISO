package it.webred.ct.rulengine.web.bean.diag.command.thread;

import java.util.Date;
import java.util.List;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.REventiLaunch;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;

import it.webred.ct.rulengine.dto.Task;

import it.webred.ct.rulengine.service.bean.EventLaunchService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.web.bean.diag.command.thread.exception.DiagnosticCommandException;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RunningAck;
import it.webred.rulengine.dto.ConfigurazioneEnte;
import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.entrypoint.impl.JellyLauncher;

import org.apache.log4j.Logger;

public class DiagnosticCommandThread implements Runnable {
	
	private Logger log = Logger.getLogger(DiagnosticCommandThread.class.getName());

	
	private VerificaInitProcessService verificaInitProcessService;
	private MainControllerService mainControllerService;
	private ProcessMonitorService processMonitorService;
	private ComuneService comuneService; 
	private ParameterService parameterService;
	private EventLaunchService eventLaunchService;
	
	private Task task;
	
	public DiagnosticCommandThread(Task task) {
		super();
		
		this.task = task;

		verificaInitProcessService = 
			(VerificaInitProcessService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "VerificaInitProcessServiceBean");
		
		mainControllerService = 
			(MainControllerService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
		
		processMonitorService = 
			(ProcessMonitorService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "ProcessMonitorServiceBean");
		
		parameterService = 
			(ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
		
		comuneService = 
			(ComuneService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
		
		eventLaunchService = 
			(EventLaunchService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "EventLaunchServiceBean");
	}

	public void run() {
		log.info("#### [THREAD] Inizio esecuzione lancio diagnostica");
		
		try {
			log.debug("Verifiche pre esecuzione processo");
			RCommand rCommand = (RCommand)task.getFreeObj();
			this.verificaPreEsecuzione(rCommand);
			
			if(!task.isProcessable()) {
				log.info("Comando non eseguibile al momento !!");
			}
			else {
				String processId = task.getTaskId()+"@"+task.getStartTime().getTime();
				log.debug("Process ID: "+processId);
				task.setProcessId(processId);
				
				//recupero stato iniziale dell'operazione corrente
				RAnagStati stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "I");
				
				//aggiornamento stato iniziale operazione
				this.aggiornamentoMonitor(rCommand,processId,stato);
				
				log.debug("Ente: "+task.getBelfiore());
				
				execute(rCommand);
			}
		}catch(Exception ex) {
			log.error("Thread Job in eccezione: "+ex.getMessage(),ex);
		}
	}
	
	
	private void execute(RCommand rCommand) throws Exception {
		RAnagStati stato = null;
		
		try {
			log.debug("ID FONTE DATI: "+task.getIdFonte());
			
			if(rCommand.getSystemCommand().intValue() == 3) {
				throw new DiagnosticCommandException("Processo DUMMY, impostazione stato finale",
						rCommand.getRCommandType().getId());
			}
			
			//recupero parametri delle nete
			log.debug("Recupero configurazione ente");
			List parametriConfigurazioneEnte = this.getEnteConfig(task.getBelfiore());
			List enteFonteDati = this.getEnteFonteDati(task.getBelfiore());
			ConfigurazioneEnte configurazioneEnte = 
				new ConfigurazioneEnte(parametriConfigurazioneEnte,enteFonteDati);
			
			//il launcher ritorna un Ack
			Launcher l = new JellyLauncher(task.getBelfiore(),task.getIdFonte(),configurazioneEnte);
			CommandAck cAck = l.executeCommand(rCommand.getCodCommand(), task.getDiagParams() ,task.getProcessId());
			task.setStatus("Terminato");
			task.setEndTime(new Date());
			log.debug("Task terminato, controllare eventuali segnalazioni");
			
			if(cAck instanceof ApplicationAck) {
				//recupero stato finale dell'operazione corrente
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "F");
			}
			else if(cAck instanceof RunningAck) {
				stato = null;
			}
			else if(cAck instanceof NotFoundAck) {
				log.debug("Ack  con segnalazioni!!!");
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "W");
			}
			else {
				//esito ko
				log.debug("Ack negativo !!!");
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "E");
			}
		}catch(DiagnosticCommandException e) {
			task.setStatus("Processo DUMMY eseguito");
			//gestione processo DUMMY
			stato = mainControllerService.getRAnagStato(e.getCommandType(), "F");
		}catch(Exception ex) {
			task.setStatus("Errore applicativo imprevisto !!");
			stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "E");
			log.error("Thread Event Command in eccezione: "+ex.getMessage(),ex);
		}finally {
			try {
				//se il porcesso è "not allowed" non aggiorno tabelle
				if(task.isProcessable() && stato != null) {
					//aggiornamento stato finale operazione
					this.aggiornamentoMonitor(rCommand,task.getProcessId(),stato);	
					//interrogazione registro eventi collegati
					this.aggiornamentoEventiLaunch(task, (RCommand)task.getFreeObj(), stato);
				}
			}catch(Exception e) {
				log.error("Errore aggiornamento monitor: "+e.getMessage(),e);
			}
		}
	}
	
	private void verificaPreEsecuzione(RCommand rCommand) throws Exception {
		
		//copia oggetto task per la verifica
		Task taskVerifica = task.getCopy();
		taskVerifica.setFreeObj(rCommand);
		
		switch(rCommand.getRCommandType().getId().intValue()) {
			
			case 31:
			case 32: {
				log.debug("Analisi comando di trattamento");
				task.setTaskId(task.getBelfiore()+"::"+rCommand.getCodCommand());
				//controlli incrociati su processo chiamato
				taskVerifica = verificaInitProcessService.verificaInizialeProcessiTrattamento(taskVerifica);
				break;
			}
			default: {
				taskVerifica.setProcessable(true);
				taskVerifica.setStatus("");
				break;
			}
		}
		
		task.setProcessable(taskVerifica.isProcessable());
		task.setStatus(taskVerifica.getStatus());
	}
	
	
	
	private void aggiornamentoMonitor(RCommand rCommand,String processId,RAnagStati stato) throws Exception {
		
		RProcessMonitor rpm = new RProcessMonitor();
		rpm.setId(new RProcessMonitorPK(task.getBelfiore(),rCommand.getId()));
		rpm.setIstante(task.getStartTime().getTime());
		rpm.setProcessid(processId);
		rpm.setRCommand(rCommand);
		rpm.setRAnagStato(stato);
		processMonitorService.saveOrUpdateProcessInstance(rpm);
		log.debug("Processo aggiornato nel monitor");
	}
	
	
	private void aggiornamentoEventiLaunch(Task task, RCommand rCommand,RAnagStati stato) throws Exception {
		
		REventiLaunch rel = new REventiLaunch();
		rel.setBelfiore(task.getBelfiore());
		rel.setIstante(task.getStartTime().getTime());
		rel.setCommandId(rCommand.getId());
		rel.setCommandType(rCommand.getRCommandType().getId());
		rel.setIdFonte(task.getIdFonte());
		rel.setCommandStato(stato.getId());
		eventLaunchService.saveREventoLaunch(rel);
		log.info("Evento segnalato");
	}
	
	
	private List getEnteConfig(String belfiore) throws Exception {
		return parameterService.getAmKeyValueExtByComune(belfiore);
	}
	
	private List getEnteFonteDati(String belfiore) throws Exception {
		return comuneService.getListaFonteByComune(belfiore);
	}
}
