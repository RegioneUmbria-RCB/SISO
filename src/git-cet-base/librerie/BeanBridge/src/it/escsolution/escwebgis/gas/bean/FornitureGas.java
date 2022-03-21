package it.escsolution.escwebgis.gas.bean;

import it.escsolution.escwebgis.common.EscObject;

import java.io.Serializable;

public class FornitureGas extends EscObject implements Serializable {

	private String id;
	private String idExt;
	private String idOrig;

	private String tipologiaFornitura;
	private String annoRiferimento;
	private String codiceCatastaleUtenza;
	private String cfErogante;
	private String cfTitolareUtenza;
	private String tipoSoggetto;
	private String cognomeUtente;
	private String nomeUtente;
	private String sesso;
	private String dataNascita;
	private String descComuneNascita;
	private String siglaProvNascita;
	private String ragioneSociale;
	
	private String identificativoUtenza;
	private String tipoUtenza;
	private String indirizzoUtenza;
	private String capUtenza;
	private String spesaConsumoNettoIva;
	private String nMesiFatturazione;
	
	private String segnoAmmontFatturato;
	private String ammontFatturato;
	private String consumoFatturato;
	private String esitoCtrlFormale;
	private String esitoCtrlFormaleQual;
	
	
	public String getIdentificativoUtenza() {
		return identificativoUtenza;
	}
	public void setIdentificativoUtenza(String identificativoUtenza) {
		this.identificativoUtenza = identificativoUtenza;
	}
	public String getTipoUtenza() {
		return tipoUtenza;
	}
	public void setTipoUtenza(String tipoUtenza) {
		this.tipoUtenza = tipoUtenza;
	}
	public String getIndirizzoUtenza() {
		return indirizzoUtenza;
	}
	public void setIndirizzoUtenza(String indirizzoUtenza) {
		this.indirizzoUtenza = indirizzoUtenza;
	}
	public String getCapUtenza() {
		return capUtenza;
	}
	public void setCapUtenza(String capUtenza) {
		this.capUtenza = capUtenza;
	}
	public String getSpesaConsumoNettoIva() {
		return spesaConsumoNettoIva;
	}
	public void setSpesaConsumoNettoIva(String spesaConsumoNettoIva) {
		this.spesaConsumoNettoIva = spesaConsumoNettoIva;
	}
	public String getnMesiFatturazione() {
		return nMesiFatturazione;
	}
	public void setnMesiFatturazione(String nMesiFatturazione) {
		this.nMesiFatturazione = nMesiFatturazione;
	}
	public String getSegnoAmmontFatturato() {
		return segnoAmmontFatturato;
	}
	public void setSegnoAmmontFatturato(String segnoAmmontFatturato) {
		this.segnoAmmontFatturato = segnoAmmontFatturato;
	}
	public String getAmmontFatturato() {
		return ammontFatturato;
	}
	public void setAmmontFatturato(String ammontFatturato) {
		this.ammontFatturato = ammontFatturato;
	}
	public String getConsumoFatturato() {
		return consumoFatturato;
	}
	public void setConsumoFatturato(String consumoFatturato) {
		this.consumoFatturato = consumoFatturato;
	}
	public String getEsitoCtrlFormale() {
		return esitoCtrlFormale;
	}
	public void setEsitoCtrlFormale(String esitoCtrlFormale) {
		this.esitoCtrlFormale = esitoCtrlFormale;
	}
	public String getEsitoCtrlFormaleQual() {
		return esitoCtrlFormaleQual;
	}
	public void setEsitoCtrlFormaleQual(String esitoCtrlFormaleQual) {
		this.esitoCtrlFormaleQual = esitoCtrlFormaleQual;
	}



	private String provenienza;
	
	
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdExt() {
		return idExt;
	}
	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}
	public String getIdOrig() {
		return idOrig;
	}
	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}
	public String getTipologiaFornitura() {
		return tipologiaFornitura;
	}
	public void setTipologiaFornitura(String tipologiaFornitura) {
		this.tipologiaFornitura = tipologiaFornitura;
	}
	public String getAnnoRiferimento() {
		return annoRiferimento;
	}
	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}
	public String getCodiceCatastaleUtenza() {
		return codiceCatastaleUtenza;
	}
	public void setCodiceCatastaleUtenza(String codiceCatastaleUtenza) {
		this.codiceCatastaleUtenza = codiceCatastaleUtenza;
	}
	public String getCfErogante() {
		return cfErogante;
	}
	public void setCfErogante(String cfErogante) {
		this.cfErogante = cfErogante;
	}
	public String getCfTitolareUtenza() {
		return cfTitolareUtenza;
	}
	public void setCfTitolareUtenza(String cfTitolareUtenza) {
		this.cfTitolareUtenza = cfTitolareUtenza;
	}
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}
	public String getCognomeUtente() {
		return cognomeUtente;
	}
	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}
	public String getNomeUtente() {
		return nomeUtente;
	}
	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
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
	public String getDescComuneNascita() {
		return descComuneNascita;
	}
	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
	}
	public String getSiglaProvNascita() {
		return siglaProvNascita;
	}
	public void setSiglaProvNascita(String siglaProvNascita) {
		this.siglaProvNascita = siglaProvNascita;
	}
	public String getRagioneSociale() {
		return ragioneSociale;
	}
	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	
	
}
