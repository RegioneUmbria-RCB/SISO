package it.webred.ct.rulengine.service.bean;
import java.util.Calendar;
import java.util.List;

import it.webred.ct.rulengine.controller.model.RSchedulerTime;

import javax.ejb.Remote;

@Remote
public interface QuartzService {
	
	public void addJob(RSchedulerTime rst);
	
	public void editJob(RSchedulerTime oldRst, RSchedulerTime newRst);
	
	public void deleteJob(RSchedulerTime rst);
	
	public List<String> getJobs();
	
	public boolean isJobPresent(String jobname, List<String> jobList);
	
	public Calendar getNextExecutionTime(RSchedulerTime rst);
}
