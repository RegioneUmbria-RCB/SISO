package it.umbriadigitale.argo.ejb.client.cs.dto.configurazione;

import java.io.Serializable;

public class ProgettiSearchCriteria implements Serializable {

	private Integer first;
	private Integer pageSize;
	private String zonaSociale;
	private String codiceMemo;
	private String descrizione;
	public Integer getFirst() {
		return first;
	}
	public void setFirst(Integer first) {
		this.first = first;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getCodiceMemo() {
		return codiceMemo;
	}
	public void setCodiceMemo(String codiceMemo) {
		this.codiceMemo = codiceMemo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getZonaSociale() {
		return zonaSociale;
	}
	public void setZonaSociale(String zonaSociale) {
		this.zonaSociale = zonaSociale;
	}
	
}
