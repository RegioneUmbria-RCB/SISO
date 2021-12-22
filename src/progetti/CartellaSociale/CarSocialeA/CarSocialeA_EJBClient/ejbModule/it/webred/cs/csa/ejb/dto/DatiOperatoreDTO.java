package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.Date;

public class DatiOperatoreDTO implements Serializable {
	
	private Long id;
	private String denominazione;
	private String username;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
