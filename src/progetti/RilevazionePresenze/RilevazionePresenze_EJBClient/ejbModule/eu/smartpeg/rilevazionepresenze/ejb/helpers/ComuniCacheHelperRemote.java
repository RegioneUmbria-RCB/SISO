package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import java.util.List;

import javax.ejb.Remote;

import it.webred.ct.config.model.AmTabComuni;

@Remote
public interface ComuniCacheHelperRemote {
	public boolean isListaComuniCaricata();
	public AmTabComuni getComuneByCodiceIstat(String codiceIstat);
	public List <AmTabComuni> trovaComuniPerDenominazione(String query);
}
