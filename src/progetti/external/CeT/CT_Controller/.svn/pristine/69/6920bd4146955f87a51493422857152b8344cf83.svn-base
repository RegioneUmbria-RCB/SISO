package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the R_EVENTI_ENTE database table.
 * 
 */
@Embeddable
public class REventiEntePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Long id;

	private String belfiore;

    public REventiEntePK() {
    }
    
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBelfiore() {
		return this.belfiore;
	}
	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof REventiEntePK)) {
			return false;
		}
		REventiEntePK castOther = (REventiEntePK)other;
		return 
			(this.id == castOther.id)
			&& this.belfiore.equals(castOther.belfiore);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.id ^ (this.id >>> 32)));
		hash = hash * prime + this.belfiore.hashCode();
		
		return hash;
    }
}