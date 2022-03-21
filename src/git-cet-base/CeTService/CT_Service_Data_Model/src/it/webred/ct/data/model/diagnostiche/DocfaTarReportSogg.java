package it.webred.ct.data.model.diagnostiche;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DOCFA_TAR_REPORT_SOGG database table.
 * 
 */
@Entity
@Table(name="DOCFA_TAR_REPORT_SOGG")
public class DocfaTarReportSogg implements Serializable {
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

	@Column(name="FLG_FAM_TAR_ANTE")
	private String flgFamTarAnte;

	@Column(name="FLG_FAM_TAR_CIV_ANTE")
	private String flgFamTarCivAnte;

	@Column(name="FLG_FAM_TAR_CIV_POST")
	private String flgFamTarCivPost;

	@Column(name="FLG_FAM_TAR_POST")
	private String flgFamTarPost;

	@Column(name="FLG_FAM_TAR_UIU_ANTE")
	private String flgFamTarUiuAnte;

	@Column(name="FLG_FAM_TAR_UIU_POST")
	private String flgFamTarUiuPost;

	@Column(name="FLG_PERS_FISICA")
	private String flgPersFisica;

	@Column(name="FLG_RESID_IND_CAT_3112")
	private String flgResidIndCat3112;

	@Column(name="FLG_RESID_IND_DCF_3112")
	private String flgResidIndDcf3112;

	@Column(name="FLG_TIT_TAR_ANTE")
	private String flgTitTarAnte;

	@Column(name="FLG_TIT_TAR_CIV_ANTE")
	private String flgTitTarCivAnte;

	@Column(name="FLG_TIT_TAR_CIV_POST")
	private String flgTitTarCivPost;

	@Column(name="FLG_TIT_TAR_POST")
	private String flgTitTarPost;

	@Column(name="FLG_TIT_TAR_UIU_ANTE")
	private String flgTitTarUiuAnte;

	@Column(name="FLG_TIT_TAR_UIU_POST")
	private String flgTitTarUiuPost;

	@Column(name="FLG_TITOLARE_3112")
	private String flgTitolare3112;

	@Column(name="FLG_TITOLARE_DATA_DOCFA")
	private String flgTitolareDataDocfa;

	private String nome;

	@Column(name="NUM_FAMILIARI")
	private BigDecimal numFamiliari;

	@Column(name="PERC_POSS")
	private BigDecimal percPoss;

	@Column(name="RAGI_SOCI")
	private String ragiSoci;

	private String sesso;
	
	private String titolo;
	
	public DocfaTarReportSogg() {}

	public DocfaReportSoggPK getId() {
		return id;
	}

	public void setId(DocfaReportSoggPK id) {
		this.id = id;
	}

