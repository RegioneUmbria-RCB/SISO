package it.webred.cs.csa.ejb.dto.rest;

import java.io.Serializable;

public class InterventoDTO implements Serializable{

//	public InterventoDTO(Long id, String descrizione){
//		this.id = id;
//		this.descrizione = descrizione;
//	}
	private String descrizione;
	private Long id;
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
