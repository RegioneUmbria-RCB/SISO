package it.webred.cs.jsf.bean;

import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;

public class CasiOperatoreBean {
	
	private CsOOperatore operatore;
	private String organizzazioni;
	private Integer numCasiEnte;
	private Integer numCasiAltro;
	
	public CsOOperatore getOperatore() {
		return operatore;
	}
	public void setOperatore(CsOOperatore operatore) {
		this.operatore = operatore;
	}
	public CsOOperatoreAnagrafica getAmAnagrafica() {
		return operatore.getCsOOperatoreAnagrafica();
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
