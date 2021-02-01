package it.webred.ct.data.access.basic.fabbricato.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class SearchFabbricatoDTO extends CeTBaseObject implements Serializable{

	private String sezione ;
	private String foglio ;
	private String  particella ;
	private String  sub ;
	private boolean maiDichiarato;
	private boolean exRurale;
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
	public boolean isMaiDichiarato() {
		return maiDichiarato;
	}
	public void setMaiDichiarato(boolean maiDichiarato) {
		this.maiDichiarato = maiDichiarato;
	}
	public boolean isExRurale() {
		return exRurale;
	}
	public void setExRurale(boolean exRurale) {
		this.exRurale = exRurale;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	
}
