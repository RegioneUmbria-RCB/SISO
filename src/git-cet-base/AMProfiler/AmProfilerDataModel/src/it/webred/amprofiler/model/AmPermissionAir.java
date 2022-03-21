package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_PERMISSION_AIR database table.
 * 
 */
@Entity
@Table(name="AM_PERMISSION_AIR")
public class AmPermissionAir implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AmPermissionAirPK id;

    public AmPermissionAir() {
    }

	public AmPermissionAirPK getId() {
		return id;
	}

	public void setId(AmPermissionAirPK id) {
		this.id = id;
	}

}