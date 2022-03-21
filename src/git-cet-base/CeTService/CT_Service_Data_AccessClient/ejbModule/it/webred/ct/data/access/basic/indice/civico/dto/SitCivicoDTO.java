package it.webred.ct.data.access.basic.indice.civico.dto;

import it.webred.ct.data.model.indice.SitEnteSorgente;

import java.io.Serializable;

public class SitCivicoDTO implements Serializable{

	private String hash;
	private String civico;
	private String progressivo;
	private SitEnteSorgente sitEnteSorgente;
	private String informazione;
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(String progressivo) {
		this.progressivo = progressivo;
	}
	public SitEnteSorgente getSitEnteSorgente() {
		return sitEnteSorgente;
	}
	public void setSitEnteSorgente(SitEnteSorgente sitEnteSorgente) {
		this.sitEnteSorgente = sitEnteSorgente;
	}
	public String getInformazione() {
		return informazione;
	}
	public void setInformazione(String informazione) {
		this.informazione = informazione;
	}	
	
}
