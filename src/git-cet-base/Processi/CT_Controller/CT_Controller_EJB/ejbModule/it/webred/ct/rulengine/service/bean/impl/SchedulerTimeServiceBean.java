package it.webred.ct.rulengine.service.bean.impl;

import java.util.Calendar;
import java.util.List;


import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.service.bean.SchedulerTimeService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class SchedulerTimeServiceBean
 */
@Stateless
public class SchedulerTimeServiceBean implements SchedulerTimeService {
	
	private Logger logger = Logger.getLogger(SchedulerTimeServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;

    /**
     * Default constructor. 
     */
    public SchedulerTimeServiceBean() {
        // TODO Auto-generated constructor stub
    }

	public List<RSchedulerTime> getSchedulerProcesses() {
		List<RSchedulerTime> scheds = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getScheduledProcessesAll");
			q.setParameter("now", Calendar.getInstance().getTime());
			
			scheds = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return scheds;
	}
    
	public List<RSchedulerTime> getSchedulerProcesses(List<String> enti) {
		List<RSchedulerTime> scheds = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getScheduledProcesses");
			q.setParameter("now", Calendar.getInstance().getTime());
			q.setParameter("enti", enti);
			
			scheds = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return scheds;
	}
	
	public List<RSchedulerTime> getSchedulerProcessesExpired(List<String> enti) {
		List<RSchedulerTime> scheds = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getScheduledProcessesExpired");
			q.setParameter("now", Calendar.getInstance().getTime());
			q.setParameter("enti", enti);
			
			scheds = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return scheds;
	}

	public RSchedulerTime getScheduledProcessesById(Long id) {
		
		try {			
			Query q = manager.createNamedQuery("Controller.getScheduledProcessesById");
			q.setParameter("id", id);
			
			return (RSchedulerTime) q.getSingleResult();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public List<RSchedulerTime> getSchedulerProcesses(String belfiore) {
		List<RSchedulerTime> scheds = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getScheduledProcessesByEnte");
			q.setParameter("idEnte", belfiore);
			q.setParameter("now", Calendar.getInstance().getTime());
			
			scheds = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return scheds;
	}
	
	public List<RSchedulerTime> getSchedulerProcessesExpired(String belfiore) {
		List<RSchedulerTime> scheds = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getScheduledProcessesExpiredByEnte");
			q.setParameter("idEnte", belfiore);
			q.setParameter("now", Calendar.getInstance().getTime());
			
			scheds = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return scheds;
	}

	public void saveOrUpdateSchedulerProcessInstance(RSchedulerTime rSchedulerTime) {
		try {			
			logger.debug("Aggiornamento informazioni sheduler");
			manager.merge(rSchedulerTime);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public void saveSchedulerProcessInstance(RSchedulerTime rSchedulerTime) {
		try {			
			logger.debug("Inserimento informazioni sheduler");
			manager.persist(rSchedulerTime);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public void deleteSchedulerProcessInstanceById(Long id) {
		try {			
			logger.debug("Eliminazione informazioni sheduler");
			
			Query q = manager.createNamedQuery("Controller.deleteScheduledProcessesById");
			q.setParameter("id", id);
			q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public void deleteSchedulerProcessInstanceExpired() {
		try {			
			logger.debug("Eliminazione informazioni sheduler scaduti");
			
			Query q = manager.createNamedQuery("Controller.deleteScheduledProcessesExpired");
			q.setParameter("now", Calendar.getInstance().getTime());
			q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public RSchedulerTime getSchedulerProcess(String jobname) {
		RSchedulerTime rSchedulerTime = null;
		
		try {			
			logger.debug("Recupero processo sheduler");
					
			Query q = manager.createNamedQuery("Controller.getScheduledProcess");
			
			String[] params = jobname.split("@");
			String[] jobente = params[0].split(":"); 
			q.setParameter("idEnte", jobente[1]);
			q.setParameter("codComando", params[1]);
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(new Long(params[2]));
			q.setParameter("dtStart", c.getTime());
			
			rSchedulerTime = (RSchedulerTime)q.getSingleResult();
		}catch(NoResultException nre) {
			rSchedulerTime = null;
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rSchedulerTime;
	}

}
