package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;

public class IseeDataIn extends CeTBaseObject implements Serializable {
		
	//recupero dati
	private String codiceFiscale;
	private Integer anno;
	private boolean richiediUltimiRedditiDisp;
	
	public IseeDataIn() {
		super();
	}

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public boolean isRichiediUltimiRedditiDisp() {
		return richiediUltimiRedditiDisp;
	}

	public void setRichiediUltimiRedditiDisp(boolean richiediUltimiRedditiDisp) {
		this.richiediUltimiRedditiDisp = richiediUltimiRedditiDisp;
	}
	
	
	public String getAnnoStr() {
		if (getAnno() != null) {
			return "" + getAnno().intValue();
		}
		return null;
	}
	
}
