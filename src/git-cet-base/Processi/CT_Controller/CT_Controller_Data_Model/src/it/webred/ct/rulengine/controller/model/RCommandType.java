package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the R_COMMAND_TYPE database table.
 * 
 */
@Entity
@Table(name="R_COMMAND_TYPE")
public class RCommandType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descr;


    public RCommandType() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	
}