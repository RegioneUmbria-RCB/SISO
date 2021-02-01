package it.webred.amprofiler.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the AM_SECTION database table.
 * 
 */
@Entity
@Table(name="AM_SECTION")
public class AmSection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="FK_AM_APPLICATION")
	private String fkAmApplication;

	@Column(name="FK_AM_FONTE")
	private BigDecimal fkAmFonte;

	@Column(name="N_ORDINE")
	private BigDecimal nOrdine;

	private String name;

	private BigDecimal tipo;

    public AmSection() {
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

	public BigDecimal getFkAmFonte() {
		return this.fkAmFonte;
	}

	public void setFkAmFonte(BigDecimal fkAmFonte) {
		this.fkAmFonte = fkAmFonte;
	}

	public BigDecimal getNOrdine() {
		return this.nOrdine;
	}

	public void setNOrdine(BigDecimal nOrdine) {
		this.nOrdine = nOrdine;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getTipo() {
		return this.tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

}