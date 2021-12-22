package it.webred.cs.csa.ejb.dto.retvalue;

import it.webred.cs.data.model.CsItStepLAZY;

import java.io.Serializable;
import java.util.Date;


public class CsIterStepByCasoDTO implements Serializable {

	private CsItStepLAZY csItStep;
	
	private Date dataAmministrativa;
	private String resposabileDenominazione;
	
	
	public CsIterStepByCasoDTO(CsItStepLAZY csItStep) {
		this.csItStep = csItStep;
	}


	public java.util.Date getDataAmministrativa() {
		return dataAmministrativa;
	}

	public void setDataAmministrativa(java.util.Date dataAmministrativa) {
		this.dataAmministrativa = dataAmministrativa;
	}
	


	public String getResposabileDenominazione() {
		return resposabileDenominazione;
	}


	public void setResposabileDenominazione(String resposabileDenominazione) {
		this.resposabileDenominazione = resposabileDenominazione;
	}


	public CsItStepLAZY getCsItStep() {
		return csItStep;
	}

	public void setCsItStep(CsItStepLAZY csItStep) {
		this.csItStep = csItStep;
	}	
}
