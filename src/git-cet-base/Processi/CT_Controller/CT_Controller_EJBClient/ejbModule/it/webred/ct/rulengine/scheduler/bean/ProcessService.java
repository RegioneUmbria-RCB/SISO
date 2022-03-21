package it.webred.ct.rulengine.scheduler.bean;

import it.webred.ct.rulengine.dto.Task;

import javax.ejb.Remote;


@Remote
public interface ProcessService {
	
	
	public Task scheduleTask(Task taskParam) throws Exception;
	
}
