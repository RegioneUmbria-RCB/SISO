package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class TipoAttivitaDuePdfDTO extends ReportPdfDTO {
	
	private String testo = EMPTY_VALUE;
	private String descrizioneCon = EMPTY_VALUE;
	
	public String getTesto() {
		return testo;
	}
	public void setTesto(String testo) {
		this.testo = testo;
	}
	public String getDescrizioneCon() {
		return descrizioneCon;
	}
	public void setDescrizioneCon(String descrizioneCon) {
		this.descrizioneCon = descrizioneCon;
	}

	
}