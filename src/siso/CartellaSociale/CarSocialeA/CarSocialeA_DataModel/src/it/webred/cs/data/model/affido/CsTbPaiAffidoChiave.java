package it.webred.cs.data.model.affido;

import java.io.Serializable;

public class CsTbPaiAffidoChiave implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String dominio;
	private String codice;
	
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
	
}
