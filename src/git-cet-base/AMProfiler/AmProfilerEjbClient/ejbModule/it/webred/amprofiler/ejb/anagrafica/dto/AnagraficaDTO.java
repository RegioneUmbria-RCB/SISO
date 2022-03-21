package it.webred.amprofiler.ejb.anagrafica.dto;

import it.webred.amprofiler.model.AmAnagrafica;

import java.io.Serializable;

public class AnagraficaDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private AmAnagrafica anagrafica;
	private String statoPwd;
	
	public AmAnagrafica getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(AmAnagrafica anagrafica) {
		this.anagrafica = anagrafica;
	}
	public String getStatoPwd() {
		return statoPwd;
	}
	public void setStatoPwd(String statoPwd) {
		this.statoPwd = statoPwd;
	}
	
}
