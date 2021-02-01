package it.webred.ct.data.model.diagnostiche;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_ICI_REPORT_SOGG database table.
 * 
 */
@Entity
@Table(name="DOCFA_ICI_REPORT_SOGG")
public class DocfaIciReportSogg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfaReportSoggPK id;
	
	private String annotazioni;

	@Column(name="CAT_PKID")
	private BigDecimal catPkid;

	@Column(name="COD_ANOMALIE")
	private String codAnomalie;

	@Column(name="CODI_FISC")
	private String codiFisc;

	@Column(name="CODI_PIVA")
	private String codiPiva;

	@Column(name="COMU_NASC")
	private String comuNasc;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_AGGIORNAMENTO")
	private Date dataAggiornamento;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_MORTE")
	private Date dataMorte;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_NASC")
	private Date dataNasc;

	@Column(name="FLG_ANOMALIA")
	private String flgAnomalia;

	@Column(name="FLG_PERS_FISICA")
	private String flgPersFisica;

	@Column(name="FLG_RESID_IND_CAT_3112")
	private String flgResidIndCat3112;

	@Column(name="FLG_RESID_IND_DCF_3112")
	private String flgResidIndDcf3112;

	@Column(name="FLG_TIT_ICI_ANTE")
	private String flgTitIciAnte;

	@Column(name="FLG_TIT_ICI_CIV_ANTE")
	private String flgTitIciCivAnte;

	@Column(name="FLG_TIT_ICI_CIV_POST")
	private String flgTitIciCivPost;

	@Column(name="FLG_TIT_ICI_POST")
	private String flgTitIciPost;

	@Column(name="FLG_TIT_ICI_UIU_ANTE")
	private String flgTitIciUiuAnte;

	@Column(name="FLG_TIT_ICI_UIU_POST")
	private String flgTitIciUiuPost;

	@Column(name="FLG_TITOLARE_3112")
	private String flgTitolare3112;

	@Column(name="FLG_TITOLARE_DATA_DOCFA")
	private String flgTitolareDataDocfa;

	private String nome;

	@Column(name="NUM_FAMILIARI")
	private String numFamiliari;

	@Column(name="PERC_POSS")
	private BigDecimal percPoss;

	@Column(name="RAGI_SOCI")
	private String ragiSoci;

	private String sesso;
	
	private String titolo;

    public DocfaIciReportSogg() {
    }

	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public BigDecimal getCatPkid() {
		return this.catPkid;
	}

	public void setCatPkid(BigDecimal catPkid) {
		this.catPkid = catPkid;
	}

	public String getCodAnomalie() {
		return this.codAnomalie;
	}

	public void setCodAnomalie(String codAnomalie) {
		this.codAnomalie = codAnomalie;
	}

	public String getCodiFisc() {
		return this.codiFisc;
	}

	public void setCodiFisc(String codiFisc) {
		this.codiFisc = codiFisc;
	}

	public String getCodiPiva() {
		return this.codiPiva;
	}

	public void setCodiPiva(String codiPiva) {
		this.codiPiva = codiPiva;
	}

	public String getComuNasc() {
		return this.comuNasc;
	}

	public void setComuNasc(String comuNasc) {
		this.comuNasc = comuNasc;
	}

	public Date getDataAggiornamento() {
		return this.dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}


	public Date getDataMorte() {
		return this.dataMorte;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}

	public Date getDataNasc() {
		return this.dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getFlgAnomalia() {
		return this.flgAnomalia;
	}

	public void setFlgAnomalia(String flgAnomalia) {
		this.flgAnomalia = flgAnomalia;
	}

	public String getFlgPersFisica() {
		return this.flgPersFisica;
	}

	public void setFlgPersFisica(String flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}

	

	public String getFlgTitIciAnte() {
		return this.flgTitIciAnte;
	}

	public void setFlgTitIciAnte(String flgTitIciAnte) {
		this.flgTitIciAnte = flgTitIciAnte;
	}

	public String getFlgTitIciCivAnte() {
		return this.flgTitIciCivAnte;
	}

	public void setFlgTitIciCivAnte(String flgTitIciCivAnte) {
		this.flgTitIciCivAnte = flgTitIciCivAnte;
	}

	public String getFlgTitIciCivPost() {
		return this.flgTitIciCivPost;
	}

	public void setFlgTitIciCivPost(String flgTitIciCivPost) {
		this.flgTitIciCivPost = flgTitIciCivPost;
	}

	public String getFlgTitIciPost() {
		return this.flgTitIciPost;
	}

	public void setFlgTitIciPost(String flgTitIciPost) {
		this.flgTitIciPost = flgTitIciPost;
	}

	public String getFlgTitIciUiuAnte() {
		return this.flgTitIciUiuAnte;
	}

	public void setFlgTitIciUiuAnte(String flgTitIciUiuAnte) {
		this.flgTitIciUiuAnte = flgTitIciUiuAnte;
	}

	public String getFlgTitIciUiuPost() {
		return this.flgTitIciUiuPost;
	}

	public void setFlgTitIciUiuPost(String flgTitIciUiuPost) {
		this.flgTitIciUiuPost = flgTitIciUiuPost;
	}

	public String getFlgTitolare3112() {
		return this.flgTitolare3112;
	}

	public void setFlgTitolare3112(String flgTitolare3112) {
		this.flgTitolare3112 = flgTitolare3112;
	}

	public String getFlgTitolareDataDocfa() {
		return this.flgTitolareDataDocfa;
	}

	public void setFlgTitolareDataDocfa(String flgTitolareDataDocfa) {
		this.flgTitolareDataDocfa = flgTitolareDataDocfa;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumFamiliari() {
		return this.numFamiliari;
	}

	public void setNumFamiliari(String numFamiliari) {
		this.numFamiliari = numFamiliari;
	}

	public BigDecimal getPercPoss() {
		return this.percPoss;
	}

	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}

	public String getRagiSoci() {
		return this.ragiSoci;
	}

	public void setRagiSoci(String ragiSoci) {
		this.ragiSoci = ragiSoci;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public DocfaReportSoggPK getId() {
		return id;
	}

	public void setId(DocfaReportSoggPK id) {
		this.id = id;
	}

	public String getFlgResidIndCat3112() {
		return flgResidIndCat3112;
	}

	public void setFlgResidIndCat3112(String flgResidIndCat3112) {
		this.flgResidIndCat3112 = flgResidIndCat3112;
	}

	public String getFlgResidIndDcf3112() {
		return flgResidIndDcf3112;
	}

	public void setFlgResidIndDcf3112(String flgResidIndDcf3112) {
		this.flgResidIndDcf3112 = flgResidIndDcf3112;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getTitolo() {
		return titolo;
	}

}