package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the SITIPART database table.
 * 
 */
@Embeddable
public class SitipartPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="COD_NAZIONALE")
	private String codNazionale;

	private Long foglio;

	private String particella;

	private String sub;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private java.util.Date dataFineVal;

    public SitipartPK() {
    }
	public String getCodNazionale() {
		return this.codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public Long getFoglio() {
		return this.foglio;
	}
	public void setFoglio(Long foglio) {
		this.foglio = foglio;
	}
	public String getParticella() {
		return this.particella;
	}
	public void setParticella(String particella) {
		this.particella = particella;
	}
	public String getSub() {
		return this.sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public java.util.Date getDataFineVal() {
		return this.dataFineVal;
	}
	public void setDataFineVal(java.util.Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitipartPK)) {
			return false;
		}
		SitipartPK castOther = (SitipartPK)other;
		return 
			this.codNazionale.equals(castOther.codNazionale)
			&& (this.foglio == castOther.foglio)
			&& this.particella.equals(castOther.particella)
			&& this.sub.equals(castOther.sub)
			&& this.dataFineVal.equals(castOther.dataFineVal);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.codNazionale.hashCode();
		hash = hash * prime + ((int) (this.foglio ^ (this.foglio >>> 32)));
		hash = hash * prime + this.particella.hashCode();
		hash = hash * prime + this.sub.hashCode();
		hash = hash * prime + this.dataFineVal.hashCode();
		
		return hash;
    }
}