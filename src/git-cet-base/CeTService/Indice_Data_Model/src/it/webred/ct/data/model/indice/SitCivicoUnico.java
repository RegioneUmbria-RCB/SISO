package it.webred.ct.data.model.indice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SIT_CIVICO_UNICO database table.
 * 
 */
public class SitCivicoUnico implements SitUnico, Serializable {
	private static final long serialVersionUID = 1L;

	private long idCivico;

	private String civico;

 	private Date dtIns;

	private BigDecimal validato;

	private BigDecimal fkVia;
	
	private String codiceCivOrig;
	
	private BigDecimal rating;
	
	

    public BigDecimal getFkVia() {
		return fkVia;
	}

	public void setFkVia(BigDecimal fkVia) {
		this.fkVia = fkVia;
	}

	public SitCivicoUnico() {
    }

	public long getIdCivico() {
		return this.idCivico;
	}

	public void setIdCivico(long idCivico) {
		this.idCivico = idCivico;
	}

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public BigDecimal getValidato() {
		return this.validato;
	}

	public void setValidato(BigDecimal validato) {
		this.validato = validato;
	}

	public long getId() {
		return getIdCivico();
	}

	public String getCodiceCivOrig() {
		return codiceCivOrig;
	}

	public void setCodiceCivOrig(String codiceCivOrig) {
		this.codiceCivOrig = codiceCivOrig;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
	
	
}