package it.webred.ct.controller.ejbclient.utilities.dto;

import java.io.Serializable;
import java.util.HashMap;

public class GestioneUtilitiesDTO implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String stato;
	private String nomeFunzione;
	private HashMap<String, Object> parametri;
	private String operatore;
	private String note;
	
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getNomeFunzione() {
		return nomeFunzione;
	}
	public void setNomeFunzione(String nomeFunzione) {
		this.nomeFunzione = nomeFunzione;
	}
	public HashMap<String, Object> getParametri() {
		return parametri;
	}
	public void setParametri(HashMap<String, Object> parametri) {
		this.parametri = parametri;
	}
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	
}
