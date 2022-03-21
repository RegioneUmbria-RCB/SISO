package it.webred.ct.rulengine.service.bean.impl;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.service.bean.QueueService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class QueueServiceBean
 */
@Stateless
public class QueueServiceBean implements QueueService {
	private static Logger logger = Logger.getLogger(QueueServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
    /**
     * Default constructor. 
     */
    public QueueServiceBean() {
        // TODO Auto-generated constructor stub
    }

	public void saveOrUpdateProcess(RCoda rCoda) {
		
		try {
			logger.debug("Aggiornamento informazioni processo");
			manager.merge(rCoda);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public void saveProcess(RCoda rCoda) {
		try {
			logger.debug("Inserimento informazioni processo");
			manager.persist(rCoda);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public RCoda getProcessFromCoda() {
		RCoda rCoda = null;
		
		try {
			logger.debug("Recupero processo dalla coda");
			Query q = manager.createNamedQuery("Controller.getSingleProcessFromCoda");
			rCoda = (RCoda)q.getSingleResult();
		}catch(NoResultException nre){
			logger.warn("Coda vuota !");
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rCoda;
	}

	public void deleteEntireQueue() {
		try {
			logger.debug("Cancellazione intera coda processi");
			Query q = manager.createNamedQuery("Controller.deleteEntireQueue");
			int esito = q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public void deleteProcess(RCoda rCoda) {
		try {
			logger.debug("Eliminazione processo");
			RCoda rCodaToRemove = manager.find(rCoda.getClass(), rCoda.getIstante());
			manager.remove(rCodaToRemove);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}	
	}

	public List<RCoda> getRCodaByEnte(String belfiore) {
		List<RCoda> rCode = null;
		
		try {
			logger.debug("Recupero lista job dalla coda");
			Query q = manager.createNamedQuery("Controller.getProcessListFromCodaByEnte");
			q.setParameter("idEnte", belfiore);
			
			rCode = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rCode;
	}

	public List<RCoda> getRCodaByEnti(List<String> enti) {
		List<RCoda> rCode = null;
		
		try {
			logger.debug("Recupero lista job dalla coda");
			Query q = manager.createNamedQuery("Controller.getProcessListFromCodaByEnti");
			q.setParameter("enti", enti);
			
			rCode = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rCode;
	}
	
	public List<RCoda> getFullRCoda() {
		List<RCoda> rCode = null;
		
		try {
			logger.debug("Recupero lista job dalla coda");
			Query q = manager.createNamedQuery("Controller.getProcessListFromCoda");
			
			rCode = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rCode;
	}

	public List<String> getListaEntiInQueue() {
		List<String> enti = null;
		
		try {
			logger.debug("Recupero enti in coda");
			Query q = manager.createNamedQuery("Controller.getEntiRunningQueue");
			
			enti = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return enti;
	}


}
