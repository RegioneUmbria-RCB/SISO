package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

/**
 * The persistent class for the CS_A_CASO database table.
 * 
 */
@Entity
@Table(name="CS_A_CASO")
@NamedQuery(name="CsACaso.findAll", query="SELECT c FROM CsACaso c")
public class CsACaso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_CASO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_CASO_ID_GENERATOR")
	private Long id;

	private Long identificativo;
	
	private String note;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;
	
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

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	/*
	//bi-directional many-to-one association to CsACasoOpeTipoOpe
	@OneToMany(mappedBy="csACaso")
	private List<CsACasoOpeTipoOpe> csACasoOpeTipoOpes;*/

	/*//bi-directional many-to-one association to CsACasoOpeTipoOpe2
	@OneToMany(mappedBy="csACaso")
	private List<CsACasoOpeTipoOpe2> csACasoOpeTipoOpe2;*/
	
	//bi-directional one-to-one association to CsASoggetto
	@OneToOne(mappedBy="csACaso")
	private CsASoggettoLAZY csASoggetto;

	//bi-directional many-to-one association to CsDDiario
	@OneToMany(mappedBy="csACaso" , fetch = FetchType.LAZY)
	private List<CsDDiario> csDDiarios;

	//bi-directional many-to-one association to CsItStep
	@OneToMany(mappedBy="csACaso", fetch = FetchType.LAZY)
	private List<CsItStep> csItSteps;

/*	//bi-directional many-to-one association to CsAlert
	@OneToMany(mappedBy="csACaso", fetch = FetchType.LAZY)
	private List<CsAlert> csAlerts;*/

	public CsACaso() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

/*	public List<CsACasoOpeTipoOpe> getCsACasoOpeTipoOpes() {
		return this.csACasoOpeTipoOpes;
	}

	public void setCsACasoOpeTipoOpes(List<CsACasoOpeTipoOpe> csACasoOpeTipoOpes) {
		this.csACasoOpeTipoOpes = csACasoOpeTipoOpes;
	}

	
	
	public CsACasoOpeTipoOpe addCsACasoOpeTipoOpe(CsACasoOpeTipoOpe csACasoOpeTipoOpe) {
		getCsACasoOpeTipoOpes().add(csACasoOpeTipoOpe);
		csACasoOpeTipoOpe.setCsACaso(this);

		return csACasoOpeTipoOpe;
	}

	public CsACasoOpeTipoOpe removeCsACasoOpeTipoOpe(CsACasoOpeTipoOpe csACasoOpeTipoOpe) {
		getCsACasoOpeTipoOpes().remove(csACasoOpeTipoOpe);
		csACasoOpeTipoOpe.setCsACaso(null);

		return csACasoOpeTipoOpe;
	}*/

	public CsASoggettoLAZY getCsASoggetto() {
	    return csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY param) {
	    this.csASoggetto = param;
	}

	public List<CsDDiario> getCsDDiarios() {
		return this.csDDiarios;
	}

	public void setCsDDiarios(List<CsDDiario> csDDiarios) {
		this.csDDiarios = csDDiarios;
	}

	public CsDDiario addCsDDiario(CsDDiario csDDiario) {
		getCsDDiarios().add(csDDiario);
		csDDiario.setCsACaso(this);

		return csDDiario;
	}

	public CsDDiario removeCsDDiario(CsDDiario csDDiario) {
		getCsDDiarios().remove(csDDiario);
		csDDiario.setCsACaso(null);

		return csDDiario;
	}

	public List<CsItStep> getCsItSteps() {
		return this.csItSteps;
	}

	public void setCsItSteps(List<CsItStep> csItSteps) {
		this.csItSteps = csItSteps;
	}

	public CsItStep addCsItStep(CsItStep csItStep) {
		getCsItSteps().add(csItStep);
		csItStep.setCsACaso(this);

		return csItStep;
	}

	public CsItStep removeCsItStep(CsItStep csItStep) {
		getCsItSteps().remove(csItStep);
		csItStep.setCsACaso(null);

		return csItStep;
	}

/*	public List<CsAlert> getCsAlerts() {
		return this.csAlerts;
	}

	public void setCsAlerts(List<CsAlert> csAlerts) {
		this.csAlerts = csAlerts;
	}

	public CsAlert addCsAlert(CsAlert csAlert) {
		getCsAlerts().add(csAlert);
		csAlert.setCsACaso(this);

		return csAlert;
	}

	public CsAlert removeCsAlert(CsAlert csAlert) {
		getCsAlerts().remove(csAlert);
		csAlert.setCsACaso(null);

		return csAlert;
	}*/

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getIdentificativo() {
		return identificativo;
	}

	public void setIdentificativo(Long identificativo) {
		this.identificativo = identificativo;
	}

}