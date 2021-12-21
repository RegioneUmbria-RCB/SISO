package it.webred.cs.jsf.manbean.comuneNazione;

import it.webred.cs.jsf.manbean.superc.ComuneMan;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean
@NoneScoped
public class ComuneGenericMan extends ComuneMan implements Serializable{

	private static final long serialVersionUID = 1L;
	private String tipoComune;
	
	public ComuneGenericMan(){
		super();
	}
	
	public ComuneGenericMan(String tipoComune){
		this();
		this.tipoComune = tipoComune;
	}
	
	@Override
	public String getTipoComune() {
		return tipoComune;
	}
	
	public void setTipoComune(String tipoComune) {
		this.tipoComune = tipoComune;
	}

	@Override
	public String getValidatorMessage() {
		//return "Comune di residenza è un campo obbligatorio";
		return null; //gestito nel listener del pulsante
	}

	@Override
	public String getMemoWidgetName() {
		return tipoComune+"Wid";

	}
	
}
