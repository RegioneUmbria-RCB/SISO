package it.webred.ct.data.access.basic.acqua;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class AcquaDataIn extends CeTBaseObject implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private String codFiscale;

	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}
	
}
