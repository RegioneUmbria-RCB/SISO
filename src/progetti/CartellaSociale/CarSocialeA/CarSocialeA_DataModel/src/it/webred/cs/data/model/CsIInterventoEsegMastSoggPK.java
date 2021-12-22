package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Embeddable
public class CsIInterventoEsegMastSoggPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column
	private String cf;
	
	@Column(name="INT_ESEG_MAST_ID")
	private Long masterId;
	

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CsIInterventoEsegMastSoggPK)) {
			return false;
		}
		CsIInterventoEsegMastSoggPK castOther = (CsIInterventoEsegMastSoggPK)other;
		return 
			(this.cf == castOther.cf)
			&& (this.masterId == castOther.masterId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.masterId.intValue();
		hash = hash * prime + this.cf.hashCode();
		
		return hash;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	
}