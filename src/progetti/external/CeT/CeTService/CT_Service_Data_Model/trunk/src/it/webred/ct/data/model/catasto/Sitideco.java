package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SITIDECO database table.
 * 
 */
@Entity
public class Sitideco implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitidecoPK id;

	private String datatype;

	private String description;

    public Sitideco() {
    }

	public SitidecoPK getId() {
		return this.id;
	}

	public void setId(SitidecoPK id) {
		this.id = id;
	}
	
	public String getDatatype() {
		return this.datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}