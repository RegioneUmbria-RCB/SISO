package it.webred.cs.csa.ejb.client;

import it.webred.ct.config.model.AmTabComuni;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableComuniSessionBeanRemote {

	public List<AmTabComuni> getComuni();
	
	public AmTabComuni getComuneByIstat(String codIstat);
	
	public AmTabComuni getComuneByBelfiore(String belfiore);
	
	public List<AmTabComuni> getComuniByDenomContains(String denominazione, boolean attivi);
	
	public AmTabComuni getComuneByDenominazioneProv(String denominazione, String provincia);
	
}

