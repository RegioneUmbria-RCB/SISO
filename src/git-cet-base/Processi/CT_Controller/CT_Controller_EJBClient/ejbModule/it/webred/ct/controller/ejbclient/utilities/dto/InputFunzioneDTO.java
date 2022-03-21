package it.webred.ct.controller.ejbclient.utilities.dto;

import it.webred.ct.rulengine.controller.model.RConnection;

import java.io.Serializable;

public class InputFunzioneDTO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private RConnection connessione;
	private String belfiore;
	private String nomeTabella;
	
	public String getNomeTabella() {
		return nomeTabella;
	}

	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public RConnection getConnessione() {
		return connessione;
	}

	public void setConnessione(RConnection connessione) {
		this.connessione = connessione;
	}

	
}
