package it.webred.ct.data.access.basic.anagrafe.dto;

import java.io.Serializable;

public class ComuneProvinciaDTO extends AnagrafeBaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String belfiore;
	private String descComune;
	private String siglaProv;
	private String descProv;
	
	public String getBelfiore() {
		return belfiore;
	}
	public String getDescComune() {
		return descComune;
	}
	public String getSiglaProv() {
		return siglaProv;
	}
	public String getDescProv() {
		return descProv;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}
	public void setDescComune(String descComune) {
		this.descComune = descComune;
	}
	public void setSiglaProv(String siglaProv) {
		this.siglaProv = siglaProv;
	}
	public void setDescProv(String descProv) {
		this.descProv = descProv;
	}
	
	
	
}
