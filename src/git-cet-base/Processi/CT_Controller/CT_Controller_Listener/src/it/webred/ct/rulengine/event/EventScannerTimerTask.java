package it.webred.ct.rulengine.event;


import it.webred.ct.rulengine.controller.model.RAnagEventi;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommandType;
import it.webred.ct.rulengine.controller.model.REventiLaunch;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.event.command.thread.EventCommandThread;
import it.webred.ct.rulengine.service.bean.EventLaunchService;
import it.webred.ct.rulengine.service.bean.EventService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.rulengine.ServiceLocator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class EventScannerTimerTask extends TimerTask {

	private static Logger log = Logger.getLogger(EventScannerTimerTask.class.getName());
	
	private EventLaunchService eventLaunchService;
	
	private EventService eventService;
	
	private RecuperaComandoService recuperaComandoService;
	
	
	public EventScannerTimerTask() {
		super();

		eventLaunchService = 
			(EventLaunchService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "EventLaunchServiceBean");
		
		eventService = 
			(EventService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "EventServiceBean");
		
		recuperaComandoService = 
			(RecuperaComandoService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");
	}

	@Override
	public void run() {
		log.debug("Event scanner: task di gestione eventi");
		
		try {
			log.debug("Recupero elenco eventi segnalati");
			List<REventiLaunch> rEventi = eventLaunchService.getREventiLaunch();
			
			if(rEventi != null) {
				for(REventiLaunch rel: rEventi) {
					
					//individuare le casistiche di appartenenza della singola istanza di evento segnalata
					List<RAnagEventi> nexts = this.findNextCommands(rel);
					log.debug("Torvati "+nexts.size()+" comandi da lanciare dopo il comando "+rel.getCommandId()+
							  " per l'ente "+rel.getBelfiore());

					for(RAnagEventi item: nexts) {
						
						/*
						 * Gli oggetti RAnagEventi recuperati nn contengono le info complete del
						 * comando che ha scatenato l'evento, quindi vanno impostate.
						 */

						if(item.getRCommandType() == null) {
							RCommandType rct = new RCommandType();
							rct.setId(rel.getCommandType());
							item.setRCommandType(rct);
						}
							
						if(item.getAfterIdFonte() == null)
							item.setAfterIdFonte(rel.getIdFonte());
						
						if(item.getAfterCommand() == null)
							item.setAfterCommand(rel.getCommandId());
						
						if(item.getRAnagStati() == null) {
							RAnagStati ras = new RAnagStati();
							ras.setId(rel.getCommandStato());
							item.setRAnagStati(ras);
						}
						
						this.launchThread(item,rel.getBelfiore(),rel.getIstante());
					}
					
					//cancellazione istanza di evento
					eventLaunchService.deleteREventoLaunch(rel);
					log.debug("Istanza evento con ID "+rel.getIstante()+" eliminata");
				}
			}
			
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
		}
	}
	
	
	private List<RAnagEventi> findNextCommands(REventiLaunch rel) throws Exception {
		List<RAnagEventi> nexts = new ArrayList<RAnagEventi>();
		
		try {
			List<RAnagEventi> classeA = eventService.getEventiClasseA(rel);
			if(classeA != null) 
				nexts.addAll(classeA);
			
			List<RAnagEventi> classeB = eventService.getEventiClasseB(rel);
			if(classeB != null) 
				nexts.addAll(classeB);
			
			List<RAnagEventi> classeC = eventService.getEventiClasseC(rel);
			if(classeC != null) 
				nexts.addAll(classeC);
			
			List<RAnagEventi> classeD = eventService.getEventiClasseD(rel);
			if(classeD != null) 
				nexts.addAll(classeD);
			
			List<RAnagEventi> classeE = eventService.getEventiClasseE(rel);
			if(classeE != null) 
				nexts.addAll(classeE);
			
			List<RAnagEventi> classeF = eventService.getEventiClasseF(rel);
			if(classeF != null) 
				nexts.addAll(classeF);
			
			List<RAnagEventi> classeG = eventService.getEventiClasseG(rel);
			if(classeG != null) 
				nexts.addAll(classeG);
			
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage());
			throw e;
		}
		
		return nexts;
	}
	
	
	private void launchThread(RAnagEventi item,String belfiore,Long istante) throws Exception {
		
		try {
			log.info("Preparazione parametro task per thread Event Command");
			Long idTipologia = item.getRCommand().getRCommandType().getId();
			Task t = new Task(belfiore,null,idTipologia);
			t.setFreeObj(item);
			
			//istante per startDate
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(istante);
			t.setStartTime(c.getTime());
			
			t.setDescription("Thread Command della coda eventi launch");
			
			//in base alla tipologia va recuperato o meno la FD
			switch(idTipologia.intValue()) {
				case 10:
				case 20: {
					List<RFontedatiCommand> fds =  recuperaComandoService.getRCommandFDs(item.getRCommand());
					log.debug("Fonti dati recuperata: "+fds.get(0).getId().getIdFonte());
					t.setIdFonte(fds.get(0).getId().getIdFonte());
					t.setTaskId(belfiore+"::"+t.getIdFonte());
					break;
				}
				default: {
					//do nothing
					t.setTaskId(belfiore+"::"+item.getRCommand().getCodCommand());
				}
			}
			
    	    //lancio thread che esegue il comando dell'evento
			log.info("Lancio thread Command della coda eventi launch");
			new Thread(new EventCommandThread(t)).start();
			log.info("Thread Event Command in esecuzione");
			
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage());
			throw e;
		}
	}
	
	
}
