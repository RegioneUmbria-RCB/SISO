package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import it.webred.ct.config.model.AmTabComuni;

/**
 * Session Bean implementation class ComuniCacheHelper
 */
@Stateless
public class ComuniCacheHelper implements ComuniCacheHelperRemote,ComuniCacheHelperLocal {
	 
    
    public static Logger logger = Logger.getLogger("rilevazionepresenze.log");
    
    @EJB private AmTabComuniAsyncWrapper amTabComuniAsyncWrapper;
    
    private Map<String,AmTabComuni> mappaComuni=null; 
    private Map<String,AmTabComuni> mappaComuniBelfiore = null;
    private List<AmTabComuni> listaComuni = null;
    
    private Future<List<AmTabComuni>> futureListComuni;

	public ComuniCacheHelper() {
		logger.debug("ComuniNazioniCacheHelper - COSTRUTTORE");
	}
	
	public boolean isListaComuniCaricata() {
		if(futureListComuni!=null) {
			return futureListComuni.isDone();
		}else {
			return false;
		}
	}
	
	private void caricaComuniAsync() {
		futureListComuni = amTabComuniAsyncWrapper.caricaComuniDaDatabse();
	}
	
	@PostConstruct
	private void init() {
//		logger.info("avvio init @PostConstruct - caricamento async cache comuni e nazioni");
//		caricaComuniAsync();
	}
		
/**
 * Cerca comune per codice istat. 
 * Se lista mappa comuni è disponibile in memoria utilizza la mappa
 * altrimenti esegue query su DB
 */
	@Override
	public AmTabComuni getComuneByCodiceIstat(String codiceIstat) {
		if(codiceIstat==null) {
			logger.warn("getComuneByCodiceIstat(String codiceIstat) con codice NULL");
			return null;
		}
		
		AmTabComuni res= null;
		if(getMappaComuni()!=null) {
			res = getMappaComuni().get(codiceIstat);
		}else {
			logger.info("mappa comuni non disponibile. Eseguo query su DB");
			try {
				res = amTabComuniAsyncWrapper.getByCodiceIstat(codiceIstat).get();
			} catch (InterruptedException e) {
				logger.warn("Caricamento asincono lista comuni interrotto",e);
			} catch (ExecutionException e) {
				logger.warn("Errore durante caricamento asincono lista comuni",e);
			}
		}
		if(res==null) {
			logger.warn("getComuneByCodiceIstat RETURN NULL per codiceIStat: "+codiceIstat);
		}
	
		return res;
	}

	@Override
	public List<AmTabComuni> trovaComuniPerDenominazione(String query) {
		List<AmTabComuni> result= new ArrayList<AmTabComuni>();
		
		if(getListaComuni()!=null) {
			for(AmTabComuni comune : getListaComuni()) {  
				//TODO: lasciare solo startsWith ? 
				if(comune.getDenominazione().equals(query.toUpperCase())  || comune.getDenominazione().startsWith(query.toUpperCase())) {
					result.add(comune);						
				}				
			}
		}else {
			logger.info("getListaComuni - Elenco comuni non disponibile in cache eseguo query diretta");
			try {
				result = amTabComuniAsyncWrapper.trovaComuniPerDenominazione(query).get();
			} catch (InterruptedException e) {
				logger.warn("Caricamento asincrono lista comuni interrotto",e);
			} catch (ExecutionException e) {
				logger.warn("Errore durante caricamento asincrono lista comuni",e);
			}
		}
		
		//TODO: query su lista e fall back query ditetta su DB
		if(result.isEmpty()) {
			
		}
		return result;
	}
	
	/**
	 * Restituisce la mappa CdiceIStat-->AmTabComuni
	 * Quando i dati sono disponibili inizializza la struttura dati alla prima chiamata.
	 * Restituisce NULL se i dati non sono ancora disponibili o in caso di errore durante
	 * caricamento dati 
	 * @return
	 */
	private Map<String, AmTabComuni> getMappaComuni() {
		if(mappaComuni==null && getListaComuni()!=null) {
			logger.debug("mappaComuni NULL. Dati disponibili costruisco mappa.");
			costruisciMappaComuni(getListaComuni());
		}
		return mappaComuni;
	};
	
	private Map<String, AmTabComuni> getMappaComuniBelfiore() {
		if(mappaComuniBelfiore==null && getListaComuni()!=null) {
			logger.debug("mappaComuni NULL. Dati disponibili costruisco mappa.");
			costruisciMappaComuni(getListaComuni());
		}
		return mappaComuniBelfiore;
	};
	
	/** Restituisce la lista dei comuni caricati in memoria. Restituisce NULL se
	 * i comuni non sono stati ancora caricati (in attesa di risposta dal dB oppure errore durante il caricamento)
	 * @return listaComuni
	 */
	private List<AmTabComuni> getListaComuni() {
		if(listaComuni==null && futureListComuni!=null && futureListComuni.isDone()) {
			try {
				logger.debug("inizializzo lista comuni");
				listaComuni = futureListComuni.get();
			} catch (InterruptedException e) {
				logger.warn("Caricamento asincono lista comuni interrotto",e);
			} catch (ExecutionException e) {
				logger.warn("Errore durante caricamento asincono lista comuni",e);
			}
		}
				
		return listaComuni;
	}

	/**
	 * Costruisce un struttura dati di tipo Mappa CodiceIStat --> AmTabComune a partire dalla lista
	 * passata come parametro. Utilizzare questa struttura per ricerca veloce 
	 * dati in cache per "codiceIStat"
	 * @param listaComuni 
	 * @return mappa comuni indicizzata per "Codice Istat"
	 */
	private void costruisciMappaComuni(List<AmTabComuni> lista ){
		if(lista==null) {
			logger.warn("impossibile inizializzare mappa comuni da una lista vuota");
		}
		
		logger.debug("inizializzo mappa comuni da lista contenente N record: "+lista.size());
		
		this.mappaComuni = new HashMap<String, AmTabComuni>();
		this.mappaComuniBelfiore = new HashMap<String, AmTabComuni>();
		for(AmTabComuni comune : lista) {
			this.mappaComuni.put(comune.getCodIstatComune(), comune);
			this.mappaComuniBelfiore.put(comune.getCodNazionale(),comune);
		}	
	}
	
	@Override
	public AmTabComuni getComuneByCodBelfiore(String codBelfiore) {
		if(codBelfiore==null) {
			logger.warn("getComuneByCodBelfiore(String codBelfiore) con codice NULL");
			return null;
		}
		
		AmTabComuni res= null;
		if(getMappaComuniBelfiore()!=null) {
			res = getMappaComuniBelfiore().get(codBelfiore);
		}else {
			logger.info("mappa comuni non disponibile. Eseguo query su DB");
			try {
				res = amTabComuniAsyncWrapper.getByCodBelfiore(codBelfiore).get();
			} catch (InterruptedException e) {
				logger.warn("Caricamento asincono lista comuni interrotto",e);
			} catch (ExecutionException e) {
				logger.warn("Errore durante caricamento asincono lista comuni",e);
			}
		}
		if(res==null) {
			logger.warn("getComuneByCodBelfiore RETURN NULL per codiceBelfiore: "+codBelfiore);
		}
	
		return res;
	}

}
