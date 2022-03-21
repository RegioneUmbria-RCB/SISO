package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the CS_I_RESI_ADULTI database table.
 * 
 */
@Entity
@Table(name="CS_I_RESI_ADULTI")
@NamedQuery(name="CsIResiAdulti.findAll", query="SELECT c FROM CsIResiAdulti c")
public class CsIResiAdulti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoId;

	private BigDecimal compartecipazione;

	@Column(name="CONTR_PER_OSPITE")
	private BigDecimal contrPerOspite;

	@Column(name="PRESSO_ALTRO")
	private String pressoAltro;

	@Column(name="VALORE_RETTA")
	private BigDecimal valoreRetta;

	//bi-directional many-to-one association to CsCComunita
	@ManyToOne
	@JoinColumn(name="PRESSO_ID")
	private CsCComunita csCComunita;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	//bi-directional many-to-one association to CsTbTipoRetta
	@ManyToOne
	@JoinColumn(name="TIPO_RETTA_ID")
	private CsTbTipoRetta csTbTipoRetta;

	public CsIResiAdulti() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public BigDecimal getCompartecipazione() {
		return this.compartecipazione;
	}

	public void setCompartecipazione(BigDecimal compartecipazione) {
		this.compartecipazione = compartecipazione;
	}

	public BigDecimal getContrPerOspite() {
		return this.contrPerOspite;
	}

	public void setContrPerOspite(BigDecimal contrPerOspite) {
		this.contrPerOspite = contrPerOspite;
	}

	public String getPressoAltro() {
		return this.pressoAltro;
	}

	public void setPressoAltro(String pressoAltro) {
		this.pressoAltro = pressoAltro;
	}

	public BigDecimal getValoreRetta() {
		return this.valoreRetta;
	}

	public void setValoreRetta(BigDecimal valoreRetta) {
		this.valoreRetta = valoreRetta;
	}

	public CsCComunita getCsCComunita() {
		return this.csCComunita;
	}

	public void setCsCComunita(CsCComunita csCComunita) {
		this.csCComunita = csCComunita;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

	public CsTbTipoRetta getCsTbTipoRetta() {
		return this.csTbTipoRetta;
	}

	public void setCsTbTipoRetta(CsTbTipoRetta csTbTipoRetta) {
		this.csTbTipoRetta = csTbTipoRetta;
	}

}