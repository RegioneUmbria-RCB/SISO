package it.webred.ct.config.model;

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

	@Id
	@Column(name="FK_AM_USER")
	private String fkAmUser;

	//uni-directional many-to-one association to AmGroup
    @ManyToOne
	@JoinColumn(name="FK_AM_GROUP")
	private AmGroup amGroup;

    public AmUserGroup() {
    }

	public String getFkAmUser() {
		return this.fkAmUser;
	}

	public void setFkAmUser(String fkAmUser) {
		this.fkAmUser = fkAmUser;
	}

	public AmGroup getAmGroup() {
		return this.amGroup;
	}

	public void setAmGroup(AmGroup amGroup) {
		this.amGroup = amGroup;
	}
	
}