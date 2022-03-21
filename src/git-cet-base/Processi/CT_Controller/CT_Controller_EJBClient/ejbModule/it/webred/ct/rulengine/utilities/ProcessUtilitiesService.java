package it.webred.ct.rulengine.utilities;
import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.dto.Task;

import javax.ejb.Remote;

@Remote
public interface ProcessUtilitiesService {
	
	public void aggiornaEsito(RCommand rCommand,Task task,RAnagStati stato) throws Exception;
	public void aggiornamentoMonitor(RCommand rCommand,Task task,RAnagStati stato) throws Exception ;
	public void aggiornamentoTracciaStato(Task task,RAnagStati stato) throws Exception ;
	public void aggiornamentoTracciaDate(Task task) throws Exception;
	public void aggiornamentoEventiLaunch(Task task, RCommand rCommand,RAnagStati stato) throws Exception ;
	
}
