package it.webred.jsf.bean;

import it.webred.ct.config.model.AmTabComuni;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComuneBean {

	private String codIstatComune;
	private String denominazione;
	private String denominazione2;
	private String siglaProv;
	private Boolean attivo;
	
	public ComuneBean(){}
	
	public ComuneBean(String codIstatComune, String denominazione, String siglaPov) {
		this.codIstatComune = codIstatComune;
		this.denominazione = denominazione;
		this.siglaProv = siglaPov;	
	}
	
	public ComuneBean(AmTabComuni comune){
		this.codIstatComune=comune.getCodIstatComune();
		this.denominazione=comune.getDenominazione();
		this.denominazione2=comune.getDenominazione2();
		this.siglaProv = comune.getSiglaProv();
		this.attivo=comune.getAttivo()!=null && comune.getAttivo().booleanValue();
	}
	
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getCodIstatComune() {
		return codIstatComune;
	}
	public void setCodIstatComune(String codIstatComune) {
		this.codIstatComune = codIstatComune;
	}
	public String getSiglaProv() {
		return siglaProv;
	}
	public void setSiglaProv(String siglaProv) {
		this.siglaProv = siglaProv;
	}

	public String getDenominazione2() {
		return denominazione2;
	}
	public void setDenominazione2(String denominazione2) {
		this.denominazione2 = denominazione2;
	}

	public boolean isAttivo() {
		return attivo;
	}

	public void setAttivo(boolean attivo) {
		this.attivo = attivo;
	}

}
