package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CS_I_CENTROD database table.
 * 
 */
@Entity
@Table(name="CS_I_INTERVENTO_PR_FSE")
public class CsIInterventoPrFse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long interventoPrId;

	//bi-directional one-to-one association to CsIIntervento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INTERVENTO_PR_ID")
	@MapsId
	private CsIInterventoPr csIInterventoPr;
	
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
	
	@Column(name="FLAG_ALTRO_CORSO")
	private Boolean flagAltroCorso;
	
	/* 1 o 3 di ING_MERCATO*/
	@Column(name="DURATA_RIC_LAVORO_ID")
	private String durataRicLavoroId;
	
	/* 2 di ING_MERCATO*/
	@Column(name="DESC_TIPO_LAVORO")
	private String lavoroDescTipo;
	
	@Column(name="DESC_ORARIO_LAVORO")
	private String lavoroDescOrario;
	
	@Column(name="AZ_RAGIONE_SOCIALE")
	private String azRagioneSociale;
	
	@Column(name="AZ_PI")
	private String azPIVA;
	
	@Column(name="AZ_CF")
	private String azCF;
	
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
	
	@ManyToOne
	@JoinColumn(name="AZ_FORMA_GIURIDICA")
	private CsTbFormaGiuridica azFormaGiuridica;
	
	@Column(name="DESC_DIM_AZIENDA")
	private String azDescDimensioni;
	
	
	/*DATI TITOLO STUDIO*/
	@Column(name="ANNO_CONSEGUIMENTO_TITOLO_STU")
	private BigDecimal titoloStudioAnno;
	
	
	@ManyToOne
	@JoinColumn(name="GR_VULNERABILE_ID")
	private CsTbGVulnerabile csTbGrVulnerabile;
	
	 private String iban;
	 
	 @Column(name="COMUNE_DOMICILIO")
	 private String comuneDomicilio; //JSON
	 
	 @Column(name="VIA_DOMICILIO")
	 private String viaDomicilio;
	
	 @Column(name="FLAG_RES_DOM")
	 private String flagResDom;
	 
	 private String cellulare;
	 private String telefono;
	 private String email;
	
		
	 //FINE SISO - 945
	 //SISO-1010
	 @Column(name="COMUNICA_VUL")
	 private Boolean comunicaVul;
	 
	 @Temporal(TemporalType.DATE)
	 @Column(name="DT_SOTTOSCRIZIONE")
	 private Date dtSottoscrizione;
	 
	 @Column(name="SOGGETTO_ATTUATORE")
	 private String soggettoAttuatore;
 
	public Boolean getComunicaVul() {
		return comunicaVul;
	}


	public void setComunicaVul(Boolean comunicaVul) {
		this.comunicaVul = comunicaVul;
	}

	public long getInterventoPrId() {
		return interventoPrId;
	}


	public void setInterventoPrId(long interventoPrId) {
		this.interventoPrId = interventoPrId;
	}


	public CsIInterventoPr getCsIInterventoPr() {
		return csIInterventoPr;
	}

	public void setCsIInterventoPr(CsIInterventoPr csIInterventoPr) {
		this.csIInterventoPr = csIInterventoPr;
	}


	public Date getDtIns() {
		return dtIns;
	}


	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}


	public Date getDtMod() {
		return dtMod;
	}


	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}


	public String getUserIns() {
		return userIns;
	}


	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}


	public String getUsrMod() {
		return usrMod;
	}


	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	public Boolean getFlagAltroCorso() {
		return flagAltroCorso;
	}


	public void setFlagAltroCorso(Boolean flagAltroCorso) {
		this.flagAltroCorso = flagAltroCorso;
	}


	public String getDurataRicLavoroId() {
		return durataRicLavoroId;
	}


	public void setDurataRicLavoroId(String durataRicLavoroId) {
		this.durataRicLavoroId = durataRicLavoroId;
	}


	public String getLavoroDescTipo() {
		return lavoroDescTipo;
	}


	public void setLavoroDescTipo(String lavoroDescTipo) {
		this.lavoroDescTipo = lavoroDescTipo;
	}


	public String getLavoroDescOrario() {
		return lavoroDescOrario;
	}


	public void setLavoroDescOrario(String lavoroDescOrario) {
		this.lavoroDescOrario = lavoroDescOrario;
	}


	public String getAzRagioneSociale() {
		return azRagioneSociale;
	}


	public void setAzRagioneSociale(String azRagioneSociale) {
		this.azRagioneSociale = azRagioneSociale;
	}


	public String getAzPIVA() {
		return azPIVA;
	}


	public void setAzPIVA(String azPIVA) {
		this.azPIVA = azPIVA;
	}


	public String getAzCF() {
		return azCF;
	}


	public void setAzCF(String azCF) {
		this.azCF = azCF;
	}


	public String getAzVia() {
		return azVia;
	}


	public void setAzVia(String azVia) {
		this.azVia = azVia;
	}


	public String getAzComuneCod() {
		return azComuneCod;
	}


	public void setAzComuneCod(String azComuneCod) {
		this.azComuneCod = azComuneCod;
	}


	public String getAzComuneDes() {
		return azComuneDes;
	}


	public void setAzComuneDes(String azComuneDes) {
		this.azComuneDes = azComuneDes;
	}


	public String getAzProv() {
		return azProv;
	}


	public void setAzProv(String azProv) {
		this.azProv = azProv;
	}


	public String getAzCodAteco() {
		return azCodAteco;
	}


	public void setAzCodAteco(String azCodAteco) {
		this.azCodAteco = azCodAteco;
	}


	public CsTbFormaGiuridica getAzFormaGiuridica() {
		return azFormaGiuridica;
	}


	public void setAzFormaGiuridica(CsTbFormaGiuridica azFormaGiuridica) {
		this.azFormaGiuridica = azFormaGiuridica;
	}


	public String getAzDescDimensioni() {
		return azDescDimensioni;
	}


	public void setAzDescDimensioni(String azDescDimensioni) {
		this.azDescDimensioni = azDescDimensioni;
	}


	public BigDecimal getTitoloStudioAnno() {
		return titoloStudioAnno;
	}


	public void setTitoloStudioAnno(BigDecimal titoloStudioAnno) {
		this.titoloStudioAnno = titoloStudioAnno;
	}

	public CsTbGVulnerabile getCsTbGrVulnerabile() {
		return csTbGrVulnerabile;
	}


	public void setCsTbGrVulnerabile(CsTbGVulnerabile csTbGrVulnerabile) {
		this.csTbGrVulnerabile = csTbGrVulnerabile;
	}


	public String getIban() {
		return iban;
	}


	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getComuneDomicilio() {
		return comuneDomicilio;
	}


	public void setComuneDomicilio(String comuneDomicilio) {
		this.comuneDomicilio = comuneDomicilio;
	}

	public String getViaDomicilio() {
		return viaDomicilio;
	}


	public void setViaDomicilio(String viaDomicilio) {
		this.viaDomicilio = viaDomicilio;
	}


	public String getFlagResDom() {
		return flagResDom;
	}


	public void setFlagResDom(String flagResDom) {
		this.flagResDom = flagResDom;
	}


	public String getCellulare() {
		return cellulare;
	}


	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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