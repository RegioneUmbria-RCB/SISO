package it.webred.ct.rulengine.service.bean;


import it.webred.ct.rulengine.controller.model.RSchedulerTime;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface SchedulerTimeService {
	
	public void saveOrUpdateSchedulerProcessInstance(RSchedulerTime rSchedulerTime);
	
	public void saveSchedulerProcessInstance(RSchedulerTime rSchedulerTime);
	
	public List<RSchedulerTime> getSchedulerProcesses();
	
	public List<RSchedulerTime> getSchedulerProcesses(List<String> enti);
	
	public List<RSchedulerTime> getSchedulerProcesses(String belfiore);
	
	public List<RSchedulerTime> getSchedulerProcessesExpired(List<String> enti);
	
	public List<RSchedulerTime> getSchedulerProcessesExpired(String belfiore);
	
	public RSchedulerTime getSchedulerProcess(String jobname);
	
	public void deleteSchedulerProcessInstanceById(Long id);
	
	public void deleteSchedulerProcessInstanceExpired();
		
	public RSchedulerTime getScheduledProcessesById(Long id);
}
