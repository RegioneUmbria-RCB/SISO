package it.umbriadigitale.soclav.service.dto.sap.lavoratore.allegato;

import java.io.Serializable;

public class ConoscenzaInformatica implements Serializable {

	/*
	 * <conoscenzainformatica>    
				<codconoscenzainformatica>016</codconoscenzainformatica>    
				<codgrado>2</codgrado>    
				<specificheinformatica>INTERNET EXPLORER</specificheinformatica>
	    </conoscenzainformatica>
	 * */
	
	private String codconoscenzainformatica;
	private String codgrado;
	private String specificheinformatica;
	
	private String desconoscenzainformatica;
	private String desgrado;
	
	public String getCodconoscenzainformatica() {
		return codconoscenzainformatica;
	}
	public void setCodconoscenzainformatica(String codconoscenzainformatica) {
		this.codconoscenzainformatica = codconoscenzainformatica;
	}
	public String getCodgrado() {
		return codgrado;
	}
	public void setCodgrado(String codgrado) {
		this.codgrado = codgrado;
	}
	public String getSpecificheinformatica() {
		return specificheinformatica;
	}
	public void setSpecificheinformatica(String specificheinformatica) {
		this.specificheinformatica = specificheinformatica;
	}
	public String getDesconoscenzainformatica() {
		return desconoscenzainformatica;
	}
	public void setDesconoscenzainformatica(String desconoscenzainformatica) {
		this.desconoscenzainformatica = desconoscenzainformatica;
	}
	public String getDesgrado() {
		return desgrado;
	}
	public void setDesgrado(String desgrado) {
		this.desgrado = desgrado;
	}
	
}
