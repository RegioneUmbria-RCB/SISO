package it.webred.cs.csa.web.manbean.report.dto.cartella;

import it.webred.cs.csa.web.manbean.report.dto.BasePdfDTO;

public class TribunalePdfDTO extends BasePdfDTO {

	private String tmCivile = EMPTY_VALUE;
	private String tmAmm = EMPTY_VALUE;
	private String penaleMin = EMPTY_VALUE;
	private	String to = EMPTY_VALUE;
	private	String nis = EMPTY_VALUE;
	private String procuraMin = EMPTY_VALUE;
	private String numProtocollo = EMPTY_VALUE;
	private	String macroSegnal = EMPTY_VALUE;
	private	String microSegnal = EMPTY_VALUE;
	private	String motivoSegnal = EMPTY_VALUE;
	
	public String getTmCivile() {
		return tmCivile;
	}
	public void setTmCivile(String tmCivile) {
		this.tmCivile = format(tmCivile);
	}
	public String getTmAmm() {
		return tmAmm;
	}
	public void setTmAmm(String tmAmm) {
		this.tmAmm = format(tmAmm);
	}
	public String getPenaleMin() {
		return penaleMin;
	}
	public void setPenaleMin(String penaleMin) {
		this.penaleMin = format(penaleMin);
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = format(to);
	}
	public String getNis() {
		return nis;
	}
	public void setNis(String nis) {
		this.nis = format(nis);
	}
	public String getMacroSegnal() {
		return macroSegnal;
	}
	public void setMacroSegnal(String macroSegnal) {
		this.macroSegnal = format(macroSegnal);
	}
	public String getMicroSegnal() {
		return microSegnal;
	}
	public void setMicroSegnal(String microSegnal) {
		this.microSegnal = format(microSegnal);
	}
	public String getMotivoSegnal() {
		return motivoSegnal;
	}
	public void setMotivoSegnal(String motivoSegnal) {
		this.motivoSegnal = format(motivoSegnal);
	}
	public String getProcuraMin() {
		return procuraMin;
	}
	public void setProcuraMin(String procuraMin) {
		this.procuraMin = procuraMin;
	}
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}

}
