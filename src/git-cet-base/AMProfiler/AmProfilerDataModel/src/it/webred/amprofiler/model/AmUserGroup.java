package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_USER_GROUP database table.
 * 
 */
@Entity
@Table(name="AM_USER_GROUP")
public class AmUserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AmUserGroupPK id;

    public AmUserGroup() {
    }

	public AmUserGroupPK getId() {
		return id;
	}

	public void setId(AmUserGroupPK id) {
		this.id = id;
	}

}