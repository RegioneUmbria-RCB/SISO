package it.webred.cs.csa.web.manbean.report.dto.foglioAmm.custom;

import it.webred.cs.csa.utils.bean.report.dto.BasePdfDTO;

public class ResAdultiPdfDTO extends BasePdfDTO {

	private String conRetta = EMPTY_VALUE;
	private String valoreRetta = EMPTY_VALUE;
	private String contributo = EMPTY_VALUE;
	private String compartecipazione = EMPTY_VALUE;
	private String comunita = EMPTY_VALUE;
	
	public String getConRetta() {
		return conRetta;
	}
	public void setConRetta(String conRetta) {
		this.conRetta = format(conRetta);
	}
	public String getValoreRetta() {
		return valoreRetta;
	}
	public void setValoreRetta(String valoreRetta) {
		this.valoreRetta = format(valoreRetta);
	}
	public String getContributo() {
		return contributo;
	}
	public void setContributo(String contributo) {
		this.contributo = format(contributo);
	}
	public String getCompartecipazione() {
		return compartecipazione;
	}
	public void setCompartecipazione(String compartecipazione) {
		this.compartecipazione = format(compartecipazione);
	}
	public String getComunita() {
		return comunita;
	}
	public void setComunita(String comunita) {
		this.comunita = format(comunita);
	}
	
}
