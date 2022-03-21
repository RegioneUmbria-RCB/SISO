package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class InfoRecapitiDTO extends CeTBaseObject {

	private static final long serialVersionUID = 1L;
	
	private boolean cartellaSociale=false;
	
	private String telefono;
	private String cellulare;
	private String email;
	private String resIndirizzo;
	private String resCivico;
	private KeyValueDTO resComune;
	private KeyValueDTO resStato;
	private String resProv;
	
	public String getTelefono() {
		return telefono;
	}
	public String getCellulare() {
		return cellulare;
	}
	public String getEmail() {
		return email;
	}
	public String getResIndirizzo() {
		return resIndirizzo;
	}
	public String getResCivico() {
		return resCivico;
	}
	public KeyValueDTO getResComune() {
		return resComune;
	}
	public KeyValueDTO getResStato() {
		return resStato;
	}
	public String getResProv() {
		return resProv;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setResIndirizzo(String resIndirizzo) {
		this.resIndirizzo = resIndirizzo;
	}
	public void setResCivico(String resCivico) {
		this.resCivico = resCivico;
	}
	public void setResComune(KeyValueDTO resComune) {
		this.resComune = resComune;
	}
	public void setResStato(KeyValueDTO resStato) {
		this.resStato = resStato;
	}
	public void setResProv(String resProv) {
		this.resProv = resProv;
	}
	public boolean isCartellaSociale() {
		return cartellaSociale;
	}
	public void setCartellaSociale(boolean cartellaSociale) {
		this.cartellaSociale = cartellaSociale;
	}
}
