package it.webred.ct.data.model.f24;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="SIT_T_F24_VERSAMENTI")
public class SitTF24Versamenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private BigDecimal acconto;

	@Column(name="ANNO_RIF")
	private BigDecimal annoRif;

	private String cab;

	private String cf;

	private String cf2;

	@Column(name="COD_ENTE_COM")
	private String codEnteCom;

	@Column(name="COD_ENTE_RD")
	private String codEnteRd;

	@Column(name="COD_ID_CF2")
	private String codIdCf2;

	@Column(name="COD_TRIBUTO")
	private String codTributo;

	@Column(name="COD_VALUTA")
	private String codValuta;

	@Column(name="COMUNE_STATO")
	private String comuneStato;

	@Column(name="CTR_HASH")
	private String ctrHash;

	private String denominazione;

	private BigDecimal detrazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_BONIFICO")
	private Date dtBonifico;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_FORNITURA")
	private Date dtFornitura;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_DATO")
	private Date dtInizioDato;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INIZIO_VAL")
	private Date dtInizioVal;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_INS")
	private Date dtIns;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

	@Column(name="DT_NASCITA")
	private String dtNascita;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_RIPARTIZIONE")
	private Date dtRipartizione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_RISCOSSIONE")
	private Date dtRiscossione;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="FLAG_ERR_AR")
	private BigDecimal flagErrAr;

	@Column(name="FLAG_ERR_CF")
	private BigDecimal flagErrCf;

	@Column(name="FLAG_ERR_CT")
	private BigDecimal flagErrCt;

	@Column(name="FLAG_ERR_ICI_IMU")
	private BigDecimal flagErrIciImu;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_TESTATA")
	private String idExtTestata;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IMP_CREDITO")
	private BigDecimal impCredito;

	@Column(name="IMP_DEBITO")
	private BigDecimal impDebito;

	private String nome;

	@Column(name="NUM_FABB_ICI_IMU")
	private BigDecimal numFabbIciImu;

	private String processid;

	@Column(name="PROG_DELEGA")
	private BigDecimal progDelega;

	@Column(name="PROG_FORNITURA")
	private BigDecimal progFornitura;

	@Column(name="PROG_RIGA")
	private BigDecimal progRiga;

	@Column(name="PROG_RIPARTIZIONE")
	private BigDecimal progRipartizione;

	private String provincia;

	private BigDecimal rateazione;

	private BigDecimal ravvedimento;

	private BigDecimal saldo;

	private String sesso;

	@Column(name="TIPO_ENTE_RD")
	private String tipoEnteRd;

	@Column(name="TIPO_IMPOSTA")
	private String tipoImposta;

	@Column(name="VAR_IMM_ICI_IMU")
	private BigDecimal varImmIciImu;

	public SitTF24Versamenti() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAcconto() {
		return this.acconto;
	}

	public void setAcconto(BigDecimal acconto) {
		this.acconto = acconto;
	}

	public BigDecimal getAnnoRif() {
		return this.annoRif;
	}

	public void setAnnoRif(BigDecimal annoRif) {
		this.annoRif = annoRif;
	}

	public String getCab() {
		return this.cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}

	public String getCf() {
		return this.cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getCf2() {
		return this.cf2;
	}

	public void setCf2(String cf2) {
		this.cf2 = cf2;
	}

	public String getCodEnteCom() {
		return this.codEnteCom;
	}

	public void setCodEnteCom(String codEnteCom) {
		this.codEnteCom = codEnteCom;
	}

	public String getCodEnteRd() {
		return this.codEnteRd;
	}

	public void setCodEnteRd(String codEnteRd) {
		this.codEnteRd = codEnteRd;
	}

	public String getCodIdCf2() {
		return this.codIdCf2;
	}

	public void setCodIdCf2(String codIdCf2) {
		this.codIdCf2 = codIdCf2;
	}

	public String getCodTributo() {
		return this.codTributo;
	}

	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}

	public String getCodValuta() {
		return this.codValuta;
	}

	public void setCodValuta(String codValuta) {
		this.codValuta = codValuta;
	}

	public String getComuneStato() {
		return this.comuneStato;
	}

	public void setComuneStato(String comuneStato) {
		this.comuneStato = comuneStato;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public BigDecimal getDetrazione() {
		return this.detrazione;
	}

	public void setDetrazione(BigDecimal detrazione) {
		this.detrazione = detrazione;
	}

	public Date getDtBonifico() {
		return this.dtBonifico;
	}

	public void setDtBonifico(Date dtBonifico) {
		this.dtBonifico = dtBonifico;
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

	public Date getDtFornitura() {
		return this.dtFornitura;
	}

	public void setDtFornitura(Date dtFornitura) {
		this.dtFornitura = dtFornitura;
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

	public String getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}

	public Date getDtRipartizione() {
		return this.dtRipartizione;
	}

	public void setDtRipartizione(Date dtRipartizione) {
		this.dtRipartizione = dtRipartizione;
	}

	public Date getDtRiscossione() {
		return this.dtRiscossione;
	}

	public void setDtRiscossione(Date dtRiscossione) {
		this.dtRiscossione = dtRiscossione;
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

	public BigDecimal getFlagErrAr() {
		return this.flagErrAr;
	}

	public void setFlagErrAr(BigDecimal flagErrAr) {
		this.flagErrAr = flagErrAr;
	}

	public BigDecimal getFlagErrCf() {
		return this.flagErrCf;
	}

	public void setFlagErrCf(BigDecimal flagErrCf) {
		this.flagErrCf = flagErrCf;
	}

	public BigDecimal getFlagErrCt() {
		return this.flagErrCt;
	}

	public void setFlagErrCt(BigDecimal flagErrCt) {
		this.flagErrCt = flagErrCt;
	}

	public BigDecimal getFlagErrIciImu() {
		return this.flagErrIciImu;
	}

	public void setFlagErrIciImu(BigDecimal flagErrIciImu) {
		this.flagErrIciImu = flagErrIciImu;
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

	public BigDecimal getImpCredito() {
		return this.impCredito;
	}

	public void setImpCredito(BigDecimal impCredito) {
		this.impCredito = impCredito;
	}

	public BigDecimal getImpDebito() {
		return this.impDebito;
	}

	public void setImpDebito(BigDecimal impDebito) {
		this.impDebito = impDebito;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getNumFabbIciImu() {
		return this.numFabbIciImu;
	}

	public void setNumFabbIciImu(BigDecimal numFabbIciImu) {
		this.numFabbIciImu = numFabbIciImu;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public BigDecimal getProgDelega() {
		return this.progDelega;
	}

	public void setProgDelega(BigDecimal progDelega) {
		this.progDelega = progDelega;
	}

	public BigDecimal getProgFornitura() {
		return this.progFornitura;
	}

	public void setProgFornitura(BigDecimal progFornitura) {
		this.progFornitura = progFornitura;
	}

	public BigDecimal getProgRiga() {
		return this.progRiga;
	}

	public void setProgRiga(BigDecimal progRiga) {
		this.progRiga = progRiga;
	}

	public BigDecimal getProgRipartizione() {
		return this.progRipartizione;
	}

	public void setProgRipartizione(BigDecimal progRipartizione) {
		this.progRipartizione = progRipartizione;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public BigDecimal getRateazione() {
		return this.rateazione;
	}

	public void setRateazione(BigDecimal rateazione) {
		this.rateazione = rateazione;
	}

	public BigDecimal getRavvedimento() {
		return this.ravvedimento;
	}

	public void setRavvedimento(BigDecimal ravvedimento) {
		this.ravvedimento = ravvedimento;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getTipoEnteRd() {
		return this.tipoEnteRd;
	}

	public void setTipoEnteRd(String tipoEnteRd) {
		this.tipoEnteRd = tipoEnteRd;
	}

	public String getTipoImposta() {
		return this.tipoImposta;
	}

	public void setTipoImposta(String tipoImposta) {
		this.tipoImposta = tipoImposta;
	}

	public BigDecimal getVarImmIciImu() {
		return this.varImmIciImu;
	}

	public void setVarImmIciImu(BigDecimal varImmIciImu) {
		this.varImmIciImu = varImmIciImu;
	}

}