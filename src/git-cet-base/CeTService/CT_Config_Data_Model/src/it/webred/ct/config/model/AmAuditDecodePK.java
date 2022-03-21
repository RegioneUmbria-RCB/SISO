package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AM_AUDIT_DECODE database table.
 * 
 */
@Embeddable
public class AmAuditDecodePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long id;

	private String standard;

    public AmAuditDecodePK() {
    }
	public long getId() {
		return this.id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getStandard() {
		return this.standard;
	}
	public void setStandard(String standard) {
		this.standard = standard;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AmAuditDecodePK)) {
			return false;
		}
		AmAuditDecodePK castOther = (AmAuditDecodePK)other;
		return 
			(this.id == castOther.id)
			&& this.standard.equals(castOther.standard);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
		hash = hash * prime + this.standard.hashCode();
		
		return hash;
    }
}