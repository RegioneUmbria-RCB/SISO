package it.webred.ct.data.model.rette;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_RTT_BOLLETTE database table.
 * 
 */
@Entity
@Table(name="SIT_RTT_BOLLETTE")
public class SitRttBollette implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal anno;

	@Column(name="ARROTONDAMENTO_ATT")
	private BigDecimal arrotondamentoAtt;

	@Column(name="ARROTONDAMENTO_PREC")
	private BigDecimal arrotondamentoPrec;

	@Column(name="COD_BOLLETTA")
	private String codBolletta;

	@Column(name="COD_SERVIZIO")
	private String codServizio;

	@Column(name="COD_SOGGETTO")
	private String codSoggetto;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name="CTR_HASH")
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_BOLLETTA")
	private Date dataBolletta;

	@Column(name="DES_INTESTATARIO")
	private String desIntestatario;

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

	@Column(name="FL_NON_PAGARE")
	private BigDecimal flNonPagare;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Id
	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="ID_SERVIZIO")
	private BigDecimal idServizio;

	@Column(name="IMPORTO_BOLLETTA_PREC")
	private BigDecimal importoBollettaPrec;

	private String indirizzo;

	@Column(name="MOT_NON_PAGARE")
	private String motNonPagare;

	private String note;

	@Column(name="NUM_BOLLETTA")
	private String numBolletta;

	@Column(name="NUM_RATE")
	private BigDecimal numRate;

	private String oggetto;

	private String processid;

	private String recapito;

	@Column(name="SPESE_SPEDIZIONE")
	private BigDecimal speseSpedizione;

	@Column(name="TOT_BOLLETTA")
	private BigDecimal totBolletta;

	@Column(name="TOT_ESENTE_IVA")
	private BigDecimal totEsenteIva;

	@Column(name="TOT_IMPONIBILE_IVA")
	private BigDecimal totImponibileIva;

	@Column(name="TOT_IVA")
	private BigDecimal totIva;

	@Column(name="TOT_PAGATO")
	private BigDecimal totPagato;

    public SitRttBollette() {
    }

	public BigDecimal getAnno() {
		return this.anno;
	}

	public void setAnno(BigDecimal anno) {
		this.anno = anno;
	}

	public BigDecimal getArrotondamentoAtt() {
		return this.arrotondamentoAtt;
	}

	public void setArrotondamentoAtt(BigDecimal arrotondamentoAtt) {
		this.arrotondamentoAtt = arrotondamentoAtt;
	}

	public BigDecimal getArrotondamentoPrec() {
		return this.arrotondamentoPrec;
	}

	public void setArrotondamentoPrec(BigDecimal arrotondamentoPrec) {
		this.arrotondamentoPrec = arrotondamentoPrec;
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

	public String getCodSoggetto() {
		return this.codSoggetto;
	}

	public void setCodSoggetto(String codSoggetto) {
		this.codSoggetto = codSoggetto;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataBolletta() {
		return this.dataBolletta;
	}

	public void setDataBolletta(Date dataBolletta) {
		this.dataBolletta = dataBolletta;
	}

	public String getDesIntestatario() {
		return this.desIntestatario;
	}

	public void setDesIntestatario(String desIntestatario) {
		this.desIntestatario = desIntestatario;
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

	public BigDecimal getFlNonPagare() {
		return this.flNonPagare;
	}

	public void setFlNonPagare(BigDecimal flNonPagare) {
		this.flNonPagare = flNonPagare;
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

	public BigDecimal getIdServizio() {
		return this.idServizio;
	}

	public void setIdServizio(BigDecimal idServizio) {
		this.idServizio = idServizio;
	}

	public BigDecimal getImportoBollettaPrec() {
		return this.importoBollettaPrec;
	}

	public void setImportoBollettaPrec(BigDecimal importoBollettaPrec) {
		this.importoBollettaPrec = importoBollettaPrec;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getMotNonPagare() {
		return this.motNonPagare;
	}

	public void setMotNonPagare(String motNonPagare) {
		this.motNonPagare = motNonPagare;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumBolletta() {
		return this.numBolletta;
	}

	public void setNumBolletta(String numBolletta) {
		this.numBolletta = numBolletta;
	}

	public BigDecimal getNumRate() {
		return this.numRate;
	}

	public void setNumRate(BigDecimal numRate) {
		this.numRate = numRate;
	}

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getRecapito() {
		return this.recapito;
	}

	public void setRecapito(String recapito) {
		this.recapito = recapito;
	}

	public BigDecimal getSpeseSpedizione() {
		return this.speseSpedizione;
	}

	public void setSpeseSpedizione(BigDecimal speseSpedizione) {
		this.speseSpedizione = speseSpedizione;
	}

	public BigDecimal getTotBolletta() {
		return this.totBolletta;
	}

	public void setTotBolletta(BigDecimal totBolletta) {
		this.totBolletta = totBolletta;
	}

	public BigDecimal getTotEsenteIva() {
		return this.totEsenteIva;
	}

	public void setTotEsenteIva(BigDecimal totEsenteIva) {
		this.totEsenteIva = totEsenteIva;
	}

	public BigDecimal getTotImponibileIva() {
		return this.totImponibileIva;
	}

	public void setTotImponibileIva(BigDecimal totImponibileIva) {
		this.totImponibileIva = totImponibileIva;
	}

	public BigDecimal getTotIva() {
		return this.totIva;
	}

	public void setTotIva(BigDecimal totIva) {
		this.totIva = totIva;
	}

	public BigDecimal getTotPagato() {
		return this.totPagato;
	}

	public void setTotPagato(BigDecimal totPagato) {
		this.totPagato = totPagato;
	}

}