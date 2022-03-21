package it.escsolution.escwebgis.acqua.bean;

import it.escsolution.escwebgis.common.EscObject;
import it.escsolution.escwebgis.cosapNew.bean.CosapTassa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AcquaUtenze extends EscObject implements Serializable {


	private static final long serialVersionUID = 1L;
	String id;
	String idExtUtente;
	String codServizio;
	String descrCategoria;
	String qualificaTitolare;
	String tipologia;
	String tipoContratto;
	String dtUtenza;
	String ragSocUbicazione;
	String viaUbicazione;
	String civicoUbicazione;
	String capUbicazione;
	String comuneUbicazione;
	String tipologiaUi;
	String mesiFatturazione;
	String consumoMedio;
	String stacco;
	String giro;
	String fatturato;
	
	String denominazione;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdExtUtente() {
		return idExtUtente;
	}
	public void setIdExtUtente(String idExtUtente) {
		this.idExtUtente = idExtUtente;
	}
	public String getCodServizio() {
		return codServizio;
	}
	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}
	public String getDescrCategoria() {
		return descrCategoria;
	}
	public void setDescrCategoria(String descrCategoria) {
		this.descrCategoria = descrCategoria;
	}
	public String getQualificaTitolare() {
		return qualificaTitolare;
	}
	public void setQualificaTitolare(String qualificaTitolare) {
		this.qualificaTitolare = qualificaTitolare;
	}
	public String getTipologia() {
		return tipologia;
	}
	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	public String getTipoContratto() {
		return tipoContratto;
	}
	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}
	public String getDtUtenza() {
		return dtUtenza;
	}
	public void setDtUtenza(String dtUtenza) {
		this.dtUtenza = dtUtenza;
	}
	public String getRagSocUbicazione() {
		return ragSocUbicazione;
	}
	public void setRagSocUbicazione(String ragSocUbicazione) {
		this.ragSocUbicazione = ragSocUbicazione;
	}
	public String getViaUbicazione() {
		return viaUbicazione;
	}
	public void setViaUbicazione(String viaUbicazione) {
		this.viaUbicazione = viaUbicazione;
	}
	public String getCivicoUbicazione() {
		return civicoUbicazione;
	}
	public void setCivicoUbicazione(String civicoUbicazione) {
		this.civicoUbicazione = civicoUbicazione;
	}
	public String getCapUbicazione() {
		return capUbicazione;
	}
	public void setCapUbicazione(String capUbicazione) {
		this.capUbicazione = capUbicazione;
	}
	public String getComuneUbicazione() {
		return comuneUbicazione;
	}
	public void setComuneUbicazione(String comuneUbicazione) {
		this.comuneUbicazione = comuneUbicazione;
	}
	public String getTipologiaUi() {
		return tipologiaUi;
	}
	public void setTipologiaUi(String tipologiaUi) {
		this.tipologiaUi = tipologiaUi;
	}
	public String getMesiFatturazione() {
		return mesiFatturazione;
	}
	public void setMesiFatturazione(String mesiFatturazione) {
		this.mesiFatturazione = mesiFatturazione;
	}
	public String getConsumoMedio() {
		return consumoMedio;
	}
	public void setConsumoMedio(String consumoMedio) {
		this.consumoMedio = consumoMedio;
	}
	public String getStacco() {
		return stacco;
	}
	public void setStacco(String stacco) {
		this.stacco = stacco;
	}
	public String getGiro() {
		return giro;
	}
	public void setGiro(String giro) {
		this.giro = giro;
	}
	public String getFatturato() {
		return fatturato;
	}
	public void setFatturato(String fatturato) {
		this.fatturato = fatturato;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}	
	
}
