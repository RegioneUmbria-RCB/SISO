package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the R_COMMAND database table.
 * 
 */
@Entity
@Table(name="R_COMMAND")
public class RCommand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name="COD_COMMAND")
	private String codCommand;

	private String descr;

	@Column(name="LONG_DESCR")
	private String longDescr;

	/*
	 * 1 - comando primario (visibile da interfaccia); 
	 * 2 - comando non secondario (non visibile da interfaccia); 
	 * 3 - comando DUMMY;
	 */
	@Column(name="SYSTEM_COMMAND")
	private Long systemCommand;

	//bi-directional many-to-one association to RCommandType
    @ManyToOne
	@JoinColumn(name="FK_COMMAND_TYPE")
	private RCommandType RCommandType;


    public RCommand() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodCommand() {
		return this.codCommand;
	}

	public void setCodCommand(String codCommand) {
		this.codCommand = codCommand;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getLongDescr() {
		return this.longDescr;
	}

	public void setLongDescr(String longDescr) {
		this.longDescr = longDescr;
	}

	public Long getSystemCommand() {
		return this.systemCommand;
	}

	public void setSystemCommand(Long systemCommand) {
		this.systemCommand = systemCommand;
	}

	public RCommandType getRCommandType() {
		return this.RCommandType;
	}

	public void setRCommandType(RCommandType RCommandType) {
		this.RCommandType = RCommandType;
	}
	
	
}