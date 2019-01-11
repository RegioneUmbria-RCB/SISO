package it.webred.cs.csa.ejb.dto.retvalue;



import it.webred.cs.data.model.CsAlertBASIC;

import java.io.Serializable;


public class CsAlertDTO implements Serializable {

	
	
	private CsAlertBASIC csAlertBASIC;
	private String settore1Nome;
	private String settore2Nome;
	private String organizzazione1Nome;
	private String organizzazione2Nome;
	private String operatore2UserName;
	private String operatore1UserName;
	
	
	public CsAlertDTO(CsAlertBASIC csAlertBASIC) {
		this.csAlertBASIC = csAlertBASIC;
	}

	





	public CsAlertBASIC getCsAlertBASIC() {
		return csAlertBASIC;
	}



	public void setCsAlertBASIC(CsAlertBASIC csAlertBASIC) {
		this.csAlertBASIC = csAlertBASIC;
	}



	public String getSettore1Nome() {
		return settore1Nome;
	}



	public void setSettore1Nome(String settore1Nome) {
		this.settore1Nome = settore1Nome;
	}



	public String getSettore2Nome() {
		return settore2Nome;
	}



	public void setSettore2Nome(String settore2Nome) {
		this.settore2Nome = settore2Nome;
	}



	public String getOrganizzazione1Nome() {
		return organizzazione1Nome;
	}



	public void setOrganizzazione1Nome(String organizzazione1Nome) {
		this.organizzazione1Nome = organizzazione1Nome;
	}



	public String getOrganizzazione2Nome() {
		return organizzazione2Nome;
	}



	public void setOrganizzazione2Nome(String organizzazione2Nome) {
		this.organizzazione2Nome = organizzazione2Nome;
	}



	public String getOperatore2UserName() {
		return operatore2UserName;
	}



	public void setOperatore2UserName(String operatore2UserName) {
		this.operatore2UserName = operatore2UserName;
	}



	public String getOperatore1UserName() {
		return operatore1UserName;
	}



	public void setOperatore1UserName(String operatore1UserName) {
		this.operatore1UserName = operatore1UserName;
	}

	
}
