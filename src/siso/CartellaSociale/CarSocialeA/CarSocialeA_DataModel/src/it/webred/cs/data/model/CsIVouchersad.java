package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_I_VOUCHERSAD database table.
 * 
 */
@Entity
@Table(name="CS_I_VOUCHERSAD")
@NamedQuery(name="CsIVouchersad.findAll", query="SELECT c FROM CsIVouchersad c")
public class CsIVouchersad implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@SequenceGenerator(name="CS_I_VOUCHERSAD_INTERVENTOID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_VOUCHERSAD_INTERVENTOID_GENERATOR")
//	@Column(name="INTERVENTO_ID")
	private long interventoId;

	@Temporal(TemporalType.DATE)
	@Column(name="AUM_DIM_ORE_DAL")
	private Date aumDimOreDal;

	@Column(name="AUMENTO_ORE")
	private BigDecimal aumentoOre;

	@Column(name="CONTRIBUTIO_UTENTE")
	private BigDecimal contributioUtente;

	@Column(name="DIMINUZIONE_ORE")
	private BigDecimal diminuzioneOre;

	@Column(name="NUM_ACCESSI")
	private BigDecimal numAccessi;

	@Column(name="ORE_PREVISTE")
	private BigDecimal orePreviste;

	@Column(name="TIPO_ORE_PREVISTE")
	private String tipoOrePreviste;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	public CsIVouchersad() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public Date getAumDimOreDal() {
		return this.aumDimOreDal;
	}

	public void setAumDimOreDal(Date aumDimOreDal) {
		this.aumDimOreDal = aumDimOreDal;
	}

	public BigDecimal getAumentoOre() {
		return this.aumentoOre;
	}

	public void setAumentoOre(BigDecimal aumentoOre) {
		this.aumentoOre = aumentoOre;
	}

	public BigDecimal getContributioUtente() {
		return this.contributioUtente;
	}

	public void setContributioUtente(BigDecimal contributioUtente) {
		this.contributioUtente = contributioUtente;
	}

	public BigDecimal getDiminuzioneOre() {
		return this.diminuzioneOre;
	}

	public void setDiminuzioneOre(BigDecimal diminuzioneOre) {
		this.diminuzioneOre = diminuzioneOre;
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