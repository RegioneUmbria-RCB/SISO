package it.webred.ct.data.model.versamenti.iciDM;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_T_ICI_DM_VERS database table.
 * 
 */
@Entity
@Table(name="SIT_T_ICI_DM_VERS")
public class SitTIciDmVers implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Column(name="ANNO_IMPOSTA")
	private String annoImposta;

	private String cap;

	@Column(name="CF_VERSANTE")
	private String cfVersante;

	@Column(name="COD_CONCESSIONE")
	private String codConcessione;

	@Column(name="COD_ENTE")
	private String codEnte;

	private String comune;

	@Column(name="CTR_HASH")
	private String ctrHash;

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

	@Temporal(TemporalType.DATE)
	@Column(name="DT_REGISTRAZIONE")
	private Date dtRegistrazione;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_VERSAMENTO")
	private Date dtVersamento;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="FLG_ACC_SALDO")
	private String flgAccSaldo;

	@Column(name="FLG_COMPETENZA")
	private String flgCompetenza;

	@Column(name="FLG_IDENTIFICAZIONE")
	private String flgIdentificazione;

	@Column(name="FLG_QUADRATURA")
	private String flgQuadratura;

	@Column(name="FLG_RAVVEDIMENTO")
	private String flgRavvedimento;

	@Column(name="FLG_REPERIBILITA")
	private String flgReperibilita;

	@Column(name="ID_EXT_AN")
	private String idExtAn;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IMP_AB_PR")
	private BigDecimal impAbPr;

	@Column(name="IMP_AREE_FAB")
	private BigDecimal impAreeFab;

	@Column(name="IMP_DETRAZIONE")
	private BigDecimal impDetrazione;

	@Column(name="IMP_FABB")
	private BigDecimal impFabb;

	@Column(name="IMP_TERR")
	private BigDecimal impTerr;

	@Column(name="IMP_VERSATO")
	private BigDecimal impVersato;

	@Column(name="NUM_FABB")
	private BigDecimal numFabb;

	@Column(name="NUM_QUIETANZA")
	private String numQuietanza;

	@Column(name="NUM_QUIETANZA_RIF")
	private String numQuietanzaRif;

	private String processid;

	private BigDecimal prog;

	@Column(name="TIPO_VERSAMENTO")
	private String tipoVersamento;

	public SitTIciDmVers() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnnoImposta() {
		return this.annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCfVersante() {
		return this.cfVersante;
	}

	public void setCfVersante(String cfVersante) {
		this.cfVersante = cfVersante;
	}

	public String getCodConcessione() {
		return this.codConcessione;
	}

	public void setCodConcessione(String codConcessione) {
		this.codConcessione = codConcessione;
	}

	public String getCodEnte() {
		return this.codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}

	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
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

	public Date getDtRegistrazione() {
		return this.dtRegistrazione;
	}

	public void setDtRegistrazione(Date dtRegistrazione) {
		this.dtRegistrazione = dtRegistrazione;
	}

	public Date getDtVersamento() {
		return this.dtVersamento;
	}

	public void setDtVersamento(Date dtVersamento) {
		this.dtVersamento = dtVersamento;
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

	public String getFlgAccSaldo() {
		return this.flgAccSaldo;
	}

	public void setFlgAccSaldo(String flgAccSaldo) {
		this.flgAccSaldo = flgAccSaldo;
	}

	public String getFlgCompetenza() {
		return this.flgCompetenza;
	}

	public void setFlgCompetenza(String flgCompetenza) {
		this.flgCompetenza = flgCompetenza;
	}

	public String getFlgIdentificazione() {
		return this.flgIdentificazione;
	}

	public void setFlgIdentificazione(String flgIdentificazione) {
		this.flgIdentificazione = flgIdentificazione;
	}

	public String getFlgQuadratura() {
		return this.flgQuadratura;
	}

	public void setFlgQuadratura(String flgQuadratura) {
		this.flgQuadratura = flgQuadratura;
	}

	public String getFlgRavvedimento() {
		return this.flgRavvedimento;
	}

	public void setFlgRavvedimento(String flgRavvedimento) {
		this.flgRavvedimento = flgRavvedimento;
	}

	public String getFlgReperibilita() {
		return this.flgReperibilita;
	}

	public void setFlgReperibilita(String flgReperibilita) {
		this.flgReperibilita = flgReperibilita;
	}

	public String getIdExtAn() {
		return this.idExtAn;
	}

	public void setIdExtAn(String idExtAn) {
		this.idExtAn = idExtAn;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public BigDecimal getImpAbPr() {
		return this.impAbPr;
	}

	public void setImpAbPr(BigDecimal impAbPr) {
		this.impAbPr = impAbPr;
	}

	public BigDecimal getImpAreeFab() {
		return this.impAreeFab;
	}

	public void setImpAreeFab(BigDecimal impAreeFab) {
		this.impAreeFab = impAreeFab;
	}

	public BigDecimal getImpDetrazione() {
		return this.impDetrazione;
	}

	public void setImpDetrazione(BigDecimal impDetrazione) {
		this.impDetrazione = impDetrazione;
	}

	public BigDecimal getImpFabb() {
		return this.impFabb;
	}

	public void setImpFabb(BigDecimal impFabb) {
		this.impFabb = impFabb;
	}

	public BigDecimal getImpTerr() {
		return this.impTerr;
	}

	public void setImpTerr(BigDecimal impTerr) {
		this.impTerr = impTerr;
	}

	public BigDecimal getImpVersato() {
		return this.impVersato;
	}

	public void setImpVersato(BigDecimal impVersato) {
		this.impVersato = impVersato;
	}

	public BigDecimal getNumFabb() {
		return this.numFabb;
	}

	public void setNumFabb(BigDecimal numFabb) {
		this.numFabb = numFabb;
	}

	public String getNumQuietanza() {
		return this.numQuietanza;
	}

	public void setNumQuietanza(String numQuietanza) {
		this.numQuietanza = numQuietanza;
	}

	public String getNumQuietanzaRif() {
		return this.numQuietanzaRif;
	}

	public void setNumQuietanzaRif(String numQuietanzaRif) {
		this.numQuietanzaRif = numQuietanzaRif;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public BigDecimal getProg() {
		return this.prog;
	}

	public void setProg(BigDecimal prog) {
		this.prog = prog;
	}

	public String getTipoVersamento() {
		return this.tipoVersamento;
	}

	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}

}