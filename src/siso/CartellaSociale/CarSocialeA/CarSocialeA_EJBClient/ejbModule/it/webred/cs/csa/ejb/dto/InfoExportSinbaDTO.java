package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDSinba;

import java.io.Serializable;
import java.util.Date;


public class InfoExportSinbaDTO  implements Serializable{

	private static final long serialVersionUID = 1L;

	private CsDSinba csDSinba;
	
	private Boolean toExport;
	
	private Boolean esportabile;

	private Boolean codiciDaAggiornare;
		
	public CsDSinba getCsDSinba() {
		return csDSinba;
	}

	public void setCsDSinba(CsDSinba csDSinba) {
		this.csDSinba = csDSinba;
	}

	public Boolean getToExport() {
		return toExport;
	}

	public void setToExport(Boolean toExport) {
		this.toExport = toExport;
	}

	public Boolean getEsportabile() {
		return esportabile;
	}

	public void setEsportabile(Boolean esportabile) {
		this.esportabile = esportabile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/*Valori da utilizzare in output*/
	public Long getDiarioId(){
		return this.csDSinba.getCsDDiario().getId();
	}
	
	public Date getDtIns(){
		return this.csDSinba.getCsDDiario().getDtIns();
	}
	
	public Date getDtValutazione(){
		return this.csDSinba.getCsDDiario().getDtAmministrativa();
	}
	
	public Date getDtModifica(){
		return this.csDSinba.getCsDDiario().getDtMod()!=null ? this.csDSinba.getCsDDiario().getDtMod() : this.csDSinba.getCsDDiario().getDtIns();
	}
	
	public CsDDiario getDiario(){
		return this.csDSinba.getCsDDiario();
	}
	
	public Date getDataExport(){
		return this.csDSinba.getDataExport();
	}
	
	public String getIdentificazioneFlusso(){
		return this.csDSinba.getCsDExportSinba()!=null ? this.csDSinba.getCsDExportSinba().getIdentificazioneFlusso() : null;
	}
	//SISO-777 
	public Boolean getCodiciDaAggiornare() {
		return codiciDaAggiornare;
	}

	public void setCodiciDaAggiornare(Boolean codiciDaAggiornare) {
		this.codiciDaAggiornare = codiciDaAggiornare;
	}
}
