package it.webred.cs.data.model;


import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_A_SOGGETTO_STATO_CIVILE database table.
 * 
 */
@Entity
@Table(name="CS_A_SOGGETTO_STATO_CIVILE")
@NamedQuery(name="CsASoggettoStatoCivile.findAll", query="SELECT c FROM CsASoggettoStatoCivile c")
public class CsASoggettoStatoCivile implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsASoggettoStatoCivilePK id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_SYS")
	private Date dataFineSys;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_SYS")
	private Date dataInizioSys;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="MOTIVO_FINE")
	private String motivoFine;

	@Column(name="MOTIVO_INIZIO")
	private String motivoInizio;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	//bi-directional many-to-one association to CsASoggetto
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID", insertable=false, updatable=false)
	private CsASoggettoLAZY csASoggetto;

	//bi-directional many-to-one association to CsTbStatoCivile
	@ManyToOne
	@JoinColumn(name="STATO_CIVILE_COD", insertable=false, updatable=false)
	private CsTbStatoCivile csTbStatoCivile;

	public CsASoggettoStatoCivile() {
	}

	public CsASoggettoStatoCivilePK getId() {
		return this.id;
	}

	public void setId(CsASoggettoStatoCivilePK id) {
		this.id = id;
	}

	public Date getDataFineSys() {
		return this.dataFineSys;
	}

	public void setDataFineSys(Date dataFineSys) {
		this.dataFineSys = dataFineSys;
	}

	public Date getDataInizioApp() {
		return this.dataInizioApp;
	}

	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}

	public Date getDataInizioSys() {
		return this.dataInizioSys;
	}

	public void setDataInizioSys(Date dataInizioSys) {
		this.dataInizioSys = dataInizioSys;
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

	public String getMotivoFine() {
		return this.motivoFine;
	}

	public void setMotivoFine(String motivoFine) {
		this.motivoFine = motivoFine;
	}

	public String getMotivoInizio() {
		return this.motivoInizio;
	}

	public void setMotivoInizio(String motivoInizio) {
		this.motivoInizio = motivoInizio;
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

	public CsTbStatoCivile getCsTbStatoCivile() {
		return this.csTbStatoCivile;
	}

	public void setCsTbStatoCivile(CsTbStatoCivile csTbStatoCivile) {
		this.csTbStatoCivile = csTbStatoCivile;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return this.csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

}