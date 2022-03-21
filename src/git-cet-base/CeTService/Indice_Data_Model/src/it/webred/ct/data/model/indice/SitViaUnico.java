package it.webred.ct.data.model.indice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the SIT_VIA_UNICO database table.
 * 
 */
public class SitViaUnico implements SitUnico,Serializable {
	private static final long serialVersionUID = 1L;

	private long idVia;

	private Date dtIns;

	private String indirizzo;

	private String sedime;

	private BigDecimal validato;
	
	private String codiceViaOrig;
	

    public SitViaUnico() {
    }

	public long getIdVia() {
		return this.idVia;
	}

	public void setIdVia(long idVia) {
		this.idVia = idVia;
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getSedime() {
		return this.sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public BigDecimal getValidato() {
		return this.validato;
	}

	public void setValidato(BigDecimal validato) {
		this.validato = validato;
	}

	public long getId() {
		return getIdVia();
	}

	public String getCodiceViaOrig() {
		return codiceViaOrig;
	}

	public void setCodiceViaOrig(String codiceViaOrig) {
		this.codiceViaOrig = codiceViaOrig;
	}
	
	/*public Set<SitViaTotale> getSitViaTotales() {
		return this.sitViaTotales;
	}

	public void setSitViaTotales(Set<SitViaTotale> sitViaTotales) {
		this.sitViaTotales = sitViaTotales;
	}
	
	public Set<SitCivicoUnico> getSitCivicoUnicos() {
		return this.sitCivicoUnicos;
	}

	public void setSitCivicoUnicos(Set<SitCivicoUnico> sitCivicoUnicos) {
		this.sitCivicoUnicos = sitCivicoUnicos;
	}*/
	
}