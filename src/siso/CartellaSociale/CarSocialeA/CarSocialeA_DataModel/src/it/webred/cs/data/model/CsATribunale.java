package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;
import java.util.List;


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
	private Boolean tmCivile;
	
	@Column(name="TM_AMM")
	private Boolean tmAmm;
	
	@Column(name="PENALE_MIN")
	private Boolean penaleMin;
	
	@Column(name="TO_FLG")
	private Boolean to;
	
	private Boolean nis;
	
	@Column(name="MACRO_SEGNAL_ID")
	private BigDecimal macroSegnalId;
	
	@Column(name="MICRO_SEGNAL_ID")
	private BigDecimal microSegnalId;
	
	@Column(name="MOTIVO_SEGNAL_ID")
	private BigDecimal motivoSegnalId;
	
	@Column(name="PROCURA_MIN")
	private Boolean procuraMin;
	
	@Column(name="PROCURA_ORD")
	private Boolean procuraOrd;
	
	@Column(name="INFO_NON_REPERIBILI")
	private Boolean infoNonReperibili;
	
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
	
	@Temporal(TemporalType.DATE)
	@Column(name="PRIMO_CONTATTO_AG")
	private Date primoContattoAG;

	@Column(name="CORTE_APPELLO")
	private Boolean corteAppello;
	
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

	public Boolean getProcuraOrd() {
		return procuraOrd;
	}

	public void setProcuraOrd(Boolean procuraOrd) {
		this.procuraOrd = procuraOrd;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public Date getPrimoContattoAG() {
		return primoContattoAG;
	}

	public void setPrimoContattoAG(Date primoContattoAG) {
		this.primoContattoAG = primoContattoAG;
	}

	public Boolean getInfoNonReperibili() {
		return infoNonReperibili;
	}

	public void setInfoNonReperibili(Boolean infoNonReperibili) {
		this.infoNonReperibili = infoNonReperibili;
	}

	public Boolean getTmCivile() {
		return tmCivile;
	}

	public void setTmCivile(Boolean tmCivile) {
		this.tmCivile = tmCivile;
	}

	public Boolean getTmAmm() {
		return tmAmm;
	}

	public void setTmAmm(Boolean tmAmm) {
		this.tmAmm = tmAmm;
	}

	public Boolean getPenaleMin() {
		return penaleMin;
	}

	public void setPenaleMin(Boolean penaleMin) {
		this.penaleMin = penaleMin;
	}

	public Boolean getTo() {
		return to;
	}

	public void setTo(Boolean to) {
		this.to = to;
	}

	public Boolean getNis() {
		return nis;
	}

	public void setNis(Boolean nis) {
		this.nis = nis;
	}

	public Boolean getProcuraMin() {
		return procuraMin;
	}

	public void setProcuraMin(Boolean procuraMin) {
		this.procuraMin = procuraMin;
	}

	public Boolean getCorteAppello() {
		return corteAppello;
	}

	public void setCorteAppello(Boolean corteAppello) {
		this.corteAppello = corteAppello;
	}

}