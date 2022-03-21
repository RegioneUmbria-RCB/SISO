package it.webred.cs.csa.ejb.dto.listaCasi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class OperatoreListaCasiDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private String denominazione;
	private Date dataInizioApp;
	private Date dataFineApp;
	private boolean responsabile;
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
	public Date getDataInizioApp() {
		return dataInizioApp;
	}
	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}
	public Date getDataFineApp() {
		return dataFineApp;
	}
	public void setDataFineApp(Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}
	public boolean isResponsabile() {
		return responsabile;
	}
	public void setResponsabile(boolean responsabile) {
		this.responsabile = responsabile;
	}
	public String getValString(){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String val = "";
		val+= this.dataInizioApp!=null ?  "dal " + df.format(this.dataInizioApp) : "" ;
		val+= this.dataFineApp!=null && !this.dataFineApp.after(new Date()) ? " al "+ df.format(this.dataFineApp) : "";
		return val;
		
	}
}
