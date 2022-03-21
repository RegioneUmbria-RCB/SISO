package it.webred.cs.csa.ejb.dto.listaCasi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class UnitaOrganizzativaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String organizzazione;
	private String settore;
	private String secondoLivello;
	private Date dataInizioApp;
	private Date dataFineApp;
	
	public String getOrganizzazione() {
		return organizzazione;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public String getSettore() {
		return settore;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}
	public String getSecondoLivello() {
		return secondoLivello;
	}
	public void setSecondoLivello(String secondoLivello) {
		this.secondoLivello = secondoLivello;
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
	
	public String getValString(){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String val = "";
		val+= this.dataInizioApp!=null ?  "dal " + df.format(this.dataInizioApp) : "" ;
		val+= this.dataFineApp!=null && !this.dataFineApp.after(new Date()) ? " al "+ df.format(this.dataFineApp) : "";
		return val;		
	}
}
