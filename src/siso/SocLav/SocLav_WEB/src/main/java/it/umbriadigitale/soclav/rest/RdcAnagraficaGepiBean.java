package it.umbriadigitale.soclav.rest;
 
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.umbriadigitale.soclav.model.gepi.RdCAnagraficaGepi;
 
 

/**
 * An EJB singleton to maintain all processed message beans.
 *
 * @author Pavel Bucek
 */
@Stateless
public class RdcAnagraficaGepiBean implements RdcAnagraficaGepiInterface{

   	
    @PersistenceContext(unitName = "socLavDirectDB") 
    protected EntityManager em;
   
    public RdcAnagraficaGepiBean() {
        // initial content
      
    }
    
    
    
    public Messaggio getAnagraficheByCodEnte(String codEnte) {
    	Messaggio messOut = new Messaggio("0", "");
    	try {
    		Query query = em.createQuery("SELECT da FROM RdCAnagraficaGepi da WHERE resComuneCod = ?1");
    		query.setParameter(1, codEnte);
    		
    		messOut.setListRdcAnagraficheGepi((List<RdCAnagraficaGepi>) query.getResultList());
			

		}
		catch(Throwable t) {
			t.printStackTrace();
			messOut.setEsito("-1000");
			messOut.setMessaggio("Eccezione durante il caricamento delle anagrafiche Gepi collegate al codice Ente " + codEnte);
		}
    	return messOut;
    }

  

  

    
}
