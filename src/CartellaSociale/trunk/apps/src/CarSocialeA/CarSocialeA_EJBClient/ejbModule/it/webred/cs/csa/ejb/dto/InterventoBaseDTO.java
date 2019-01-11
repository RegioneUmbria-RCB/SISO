package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class InterventoBaseDTO implements Serializable {
	
	private CsCTipoIntervento tipoIntervento;
	private CsCTipoInterventoCustom tipoInterventoCustom;
	private String inizio;
	private String fine;
	private Boolean erogato;
	
	
	public CsCTipoIntervento getTipoIntervento() {
		return tipoIntervento;
	}
	public CsCTipoInterventoCustom getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}
	public String getInizio() {
		return inizio;
	}
	public String getFine() {
		return fine;
	}
	public Boolean getErogato() {
		return erogato;
	}
	public void setTipoIntervento(CsCTipoIntervento tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}
	public void setTipoInterventoCustom(CsCTipoInterventoCustom tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
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

}
