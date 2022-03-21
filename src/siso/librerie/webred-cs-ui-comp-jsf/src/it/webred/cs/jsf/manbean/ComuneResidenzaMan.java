package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.ComuneMan;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class ComuneResidenzaMan extends ComuneMan implements Serializable{

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
