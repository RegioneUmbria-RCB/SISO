package it.umbriadigitale.argo.ejb.client.cs.dto;

import java.io.Serializable;

/**
 * 
 * @author andrea.niccolini
 *
 */
public class ArCsSoggettoCsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4437030520480839097L;

	
	private String denominazione;
	private String indirizzo;
	private String tel;
	private String fax;
	private String email;


	public String getDenominazione() {
		return denominazione;
	}
	
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
