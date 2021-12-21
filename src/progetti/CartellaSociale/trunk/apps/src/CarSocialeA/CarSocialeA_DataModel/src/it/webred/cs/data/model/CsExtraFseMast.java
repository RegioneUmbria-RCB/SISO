package it.webred.cs.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


//<!-- SISO-1305-1306 -->

@Entity
@Table(name="CS_EXTRA_FSE_MAST")
@NamedQuery(name="CsExtraFseMast.findAll", query="SELECT c FROM CsExtraFseMast c")
public class CsExtraFseMast implements Serializable {

	private static final long serialVersionUID = -4203825486095827408L;

	@Id
	@SequenceGenerator(name="CS_EXTRA_FSE_MAST_ID_GENERATOR", sequenceName="SQ_EXTRA_FSE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_EXTRA_FSE_MAST_ID_GENERATOR")
	private Long id;
	
	@Column(name = "SCHEDA_ID")
	private Long schedaId;
	
	@Column(name="CASO_ID")
	private Long casoId;

	@Column(name="TIPO_EXTRA_FSE_ID")
	private Long tipo;
	
	@Column(name="OPERATORE_ID")
	private Long operatoreId;

	@Column(name="ORGANIZZAZIONE_ID")
	private Long organizzazioneId;

	@Column(name="USR_INS")
	private String usrIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@OneToOne(mappedBy="master",cascade = CascadeType.ALL, orphanRemoval=true)
	@PrimaryKeyJoinColumn
	private CsExtraFseSiru siru;

	public Long getCasoId() {
		return casoId;
	}

	public void setCasoId(Long casoId) {
		this.casoId = casoId;
	}
	
	public CsExtraFseMast() {
	}

	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSchedaId() {
		return schedaId;
	}

	public void setSchedaId(Long schedaId) {
		this.schedaId = schedaId;
	}

	public String getUsrIns() {
		return this.usrIns;
	}

	public void setUsrIns(String usrIns) {
		this.usrIns = usrIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public Long getOperatoreId() {
		return operatoreId;
	}

	public void setOperatoreId(Long operatoreId) {
		this.operatoreId = operatoreId;
	}

	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}

	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public CsExtraFseSiru getSiru() {
		return siru;
	}

	public void setSiru(CsExtraFseSiru siru) {
		this.siru = siru;
	}
	
}