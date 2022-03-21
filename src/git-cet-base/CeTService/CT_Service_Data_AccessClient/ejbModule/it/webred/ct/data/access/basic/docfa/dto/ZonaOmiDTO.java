package it.webred.ct.data.access.basic.docfa.dto;

import java.io.Serializable;

public class ZonaOmiDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name = "";
	private String foglio = "";
	private String particella = "";

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
