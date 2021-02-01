package it.webred.ct.rulengine.service.bean.impl;

import java.util.List;

import it.webred.ct.rulengine.controller.model.RAnagStati;
import it.webred.ct.rulengine.controller.model.RCommandType;
import it.webred.ct.rulengine.service.ControllerBaseService;
import it.webred.ct.rulengine.service.bean.MainControllerService;
import it.webred.ct.rulengine.service.exception.ServiceException;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class RecuperaComandoServiceBean
 */
@Stateless
public class MainControllerServiceBean extends ControllerBaseService implements MainControllerService {

	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;
	
	public List<RCommandType> getListRCommandType() {
		List<RCommandType> commandType = null;
		
		try {
			logger.debug("LISTA COMMAND TYPE");
			Query q = manager
					.createNamedQuery("Controller.getRCommandType");
			commandType = q.getResultList();

		} catch (Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return commandType;
	}

	public RAnagStati getRAnagStato(Long idStato) {
		RAnagStati stato = null;
		
		try {
			logger.debug("RECUPERO STATO "+idStato);
			Query q = manager.createNamedQuery("Controller.getRAnagStato");
			q.setParameter("idStato", idStato);
			stato = (RAnagStati)q.getSingleResult();

		} catch (NoResultException nre) {
			stato = null;
		}catch (Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return stato;
	}

	public RAnagStati getRAnagStato(Long idCommandType,String tipoStato) {
		RAnagStati stato = null;
		
		try {
			logger.debug("RECUPERO STATO di "+idCommandType+" e "+tipoStato);
			Query q = manager.createNamedQuery("Controller.getRAnagStatoByTipoOperazione");
			q.setParameter("idCommandType",idCommandType);
			q.setParameter("tipoStato",tipoStato);
			stato = (RAnagStati)q.getSingleResult();

		} catch (Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return stato;
	}

	public List<RAnagStati> getAllStati() {
		List<RAnagStati> stati = null;
		
		try {
			logger.debug("LISTA STATI");
			Query q = manager.createNamedQuery("Controller.getRAnagStati");
			stati = q.getResultList();

		} catch (Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return stati;
	}

	public List<RAnagStati> getStati(Long rCommandType) {
		List<RAnagStati> stati = null;
		
		try {
			logger.debug("LISTA STATI");
			Query q = manager.createNamedQuery("Controller.getRAnagStatiByFKCommandType");
			q.setParameter("idCommandType",rCommandType);
			stati = q.getResultList();

		} catch (Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return stati;
	}

	public RCommandType getRCommandType(Long idCommandType) {
		RCommandType rct = null;
		
		try {
			logger.debug("RECUPERO COMMAND TYPE "+idCommandType);
			Query q = manager.createNamedQuery("Controller.getUniqueRCommandType");
			q.setParameter("idCommandType", idCommandType);
			rct = (RCommandType)q.getSingleResult();

		} catch (NoResultException nre) {
			rct = null;
		}catch (Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
		
		return rct;
	}

	
	
}
