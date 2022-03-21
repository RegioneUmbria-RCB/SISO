package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;


public class OperatorePdfDTO extends StoricoPdfDTO {

	private String responsabile = EMPTY_VALUE;
	private String tipoOp = EMPTY_VALUE;
	private String denominazioneOp = EMPTY_VALUE;
	
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
	
}
