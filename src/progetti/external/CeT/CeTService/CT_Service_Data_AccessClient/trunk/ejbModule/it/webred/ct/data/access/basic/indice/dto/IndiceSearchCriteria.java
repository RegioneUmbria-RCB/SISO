package it.webred.ct.data.access.basic.indice.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;

public class IndiceSearchCriteria extends CeTBaseObject implements Serializable{
	
	//Ricerca per soggetto
	private String denominazione;
	private String codiceFiscale;
	private String partitaIva;
	
	//Ricerca per oggetto
	private String foglio;
	private String particella;
	private String sub;
	
	//Ricerca per via
	private String indirizzo;
	
	//Ricerca per civico
	private String civico;
	
	//Ricerca per unico
	private String unicoId;
	private String enteSorgenteId;
	private String progressivoES;
	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getEnteSorgenteId() {
		return enteSorgenteId;
	}
	public void setEnteSorgenteId(String enteSorgenteId) {
		this.enteSorgenteId = enteSorgenteId;
	}
	public String getProgressivoES() {
		return progressivoES;
	}
	public void setProgressivoES(String progressivoES) {
		this.progressivoES = progressivoES;
	}
	public String getUnicoId() {
		return unicoId;
	}
	public void setUnicoId(String unicoId) {
		this.unicoId = unicoId;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
}
