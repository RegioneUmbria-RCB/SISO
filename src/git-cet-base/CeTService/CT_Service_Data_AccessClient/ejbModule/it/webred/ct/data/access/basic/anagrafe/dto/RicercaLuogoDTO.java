package it.webred.ct.data.access.basic.anagrafe.dto;

import java.io.Serializable;
import java.util.Date;

public class RicercaLuogoDTO extends AnagrafeBaseDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idExtComune;
	private String idExtProvincia;
	private String belfiore;
	private String desComune;
	private String desProvincia;
	private String siglaProvincia;
	
	private Date dtRif; 
	
	public String getIdExtComune() {
		return idExtComune;
	}
	public void setIdExtComune(String idExtComune) {
		this.idExtComune = idExtComune;
	}
	public String getIdExtProvincia() {
		return idExtProvincia;
	}
	public void setIdExtProvincia(String idExtProvincia) {
		this.idExtProvincia = idExtProvincia;
	}
	public String getDesComune() {
		return desComune;
	}
	public void setDesComune(String desComune) {
		this.desComune = desComune;
	}
	public String getDesProvincia() {
		return desProvincia;
	}
	public void setDesProvincia(String desProvincia) {
		this.desProvincia = desProvincia;
	}
	public String getSiglaProvincia() {
		return siglaProvincia;
	}
	public void setSiglaProvincia(String siglaProvincia) {
		this.siglaProvincia = siglaProvincia;
	}
	public Date getDtRif() {
		return dtRif;
	}
	public void setDtRif(Date dtRif) {
		this.dtRif = dtRif;
	}
	public String getBelfiore() {
		return belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

}
