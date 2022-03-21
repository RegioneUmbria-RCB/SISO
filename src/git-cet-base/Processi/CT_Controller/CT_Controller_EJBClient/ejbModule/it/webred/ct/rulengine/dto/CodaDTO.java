package it.webred.ct.rulengine.dto;

import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.RSchedulerTime;

import java.io.Serializable;
import java.util.Date;

public class CodaDTO implements Serializable {
	
	private String ente;
	private Date inizioEsec;
	
	private RCoda rCoda;
	
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public RCoda getrCoda() {
		return rCoda;
	}
	public void setrCoda(RCoda rCoda) {
		this.rCoda = rCoda;
	}
	public Date getInizioEsec() {
		return inizioEsec;
	}
	public void setInizioEsec(Date inizioEsec) {
		this.inizioEsec = inizioEsec;
	}

}
