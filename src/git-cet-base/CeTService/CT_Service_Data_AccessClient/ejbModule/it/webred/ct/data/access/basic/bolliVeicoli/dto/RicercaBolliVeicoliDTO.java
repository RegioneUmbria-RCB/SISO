package it.webred.ct.data.access.basic.bolliVeicoli.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.Date;

public class RicercaBolliVeicoliDTO extends CeTBaseObject implements Serializable{
	
	private static final long serialVersionUID = -8365392274532463200L;
	
	private String codiceFiscalePartitaIva = "";
	private String ragioneSociale = "";
	private String cognome = "";
	private String nome = "";
	private String targa = "";
	private Date dataPrimaImmatricolazioneDal = null;
	private Date dataPrimaImmatricolazioneAl = null;
	private Date dataPrimaImmatricolazioneIl = null;

	private Integer limit = 0;

	public String getCodiceFiscalePartitaIva() {
		return codiceFiscalePartitaIva;
	}

	public void setCodiceFiscalePartitaIva(String codiceFiscalePartitaIva) {
		this.codiceFiscalePartitaIva = codiceFiscalePartitaIva;
	}

	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
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

	public String getTarga() {
		return targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public Date getDataPrimaImmatricolazioneDal() {
		return dataPrimaImmatricolazioneDal;
	}

	public void setDataPrimaImmatricolazioneDal(Date dataPrimaImmatricolazioneDal) {
		this.dataPrimaImmatricolazioneDal = dataPrimaImmatricolazioneDal;
	}

	public Date getDataPrimaImmatricolazioneAl() {
		return dataPrimaImmatricolazioneAl;
	}

	public void setDataPrimaImmatricolazioneAl(Date dataPrimaImmatricolazioneAl) {
		this.dataPrimaImmatricolazioneAl = dataPrimaImmatricolazioneAl;
	}

	public Date getDataPrimaImmatricolazioneIl() {
		return dataPrimaImmatricolazioneIl;
	}

	public void setDataPrimaImmatricolazioneIl(Date dataPrimaImmatricolazioneIl) {
		this.dataPrimaImmatricolazioneIl = dataPrimaImmatricolazioneIl;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
