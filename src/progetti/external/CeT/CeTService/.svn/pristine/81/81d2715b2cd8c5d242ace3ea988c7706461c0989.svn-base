package it.webred.ct.data.access.basic.successioni.dao;

import java.util.List;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.successioni.SuccessioniQueryBuilder;
import it.webred.ct.data.access.basic.successioni.SuccessioniServiceException;
import it.webred.ct.data.access.basic.successioni.dto.RicercaSuccessioniDTO;
import it.webred.ct.data.model.successioni.SuccessioniA;
import it.webred.ct.data.model.successioni.SuccessioniB;
import it.webred.ct.data.model.successioni.SuccessioniC;
import it.webred.ct.data.model.successioni.SuccessioniD;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class SuccessioniJPAImpl extends CTServiceBaseDAO implements SuccessioniDAO {
	
	private static final long serialVersionUID = -3505715273253851080L;

	public List<SuccessioniA> getSuccessioniAByPk(RicercaSuccessioniDTO rl) {
		List<SuccessioniA> lista=null;
		
		try {
			String hql = new SuccessioniQueryBuilder().createQuerySuccessioniAByCriteria(rl);
			logger.debug("getSuccessioniAByPk SUCCESSIONI PER PK PRATICA: " + hql);			
			Query q = manager_diogene.createQuery(hql);

			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("getSuccessioniAByPk TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("getSuccessioniAByPk TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new SuccessioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	public List<SuccessioniB> getSuccessioniBByPk(RicercaSuccessioniDTO rl) {
		List<SuccessioniB> lista=null;

		try {
			String hql = new SuccessioniQueryBuilder().createQuerySuccessioniBByCriteria(rl);
			logger.debug("getSuccessioniBByPk SUCCESSIONI PER PK PRATICA: " + hql);
			Query q = manager_diogene.createQuery(hql);

			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("getSuccessioniBByPk TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("getSuccessioniBByPk TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new SuccessioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	public List<SuccessioniC> getSuccessioniCByPk(RicercaSuccessioniDTO rl) {
		List<SuccessioniC> lista=null;
		
		try {
			String hql = new SuccessioniQueryBuilder().createQuerySuccessioniCByCriteria(rl);
			logger.debug("getSuccessioniCByPk SUCCESSIONI PER PK PRATICA: " + hql );			
			Query q = manager_diogene.createQuery(hql);

			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("getSuccessioniCByPk TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("getSuccessioniCByPk TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new SuccessioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	public List<SuccessioniD> getSuccessioniDByPk(RicercaSuccessioniDTO rl) {
		List<SuccessioniD> lista=null;
		
		try {
			String hql = new SuccessioniQueryBuilder().createQuerySuccessioniDByCriteria(rl);
			logger.debug("getSuccessioniDByPk SUCCESSIONI PER PK PRATICA" + hql);			
			Query q = manager_diogene.createQuery(hql);

			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("getSuccessioniDByPk TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("getSuccessioniDByPk TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new SuccessioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	public List<Object[]> getSuccessioniEreditaByParams(RicercaSuccessioniDTO rl) {
		List<Object[]> lista = null;
		
		try {
			String sql = new SuccessioniQueryBuilder().createQuerySuccessioniByParams(rl);
			logger.info("getSuccessioniEreditaByParams SUCCESSIONI PER PK PRATICA: " + sql );			
			Query q = manager_diogene.createNativeQuery(sql);

			if (rl.getLimit()>0)
				q.setFirstResult(0).setMaxResults(rl.getLimit());
			
			lista = q.getResultList();
			if (lista != null && lista.size()>0)
				logger.warn("getSuccessioniEreditaByParams TROVATO. N.ELE: "+ lista.size());
			else
				logger.warn("getSuccessioniEreditaByParams TROVATO. N.ELE: 0");
			
		}catch (NoResultException nre) {
			logger.warn("No Result " + nre.getMessage());
		} catch (Throwable t) {
			logger.error("", t);
			throw new SuccessioniServiceException(t);
		}
		return lista;
	}//-------------------------------------------------------------------------
	
	
}	



