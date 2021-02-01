package it.webred.ct.config.parameters.fonte;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import it.webred.ct.config.model.AmFonte;
import it.webred.ct.config.model.AmFonteComune;
import it.webred.ct.config.model.AmFonteTipoinfo;
import it.webred.ct.config.parameters.CommonServiceBean;
import it.webred.ct.config.parameters.comune.ComuneServiceException;


@Stateless
public class FonteServiceBean extends CommonServiceBean implements FonteService {

	
	public List<AmFonte> getListaFonte() {

		try {
			logger.debug("LISTA FONTE");
			Query q = manager
					.createNamedQuery("AmFonte.getFonteBySection");
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new FonteServiceException(t);
		}
		
	}
	
	
	public List<AmFonteTipoinfo> getListaFonteTipoinfoByFonte(String fonte) {
		
		try {
			logger.debug("LISTA FONTETIPOINFO");
			
			Query q = manager
					.createNamedQuery("AmFonteTipoinfo.getFonteTipoinfoByFonte");
			q.setParameter("fonte", new Integer(fonte));
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new FonteServiceException(t);
		}
	}


	public AmFonte getFonte(Long idFonte) {
		
		try {
			logger.debug("AMFONTE BY ID");

			Query q = manager.createNamedQuery("AmFonte.getFonteById");
			q.setParameter("idFonte", idFonte.intValue());
			return (AmFonte) q.getSingleResult();

		} catch (Throwable t) {
			logger.error("", t);
			throw new FonteServiceException(t);
		}
	}


	public List<AmFonte> getListaFonteAll() {
		try {
			logger.debug("LISTA FONTI");
			Query q = manager.createNamedQuery("AmFonte.getFonte");
			return q.getResultList();

		} catch (Throwable t) {
			logger.error("", t);
			throw new FonteServiceException(t);
		}
	}
	
	/**
	 * Ritorna la lista delle fontin associate all'ente distinta per tipo fonte
	 * @param belfioreComune
	 * codice belfiore del comune  
	 * @param tipoFonte 
	 * se uguale (E) fonti esterne 
	 * se uguale (I) fonti interne
	 * se almeno uno dei due parametri non viene valorizzato ritorna tutte le fonti
	 * @return
	 */
	public List<AmFonte> getListaFonteByComuneETipoFonte(String belfiore, String tipoFonte) {
		try {
			if (belfiore == null || belfiore.trim().length()==0  
				|| tipoFonte == null || tipoFonte.trim().length()==0)
				return getListaFonteAll();
			
			logger.debug("LISTA FONTI BY TIPO FONTE "+ tipoFonte);
			Query q = manager
					.createNamedQuery("AmFonte.getFonteByComuneETipoFonte");			
			q.setParameter("tipoFonte", tipoFonte.toUpperCase());
			q.setParameter("comune", belfiore.toUpperCase());
			return q.getResultList();
			
		} catch (Throwable t) {
			logger.error("", t);
			throw new ComuneServiceException(t);
		}
	}	
}
