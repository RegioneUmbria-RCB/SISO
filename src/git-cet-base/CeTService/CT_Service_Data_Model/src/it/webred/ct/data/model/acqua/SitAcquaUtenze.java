package it.webred.ct.data.model.acqua;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_ACQUA_UTENZE database table.
 * 
 */
@Entity
@Table(name="SIT_ACQUA_UTENZE")
public class SitAcquaUtenze implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CAP_UBICAZIONE")
	private String capUbicazione;

	@Column(name="CIVICO_UBICAZIONE")
	private String civicoUbicazione;

	@Column(name="COD_SERVIZIO")
	private String codServizio;

	@Column(name="COMUNE_UBICAZIONE")
	private String comuneUbicazione;

	@Column(name="CONSUMO_MEDIO")
	private BigDecimal consumoMedio;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DESCR_CATEGORIA")
	private String descrCategoria;

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

    @Temporal( TemporalType.DATE)
	@Column(name="DT_UTENZA")
	private Date dtUtenza;

	private BigDecimal fatturato;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	private BigDecimal giro;

	@Id
	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_UTENTE")
	private String idExtUtente;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="MESI_FATTURAZIONE")
	private BigDecimal mesiFatturazione;

	private String processid;

	@Column(name="QUALIFICA_TITOLARE")
	private String qualificaTitolare;

	@Column(name="RAG_SOC_UBICAZIONE")
	private String ragSocUbicazione;

	private BigDecimal stacco;

	@Column(name="TIPO_CONTRATTO")
	private String tipoContratto;

	private String tipologia;

	@Column(name="TIPOLOGIA_UI")
	private String tipologiaUi;

	@Column(name="VIA_UBICAZIONE")
	private String viaUbicazione;

    public SitAcquaUtenze() {
    }

	public String getCapUbicazione() {
		return this.capUbicazione;
	}

	public void setCapUbicazione(String capUbicazione) {
		this.capUbicazione = capUbicazione;
	}

	public String getCivicoUbicazione() {
		return this.civicoUbicazione;
	}

	public void setCivicoUbicazione(String civicoUbicazione) {
		this.civicoUbicazione = civicoUbicazione;
	}

	public String getCodServizio() {
		return this.codServizio;
	}

	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}

	public String getComuneUbicazione() {
		return this.comuneUbicazione;
	}

	public void setComuneUbicazione(String comuneUbicazione) {
		this.comuneUbicazione = comuneUbicazione;
	}

	public BigDecimal getConsumoMedio() {
		return this.consumoMedio;
	}

	public void setConsumoMedio(BigDecimal consumoMedio) {
		this.consumoMedio = consumoMedio;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDescrCategoria() {
		return this.descrCategoria;
	}

	public void setDescrCategoria(String descrCategoria) {
		this.descrCategoria = descrCategoria;
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

	public Date getDtUtenza() {
		return this.dtUtenza;
	}

	public void setDtUtenza(Date dtUtenza) {
		this.dtUtenza = dtUtenza;
	}

	public BigDecimal getFatturato() {
		return this.fatturato;
	}

	public void setFatturato(BigDecimal fatturato) {
		this.fatturato = fatturato;
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

	public BigDecimal getGiro() {
		return this.giro;
	}

	public void setGiro(BigDecimal giro) {
		this.giro = giro;
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

	public String getIdExtUtente() {
		return this.idExtUtente;
	}

	public void setIdExtUtente(String idExtUtente) {
		this.idExtUtente = idExtUtente;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public BigDecimal getMesiFatturazione() {
		return this.mesiFatturazione;
	}

	public void setMesiFatturazione(BigDecimal mesiFatturazione) {
		this.mesiFatturazione = mesiFatturazione;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getQualificaTitolare() {
		return this.qualificaTitolare;
	}

	public void setQualificaTitolare(String qualificaTitolare) {
		this.qualificaTitolare = qualificaTitolare;
	}

	public String getRagSocUbicazione() {
		return this.ragSocUbicazione;
	}

	public void setRagSocUbicazione(String ragSocUbicazione) {
		this.ragSocUbicazione = ragSocUbicazione;
	}

	public BigDecimal getStacco() {
		return this.stacco;
	}

	public void setStacco(BigDecimal stacco) {
		this.stacco = stacco;
	}

	public String getTipoContratto() {
		return this.tipoContratto;
	}

	public void setTipoContratto(String tipoContratto) {
		this.tipoContratto = tipoContratto;
	}

	public String getTipologia() {
		return this.tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public String getTipologiaUi() {
		return this.tipologiaUi;
	}

	public void setTipologiaUi(String tipologiaUi) {
		this.tipologiaUi = tipologiaUi;
	}

	public String getViaUbicazione() {
		return this.viaUbicazione;
	}

	public void setViaUbicazione(String viaUbicazione) {
		this.viaUbicazione = viaUbicazione;
	}

}