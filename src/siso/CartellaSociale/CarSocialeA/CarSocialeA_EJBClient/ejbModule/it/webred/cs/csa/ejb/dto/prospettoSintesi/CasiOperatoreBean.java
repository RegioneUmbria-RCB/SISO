package it.webred.cs.csa.ejb.dto.prospettoSintesi;

import java.io.Serializable;

public class CasiOperatoreBean implements Serializable{
	
	private String operatore;
	private String organizzazioni;
	private Integer numCasiEnte;
	private Integer numCasiAltro;
		
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public Integer getNumCasiEnte() {
		return numCasiEnte;
	}
	public void setNumCasiEnte(Integer numCasiEnte) {
		this.numCasiEnte = numCasiEnte;
	}
	public Integer getNumCasiAltro() {
		return numCasiAltro;
	}
	public void setNumCasiAltro(Integer numCasiAltro) {
		this.numCasiAltro = numCasiAltro;
	}
	public String getOrganizzazioni() {
		return organizzazioni;
	}
	public void setOrganizzazioni(String organizzazioni) {
		this.organizzazioni = organizzazioni;
	}
}
