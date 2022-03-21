package it.webred.ct.data.model.licenzecommercio;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_LICENZE_COMMERCIO database table.
 * 
 */
@Entity
@Table(name="SIT_LICENZE_COMMERCIO")
@IndiceEntity(sorgente="13")
public class SitLicenzeCommercio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;

	@Column(name="ANNO_PROTOCOLLO")
	private String annoProtocollo;

	private String carattere;

	private String civico;

	private String civico2;

	private String civico3;

	@Column(name="CODICE_FABBRICATO")
	private String codiceFabbricato;

	@Column(name="CTR_HASH")
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_SOSPENSIONE")
	private Date dataFineSospensione;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_SOSPENSIONE")
	private Date dataInizioSospensione;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_RILASCIO")
	private Date dataRilascio;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_VALIDITA")
	private Date dataValidita;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

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

	private String foglio;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_VIE")
	private String idExtVie;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String note;

	private String numero;

	@Column(name="NUMERO_PROTOCOLLO")
	private String numeroProtocollo;

	private String particella;

	private String processid;

	private String provenienza;

	@Column(name="RAG_SOC")
	private String ragSoc;

	@Column(name="RIFERIM_ATTO")
	private String riferimAtto;

	@Column(name="SEZIONE_CATASTALE")
	private String sezioneCatastale;

	private String stato;

	private String subalterno;

	@Column(name="SUPERFICIE_MINUTO")
	private BigDecimal superficieMinuto;

	@Column(name="SUPERFICIE_TOTALE")
	private BigDecimal superficieTotale;

	private String tipologia;

	private String zona;

    public SitLicenzeCommercio() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnnoProtocollo() {
		return this.annoProtocollo;
	}

	public void setAnnoProtocollo(String annoProtocollo) {
		this.annoProtocollo = annoProtocollo;
	}

	public String getCarattere() {
		return this.carattere;
	}

	public void setCarattere(String carattere) {
		this.carattere = carattere;
	}

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCivico2() {
		return this.civico2;
	}

	public void setCivico2(String civico2) {
		this.civico2 = civico2;
	}

	public String getCivico3() {
		return this.civico3;
	}

	public void setCivico3(String civico3) {
		this.civico3 = civico3;
	}

	public String getCodiceFabbricato() {
		return this.codiceFabbricato;
	}

	public void setCodiceFabbricato(String codiceFabbricato) {
		this.codiceFabbricato = codiceFabbricato;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataFineSospensione() {
		return this.dataFineSospensione;
	}

	public void setDataFineSospensione(Date dataFineSospensione) {
		this.dataFineSospensione = dataFineSospensione;
	}

	public Date getDataInizioSospensione() {
		return this.dataInizioSospensione;
	}

	public void setDataInizioSospensione(Date dataInizioSospensione) {
		this.dataInizioSospensione = dataInizioSospensione;
	}

	public Date getDataRilascio() {
		return this.dataRilascio;
	}

	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
	}

	public Date getDataValidita() {
		return this.dataValidita;
	}

	public void setDataValidita(Date dataValidita) {
		this.dataValidita = dataValidita;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
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

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getIdExt() {
		return this.idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdExtVie() {
		return this.idExtVie;
	}

	public void setIdExtVie(String idExtVie) {
		this.idExtVie = idExtVie;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
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

	public String getRagSoc() {
		return this.ragSoc;
	}

	public void setRagSoc(String ragSoc) {
		this.ragSoc = ragSoc;
	}

	public String getRiferimAtto() {
		return this.riferimAtto;
	}

	public void setRiferimAtto(String riferimAtto) {
		this.riferimAtto = riferimAtto;
	}

	public String getSezioneCatastale() {
		return this.sezioneCatastale;
	}

	public void setSezioneCatastale(String sezioneCatastale) {
		this.sezioneCatastale = sezioneCatastale;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public BigDecimal getSuperficieMinuto() {
		return this.superficieMinuto;
	}

	public void setSuperficieMinuto(BigDecimal superficieMinuto) {
		this.superficieMinuto = superficieMinuto;
	}

	public BigDecimal getSuperficieTotale() {
		return this.superficieTotale;
	}

	public void setSuperficieTotale(BigDecimal superficieTotale) {
		this.superficieTotale = superficieTotale;
	}

	public String getTipologia() {
		return this.tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

}