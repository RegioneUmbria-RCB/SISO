package it.webred.ct.data.access.basic.cnc.flusso750.dto;

import java.io.Serializable;

import it.webred.ct.data.access.basic.cnc.CNCSearchCriteria;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULPartita;
import it.webred.ct.data.model.cnc.flusso750.ChiaveULRuolo;

public class Flusso750SearchCriteria extends CNCSearchCriteria implements Serializable {

	private ChiaveULRuolo chiaveRuolo = new ChiaveULRuolo();
	private ChiaveULPartita chiavePartita = new ChiaveULPartita(); 
	private String tipoInt = "D";
	private String codiceTributo;//cod_entrata
	private String codiceTipoTributo;//tipo_entrata
	private String codiceFiscale;
	private String pIva;
	
	public ChiaveULRuolo getChiaveRuolo() {
		return chiaveRuolo;
	}
	public void setChiaveRuolo(ChiaveULRuolo chiaveRuolo) {
		this.chiaveRuolo = chiaveRuolo;
	}
	public ChiaveULPartita getChiavePartita() {
		return chiavePartita;
	}
	public void setChiavePartita(ChiaveULPartita chiavePartita) {
		this.chiavePartita = chiavePartita;
	}
	public String getTipoInt() {
		return tipoInt;
	}
	public void setTipoInt(String tipoInt) {
		this.tipoInt = tipoInt;
	}
	public String getCodiceTributo() {
		return codiceTributo;
	}
	public void setCodiceTributo(String codiceTributo) {
		this.codiceTributo = codiceTributo;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getpIva() {
		return pIva;
	}
	public void setpIva(String pIva) {
		this.pIva = pIva;
	}
	public String getCodiceTipoTributo() {
		return codiceTipoTributo;
	}
	public void setCodiceTipoTributo(String codiceTipoTributo) {
		this.codiceTipoTributo = codiceTipoTributo;
	}
	
	
	
	
}
