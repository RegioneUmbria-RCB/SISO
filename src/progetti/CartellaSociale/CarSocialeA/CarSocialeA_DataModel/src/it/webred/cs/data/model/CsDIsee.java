package it.webred.cs.data.model;

import it.webred.cs.data.base.ICsDDiarioChild;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the CS_D_ISEE database table.
 * 
 */
@Entity
@Table(name="CS_D_ISEE")
@NamedQuery(name="CsDIsee.findAll", query="SELECT c FROM CsDIsee c")
public class CsDIsee implements ICsDDiarioChild {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="DIARIO_ID")
	private Long diarioId;

/*	@Temporal(TemporalType.DATE)
	@Column(name="DATA_SCADENZA_ISEE")
	private Date dataScadenzaIsee;*/
	
	@Column(name="ANNO_RIF")
	private String annoRif;
	
	private String note;
		
	@OneToOne
	@JoinColumn(name="TIPO_ID")
	private CsTbTipoIsee tipoIsee;
	
	private BigDecimal ise;
	
	private BigDecimal isee;
		
	@Column(name="SCALA_EQUIVALENZA")
	private BigDecimal scalaEquivalenza;
	
	@Column(name="PREFIX_PROT_DSU")
	private String prefixProtDSU;
	
	@Column(name="NUM_PROT_DSU")
	private String numProtDSU;
	
	@Column(name="PROG_PROT_DSU")
	private String progProtDSU;
	
	//bi-directional one-to-one association to CsDDiario
	@OneToOne
	@JoinColumn(name="DIARIO_ID")
	private CsDDiario csDDiario;
		
	public CsDIsee() {
	}

	public Long getDiarioId() {
		return diarioId;
	}

	public void setDiarioId(Long diarioId) {
		this.diarioId = diarioId;
	}

	public CsDDiario getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiario() : csDDiario;
	}

	public void setCsDDiario(CsDDiario csDDiario) {
		this.csDDiario = csDDiario;
	}

	public String getAnnoRif() {
		return annoRif;
	}

	public void setAnnoRif(String annoRif) {
		this.annoRif = annoRif;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

/*	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}*/

	public BigDecimal getIse() {
		return ise;
	}

	public void setIse(BigDecimal ise) {
		this.ise = ise;
	}

	public BigDecimal getIsee() {
		return isee;
	}

	public void setIsee(BigDecimal isee) {
		this.isee = isee;
	}

	public BigDecimal getScalaEquivalenza() {
		return scalaEquivalenza;
	}

	public void setScalaEquivalenza(BigDecimal scalaEquivalenza) {
		this.scalaEquivalenza = scalaEquivalenza;
	}

	public String getPrefixProtDSU() {
		return prefixProtDSU;
	}

	public void setPrefixProtDSU(String prefixProtDSU) {
		this.prefixProtDSU = prefixProtDSU;
	}

	public String getNumProtDSU() {
		return numProtDSU;
	}

	public void setNumProtDSU(String numProtDSU) {
		this.numProtDSU = numProtDSU;
	}

	public String getProgProtDSU() {
		return progProtDSU;
	}

	public void setProgProtDSU(String progProtDSU) {
		this.progProtDSU = progProtDSU;
	}

	public CsTbTipoIsee getTipoIsee() {
		return tipoIsee;
	}

	public void setTipoIsee(CsTbTipoIsee tipoIsee) {
		this.tipoIsee = tipoIsee;
	}
	
}