package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_APPLICATION_ITEM database table.
 * 
 */
@Entity
@Table(name="AM_APPLICATION_ITEM")
public class AmApplicationItem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="FK_AM_APPLICATION")
	private String fkAmApplication;

	@Column(name="FK_AM_ITEM")
	private String fkAmItem;

    public AmApplicationItem() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFkAmApplication() {
		return this.fkAmApplication;
	}

	public void setFkAmApplication(String fkAmApplication) {
		this.fkAmApplication = fkAmApplication;
	}

	public String getFkAmItem() {
		return this.fkAmItem;
	}

	public void setFkAmItem(String fkAmItem) {
		this.fkAmItem = fkAmItem;
	}

}