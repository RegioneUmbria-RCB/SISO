package it.webred.ss.ejb.dto;

import java.io.Serializable;

public class OperatoreDTO implements Serializable {

	private String username;
	private String cognome;
	private String nome;
	
	public String getUsername() {
		return username;
	}
	public String getCognome() {
		return cognome;
	}
	public String getNome() {
		return nome;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getDenominazione(){
		if(cognome!=null || nome!=null)
			return cognome+" "+nome;
		else
			return username;
	}
	
}
