package it.webred.ct.rulengine.service.bean.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.controller.model.RTracciaDate;
import it.webred.ct.rulengine.controller.model.RTracciaForniture;
import it.webred.ct.rulengine.controller.model.RTracciaStati;
import it.webred.ct.rulengine.service.bean.TracciaStatiService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class TracciaStatiServiceBean
 */


@Stateless
public class TracciaStatiServiceBean implements TracciaStatiService {
	private static Logger logger = Logger.getLogger(TracciaStatiServiceBean.class.getName());
	
	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
    /**
     * Default constructor. 
     */
    public TracciaStatiServiceBean() {

    }

	public void deleteTracciaStato(RTracciaStati rTracciaStati) {
		
		try {
			logger.debug("Eliminazione traccia stato");
			Query q = manager.createNamedQuery("Controller.deleteTracciaStato");
			q.setParameter("idEnte", rTracciaStati.getId().getBelfiore());
			q.setParameter("idFonte", rTracciaStati.getId().getIdFonte());
			q.setParameter("istante", rTracciaStati.getId().getIstante());

			int esito = q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}	
	}
		
	

	public List<RTracciaStati> getTracciaStato(String belfiore, Long idFonte) {
		List<RTracciaStati> rTracciaStati = null;
		
		try {
			logger.debug("Recupero lista traccia stato");
			Query q = manager.createNamedQuery("Controller.getTracciaStatoByEnteFD");
			q.setParameter("idEnte", belfiore);
			q.setParameter("idFonte", idFonte);
			
			rTracciaStati = q.getResultList();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rTracciaStati;
	}

	public List<RTracciaStati> getTracciaStato(String belfiore) {
		List<RTracciaStati> rTracciaStati = null;
		
		try {
			logger.debug("Recupero lista traccia stato");
			Query q = manager.createNamedQuery("Controller.getTracciaStatoByEnte");
			q.setParameter("idEnte", belfiore);
			
			rTracciaStati = q.getResultList();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rTracciaStati;
	}
	
	public RTracciaStati getTracciaStatoByProcessId(String processId) {
		
		try {
			//logger.debug("Recupero traccia stato by processId");
			Query q = manager.createNamedQuery("Controller.getTracciaStatoByProcessId");
			q.setParameter("processId", processId);
			
			List<RTracciaStati> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);
			else return null;
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
	}

	public void saveOrUpdateTracciaStato(RTracciaStati rTracciaStati) {
		
		try {
			logger.debug("Aggiornamento informazioni traccia stato");
			manager.merge(rTracciaStati);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public void saveTracciaStato(RTracciaStati rTracciaStati) {
		
		try {
			logger.debug("Inserimento informazioni traccia stato");
			manager.persist(rTracciaStati);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public Date getMinTracciaStato(String belfiore, Long idFonte) {
		
		Calendar c = Calendar.getInstance();
		
		try {
			logger.debug("Recupero Min traccia stato");
			Query q = manager.createNamedQuery("Controller.getMinTracciaStatoByEnteFD");
			q.setParameter("idEnte", belfiore);
			q.setParameter("idFonte", idFonte);
			
			Long istante = (Long) q.getSingleResult();
			
			if(istante != null){
				c.setTimeInMillis(istante);
				return c.getTime();
			}else return null;
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public Date getMinTracciaForniture(String belfiore, Long idFonte) {
				
		try {
			logger.debug("Recupero Min traccia forniture");
			Query q = manager.createNamedQuery("Controller.getMinTracciaFornitureByEnteFD");
			q.setParameter("idEnte", belfiore);
			q.setParameter("idFonte", idFonte);
			
			List<Date> data = q.getResultList();
			
			if(data.size() > 0)
				return data.get(0);
			else return null;
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public Date getMaxTracciaForniture(String belfiore, Long idFonte) {
				
		try {
			logger.debug("Recupero Max traccia forniture");
			Query q = manager.createNamedQuery("Controller.getMaxTracciaFornitureByEnteFD");
			q.setParameter("idEnte", belfiore);
			q.setParameter("idFonte", idFonte);
			
			List<Date> data = q.getResultList();
			
			if(data.size() > 0)
				return data.get(0);
			else return null;
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public List<RTracciaForniture> getTracciaFornitureByProcessId(String processId) {
		
		try {
			//logger.debug("Recupero traccia forniture by processId");
			Query q = manager.createNamedQuery("Controller.getTracciaFornitureByProcessId");
			q.setParameter("processId", processId);
			
			return q.getResultList();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
	}
	
	public RTracciaDate getTracciaDate(String belfiore, Long idFonte) {
				
		try {
			logger.debug("Recupero traccia date");
			Query q = manager.createNamedQuery("Controller.getTracciaDateByEnteFD");
			q.setParameter("idEnte", belfiore);
			q.setParameter("idFonte", idFonte);
			
			List<RTracciaDate> data = q.getResultList();
			
			if(data.size() > 0)
				return data.get(0);
			else return null;
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public void updateTracciaDate(RTracciaDate rTracciaDate) {
		
		try {
			logger.debug("Aggiornamento informazioni traccia date");
			manager.merge(rTracciaDate);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}

	public void saveTracciaDate(RTracciaDate rTracciaDate) {
		
		try {
			logger.debug("Inserimento informazioni traccia date");
			manager.persist(rTracciaDate);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
	public void deleteTracciaDate(RTracciaDate rTracciaDate) {
		
		try {
			logger.debug("Eliminazione traccia date");
			Query q = manager.createNamedQuery("Controller.deleteTracciaDate");
			q.setParameter("idEnte", rTracciaDate.getId().getBelfiore());
			q.setParameter("idFonte", rTracciaDate.getId().getIdFonte());

			int esito = q.executeUpdate();
			
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}	
	}

}
