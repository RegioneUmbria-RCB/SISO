package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the CONS_UFRE_TAB database table.
 * 
 */
@Embeddable
public class ConsUfreTabPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PK_CUAA")
	private long pkCuaa;

	@Column(name="COD_NAZIONALE")
	private String codNazionale;

	private long foglio;

	private String particella;

	private String sub;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE")
	private java.util.Date dataFine;

    public ConsUfreTabPK() {
    }
	public long getPkCuaa() {
		return this.pkCuaa;
	}
	public void setPkCuaa(long pkCuaa) {
		this.pkCuaa = pkCuaa;
	}
	public String getCodNazionale() {
		return this.codNazionale;
	}
	public void setCodNazionale(String codNazionale) {
		this.codNazionale = codNazionale;
	}
	public long getFoglio() {
		return this.foglio;
	}
	public void setFoglio(long foglio) {
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
	public java.util.Date getDataFine() {
		return this.dataFine;
	}
	public void setDataFine(java.util.Date dataFine) {
		this.dataFine = dataFine;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConsUfreTabPK)) {
			return false;
		}
		ConsUfreTabPK castOther = (ConsUfreTabPK)other;
		return 
			(this.pkCuaa == castOther.pkCuaa)
			&& this.codNazionale.equals(castOther.codNazionale)
			&& (this.foglio == castOther.foglio)
			&& this.particella.equals(castOther.particella)
			&& this.sub.equals(castOther.sub)
			&& this.dataFine.equals(castOther.dataFine);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.pkCuaa ^ (this.pkCuaa >>> 32)));
		hash = hash * prime + this.codNazionale.hashCode();
		hash = hash * prime + ((int) (this.foglio ^ (this.foglio >>> 32)));
		hash = hash * prime + this.particella.hashCode();
		hash = hash * prime + this.sub.hashCode();
		hash = hash * prime + this.dataFine.hashCode();
		
		return hash;
    }
}