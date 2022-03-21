package it.webred.ct.rulengine.service.bean.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.job.REJob;
import it.webred.ct.rulengine.quartz.QuartzTranslator;
import it.webred.ct.rulengine.quartz.dto.TriggerIntervalInfo;
import it.webred.ct.rulengine.service.bean.QuartzService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.quartz.JobDetail;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CalendarIntervalTrigger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.BaseCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CalendarIntervalTriggerImpl;
import org.quartz.impl.triggers.CronTriggerImpl;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


/**
 * Session Bean implementation class QuartzServiceBean
 */
@Stateless
public class QuartzServiceBean implements QuartzService {
	
	private Logger log = Logger.getLogger(QuartzServiceBean.class.getName());
    /**
     * Default constructor. 
     */
    public QuartzServiceBean() {

    }

	public void addJob(RSchedulerTime rst) {
		
		try {
			log.debug("Recupero istanza DefaultQuartzScheduler");
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			
			log.debug("Configurazione scheduler per processo "+
					  rst.getRCommand().getCodCommand()+
					  " ["+rst.getBelfiore()+"]");
			
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(rst.getDtStart().getTime());
			
			log.info("Inserimento nuovo job nello scheduler");
			//jobname ha una formattazione ben precisa (vedi javadoc classe)
			String jobname = this.getJobname(rst);
			
			//JobDetail jobDetail = new JobDetail(jobname,sched.DEFAULT_GROUP,REJob.class);
			JobDetail jobDetail = newJob(REJob.class).withIdentity(jobname, sched.DEFAULT_GROUP).build();
			jobDetail.getJobDataMap().put("rCommand", rst.getRCommand());  //oggetto RCommand
			jobDetail.getJobDataMap().put("belfiore", rst.getBelfiore());  //belfiore
			
			log.debug("Configurazione Trigger");
			//10 secondi di delay per ogni processo da schedulare
			cal.add(Calendar.SECOND, 10);
			
			String triggername = "Trigger"+rst.getBelfiore()+cal.getTimeInMillis();
			log.debug("Trigger name: "+triggername);
			
			QuartzTranslator qt = QuartzTranslator.getInstance();
			
			/*
			//gestione codifica intervallo esecuzione
			String method = qt.getMethod(rst);
			//impostazione tentativi per il job corrente
			jobDetail.getJobDataMap().put("maxtentativi", qt.getMaxTentativi());
			
			//reflection
			Class c = QuartzTranslator.class;
			Method m = c.getMethod(method,null);
			String quartzIntervalloKey = (String)m.invoke(qt,null);
			*/
			
			TriggerIntervalInfo tif = qt.getTriggerIntervalInfo(rst);
			//impostazione tentativi per il job corrente
			jobDetail.getJobDataMap().put("maxtentativi", qt.getMaxTentativi());
			
			if(!tif.isImmediate()) {
				log.info("Trigger con calendario");
				//Calendar interval trigger
				CalendarIntervalTriggerImpl t = new CalendarIntervalTriggerImpl();
				t.setName(triggername);
				t.setGroup(sched.DEFAULT_GROUP);
				t.setStartTime(rst.getDtStart());
				t.setEndTime(rst.getDtEnd());
				t.setRepeatIntervalUnit(tif.getType());
				t.setRepeatInterval(tif.getValue());
				//setto la prox esecuzione partendo da ora
				Date next = t.getFireTimeAfter(new Date());				
				t.setStartTime(next);
				Date dt = sched.scheduleJob(jobDetail,t);
				log.info("Job inserito nello scheduler [scheduled to run at "+dt+"]");
			}
			else {
				log.info("Trigger immediato");
				//CronTrigger
				TriggerBuilder<CronTrigger> tb = newTrigger().withIdentity(triggername,sched.DEFAULT_GROUP)
							.withSchedule(CronScheduleBuilder.cronSchedule(tif.getImmediateCronExpression()))
							.startAt(rst.getDtStart()).endAt(rst.getDtEnd());
		
				CronTrigger t = tb.build();
				log.debug("CronExpression: "+t.getCronExpression());
				
				Date dt = sched.scheduleJob(jobDetail,t);
				log.info("Job inserito nello scheduler [scheduled to run at "+dt+"]");
			}
						
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage());
			throw new ServiceException(e);
		}
	}

	public void editJob(RSchedulerTime oldRst, RSchedulerTime newRst) {
		
		try {
			log.debug("Recupero istanza DefaultQuartzScheduler");
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			
			//recupero nome job originale
			String jobname = this.getJobname(oldRst);
			log.info("Job da modificare :"+jobname);
			
			//eliminazione job originale
			if(sched.deleteJob(new JobKey(jobname, sched.DEFAULT_GROUP))) {
				log.info("<Job originale eliminato dallo scheduler>");
				this.addJob(newRst);
			}
			else {
				log.warn("<Job originale " + jobname + " NON ERA PRESENTE NELLO SCHEDULER QUARTZ>");
				this.addJob(newRst);
			}
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage());
			throw new ServiceException(e);
		}
		
	}
	
	public void deleteJob(RSchedulerTime rst) {
		try {
			log.debug("Recupero istanza DefaultQuartzScheduler");
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			
			log.debug("Configurazione scheduler per processo "+
					  rst.getRCommand().getCodCommand()+
					  " ["+rst.getBelfiore()+"]");
			
			log.info("Eliminazione job dallo scheduler");
			//jobname ha una formattazione ben precisa (vedi javadoc classe)
			String jobname = this.getJobname(rst);
			
			//eliminazione job originale
			if(sched.deleteJob(new JobKey(jobname, sched.DEFAULT_GROUP))) {
				log.info("<Job eliminato dallo scheduler>");
			}
			else {
				throw new ServiceException(new Throwable("Job inesistente: "+jobname));
			}
			
		}catch(ServiceException se) {
			log.warn("Eccezione: "+se.getMessage());
			throw se;
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage());
			throw new ServiceException(e);
		}
		
	}

	/**
	 * Il metodo restituisce la lista deli job contenuti nello scheduler quartz
	 */
	public List<String> getJobs() {
		List<String> jobs = null;
		
		try {
			log.debug("Recupero istanza DefaultQuartzScheduler");
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			
			//String[] jobnames = sched.getJobNames(sched.DEFAULT_GROUP);
			log.debug("Elenco job presenti nello scheduler");
			Set keys = sched.getJobKeys(GroupMatcher.groupEquals(sched.DEFAULT_GROUP));
			
			jobs = new ArrayList<String>();
			
			Iterator iter = keys.iterator();
			while (iter.hasNext()) {
				JobKey jk = (JobKey)iter.next();
				jobs.add(jk.getName());
			}
			
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage());
			throw new ServiceException(e);
		}
		
		return jobs;
	}
	
	public boolean isJobPresent(String jobname, List<String> jobList) {
		
		boolean present = false;
		for(String job: jobList){
			if(job.equals(jobname))
				present = true;
		}
		return present;
		
	}
	
	public Calendar getNextExecutionTime(RSchedulerTime rst) {
		Calendar c = Calendar.getInstance();
		
		try {
			log.debug("Recupero istanza DefaultQuartzScheduler");
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			
			log.debug("Configurazione scheduler per processo "+
					  rst.getRCommand().getCodCommand()+
					  " ["+rst.getBelfiore()+"]");
			
			log.info("Calcolo next execution time");
			//jobname ha una formattazione ben precisa (vedi javadoc classe)
			List<? extends Trigger> tt = sched.getTriggersOfJob(new JobKey(rst.getJobnameRef(), sched.DEFAULT_GROUP));
			
			//CronTriggerImpl t  = (CronTriggerImpl)tt.get(0);
			if(tt.size() > 0){
				CalendarIntervalTriggerImpl t = (CalendarIntervalTriggerImpl)tt.get(0);
				log.debug("######### Trigger class.....: "+t.getClass().getName());
				log.debug("######### Job name..........: "+t.getJobName());
				log.debug("######### Job group.........: "+t.getJobGroup());
				log.debug("######### Trigger name......: "+t.getName());
				log.debug("######### Next fire time....: "+t.getNextFireTime());
				
				c.setTime(t.getNextFireTime());
			}else return null;
			
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage());
			throw new ServiceException(e);
		}
		
		return c;
	}
	
	
	
	
	private String getJobname(RSchedulerTime r) {
		return "REJob:"+r.getBelfiore()+"@"+r.getRCommand().getCodCommand()+"@"+r.getDtStart().getTime();
	}

	

}
