package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_INSTANCE_COMUNE database table.
 * 
 */
@Entity
@Table(name="AM_INSTANCE_COMUNE")
public class AmInstanceComune implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AmInstanceComunePK id;

    public AmInstanceComune() {
    }

	public AmInstanceComunePK getId() {
		return this.id;
	}

	public void setId(AmInstanceComunePK id) {
		this.id = id;
	}
	
	
}