package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the CS_IT_STEP database table.
 * 
 */
@Entity
@Table(name="CS_IT_STEP")
public class CsItStepLAZY implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_IT_STEP_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_IT_STEP_ID_GENERATOR")
	private Long id;

	private String nota;

	@Basic
	@Column(name= "CASO_ID", insertable=false, updatable=false)
	protected Long csACasoId;
	
	//bi-directional many-to-one association to CsDDiario
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	@JoinColumn(name="DIARIO_ID")
	private CsDDiarioBASIC csDDiario;
	
	@Basic
	@Column(name= "DIARIO_ID", insertable=false, updatable=false)
	protected Long csDDiarioId;
	
	@Column(name= "OPERATORE_ID", insertable=false, updatable=false)
	protected Long csOOperatore1Id;
	
	//bi-directional many-to-one association to CsOOperatoreSettore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPERATORE_ID")
	private CsOOperatoreBASIC csOOperatore1;

	@Column(name= "OPERATORE_TO", insertable=false, updatable=false)
	protected Long csOOperatore2Id;

	//bi-directional many-to-one association to CsOOperatoreSettore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="OPERATORE_TO")
	private CsOOperatoreBASIC csOOperatore2;

	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORGANIZZAZIONE_ID_TO")
	private CsOOrganizzazione csOOrganizzazione2;

	//bi-directional many-to-one association to CsOOrganizzazione
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ORGANIZZAZIONE_ID")
	private CsOOrganizzazione csOOrganizzazione1;

	//bi-directional many-to-one association to CsOSettore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SETTORE_ID_TO")
	private CsOSettoreBASIC csOSettore2;

	//bi-directional many-to-one association to CsOSettore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SETTORE_ID")
	private CsOSettoreBASIC csOSettore1;

	//uni-directional many-to-one association to CsCfgItStato
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	@JoinColumn(name="CFG_IT_STATO_ID" )
	private CsCfgItStatoLAZY csCfgItStato;
	
//	@OneToMany(mappedBy="csItStep", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
	@Transient
	private List<CsItStepAttrValueBASIC> csItStepAttrValues;
	
	public CsItStepLAZY() {
	}

	public Long getCsACasoId() {
		return csACasoId;
	}

	public void setCsACasoId(Long csACasoId) {
		this.csACasoId = csACasoId;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public CsDDiarioBASIC getCsDDiario() {
		return csDDiario = (csDDiario==null) ? new CsDDiarioBASIC() : csDDiario;
	}

	public void setCsDDiario(CsDDiarioBASIC csDDiario) {
		this.csDDiario = csDDiario;
	}

	public CsCfgItStatoLAZY getCsCfgItStato() {
		return this.csCfgItStato;
	}

	public void setCsCfgItStato(CsCfgItStatoLAZY csCfgItStato) {
		this.csCfgItStato = csCfgItStato;
	}

	public CsOOperatoreBASIC getCsOOperatore1() {
		return this.csOOperatore1;
	}

	public void setCsOOperatore1(CsOOperatoreBASIC csOOperatore1) {
		this.csOOperatore1 = csOOperatore1;
	}

	public CsOOperatoreBASIC getCsOOperatore2() {
		return this.csOOperatore2;
	}

	public void setCsOOperatore2(CsOOperatoreBASIC csOOperatore2) {
		this.csOOperatore2 = csOOperatore2;
	}

	public CsOOrganizzazione getCsOOrganizzazione1() {
		return this.csOOrganizzazione1;
	}

	public void setCsOOrganizzazione1(CsOOrganizzazione csOOrganizzazione1) {
		this.csOOrganizzazione1 = csOOrganizzazione1;
	}

	public CsOOrganizzazione getCsOOrganizzazione2() {
		return this.csOOrganizzazione2;
	}

	public void setCsOOrganizzazione2(CsOOrganizzazione csOOrganizzazione2) {
		this.csOOrganizzazione2 = csOOrganizzazione2;
	}

	public CsOSettoreBASIC getCsOSettore1() {
		return this.csOSettore1;
	}

	public void setCsOSettore1(CsOSettoreBASIC csOSettore1) {
		this.csOSettore1 = csOSettore1;
	}

	public CsOSettoreBASIC getCsOSettore2() {
		return this.csOSettore2;
	}

	public void setCsOSettore2(CsOSettoreBASIC csOSettore2) {
		this.csOSettore2 = csOSettore2;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public List<CsItStepAttrValueBASIC> getCsItStepAttrValues() {
		return csItStepAttrValues;
	}

	public void setCsItStepAttrValues(List<CsItStepAttrValueBASIC> csItStepAttrValues) {
		this.csItStepAttrValues = csItStepAttrValues;
	}
	public Long getCsDDiarioId() {
		return csDDiarioId;
	}

	public void setCsDDiarioId(Long csDDiarioId) {
		this.csDDiarioId = csDDiarioId;
	}
	public Long getCsOOperatore1Id() {
		return csOOperatore1Id;
	}

	public void setCsOOperatore1Id(Long csOOperatore1Id) {
		this.csOOperatore1Id = csOOperatore1Id;
	}

	public Long getCsOOperatore2Id() {
		return csOOperatore2Id;
	}

	public void setCsOOperatore2Id(Long csOOperatore2Id) {
		this.csOOperatore2Id = csOOperatore2Id;
	}
	
	

	
}