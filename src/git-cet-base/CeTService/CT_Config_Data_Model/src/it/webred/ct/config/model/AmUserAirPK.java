package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AM_INSTANCE_COMUNE database table.
 * 
 */
@Embeddable
public class AmUserAirPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_AM_AI_ROLE")
	private java.math.BigDecimal fkAmAiRole;

	@Column(name="FK_AM_USER")
	private String fkAmUser;
	
	@Column(name="FK_AM_COMUNE")
	private String fkAmComune;

    public AmUserAirPK() {
    }

	public String getFkAmUser() {
		return fkAmUser;
	}

	public void setFkAmUser(String fkAmUser) {
		this.fkAmUser = fkAmUser;
	}

	public java.math.BigDecimal getFkAmAiRole() {
		return fkAmAiRole;
	}

	public void setFkAmAiRole(java.math.BigDecimal fkAmAiRole) {
		this.fkAmAiRole = fkAmAiRole;
	}
	
	public String getFkAmComune() {
		return fkAmComune;
	}

	public void setFkAmComune(String fkAmComune) {
		this.fkAmComune = fkAmComune;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AmUserAirPK)) {
			return false;
		}
		AmUserAirPK castOther = (AmUserAirPK)other;
		return 
			this.fkAmUser.equals(castOther.fkAmUser)
			&& this.fkAmAiRole.equals(castOther.fkAmAiRole)
			&& this.fkAmComune.equals(castOther.fkAmComune);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkAmUser.hashCode();
		hash = hash * prime + this.fkAmAiRole.hashCode();
		hash = hash * prime + this.fkAmComune.hashCode();
		
		return hash;
    }
}