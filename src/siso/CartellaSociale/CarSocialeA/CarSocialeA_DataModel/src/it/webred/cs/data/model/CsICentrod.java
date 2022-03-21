package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the CS_I_CENTROD database table.
 * 
 */
@Entity
@Table(name="CS_I_CENTROD")
@NamedQuery(name="CsICentrod.findAll", query="SELECT c FROM CsICentrod c")
public class CsICentrod implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoId;

	@Column(name="CONTRIBUTIO_UTENTE")
	private BigDecimal contributioUtente;

	@Column(name="DIETA_SPECIALE")
	private BigDecimal dietaSpeciale;

	@Column(name="FLAG_NECESS_TRASPORTO")
	private BigDecimal flagNecessTrasporto;

	@Column(name="TIPO_QUOTA_UTENTE")
	private String tipoQuotaUtente;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;

	public CsICentrod() {
	}

	public long getInterventoId() {
		return this.interventoId;
	}

	public void setInterventoId(long interventoId) {
		this.interventoId = interventoId;
	}

	public BigDecimal getContributioUtente() {
		return this.contributioUtente;
	}

	public void setContributioUtente(BigDecimal contributioUtente) {
		this.contributioUtente = contributioUtente;
	}

	public BigDecimal getDietaSpeciale() {
		return this.dietaSpeciale;
	}

	public void setDietaSpeciale(BigDecimal dietaSpeciale) {
		this.dietaSpeciale = dietaSpeciale;
	}

	public BigDecimal getFlagNecessTrasporto() {
		return this.flagNecessTrasporto;
	}

	public void setFlagNecessTrasporto(BigDecimal flagNecessTrasporto) {
		this.flagNecessTrasporto = flagNecessTrasporto;
	}

	public String getTipoQuotaUtente() {
		return this.tipoQuotaUtente;
	}

	public void setTipoQuotaUtente(String tipoQuotaUtente) {
		this.tipoQuotaUtente = tipoQuotaUtente;
	}

	public CsIIntervento getCsIIntervento() {
		return this.csIIntervento;
	}

	public void setCsIIntervento(CsIIntervento csIIntervento) {
		this.csIIntervento = csIIntervento;
	}

}