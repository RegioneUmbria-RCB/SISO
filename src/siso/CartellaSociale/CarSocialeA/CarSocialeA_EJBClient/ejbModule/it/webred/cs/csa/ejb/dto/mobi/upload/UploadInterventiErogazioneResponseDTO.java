package it.webred.cs.csa.ejb.dto.mobi.upload;

import java.io.Serializable;

public class UploadInterventiErogazioneResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public boolean esito;
	public String messaggio;
	public boolean isEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}

	
}
