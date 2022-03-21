package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.NazioneMan;
import it.webred.jsf.interfaces.gen.IManBeanForComponent;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;


@ManagedBean
@NoneScoped
public class NazioneResidenzaMan extends NazioneMan implements  IManBeanForComponent,  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getTipoNazione() {
		return "Residenza";
	}

	@Override
	public String getValidatorMessage() {
		//return "Stato estero di residenza è un campo obbligatorio";
		return null; //gestito nel listener del pulsante
	}

	@Override
	public String getMemoWidgetName() {
		return "nazioneResidenzaWid";
	}


	

}
