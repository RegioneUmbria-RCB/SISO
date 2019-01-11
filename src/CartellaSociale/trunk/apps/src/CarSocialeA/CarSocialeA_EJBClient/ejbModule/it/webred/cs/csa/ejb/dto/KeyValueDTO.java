package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

public class KeyValueDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Object codice;
	private String descrizione;
	
	public KeyValueDTO(Object key, String value) {
		super();
		this.codice = key;
		this.descrizione = value;
	}

	public KeyValueDTO() {
		super();
	}

	public Object getCodice() {
		return codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setCodice(Object codice) {
		this.codice = codice;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
