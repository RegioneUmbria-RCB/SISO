package it.webred.cs.csa.ejb.client.configurazione;

import it.webred.ct.config.model.AmTabComuni;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableComuniSessionBeanRemote {

	public List<AmTabComuni> getComuniByDenomContains(String denominazione, boolean attivi);
	
}

