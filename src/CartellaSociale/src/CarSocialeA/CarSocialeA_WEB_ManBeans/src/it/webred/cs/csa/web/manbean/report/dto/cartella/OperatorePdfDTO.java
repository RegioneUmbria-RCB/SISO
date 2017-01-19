package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class OperatorePdfDTO extends BasePdfDTO {

	private String responsabile = EMPTY_VALUE;
	private String tipoOp = EMPTY_VALUE;
	private String denominazioneOp = EMPTY_VALUE;
	private String dallaDataOp = EMPTY_VALUE;
	
	public String getResponsabile() {
		return responsabile;
	}
	public void setResponsabile(String responsabile) {
		this.responsabile = format(responsabile);
	}
	public String getTipoOp() {
		return tipoOp;
	}
	public void setTipoOp(String tipoOp) {
		this.tipoOp = format(tipoOp);
	}
	public String getDenominazioneOp() {
		return denominazioneOp;
	}
	public void setDenominazioneOp(String denominazioneOp) {
		this.denominazioneOp = format(denominazioneOp);
	}
	public String getDallaDataOp() {
		return dallaDataOp;
	}
	public void setDallaDataOp(String dallaDataOp) {
		this.dallaDataOp = format(dallaDataOp);
	}
	
}
