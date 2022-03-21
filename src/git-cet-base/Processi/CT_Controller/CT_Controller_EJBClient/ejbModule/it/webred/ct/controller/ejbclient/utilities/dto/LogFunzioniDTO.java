package it.webred.ct.controller.ejbclient.utilities.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public class LogFunzioniDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String stato;
	private String nomeFunzione;
	private Timestamp dataInizio;
	private Timestamp dataFine;
	private String note;
	private String operatore;
	private String[] parametri;
	
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
	public Timestamp getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}
	public Timestamp getDataFine() {
		return dataFine;
	}
	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}
	public String[] getParametri() {
		return parametri;
	}
	public void setParametri(String[] parametri) {
		this.parametri = parametri;
	}
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}

	
}
