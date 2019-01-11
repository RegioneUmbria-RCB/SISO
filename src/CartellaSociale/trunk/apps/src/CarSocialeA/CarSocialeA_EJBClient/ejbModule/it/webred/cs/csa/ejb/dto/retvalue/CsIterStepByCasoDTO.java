package it.webred.cs.csa.ejb.dto.retvalue;

import it.webred.cs.data.model.CsItStepLAZY;

import java.io.Serializable;


public class CsIterStepByCasoDTO implements Serializable {

	private CsItStepLAZY csItStep;
	
	private java.util.Date dataAmministrativa;
	private String responsabileUsername;
	
	
	
	public CsIterStepByCasoDTO(CsItStepLAZY csItStep) {
		this.csItStep = csItStep;
	}


	public java.util.Date getDataAmministrativa() {
		return dataAmministrativa;
	}

	public void setDataAmministrativa(java.util.Date dataAmministrativa) {
		this.dataAmministrativa = dataAmministrativa;
	}


	public CsItStepLAZY getCsItStep() {
		return csItStep;
	}

	public void setCsItStep(CsItStepLAZY csItStep) {
		this.csItStep = csItStep;
	}


	public String getResponsabileUsername() {
		return responsabileUsername;
	}


	public void setResponsabileUsername(String responsabile) {
		this.responsabileUsername = responsabile;
	}
	
	
}
