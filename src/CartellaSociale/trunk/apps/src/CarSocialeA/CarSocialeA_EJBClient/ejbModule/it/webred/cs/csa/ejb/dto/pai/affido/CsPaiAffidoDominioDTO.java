package it.webred.cs.csa.ejb.dto.pai.affido;

import java.io.Serializable;

public class CsPaiAffidoDominioDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String dominio;
	
	private String codice;
	
	private String descrizione;
	

	public CsPaiAffidoDominioDTO(String dominio, String codice, String descrizione) {
		this.dominio = dominio;
		this.codice = codice;
		this.descrizione = descrizione;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
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

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
