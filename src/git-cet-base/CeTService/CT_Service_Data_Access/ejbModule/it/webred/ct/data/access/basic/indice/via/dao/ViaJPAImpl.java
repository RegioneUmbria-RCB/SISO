package it.webred.ct.data.access.basic.indice.via.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;

import it.webred.ct.data.access.basic.common.dto.StringheVie;
import it.webred.ct.data.access.basic.indice.IndiceDataIn;
import it.webred.ct.data.access.basic.indice.civico.dao.CivicoQueryBuilder;
import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.model.indice.SitCivicoTotale;
import it.webred.ct.data.model.indice.SitCivicoUnico;
import it.webred.ct.data.model.indice.SitViaTotale;
import it.webred.ct.data.model.indice.SitViaUnico;

public class ViaJPAImpl extends IndiceBaseDAO implements ViaDAO{

	

	@Override
	public List<SitViaUnico> getListaUnico(int startm, int numberRecord, String sql) throws IndiceDAOException {
		
		try{
			
			Query q = manager.createQuery(sql);
			if (startm != 0 || numberRecord != 0) {
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}

			return q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
						
	}
	
	
	
	@Override
	public int deleteById(long id) throws IndiceDAOException {
					
		try{
			
			Query q = manager.createNamedQuery("SitViaUnico.deleteById");
			q.setParameter("id", id);
			int deleted = q.executeUpdate();
			
			return deleted;
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
	}

	@Override
	public List<SitCivicoTotale> getCivicoTotaleByViaTotale(SitViaTotale via) throws IndiceDAOException {

		List<SitCivicoTotale> result = null;
		
		try{
			Query q2 = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
			q2.setParameter("idVia", via.getId().getIdDwh());
			result = q2.getResultList();	
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
		
	}

	@Override
	public List<SitCivicoTotale> getCivicoTotaleByViaUnico(BigDecimal id) throws IndiceDAOException {

		List<SitCivicoTotale> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaUnico");
			q.setParameter("idVia", id);
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
		
	}

	@Override
	public SitCivicoUnico getCivicoUnicoById(SitCivicoTotale civicoTot) throws IndiceDAOException {

		SitCivicoUnico civicoUni = null;
		
		try{
			
			Query q3 = manager.createNamedQuery("SitCivicoUnico.getCivicoUnicoById");
			q3.setParameter("id", civicoTot.getFkCivico().longValue());
			civicoUni = (SitCivicoUnico) q3.getSingleResult();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}
		
		return civicoUni;
	}

	@Override
	public List<SitCivicoUnico> getCivicoUnicoByViaUnico(BigDecimal id) throws IndiceDAOException {

		List<SitCivicoUnico> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitCivicoUnico.getCivicoUnicoByViaUnico");
			q.setParameter("idVia", id);
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public List<Object[]> getListaTotale1(String sql, int startm, int numberRecord) throws IndiceDAOException {
		
		List<Object[]> result = null;
		
		try{
			Query q = manager.createQuery(sql);
			if (startm != 0 || numberRecord != 0) {
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}

			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
		
		return result;
	}

	@Override
	public List<Object[]> getListaTotale2(String sql) throws IndiceDAOException {
		
		List<Object[]> result = null;
		
		try{
			
			Query q = manager.createQuery(sql);
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
		
		return result;
	}

	@Override
	public List<Object[]> getListaTotaleBySorgente(String sql, int startm, int numberRecord) throws IndiceDAOException {
		
		List<Object[]> resultQuery = null;
		
		try{
			
			Query q = manager.createQuery(sql);
			if (startm != 0 || numberRecord != 0) {
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}

			resultQuery = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
		
		return resultQuery;
	}


	@Override
	public Long getListaTotaleRecordCount(String sql) throws IndiceDAOException {

		Long ol = 0L;
		
		try{
			
			Query q = manager.createQuery(sql);
			Object o = q.getSingleResult();
			ol = (Long) o;
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
		
		return ol;
	}
	
	@Override
	public Long getListaUnicoRecordCount(String sql) throws IndiceDAOException {
		
		Long ol = 0L;
		
		try{
						
			Query q = manager.createQuery(sql);
			Object o = q.getSingleResult();
			ol = (Long) o;
						
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return ol;
				
	}
	

	@Override
	public List<SitViaTotale> getViaTotaleByHashByEs(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitViaTotale> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	
		return result;
	}

	@Override
	public List<SitViaTotale> getViaTotaleByUnico(BigDecimal idUnicoOld) throws IndiceDAOException {
		
		List<SitViaTotale> result = null;
		
		try{
		
			Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByUnico");
			q.setParameter("idUnico", idUnicoOld);
			result = q.getResultList();
		
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	
		return result;
	}
	
	@Override
	public List<SitViaTotale> getViaTotaleByUnicoFonte(BigDecimal idUnicoOld, String fonte, String progEs ) throws IndiceDAOException {
		
		List<SitViaTotale> result = null;
		
		try{
			
			logger.debug("RICERCA VIA TOTALE [idUnico: "+idUnicoOld +"," +
											 "fonte: "+fonte +"," +
											 "progEs: "+progEs+ "]");
		
			Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByUnicoFonte");
			q.setParameter("idUnico", idUnicoOld);
			q.setParameter("enteSorgente", fonte);
			q.setParameter("progEs", progEs);
			result = q.getResultList();
			
			logger.debug("Result["+result.size()+"]");
		
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	
		return result;
	}

	@Override
	public List<SitViaTotale> getViaTotaleNativaByHash(IndiceOperationCriteria criteria) throws IndiceDAOException {
				
		List<SitViaTotale> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleNativaByHash");
			q.setParameter("hash", criteria.getHash());
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public List<SitCivicoTotale> invalidaSitCivicoTotale(SitViaTotale via) throws IndiceDAOException {
		
		List<SitCivicoTotale> result = null;
		
		try{
			
			Query q2 = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
			q2.setParameter("idVia", via.getId().getIdDwh());
			result = q2.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;

	}

	@Override
	public List<SitViaTotale> invalidaSitViaTotale(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitViaTotale> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public void mergeCivico(SitCivicoTotale civico) throws IndiceDAOException {
		
		try{
			manager.merge(civico);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	}

	@Override
	public void mergeCivicoUnico(SitCivicoUnico civUnico) throws IndiceDAOException {

		try{
			manager.merge(civUnico);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	}

	@Override
	public void mergeVia(SitViaTotale via) throws IndiceDAOException {

		try{
			manager.merge(via);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
	}


	@Override
	public void persist(SitViaUnico nuovo) throws IndiceDAOException {
		
		try{
			manager.persist(nuovo);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
	}
	
	@Override
	public void persist(SitCivicoUnico nuovo) throws IndiceDAOException {
		
		try{
			manager.persist(nuovo);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
	}
	
	@Override
	public void deleteCivicoUnicoByIdVie(BigDecimal idVia1, BigDecimal idVia2) throws IndiceDAOException {
		
		try{
			
			Query q = manager.createNamedQuery("SitCivicoUnico.deleteByIdVia1IdVia2");
			q.setParameter("idVia1", idVia1);
			q.setParameter("idVia2", idVia2);
			int deleted = q.executeUpdate();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}		
	}

	@Override
	public List<SitCivicoTotale> validaSitCivicoTotale(SitViaTotale via) throws IndiceDAOException {

		List<SitCivicoTotale> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByViaTotale");
			q.setParameter("idVia", via.getId().getIdDwh());
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public List<SitViaTotale> validaSitViaTotale(IndiceOperationCriteria criteria) throws IndiceDAOException {
		
		List<SitViaTotale> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitViaTotale.getViaTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}
	
	public List<SitViaUnico> getListaViaUnicoByDescr(String toponimo, String via) throws IndiceDAOException {
		List<SitViaUnico> result = new ArrayList<SitViaUnico>();
		List<String> listaPrefissi = StringheVie.getToponimiDecoded(toponimo);
		if(listaPrefissi.size()==0)
			listaPrefissi.add(toponimo);
		
		//Estrae la strada da vie unico partendo da: toponimo, descrizione
		try {
			
			logger.debug("RICERCA VIA [toponimo: "+toponimo +"," +
									 "listaPrefissi: "+listaPrefissi +"," +
									 "descrizione: "+via+ "]");
		
			Query q = manager.createNamedQuery("SitViaUnico.getListaViaUnicoByDescr");
			q.setParameter("listaPrefissi", listaPrefissi);
			q.setParameter("toponimo", toponimo);
			q.setParameter("descrizione", via);
			result = q.getResultList();
			
			logger.debug("RESULT["+result.size()+"]");
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}
	
	public List<String> getDistinctCivicoUnicoByIdVie(BigDecimal idVia1, BigDecimal idVia2) throws IndiceDAOException {
		List<String> result = new ArrayList<String>();

		try {
		
			Query q = manager.createNamedQuery("SitCivicoUnico.getDistinctCivicoByVieUniche");
			q.setParameter("idVia1", idVia1);
			q.setParameter("idVia2", idVia2);
			result = q.getResultList();
			
			logger.debug("RESULT["+result.size()+"]");
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}
	
	@Override
	public List<SitCivicoTotale> getCivicoTotaleByVieUnicoCivico(BigDecimal idVia1, BigDecimal idVia2, String civico) throws IndiceDAOException {

		List<SitCivicoTotale> result = null;
		
		try{
			
			Query q = manager.createNamedQuery("SitCivicoTotale.getCivicoTotaleByVieUnicoCivico");
			q.setParameter("idVia1", idVia1);
			q.setParameter("idVia2", idVia2);
			q.setParameter("civico", civico);
			result = q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}
	
}
