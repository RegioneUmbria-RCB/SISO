package it.webred.cs.csa.ejb.dto.udc;

import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;

import java.io.Serializable;

public class ListaDatiSchedaUdC implements Serializable{

	private DatiSchedaAccessoDTO udc;
	private CsIterStepByCasoDTO iterCaso;
	
	public DatiSchedaAccessoDTO getUdc() {
		return udc;
	}
	public void setUdc(DatiSchedaAccessoDTO udc) {
		this.udc = udc;
	}
	public CsIterStepByCasoDTO getIterCaso() {
		return iterCaso;
	}
	public void setIterCaso(CsIterStepByCasoDTO iterCaso) {
		this.iterCaso = iterCaso;
	}
}
