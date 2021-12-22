package it.siso.isee.obj;

import java.io.Serializable;

public class Valori implements Serializable {
	
	private String ISE;
	private String ISEE;
	private String ISR;
	private String ISP;
	private String scalaEquivalenza;
	
	public String getISE() {
		return ISE;
	}
	public void setISE(String iSE) {
		ISE = iSE;
	}
	public String getISEE() {
		return ISEE;
	}
	public void setISEE(String iSEE) {
		ISEE = iSEE;
	}
	public String getISR() {
		return ISR;
	}
	public void setISR(String iSR) {
		ISR = iSR;
	}
	public String getISP() {
		return ISP;
	}
	public void setISP(String iSP) {
		ISP = iSP;
	}
	public String getScalaEquivalenza() {
		return scalaEquivalenza;
	}
	public void setScalaEquivalenza(String scalaEquivalenza) {
		this.scalaEquivalenza = scalaEquivalenza;
	}
	
}
