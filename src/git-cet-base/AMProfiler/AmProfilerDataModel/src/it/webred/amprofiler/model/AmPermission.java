package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_PERMISSION database table.
 * 
 */
@Entity
@Table(name="AM_PERMISSION")
public class AmPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;

	@Column(name="FK_AM_ITEM")
	private String fkAmItem;

    public AmPermission() {
    }

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFkAmItem() {
		return this.fkAmItem;
	}

	public void setFkAmItem(String fkAmItem) {
		this.fkAmItem = fkAmItem;
	}

}