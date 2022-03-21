package it.webred.ct.data.access.basic.indice.soggetto.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.model.indice.SitSoggettoTotale;
import it.webred.ct.data.model.indice.SitSoggettoUnico;

public class SoggettoJPAImpl extends IndiceBaseDAO implements SoggettoDAO{

	@Override
	public int deleteById(long id) throws IndiceDAOException {
		
		try{
			
			Query q = manager.createNamedQuery("SitSoggettoUnico.deleteById");
			q.setParameter("id", id);
			int deleted = q.executeUpdate();
			
			return deleted;
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	}

	@Override
	public List<Object[]> getListaTotale1(String sql, int startm, int numberRecord) throws IndiceDAOException {
		
		List<Object[]> result = null;
		
		try{
			
			Query q = manager.createQuery(sql);
			if(startm != 0 || numberRecord != 0){
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
			if(startm != 0 || numberRecord != 0){
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
			ol = (Long) q.getSingleResult();
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return ol;
	}

	@Override
	public List<SitSoggettoUnico> getListaUnico(int startm, int numberRecord,String sql) throws IndiceDAOException {
		
		try{
			
			Query q = manager.createQuery(sql);
			if(startm != 0 || numberRecord != 0){
				q.setFirstResult(startm);
				q.setMaxResults(numberRecord);
			}			
			return q.getResultList();
			
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	
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
	public List<SitSoggettoTotale> getSoggettoTotaleByHashByEs(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitSoggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByHashByEs");
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
	public List<SitSoggettoTotale> getSoggettoTotaleByUnico(BigDecimal idUnicoOld) throws IndiceDAOException {

		List<SitSoggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByUnico");
			q.setParameter("idUnico", idUnicoOld);
			result = q.getResultList();
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public List<SitSoggettoTotale> getSoggettoTotaleNativaByHash(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitSoggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleNativaByHash");
			q.setParameter("hash", criteria.getHash());
			result = q.getResultList();
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public SitSoggettoUnico getSoggettoUnicoById(BigDecimal id) throws IndiceDAOException {

		SitSoggettoUnico example = null;
		
		try{
			Query q = manager.createNamedQuery("SitSoggettoUnico.getSoggettoUnicoById");
			q.setParameter("id", id.longValue());
			example = (SitSoggettoUnico) q.getSingleResult();
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return example;
	}

	@Override
	public List<SitSoggettoTotale> invalidaSitSoggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitSoggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByHashByEs");
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
	public void mergeSoggetto(SitSoggettoTotale sogg) throws IndiceDAOException {
		
		try{
			manager.merge(sogg);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
	}

	@Override
	public void persist(SitSoggettoUnico nuovo) throws IndiceDAOException {
		
		try{
			manager.persist(nuovo);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	}

	@Override
	public List<SitSoggettoTotale> validaSitSoggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitSoggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitSoggettoTotale.getSoggettoTotaleByHashByEs");
			q.setParameter("hash", criteria.getHash());
			q.setParameter("enteSorgente", criteria.getIdSorgente());
			q.setParameter("progEs", new Long(criteria.getProgressivo()).longValue());
			result = q.getResultList();
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

}
