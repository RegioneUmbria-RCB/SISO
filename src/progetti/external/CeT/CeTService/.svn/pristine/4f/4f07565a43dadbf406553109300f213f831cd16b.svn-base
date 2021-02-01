package it.webred.ct.data.model.catasto;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_REP_TARSU database table.
 * 
 */
@Entity
@Table(name="SIT_REP_TARSU")
public class SitRepTarsu implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SitRepTarsuPK id;

	@Column(name="ALTRI_RES_CIV")
	private BigDecimal altriResCiv;

	@Column(name="ALTRI_RES_TARSU")
	private BigDecimal altriResTarsu;

	private String categoria;

	private String classe;

	private BigDecimal consistenza;

	private String cuaa;

    @Temporal( TemporalType.DATE)
	@Column(name="D_NASCITA")
	private Date dNascita;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO")
	private Date dataInizio;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

	private String denominazione;

	private String edificio;

	@Column(name="F_ACQ")
	private BigDecimal fAcq;

	@Column(name="F_ACQ_CIV")
	private BigDecimal fAcqCiv;

	@Column(name="F_ACQ_FPS")
	private BigDecimal fAcqFps;

	@Column(name="F_C340")
	private BigDecimal fC340;

	@Column(name="F_ENEL")
	private BigDecimal fEnel;

	@Column(name="F_ENEL_CIV")
	private BigDecimal fEnelCiv;

	@Column(name="F_ENEL_FPS")
	private BigDecimal fEnelFps;

	private String foglio;

	private String interno;

	private BigDecimal locatari;

	@Column(name="LOCATARI_TARSU")
	private BigDecimal locatariTarsu;
	
	@Column(name="LOCATARIO_TARSU")
	private BigDecimal locatarioTarsu;

	private BigDecimal locatore;

	private String particella;

	@Column(name="PERC_POSS")
	private BigDecimal percPoss;

	private String piano;

	@Column(name="PK_CUAA")
	private BigDecimal pkCuaa;	

	@Column(name="PROP_PART")
	private BigDecimal propPart;

	@Column(name="PROP_UIU")
	private BigDecimal propUiu;

	private BigDecimal rendita;

	private BigDecimal res;

	@Column(name="RES_CIV")
	private BigDecimal resCiv;

	private String scala;

	private String sesso;

	private String sub;

	@Column(name="SUP_CAT")
	private BigDecimal supCat;
	
	@Column(name="SUP_TARSU")
	private BigDecimal supTarsu;

	@Column(name="SUP_TARSU_CAT")
	private String supTarsuCat;

	private BigDecimal tarsu;

	@Column(name="TARSU_FAM")
	private BigDecimal tarsuFam;

	@Column(name="TARSU_TIT")
	private BigDecimal tarsuTit;

	@Column(name="TIPO_TITOLO")
	private String tipoTitolo;

	@Column(name="UIU_CIV")
	private BigDecimal uiuCiv;

	private String unimm;

	private String zona;

    public SitRepTarsu() {
    }
    
	public SitRepTarsuPK getId() {
		return id;
	}
	
	public void setId(SitRepTarsuPK id) {
		this.id = id;
	}
	
	public BigDecimal getAltriResCiv() {
		return this.altriResCiv;
	}

	public void setAltriResCiv(BigDecimal altriResCiv) {
		this.altriResCiv = altriResCiv;
	}

	public BigDecimal getAltriResTarsu() {
		return this.altriResTarsu;
	}

	public void setAltriResTarsu(BigDecimal altriResTarsu) {
		this.altriResTarsu = altriResTarsu;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getClasse() {
		return this.classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public BigDecimal getConsistenza() {
		return this.consistenza;
	}

	public void setConsistenza(BigDecimal consistenza) {
		this.consistenza = consistenza;
	}

	public String getCuaa() {
		return this.cuaa;
	}

	public void setCuaa(String cuaa) {
		this.cuaa = cuaa;
	}

	public Date getDNascita() {
		return this.dNascita;
	}

	public void setDNascita(Date dNascita) {
		this.dNascita = dNascita;
	}

	public Date getDataInizio() {
		return this.dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getEdificio() {
		return this.edificio;
	}

	public void setEdificio(String edificio) {
		this.edificio = edificio;
	}

	public BigDecimal getFAcq() {
		return this.fAcq;
	}

	public void setFAcq(BigDecimal fAcq) {
		this.fAcq = fAcq;
	}

	public BigDecimal getFAcqCiv() {
		return this.fAcqCiv;
	}

	public void setFAcqCiv(BigDecimal fAcqCiv) {
		this.fAcqCiv = fAcqCiv;
	}

	public BigDecimal getFAcqFps() {
		return this.fAcqFps;
	}

	public void setFAcqFps(BigDecimal fAcqFps) {
		this.fAcqFps = fAcqFps;
	}

	public BigDecimal getFC340() {
		return this.fC340;
	}

	public void setFC340(BigDecimal fC340) {
		this.fC340 = fC340;
	}

	public BigDecimal getFEnel() {
		return this.fEnel;
	}

	public void setFEnel(BigDecimal fEnel) {
		this.fEnel = fEnel;
	}

	public BigDecimal getFEnelCiv() {
		return this.fEnelCiv;
	}

	public void setFEnelCiv(BigDecimal fEnelCiv) {
		this.fEnelCiv = fEnelCiv;
	}

	public BigDecimal getFEnelFps() {
		return this.fEnelFps;
	}

	public void setFEnelFps(BigDecimal fEnelFps) {
		this.fEnelFps = fEnelFps;
	}

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public BigDecimal getLocatari() {
		return this.locatari;
	}

	public void setLocatari(BigDecimal locatari) {
		this.locatari = locatari;
	}

	public BigDecimal getLocatariTarsu() {
		return this.locatariTarsu;
	}

	public void setLocatariTarsu(BigDecimal locatariTarsu) {
		this.locatariTarsu = locatariTarsu;
	}

	public BigDecimal getLocatarioTarsu() {
		return locatarioTarsu;
	}

	public void setLocatarioTarsu(BigDecimal locatarioTarsu) {
		this.locatarioTarsu = locatarioTarsu;
	}

	public BigDecimal getLocatore() {
		return this.locatore;
	}

	public void setLocatore(BigDecimal locatore) {
		this.locatore = locatore;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public BigDecimal getPercPoss() {
		return this.percPoss;
	}

	public void setPercPoss(BigDecimal percPoss) {
		this.percPoss = percPoss;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public BigDecimal getPkCuaa() {
		return this.pkCuaa;
	}

	public void setPkCuaa(BigDecimal pkCuaa) {
		this.pkCuaa = pkCuaa;
	}

	public BigDecimal getPropPart() {
		return this.propPart;
	}

	public void setPropPart(BigDecimal propPart) {
		this.propPart = propPart;
	}

	public BigDecimal getPropUiu() {
		return this.propUiu;
	}

	public void setPropUiu(BigDecimal propUiu) {
		this.propUiu = propUiu;
	}

	public BigDecimal getRendita() {
		return this.rendita;
	}

	public void setRendita(BigDecimal rendita) {
		this.rendita = rendita;
	}

	public BigDecimal getRes() {
		return this.res;
	}

	public void setRes(BigDecimal res) {
		this.res = res;
	}

	public BigDecimal getResCiv() {
		return this.resCiv;
	}

	public void setResCiv(BigDecimal resCiv) {
		this.resCiv = resCiv;
	}

	public String getScala() {
		return this.scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSub() {
		return this.sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public BigDecimal getSupCat() {
		return this.supCat;
	}

	public void setSupCat(BigDecimal supCat) {
		this.supCat = supCat;
	}

	public BigDecimal getSupTarsu() {
		return this.supTarsu;
	}

	public void setSupTarsu(BigDecimal supTarsu) {
		this.supTarsu = supTarsu;
	}

	public String getSupTarsuCat() {
		return this.supTarsuCat;
	}

	public void setSupTarsuCat(String supTarsuCat) {
		this.supTarsuCat = supTarsuCat;
	}

	public BigDecimal getTarsu() {
		return this.tarsu;
	}

	public void setTarsu(BigDecimal tarsu) {
		this.tarsu = tarsu;
	}

	public BigDecimal getTarsuFam() {
		return this.tarsuFam;
	}

	public void setTarsuFam(BigDecimal tarsuFam) {
		this.tarsuFam = tarsuFam;
	}

	public BigDecimal getTarsuTit() {
		return this.tarsuTit;
	}

	public void setTarsuTit(BigDecimal tarsuTit) {
		this.tarsuTit = tarsuTit;
	}

	public String getTipoTitolo() {
		return this.tipoTitolo;
	}

	public void setTipoTitolo(String tipoTitolo) {
		this.tipoTitolo = tipoTitolo;
	}

	public BigDecimal getUiuCiv() {
		return this.uiuCiv;
	}

	public void setUiuCiv(BigDecimal uiuCiv) {
		this.uiuCiv = uiuCiv;
	}

	public String getUnimm() {
		return this.unimm;
	}

	public void setUnimm(String unimm) {
		this.unimm = unimm;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

}