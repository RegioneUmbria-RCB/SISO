package it.webred.ct.data.access.basic.compravendite.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class SoggettoCompravenditeDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tipoSoggetto;
	private String cognome ;
	private String nome;
	private String codiceFiscale;
	private String denominazione;
	private String codiceFiscaleG;
	private String flagTipoTitolContro;
	private String flagTipoTitolFavore;
	private String scCodiceDiritto = ""; 
	private String scQuotaNumeratore = "";
	private String scQuotaDenominatore = "";
	private String sfCodiceDiritto = ""; 
	private String sfQuotaNumeratore = "";
	private String sfQuotaDenominatore = "";
	private String sede = "";
	private String sesso = ""; 
	private String dataNascita = "";
	private String luogoNascita = "";
	private String iidNota = "";
	
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodiceFiscaleG() {
		return codiceFiscaleG;
	}
	public void setCodiceFiscaleG(String codiceFiscaleG) {
		this.codiceFiscaleG = codiceFiscaleG;
	}
	public String getFlagTipoTitolContro() {
		return flagTipoTitolContro;
	}
	public void setFlagTipoTitolContro(String flagTipoTitolContro) {
		this.flagTipoTitolContro = flagTipoTitolContro;
	}
	public String getFlagTipoTitolFavore() {
		return flagTipoTitolFavore;
	}
	public void setFlagTipoTitolFavore(String flagTipoTitolFavore) {
		this.flagTipoTitolFavore = flagTipoTitolFavore;
	}
	public String getScCodiceDiritto() {
		return scCodiceDiritto;
	}
	public void setScCodiceDiritto(String scCodiceDiritto) {
		this.scCodiceDiritto = scCodiceDiritto;
	}
	public String getScQuotaNumeratore() {
		return scQuotaNumeratore;
	}
	public void setScQuotaNumeratore(String scQuotaNumeratore) {
		this.scQuotaNumeratore = scQuotaNumeratore;
	}
	public String getScQuotaDenominatore() {
		return scQuotaDenominatore;
	}
	public void setScQuotaDenominatore(String scQuotaDenominatore) {
		this.scQuotaDenominatore = scQuotaDenominatore;
	}
	public String getSfCodiceDiritto() {
		return sfCodiceDiritto;
	}
	public void setSfCodiceDiritto(String sfCodiceDiritto) {
		this.sfCodiceDiritto = sfCodiceDiritto;
	}
	public String getSfQuotaNumeratore() {
		return sfQuotaNumeratore;
	}
	public void setSfQuotaNumeratore(String sfQuotaNumeratore) {
		this.sfQuotaNumeratore = sfQuotaNumeratore;
	}
	public String getSfQuotaDenominatore() {
		return sfQuotaDenominatore;
	}
	public void setSfQuotaDenominatore(String sfQuotaDenominatore) {
		this.sfQuotaDenominatore = sfQuotaDenominatore;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSede() {
		return sede;
	}
	public void setSede(String sede) {
		this.sede = sede;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	public String getIidNota() {
		return iidNota;
	}
	public void setIidNota(String iidNota) {
		this.iidNota = iidNota;
	}
	

}
