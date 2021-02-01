package it.webred.ct.data.model.rette;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_RTT_RATE_BOLLETTE database table.
 * 
 */
@Entity
@Table(name="SIT_RTT_RATE_BOLLETTE")
public class SitRttRateBollette implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="COD_BOLLETTA")
	private String codBolletta;

	@Column(name="COD_SERVIZIO")
	private String codServizio;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DES_CANALE")
	private String desCanale;

	@Column(name="DES_DISTINTA")
	private String desDistinta;

	@Column(name="DES_PAGAMENTO")
	private String desPagamento;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_DISTINTA")
	private Date dtDistinta;

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
	@Column(name="DT_PAGAMENTO")
	private Date dtPagamento;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_REG_PAGAMENTO")
	private Date dtRegPagamento;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_SCADENZA_RATA")
	private Date dtScadenzaRata;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Id
	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="ID_PRATICA")
	private String idPratica;

	@Column(name="ID_SERVIZIO")
	private BigDecimal idServizio;

	@Column(name="IMPORTO_PAGATO")
	private BigDecimal importoPagato;

	@Column(name="IMPORTO_RATA")
	private BigDecimal importoRata;

	private String note;

	@Column(name="NUM_RATA")
	private BigDecimal numRata;

	private String processid;

    public SitRttRateBollette() {
    }

	public String getCodBolletta() {
		return this.codBolletta;
	}

	public void setCodBolletta(String codBolletta) {
		this.codBolletta = codBolletta;
	}

	public String getCodServizio() {
		return this.codServizio;
	}

	public void setCodServizio(String codServizio) {
		this.codServizio = codServizio;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDesCanale() {
		return this.desCanale;
	}

	public void setDesCanale(String desCanale) {
		this.desCanale = desCanale;
	}

	public String getDesDistinta() {
		return this.desDistinta;
	}

	public void setDesDistinta(String desDistinta) {
		this.desDistinta = desDistinta;
	}

	public String getDesPagamento() {
		return this.desPagamento;
	}

	public void setDesPagamento(String desPagamento) {
		this.desPagamento = desPagamento;
	}

	public Date getDtDistinta() {
		return this.dtDistinta;
	}

	public void setDtDistinta(Date dtDistinta) {
		this.dtDistinta = dtDistinta;
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

	public Date getDtPagamento() {
		return this.dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public Date getDtRegPagamento() {
		return this.dtRegPagamento;
	}

	public void setDtRegPagamento(Date dtRegPagamento) {
		this.dtRegPagamento = dtRegPagamento;
	}

	public Date getDtScadenzaRata() {
		return this.dtScadenzaRata;
	}

	public void setDtScadenzaRata(Date dtScadenzaRata) {
		this.dtScadenzaRata = dtScadenzaRata;
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

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getIdPratica() {
		return this.idPratica;
	}

	public void setIdPratica(String idPratica) {
		this.idPratica = idPratica;
	}

	public BigDecimal getIdServizio() {
		return this.idServizio;
	}

	public void setIdServizio(BigDecimal idServizio) {
		this.idServizio = idServizio;
	}

	public BigDecimal getImportoPagato() {
		return this.importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public BigDecimal getImportoRata() {
		return this.importoRata;
	}

	public void setImportoRata(BigDecimal importoRata) {
		this.importoRata = importoRata;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public BigDecimal getNumRata() {
		return this.numRata;
	}

	public void setNumRata(BigDecimal numRata) {
		this.numRata = numRata;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

}