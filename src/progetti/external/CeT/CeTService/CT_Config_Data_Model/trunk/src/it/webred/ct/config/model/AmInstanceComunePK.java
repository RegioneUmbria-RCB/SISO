package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AM_INSTANCE_COMUNE database table.
 * 
 */
@Embeddable
public class AmInstanceComunePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_AM_COMUNE")
	private String fkAmComune;

	@Column(name="FK_AM_INSTANCE")
	private String fkAmInstance;

    public AmInstanceComunePK() {
    }
	public String getFkAmComune() {
		return this.fkAmComune;
	}
	public void setFkAmComune(String fkAmComune) {
		this.fkAmComune = fkAmComune;
	}
	public String getFkAmInstance() {
		return this.fkAmInstance;
	}
	public void setFkAmInstance(String fkAmInstance) {
		this.fkAmInstance = fkAmInstance;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AmInstanceComunePK)) {
			return false;
		}
		AmInstanceComunePK castOther = (AmInstanceComunePK)other;
		return 
			this.fkAmComune.equals(castOther.fkAmComune)
			&& this.fkAmInstance.equals(castOther.fkAmInstance);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkAmComune.hashCode();
		hash = hash * prime + this.fkAmInstance.hashCode();
		
		return hash;
    }
}