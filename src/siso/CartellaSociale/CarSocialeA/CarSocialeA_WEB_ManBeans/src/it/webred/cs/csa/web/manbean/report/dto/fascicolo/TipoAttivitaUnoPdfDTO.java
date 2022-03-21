package it.webred.cs.csa.web.manbean.report.dto.fascicolo;

import it.webred.cs.csa.utils.bean.report.dto.ReportPdfDTO;

public class TipoAttivitaUnoPdfDTO extends ReportPdfDTO {
	
	private String socioAmbientale = EMPTY_VALUE;
	private String parentale = EMPTY_VALUE;
	private String sanitaria = EMPTY_VALUE;
	private String orgServizio = EMPTY_VALUE;
	private String proposta = EMPTY_VALUE;
	
	public String getSocioAmbientale() {
		return socioAmbientale;
	}
	public void setSocioAmbientale(String socioAmbientale) {
		this.socioAmbientale = socioAmbientale;
	}
	public String getParentale() {
		return parentale;
	}
	public void setParentale(String parentale) {
		this.parentale = parentale;
	}
	public String getSanitaria() {
		return sanitaria;
	}
	public void setSanitaria(String sanitaria) {
		this.sanitaria = sanitaria;
	}
	public String getOrgServizio() {
		return orgServizio;
	}
	public void setOrgServizio(String orgServizio) {
		this.orgServizio = orgServizio;
	}
	public String getProposta() {
		return proposta;
	}
	public void setProposta(String proposta) {
		this.proposta = proposta;
	}
	
	
}