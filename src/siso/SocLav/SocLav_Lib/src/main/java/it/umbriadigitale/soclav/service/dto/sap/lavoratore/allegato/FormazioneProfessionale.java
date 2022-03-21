package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class FormazioneProfessionale implements Serializable {

	/*
	 * 		
			<titolocorso>SILVICOLTURA/FORESTAZIONE</titolocorso>    
			<ente>Regione Marche</ente>    
			<codregione>11</codregione>    
			<durata>120</durata>    
			<codtipologiadurata>O</codtipologiadurata>    
			<certificazioniattestati>F</certificazioniattestati>    
			<stage>NO</stage>
	*/
	
	private String titolocorso;
	private String ente;
	private String codregione;
	private String durata;
	private String codtipologiadurata;
	private String certificazioniattestati;
	private String stage;
	
	private String destipologiadurata;
	private String descertificazioniattestati;
	private String desregione;
	
	public String getTitolocorso() {
		return titolocorso;
	}
	public void setTitolocorso(String titolocorso) {
		this.titolocorso = titolocorso;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getCodregione() {
		return codregione;
	}
	public void setCodregione(String codregione) {
		this.codregione = codregione;
	}
	public String getDurata() {
		return durata;
	}
	public void setDurata(String durata) {
		this.durata = durata;
	}
	public String getCodtipologiadurata() {
		return codtipologiadurata;
	}
	public void setCodtipologiadurata(String codtipologiadurata) {
		this.codtipologiadurata = codtipologiadurata;
	}
	public String getCertificazioniattestati() {
		return certificazioniattestati;
	}
	public void setCertificazioniattestati(String certificazioniattestati) {
		this.certificazioniattestati = certificazioniattestati;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getDestipologiadurata() {
		return destipologiadurata;
	}
	public void setDestipologiadurata(String destipologiadurata) {
		this.destipologiadurata = destipologiadurata;
	}
	public String getDescertificazioniattestati() {
		return descertificazioniattestati;
	}
	public void setDescertificazioniattestati(String descertificazioniattestati) {
		this.descertificazioniattestati = descertificazioniattestati;
	}
	public String getDesregione() {
		return desregione;
	}
	public void setDesregione(String desregione) {
		this.desregione = desregione;
	}
	
}
