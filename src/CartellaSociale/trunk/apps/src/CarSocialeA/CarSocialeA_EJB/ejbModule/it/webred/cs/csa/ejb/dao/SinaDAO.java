package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaEseg;

import java.io.Serializable;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class SinaDAO extends CarSocialeBaseDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	public CsDSina newSina(CsDSina sina) {
		em.persist(sina);
		return sina;
	}

	public CsDSina updateSina(CsDSina sina) {
		try {
			  
			  sina = em.merge(sina);  // provare sina = em.merge(sina);
			 
		} catch (Exception e) {
			logger.error("updateSina " + e.getMessage(), e);
		}
		return sina;
	}

	@SuppressWarnings("unchecked")
	public CsDSina getSinaById(Long idSina) {
		List<CsDSina> resultList; // = new ArrayList<CsDPai>();
		CsDSina result = null;

		try {
			if (idSina != null) {
				Query q = em.createNamedQuery("CsDSina.getSinaById");
				q.setParameter("idSina", idSina);
				resultList = q.getResultList();

				result = (!resultList.isEmpty()) ? resultList.get(0) : null;
			}
		} catch (Exception e) {
			logger.error("getSinaById " + e.getMessage(), e);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public CsDSina getSinaByDiarioId(Long idDiario) {
		List<CsDSina> resultList; // = new ArrayList<CsDPai>();
		CsDSina result = null;

		try {
			if (idDiario != null) {
				Query q = em.createNamedQuery("CsDSina.getSinaByDiarioId");
				q.setParameter("idDiario", idDiario);
				resultList = q.getResultList();

				result = (!resultList.isEmpty()) ? resultList.get(0) : null;
				
//				if(result.getCsDSinaEseg() != null && result.getCsDSinaEseg().size() > 0){
//					//eseguire la query
//					List<CsDSinaEseg> resultEsegList;
//					Query q1 = em.createNamedQuery("CsDSina.getSinaEsegBySinaId");
//					q1.setParameter("idSina", result.getId());
//					resultEsegList = q1.getResultList();
//
//					result.setCsDSinaEseg(resultEsegList);
//					//fine query
//				}
			}
		} catch (Exception e) {
			logger.error("getSinaById " + e.getMessage(), e);
		}
		return result;
	}

	public void deleteSinaEsegById(Long idSina) {
		Query q = em.createNamedQuery("CsDSina.deleteSinaEsegById");
		q.setParameter("idSina", idSina);
		q.executeUpdate();
	}
}
