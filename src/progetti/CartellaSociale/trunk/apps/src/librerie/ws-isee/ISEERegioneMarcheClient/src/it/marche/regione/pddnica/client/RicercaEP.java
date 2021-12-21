package it.marche.regione.pddnica.client;

import java.io.Serializable;

public class RicercaEP implements Serializable {

	private String cf;
	private String numeroProtocolloDSU;
	
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getNumeroProtocolloDSU() {
		return numeroProtocolloDSU;
	}
	public void setNumeroProtocolloDSU(String numeroProtocolloDSU) {
		this.numeroProtocolloDSU = numeroProtocolloDSU;
	}
	
	
	
}
