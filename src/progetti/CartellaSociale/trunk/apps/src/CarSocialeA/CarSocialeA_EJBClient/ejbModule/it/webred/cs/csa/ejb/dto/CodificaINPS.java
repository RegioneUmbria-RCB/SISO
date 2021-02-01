package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

public class CodificaINPS implements Serializable {

	private String codice;
	private boolean psa;
	private String denomINPS;
	private String descINPS;
	public String getCodice() {
		return codice;
	}
	public boolean isPsa() {
		return psa;
	}
	public String getDenomINPS() {
		return denomINPS;
	}
	public String getDescINPS() {
		return descINPS;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public void setPsa(boolean psa) {
		this.psa = psa;
	}
	public void setDenomINPS(String denomINPS) {
		this.denomINPS = denomINPS;
	}
	public void setDescINPS(String descINPS) {
		this.descINPS = descINPS;
	}
	
}
