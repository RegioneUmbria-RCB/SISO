package it.webred.ct.rulengine.service.bean.impl;

import java.util.List;

import it.webred.ct.rulengine.controller.model.REventiLaunch;
import it.webred.ct.rulengine.service.bean.EventLaunchService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class EventLaunchServiceBean
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW )
public class EventLaunchServiceBean implements EventLaunchService {
	
	private static Logger logger = Logger.getLogger(EventLaunchServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
	
    /**
     * Default constructor. 
     */
    public EventLaunchServiceBean() {
        // TODO Auto-generated constructor stub
    }


    public void saveREventoLaunch(REventiLaunch rel) {
		try {
			//logger.debug("Inserimento evento launch");
			manager.persist(rel);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

  	public void deleteREventoLaunch(REventiLaunch rel) {
		try {
			//logger.debug("Cancellazione evento launch");
			Query q = manager.createNamedQuery("Controller.deleteEventoLaunch");
			q.setParameter("istante", rel.getIstante());
			
			int esito = q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public List<REventiLaunch> getREventiLaunch() {
		List<REventiLaunch> rEventi = null;
		
		try {
			logger.debug("Recupero lista eventi da mettere in coda");
			Query q = manager.createNamedQuery("Controller.getREventiLaunch");
			
			rEventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rEventi;
	}


	public void deleteAllEventi() {
		try {
			logger.debug("Cancellazione intera tabella eventi launch");
			Query q = manager.createNamedQuery("Controller.deleteAllEventi");
			int esito = q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}	
	}


	
}
