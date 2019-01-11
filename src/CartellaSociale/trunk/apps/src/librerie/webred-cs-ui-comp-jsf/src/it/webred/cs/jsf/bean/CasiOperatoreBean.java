package it.webred.cs.jsf.bean;

import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;

public class CasiOperatoreBean {
	
	private CsOOperatore operatore;
	private String organizzazioni;
	private Integer numCasi;
	
	public CsOOperatore getOperatore() {
		return operatore;
	}
	public void setOperatore(CsOOperatore operatore) {
		this.operatore = operatore;
	}
	public CsOOperatoreAnagrafica getAmAnagrafica() {
		return operatore.getCsOOperatoreAnagrafica();
	}
	public Integer getNumCasi() {
		return numCasi;
	}
	public void setNumCasi(Integer numCasi) {
		this.numCasi = numCasi;
	}
	public String getOrganizzazioni() {
		return organizzazioni;
	}
	public void setOrganizzazioni(String organizzazioni) {
		this.organizzazioni = organizzazioni;
	}

	
}
