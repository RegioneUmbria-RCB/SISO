package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabNazioni;

import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class AmTabNazioniAsyncWrapper
 */
@Stateless
@LocalBean
public class AmTabNazioniAsyncWrapper {

	private static final long serialVersionUID = 1L;
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
    public AmTabNazioniAsyncWrapper() {
        // TODO Auto-generated constructor stub
    }
    
    @Asynchronous
    public Future<List<AmTabNazioni>> caricaNazioniDaDatabse(){
    		
		List<AmTabNazioni> beanLstComuni = luoghiService.getNazioni();
   			
    	return new AsyncResult<List<AmTabNazioni>>(beanLstComuni);      	
    }
    
    @Asynchronous
    public Future<AmTabNazioni> getNazioneByCodiceGenerico(String codice) {
    	AmTabNazioni res =  luoghiService.getNazioneByCodiceGenerico(codice);
    	return new AsyncResult<AmTabNazioni>(res);
    }

    @Asynchronous
	public Future<List<AmTabNazioni>> trovaComuniPerDenominazione(String denominazione) {
    	List<AmTabNazioni> res =  luoghiService.getNazioniByDenominazioneContains(denominazione);
    	return new AsyncResult<List<AmTabNazioni>>(res);
	}    

}
