package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CS_A_INDIRIZZO database table.
 * 
 */
@Entity
@Table(name="CS_A_INDIRIZZO")
@NamedQuery(name="CsAIndirizzo.findAll", query="SELECT c FROM CsAIndirizzo c")
public class CsAIndirizzo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ANA_INDIRIZZO_ID")
	private Long anaIndirizzoId;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private Date dataFineApp;

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

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	//bi-directional one-to-one association to CsAAnaIndirizzo
	@OneToOne
	@JoinColumn(name="ANA_INDIRIZZO_ID")
	private CsAAnaIndirizzo csAAnaIndirizzo;

	//bi-directional many-to-one association to CsASoggetto
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID")
	private CsASoggettoLAZY csASoggetto;

	//bi-directional many-to-one association to CsTbTipoIndirizzo
	@ManyToOne
	@JoinColumn(name="TIPO_INDIRIZZO_ID")
	private CsTbTipoIndirizzo csTbTipoIndirizzo;

	public CsAIndirizzo() {
	}

	public Long getAnaIndirizzoId() {
		return this.anaIndirizzoId;
	}

	public void setAnaIndirizzoId(Long anaIndirizzoId) {
		this.anaIndirizzoId = anaIndirizzoId;
	}

	public Date getDataFineApp() {
		return this.dataFineApp;
	}

	public void setDataFineApp(Date dataFineApp) {
		this.dataFineApp = dataFineApp;
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

	public CsAAnaIndirizzo getCsAAnaIndirizzo() {
		return this.csAAnaIndirizzo;
	}

	public void setCsAAnaIndirizzo(CsAAnaIndirizzo csAAnaIndirizzo) {
		this.csAAnaIndirizzo = csAAnaIndirizzo;
	}

	public CsASoggettoLAZY getCsASoggetto() {
		return this.csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public CsTbTipoIndirizzo getCsTbTipoIndirizzo() {
		return this.csTbTipoIndirizzo;
	}

	public void setCsTbTipoIndirizzo(CsTbTipoIndirizzo csTbTipoIndirizzo) {
		this.csTbTipoIndirizzo = csTbTipoIndirizzo;
	}

}