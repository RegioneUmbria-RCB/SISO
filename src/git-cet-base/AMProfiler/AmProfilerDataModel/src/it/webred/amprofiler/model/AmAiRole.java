package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the AM_AI_ROLE database table.
 * 
 */
@Entity
@Table(name="AM_AI_ROLE")
public class AmAiRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="FK_AM_APPLICATION_ITEM")
	private java.math.BigDecimal fkAmApplicationItem;

	@Column(name="FK_AM_ROLE")
	private String fkAmRole;

    public AmAiRole() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public java.math.BigDecimal getFkAmApplicationItem() {
		return this.fkAmApplicationItem;
	}

	public void setFkAmApplicationItem(java.math.BigDecimal fkAmApplicationItem) {
		this.fkAmApplicationItem = fkAmApplicationItem;
	}

	public String getFkAmRole() {
		return this.fkAmRole;
	}

	public void setFkAmRole(String fkAmRole) {
		this.fkAmRole = fkAmRole;
	}

}