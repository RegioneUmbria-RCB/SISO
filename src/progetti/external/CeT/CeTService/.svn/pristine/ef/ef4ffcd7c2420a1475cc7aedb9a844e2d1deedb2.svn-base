package it.webred.ct.config.luoghi;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.luoghi.LuoghiServiceException;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

@Stateless
public class LuoghiServiceBean implements
		LuoghiService {

	protected static Logger logger = Logger.getLogger("ctservice.log");
	
	@PersistenceContext(unitName = "ConfigDataModel")
	protected EntityManager manager;
	
	public AmTabComuni getComuneItaByIstat(String codIstat){

		if(codIstat != null){
			
			try {
				Query q = manager.createNamedQuery("AmTabComuni.getComuneByIstat");
				q.setParameter("codistat", new BigDecimal(codIstat));
				List<AmTabComuni> lista = q.getResultList();
				if(lista.size() > 0)
					return lista.get(0);
				
				logger.warn("LuoghiServiceBean.getComuneItaByIstat: " + codIstat+ " - No Results");
				return null;
	
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	public AmTabComuni getComuneItaByBelfiore(String belfiore){

		if(belfiore != null){
			try {
				Query q = manager
						.createNamedQuery("AmTabComuni.getComuneByBelfiore");
				q.setParameter("belfiore", belfiore);
				List<AmTabComuni> lista = q.getResultList();
				if(lista.size() > 0)
					return lista.get(0);
				
				logger.warn("LuoghiServiceBean.getComuneItaByBelfiore: " + belfiore+ " - No Results");
				return null;
	
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	public AmTabComuni getComuneItaByDenominazione(String denominazione){

		if(denominazione != null){
			try {
				Query q = manager
						.createNamedQuery("AmTabComuni.getComuneByDenominazione");
				q.setParameter("denominazione", denominazione.toUpperCase());
				List<AmTabComuni> lista = q.getResultList();
				if(lista.size() > 0)
					return lista.get(0);
				
				logger.warn("LuoghiServiceBean.getComuneItaByDenominazione: " + denominazione+ " - No Results");
				return null;
	
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	public List<AmTabComuni> getComuniItaByDenominazioneStartsWith(String denominazione){

		if(denominazione != null){
			try {
				Query q = manager
						.createNamedQuery("AmTabComuni.getComuneByDenominazioneStartsWith");
				q.setParameter("denominazione", denominazione.toUpperCase());
				return q.getResultList();
				
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	public List<AmTabComuni> getComuniItaByDenominazioneContains(String denominazione){

		if(denominazione != null){
			try {
				Query q = manager
						.createNamedQuery("AmTabComuni.getComuneByDenominazioneContains");
				q.setParameter("denominazione", denominazione.toUpperCase());
				return q.getResultList();
				
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	public AmTabComuni getComuneItaByDenominazioneProvincia(String denominazione, String provincia){

		if(denominazione != null && provincia != null){
			try {
				Query q = manager
						.createNamedQuery("AmTabComuni.getComuneByDenominazioneProvincia");
				q.setParameter("denominazione", denominazione.toUpperCase());
				q.setParameter("provincia", provincia.toUpperCase());
				List<AmTabComuni> lista = q.getResultList();
				if(lista.size() > 0)
					return lista.get(0);
				
				logger.warn("LuoghiServiceBean.getComuneItaByDenominazioneProvincia: " + denominazione+ " - No Results");
				return null;
	
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	public List<AmTabComuni> getComuniIta(){

		try {
			Query q = manager
					.createNamedQuery("AmTabComuni.getComune");
			return q.getResultList();

		} catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}

	}
	

	public AmTabNazioni getNazioneById(String codice){
		try{
			
			return manager.find(AmTabNazioni.class, codice);
			
		}catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}
	}
	
	public AmTabNazioni getNazioneByIstat(String codIstat){

		try {
			Query q = manager
					.createNamedQuery("AmTabNazioni.getNazioneByIstat");
			q.setParameter("codistat", codIstat);
			List<AmTabNazioni> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);
			
			logger.warn("LuoghiServiceBean.getNazioneByIstat: " + codIstat+ " - No Results");
			return null;

		} catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}

	}
	
	public AmTabNazioni getNazioneByCodCie(String codCie){

		try {
			Query q = manager
					.createNamedQuery("AmTabNazioni.getNazioneByCodCie");
			q.setParameter("codcie", codCie);
			List<AmTabNazioni> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);
			
			logger.warn("LuoghiServiceBean.getNazioneByCodCie: " + codCie+ " - No Results");
			return null;

		} catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}

	}
	
	public AmTabNazioni getNazioneBySigla(String sigla){

		try {
			Query q = manager
					.createNamedQuery("AmTabNazioni.getNazioneBySigla");
			q.setParameter("sigla", sigla);
			List<AmTabNazioni> lista = q.getResultList();
			if(lista.size() > 0)
				return lista.get(0);
			
			logger.warn("LuoghiServiceBean.getNazioneBySigla: " + sigla+ " - No Results");
			return null;

		} catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}

	}
	
	public AmTabNazioni getNazioneByDenominazione(String nazione) {
		
		if(nazione != null){
			try {
				Query q = manager.createNamedQuery("AmTabNazioni.getNazioneByDenominazione");
				q.setParameter("nazione", nazione.toUpperCase());
				List<AmTabNazioni> lista = q.getResultList();
				if(lista.size() > 0)
					return lista.get(0);
				
				logger.warn("LuoghiServiceBean.getNazioneByDenominazione: " + nazione+ " - No Results");
				return null;
				
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;
		
	}
	
	public List<AmTabNazioni> getNazioni(){

		try {
			Query q = manager
					.createNamedQuery("AmTabNazioni.getNazioni");
			return q.getResultList();

		} catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}

	}
	
	@Override
	public List<AmTabNazioni> getNazioniByDenominazioneStartsWith(String nazione){

		if(nazione != null){
			try {
				Query q = manager
						.createNamedQuery("AmTabNazioni.getNazioneByDenominazioneStartsWith");
				q.setParameter("nazione", nazione.toUpperCase());
				return q.getResultList();
				
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	@Override
	public List<AmTabNazioni> getNazioniByDenominazioneContains(String nazione){

		if(nazione != null){
			try {
				Query q = manager
						.createNamedQuery("AmTabNazioni.getNazioneByDenominazioneContains");
				q.setParameter("nazione", nazione.toUpperCase());
				return q.getResultList();
				
			} catch (Throwable t) {
				throw new LuoghiServiceException(t);
			}
		}else return null;

	}
	
	public List<String> getCittadinanze(){

		try {
			Query q1 = manager
					.createNamedQuery("AmTabNazioni.getCittadinanzeNoIt");
			List<String> noIt = q1.getResultList();
			
			Query q2 = manager
					.createNamedQuery("AmTabNazioni.getCittadinanzaIt");
			List<String> it = q2.getResultList();
			
			it.addAll(noIt);
			
			return it;
			

		} catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}

	}
	
	public List<String> getComuniRegione(){

		try {
			Query q = manager
					.createNativeQuery("select distinct cod_nazionale from am_tab_comuni where cod_istat_regione = " +
						"(select cod_istat_regione from am_tab_comuni where cod_nazionale = " + 
						"(select belfiore from am_comune where rownum = 1  and descrizione != '-'))" +
						"and cod_nazionale is not null");
			return q.getResultList();

		} catch (Throwable t) {
			throw new LuoghiServiceException(t);
		}

	}

}
