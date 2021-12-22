package it.umbriadigitale.soclav.util;

import it.webred.ct.support.validation.CeTToken;

public class EsitoAut {

	private int esito;
	
	private String messaggio;
	private Exception eccezione;
	private CeTToken cetToken = null;
			
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getEsito() {
		return esito;
	}

	public void setEsito(int esito) {
		this.esito = esito;
	}

	public String getMessaggio() {
		return messaggio;
	}
	

	public Exception getEccezione() {
		return eccezione;
	}

	public void setEccezione(Exception eccezione) {
		this.eccezione = eccezione;
		if(eccezione != null)
			this.messaggio = (this.messaggio == null ? "" + eccezione.getMessage() : this.messaggio.concat(" - ") + eccezione.getMessage());
		
	}

	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	public CeTToken getCetToken() {
		return cetToken;
	}

	public void setCetToken(CeTToken cetToken) {
		this.cetToken = cetToken;
	}
	
	
}
