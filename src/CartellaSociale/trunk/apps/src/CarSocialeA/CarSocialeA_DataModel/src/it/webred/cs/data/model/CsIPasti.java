package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the CS_I_PASTI database table.
 * 
 */
@Entity
@Table(name="CS_I_PASTI")
@NamedQuery(name="CsIPasti.findAll", query="SELECT c FROM CsIPasti c")
public class CsIPasti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
    private Long interventoId;
	
	@Column(name="CONTRIBUTIO_UTENTE")
	private BigDecimal contributioUtente;

	@Column(name="DIETA_SPECIALE")
	private BigDecimal dietaSpeciale;

	@Column(name="TIPO_QUOTA_UTENTE")
	private String tipoQuotaUtente;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_ID")
	@MapsId
	private CsIIntervento csIIntervento;
    
	public CsIPasti() {
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
		this.interventoId = csIIntervento.getId();
	}

	public Long getInterventoId() {
		return interventoId;
	}

	public void setInterventoId(Long interventoId) {
		this.interventoId = interventoId;
	}

}