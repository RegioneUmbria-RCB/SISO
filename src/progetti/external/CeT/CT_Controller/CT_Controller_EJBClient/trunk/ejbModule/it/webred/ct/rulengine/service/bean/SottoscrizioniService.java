package it.webred.ct.rulengine.service.bean;
import java.util.List;

import it.webred.ct.rulengine.controller.model.RSottoscrittori;

import javax.ejb.Remote;

@Remote
public interface SottoscrizioniService {
	
	public void subscribe(RSottoscrittori rs);
	
	public void unsubscribe(String utente,String belfiore,String codCommand);
	
	public List<RSottoscrittori> getSottoscrizioni(String utente,String belfiore);
	
	public List<RSottoscrittori> getUtentiSottoscritti(String belfiore,String codCommand);
	
	public RSottoscrittori getSottoscrizione(String utente,String belfiore,String codCommand);
}
