package it.webred.ct.rulengine.scheduler.bean.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.webred.ct.rulengine.scheduler.bean.ProcessService;
import it.webred.ct.rulengine.scheduler.exception.DummyProcessException;
import it.webred.ct.rulengine.scheduler.exception.ProcessNotAllowedException;
import it.webred.ct.rulengine.scheduler.exception.ProcessServiceException;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.REventiLaunch;
import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;
import it.webred.ct.rulengine.controller.model.RTracciaDate;
import it.webred.ct.rulengine.controller.model.RTracciaDatePK;
import it.webred.ct.rulengine.controller.model.RTracciaStati;
import it.webred.ct.rulengine.controller.model.RTracciaStatiPK;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.service.ControllerBaseService;
import it.webred.ct.rulengine.service.bean.EventLaunchService;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.service.bean.MainControllerService;


import it.webred.ct.rulengine.utilities.ProcessUtilitiesService;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RejectAck;
import it.webred.rulengine.brick.bean.RunningAck;
import it.webred.rulengine.brick.bean.WarningAck;
import it.webred.rulengine.dto.ConfigurazioneEnte;
import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.entrypoint.impl.JellyLauncher;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;


/**
 * Session Bean implementation class ProcessServiceBean
 */
@Stateless
public class ProcessServiceBean extends ControllerBaseService implements ProcessService {
	
	@Resource
    TimerService timerService;
		
	
	@EJB
	private MainControllerService mainControllerService;
	
	@EJB
	private VerificaInitProcessService verificaInitProcessService;
	
	/*@EJB
	private TracciaStatiService tracciaStatiService;
	
	@EJB
	private EventLaunchService eventLaunchService;*/
	
	@EJB
	private ProcessUtilitiesService processUtilitiesService;

