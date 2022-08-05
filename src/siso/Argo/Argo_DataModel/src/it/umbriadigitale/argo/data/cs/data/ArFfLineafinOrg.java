package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "AR_FF_LINEAFIN_ORG")
public class ArFfLineafinOrg implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AR_LINEAFIN_ORG_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AR_LINEAFIN_ORG_ID_GENERATOR")
	private Long id;
	
	@Column(name="ORGANIZZAZIONE_ID")
	private Long organizzazioneId;
	
	@Column(name="LINEAFIN_ID")
	private Long lineaFinId;

	@ManyToOne
	@JoinColumn(name="ORGANIZZAZIONE_ID", insertable=false, updatable=false)
	private ArOOrganizzazione arOrganizzazione;
	
	private BigDecimal importo;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ArOOrganizzazione getArOrganizzazione() {
		return arOrganizzazione;
	}
	public void setArOrganizzazione(ArOOrganizzazione arOrganizzazione) {
		this.arOrganizzazione = arOrganizzazione;
	}

	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}
	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	public Long getLineaFinId() {
		return lineaFinId;
	}
	public void setLineaFinId(Long lineaFinId) {
		this.lineaFinId = lineaFinId;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

}
