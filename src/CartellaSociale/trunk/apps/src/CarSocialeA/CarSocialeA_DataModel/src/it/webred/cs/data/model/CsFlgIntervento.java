package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CS_FLG_INTERVENTO database table.
 * 
 */
@Entity
@Table(name="CS_FLG_INTERVENTO")
@NamedQuery(name="CsFlgIntervento.findAll", query="SELECT c FROM CsFlgIntervento c")
public class CsFlgIntervento implements ICsDDiarioChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

	@Column(name="DESCR_SOSPENSIONE")
	private String descrSospensione;

	@Column(name="FLAG_ATT_SOSP_C")
	private String flagAttSospC;

	@Column(name="FLAG_RESPINTO")
	private BigDecimal flagRespinto;

	@Column(name="MOTIVO_RESPINTO")
	private String motivoRespinto;

	@Column(name="TIPO_ATTIVAZIONE")
	private String tipoAttivazione;

	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID")
	private CsDDiario csDDiario;
	
	//bi-directional many-to-one association to CsIIntervento
	@ManyToOne
	@JoinColumn(name="INTERVENTO_ID")
	private CsIIntervento csIIntervento;

	//bi-directional many-to-one association to CsTbMotivoChiusuraInt
	@ManyToOne
	@JoinColumn(name="MOTIVO_CHIUSURA_INT_ID")
	private CsTbMotivoChiusuraInt csTbMotivoChiusuraInt;

	public CsFlgIntervento() {
	}

	public Long getDiarioId() {
		return this.diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public String getDescrSospensione() {
		return this.descrSospensione;
	}

	public void setDescrSospensione(String descrSospensione) {
		this.descrSospensione = descrSospensione;
	}

	public String getFlagAttSospC() {
		return this.flagAttSospC;
	}

	public void setFlagAttSospC(String flagAttSospC) {
		this.flagAttSospC = flagAttSospC;
	}

	public BigDecimal getFlagRespinto() {
		return this.flagRespinto;
	}

	public void setFlagRespinto(BigDecimal flagRespinto) {
		this.flagRespinto = flagRespinto;
	}

	public String getMotivoRespinto() {
		return this.motivoRespinto;
	}

	public void setMotivoRespinto(String motivoRespinto) {
		this.motivoRespinto = motivoRespinto;
	}

	public String getTipoAttivazione() {
		return this.tipoAttivazione;
	}

	public void setTipoAttivazione(String tipoAttivazione) {
		this.tipoAttivazione = tipoAttivazione;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiario() : csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

	public CsTbMotivoChiusuraInt getCsTbMotivoChiusuraInt() {
		return this.csTbMotivoChiusuraInt;
	}

	public void setCsTbMotivoChiusuraInt(CsTbMotivoChiusuraInt csTbMotivoChiusuraInt) {
		this.csTbMotivoChiusuraInt = csTbMotivoChiusuraInt;
	}

}