package it.webred.ct.data.access.basic.common.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.catasto.CatastoServiceException;
import it.webred.ct.data.model.common.SitEnte;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class CommonJPAImpl extends CTServiceBaseDAO implements CommonDAO {

	private static final long serialVersionUID = 1L;

	public SitEnte getEnte() {
		
		SitEnte ente = null;
		try{
			logger.debug("ESTRAZIONE ENTE in corso..");
			Query q = manager_diogene.createNamedQuery("SitEnte.getEnte");
			ente = (SitEnte)q.getSingleResult();
			logger.debug("ESTRAZIONE ENTE result["+ente.getCodent()+"]");
		} catch (NoResultException nre) {	
			logger.error("Ente non presente.");
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		return ente;
		
	}
	
	public  List<Object[]> executeNativeQuery(String query) {
				
		try{
			
			logger.debug("ESECUZIONE QUERY: " + query); 
			Query q = manager_diogene
			.createNativeQuery(query);
			return q.getResultList();

		} catch (NoResultException nre) {	
			logger.error("Nessun risultato.");
		} catch (Throwable t) {
			logger.error("", t);
			throw new CatastoServiceException(t);
		}
		
		return null;
		
	}
	

}
