package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_GROUP_AIR database table.
 * 
 */
@Entity
@Table(name="AM_GROUP_AIR")
public class AmGroupAir implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AmGroupAirPK id;

    public AmGroupAir() {
    }

	public AmGroupAirPK getId() {
		return id;
	}

	public void setId(AmGroupAirPK id) {
		this.id = id;
	}

}