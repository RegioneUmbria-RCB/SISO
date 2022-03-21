package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.jsf.manbean.superc.ComuneMan;

import java.io.Serializable;

public class ComuneResidenzaBean extends ComuneMan implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String getTipoComune() {
		return "Residenza";
	}
	
	@Override
	public String getValidatorMessage() {
		//return "Comune di residenza è un campo obbligatorio";
		return null; //gestito nel listener del pulsante
	}

	@Override
	public String getMemoWidgetName() {
		return "comuneResidenzaWid";

	}
	
}
