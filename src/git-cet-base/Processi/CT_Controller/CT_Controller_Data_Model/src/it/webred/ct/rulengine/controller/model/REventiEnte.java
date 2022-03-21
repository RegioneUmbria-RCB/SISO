package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_EVENTI_ENTE database table.
 * 
 */
@Entity
@Table(name="R_EVENTI_ENTE")
public class REventiEnte implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private REventiEntePK id;
	
	private String standard;

    public REventiEnte() {
    }

	public REventiEntePK getId() {
		return this.id;
	}

	public void setId(REventiEntePK id) {
		this.id = id;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}
	
}