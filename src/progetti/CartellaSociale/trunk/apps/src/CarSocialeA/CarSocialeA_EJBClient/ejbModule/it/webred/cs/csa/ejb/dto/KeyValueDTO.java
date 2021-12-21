package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

public class KeyValueDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Object codice;
	private String descrizione;
	private boolean abilitato;
	
	public KeyValueDTO(Object key, String value) {
		super();
		this.codice = key;
		this.descrizione = value;
		this.abilitato = true;
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

	public boolean isAbilitato() {
		return abilitato;
	}

	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}

}
