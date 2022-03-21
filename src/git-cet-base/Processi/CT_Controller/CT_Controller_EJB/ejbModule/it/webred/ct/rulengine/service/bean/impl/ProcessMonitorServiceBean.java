package it.webred.ct.rulengine.service.bean.impl;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RProcessMonitor;
import it.webred.ct.rulengine.controller.model.RProcessMonitorPK;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.service.ControllerBaseService;
import it.webred.ct.rulengine.service.bean.ProcessMonitorService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Remove;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class ProcessMonitorServiceBean
 */
@Stateless
public class ProcessMonitorServiceBean implements ProcessMonitorService {
	private static Logger logger = Logger.getLogger(ProcessMonitorServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
    /**
     * Default constructor. 
     */
    public ProcessMonitorServiceBean() {
        // TODO Auto-generated constructor stub
    }


	public void saveProcessInstance(RProcessMonitor rProcessMonitor) {
		
		try {
			logger.debug("Inserimento informazioni processo");
			manager.persist(rProcessMonitor);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	
	public void saveOrUpdateProcessInstance(RProcessMonitor rProcessMonitor) {
		try {
			logger.debug("Aggiornamento informazioni processo");
			manager.merge(rProcessMonitor);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}	
	}


	public RProcessMonitor getProcessMonitor(RProcessMonitorPK rProcessMonitorPK) {
		RProcessMonitor rProcessMonitor = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getProcessMonitor");
			q.setParameter("idEnte", rProcessMonitorPK.getBelfiore());
			q.setParameter("idCommand", rProcessMonitorPK.getFkCommand());
			rProcessMonitor = (RProcessMonitor)q.getSingleResult();
		}catch(NoResultException nre) {
			//Significa che il processo chiamato non è già stato chiamato
			rProcessMonitor = null;
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rProcessMonitor;
	}
	
	public RProcessMonitor getProcessMonitorByProcessId(String processId) {
		RProcessMonitor rProcessMonitor = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getProcessMonitorByProcessId");
			q.setParameter("processId", processId);
			rProcessMonitor = (RProcessMonitor)q.getSingleResult();
		}catch(NoResultException nre) {
			//Significa che il processo chiamato non è già stato chiamato
			rProcessMonitor = null;
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rProcessMonitor;
	}

	public List<RProcessMonitor> getLockedProcessMonitor(String belfiore, Long idFonte) {
		
		List<RProcessMonitor> locked = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getLockedProcessMonitor");
			q.setParameter("idEnte", belfiore);
			q.setParameter("tipoI", "I");
			q.setParameter("idFonte",idFonte);
			locked = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return locked;
	}
	
	
	public List<RProcessMonitor> getPrevProcessMonitor(String belfiore, Long idFonte) {
		
		List<RProcessMonitor> rProcessMonitors = null;
		
		try {			
			Query q = manager.createNamedQuery("Controller.getPrevProcessMonitor");
			q.setParameter("idEnte", belfiore);
			q.setParameter("tipoI", "I");
			q.setParameter("idFonte",idFonte);
			rProcessMonitors = q.getResultList();
		}catch(NoResultException nre) {
			logger.warn("Nessun processo precedente sulla FD");
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rProcessMonitors;
	}


	public Task isTrattamentoRunning(Task task) {

		Task tk = task.getCopy();
		
		//impostazione iniziale
		tk.setProcessable(true);
		
		try {
			Query q = manager.createNamedQuery("Controller.getTrattamentoRunningProcess");
    		q.setParameter("idFonte", task.getIdFonte());
    		q.setParameter("idEnte", task.getBelfiore());
    		q.setParameter("tipoI", "I");
    		
    		List<Object[]> ll = q.getResultList();
    		logger.debug("Processi di trattamento in esecuzione: "+ll.size());
    		
    		//va considertao solo il primo record
    		for(Object[] item: ll) {
    			tk.setProcessable(false);  //processo di trattamento in esecuzione
    			tk.setStatus(item[1].toString());
    			tk.setDescription("E' in esecuzione il comando "+item[2]+" [ID comando "+item[3]+"]");
    			tk.setFreeObj(item[2]);
    			break;
    		}
    		
		}catch(Throwable t){
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return tk;
	}
    
}
