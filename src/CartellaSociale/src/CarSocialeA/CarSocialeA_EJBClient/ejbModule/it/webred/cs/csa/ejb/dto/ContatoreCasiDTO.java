package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ContatoreCasiDTO extends CeTBaseObject {
	private static final long serialVersionUID = 1L;
	
	private Integer countZonaSociale;
	private Integer countOrganizzazione;
	private Integer countSettore;
	
	public ContatoreCasiDTO(){
		countZonaSociale=0;
		countOrganizzazione=0;
		countSettore=0;
	}
	
	public Integer getCountZonaSociale() {
		return countZonaSociale;
	}
	public Integer getCountOrganizzazione() {
		return countOrganizzazione;
	}
	public Integer getCountSettore() {
		return countSettore;
	}
	public void setCountZonaSociale(Integer countZonaSociale) {
		this.countZonaSociale = countZonaSociale;
	}
	public void setCountOrganizzazione(Integer countOrganizzazione) {
		this.countOrganizzazione = countOrganizzazione;
	}
	public void setCountSettore(Integer countSettore) {
		this.countSettore = countSettore;
	}

}
