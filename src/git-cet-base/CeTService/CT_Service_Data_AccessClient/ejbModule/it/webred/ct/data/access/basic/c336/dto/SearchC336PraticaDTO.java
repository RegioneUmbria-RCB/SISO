package it.webred.ct.data.access.basic.c336.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class SearchC336PraticaDTO extends CeTBaseObject implements Serializable{
    
	private String sezione ;
	private String foglio ;
	private String  particella ;
	private String sub;
	private String codStato;
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
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getCodStato() {
		return codStato;
	}
	public void setCodStato(String codStato) {
		this.codStato = codStato;
	}
	

}
