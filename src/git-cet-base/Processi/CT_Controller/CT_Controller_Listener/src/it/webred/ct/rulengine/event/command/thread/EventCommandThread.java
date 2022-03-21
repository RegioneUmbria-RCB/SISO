package it.webred.ct.rulengine.event.command.thread;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.comune.ComuneService;
import it.webred.ct.rulengine.controller.model.RAnagEventi;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.event.command.thread.exception.EventCommandException;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.VerificaInitProcessService;
import it.webred.ct.rulengine.utilities.ProcessUtilitiesService;
import it.webred.rulengine.ServiceLocator;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.bean.RunningAck;
import it.webred.rulengine.diagnostics.dto.RCommandDTO;
import it.webred.rulengine.dto.ConfigurazioneEnte;
import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.entrypoint.impl.JellyLauncher;
import it.webred.rulengine.type.Variable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class EventCommandThread implements Runnable  {

	
	private Logger log = Logger.getLogger(EventCommandThread.class.getName());
	
	private VerificaInitProcessService verificaInitProcessService;
	private MainControllerService mainControllerService;
	private ComuneService comuneService; 
	private ParameterService parameterService;
	private RecuperaComandoService recuperaComandoService;
	private ProcessUtilitiesService processUtilitiesService;
	
	private Task task;
	
	public EventCommandThread(Task task) {
		super();
		this.task = task;

		verificaInitProcessService = 
				(VerificaInitProcessService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "VerificaInitProcessServiceBean");
			
		mainControllerService = 
				(MainControllerService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "MainControllerServiceBean");
				
		parameterService = 
				(ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			
		comuneService = 
				(ComuneService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ComuneServiceBean");
		
		recuperaComandoService = 
			(RecuperaComandoService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");
		
		processUtilitiesService = 
			(ProcessUtilitiesService)ServiceLocator.getInstance().getService("CT_Controller" ,"CT_Controller_EJB", "ProcessUtilitiesServiceBean");
		
	}

	public void run() {
		log.info("#### [THREAD] Inizio analisi ed esecuzione Event Command");
		
		try {
			log.debug("Verifiche pre esecuzione processo");
			
			RAnagEventi rae = (RAnagEventi)task.getFreeObj();
			this.verificaPreEsecuzione(rae);
			
			if(!task.isProcessable()) {
				log.info("Comando non eseguibile al momento !!");
				//resume nn gestita nel caso di eventi
			}
			else {
				String processId = task.getTaskId()+"@"+task.getStartTime().getTime();
				log.debug("Process ID: "+processId);
				task.setProcessId(processId);
				
				//recupero stato iniziale dell'operazione corrente
				RAnagStati stato = mainControllerService.getRAnagStato(task.getIdTipologia(), "I");
				//aggiornamento stato iniziale operazione
				
				processUtilitiesService.aggiornamentoMonitor(rae.getRCommand(),task,stato);
				
				log.debug("Ente: "+task.getBelfiore());
				execute(rae.getRCommand());
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
				throw new EventCommandException("Processo DUMMY, impostazione stato finale",
						rCommand.getRCommandType().getId());
			}
			
			//recupero parametri delle nete
			log.debug("Recupero configurazione ente");
			List parametriConfigurazioneEnte = this.getEnteConfig(task.getBelfiore());
			List enteFonteDati = this.getEnteFonteDati(task.getBelfiore());
			ConfigurazioneEnte configurazioneEnte = 
				new ConfigurazioneEnte(parametriConfigurazioneEnte,enteFonteDati);
			
			/*
			 * Nella List<Variable>, ovvero il param numero 4 del metodo executeCommand del jellyLauncher,
			 * va inserito l'oggetto comando da cui si proviene (o after command)
			 */
			log.debug("Preparazione variabile con oggetto comando 'after'");
			RAnagEventi rae = (RAnagEventi)task.getFreeObj();
			List<Variable> llvv = this.prepareAfterCommandInfo(rae);
			
			//il launcher ritorna un Ack
			Launcher l = new JellyLauncher(task.getBelfiore(),task.getIdFonte(),configurazioneEnte);
			CommandAck cAck = l.executeCommand(rCommand.getCodCommand(),null, task.getProcessId(),llvv,null);
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
		}catch(EventCommandException e) {
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
				System.out.println("_________ PROCESSABLE: " + task.isProcessable());
				System.out.println("_________ STATO: " + stato.getDescr());
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
	
	
	
	
	private void verificaPreEsecuzione(RAnagEventi rae) throws Exception {
		RCommand rCommand = rae.getRCommand();
		
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
				taskVerifica.setProcessable(true);
				taskVerifica.setStatus("");
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
	
	
	/**
	 * Il metodo prepara la lista con delle info relative al comando eseguito
	 * in modo da poterle utilizzare eventualmente nel comando next
	 */
	private List<Variable> prepareAfterCommandInfo(RAnagEventi rae) {
		List<Variable> vars = new ArrayList<Variable>();
		
		RCommand after = recuperaComandoService.getRCommand(rae.getAfterCommand());
		
		//variabile per codice comando
		RCommandDTO rCommandDTO = new RCommandDTO(after.getId(),
												  after.getCodCommand(),
												  after.getDescr(),
												  after.getLongDescr(),
												  after.getSystemCommand(),
												  after.getRCommandType().getId());
		vars.add(new Variable("event.after.command.object",RCommandDTO.class.getName(),rCommandDTO));
		
		return vars;
	}
	
}
