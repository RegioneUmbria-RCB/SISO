package it.webred.ct.rulengine.service.bean.verifica.handler.dto;

import it.webred.ct.rulengine.controller.model.RProcessMonitor;

import java.io.Serializable;
import java.util.List;

public class VerificaHandlerParams implements Serializable {
	
	//processi lock su FD
	private List<RProcessMonitor> listaRProcessMonitor;
	
	//esito processi precedenti sulla FD
	private List<RProcessMonitor> prevRProcessMonitor;
	
	//stato FD attuale
	private RProcessMonitor rProcessMonitor;
	
	

	public VerificaHandlerParams(List<RProcessMonitor> listaRProcessMonitor,List<RProcessMonitor> prevRProcessMonitor,
			RProcessMonitor rProcessMonitor) {
		super();
		this.listaRProcessMonitor = listaRProcessMonitor;
		this.prevRProcessMonitor = prevRProcessMonitor;
		this.rProcessMonitor = rProcessMonitor;
	}

	public List<RProcessMonitor> getListaRProcessMonitor() {
		return listaRProcessMonitor;
	}

	public void setListaRProcessMonitor(List<RProcessMonitor> listaRProcessMonitor) {
		this.listaRProcessMonitor = listaRProcessMonitor;
	}

	public RProcessMonitor getrProcessMonitor() {
		return rProcessMonitor;
	}

	public void setrProcessMonitor(RProcessMonitor rProcessMonitor) {
		this.rProcessMonitor = rProcessMonitor;
	}

	public List<RProcessMonitor> getPrevRProcessMonitor() {
		return prevRProcessMonitor;
	}

	public void setPrevRProcessMonitor(List<RProcessMonitor> prevRProcessMonitor) {
		this.prevRProcessMonitor = prevRProcessMonitor;
	}


}
