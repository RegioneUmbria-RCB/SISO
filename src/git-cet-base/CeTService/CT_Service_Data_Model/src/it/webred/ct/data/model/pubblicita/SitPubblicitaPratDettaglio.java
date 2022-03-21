package it.webred.ct.data.model.pubblicita;

import it.webred.ct.data.model.annotation.IndiceEntity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_PUBBLICITA_PRAT_DETTAGLIO database table.
 * 
 */
@Entity
@Table(name="SIT_PUB_PRAT_DETTAGLIO")
@IndiceEntity(sorgente="27")
public class SitPubblicitaPratDettaglio implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal altezza;

	@Column(name="ANNO_PRATICA")
	private String annoPratica;

	private String annotazioni;

	private BigDecimal base;

	private String civico;

	@Column(name="COD_CARATTERISTICA")
	private String codCaratteristica;

	@Column(name="COD_CLS")
	private String codCls;

	@Column(name="COD_OGGETTO")
	private String codOggetto;

	@Column(name="COD_ZONA_CESPITE")
	private String codZonaCespite;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DESC_CARATTERISTICA")
	private String descCaratteristica;

	@Column(name="DESC_CLS")
	private String descCls;

	@Column(name="DESC_OGGETTO")
	private String descOggetto;

	@Column(name="DESC_ZONA_CESPITE")
	private String descZonaCespite;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE")
	private Date dtFine;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO")
	private Date dtInizio;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO")
	private Date dtInizioDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="GIORNI_OCCUPAZIONE")
	private BigDecimal giorniOccupazione;

	@Id
	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_TESTATA")
	private String idExtTestata;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String indirizzo;

	@Column(name="NUM_ESEMPLARI")
	private BigDecimal numEsemplari;

	@Column(name="NUM_FACCE")
	private BigDecimal numFacce;

	@Column(name="NUM_PRATICA")
	private BigDecimal numPratica;

	private String processid;

	private String provenienza;

	@Column(name="SUP_ESISTENTE")
	private BigDecimal supEsistente;

	@Column(name="SUP_IMPONIBILE")
	private BigDecimal supImponibile;

	@Column(name="TIPO_PRATICA")
	private String tipoPratica;

	private String via;

    public SitPubblicitaPratDettaglio() {
    }

	public BigDecimal getAltezza() {
		return this.altezza;
	}

	public void setAltezza(BigDecimal altezza) {
		this.altezza = altezza;
	}

	public String getAnnoPratica() {
		return this.annoPratica;
	}

	public void setAnnoPratica(String annoPratica) {
		this.annoPratica = annoPratica;
	}

	public String getAnnotazioni() {
		return this.annotazioni;
	}

	public void setAnnotazioni(String annotazioni) {
		this.annotazioni = annotazioni;
	}

	public BigDecimal getBase() {
		return this.base;
	}

	public void setBase(BigDecimal base) {
		this.base = base;
	}

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodCaratteristica() {
		return this.codCaratteristica;
	}

	public void setCodCaratteristica(String codCaratteristica) {
		this.codCaratteristica = codCaratteristica;
	}

	public String getCodCls() {
		return this.codCls;
	}

	public void setCodCls(String codCls) {
		this.codCls = codCls;
	}

	public String getCodOggetto() {
		return this.codOggetto;
	}

	public void setCodOggetto(String codOggetto) {
		this.codOggetto = codOggetto;
	}

	public String getCodZonaCespite() {
		return this.codZonaCespite;
	}

	public void setCodZonaCespite(String codZonaCespite) {
		this.codZonaCespite = codZonaCespite;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDescCaratteristica() {
		return this.descCaratteristica;
	}

	public void setDescCaratteristica(String descCaratteristica) {
		this.descCaratteristica = descCaratteristica;
	}

	public String getDescCls() {
		return this.descCls;
	}

	public void setDescCls(String descCls) {
		this.descCls = descCls;
	}

	public String getDescOggetto() {
		return this.descOggetto;
	}

	public void setDescOggetto(String descOggetto) {
		this.descOggetto = descOggetto;
	}

	public String getDescZonaCespite() {
		return this.descZonaCespite;
	}

	public void setDescZonaCespite(String descZonaCespite) {
		this.descZonaCespite = descZonaCespite;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public Date getDtFine() {
		return this.dtFine;
	}

	public void setDtFine(Date dtFine) {
		this.dtFine = dtFine;
	}

	public Date getDtFineDato() {
		return this.dtFineDato;
	}

	public void setDtFineDato(Date dtFineDato) {
		this.dtFineDato = dtFineDato;
	}

	public Date getDtFineVal() {
		return this.dtFineVal;
	}

	public void setDtFineVal(Date dtFineVal) {
		this.dtFineVal = dtFineVal;
	}

	public Date getDtInizio() {
		return this.dtInizio;
	}

	public void setDtInizio(Date dtInizio) {
		this.dtInizio = dtInizio;
	}

	public Date getDtInizioDato() {
		return this.dtInizioDato;
	}

	public void setDtInizioDato(Date dtInizioDato) {
		this.dtInizioDato = dtInizioDato;
	}

	public Date getDtInizioVal() {
		return this.dtInizioVal;
	}

	public void setDtInizioVal(Date dtInizioVal) {
		this.dtInizioVal = dtInizioVal;
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

	public BigDecimal getFkEnteSorgente() {
		return this.fkEnteSorgente;
	}

	public void setFkEnteSorgente(BigDecimal fkEnteSorgente) {
		this.fkEnteSorgente = fkEnteSorgente;
	}

	public BigDecimal getFlagDtValDato() {
		return this.flagDtValDato;
	}

	public void setFlagDtValDato(BigDecimal flagDtValDato) {
		this.flagDtValDato = flagDtValDato;
	}

	public BigDecimal getGiorniOccupazione() {
		return this.giorniOccupazione;
	}

	public void setGiorniOccupazione(BigDecimal giorniOccupazione) {
		this.giorniOccupazione = giorniOccupazione;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExt() {
		return this.idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdExtTestata() {
		return this.idExtTestata;
	}

	public void setIdExtTestata(String idExtTestata) {
		this.idExtTestata = idExtTestata;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public BigDecimal getNumEsemplari() {
		return this.numEsemplari;
	}

	public void setNumEsemplari(BigDecimal numEsemplari) {
		this.numEsemplari = numEsemplari;
	}

	public BigDecimal getNumFacce() {
		return this.numFacce;
	}

	public void setNumFacce(BigDecimal numFacce) {
		this.numFacce = numFacce;
	}

	public BigDecimal getNumPratica() {
		return this.numPratica;
	}

	public void setNumPratica(BigDecimal numPratica) {
		this.numPratica = numPratica;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProvenienza() {
		return this.provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public BigDecimal getSupEsistente() {
		return this.supEsistente;
	}

	public void setSupEsistente(BigDecimal supEsistente) {
		this.supEsistente = supEsistente;
	}

	public BigDecimal getSupImponibile() {
		return this.supImponibile;
	}

	public void setSupImponibile(BigDecimal supImponibile) {
		this.supImponibile = supImponibile;
	}

	public String getTipoPratica() {
		return this.tipoPratica;
	}

	public void setTipoPratica(String tipoPratica) {
		this.tipoPratica = tipoPratica;
	}

	public String getVia() {
		return this.via;
	}

	public void setVia(String via) {
		this.via = via;
	}

}