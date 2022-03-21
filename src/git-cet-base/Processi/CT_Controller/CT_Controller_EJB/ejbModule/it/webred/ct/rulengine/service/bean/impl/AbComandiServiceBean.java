package it.webred.ct.rulengine.service.bean.impl;

import java.util.ArrayList;
import java.util.List;

import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RCommandAck;
import it.webred.ct.rulengine.controller.model.RCommandLaunch;
import it.webred.ct.rulengine.controller.model.REnteEsclusioni;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.dto.EnteEsclusioniDTO;
import it.webred.ct.rulengine.dto.LogDTO;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.service.bean.AbComandiService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.exception.ServiceException;

import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

/**
 * Session Bean implementation class RecuperaComandoServiceBean
 */
@Stateless
public class AbComandiServiceBean implements AbComandiService {
	protected Logger logger = Logger.getLogger(AbComandiServiceBean.class.getName());

	@PersistenceContext(unitName = "Controller_Model")
	private EntityManager manager;

	public List<EnteEsclusioniDTO> getEnteEsclusioniByCommandType(Long idCommandType) {
		List<EnteEsclusioniDTO> listaComandi = new ArrayList<EnteEsclusioniDTO>();

		try {

			Query q = manager
					.createNamedQuery("Controller.getEnteEsclusioniByCommandType");
			q.setParameter("idCommandType", idCommandType);

			List<Object[]> lista = (List<Object[]>) q.getResultList();
			for (Object[] o : lista) {
				EnteEsclusioniDTO dto = new EnteEsclusioniDTO();
				dto.setrCommand((RCommand) o[0]);
				dto.setrEnteEsclusioni((REnteEsclusioni) o[1]);

				listaComandi.add(dto);
			}

		} catch (Throwable t) {
			logger.error("Eccezione: " + t.getMessage());
			throw new ServiceException(t);
		}

		return listaComandi;
	}

	public void updateEnteEsclusioni(REnteEsclusioni rEnteEsclusioni) {
		try {			
			logger.debug("Aggiornamento informazioni ente esclusioni");
			manager.merge(rEnteEsclusioni);
		}catch(Throwable t) {
			logger.error("Eccezione: "+t.getMessage());
			throw new ServiceException(t);
		}
	}
	
}
