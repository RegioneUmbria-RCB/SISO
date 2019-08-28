package it.webred.cs.jsf.manbean;

import it.webred.cs.jsf.manbean.superc.NazioneMan;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;


@ManagedBean 
@NoneScoped
public class NazioneNascitaMan extends NazioneMan implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getTipoNazione() {
		// TODO Auto-generated method stub
		return "Nascita";
	}

	@Override
	public String getValidatorMessage() {
		// TODO Auto-generated method stub
		return "stato estero di nascita obbligatorio";
	}

	@Override
	public String getMemoWidgetName() {
		// TODO Auto-generated method stub
		return "nazioneNascitaWid";
	}

}
