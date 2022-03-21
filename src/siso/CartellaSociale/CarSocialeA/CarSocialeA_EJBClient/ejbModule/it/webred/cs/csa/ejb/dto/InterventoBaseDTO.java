package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

public class InterventoBaseDTO implements Serializable {
	
	private String tipoIntervento;
	private String tipoInterventoCustom;
	private String inizio;
	private String fine;
	private Boolean erogato;
	
	public String getInizio() {
		return inizio;
	}
	public String getFine() {
		return fine;
	}
	public Boolean getErogato() {
		return erogato;
	}
	public void setInizio(String inizio) {
		this.inizio = inizio;
	}
	public void setFine(String fine) {
		this.fine = fine;
	}
	public void setErogato(Boolean erogato) {
		this.erogato = erogato;
	}
	public String getTipoIntervento() {
		return tipoIntervento;
	}
	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	public String getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}
	public void setTipoInterventoCustom(String tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}

}
