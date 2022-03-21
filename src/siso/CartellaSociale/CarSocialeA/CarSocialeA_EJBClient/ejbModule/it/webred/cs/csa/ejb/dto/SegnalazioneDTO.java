package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class SegnalazioneDTO extends CeTBaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String comuneResidenza;
	private String cognome;
	private String nome;
	private String dataNascita;
	private String cfOperatore;
	private String nomeOperatore;
	private String cognomeOperatore;
	private String ufficio;

	public String getComuneResidenza() {
		return comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
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

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public String getNomeOperatore() {
		return nomeOperatore;
	}

	public void setNomeOperatore(String nomeOperatore) {
		this.nomeOperatore = nomeOperatore;
	}

	public String getCognomeOperatore() {
		return cognomeOperatore;
	}

	public void setCognomeOperatore(String cognomeOperatore) {
		this.cognomeOperatore = cognomeOperatore;
	}

	public String getUfficio() {
		return ufficio;
	}

	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}

}
