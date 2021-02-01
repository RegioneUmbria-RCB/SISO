package it.webred.ct.data.access.basic.indice.dao;


import it.webred.ct.data.model.indice.SitEnteSorgente;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

public class IndiceBaseDAO implements Serializable {

	@PersistenceContext(unitName="CT_Diogene")
	protected EntityManager manager;


	protected static Logger logger = Logger.getLogger("ctservice.log");

	public List<SitEnteSorgente> getListaEntiSorgenti() throws IndiceDAOException{
		
		try{
			logger.debug("LISTA ENTE SORGENTE");
			Query q = manager.createNamedQuery("SitEnteSorgente.getEntiSorgenti");
			return q.getResultList();
			
		}catch (Throwable t) {			
			throw new IndiceDAOException(t);
		}
		
	}
	
	public SitEnteSorgente getEnteSorgente(long id) throws IndiceDAOException{
		
		try{
			logger.debug("ENTE SORGENTE");
			Query q = manager.createNamedQuery("SitEnteSorgente.getEnteSorgente").setParameter("id", id);
			return (SitEnteSorgente) q.getSingleResult();
			
		}catch (Throwable t) {			
			throw new IndiceDAOException(t);
		}
		
	}

	/*

	public AmFonteTipoinfo getFonteTipoinfo(Integer idEnte, BigDecimal prog) throws IndiceDAOException{
		
	try{
		
		return (AmFonteTipoinfo) amProfilerService.getFonteTipoInfo(idEnte, prog);
	
	}catch (Throwable t) {			
		throw new IndiceDAOException(t);
	}
		
	}
	*/
	
}

