package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the AM_INSTANCE_COMUNE database table.
 * 
 */
@Embeddable
public class AmUserGroupPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="FK_AM_GROUP")
	private String fkAmGroup;

	@Column(name="FK_AM_USER")
	private String fkAmUser;

    public AmUserGroupPK() {
    }

	public String getFkAmUser() {
		return fkAmUser;
	}

	public void setFkAmUser(String fkAmUser) {
		this.fkAmUser = fkAmUser;
	}

	public String getFkAmGroup() {
		return fkAmGroup;
	}

	public void setFkAmGroup(String fkAmGroup) {
		this.fkAmGroup = fkAmGroup;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AmUserGroupPK)) {
			return false;
		}
		AmUserGroupPK castOther = (AmUserGroupPK)other;
		return 
			this.fkAmUser.equals(castOther.fkAmUser)
			&& this.fkAmGroup.equals(castOther.fkAmGroup);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.fkAmUser.hashCode();
		hash = hash * prime + this.fkAmGroup.hashCode();
		
		return hash;
    }
}