package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.ct.config.model.AmTabComuni;

/**
 * wrapper con metodi AsyncResult di AccessTableComuniSessionBean
 */
@Stateless
@LocalBean
public class AmTabComuniAsyncWrapper {

	@EJB(lookup = "java:global/CarSocialeA/CarSocialeA_EJB/AccessTableComuniSessionBean!it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote") 
	private AccessTableComuniSessionBeanRemote accessTableComuniSessionBeanRemote;
	

    /**
     * Default constructor. 
     */
    public AmTabComuniAsyncWrapper() {
        // TODO Auto-generated constructor stub
    }
    
    @Asynchronous
    public Future<List<AmTabComuni>> caricaComuniDaDatabse(){    		
		List<AmTabComuni> beanLstComuni = accessTableComuniSessionBeanRemote.getComuni();   		
    	return new AsyncResult<List<AmTabComuni>>(beanLstComuni);      	
    }
    
    @Asynchronous
    public Future<AmTabComuni> getByCodiceIstat(String codiceIstat) {
    	AmTabComuni res =  accessTableComuniSessionBeanRemote.getComuneByIstat(codiceIstat);
    	return new AsyncResult<AmTabComuni>(res);
    }

    @Asynchronous
	public Future<List<AmTabComuni>> trovaComuniPerDenominazione(String denominazione) {
    	List<AmTabComuni> res =  accessTableComuniSessionBeanRemote.getComuniByDenomContains(denominazione, false);
    	return new AsyncResult<List<AmTabComuni>>(res);
	}
 
}
