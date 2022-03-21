package it.webred.ct.data.access.basic.concedilizie.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class SoggettoConcessioneDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String datiAnag;
	private String titolo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatiAnag() {
		return datiAnag;
	}
	public void setDatiAnag(String datiAnag) {
		this.datiAnag = datiAnag;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	

}
