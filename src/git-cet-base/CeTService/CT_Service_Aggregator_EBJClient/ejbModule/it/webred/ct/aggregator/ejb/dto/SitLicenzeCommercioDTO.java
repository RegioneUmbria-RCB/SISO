package it.webred.ct.aggregator.ejb.dto;

import it.webred.ct.data.model.licenzecommercio.SitLicenzeCommercio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SitLicenzeCommercioDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String annoProtocollo;

	private String carattere;

	private String civico;

	private String civico2;

	private String civico3;

	private String codiceFabbricato;

	private Date dataFineSospensione;

	private Date dataInizioSospensione;

	private Date dataRilascio;

	private Date dataValidita;

	private Date dtExpDato;

	private Date dtFineDato;

	private Date dtFineVal;

	private Date dtInizioDato;

	private Date dtInizioVal;

	private BigDecimal flagDtValDato;

	private String foglio;

	private String note;

	private String numero;

	private String numeroProtocollo;

	private String particella;

	private String provenienza;

	private String ragSoc;

	private String riferimAtto;

	private String sezioneCatastale;

	private String stato;

	private String subalterno;

	private BigDecimal superficieMinuto;

	private BigDecimal superficieTotale;

	private String tipologia;

	private String zona;
	
	public SitLicenzeCommercioDTO(){}

	public SitLicenzeCommercioDTO(SitLicenzeCommercio lic){
		this.annoProtocollo = lic.getAnnoProtocollo();
		this.carattere = lic.getCarattere();
		this.civico = lic.getCivico();
		this.civico2 = lic.getCivico2();
		this.civico3 = lic.getCivico3();
		this.codiceFabbricato = lic.getCodiceFabbricato();
		this.dataFineSospensione = lic.getDataFineSospensione();
		this.dataInizioSospensione = lic.getDataInizioSospensione();
		this.dataRilascio = lic.getDataRilascio();
		this.dataValidita = lic.getDataValidita();
		this.dtExpDato = lic.getDtExpDato();
		this.dtFineDato = lic.getDtFineDato();
		this.dtFineVal = lic.getDtFineVal();
		this.dtInizioDato = lic.getDtInizioDato();
		this.dtInizioVal = lic.getDtInizioVal();
		this.flagDtValDato = lic.getFlagDtValDato();
		this.foglio = lic.getFoglio();
		this.note = lic.getNote();
		this.numero = lic.getNumero();
		this.numeroProtocollo = lic.getNumeroProtocollo();
		this.particella = lic.getParticella();
		this.provenienza = lic.getProvenienza();
		this.ragSoc = lic.getRagSoc();
		this.riferimAtto = lic.getRiferimAtto();
		this.sezioneCatastale = lic.getSezioneCatastale();
		this.stato = lic.getStato();
		this.subalterno =  lic.getSubalterno();
		this.superficieMinuto = lic.getSuperficieMinuto();
		this.superficieTotale = lic.getSuperficieTotale();
		this.tipologia = lic.getTipologia();
		this.zona = lic.getZona();
	}
	
	public String getAnnoProtocollo() {
		return annoProtocollo;
	}

	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}

	public String getCarattere() {
		return carattere;
	}

	public void setCarattere(String carattere) {
		this.carattere = carattere;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCivico2() {
		return civico2;
	}

	public void setCivico2(String civico2) {
		this.civico2 = civico2;
	}

	public String getCivico3() {
		return civico3;
	}

	public void setCivico3(String civico3) {
		this.civico3 = civico3;
	}

	public String getCodiceFabbricato() {
		return codiceFabbricato;
	}

	public void setCodiceFabbricato(String codiceFabbricato) {
		this.codiceFabbricato = codiceFabbricato;
	}

	public Date getDataFineSospensione() {
		return dataFineSospensione;
	}

	public void setDataFineSospensione(Date dataFineSospensione) {
		this.dataFineSospensione = dataFineSospensione;
	}

	public Date getDataInizioSospensione() {
		return dataInizioSospensione;
	}

	public void setDataInizioSospensione(Date dataInizioSospensione) {
		this.dataInizioSospensione = dataInizioSospensione;
	}

	public Date getDataRilascio() {
		return dataRilascio;
	}

	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}

	public Date getDataValidita() {
		return dataValidita;
	}

	public void setDataValidita(Date dataValidita) {
		this.dataValidita = dataValidita;
	}

	public Date getDtExpDato() {
		return dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public Date getDtFineDato() {
		return dtFineDato;
	}

	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}

	public Date getDtFineVal() {
		return dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public Date getDtInizioDato() {
		return dtInizioDato;
	}

	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}

	public Date getDtInizioVal() {
		return dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
	}

	public BigDecimal getFlagDtValDato() {
		return flagDtValDato;
	}

	public void setFlagDtValDato(BigDecimal flagDtValDato) {
		this.flagDtValDato = flagDtValDato;
	}

	public String getFoglio() {
		return foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getParticella() {
		return particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getProvenienza() {
		return provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getRagSoc() {
		return ragSoc;
	}

	public void setRagSoc(String ragSoc) {
		this.ragSoc = ragSoc;
	}

	public String getRiferimAtto() {
		return riferimAtto;
	}

	public void setRiferimAtto(String riferimAtto) {
		this.riferimAtto = riferimAtto;
	}

	public String getSezioneCatastale() {
		return sezioneCatastale;
	}

	public void setSezioneCatastale(String sezioneCatastale) {
		this.sezioneCatastale = sezioneCatastale;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getSubalterno() {
		return subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public BigDecimal getSuperficieMinuto() {
		return superficieMinuto;
	}

	public void setSuperficieMinuto(BigDecimal superficieMinuto) {
		this.superficieMinuto = superficieMinuto;
	}

	public BigDecimal getSuperficieTotale() {
		return superficieTotale;
	}

	public void setSuperficieTotale(BigDecimal superficieTotale) {
		this.superficieTotale = superficieTotale;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

}
