package it.webred.ct.aggregator.ejb.immobiliAccatastati.dto;

import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ImmobiliAccatastatiInputDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;
	
	private String codiceFiscale;
	
	private String cognome;
	private String nome;
	private String dataNascita;
	
	private String dataInizio;
	private String dataFine;
	
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	public String getDataInizio() {
		return dataInizio;
	}
	public void setDataInizio(String dataInizio) {
		this.dataInizio = dataInizio;
	}
	public String getDataFine() {
		return dataFine;
	}
	public void setDataFine(String dataFine) {
		this.dataFine = dataFine;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}
	
}
