package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the R_TRACCIA_DATE database table.
 * 
 */
@Entity
@Table(name="R_TRACCIA_DATE")
public class RTracciaDate implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RTracciaDatePK id;
	
    @Temporal( TemporalType.DATE)
	private Date datamax;

    @Temporal( TemporalType.DATE)
	private Date datamin;

    public RTracciaDate() {
    }

	public Date getDatamax() {
		return this.datamax;
	}

	public void setDatamax(Date datamax) {
		this.datamax = datamax;
	}

	public Date getDatamin() {
		return this.datamin;
	}

	public void setDatamin(Date datamin) {
		this.datamin = datamin;
	}

	public RTracciaDatePK getId() {
		return id;
	}

	public void setId(RTracciaDatePK id) {
		this.id = id;
	}

}