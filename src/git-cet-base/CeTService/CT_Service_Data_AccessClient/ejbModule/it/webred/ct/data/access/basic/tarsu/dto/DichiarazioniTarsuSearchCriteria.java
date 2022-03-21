
package it.webred.ct.data.access.basic.tarsu.dto;

import java.io.Serializable;


public class DichiarazioniTarsuSearchCriteria implements Serializable {

	private String cod_nazionale;
	private String classe;

	private String foglio;
	private String particella;
	private String unimm;
	
	//Ricerca per indirizzo
	private String via;
	private String civico;
	
	//Ricerca per soggetto 
	private String soggettoF;
	private String soggettoG;
	
	private String[] titoliSoggetto;
	
	//Operatori di ricerca delle stringhe
	private String viaOp;
	
	private String nomeSoggettoOp;
	private String cognomeSoggettoOp;
	
	private String denominazioneSoggettoOp;
	
	
	public String getCod_nazionale() {
		return cod_nazionale;
	}
	public void setCod_nazionale(String codNazionale) {
		cod_nazionale = codNazionale;
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
	
	public String getUnimm() {
		return unimm;
	}
	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public String getClasse() {
		return classe;
	}
	public void setClasse(String classe) {
		this.classe = classe;
	}
	public String[] getTitoloSoggetto() {
		return titoliSoggetto;
	}
	public void setTitoloSoggetto(String[] titoloSoggetto) {
		this.titoliSoggetto = titoloSoggetto;
	}
	public String[] getTitoliSoggetto() {
		return titoliSoggetto;
	}
	public void setTitoliSoggetto(String[] titoliSoggetto) {
		this.titoliSoggetto = titoliSoggetto;
	}
	public String getViaOp() {
		return viaOp;
	}
	public void setViaOp(String viaOp) {
		this.viaOp = viaOp;
	}
	public String getNomeSoggettoOp() {
		return nomeSoggettoOp;
	}
	public void setNomeSoggettoOp(String nomeSoggettoOp) {
		this.nomeSoggettoOp = nomeSoggettoOp;
	}
	public String getCognomeSoggettoOp() {
		return cognomeSoggettoOp;
	}
	public String getSoggettoF() {
		return soggettoF;
	}
	public void setSoggettoF(String soggettoF) {
		this.soggettoF = soggettoF;
	}
	public String getSoggettoG() {
		return soggettoG;
	}
	public void setSoggettoG(String soggettoG) {
		this.soggettoG = soggettoG;
	}
	public void setCognomeSoggettoOp(String cognomeSoggettoOp) {
		this.cognomeSoggettoOp = cognomeSoggettoOp;
	}
	public String getDenominazioneSoggettoOp() {
		return denominazioneSoggettoOp;
	}
	public void setDenominazioneSoggettoOp(String denominazioneSoggettoOp) {
		this.denominazioneSoggettoOp = denominazioneSoggettoOp;
	}
}
