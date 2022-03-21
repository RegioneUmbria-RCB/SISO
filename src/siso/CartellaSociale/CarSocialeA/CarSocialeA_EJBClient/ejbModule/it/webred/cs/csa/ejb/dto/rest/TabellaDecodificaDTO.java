package it.webred.cs.csa.ejb.dto.rest;

import java.io.Serializable;

public class TabellaDecodificaDTO extends TabellaDecodificaBaseDTO implements Serializable {
	
	private String id;
	
	 
	private String note;
	
	 
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	 

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	 
	

}
