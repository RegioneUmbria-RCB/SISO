package it.webred.ct.data.model.docfa;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the DOCFA_VALORI database table.
 * 
 */
@Embeddable
public class DocfaValoriPK implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private String microzona;

	private String stato;

	@Column(name="TIPOLOGIA_EDILIZIA")
	private String tipologiaEdilizia;

	public String getMicrozona() {
		return microzona;
	}

	public void setMicrozona(String microzona) {
		this.microzona = microzona;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getTipologiaEdilizia() {
		return tipologiaEdilizia;
	}

	public void setTipologiaEdilizia(String tipologiaEdilizia) {
		this.tipologiaEdilizia = tipologiaEdilizia;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean equals(Object o) {
		if (! (o instanceof DocfaValoriPK) )
			return false;
		DocfaValoriPK apk = (DocfaValoriPK) o;
		return (this.microzona.equals(apk.getMicrozona()) && this.stato.equals(apk.getStato()) &&
				this.tipologiaEdilizia.equals(apk.getTipologiaEdilizia())) ;
				
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.microzona.hashCode();
		hash = hash * prime + this.stato.hashCode();
		hash = hash * prime + this.tipologiaEdilizia.hashCode();
		
		
		return hash;
    }

}