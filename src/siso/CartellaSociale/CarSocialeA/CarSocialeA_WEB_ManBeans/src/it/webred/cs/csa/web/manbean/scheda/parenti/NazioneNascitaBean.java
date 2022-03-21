package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.jsf.manbean.superc.NazioneMan;
import it.webred.jsf.interfaces.gen.IManBeanForComponent;

import java.io.Serializable;

public class NazioneNascitaBean extends NazioneMan implements  IManBeanForComponent,  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getTipoNazione() {
		return "Nascita";
	}

	@Override
	public String getValidatorMessage() {
		//return "Stato estero di residenza è un campo obbligatorio";
		return null; //gestito nel listener del pulsante
	}

	@Override
	public String getMemoWidgetName() {
		return "nazioneNascitaWid";
	}


	

}
