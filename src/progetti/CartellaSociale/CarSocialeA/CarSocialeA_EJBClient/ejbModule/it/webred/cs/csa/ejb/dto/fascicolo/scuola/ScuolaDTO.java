package it.webred.cs.csa.ejb.dto.fascicolo.scuola;

import java.io.Serializable;

public class ScuolaDTO implements Serializable {
	
	private String descrizione;
	private String indirizzo;
	private String telefono;
	private String cap;
	private String comune;
	private String fax;
	private String sitoweb;
	private String email;
	
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getSitoweb() {
		return sitoweb;
	}
	public void setSitoweb(String sitoweb) {
		this.sitoweb = sitoweb;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}
	public String getComune() {
		return comune;
	}
	public void setComune(String comune) {
		this.comune = comune;
	}

}
