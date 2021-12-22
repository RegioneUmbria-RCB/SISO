package it.webred.cs.csa.ejb.dto.rest;

import java.io.Serializable;

public class TabellaDecodificaExtDTO extends TabellaDecodificaBaseDTO implements Serializable{

	private String cod;

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}
	
	
}
