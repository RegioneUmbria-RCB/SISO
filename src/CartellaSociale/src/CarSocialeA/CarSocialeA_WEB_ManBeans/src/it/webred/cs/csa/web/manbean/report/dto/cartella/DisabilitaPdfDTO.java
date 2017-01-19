package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class DisabilitaPdfDTO extends BasePdfDTO {

	private String gravitaDis = EMPTY_VALUE;
	private String tipologiaDis = EMPTY_VALUE;
	
	public String getGravitaDis() {
		return gravitaDis;
	}
	public void setGravitaDis(String gravitaDis) {
		this.gravitaDis = format(gravitaDis);
	}
	public String getTipologiaDis() {
		return tipologiaDis;
	}
	public void setTipologiaDis(String tipologiaDis) {
		this.tipologiaDis = format(tipologiaDis);
	}
	
}
