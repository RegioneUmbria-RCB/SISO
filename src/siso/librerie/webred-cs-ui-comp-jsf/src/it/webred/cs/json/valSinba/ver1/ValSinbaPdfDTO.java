package it.webred.cs.json.valSinba.ver1;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;


public class ValSinbaPdfDTO extends ReportPdfDTO {
	
	private String denominazione="EMPTY_VALUE";

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	
	
}
