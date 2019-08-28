package it.webred.cs.jsf.manbean.comuneNazione;

import it.webred.cs.jsf.manbean.superc.NazioneMan;
import it.webred.jsf.interfaces.gen.IManBeanForComponent;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class NazioneGenericMan extends NazioneMan implements  IManBeanForComponent,  Serializable {

	private static final long serialVersionUID = 1L;
	private String tipoNazione;
	
	public NazioneGenericMan(String tipoNazione){
		super();
		this.tipoNazione = tipoNazione;
	}
	
	@Override
	public String getTipoNazione() {
		return tipoNazione;
	}

	@Override
	public String getValidatorMessage() {
		//return "Stato estero di residenza è un campo obbligatorio";
		return null; //gestito nel listener del pulsante
	}

	@Override
	public String getMemoWidgetName() {
		return tipoNazione+"Wid";
	}


	

}
