package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.data.model.CsDSina;
import it.webred.cs.data.model.CsDSinaEseg;
import it.webred.cs.data.model.CsDSinaLIGHT;
import it.webred.cs.data.model.CsDSinaPresInps;
import it.webred.cs.data.model.CsDSinaPresInpsPK;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class SinaDAO extends CarSocialeBaseDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	public CsDSina updateSina(CsDSina sina) {
		try {

			sina = em.merge(sina); // provare sina = em.merge(sina);

		} catch (Exception e) {
			logger.error("updateSina " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		return sina;
	}
	
	public CsDSinaEseg updateSinaEseg(CsDSinaEseg se) {
		try {

			return em.merge(se);

		} catch (Exception e) {
			logger.error("updateSinaEseg " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public CsDSina getSinaById(Long idSina) {
		CsDSina result = null;
		try {
			if (idSina != null)
				result = em.find(CsDSina.class, idSina);
		} catch (Exception e) {
			logger.error("getSinaById " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		return result;
	}
	
	public List<CsDSinaEseg> getSinaEsegBySinaId(Long idSina) {
		List<CsDSinaEseg> resultList = new ArrayList<CsDSinaEseg>(); 
		try {
			if (idSina != null) {
				Query q = em.createNamedQuery("CsDSinaEseg.getBySinaId");
				q.setParameter("idSina", idSina);
				resultList = q.getResultList();
			}
		} catch (Exception e) {
			logger.error("getSinaEsegBySinaId " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		return resultList;
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
			}
		} catch (Exception e) {
			logger.error("getSinaById " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		return result;
	}

	public void deleteSinaEsegById(Long idSina) {
		Query q = em.createNamedQuery("CsDSinaEseg.deleteBySinaId");
		q.setParameter("idSina", idSina);
		q.executeUpdate();
	}
	
	public void deleteSinaById(Long idSina) {
		this.deleteSinaEsegById(idSina);
		Query q = em.createNamedQuery("CsDSina.deleteById");
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
			throw new CarSocialeServiceException(e);
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
			throw new CarSocialeServiceException(e);
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
			throw new CarSocialeServiceException(e);
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
			throw new CarSocialeServiceException(e);
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
			throw new CarSocialeServiceException(e);
		}
		return result;
	}

	public void savePrestazioneInpsSina(Long idSina, List<String> lstPrestazioniInpsScelte) {
		try {
			Query q1 = em.createNamedQuery("CsDSinaPresInps.deleteBySinaId");
			q1.setParameter("idSina", idSina);
			q1.executeUpdate();
			
			for(String p : lstPrestazioniInpsScelte) {
				CsDSinaPresInps pres = new CsDSinaPresInps();
				CsDSinaPresInpsPK pk = new CsDSinaPresInpsPK();
				pk.setSinaId(idSina);
				pk.setPrestazioneInpsId(p);
				pres.setId(pk);
				em.merge(pres);
			}
			
		} catch (Exception e) {
			logger.error("savePrestazioneInpsSina " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
	}

	public List<String> getSinaPrestazioniInpsBySinaId(Long idSina) {
		List<String> lst = new ArrayList<String>();
		try {
			String sel = "SELECT PRESTAZIONE_INPS_ID FROM CS_D_SINA_PRES_INPS WHERE SINA_ID = :sinaId";
			Query q1 = em.createNativeQuery(sel);
			q1.setParameter("sinaId", idSina);
			lst = q1.getResultList();
		
		} catch (Exception e) {
			logger.error("getSinaPrestazioniInpsBySinaId " + e.getMessage(), e);
			throw new CarSocialeServiceException(e);
		}
		return lst;
	}
}
