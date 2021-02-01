package it.webred.ct.rulengine.service.utilities;

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
import it.webred.ct.rulengine.service.bean.EventLaunchService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.utilities.ProcessUtilitiesService;
import it.webred.rulengine.ServiceLocator;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

@Stateless
public class ProcessUtilitiesServiceBean implements ProcessUtilitiesService {
	private Logger log = Logger.getLogger(ProcessUtilitiesServiceBean.class.getName());
	
	@EJB
	private TracciaStatiService tracciaStatiService;
	
	@EJB
	private EventLaunchService eventLaunchService;
	
	@EJB
	protected ProcessMonitorService processMonitorService;
	
	public void aggiornaEsito(RCommand rCommand,Task task,RAnagStati stato) throws Exception{
		
		//aggiornamento stato finale operazione
		this.aggiornamentoMonitor(rCommand,task,stato);	
		//aggiornamento traccia stati
		this.aggiornamentoTracciaStato(task, stato);
		//aggiornamento traccia date
		this.aggiornamentoTracciaDate(task);
		//interrogazione registro eventi collegati
		this.aggiornamentoEventiLaunch(task, rCommand, stato);
	}
	
	public void aggiornamentoMonitor(RCommand rCommand,Task task,RAnagStati stato) throws Exception {
		
		RProcessMonitor rpm = new RProcessMonitor();
		rpm.setId(new RProcessMonitorPK(task.getBelfiore(),rCommand.getId()));
		rpm.setIstante(task.getStartTime().getTime());
		rpm.setProcessid(task.getProcessId());
		rpm.setRCommand(rCommand);
		rpm.setRAnagStato(stato);
		processMonitorService.saveOrUpdateProcessInstance(rpm);
		log.debug("Processo aggiornato nel monitor");
	}

	public  void aggiornamentoTracciaStato(Task task,RAnagStati stato) throws Exception {
			
		if(task.getIdTipologia().longValue() == 10 || 
		   task.getIdTipologia().longValue() == 20) {
			
			RTracciaStati rts = new RTracciaStati();
			rts.setId(new RTracciaStatiPK(task.getBelfiore(),task.getIdFonte(),Calendar.getInstance().getTimeInMillis()));
			rts.setRAnagStati(stato);
			rts.setNote(stato.getDescr());
			rts.setProcessid(task.getProcessId());
			tracciaStatiService.saveTracciaStato(rts);
			log.debug("Traccia stato salvata");
		}
	}
	
	public void aggiornamentoTracciaDate(Task task) throws Exception {
			
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
				
				log.debug("Traccia date salvata");
			}
		}
	}
	
	public void aggiornamentoEventiLaunch(Task task, RCommand rCommand,RAnagStati stato) throws Exception {
		REventiLaunch rel = new REventiLaunch();
		rel.setBelfiore(task.getBelfiore());
		rel.setIstante(task.getStartTime().getTime());
		rel.setCommandId(rCommand.getId());
		rel.setCommandType(rCommand.getRCommandType().getId());
		rel.setIdFonte(task.getIdFonte());
		rel.setCommandStato(stato.getId());
		eventLaunchService.saveREventoLaunch(rel);
		log.debug("Evento segnalato");
	}
	
}
