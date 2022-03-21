package it.webred.cs.csa.web.manbean.report.dto.cartella;

import java.util.List;

import it.webred.cs.csa.utils.bean.report.dto.StoricoPdfDTO;


public class TribunalePdfDTO extends StoricoPdfDTO {

	private String numProtocollo = EMPTY_VALUE;
	private	String macroSegnal = EMPTY_VALUE;
	private	String microSegnal = EMPTY_VALUE;
	private	String motivoSegnal = EMPTY_VALUE;
	private String primoContattoAG = EMPTY_VALUE;
	private Boolean infoNonReperibili;
	private String strutture = EMPTY_VALUE;
	
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
	public String getNumProtocollo() {
		return numProtocollo;
	}
	public void setNumProtocollo(String numProtocollo) {
		this.numProtocollo = numProtocollo;
	}
	public String getPrimoContattoAG() {
		return primoContattoAG;
	}
	public void setPrimoContattoAG(String primoContattoAG) {
		this.primoContattoAG = primoContattoAG;
	}
	public Boolean getInfoNonReperibili() {
		return infoNonReperibili;
	}
	public void setInfoNonReperibili(Boolean infoNonReperibili) {
		this.infoNonReperibili = infoNonReperibili;
	}
	public String getStrutture() {
		return strutture;
	}
	public void setStrutture(String strutture) {
		this.strutture = strutture;
	}
}
