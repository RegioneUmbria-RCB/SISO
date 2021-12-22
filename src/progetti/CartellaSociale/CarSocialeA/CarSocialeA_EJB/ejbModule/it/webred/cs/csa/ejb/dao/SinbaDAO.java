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

	@SuppressWarnings("unchecked")
	public CsDSinba getLastSchedaSinbaByIdCaso(Long idCaso, Long idTipoDiario) {
		CsDSinba csDSinba = null ;
		List<CsDSinba> lstI = new ArrayList<CsDSinba>();
		logger.info("getLastSchedaSinbaByCaso " + idCaso);
		try{
		String qq = "SELECT cds.*  FROM CS_D_SINBA cds " + 
				" INNER JOIN CS_D_DIARIO diario " + 
				" ON cds.DIARIO_ID = diario.ID " + 
				" WHERE diario.CASO_ID = :idCaso "+
				" AND diario.TIPO_DIARIO_ID = :idTipoDiario "+
				" AND ROWNUM = 1 " +
				" ORDER BY diario.DT_AMMINISTRATIVA  DESC  ";
				
		Query q = em.createNativeQuery(qq);
		q.setParameter("idCaso", idCaso);
		q.setParameter("idTipoDiario", idTipoDiario);
		
		lstI = q.getResultList();
		for (CsDSinba dd : lstI) {
			csDSinba = lstI.get(0);
		}
		
			logger.info("getLastSchedaSinbaByIdCaso id["+idCaso+"]");
		}catch(Throwable e){
			logger.error(e);
			throw new CarSocialeServiceException(e);
		}
		
		return csDSinba;
	}
	
}
