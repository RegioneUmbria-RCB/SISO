package it.webred.ct.data.access.basic.redditianalitici.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.redditi.RedditiServiceException;
import it.webred.ct.data.access.basic.redditi.dto.KeySoggDTO;
import it.webred.ct.data.access.basic.redditianalitici.RedditiAnServiceException;
import it.webred.ct.data.model.redditi.analitici.RedAnFabbricati;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

public class RedditiAnJPAImpl extends CTServiceBaseDAO implements RedditiAnDAO {

	@Override
	public List<RedAnFabbricati> getQuadroRBFabbricatiByKeyAnno(KeySoggDTO rs)	throws RedditiAnServiceException {
		List<RedAnFabbricati> lista=new ArrayList<RedAnFabbricati>();
		 logger.debug("RedditiAnJPAImpl.getQuadroRBFabbricatiByKeyAnno ->" +
		 		"REDDITI PER SOGG-KEY: " + rs.getCodFis() + "|" + rs.getIdeTelematico()+ "|" +rs.getAnno());
			try { 
				Query q = manager_diogene.createNamedQuery("RedAnFabbricati.getQuadroRBFabbricatiByKeyAnno");
				q.setParameter("codFis", rs.getCodFis());
				q.setParameter("ideTel", rs.getIdeTelematico());
				q.setParameter("anno", rs.getAnno());
				lista = q.getResultList();
				logger.debug("Result size ["+lista.size()+"]");
				
			}catch (NoResultException nre) {
				logger.warn("RedditiAnJPAImpl.getQuadroRBFabbricatiByKeyAnno - No Result " + nre.getMessage());
			} catch (Throwable t) {
				logger.error("", t);
				throw new RedditiServiceException(t);
			}
		 return lista;
	}

}
