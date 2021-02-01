package it.webred.ct.config.luoghi;

import java.util.List;

import it.webred.ct.config.model.AmTabComuni;
import it.webred.ct.config.model.AmTabNazioni;

import javax.ejb.Remote;

@Remote
public interface LuoghiService {

	public AmTabComuni getComuneItaByIstat(String codIstat);
	
	public AmTabComuni getComuneItaByBelfiore(String belfiore);
	
	public AmTabComuni getComuneItaByDenominazione(String denominazione);
	
	//public List<AmTabComuni> getComuniItaByDenominazioneStartsWith(String denominazione);
	
	public List<AmTabComuni> getComuniItaByDenominazioneContains(String denominazione);
	
	public List<AmTabComuni> getComuniIta();
	
	public List<String> getComuniRegione();
	
	public AmTabNazioni getNazioneById(String codice);
	
	public AmTabNazioni getNazioneByIstat(String codIstat);
	
	public AmTabNazioni getNazioneByCodCie(String codCie);
	
	public AmTabNazioni getNazioneBySigla(String sigla);
	
	public AmTabNazioni getNazioneByDenominazione(String nazione);
	
	public List<AmTabNazioni> getNazioni();
	
	public List<String> getCittadinanze();

	public AmTabComuni getComuneItaByDenominazioneProvincia(
			String denominazione, String provincia);

	public List<AmTabNazioni> getNazioniByDenominazioneStartsWith(String nazione);
	public List<AmTabNazioni> getNazioniByDenominazioneContains(String nazione);
}
