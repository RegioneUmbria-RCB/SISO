package it.webred.ct.data.access.basic.fornitureelettriche.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class FornituraElettricaDTO  extends CeTBaseObject implements Serializable{

	private static final long serialVersionUID = -8859487096037373438L;
	
	private String sezione = "";
	private String foglio = "";
	private String particella = "";
	private String subalterno = "";
	
	private String tipoArea = "";
	private String via = "";
	private String civico = "";

	private String identificativo = "";
	private String tipologia = "";
	private String denominazione = "";
	private String dataNascita = "";
	
	private String annoUtenza = "";
	private String tipologiaUtenza = "";
	private String codiceUtenza = "";
	
	private Integer limit = 0;
	
	public FornituraElettricaDTO() {
	}//-------------------------------------------------------------------------

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
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

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipoArea() {
		return tipoArea;
	}

	public void setTipoArea(String tipoArea) {
		this.tipoArea = tipoArea;
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

	public String getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(String identificativo) {
		this.identificativo = identificativo;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getAnnoUtenza() {
		return annoUtenza;
	}

	public void setAnnoUtenza(String annoUtenza) {
		this.annoUtenza = annoUtenza;
	}

	public String getTipologiaUtenza() {
		return tipologiaUtenza;
	}

	public void setTipologiaUtenza(String tipologiaUtenza) {
		this.tipologiaUtenza = tipologiaUtenza;
	}

	public String getCodiceUtenza() {
		return codiceUtenza;
	}

	public void setCodiceUtenza(String codiceUtenza) {
		this.codiceUtenza = codiceUtenza;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	

}
