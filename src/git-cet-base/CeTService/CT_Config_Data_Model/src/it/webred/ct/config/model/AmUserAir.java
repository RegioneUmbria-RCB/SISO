package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_USER_AIR database table.
 * 
 */
@Entity
@Table(name="AM_USER_AIR")
public class AmUserAir implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AmUserAirPK id;

    public AmUserAir() {
    }

	public AmUserAirPK getId() {
		return id;
	}

	public void setId(AmUserAirPK id) {
		this.id = id;
	}

}