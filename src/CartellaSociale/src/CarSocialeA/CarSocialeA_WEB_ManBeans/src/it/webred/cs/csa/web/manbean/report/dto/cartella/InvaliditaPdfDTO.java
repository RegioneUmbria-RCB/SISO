package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class InvaliditaPdfDTO extends BasePdfDTO {

	private String invInCorso = EMPTY_VALUE;
	private String invInizio = EMPTY_VALUE;
	private String invFine = EMPTY_VALUE;
	private String invPerc = EMPTY_VALUE;
	private	String certH = EMPTY_VALUE;
	private	String certHInizio = EMPTY_VALUE;
	private	String certHFine = EMPTY_VALUE;
	private String accompagnamento = EMPTY_VALUE;
	private String legge104 = EMPTY_VALUE;
	
	public String getInvInCorso() {
		return invInCorso;
	}
	public void setInvInCorso(String invInCorso) {
		this.invInCorso = format(invInCorso);
	}
	public String getInvInizio() {
		return invInizio;
	}
	public void setInvInizio(String invInizio) {
		this.invInizio = format(invInizio);
	}
	public String getInvFine() {
		return invFine;
	}
	public void setInvFine(String invFine) {
		this.invFine = format(invFine);
	}
	public String getInvPerc() {
		return invPerc;
	}
	public void setInvPerc(String invPerc) {
		this.invPerc = format(invPerc);
	}
	public String getCertH() {
		return certH;
	}
	public void setCertH(String certH) {
		this.certH = format(certH);
	}
	public String getCertHInizio() {
		return certHInizio;
	}
	public void setCertHInizio(String certHInizio) {
		this.certHInizio = format(certHInizio);
	}
	public String getCertHFine() {
		return certHFine;
	}
	public void setCertHFine(String certHFine) {
		this.certHFine = format(certHFine);
	}
	public String getAccompagnamento() {
		return accompagnamento;
	}
	public void setAccompagnamento(String accompagnamento) {
		this.accompagnamento = format(accompagnamento);
	}
	public String getLegge104() {
		return legge104;
	}
	public void setLegge104(String legge104) {
		this.legge104 = format(legge104);
	}	
	
}
