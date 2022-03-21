package it.webred.cs.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

// <!-- SISO-1305 -->

@Entity
@Table(name="CS_EXTRA_FSE_DATI_LAVORO")
@NamedQuery(name="CsExtraFseDatiLavoro.findAll", query="SELECT c FROM CsExtraFseDatiLavoro c")
public class CsExtraFseDatiLavoro implements Serializable {
	
	private static final long serialVersionUID = -6508827039482051925L;

	@Id
	@SequenceGenerator(name="CS_EXTRA_FSE_DATI_LAVORO_ID_GENERATOR", sequenceName="SQ_EXTRA_FSE",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_EXTRA_FSE_DATI_LAVORO_ID_GENERATOR")
	private Long id;

	@Column(name="ANNO_CONSEGUIMENTO_TITOLO_STU")
	private BigDecimal annoConseguimentoTitoloStu;
	
	@Column(name="FLAG_ALTRO_CORSO")
	private Boolean flagAltroCorso;
	
	@Column(name="DURATA_RIC_LAVORO_ID")
	private String durataRicLavoroId;
	
	@Column(name="DESC_TIPO_LAVORO")
	private String descTipoLavoro;
	
	@Column(name="DESC_ORARIO_LAVORO")
	private String descOrarioLavoro;

	@Column(name="AZ_RAGIONE_SOCIALE")
	private String azRagioneSociale;
	
	@Column(name="AZ_PI")
	private String azPi;

	@Column(name="AZ_CF")
	private String azCf;
	
	@Column(name="AZ_VIA")
	private String azVia;
	
	@Column(name="AZ_COMUNE_COD")
	private String azComuneCod;

	@Column(name="AZ_COMUNE_DES")
	private String azComuneDes;
	
	@Column(name="AZ_PROV")
	private String azProv;

	@Column(name="AZ_COD_ATECO")
	private String azCodAteco;

	@Column(name="AZ_FORMA_GIURIDICA")
	private String azFormaGiuridica;

	@Column(name="DESC_DIM_AZIENDA")
	private String descDimAzienda;

	@Column(name="IBAN")
	private String iban;

	@Column(name="FLAG_RES_DOM")
	private Boolean flagResDom;

	@Column(name="COMUNICA_VUL")
	private Boolean comunicaVul;
	
	@Column(name="USER_INS")
	private String userIns;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Column(name="USER_MOD")
	private String userMod;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_SOTTOSCRIZIONE")
	private Date dtSottoscrizione;
	
	 @Column(name="SOGGETTO_ATTUATORE")
	 private String soggettoAttuatore;

	//Relazioni	
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="PROGETTO_ID")
	private ArFfProgetto progetto;
		
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="PROGETTO_ATTIVITA_ID")
	private ArFfProgettoAttivita progettoAttivita;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name="EXTRA_FSE_MAST_ID")
	private CsExtraFseMast master = new CsExtraFseMast();

	public Boolean getComunicaVul() {
		return comunicaVul;
	}

	public void setComunicaVul(Boolean comunicaVul) {
		this.comunicaVul = comunicaVul;
	}

	public ArFfProgetto getProgetto() {
		return progetto;
	}

	public void setProgetto(ArFfProgetto progetto) {
		this.progetto = progetto;
	}

	public ArFfProgettoAttivita getProgettoAttivita() {
		return progettoAttivita;
	}

	public void setProgettoAttivita(ArFfProgettoAttivita progettoAttivita) {
		this.progettoAttivita = progettoAttivita;
	}


	public CsExtraFseMast getMaster() {
		return master;
	}

	public void setMaster(CsExtraFseMast master) {
		this.master = master;
	}

	public CsExtraFseDatiLavoro() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAnnoConseguimentoTitoloStu() {
		return this.annoConseguimentoTitoloStu;
	}

	public void setAnnoConseguimentoTitoloStu(BigDecimal annoConseguimentoTitoloStu) {
		this.annoConseguimentoTitoloStu = annoConseguimentoTitoloStu;
	}

	public String getAzCf() {
		return this.azCf;
	}

	public void setAzCf(String azCf) {
		this.azCf = azCf;
	}

	public String getAzCodAteco() {
		return this.azCodAteco;
	}

	public void setAzCodAteco(String azCodAteco) {
		this.azCodAteco = azCodAteco;
	}

	public String getAzComuneCod() {
		return this.azComuneCod;
	}

	public void setAzComuneCod(String azComuneCod) {
		this.azComuneCod = azComuneCod;
	}

	public String getAzComuneDes() {
		return this.azComuneDes;
	}

	public void setAzComuneDes(String azComuneDes) {
		this.azComuneDes = azComuneDes;
	}

	public String getAzFormaGiuridica() {
		return this.azFormaGiuridica;
	}

	public void setAzFormaGiuridica(String azFormaGiuridica) {
		this.azFormaGiuridica = azFormaGiuridica;
	}

	public String getAzPi() {
		return this.azPi;
	}

	public void setAzPi(String azPi) {
		this.azPi = azPi;
	}

	public String getAzProv() {
		return this.azProv;
	}

	public void setAzProv(String azProv) {
		this.azProv = azProv;
	}

	public String getAzRagioneSociale() {
		return this.azRagioneSociale;
	}

	public void setAzRagioneSociale(String azRagioneSociale) {
		this.azRagioneSociale = azRagioneSociale;
	}

	public String getAzVia() {
		return this.azVia;
	}

	public void setAzVia(String azVia) {
		this.azVia = azVia;
	}

	public String getDescDimAzienda() {
		return this.descDimAzienda;
	}

	public void setDescDimAzienda(String descDimAzienda) {
		this.descDimAzienda = descDimAzienda;
	}

	public String getDescOrarioLavoro() {
		return this.descOrarioLavoro;
	}

	public void setDescOrarioLavoro(String descOrarioLavoro) {
		this.descOrarioLavoro = descOrarioLavoro;
	}

	public String getDescTipoLavoro() {
		return this.descTipoLavoro;
	}

	public void setDescTipoLavoro(String descTipoLavoro) {
		this.descTipoLavoro = descTipoLavoro;
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

	public String getDurataRicLavoroId() {
		return durataRicLavoroId;
	}

	public void setDurataRicLavoroId(String durataRicLavoroId) {
		this.durataRicLavoroId = durataRicLavoroId;
	}

	public Boolean getFlagAltroCorso() {
		return flagAltroCorso;
	}

	public void setFlagAltroCorso(Boolean flagAltroCorso) {
		this.flagAltroCorso = flagAltroCorso;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public String getUserMod() {
		return this.userMod;
	}

	public void setUserMod(String userMod) {
		this.userMod = userMod;
	}

	public Boolean getFlagResDom() {
		return flagResDom;
	}

	public void setFlagResDom(Boolean flagResDom) {
		this.flagResDom = flagResDom;
	}

	public Date getDtSottoscrizione() {
		return dtSottoscrizione;
	}

	public void setDtSottoscrizione(Date dtSottoscrizione) {
		this.dtSottoscrizione = dtSottoscrizione;
	}

	public String getSoggettoAttuatore() {
		return soggettoAttuatore;
	}

	public void setSoggettoAttuatore(String soggettoAttuatore) {
		this.soggettoAttuatore = soggettoAttuatore;
	}
}