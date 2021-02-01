package it.webred.ct.data.model.indice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SitFabbricatoUnico implements SitUnico,Serializable {
	
	private static final long serialVersionUID = 1L;

	private long idFabbricato;

	private Date dtIns;

	private String foglio;

	private String particella;

	private BigDecimal validato;
	
	private String codiceFabbOrig;
	
	private String sezione;
	
	private BigDecimal rating;

	
	public SitFabbricatoUnico() {
    }

				
	public long getIdFabbricato() {
		return idFabbricato;
	}
	public void setIdFabbricato(long idFabbricato) {
		this.idFabbricato = idFabbricato;
	}
	public Date getDtIns() {
		return dtIns;
	}
	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}
	public String getFoglio() {
		return foglio;
	}
	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public BigDecimal getValidato() {
		return validato;
	}
	public void setValidato(BigDecimal validato) {
		this.validato = validato;
	}
	public String getCodiceFabbOrig() {
		return codiceFabbOrig;
	}
	public void setCodiceFabbOrig(String codiceFabbOrig) {
		this.codiceFabbOrig = codiceFabbOrig;
	}






	public String getSezione() {
		return sezione;
	}






	public void setSezione(String sezione) {
		this.sezione = sezione;
	}






	public BigDecimal getRating() {
		return rating;
	}






	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}






	public long getId() {
		return getIdFabbricato();
	}
	
	
}
