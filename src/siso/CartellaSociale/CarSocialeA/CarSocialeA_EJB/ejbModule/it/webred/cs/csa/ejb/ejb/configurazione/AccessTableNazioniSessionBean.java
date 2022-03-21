package it.webred.cs.csa.ejb.ejb.configurazione;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableNazioniSessionBeanRemote;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Session Bean implementation class AccessTableDataNazioniSessionBean
 */
@Stateless
public class AccessTableNazioniSessionBean extends CarSocialeBaseSessionBean implements AccessTableNazioniSessionBeanRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB(mappedName = "java:global/CT_Service/CT_Config_Manager/LuoghiServiceBean")
	protected LuoghiService luoghiService;
	
	/**
     * Default constructor. 
     */
    public AccessTableNazioniSessionBean() {
        // TODO Auto-generated constructor stub
    }

    @AuditSaltaValidazioneSessionID
    public List<AmTabNazioni> getNazioniByDenomContains(String nazione) {
    	
    	List<AmTabNazioni> lista = luoghiService.getNazioniByDenominazioneContains(nazione);
    	
    	return lista;
    	
    }
    
    @AuditSaltaValidazioneSessionID
    public AmTabNazioni getNazioneByCodiceGenerico(String codIstat){
    	
    	AmTabNazioni nazione = luoghiService.getNazioneByCodiceGenerico(codIstat);
    	
    	return nazione;
    }
    
    @AuditSaltaValidazioneSessionID
    public AmTabNazioni getNazioneByDenominazione(String denominazione) {
    	AmTabNazioni nazione = luoghiService.getNazioneByDenominazione(denominazione);
    	
    	return nazione;
    }
    
    @AuditSaltaValidazioneSessionID
    public  List<String>  getCittadinanze(){
    	
    	List<String> lista = luoghiService.getCittadinanze();
    	
    	return lista;
    	
    	
 	
    }

	@Override
	@AuditSaltaValidazioneSessionID
	public List<AmTabNazioni> getNazioni() {
		List<AmTabNazioni> naz = luoghiService.getNazioni(); 
		return naz;
	}


}
