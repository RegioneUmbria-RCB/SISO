package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_A_TRIBUNALE database table.
 * 
 */
@Entity
@Table(name="CS_A_TRIBUNALE")
@NamedQuery(name="CsATribunale.findAll", query="SELECT c FROM CsATribunale c")
public class CsATribunale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_TRIBUNALE_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_TRIBUNALE_ID_GENERATOR")
	private long id;

	@Column(name="TM_CIVILE")
	private String tmCivile;
	
	@Column(name="TM_AMM")
	private String tmAmm;
	
	@Column(name="PENALE_MIN")
	private String penaleMin;
	
	@Column(name="TO_FLG")
	private String to;
	
	private String nis;
	
	@Column(name="MACRO_SEGNAL_ID")
	private BigDecimal macroSegnalId;
	
	@Column(name="MICRO_SEGNAL_ID")
	private BigDecimal microSegnalId;
	
	@Column(name="MOTIVO_SEGNAL_ID")
	private BigDecimal motivoSegnalId;
	
	@Column(name="PROCURA_MIN")
	private String procuraMin;
	
	@Column(name="NUMERO_PROTOCOLLO")
	private String numeroProtocollo;
	
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
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID")
	private CsASoggettoLAZY csASoggetto;

	public CsATribunale() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
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

	public CsASoggettoLAZY getCsASoggetto() {
		return this.csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public String getTmCivile() {
		return tmCivile;
	}

	public void setTmCivile(String tmCivile) {
		this.tmCivile = tmCivile;
	}

	public String getTmAmm() {
		return tmAmm;
	}

	public void setTmAmm(String tmAmm) {
		this.tmAmm = tmAmm;
	}

	public String getPenaleMin() {
		return penaleMin;
	}

	public void setPenaleMin(String penaleMin) {
		this.penaleMin = penaleMin;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getNis() {
		return nis;
	}

	public void setNis(String nis) {
		this.nis = nis;
	}

	public BigDecimal getMacroSegnalId() {
		return macroSegnalId;
	}

	public void setMacroSegnalId(BigDecimal macroSegnalId) {
		this.macroSegnalId = macroSegnalId;
	}

	public BigDecimal getMicroSegnalId() {
		return microSegnalId;
	}

	public void setMicroSegnalId(BigDecimal microSegnalId) {
		this.microSegnalId = microSegnalId;
	}

	public BigDecimal getMotivoSegnalId() {
		return motivoSegnalId;
	}

	public void setMotivoSegnalId(BigDecimal motivoSegnalId) {
		this.motivoSegnalId = motivoSegnalId;
	}

	public String getProcuraMin() {
		return procuraMin;
	}

	public void setProcuraMin(String procuraMin) {
		this.procuraMin = procuraMin;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

}