package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;

import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * wrapper con metodi AsyncResult di AccessTableComuniSessionBean
 */
@Stateless
@LocalBean
public class AmTabComuniAsyncWrapper {
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;

	

    /**
     * Default constructor. 
     */
    public AmTabComuniAsyncWrapper() {
        // TODO Auto-generated constructor stub
    }
    
    @Asynchronous
    public Future<List<AmTabComuni>> caricaComuniDaDatabse(){    		
		List<AmTabComuni> beanLstComuni = luoghiService.getComuniIta();
    	return new AsyncResult<List<AmTabComuni>>(beanLstComuni);      	
    }
    
    @Asynchronous
    public Future<AmTabComuni> getByCodiceIstat(String codiceIstat) {
    	AmTabComuni res =  luoghiService.getComuneItaByIstat(codiceIstat);
    	return new AsyncResult<AmTabComuni>(res);
    }
    
    @Asynchronous
    public Future<AmTabComuni> getByCodBelfiore(String codBelfiore) {
    	AmTabComuni res =  luoghiService.getComuneItaByBelfiore(codBelfiore);
    	return new AsyncResult<AmTabComuni>(res);
    }

    @Asynchronous
	public Future<List<AmTabComuni>> trovaComuniPerDenominazione(String denominazione) {
    	List<AmTabComuni> res =  luoghiService.getComuniItaByDenominazioneContains(denominazione);
    	return new AsyncResult<List<AmTabComuni>>(res);
	}
 
}
