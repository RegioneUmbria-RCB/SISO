package it.webred.cs.data.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the CS_A_INVALIDITA database table.
 * 
 */
@Entity
@Table(name="CS_A_DATI_INVALIDITA")
@NamedQuery(name="CsADatiInvalidita.findAll", query="SELECT c FROM CsADatiInvalidita c")
public class CsADatiInvalidita implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_A_INVALIDITA_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_A_INVALIDITA_ID_GENERATOR")
	private Long id;

	@Column(name="INVALIDITA_CIVILE_IN_CORSO")
	private String invaliditaCivileInCorso;
	
	@Column(name="INVALIDITA_CIVILE")
	private String invaliditaCivile;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INVALIDITA")
	private Date dataInvalidita;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_SCADENZA_INVALIDITA")
	private Date dataScadenzaInvalidita;
	
	@Column(name="ICD10_ID")
	private BigDecimal icd10d1Id;
	
	@Column(name="ICD9_ID")
	private BigDecimal icd9d1Id;
	
	@Column(name="ICD10_2_ID")
	private BigDecimal icd10d2Id;
	
	@Column(name="ICD9_2_ID")
	private BigDecimal icd9d2Id;
	
	@Column(name="NOTE_SANITARIE")
	private String noteSanitarie;
	
	@Column(name="CERTIFICAZIONE_H")
	private String certificazioneH;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CERTIFICAZIONE")
	private Date dataCertificazione;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_SCADENZA_CERTIFICAZIONE")
	private Date dataScadenzaCertificazione;
	
	@Column(name="PERC_INVALIDITA")
	private BigDecimal percInvalidita;
	
	@Column(name="IN_VALUTAZIONE")
	private String inValutazione;
	
	@Column(name="INDENNITA_FREQUENZA")
	private String indennitaFrequenza;
	
	private String accompagnamento;
	
	private String legge104;
	
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
	
	@Column(name="MOTIVO_FINE")
	private String motivoFine;

	@Column(name="MOTIVO_INIZIO")
	private String motivoInizio;

	//bi-directional many-to-one association to CsASoggetto
	@ManyToOne
	@JoinColumn(name="SOGGETTO_ANAGRAFICA_ID")
	private CsASoggettoLAZY csASoggetto;

	public CsADatiInvalidita() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public CsASoggettoLAZY getCsASoggetto() {
		return this.csASoggetto;
	}

	public void setCsASoggetto(CsASoggettoLAZY csASoggetto) {
		this.csASoggetto = csASoggetto;
	}

	public String getInvaliditaCivileInCorso() {
		return invaliditaCivileInCorso;
	}

	public void setInvaliditaCivileInCorso(String invaliditaCivileInCorso) {
		this.invaliditaCivileInCorso = invaliditaCivileInCorso;
	}

	public String getInvaliditaCivile() {
		return invaliditaCivile;
	}

	public void setInvaliditaCivile(String invaliditaCivile) {
		this.invaliditaCivile = invaliditaCivile;
	}

	public Date getDataInvalidita() {
		return dataInvalidita;
	}

	public void setDataInvalidita(Date dataInvalidita) {
		this.dataInvalidita = dataInvalidita;
	}

	public Date getDataScadenzaInvalidita() {
		return dataScadenzaInvalidita;
	}

	public void setDataScadenzaInvalidita(Date dataScadenzaInvalidita) {
		this.dataScadenzaInvalidita = dataScadenzaInvalidita;
	}

	public BigDecimal getIcd10d1Id() {
		return icd10d1Id;
	}

	public void setIcd10d1Id(BigDecimal icd10d1Id) {
		this.icd10d1Id = icd10d1Id;
	}

	public BigDecimal getIcd9d1Id() {
		return icd9d1Id;
	}

	public void setIcd9d1Id(BigDecimal icd9d1Id) {
		this.icd9d1Id = icd9d1Id;
	}

	public BigDecimal getIcd10d2Id() {
		return icd10d2Id;
	}

	public void setIcd10d2Id(BigDecimal icd10d2Id) {
		this.icd10d2Id = icd10d2Id;
	}

	public BigDecimal getIcd9d2Id() {
		return icd9d2Id;
	}

	public void setIcd9d2Id(BigDecimal icd9d2Id) {
		this.icd9d2Id = icd9d2Id;
	}

	public String getNoteSanitarie() {
		return noteSanitarie;
	}

	public void setNoteSanitarie(String noteSanitarie) {
		this.noteSanitarie = noteSanitarie;
	}

	public String getCertificazioneH() {
		return certificazioneH;
	}

	public void setCertificazioneH(String certificazioneH) {
		this.certificazioneH = certificazioneH;
	}

	public Date getDataCertificazione() {
		return dataCertificazione;
	}

	public void setDataCertificazione(Date dataCertificazione) {
		this.dataCertificazione = dataCertificazione;
	}

	public Date getDataScadenzaCertificazione() {
		return dataScadenzaCertificazione;
	}

	public void setDataScadenzaCertificazione(Date dataScadenzaCertificazione) {
		this.dataScadenzaCertificazione = dataScadenzaCertificazione;
	}

	public BigDecimal getPercInvalidita() {
		return percInvalidita;
	}

	public void setPercInvalidita(BigDecimal percInvalidita) {
		this.percInvalidita = percInvalidita;
	}

	public String getAccompagnamento() {
		return accompagnamento;
	}

	public void setAccompagnamento(String accompagnamento) {
		this.accompagnamento = accompagnamento;
	}

	public String getInValutazione() {
		return inValutazione;
	}

	public void setInValutazione(String inValutazione) {
		this.inValutazione = inValutazione;
	}
	
	public String getIndennitaFrequenza() {
		return indennitaFrequenza;
	}

	public void setIndennitaFrequenza(String indennitaFrequenza) {
		this.indennitaFrequenza = indennitaFrequenza;
	}
	
	public String getLegge104() {
		return legge104;
	}

	public void setLegge104(String legge104) {
		this.legge104 = legge104;
	}

	public Date getDataFineApp() {
		return dataFineApp;
	}

	public void setDataFineApp(Date dataFineApp) {
		this.dataFineApp = dataFineApp;
	}

	public Date getDataFineSys() {
		return dataFineSys;
	}

	public void setDataFineSys(Date dataFineSys) {
		this.dataFineSys = dataFineSys;
	}

	public Date getDataInizioApp() {
		return dataInizioApp;
	}

	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}

	public Date getDataInizioSys() {
		return dataInizioSys;
	}

	public void setDataInizioSys(Date dataInizioSys) {
		this.dataInizioSys = dataInizioSys;
	}

	public String getMotivoFine() {
		return motivoFine;
	}

	public void setMotivoFine(String motivoFine) {
		this.motivoFine = motivoFine;
	}

	public String getMotivoInizio() {
		return motivoInizio;
	}

	public void setMotivoInizio(String motivoInizio) {
		this.motivoInizio = motivoInizio;
	}	
	
}