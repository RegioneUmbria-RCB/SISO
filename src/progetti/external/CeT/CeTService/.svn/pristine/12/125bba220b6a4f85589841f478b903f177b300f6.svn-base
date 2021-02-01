package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_R_99C_FINE_FILE database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R_99C_FINE_FILE")
public class VFineFlusso implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Column(name="COD_DEST_MITT")
	private String codDestMitt;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	private String filler;

	private String filler1;

	private String processid;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="TOT_REC")
	private BigDecimal totRec;

    public VFineFlusso() {
    }

	public String getCodDestMitt() {
		return this.codDestMitt;
	}

	public void setCodDestMitt(String codDestMitt) {
		this.codDestMitt = codDestMitt;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getFiller1() {
		return this.filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public BigDecimal getTotRec() {
		return this.totRec;
	}

	public void setTotRec(BigDecimal totRec) {
		this.totRec = totRec;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
}