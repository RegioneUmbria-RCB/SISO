package it.webred.ct.data.model.f24;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name="SIT_T_F24_ID_ACCREDITO")
public class SitTF24IdAccredito implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="COD_ENTE_COM")
	private String codEnteCom;

	@Column(name="COD_MOVIMENTO")
	private BigDecimal codMovimento;

	@Column(name="COD_VALUTA")
	private String codValuta;

	private BigDecimal cro;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_ACCREDITO")
	private Date dtAccredito;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_BONIFICO_ORIG")
	private Date dtBonificoOrig;

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

	@Temporal(TemporalType.DATE)
	@Column(name="DT_RIPART_ORIG")
	private Date dtRipartOrig;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	private String iban;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_TESTATA")
	private String idExtTestata;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IMP_ACCREDITO")
	private BigDecimal impAccredito;

	@Column(name="NUM_CONTO_TU")
	private BigDecimal numContoTu;

	private String processid;

	@Column(name="PROG_FORNITURA")
	private BigDecimal progFornitura;

	@Column(name="PROG_RIPART_ORIG")
	private BigDecimal progRipartOrig;

	@Column(name="SEZ_CONTO_TU")
	private String sezContoTu;

	@Column(name="TIPO_IMPOSTA")
	private String tipoImposta;

	public SitTF24IdAccredito() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodEnteCom() {
		return this.codEnteCom;
	}

	public void setCodEnteCom(String codEnteCom) {
		this.codEnteCom = codEnteCom;
	}

	public BigDecimal getCodMovimento() {
		return this.codMovimento;
	}

	public void setCodMovimento(BigDecimal codMovimento) {
		this.codMovimento = codMovimento;
	}

	public String getCodValuta() {
		return this.codValuta;
	}

	public void setCodValuta(String codValuta) {
		this.codValuta = codValuta;
	}

	public BigDecimal getCro() {
		return this.cro;
	}

	public void setCro(BigDecimal cro) {
		this.cro = cro;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDtAccredito() {
		return this.dtAccredito;
	}

	public void setDtAccredito(Date dtAccredito) {
		this.dtAccredito = dtAccredito;
	}

	public Date getDtBonificoOrig() {
		return this.dtBonificoOrig;
	}

	public void setDtBonificoOrig(Date dtBonificoOrig) {
		this.dtBonificoOrig = dtBonificoOrig;
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

	public Date getDtRipartOrig() {
		return this.dtRipartOrig;
	}

	public void setDtRipartOrig(Date dtRipartOrig) {
		this.dtRipartOrig = dtRipartOrig;
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

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
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

	public BigDecimal getImpAccredito() {
		return this.impAccredito;
	}

	public void setImpAccredito(BigDecimal impAccredito) {
		this.impAccredito = impAccredito;
	}

	public BigDecimal getNumContoTu() {
		return this.numContoTu;
	}

	public void setNumContoTu(BigDecimal numContoTu) {
		this.numContoTu = numContoTu;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public BigDecimal getProgFornitura() {
		return this.progFornitura;
	}

	public void setProgFornitura(BigDecimal progFornitura) {
		this.progFornitura = progFornitura;
	}

	public BigDecimal getProgRipartOrig() {
		return this.progRipartOrig;
	}

	public void setProgRipartOrig(BigDecimal progRipartOrig) {
		this.progRipartOrig = progRipartOrig;
	}

	public String getSezContoTu() {
		return this.sezContoTu;
	}

	public void setSezContoTu(String sezContoTu) {
		this.sezContoTu = sezContoTu;
	}

	public String getTipoImposta() {
		return this.tipoImposta;
	}

	public void setTipoImposta(String tipoImposta) {
		this.tipoImposta = tipoImposta;
	}

}