package it.webred.ct.data.access.basic.cnc.flusso290.dto;

import it.webred.ct.data.access.basic.cnc.CNCSearchCriteria;

import java.io.Serializable;

public class Flusso290SearchCriteria extends CNCSearchCriteria implements Serializable {
	
	private String progressivoMinuta;
	private String codiceComuneIscrizione;
	private String codicePartita;
	private String codiceFiscale;
	private String codiceTributo;
	
	public String getProgressivoMinuta() {
		return progressivoMinuta;
	}
	public void setProgressivoMinuta(String progressivoMinuta) {
		this.progressivoMinuta = progressivoMinuta;
	}
	public String getCodiceComuneIscrizione() {
		return codiceComuneIscrizione;
	}
	public void setCodiceComuneIscrizione(String codiceComuneIscrizione) {
		this.codiceComuneIscrizione = codiceComuneIscrizione;
	}
	public String getCodicePartita() {
		return codicePartita;
	}
	public void setCodicePartita(String codicePartita) {
		this.codicePartita = codicePartita;
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
