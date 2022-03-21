package it.umbriadigitale.soclav.service.dto.sap.lavoratore.datiAnagrafici;

import java.io.Serializable;

public class Recapito implements Serializable {

	 private String telefono;
	 private String cellulare;
	 private String email;
	 
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCellulare() {
		return cellulare;
	}
	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}
	
}
