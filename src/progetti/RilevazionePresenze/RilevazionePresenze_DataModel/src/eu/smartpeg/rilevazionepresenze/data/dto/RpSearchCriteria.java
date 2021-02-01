package eu.smartpeg.rilevazionepresenze.data.dto;

import java.io.Serializable;
import java.util.Date;

public class RpSearchCriteria implements Serializable {
private static final long serialVersionUID = 1L;
	
	
	//RICERCA SOGGETTO
	private String cf;
	private String cognome;
	private String nome;
	private Date dataNascitaDa;
	private Date dataNascitaA;
	private String sesso;
	
	
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
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

	public Date getDataNascitaDa() {
		return dataNascitaDa;
	}
	public void setDataNascitaDa(Date dataNascitaDa) {
		this.dataNascitaDa = dataNascitaDa;
	}
	public Date getDataNascitaA() {
		return dataNascitaA;
	}
	public void setDataNascitaA(Date dataNascitaA) {
		this.dataNascitaA = dataNascitaA;
	}
	public String getSesso() {
		return sesso;
	}
	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
}
