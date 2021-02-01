package it.webred.ct.rulengine.service.bean.impl;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.rulengine.controller.model.RSottoscrittori;
import it.webred.ct.rulengine.service.bean.SottoscrizioniService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class SottoscrizioniServiceBean
 */
@Stateless
public class SottoscrizioniServiceBean implements SottoscrizioniService {
	
	private static Logger logger = Logger.getLogger(SottoscrizioniServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
    /**
     * Default constructor. 
     */
    public SottoscrizioniServiceBean() {
        // TODO Auto-generated constructor stub
    }

	public List<RSottoscrittori> getSottoscrizioni(String utente,String belfiore) {
		
		List<RSottoscrittori> ss = null;
		
		try {
			logger.debug("Recupero lista sottoscrizioni");
			Query q = manager.createNamedQuery("Controller.getSottoscrizioni");
			q.setParameter("utente", utente);
			q.setParameter("idEnte", belfiore);
			
			ss = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}

		return ss;
	}

	public void subscribe(RSottoscrittori rs) {
		try {			
			logger.debug("Abilitazione sottoscrizione");
			manager.merge(rs);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	
	public void unsubscribe(String utente, String belfiore, String codCommand) {
		try {			
			logger.debug("Disabilitazione sottoscrizione");
			
			Query q = manager.createNamedQuery("Controller.unsubscribe");
			q.setParameter("utente", utente);
			q.setParameter("idEnte", belfiore);
			q.setParameter("codCommand", codCommand);
			
			q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public RSottoscrittori getSottoscrizione(String utente, String belfiore,
			String codCommand) {
		
		RSottoscrittori rs = null;
		
		try {
			logger.debug("Recupero sottoscrizione");
			Query q = manager.createNamedQuery("Controller.getSottoscrizione");
			q.setParameter("utente", utente);
			q.setParameter("idEnte", belfiore);
			q.setParameter("codCommand", codCommand);
			
			rs = (RSottoscrittori)q.getSingleResult();
		}catch(NoResultException nre) {
			rs = null;
			//logger.warn("Nessuna sottoscrizione trovata");
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rs;
	}

	public List<RSottoscrittori> getUtentiSottoscritti(String belfiore,String codCommand) {
		
		List<RSottoscrittori> ss = null;
		
		try {
			logger.debug("Recupero lista sottoscrizioni");
			Query q = manager.createNamedQuery("Controller.getUtentiSottoscritti");
			q.setParameter("idEnte", belfiore);
			q.setParameter("codCommand", codCommand);
			
			ss = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}

		return ss;
		
	}

}
