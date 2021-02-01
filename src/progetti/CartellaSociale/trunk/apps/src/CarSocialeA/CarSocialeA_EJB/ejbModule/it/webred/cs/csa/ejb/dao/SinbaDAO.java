package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDSinba;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class SinbaDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	public CsDSinba saveSchedaSinba(CsDSinba schedaSinba) throws Exception {
		return em.merge(schedaSinba);
	}


	public CsDSinba getSchedaSinbaById(Long id) {
		CsDSinba sinba = em.find(CsDSinba.class, id);
		return sinba;
	}

	
	@SuppressWarnings("unchecked")
	public List<CsDSinba> getListaSchedaSinbaByIdCaso(Long id) {
		List<CsDSinba> lstI = new ArrayList<CsDSinba>();
		logger.info("getListaSchedaSinbaByIdCaso " + id);

		Query q;
		try{
			q = em.createNamedQuery("CsDSinba.getListaSchedaSinbaByIdCaso");
			q.setParameter("casoId", id);
			lstI = q.getResultList();
			logger.info("getListaSchedaSinbaByIdCaso id["+id+"]- result[" + lstI.size() + "]");
		}catch(Throwable e){
			logger.error(e);
			throw new CarSocialeServiceException(e);
		}
		
		return lstI;
	}

	
	public void deleteSchedaSinba(Long obj) throws Exception {
		CsDSinba i = this.getSchedaSinbaById(obj);
		em.remove(i);
		/*
		 * Query q = em.createNamedQuery("CsIIntervento.deleteById");
		 * q.setParameter("idIntervento", obj); q.executeUpdate();
		 */
	}

	
}
