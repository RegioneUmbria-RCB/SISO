package it.webred.ct.data.model.condono;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;


@Embeddable
public class CondonoCoordinatePK implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal codcondono;

	private BigDecimal foglio;

	private String mappale;

	private String sub;

    public CondonoCoordinatePK() {
    }

	public BigDecimal getCodcondono() {
		return this.codcondono;
	}

	public void setCodcondono(BigDecimal codcondono) {
		this.codcondono = codcondono;
	}

	public BigDecimal getFoglio() {
		return this.foglio;
	}

	public void setFoglio(BigDecimal foglio) {
		this.foglio = foglio;
	}

	public String getMappale() {
		return this.mappale;
	}

	public void setMappale(String mappale) {
		this.mappale = mappale;
	}

	public String getSub() {
		return this.sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;

		if(obj instanceof CondonoCoordinatePK){
			
		CondonoCoordinatePK pk = (CondonoCoordinatePK) obj;
		return codcondono.equals(pk.getCodcondono())
				&& foglio.equals(pk.getFoglio())
				&& mappale.equals(pk.getMappale())
				&& sub.equals(pk.getSub());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.codcondono.hashCode() ^ (this.codcondono.hashCode() >>> 32)));
		hash = hash * prime + ((int) (this.foglio.hashCode() ^ (this.foglio.hashCode() >>> 32)));
		hash = hash * prime + this.mappale.hashCode();
		hash = hash * prime + this.sub.hashCode();
		
		return hash;
    }

}