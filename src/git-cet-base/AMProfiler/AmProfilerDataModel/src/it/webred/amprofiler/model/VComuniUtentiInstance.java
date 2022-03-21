package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the V_COMUNI_UTENTI_INSTANCE database table.
 * 
 */
@Entity
@Table(name="V_COMUNI_UTENTI_INSTANCE")
public class VComuniUtentiInstance implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VComuniUtentiInstancePK id;

    public VComuniUtentiInstance() {
    }

	public VComuniUtentiInstancePK getId() {
		return id;
	}

	public void setId(VComuniUtentiInstancePK id) {
		this.id = id;
	}

    
}