package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import java.util.List;
import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.ct.config.model.AmTabNazioni;

/**
 * Session Bean implementation class AmTabNazioniAsyncWrapper
 */
@Stateless
@LocalBean
public class AmTabNazioniAsyncWrapper {

	@EJB(lookup = "java:global/CarSocialeA/CarSocialeA_EJB/AccessTableNazioniSessionBean!it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote") 
	private AccessTableNazioniSessionBeanRemote accessTableNazioniSessionBeanRemote;
    /**
     * Default constructor. 
     */
    public AmTabNazioniAsyncWrapper() {
        // TODO Auto-generated constructor stub
    }
    
    @Asynchronous
    public Future<List<AmTabNazioni>> caricaNazioniDaDatabse(){
    		
		List<AmTabNazioni> beanLstComuni = accessTableNazioniSessionBeanRemote.getNazioni();
   			
    	return new AsyncResult<List<AmTabNazioni>>(beanLstComuni);      	
    }
    
    @Asynchronous
    public Future<AmTabNazioni> getNazioneByCodiceGenerico(String codice) {
    	AmTabNazioni res =  accessTableNazioniSessionBeanRemote.getNazioneByCodiceGenerico(codice);
    	return new AsyncResult<AmTabNazioni>(res);
    }

    @Asynchronous
	public Future<List<AmTabNazioni>> trovaComuniPerDenominazione(String denominazione) {
    	List<AmTabNazioni> res =  accessTableNazioniSessionBeanRemote.getNazioniByDenomContains(denominazione);
    	return new AsyncResult<List<AmTabNazioni>>(res);
	}    

}
