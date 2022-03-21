package it.webred.cs.csa.ejb.ejb.configurazione;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableComuniSessionBeanRemote;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless
public class AccessTableComuniSessionBean extends CarSocialeBaseSessionBean implements AccessTableComuniSessionBeanRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
    /**
     * Default constructor. 
     */
    public AccessTableComuniSessionBean() {
        // TODO Auto-generated constructor stub
    }

    
   public List<AmTabComuni> getComuniByDenomContains(String denominazione, boolean attivi) {
    	List<AmTabComuni> tutti = luoghiService.getComuniItaByDenominazioneContains(denominazione);
    	if(attivi){
    		List<AmTabComuni> lista = new ArrayList<AmTabComuni>();
	    	for(AmTabComuni a : tutti){
	    		if(a.getAttivo()!=null && a.getAttivo().booleanValue())
	    			lista.add(a);
	    	}
	    	return lista;
    	}else
    		return tutti;
    }
    
}

