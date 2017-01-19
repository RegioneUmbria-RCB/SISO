package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_A_CASO_OPE_TIPO_OPE database table.
 * 
 */
@Entity
@Table(name="CS_A_CASO_OPE_TIPO_OPE")
@NamedQuery(name="CsACasoOpeTipoOpe.findAll", query="SELECT c FROM CsACasoOpeTipoOpe c")
public class CsACasoOpeTipoOpe implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CS_A_CASO_OPE_TIPO_OPE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_CASO_OPE_TIPO_OPE_ID_GENERATOR")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_APP")
	private java.util.Date dataFineApp;

	@Column(name="CASO_ID")
	private long casoId;

	@Column(name="OPERATORE_TIPO_OPERATORE_ID")
	private long operatoreTipoOperatoreId;
	
/*	@EmbeddedId
	private CsACasoOpeTipoOpePK id;*/

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_SYS")
	private Date dataInizioSys;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_SYS")
	private Date dataFineSys;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_APP")
	private Date dataInizioApp;

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

	@Column(name="FLAG_RESPONSABILE")
	private Boolean flagResponsabile;
	
	//bi-directional many-to-one association to CsACaso
	@ManyToOne
	@JoinColumn(name="CASO_ID", insertable=false, updatable=false)
	private CsACaso csACaso;

	//bi-directional many-to-one association to CsOOperatoreTipoOperatore
	@ManyToOne
	@JoinColumn(name="OPERATORE_TIPO_OPERATORE_ID", insertable=false, updatable=false)
	private CsOOperatoreTipoOperatore csOOperatoreTipoOperatore;

	public CsACasoOpeTipoOpe() {
	}

	/*public CsACasoOpeTipoOpePK getId() {
		return this.id;
	}

	public void setId(CsACasoOpeTipoOpePK id) {
		this.id = id;
	}*/

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
	
	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
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
	
	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public CsACaso getCsACaso() {
		return this.csACaso;
	}

	public void setCsACaso(CsACaso csACaso) {
		this.csACaso = csACaso;
	}

	public CsOOperatoreTipoOperatore getCsOOperatoreTipoOperatore() {
		return this.csOOperatoreTipoOperatore;
	}

	public void setCsOOperatoreTipoOperatore(CsOOperatoreTipoOperatore csOOperatoreTipoOperatore) {
		this.csOOperatoreTipoOperatore = csOOperatoreTipoOperatore;
	}

	public Boolean getFlagResponsabile() {
		return flagResponsabile;
	}

	public void setFlagResponsabile(Boolean flagResponsabile) {
		this.flagResponsabile = flagResponsabile;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public java.util.Date getDataFineApp() {
		return dataFineApp;
	}

	public void setDataFineApp(java.util.Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}

	public long getCasoId() {
		return casoId;
	}

	public void setCasoId(long casoId) {
		this.casoId = casoId;
	}

	public long getOperatoreTipoOperatoreId() {
		return operatoreTipoOperatoreId;
	}

	public void setOperatoreTipoOperatoreId(long operatoreTipoOperatoreId) {
		this.operatoreTipoOperatoreId = operatoreTipoOperatoreId;
	}
	
}