package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.jboss.logging.Logger;

import it.webred.ct.config.model.AmTabNazioni;

/**
 * Session Bean implementation class NazioniCacheHelper
 */
@Stateless
@LocalBean
public class NazioniCacheHelper implements NazioniCacheHelperRemote, NazioniCacheHelperLocal {

	public static Logger logger = Logger.getLogger("rilevazionepresenze.log");
	
    @EJB private AmTabNazioniAsyncWrapper amTabNazioniAsyncWrapper;
    
    private Future<List<AmTabNazioni>> futureListaNazioni;
    private List<AmTabNazioni> listaNazioni;
    private Map<String,AmTabNazioni> mappaNazioni;
    /**
     * Default constructor. 
     */
    public NazioniCacheHelper() {
		logger.debug("NazioniCacheHelper - COSTRUTTORE");
    }
    
	public boolean isListaNazioniCaricata() {
		if(futureListaNazioni!=null) {
			return futureListaNazioni.isDone();
		}else {
			return false;
		}
	}
	
	private void caricaNazioniAsync() {
		futureListaNazioni = amTabNazioniAsyncWrapper.caricaNazioniDaDatabse();
	}
	
	@PostConstruct
	private void init() {
		logger.info("avvio init - caricmamento async chache comuni e nazioni");
		caricaNazioniAsync();
	}
	
	@Override
	public AmTabNazioni getNazioneByCodiceAnagrafe(String codiceAnagrafe) {		
		if(codiceAnagrafe==null) {
			logger.warn("getNazioneByCodiceAnagrafe(String codiceAnagrafe) con codice NULL");
			return null;
		}
		
		AmTabNazioni res= null;
		if(getMappaNazioni()!=null) {
			res = getMappaNazioni().get(codiceAnagrafe);
		}else {
			logger.info("mappa nazioni non disponibile. Eseguo query su DB");
			try {
				//TODO: non ho ilmetodo per la query con codice anagrafe ... posso usare questo oppure devo scrivere query?
				res = amTabNazioniAsyncWrapper.getNazioneByCodiceGenerico(codiceAnagrafe).get();
			} catch (InterruptedException e) {
				logger.warn("Caricamento asincono lista nazioni interrotto",e);
			} catch (ExecutionException e) {
				logger.warn("Errore durante caricamento asincono lista nazioni",e);
			}
		}
		if(res==null) {
			logger.warn("getNazioneByCodiceAnagrafe RETURN NULL per codiceIStat: "+codiceAnagrafe);
		}
	
		return res;		
	}	
	
	public Map<String, AmTabNazioni> getMappaNazioni() {
		if(mappaNazioni==null && getListaNazioni()!=null) {
			logger.debug("mappaComuni NULL. Dati disponibili costruisco mappa.");
			mappaNazioni =  costruisciMappaNazioni(getListaNazioni());
		}
		return mappaNazioni;
	}

	private Map<String, AmTabNazioni> costruisciMappaNazioni(List<AmTabNazioni> lista) {
		if(lista==null) {
			logger.warn("impossibile inizializzare mappa nazioni da una lista vuota");
			return null;
		}
		
		logger.debug("inizializzo mappa nazioni da lista contenente N record: "+lista.size());
		Map<String, AmTabNazioni> mappa = new HashMap<>();
		for(AmTabNazioni nazione : lista) {
			mappa.put(nazione.getCodNazioneAnagrafe(), nazione);
		}
		return mappa;
	}

	@Override
	public List<AmTabNazioni> trovaNazioniPerDenominazione(String query) {
		List<AmTabNazioni> result= new ArrayList<AmTabNazioni>();
		
		if(getListaNazioni()!=null) {
			for(AmTabNazioni nazione : getListaNazioni()) {  
				//TODO: lasciare solo startsWith ? 
				if(nazione.getNazione().equals(query.toUpperCase())  || nazione.getNazione().startsWith(query.toUpperCase())) {
					result.add(nazione);						
				}				
			}
		}else {
			logger.info("getListaComuni - Elenco comuni non disponibile in cache eseguo query diretta");
			try {
				result = amTabNazioniAsyncWrapper.trovaComuniPerDenominazione(query).get();
			} catch (InterruptedException e) {
				logger.warn("Caricamento asincono lista comuni interrotto",e);
			} catch (ExecutionException e) {
				logger.warn("Errore durante caricamento asincono lista comuni",e);
			}
		}
		
		//TODO: query su lista e fall back query ditetta su DB
		if(result.isEmpty()) {
			
		}
		return result;
	}

	private List<AmTabNazioni> getListaNazioni() {
		if(listaNazioni==null && futureListaNazioni!=null && futureListaNazioni.isDone()) {
			try {
				logger.debug("inizializzo lista comuni");
				listaNazioni = futureListaNazioni.get();
			} catch (InterruptedException e) {
				logger.warn("Caricamento asincono lista nazioni interrotto",e);
			} catch (ExecutionException e) {
				logger.warn("Errore durante caricamento asincono lista nazioni",e);
			}
		}
		return listaNazioni;
	}		

}
