package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

public class ConfiguratoreDatiEsterniDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeColonnaCodPrestazione = null;
	private String nomeColonnaTipoPrestazione =null;
	private String nomeColonnaCF = null;
	private String nomeColonnaEnte = null;
	private String nomeColonnaIndirizzo = null;
	private String nomeColonnaDataApertura = null;
	private String nomeColonnaDataChiusura = null;
	private String nomeColonnaServizioStato = null;
	private String nomeColonnaEntitaServizio = null;
	private String nomeColonnaEntitaServizioUnitaMisura = null;
	
	public String getNomeColonnaEntitaServizio() {
		return nomeColonnaEntitaServizio;
	}
	public void setNomeColonnaEntitaServizio(String nomeColonnaEntitaServizio) {
		this.nomeColonnaEntitaServizio = nomeColonnaEntitaServizio;
	}
	public String getNomeColonnaTipoPrestazione() {
		return nomeColonnaTipoPrestazione;
	}
	public void setNomeColonnaTipoPrestazione(String nomeColonnaTipoPrestazione) {
		this.nomeColonnaTipoPrestazione = nomeColonnaTipoPrestazione;
	}
	public String getNomeColonnaCF() {
		return nomeColonnaCF;
	}
	public void setNomeColonnaCF(String nomeColonnaCF) {
		this.nomeColonnaCF = nomeColonnaCF;
	}
	public String getNomeColonnaEnte() {
		return nomeColonnaEnte;
	}
	public void setNomeColonnaEnte(String nomeColonnaEnte) {
		this.nomeColonnaEnte = nomeColonnaEnte;
	}
	public String getNomeColonnaCodPrestazione() {
		return nomeColonnaCodPrestazione;
	}
	public void setNomeColonnaCodPrestazione(String nomeColonnaCodPrestazione) {
		this.nomeColonnaCodPrestazione = nomeColonnaCodPrestazione;
	}
	public String getNomeColonnaServizioStato() {
		return nomeColonnaServizioStato;
	}
	public void setNomeColonnaServizioStato(String nomeColonnaServizioStato) {
		this.nomeColonnaServizioStato = nomeColonnaServizioStato;
	}
	public String getNomeColonnaDataApertura() {
		return nomeColonnaDataApertura;
	}
	public void setNomeColonnaDataApertura(String nomeColonnaDataApertura) {
		this.nomeColonnaDataApertura = nomeColonnaDataApertura;
	}
	public String getNomeColonnaDataChiusura() {
		return nomeColonnaDataChiusura;
	}
	public void setNomeColonnaDataChiusura(String nomeColonnaDataChiusura) {
		this.nomeColonnaDataChiusura = nomeColonnaDataChiusura;
	}
	public String getNomeColonnaIndirizzo() {
		return nomeColonnaIndirizzo;
	}
	public void setNomeColonnaIndirizzo(String nomeColonnaIndirizzo) {
		this.nomeColonnaIndirizzo = nomeColonnaIndirizzo;
	}
	public String getNomeColonnaEntitaServizioUnitaMisura() {
		return nomeColonnaEntitaServizioUnitaMisura;
	}
	public void setNomeColonnaEntitaServizioUnitaMisura(String nomeColonnaEntitaServizioUnitaMisura) {
		this.nomeColonnaEntitaServizioUnitaMisura = nomeColonnaEntitaServizioUnitaMisura;
	}
}
