package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;

public class ParticellaKeyDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String idSezc;
	private String foglio;
	private String particella;
	public String getIdSezc() {
		return idSezc;
	}
	public void setIdSezc(String idSezc) {
		this.idSezc = idSezc;
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
	
}
