package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_I_ADM_ADH database table.
 * 
 */
@Entity
@Table(name="CS_I_ADM_ADH")
@NamedQuery(name="CsIAdmAdh.findAll", query="SELECT c FROM CsIAdmAdh c")
public class CsIAdmAdh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoId;

	private BigDecimal anno;

	@Column(name="CONTRIBUTO_FAMIGLIA")
	private BigDecimal contributoFamiglia;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_PROV_AG")
	private Date dtProvAg;

	@Column(name="FLAG_SENZA_PROVVEDIMENTO")
	private BigDecimal flagSenzaProvvedimento;

	@Column(name="N_PROVV_AG")
	private String nProvvAg;

	@Column(name="NUM_ACCESSI")
	private BigDecimal numAccessi;

	@Column(name="ORE_PREVISTE")
	private BigDecimal orePreviste;

	private String tipo;

	@Column(name="TIPO_ORE_PREVISTE")
	private String tipoOrePreviste;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	public CsIAdmAdh() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public BigDecimal getContributoFamiglia() {
		return this.contributoFamiglia;
	}

	public void setContributoFamiglia(BigDecimal contributoFamiglia) {
		this.contributoFamiglia = contributoFamiglia;
	}

	public Date getDtProvAg() {
		return this.dtProvAg;
	}

	public void setDtProvAg(Date dtProvAg) {
		this.dtProvAg = dtProvAg;
	}

	public BigDecimal getFlagSenzaProvvedimento() {
		return this.flagSenzaProvvedimento;
	}

	public void setFlagSenzaProvvedimento(BigDecimal flagSenzaProvvedimento) {
		this.flagSenzaProvvedimento = flagSenzaProvvedimento;
	}

	public String getNProvvAg() {
		return this.nProvvAg;
	}

	public void setNProvvAg(String nProvvAg) {
		this.nProvvAg = nProvvAg;
	}

	public BigDecimal getNumAccessi() {
		return this.numAccessi;
	}

	public void setNumAccessi(BigDecimal numAccessi) {
		this.numAccessi = numAccessi;
	}

	public BigDecimal getOrePreviste() {
		return this.orePreviste;
	}

	public void setOrePreviste(BigDecimal orePreviste) {
		this.orePreviste = orePreviste;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoOrePreviste() {
		return this.tipoOrePreviste;
	}

	public void setTipoOrePreviste(String tipoOrePreviste) {
		this.tipoOrePreviste = tipoOrePreviste;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

}