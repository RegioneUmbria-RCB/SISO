package it.webred.ct.data.access.basic.tarsu.dto;

import it.webred.ct.data.access.basic.catasto.dto.CatastoBaseDTO;

import java.io.Serializable;

public class RicercaOggettoTarsuParCatDTO extends CatastoBaseDTO implements Serializable {
	private String sezione; //opzionale
	private String foglio;
	private String particella;
	private String subalterno; 
	
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

	public String getSezione() {
		return sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

}
