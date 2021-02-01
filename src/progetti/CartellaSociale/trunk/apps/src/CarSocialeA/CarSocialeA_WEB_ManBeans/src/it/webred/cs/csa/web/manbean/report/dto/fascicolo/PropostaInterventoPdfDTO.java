package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class PropostaInterventoPdfDTO extends  ReportPdfDTO {

	private String descrizione = EMPTY_VALUE;
	private String custom = EMPTY_VALUE;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getCustom() {
		return custom;
	}
	public void setCustom(String custom) {
		this.custom = custom;
	}
	
	
	
	
}
