package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class PastiDomiciliariPdfDTO extends BasePdfDTO {

	private String quotaUtente = EMPTY_VALUE;
	private String contributoUtente = EMPTY_VALUE;
	private String dietaSpeciale = EMPTY_VALUE;
	
	public String getQuotaUtente() {
		return quotaUtente;
	}
	public void setQuotaUtente(String quotaUtente) {
		this.quotaUtente = format(quotaUtente);
	}
	public String getContributoUtente() {
		return contributoUtente;
	}
	public void setContributoUtente(String contributoUtente) {
		this.contributoUtente = format(contributoUtente);
	}
	public String getDietaSpeciale() {
		return dietaSpeciale;
	}
	public void setDietaSpeciale(String dietaSpeciale) {
		this.dietaSpeciale = format(dietaSpeciale);
	}
	
}
