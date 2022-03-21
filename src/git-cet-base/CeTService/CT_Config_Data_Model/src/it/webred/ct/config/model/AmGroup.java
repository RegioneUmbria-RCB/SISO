package it.webred.ct.config.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_GROUP database table.
 * 
 */
@Entity
@Table(name="AM_GROUP")
public class AmGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	@Column(name="FK_AM_COMUNE")
	private String fkAmComune;

    public AmGroup() {
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFkAmComune() {
		return this.fkAmComune;
	}

	public void setFkAmComune(String fkAmComune) {
		this.fkAmComune = fkAmComune;
	}

}