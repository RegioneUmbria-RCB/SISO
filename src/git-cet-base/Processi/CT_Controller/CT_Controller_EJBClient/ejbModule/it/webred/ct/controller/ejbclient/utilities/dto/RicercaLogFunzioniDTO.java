package it.webred.ct.controller.ejbclient.utilities.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;

public class RicercaLogFunzioniDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String stato;
	private String nome;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
