package it.webred.ct.data.access.basic.cnc.statoriscossione.dto;

import it.webred.ct.data.model.cnc.statoriscossione.ChiaveULStatoRiscossione;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class StatoRiscossioneSearchCriteria extends CeTBaseObject implements Serializable {

	private ChiaveULStatoRiscossione chiaveRiscossione = new ChiaveULStatoRiscossione();
	private String codiceFiscale;
	private String codiceTributo; // cod_entrata
	
	public ChiaveULStatoRiscossione getChiaveRiscossione() {
		return chiaveRiscossione;
	}
	public void setChiaveRiscossione(ChiaveULStatoRiscossione chiaveRiscossione) {
		this.chiaveRiscossione = chiaveRiscossione;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getCodiceTributo() {
		return codiceTributo;
	}
	public void setCodiceTributo(String codiceTributo) {
		this.codiceTributo = codiceTributo;
	}
	
	
}
