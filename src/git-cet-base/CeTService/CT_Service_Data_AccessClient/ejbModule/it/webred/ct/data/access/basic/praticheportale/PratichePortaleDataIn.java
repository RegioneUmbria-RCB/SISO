package it.webred.ct.data.access.basic.praticheportale;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class PratichePortaleDataIn extends CeTBaseObject implements Serializable{

	private String codFiscale;

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	
}
