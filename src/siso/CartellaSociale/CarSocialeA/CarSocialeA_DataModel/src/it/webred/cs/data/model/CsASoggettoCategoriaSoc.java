package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the CS_A_SOGGETTO_CATEGORIA_SOC database table.
 * 
 */
@Entity
@Table(name="CS_A_SOGGETTO_CATEGORIA_SOC")
@NamedQuery(name="CsASoggettoCategoriaSoc.findAll", query="SELECT c FROM CsASoggettoCategoriaSoc c")
public class CsASoggettoCategoriaSoc implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CsASoggettoCategoriaSocPK id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_SYS")
	private Date dataInizioSys;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_SYS")
	private Date dataFineSys;
	
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

	private Integer prevalente;
	
	//bi-directional many-to-one association to CsCCategoriaSociale
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CATEGORIA_SOCIALE_ID", insertable=false, updatable=false)
	private CsCCategoriaSociale csCCategoriaSociale;

	public CsASoggettoCategoriaSoc() {
	}

	public CsASoggettoCategoriaSocPK getId() {
		return this.id;
	}

	public void setId(CsASoggettoCategoriaSocPK id) {
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

	public CsCCategoriaSociale getCsCCategoriaSociale() {
		return this.csCCategoriaSociale;
	}

	public void setCsCCategoriaSociale(CsCCategoriaSociale csCCategoriaSociale) {
		this.csCCategoriaSociale = csCCategoriaSociale;
	}

	public Integer getPrevalente() {
		return prevalente;
	}

	public void setPrevalente(Integer prevalente) {
		this.prevalente = prevalente;
	}

}