	public String getAnnotazioni() {
		return annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public BigDecimal getCatPkid() {
		return catPkid;
	}

	public void setCatPkid(BigDecimal catPkid) {
		this.catPkid = catPkid;
	}

	public String getCodAnomalie() {
		return codAnomalie;
	}

	public void setCodAnomalie(String codAnomalie) {
		this.codAnomalie = codAnomalie;
	}

	public String getCodiFisc() {
		return codiFisc;
	}

	public void setCodiFisc(String codiFisc) {
		this.codiFisc = codiFisc;
	}

	public String getCodiPiva() {
		return codiPiva;
	}

	public void setCodiPiva(String codiPiva) {
		this.codiPiva = codiPiva;
	}

	public String getComuNasc() {
		return comuNasc;
	}

	public void setComuNasc(String comuNasc) {
		this.comuNasc = comuNasc;
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

	public Date getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Date dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Date getDataMorte() {
		return dataMorte;
	}

	public void setDataMorte(Date dataMorte) {
		this.dataMorte = dataMorte;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getFlgAnomalia() {
		return flgAnomalia;
	}

	public void setFlgAnomalia(String flgAnomalia) {
		this.flgAnomalia = flgAnomalia;
	}

	public String getFlgFamTarAnte() {
		return flgFamTarAnte;
	}

	public void setFlgFamTarAnte(String flgFamTarAnte) {
		this.flgFamTarAnte = flgFamTarAnte;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getFlgFamTarCivAnte() {
		return flgFamTarCivAnte;
	}

	public void setFlgFamTarCivAnte(String flgFamTarCivAnte) {
		this.flgFamTarCivAnte = flgFamTarCivAnte;
	}

	public String getFlgFamTarCivPost() {
		return flgFamTarCivPost;
	}

	public void setFlgFamTarCivPost(String flgFamTarCivPost) {
		this.flgFamTarCivPost = flgFamTarCivPost;
	}

	public String getFlgFamTarPost() {
		return flgFamTarPost;
	}

	public void setFlgFamTarPost(String flgFamTarPost) {
		this.flgFamTarPost = flgFamTarPost;
	}

	public String getFlgFamTarUiuAnte() {
		return flgFamTarUiuAnte;
	}

	public void setFlgFamTarUiuAnte(String flgFamTarUiuAnte) {
		this.flgFamTarUiuAnte = flgFamTarUiuAnte;
	}

	public String getFlgFamTarUiuPost() {
		return flgFamTarUiuPost;
	}

	public void setFlgFamTarUiuPost(String flgFamTarUiuPost) {
		this.flgFamTarUiuPost = flgFamTarUiuPost;
	}

	public String getFlgPersFisica() {
		return flgPersFisica;
	}

	public void setFlgPersFisica(String flgPersFisica) {
		this.flgPersFisica = flgPersFisica;
	}

	public String getFlgTitTarAnte() {
		return flgTitTarAnte;
	}

	public void setFlgTitTarAnte(String flgTitTarAnte) {
		this.flgTitTarAnte = flgTitTarAnte;
	}

	public String getFlgTitTarCivAnte() {
		return flgTitTarCivAnte;
	}

	public void setFlgTitTarCivAnte(String flgTitTarCivAnte) {
		this.flgTitTarCivAnte = flgTitTarCivAnte;
	}

	public String getFlgTitTarCivPost() {
		return flgTitTarCivPost;
	}

	public void setFlgTitTarCivPost(String flgTitTarCivPost) {
		this.flgTitTarCivPost = flgTitTarCivPost;
	}

	public String getFlgTitTarPost() {
		return flgTitTarPost;
	}

	public void setFlgTitTarPost(String flgTitTarPost) {
		this.flgTitTarPost = flgTitTarPost;
	}

	public String getFlgTitTarUiuAnte() {
		return flgTitTarUiuAnte;
	}

	public void setFlgTitTarUiuAnte(String flgTitTarUiuAnte) {
		this.flgTitTarUiuAnte = flgTitTarUiuAnte;
	}

	public String getFlgTitTarUiuPost() {
		return flgTitTarUiuPost;
	}

	public void setFlgTitTarUiuPost(String flgTitTarUiuPost) {
		this.flgTitTarUiuPost = flgTitTarUiuPost;
	}

	public String getFlgTitolare3112() {
		return flgTitolare3112;
	}

	public void setFlgTitolare3112(String flgTitolare3112) {
		this.flgTitolare3112 = flgTitolare3112;
	}

	public String getFlgTitolareDataDocfa() {
		return flgTitolareDataDocfa;
	}

	public void setFlgTitolareDataDocfa(String flgTitolareDataDocfa) {
		this.flgTitolareDataDocfa = flgTitolareDataDocfa;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getNumFamiliari() {
		return numFamiliari;
	}

	public void setNumFamiliari(BigDecimal numFamiliari) {
		this.numFamiliari = numFamiliari;
	}

	public BigDecimal getPercPoss() {
		return percPoss;
	}

	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}

	public String getRagiSoci() {
		return ragiSoci;
	}

	public void setRagiSoci(String ragiSoci) {
		this.ragiSoci = ragiSoci;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
}