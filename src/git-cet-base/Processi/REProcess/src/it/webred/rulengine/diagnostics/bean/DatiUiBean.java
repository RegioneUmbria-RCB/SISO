package it.webred.rulengine.diagnostics.bean;

import java.io.Serializable;

public class DatiUiBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codNazionale;
	private Long foglio;
	private String particella;
	private Long unimm;
	private String sub;
	
	
	public DatiUiBean() {
		super();
	}
	public DatiUiBean(String codNazionale, Long foglio, String particella,
			Long unimm, String sub) {
		super();
		this.codNazionale = codNazionale;
		this.foglio = foglio;
		this.particella = particella;
		this.unimm = unimm;
		this.sub = sub;
	}
	public String getCodNazionale() {
		return codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public Long getFoglio() {
		return foglio;
	}
	public void setFoglio(Long foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	
	public Long getUnimm() {
		return unimm;
	}
	public void setUnimm(Long unimm) {
		this.unimm = unimm;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
}
