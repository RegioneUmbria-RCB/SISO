package it.webred.cs.json.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KeyValueDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String codice;
	private String descrizione;
	
	public KeyValueDTO(String key, String value) {
		super();
		this.codice = key;
		this.descrizione = value;
	}

	public KeyValueDTO() {
		super();
	}

	public String getCodice() {
		return codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
