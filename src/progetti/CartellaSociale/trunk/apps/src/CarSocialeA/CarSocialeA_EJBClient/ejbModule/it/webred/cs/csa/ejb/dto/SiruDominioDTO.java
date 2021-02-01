package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

public class SiruDominioDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String codice;
	
	private String descrizione;
	
	private String versione;  //SISO-850

	public SiruDominioDTO(String codice) {
		this.codice = codice;
	}
	public SiruDominioDTO(String codice, String descrizione) {
		this.codice = codice;
		this.descrizione = descrizione;
	}
	public SiruDominioDTO(String codice, String descrizione,String versione) {
		this.codice = codice;
		this.descrizione = descrizione;
		this.versione = versione;
	}
	
	
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	//SISO-850
	public String getVersione() {
		return versione;
	}
	public void setVersione(String versione) {
		this.versione = versione;
	}
	//SISO-850
}
