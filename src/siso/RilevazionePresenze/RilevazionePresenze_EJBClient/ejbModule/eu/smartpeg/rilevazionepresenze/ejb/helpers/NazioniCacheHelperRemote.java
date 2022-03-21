package eu.smartpeg.rilevazionepresenze.ejb.helpers;

import java.util.List;

import javax.ejb.Remote;

import it.webred.ct.config.model.AmTabNazioni;

@Remote
public interface NazioniCacheHelperRemote {

	public boolean isListaNazioniCaricata();
	public List<AmTabNazioni> trovaNazioniPerDenominazione(String query);
	public AmTabNazioni getNazioneByCodiceAnagrafe(String codiceAnagrafe);
	public AmTabNazioni getNazioneByCodiceIstat(String codiceAnagrafe);
}
