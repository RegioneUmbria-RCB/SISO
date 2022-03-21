package it.webred.ct.rulengine.service.bean.impl;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RAnagEventi;
import it.webred.ct.rulengine.controller.model.REventiEnte;
import it.webred.ct.rulengine.controller.model.REventiEntePK;
import it.webred.ct.rulengine.controller.model.REventiLaunch;
import it.webred.ct.rulengine.service.bean.EventService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class EventServiceDean
 */
@Stateless
public class EventServiceBean implements EventService {

	private static Logger logger = Logger.getLogger(EventServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
    /**
     * Default constructor. 
     */
    public EventServiceBean() {
        // TODO Auto-generated constructor stub
    }

	public List<RAnagEventi> getEventiClasseA(REventiLaunch rel) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi di classe A");
			Query q = manager.createNamedQuery("Controller.getEventiClasseA");
			q.setParameter("commandType", rel.getCommandType());
			q.setParameter("idEnte", rel.getBelfiore());
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public List<RAnagEventi> getEventiClasseB(REventiLaunch rel) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi di classe B");
			Query q = manager.createNamedQuery("Controller.getEventiClasseB");
			q.setParameter("commandType", rel.getCommandType());
			q.setParameter("idFonte", rel.getIdFonte());
			q.setParameter("idEnte", rel.getBelfiore());
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public List<RAnagEventi> getEventiClasseC(REventiLaunch rel) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi di classe C");
			Query q = manager.createNamedQuery("Controller.getEventiClasseC");
			q.setParameter("idFonte", rel.getIdFonte());
			q.setParameter("idEnte", rel.getBelfiore());
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public List<RAnagEventi> getEventiClasseD(REventiLaunch rel) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi di classe D");
			Query q = manager.createNamedQuery("Controller.getEventiClasseD");
			q.setParameter("commandId", rel.getCommandId());
			q.setParameter("idEnte", rel.getBelfiore());
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public List<RAnagEventi> getEventiClasseE(REventiLaunch rel) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi di classe E");
			Query q = manager.createNamedQuery("Controller.getEventiClasseE");
			q.setParameter("commandId", rel.getCommandId());
			q.setParameter("statoId", rel.getCommandStato());
			q.setParameter("idEnte", rel.getBelfiore());
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public List<RAnagEventi> getEventiClasseF(REventiLaunch rel) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi di classe F");
			Query q = manager.createNamedQuery("Controller.getEventiClasseF");
			q.setParameter("statoId", rel.getCommandStato());
			q.setParameter("idEnte", rel.getBelfiore());
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}
	
	public List<RAnagEventi> getEventiClasseG(REventiLaunch rel) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi di classe G");
			Query q = manager.createNamedQuery("Controller.getEventiClasseG");
			q.setParameter("commandType", rel.getCommandType());
			q.setParameter("commandId", rel.getCommandId());
			q.setParameter("idFonte", rel.getIdFonte());
			q.setParameter("statoId", rel.getCommandStato());
			q.setParameter("idEnte", rel.getBelfiore());
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public List<RAnagEventi> getEventiStd() {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi standard");
			Query q = manager.createNamedQuery("Controller.getEventiStd");
						
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public List<RAnagEventi> getEventiEnte(String belfiore) {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi ente");
			Query q = manager.createNamedQuery("Controller.getEventiEnte");
			q.setParameter("idEnte", belfiore);
			
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public void abilitaEvento(REventiEnte ree) {
		try {			
			logger.debug("Abilitazione evento");
			manager.merge(ree);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
	}

	public void disabilitaEvento(REventiEntePK ree) {
		try {			
			logger.debug("Disabilitazione evento");
			
			Query q = manager.createNamedQuery("Controller.disabilitaEvento");
			q.setParameter("id", ree.getId());
			//q.setParameter("standard", ree.getStandard());
			q.setParameter("idEnte", ree.getBelfiore());
			q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public List<RAnagEventi> getEventi() {
		List<RAnagEventi> eventi = null;
		
		try{
			logger.debug("Recupero lista eventi");
			Query q = manager.createNamedQuery("Controller.getEventi");
						
			eventi = q.getResultList();
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return eventi;
	}

	public void salvaNuovoEvento(RAnagEventi rav) {
		try {			
			logger.debug("Salvataggio nuovo evento");
			manager.persist(rav);
			logger.info("Nuovo evento inserito");
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public void deleteEvento(Long ravId) {
		try {			
			logger.debug("Eliminazione evento");
			
			Query q = manager.createNamedQuery("Controller.deleteEvento");
			q.setParameter("idEvento", ravId);
			q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
	}
	
	
}
