package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_ITEM database table.
 * 
 */
@Entity
@Table(name="AM_ITEM")
public class AmItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

    public AmItem() {
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}