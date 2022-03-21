package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;
 

/**
 * The persistent class for the CS_I_INTERVENTO database table.
 * 
 */
@Entity
@Table(name="CS_I_INTERVENTO")
@NamedQuery(name="CsIIntervento.findAll", query="SELECT c FROM CsIIntervento c")
public class CsIIntervento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_I_INTERVENTO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_I_INTERVENTO_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Temporal(TemporalType.DATE)
	@Column(name="FINE_AL")
	private Date fineAl;

	@Temporal(TemporalType.DATE)
	@Column(name="FINE_DAL")
	private Date fineDal;

	@Column(name="FLAG_PRIMAER_RINNOVO")
	private String flagPrimaerRinnovo;

	@Column(name="FLAG_UNATANTUM")
	private Boolean flagUnatantum;
	
	@Temporal(TemporalType.DATE)
	@Column(name="INIZIO_AL")
	private Date inizioAl;

	@Temporal(TemporalType.DATE)
	@Column(name="INIZIO_DAL")
	private Date inizioDal;
	
	@Column(name="COMP_CITTA")
	private String compCitta;

	@Column(name="COMP_DENOMINAZIONE")
	private String compDenominazione;

	@Column(name="COMP_INDIRIZZO")
	private String compIndirizzo;

	@Column(name="COMP_TEL")
	private String compTel;
	
	private String note;
	
	@Column(name="DIARIO_PAI_ID")
	private Long diarioPaiId;
	
	//bi-directional many-to-one association to CsAComponente
	@Column(name="COMPONENTE_ID")
	private Long componenteId;

	@Column(name="USER_INS")
	private String userIns;

	@Column(name="USR_MOD")
	private String usrMod;

	//bi-directional many-to-one association to CsRelSettCsocTipoInter
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="SCTI_CATEGORIA_SOCIALE_ID", referencedColumnName="SCS_CATEGORIA_SOCIALE_ID"),
		@JoinColumn(name="SCTI_SETTORE_ID", referencedColumnName="SCS_SETTORE_ID"),
		@JoinColumn(name="SCTI_TIPO_INTERVENTO_ID", referencedColumnName="CSTI_TIPO_INTERVENTO_ID")
		})
	private CsRelSettCsocTipoInter csRelSettCsocTipoInter;

	
	//bi-directional many-to-one association to CsFlgIntervento
	@OneToMany(mappedBy="csIIntervento", fetch = FetchType.EAGER)
	@OrderBy("diarioId DESC")
	private Set<CsFlgIntervento> csFlgInterventos;
	
	//bi-directional one-to-one association to CsIBuonoSoc
	@OneToMany(mappedBy="csIIntervento", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsIBuonoSoc> csIBuonoSoc;

	//bi-directional one-to-one association to CsICentrod
	@OneToMany(mappedBy="csIIntervento", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsICentrod> csICentrod;
	
	//bi-directional one-to-one association to CsIContrEco
	@OneToMany(mappedBy="csIIntervento", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsIContrEco> csIContrEco;

	//bi-directional one-to-one association to CsIPasti
	@OneToMany(mappedBy="csIIntervento", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsIPasti> csIPasti;

	//bi-directional one-to-one association to CsIResiMinore
	@OneToMany(mappedBy="csIIntervento",cascade = CascadeType.ALL, fetch=FetchType.LAZY )
	@PrimaryKeyJoinColumn
	private Set<CsIResiMinore> csIResiMinore;

	//bi-directional one-to-one association to CsIVouchersad
	@OneToMany(mappedBy="csIIntervento",cascade = CascadeType.ALL , fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsIVouchersad> csIVouchersad;
	
	//bi-directional one-to-one association to CsIAdmAdh
	@OneToMany(mappedBy="csIIntervento",cascade = CascadeType.ALL , fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsIAdmAdh> csIAdmAdh;

	//bi-directional one-to-one association to CsIAffidoFam
	@OneToMany(mappedBy="csIIntervento",cascade = CascadeType.ALL , fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsIAffidoFam> csIAffidoFam;

	//bi-directional one-to-one association to CsIResiAdulti
	@OneToMany(mappedBy="csIIntervento",cascade = CascadeType.ALL , fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsIResiAdulti> csIResiAdulti;

	//bi-directional one-to-one association to CsISemiResiMin
	@OneToMany(mappedBy="csIIntervento",cascade = CascadeType.ALL , fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsISemiResiMin> csISemiResiMin;
	
	//bi-directional one-to-one association to CsISchedaLavoro
	@OneToMany(mappedBy="csIIntervento",cascade = CascadeType.ALL , fetch=FetchType.LAZY)
	@PrimaryKeyJoinColumn
	private Set<CsISchedaLavoro> csISchedaLavoro;

	@ManyToOne
	@JoinColumn(name = "TIPO_INTERVENTO_CUSTOM_ID")
	private CsCTipoInterventoCustom csIInterventoCustom;
	
	//TODO ML: http://progetti.asc.webred.it/browse/SISO-456
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name ="QUOTA_ID")
	private CsIQuota csIQuota;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name ="INT_PROGETTO_ID")
	private CsIInterventoPr csIInterventoPr;
	
	public CsIIntervento() {
	}

	public Long getId() {
		return id;
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

	public Date getInizioAl() {
		return this.inizioAl;
	}

	public void setInizioAl(Date inizioAl) {
		this.inizioAl = inizioAl;
	}

	public Date getInizioDal() {
		return this.inizioDal;
	}

	public void setInizioDal(Date inizioDal) {
		this.inizioDal = inizioDal;
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

	public Set<CsFlgIntervento> getCsFlgInterventos() {
		return this.csFlgInterventos;
	}

	public void setCsFlgInterventos(Set<CsFlgIntervento> csFlgInterventos) {
		this.csFlgInterventos = csFlgInterventos;
	}

	public CsFlgIntervento addCsFlgIntervento(CsFlgIntervento csFlgIntervento) {
		getCsFlgInterventos().add(csFlgIntervento);
		csFlgIntervento.setCsIIntervento(this);

		return csFlgIntervento;
	}

	public CsFlgIntervento removeCsFlgIntervento(CsFlgIntervento csFlgIntervento) {
		getCsFlgInterventos().remove(csFlgIntervento);
		csFlgIntervento.setCsIIntervento(null);

		return csFlgIntervento;
	}

	public Set<CsIContrEco> getCsIContrEco() {
		return this.csIContrEco;
	}

	public void setCsIContrEco(Set<CsIContrEco> csIContrEco) {
		this.csIContrEco = csIContrEco;
	}

	public Set<CsIPasti> getCsIPasti() {
		return this.csIPasti;
	}

	public void setCsIPasti(Set<CsIPasti> csIPasti) {
		this.csIPasti = csIPasti;
	}

	public Set<CsIResiMinore> getCsIResiMinore() {
		return this.csIResiMinore;
	}

	public void setCsIResiMinore(Set<CsIResiMinore> csIResiMinore) {
		this.csIResiMinore = csIResiMinore;
	}

	public Set<CsIVouchersad> getCsIVouchersad() {
		return this.csIVouchersad;
	}

	public void setCsIVouchersad(Set<CsIVouchersad> csIVouchersad) {
		this.csIVouchersad = csIVouchersad;
	}

	public Set<CsIBuonoSoc> getCsIBuonoSoc() {
		return csIBuonoSoc;
	}

	public void setCsIBuonoSoc(Set<CsIBuonoSoc> csIBuonoSoc) {
		this.csIBuonoSoc = csIBuonoSoc;
	}

	public Set<CsICentrod> getCsICentrod() {
		return csICentrod;
	}

	public void setCsICentrod(Set<CsICentrod> csICentrod) {
		this.csICentrod = csICentrod;
	}

	public Date getFineAl() {
		return fineAl;
	}

	public void setFineAl(Date fineAl) {
		this.fineAl = fineAl;
	}

	public Date getFineDal() {
		return fineDal;
	}

	public void setFineDal(Date fineDal) {
		this.fineDal = fineDal;
	}

	public String getFlagPrimaerRinnovo() {
		return flagPrimaerRinnovo;
	}

	public void setFlagPrimaerRinnovo(String flagPrimaerRinnovo) {
		this.flagPrimaerRinnovo = flagPrimaerRinnovo;
	}

	public CsRelSettCsocTipoInter getCsRelSettCsocTipoInter() {
		return this.csRelSettCsocTipoInter;
	}

	public void setCsRelSettCsocTipoInter(CsRelSettCsocTipoInter csRelSettCsocTipoInter) {
		this.csRelSettCsocTipoInter = csRelSettCsocTipoInter;
	}

	public Boolean getFlagUnatantum() {
		return flagUnatantum;
	}

	public void setFlagUnatantum(Boolean flagUnatantum) {
		this.flagUnatantum = flagUnatantum;
	}

	public CsCTipoInterventoCustom getCsIInterventoCustom() {
		return csIInterventoCustom;
	}

	public Set<CsIAdmAdh> getCsIAdmAdh() {
		return csIAdmAdh;
	}

	public void setCsIAdmAdh(Set<CsIAdmAdh> csIAdmAdh) {
		this.csIAdmAdh = csIAdmAdh;
	}

	public Set<CsIAffidoFam> getCsIAffidoFam() {
		return csIAffidoFam;
	}

	public void setCsIAffidoFam(Set<CsIAffidoFam> csIAffidoFam) {
		this.csIAffidoFam = csIAffidoFam;
	}

	public Set<CsIResiAdulti> getCsIResiAdulti() {
		return csIResiAdulti;
	}

	public void setCsIResiAdulti(Set<CsIResiAdulti> csIResiAdulti) {
		this.csIResiAdulti = csIResiAdulti;
	}

	public Set<CsISemiResiMin> getCsISemiResiMin() {
		return csISemiResiMin;
	}

	public void setCsISemiResiMin(Set<CsISemiResiMin> csISemiResiMin) {
		this.csISemiResiMin = csISemiResiMin;
	}

	public Set<CsISchedaLavoro> getCsISchedaLavoro() {
		return csISchedaLavoro;
	}

	public void setCsISchedaLavoro(Set<CsISchedaLavoro> csISchedaLavoro) {
		this.csISchedaLavoro = csISchedaLavoro;
	}

	public void setCsIInterventoCustom(CsCTipoInterventoCustom csIInterventoCustom) {
		this.csIInterventoCustom = csIInterventoCustom;
	}

	public String getCompCitta() {
		return compCitta;
	}

	public void setCompCitta(String compCitta) {
		this.compCitta = compCitta;
	}

	public String getCompDenominazione() {
		return compDenominazione;
	}

	public void setCompDenominazione(String compDenominazione) {
		this.compDenominazione = compDenominazione;
	}

	public String getCompIndirizzo() {
		return compIndirizzo;
	}

	public void setCompIndirizzo(String compIndirizzo) {
		this.compIndirizzo = compIndirizzo;
	}

	public String getCompTel() {
		return compTel;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}

	public Long getComponenteId() {
		return componenteId;
	}

	public void setComponenteId(Long componenteId) {
		this.componenteId = componenteId;
	}

	//TODO ML:http://progetti.asc.webred.it/browse/SISO-456	
	public CsIQuota getCsIQuota() {
		return csIQuota;
	}

	public Long getDiarioPaiId() {
		return diarioPaiId;
	}

	public void setDiarioPaiId(Long diarioPaiId) {
		this.diarioPaiId = diarioPaiId;
	}

	public void setCsIQuota(CsIQuota csIQuota) {
		this.csIQuota = csIQuota;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public CsIInterventoPr getCsIInterventoPr() {
		return csIInterventoPr;
	}

	public void setCsIInterventoPr(CsIInterventoPr csIInterventoPr) {
		this.csIInterventoPr = csIInterventoPr;
	}

}