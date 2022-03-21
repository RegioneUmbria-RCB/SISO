package it.webred.ct.data.access.basic.fornituregas.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class FornituraGasDTO  extends CeTBaseObject implements Serializable{

	private static final long serialVersionUID = 3367014570972566943L;
	
	private String tipoArea = "";
	private String via = "";
	private String civico = "";

	private String identificativo = "";
	private String tipologia = "";
	private String denominazione = "";
	private String cognome = "";
	private String nome = "";
	
	private String annoUtenza = "";
	private String tipologiaUtenza = "";
	private String codiceUtenza = "";
	
	private Integer limit = 0;
	
	public FornituraGasDTO() {
	}//-------------------------------------------------------------------------

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
	
	

}


