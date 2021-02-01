package it.webred.ct.data.access.basic.catasto.dto;

import java.io.Serializable;

public class ParametriCatastaliDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String sezione;
	private String foglio;
	private String particella;
	private String subalterno;
	
	public ParametriCatastaliDTO(){}
	
	public ParametriCatastaliDTO(ParametriCatastaliDTO dto){
		sezione = dto.getSezione();
		foglio = dto.getFoglio();
		particella = dto.getParticella();
		subalterno = dto.getSubalterno();
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
	public String getSubalterno() {
		return subalterno;
	}
	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}
	public void setSezione(String sezione) {
		this.sezione = sezione;
	}
	public String getSezione() {
		return sezione;
	}

}
