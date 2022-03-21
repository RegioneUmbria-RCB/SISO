package it.webred.cs.csa.ejb.dto;

import java.util.List;

public class OperatoriSearchCriteria extends PaginationDTO{

	private static final long serialVersionUID = 1L;

	private String cognome;
	private String nome;
	private String codiceFiscale;
	private String username;
	private Boolean abilitato;
	private List<String> enti;
	
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
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Boolean getAbilitato() {
		return abilitato;
	}
	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}
	public List<String> getEnti() {
		return enti;
	}
	public void setEnti(List<String> enti) {
		this.enti = enti;
	}
	
}
