package it.siso.isee.obj;

import java.io.Serializable;

/***
 * (Quadro S2 - Modulo MS)
 * @author Francesco Pellegrini
 *
 */
public class VariazioneDichiarazione extends VariazioneBase implements Serializable {

	private String codiceFiscale;
	/***
	 * Reddito da lavoro dipendente, pensione e assimilati degli ultimi 12 mesi.
	 */
	private Integer redditoDipendente12Mesi;
	/***
	 * Reddito da attività d’impresa o lavoro autonomo degli ultimi 12 mesi.
	 */
	private Integer redditoAutonomo12Mesi;
	private Integer trattamentiAssistenziali12Mesi;
	private Integer redditoDipendente2Mesi;
	private Integer redditoAutonomo2Mesi;
	private Integer trattamentiAssistenziali2Mesi;
	
	/***
	 * obbligatorio, estremi della documentazione probante la variazione
	 * (Quadro S4 - Modulo MS)
	 */
	private String documentazione;
	
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public Integer getRedditoDipendente12Mesi() {
		return redditoDipendente12Mesi;
	}
	public void setRedditoDipendente12Mesi(Integer redditoDipendente12Mesi) {
		this.redditoDipendente12Mesi = redditoDipendente12Mesi;
	}
	public Integer getRedditoAutonomo12Mesi() {
		return redditoAutonomo12Mesi;
	}
	public void setRedditoAutonomo12Mesi(Integer redditoAutonomo12Mesi) {
		this.redditoAutonomo12Mesi = redditoAutonomo12Mesi;
	}
	public Integer getTrattamentiAssistenziali12Mesi() {
		return trattamentiAssistenziali12Mesi;
	}
	public void setTrattamentiAssistenziali12Mesi(Integer trattamentiAssistenziali12Mesi) {
		this.trattamentiAssistenziali12Mesi = trattamentiAssistenziali12Mesi;
	}
	public Integer getRedditoDipendente2Mesi() {
		return redditoDipendente2Mesi;
	}
	public void setRedditoDipendente2Mesi(Integer redditoDipendente2Mesi) {
		this.redditoDipendente2Mesi = redditoDipendente2Mesi;
	}
	public Integer getRedditoAutonomo2Mesi() {
		return redditoAutonomo2Mesi;
	}
	public void setRedditoAutonomo2Mesi(Integer redditoAutonomo2Mesi) {
		this.redditoAutonomo2Mesi = redditoAutonomo2Mesi;
	}
	public Integer getTrattamentiAssistenziali2Mesi() {
		return trattamentiAssistenziali2Mesi;
	}
	public void setTrattamentiAssistenziali2Mesi(Integer trattamentiAssistenziali2Mesi) {
		this.trattamentiAssistenziali2Mesi = trattamentiAssistenziali2Mesi;
	}
	public String getDocumentazione() {
		return documentazione;
	}
	public void setDocumentazione(String documentazione) {
		this.documentazione = documentazione;
	}
	
}
