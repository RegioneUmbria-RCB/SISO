package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaLIGHT;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

			sina = em.merge(sina); // provare sina = em.merge(sina);

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

				// if(result.getCsDSinaEseg() != null &&
				// result.getCsDSinaEseg().size() > 0){
				// //eseguire la query
				// List<CsDSinaEseg> resultEsegList;
				// Query q1 =
				// em.createNamedQuery("CsDSina.getSinaEsegBySinaId");
				// q1.setParameter("idSina", result.getId());
				// resultEsegList = q1.getResultList();
				//
				// result.setCsDSinaEseg(resultEsegList);
				// //fine query
				// }
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

	// SISO - 884
	@SuppressWarnings("unchecked")
	public List<CsDSina> getSinaByIntEsegMastId(Long interventoEsegMastId) {
		Query q = em.createNamedQuery("CsDSina.getSinaByInterventoEsegMastId");
		q.setParameter("interventoEsegMastId", interventoEsegMastId);
		List results = q.getResultList();
		if (results.isEmpty()) {
			return null;
		} else {
			return (List<CsDSina>) results;
		}

	}

	// SISO - 884
	public void sinaSetNullMastId(Long id) {
		Query q = em.createNamedQuery("CsDSina.setNullMastId");
		q.setParameter("id", id);
		q.executeUpdate();
	}

	// SISO-783
	public List<CsDSina> findSinaByMastId(Long mastId) {

		List<CsDSina> result = new ArrayList<CsDSina>();

		try {
			Query q = em.createNamedQuery("CsDSina.findSinaByMastId");
			q.setParameter("mastId", mastId);
			result = q.getResultList();
		} catch (Exception e) {
			logger.error("findDiarioFromSinaMastId " + e.getMessage(), e);
		}
		return result;
	}

	// SISO-784
	public List<CsDSinaLIGHT> getSinaByMastId(List<Long> ids) {
		List<CsDSinaLIGHT> resultList = new ArrayList<CsDSinaLIGHT>();
		logger.error("getSinaByMastId ["+ids+"]");
		try {
			Query q = em.createNamedQuery("CsDSinaLIGHT.getSinaByMastId");
			q.setParameter("ids", ids);
			resultList = q.getResultList();
			logger.error("getSinaByMastId result["+resultList.size()+"]");
		} catch (Exception e) {
			logger.error("getSinaById " + e.getMessage(), e);
		}
		return resultList;
	}

	// SISO-783
	public List<CsDSina> findSinaByCaso(Long casoId) {

		List<CsDSina> result = new ArrayList<CsDSina>();

		try {
			Query q = em.createNamedQuery("CsDSina.findSinaByCaso");
			q.setParameter("casoId", casoId);
			result = q.getResultList();
		} catch (Exception e) {
			logger.error("findSinaByCaso " + e.getMessage(), e);
		}
		return result;
	}

	// SISO-783
	public Date findMinDateSinaCollegatiByMastId(Long mastId) {
		Date result = null;

		try {
			Query q = em
					.createNamedQuery("CsDSina.findMinDateCollegatiByMastId");
			q.setParameter("mastId", mastId);
			List<Date> resultList = q.getResultList();
			result = (!resultList.isEmpty()) ? resultList.get(0) : null;
		} catch (Exception e) {
			logger.error("findMinDateCollegatiByMastId " + e.getMessage(), e);
		}
		return result;
	}
	
	// SISO-928
		public List<CsDSina> findSinaCollegabiliByCf(String cf, Long mastId) {

			List<CsDSina> result = new ArrayList<CsDSina>();

			try {
				Query q = em.createNamedQuery("CsDSina.findSinaCollegabiliByCF");
				q.setParameter("cf", cf);
				q.setParameter("mastId", mastId);
				result = q.getResultList();
			} catch (Exception e) {
				logger.error("findSinaCollegabiliByCf " + e.getMessage(), e);
			}
			return result;
		}
}
