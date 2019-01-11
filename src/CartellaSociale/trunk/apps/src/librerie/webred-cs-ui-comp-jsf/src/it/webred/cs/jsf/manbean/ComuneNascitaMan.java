package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.ComuneMan;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;


@ManagedBean
@NoneScoped
public class ComuneNascitaMan extends ComuneMan implements   Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; 

	@Override
	public String getTipoComune() {
		return "Nascita";
	}
	
	@Override
	public String getValidatorMessage() {
		return "Comune di nascita obbligatorio";
	}

	@Override
	public String getMemoWidgetName() {
		return "comuneNascitaWid";

	}


}
	


