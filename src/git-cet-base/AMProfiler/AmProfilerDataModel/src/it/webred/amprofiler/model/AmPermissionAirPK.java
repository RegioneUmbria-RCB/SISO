package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AM_INSTANCE_COMUNE database table.
 * 
 */
@Embeddable
public class AmPermissionAirPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_AM_AI_ROLE")
	private java.math.BigDecimal fkAmAiRole;

	@Column(name="FK_AM_PERMISSION")
	private String fkAmPermission;

    public AmPermissionAirPK() {
    }

	public java.math.BigDecimal getFkAmAiRole() {
		return fkAmAiRole;
	}

	public void setFkAmAiRole(java.math.BigDecimal fkAmAiRole) {
		this.fkAmAiRole = fkAmAiRole;
	}

	public String getFkAmPermission() {
		return fkAmPermission;
	}

	public void setFkAmPermission(String fkAmPermission) {
		this.fkAmPermission = fkAmPermission;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AmPermissionAirPK)) {
			return false;
		}
		AmPermissionAirPK castOther = (AmPermissionAirPK)other;
		return 
			this.fkAmAiRole.equals(castOther.fkAmAiRole)
			&& this.fkAmAiRole.equals(castOther.fkAmAiRole);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkAmAiRole.hashCode();
		hash = hash * prime + this.fkAmAiRole.hashCode();
		
		return hash;
    }
}