	/**
     * Default constructor. 
     */
    public ProcessServiceBean() {

    }

    
	public Task scheduleTask(Task taskParam) throws Exception {
		
		try {
			logger.debug("Schedule task. Profile ["+taskParam.getDescription()+"]");
			taskParam.setStartTime(Calendar.getInstance().getTime());
			
			RCommand rCommand = (RCommand)taskParam.getFreeObj();
			
			//controllo tipologia comando
			switch(rCommand.getRCommandType().getId().intValue()) {
				case 10:
				case 20: {
					/*
					 * Si tratta di un processo di REPERIMENTO(10) o ACQUISIZIONE(20)
					 */
					//impostazione id task
					taskParam.setTaskId(taskParam.getBelfiore()+"::"+taskParam.getIdFonte().intValue());
					logger.debug("Task ["+taskParam.getTaskId()+"]");
		
					//controlli incrociati su processo chiamato
					taskParam = verificaInitProcessService.verificaInizialeProcesso(taskParam);
					break;
				}
				case 31:
				case 32:
				case 33:
				case 34:
				case 35:
				case 36:
				case 37: {
					/*
					 * Si tratta di un processo di trattamento di tipo CORRELAZIONE (33),CONFRONTO(32)
					 * ELABORAZIONE(34) o CONTROLLO(31) e altri tarttamenti
					 */
					//impostazione id task
					taskParam.setTaskId(taskParam.getBelfiore()+"::"+rCommand.getCodCommand());
					logger.debug("Task ["+taskParam.getTaskId()+"]");
					
					//controlli incrociati su processo chiamato
					taskParam = verificaInitProcessService.verificaInizialeProcessiTrattamento(taskParam);
					break;
				}
				default: {
					//do nothing
					break;
				}
			}
			
			
			
			//controllo su oggetto restituito: se negativo  togliere il command da freeObject e metter un oggetto Ack
			if(!taskParam.isProcessable()) {
				CommandAck ack = new WarningAck(taskParam.getStatus());
				taskParam.setAck(ack);
			}
			else {
				String processId = taskParam.getTaskId()+"@"+Calendar.getInstance().getTimeInMillis();
				logger.debug("Process ID: "+processId);
				taskParam.setProcessId(processId);
				//recupero stato iniziale dell'operazione corrente
				RAnagStati stato = mainControllerService.getRAnagStato(taskParam.getIdTipologia(), "I");
				//aggiornamento stato iniziale operazione
				
				processUtilitiesService.aggiornamentoMonitor(rCommand, taskParam, stato);
				
				//this.aggiornamentoMonitor(taskParam,rCommand,processId,stato);
				//aggiornamento traccia stati
				//this.aggiornamentoTracciaStato(taskParam, stato);
			}
			
			//gestione time scheduler
			timerService.createTimer(taskParam.getStartTime(), taskParam);
			logger.debug("Ente: "+taskParam.getBelfiore());
			
		}catch(Throwable t) {
			logger.error(t.getMessage(),t);
		    throw new ProcessServiceException(t);
		}
		
		return taskParam;
	}
    
    
	@Timeout     
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void execute(Timer timer) {
		logger.debug("Checking ...");
		
		Task task = (Task) timer.getInfo();
		RAnagStati stato = null;
		
		try {
			//se il processo nn è eseguibile throw new ProcessNotAllowedException
			if(!task.isProcessable()) {
				throw new ProcessNotAllowedException(new Throwable(task.getStatus()));
			}
				
			logger.debug("Checking and executing task");
			task.setStatus("In esecuzione");
			logger.debug("ID FONTE DATI: "+task.getIdFonte());
			
			//l'oggetto comando viene recuperato da task
			RCommand rCommand = (RCommand)task.getFreeObj();
			
			if(rCommand.getSystemCommand().intValue() == 3) {
				throw new DummyProcessException("Processo DUMMY, impostazione stato finale",
						rCommand.getRCommandType().getId());
			}
						
			//recupero parametri delle nete
			logger.debug("Recupero configurazione ente");
			List parametriConfigurazioneEnte = this.getEnteConfig(task.getBelfiore());
			List enteFonteDati = this.getEnteFonteDati(task.getBelfiore());
			ConfigurazioneEnte configurazioneEnte = new ConfigurazioneEnte(parametriConfigurazioneEnte,enteFonteDati);
			
			//il launcher ritorna un Ack
			Launcher l = new JellyLauncher(task.getBelfiore(),task.getIdFonte(),configurazioneEnte);
			CommandAck cAck = l.executeCommand(rCommand.getCodCommand(), task.getProcessId());
			
			task.setStatus("Terminato");
			task.setEndTime(new Date());
			logger.debug("Task terminato, controllare eventuali segnalazioni");
			
			//test dell Ack ed evntuale chiamata a ejb che gestisce  gli eventi passandogli l'oggetto task
			if(cAck instanceof ApplicationAck) {
				//recupero stato finale dell'operazione corrente
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "F");
			}
			else if(cAck instanceof RunningAck) {
				//se si tratta di un ack di un processo che sta girando nn aggiorno lo stato
				stato = null;
			}
			else if(cAck instanceof NotFoundAck) {
				logger.debug("Ack  con segnalazioni!!!");
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "W");
			}
			else {
				//esito ko
				logger.debug("Ack negativo !!!");
				stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "E");
			}
		}catch(DummyProcessException dpe){
			task.setStatus("Processo DUMMY eseguito");
			stato = mainControllerService.getRAnagStato(dpe.getCommandType(), "F");
		}catch(ProcessNotAllowedException pnae) {
			logger.debug(task.getStatus());
		}catch(Exception e) {
			task.setStatus("Errore applicativo imprevisto !!");
			stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "E");
			logger.error(e.getMessage(),e);
		}finally {
			try {
				//se il porcesso è "not allowed" non aggiorno tabelle
				if(task.isProcessable() && stato != null) {
					
					processUtilitiesService.aggiornaEsito((RCommand)task.getFreeObj(), task, stato);
					
				/*	//aggiornamento stato finale operazione
					this.aggiornamentoMonitor(task,(RCommand)task.getFreeObj(),task.getProcessId(),stato);
					//aggiornamento traccia stati
					this.aggiornamentoTracciaStato(task, stato);
					//aggiornamento traccia date
					this.aggiornamentoTracciaDate(task);
					//interrogazione registro eventi collegati
					this.aggiornamentoEventiLaunch(task, (RCommand)task.getFreeObj(), stato);*/
				}
			}catch(Exception e) {
				logger.error("Errore aggiornamento monitor: " + e.getMessage(),e);
			}
		}
	}


	
	
	/*	private void aggiornamentoMonitor(Task task, RCommand rCommand,String processId,RAnagStati stato) throws Exception {
		
		RProcessMonitor rpm = new RProcessMonitor();
		rpm.setId(new RProcessMonitorPK(task.getBelfiore(),rCommand.getId()));
		rpm.setIstante(task.getStartTime().getTime());
		rpm.setProcessid(processId);
		rpm.setRCommand(rCommand);
		rpm.setRAnagStato(stato);
		processMonitorService.saveOrUpdateProcessInstance(rpm);
		logger.debug("Processo aggiornato nel monitor");
	}
	
	private void aggiornamentoTracciaStato(Task task,RAnagStati stato) throws Exception {
		if(task.getIdTipologia().longValue() == 10 || 
		   task.getIdTipologia().longValue() == 20) {
			
			RTracciaStati rts = new RTracciaStati();
			rts.setId(new RTracciaStatiPK(task.getBelfiore(),task.getIdFonte(),Calendar.getInstance().getTimeInMillis()));
			rts.setRAnagStati(stato);
			rts.setNote(stato.getDescr());
			rts.setProcessid(task.getProcessId());
			tracciaStatiService.saveTracciaStato(rts);
			logger.debug("Traccia stato salvata");
		}
	}
	
	private void aggiornamentoTracciaDate(Task task) throws Exception {
		if(task.getIdTipologia().longValue() == 10 || 
		   task.getIdTipologia().longValue() == 20) {
			
			Date min = tracciaStatiService.getMinTracciaForniture(task.getBelfiore(),task.getIdFonte());
			if(min != null){
				Date max = tracciaStatiService.getMaxTracciaForniture(task.getBelfiore(),task.getIdFonte());

				boolean nuovo = false;
				RTracciaDate rtd = tracciaStatiService.getTracciaDate(task.getBelfiore(),task.getIdFonte());
				if(rtd == null){
					nuovo = true;
					rtd = new RTracciaDate();
					rtd.setId(new RTracciaDatePK(task.getBelfiore(),task.getIdFonte()));
				}
				rtd.setDatamin(min);
				rtd.setDatamax(max);
				
				if(nuovo)
					tracciaStatiService.saveTracciaDate(rtd);
				else tracciaStatiService.updateTracciaDate(rtd);
				
				logger.debug("Traccia date salvata");
			}
		}
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
		logger.debug("Evento segnalato");
	}
	*/
    
	private List getEnteConfig(String belfiore) throws Exception {
		return parameterService.getAmKeyValueExtByComune(belfiore);
	}
	
	private List getEnteFonteDati(String belfiore) throws Exception {
		return comuneService.getListaFonteByComune(belfiore);
	}
	
	
}
