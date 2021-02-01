package it.webred.ct.data.access.basic.diagnostiche.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import it.webred.ct.data.model.diagnostiche.*;

public class IndirizzoDTO implements Serializable{
	
	private String indirizzo;
	private String civico;
	private Date dataInizioVal;
	private Date dataFineVal;
	private String anno;
	private boolean anteDocfa; 
	
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCivico() {
		return civico;
	}
	public void setCivico(String civico) {
		this.civico = civico;
	}
	public Date getDataInizioVal() {
		return dataInizioVal;
	}
	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}
	public Date getDataFineVal() {
		return dataFineVal;
	}
	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public boolean isAnteDocfa() {
		return anteDocfa;
	}
	public void setAnteDocfa(boolean anteDocfa) {
		this.anteDocfa = anteDocfa;
	}
	
}
