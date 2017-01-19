package it.webred.cs.jsf.bean;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.data.model.CsOOperatore;

public class CasiOperatoreBean {
	
	private CsOOperatore operatore;
	private AmAnagrafica amAnagrafica;
	private String organizzazioni;
	private Integer numCasi;
	
	public CsOOperatore getOperatore() {
		return operatore;
	}
	public void setOperatore(CsOOperatore operatore) {
		this.operatore = operatore;
	}
	public AmAnagrafica getAmAnagrafica() {
		return amAnagrafica;
	}
	public void setAmAnagrafica(AmAnagrafica amAnagrafica) {
		this.amAnagrafica = amAnagrafica;
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
