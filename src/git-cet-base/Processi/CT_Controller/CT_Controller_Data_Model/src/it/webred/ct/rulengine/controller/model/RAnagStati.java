package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the R_ANAG_STATI database table.
 * 
 */
@Entity
@Table(name="R_ANAG_STATI")
public class RAnagStati implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String descr;
	
	@ManyToOne
	@JoinColumn(name="FK_COMMAND_TYPE", referencedColumnName="ID")
	private RCommandType RCommandType;
	
	/*
	 * Indica se si tratta di uno stato iniziale finale o di errore,
	 * utile in fase di runtime per il recupero in base al tipo di operazione
	 * sulla fonte dati
	 */
	private String tipo;
	
    public RAnagStati() {
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

	public RCommandType getRCommandType() {
		return RCommandType;
	}

	public void setRCommandType(RCommandType RCommandType) {
		this.RCommandType = RCommandType;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}


}