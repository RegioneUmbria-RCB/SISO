package it.webred.ct.data.access.basic.indice.oggetto.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Query;

import it.webred.ct.data.access.basic.indice.dao.IndiceBaseDAO;
import it.webred.ct.data.access.basic.indice.dao.IndiceDAOException;
import it.webred.ct.data.access.basic.indice.dto.IndiceOperationCriteria;
import it.webred.ct.data.model.indice.SitOggettoTotale;
import it.webred.ct.data.model.indice.SitOggettoUnico;

public class OggettoJPAImpl extends IndiceBaseDAO implements OggettoDAO{

	@Override
	public int deleteById(long id) throws IndiceDAOException {
		
		try{
			Query q = manager.createNamedQuery("SitOggettoUnico.deleteById");
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

		List<Object[]> resultQuery  = null;
			
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
	public List<SitOggettoUnico> getListaUnico(int startm, int numberRecord, String sql) throws IndiceDAOException {

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
	public List<SitOggettoTotale> getOggettoTotaleByHashByEs(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitOggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByHashByEs");
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
	public List<SitOggettoTotale> getOggettoTotaleByUnico(BigDecimal idUnicoOld)throws IndiceDAOException {

		List<SitOggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByUnico");
			q.setParameter("idUnico", idUnicoOld);
			result = q.getResultList();
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public List<SitOggettoTotale> getOggettoTotaleNativaByHash(IndiceOperationCriteria criteria) throws IndiceDAOException {
		
		List<SitOggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleNativaByHash");
			q.setParameter("hash", criteria.getHash());
			result = q.getResultList();
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
		return result;
	}

	@Override
	public SitOggettoUnico getOggettoUnicoById(BigDecimal id)
			throws IndiceDAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SitOggettoTotale> invalidaSitOggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException {

		List<SitOggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByHashByEs");
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
	public void mergeOggetto(SitOggettoTotale ogg) throws IndiceDAOException {
		 
		try{
			manager.merge(ogg);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
	}

	@Override
	public void persist(SitOggettoUnico nuovo) throws IndiceDAOException {
		
		try{
			manager.persist(nuovo);
		}catch(Throwable t) {
			throw new IndiceDAOException(t);
		}	
		
	}

	@Override
	public List<SitOggettoTotale> validaSitOggettoTotale(IndiceOperationCriteria criteria) throws IndiceDAOException {
		
		List<SitOggettoTotale> result = null;
		
		try{
			Query q = manager.createNamedQuery("SitOggettoTotale.getOggettoTotaleByHashByEs");
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
