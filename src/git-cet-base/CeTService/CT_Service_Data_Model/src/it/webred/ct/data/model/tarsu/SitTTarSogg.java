package it.webred.ct.data.model.tarsu;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;
import it.webred.ct.data.model.catasto.ConsSoggTab;
import it.webred.ct.data.model.catasto.Siticivi;
import it.webred.ct.data.model.catasto.Sitidstr;
import it.webred.ct.data.model.catasto.Sitiuiu;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_T_TAR_SOGG database table.
 * 
 */
@Entity
@Table(name="SIT_T_TAR_SOGG")
@IndiceEntity(sorgente="2")
public class SitTTarSogg implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;
	
	@Column(name="CAP_COM_NSC")
	private String capComNsc;

	@Column(name="CAP_COM_RES")
	private String capComRes;

	@Column(name="COD_BLFR_CMN_NSC")
	private String codBlfrCmnNsc;

	@Column(name="COD_BLFR_CMN_RES")
	private String codBlfrCmnRes;

	@Column(name="COD_CMN_NSC")
	private String codCmnNsc;

	@Column(name="COD_CMN_RES")
	private String codCmnRes;

	@Column(name="COD_FISC")
	private String codFisc;

	@Column(name="COD_IST_CMN_NSC")
	private String codIstCmnNsc;

	@Column(name="COD_IST_CMN_RES")
	private String codIstCmnRes;

	@Column(name="COD_STATO_NSC")
	private String codStatoNsc;

	@Column(name="COD_STATO_RES")
	private String codStatoRes;

	@Column(name="COG_DENOM")
	private String cogDenom;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DES_COM_NSC")
	private String desComNsc;

	@Column(name="DES_COM_RES")
	private String desComRes;

	@Column(name="DES_IND_RES")
	private String desIndRes;

	@Column(name="DES_PROV_NSC")
	private String desProvNsc;

	@Column(name="DES_PROV_RES")
	private String desProvRes;

	@Column(name="DES_STATO_NSC")
	private String desStatoNsc;

	@Column(name="DES_STATO_RES")
	private String desStatoRes;

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
	@Column(name="DT_NSC")
	private Date dtNsc;

	@Column(name="ESP_CIV_RES")
	private String espCivRes;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="FLG_TRF")
	private String flgTrf;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_VIA_RES")
	private String idExtViaRes;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="ID_ORIG_SOGG_U")
	private String idOrigSoggU;

	@Column(name="IND_RES_EXT")
	private String indResExt;

	@Column(name="INTERNO_RES")
	private String internoRes;

	private String nome;

	@Column(name="NUM_CIV_EXT")
	private String numCivExt;

	@Column(name="NUM_CIV_RES")
	private String numCivRes;

	@Column(name="PART_IVA")
	private String partIva;

	@Column(name="PIANO_RES")
	private String pianoRes;

	private String processid;

	private String provenienza;

	@Column(name="SCALA_RES")
	private String scalaRes;

	private String sesso;

	@Column(name="SIGLA_PROV_NSC")
	private String siglaProvNsc;

	@Column(name="SIGLA_PROV_RES")
	private String siglaProvRes;

	@Column(name="TIP_SOGG")
	private String tipSogg;

    @Temporal( TemporalType.DATE)
	@Column(name="TMS_AGG")
	private Date tmsAgg;

    @Temporal( TemporalType.DATE)
	@Column(name="TMS_BON")
	private Date tmsBon;

    public SitTTarSogg() {
    }

	public String getCapComNsc() {
		return this.capComNsc;
	}

	public void setCapComNsc(String capComNsc) {
		this.capComNsc = capComNsc;
	}

	public String getCapComRes() {
		return this.capComRes;
	}

	public void setCapComRes(String capComRes) {
		this.capComRes = capComRes;
	}

	public String getCodBlfrCmnNsc() {
		return this.codBlfrCmnNsc;
	}

	public void setCodBlfrCmnNsc(String codBlfrCmnNsc) {
		this.codBlfrCmnNsc = codBlfrCmnNsc;
	}

	public String getCodBlfrCmnRes() {
		return this.codBlfrCmnRes;
	}

	public void setCodBlfrCmnRes(String codBlfrCmnRes) {
		this.codBlfrCmnRes = codBlfrCmnRes;
	}

	public String getCodCmnNsc() {
		return this.codCmnNsc;
	}

	public void setCodCmnNsc(String codCmnNsc) {
		this.codCmnNsc = codCmnNsc;
	}

	public String getCodCmnRes() {
		return this.codCmnRes;
	}

	public void setCodCmnRes(String codCmnRes) {
		this.codCmnRes = codCmnRes;
	}

	public String getCodFisc() {
		return this.codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getCodIstCmnNsc() {
		return this.codIstCmnNsc;
	}

	public void setCodIstCmnNsc(String codIstCmnNsc) {
		this.codIstCmnNsc = codIstCmnNsc;
	}

	public String getCodIstCmnRes() {
		return this.codIstCmnRes;
	}

	public void setCodIstCmnRes(String codIstCmnRes) {
		this.codIstCmnRes = codIstCmnRes;
	}

	public String getCodStatoNsc() {
		return this.codStatoNsc;
	}

	public void setCodStatoNsc(String codStatoNsc) {
		this.codStatoNsc = codStatoNsc;
	}

	public String getCodStatoRes() {
		return this.codStatoRes;
	}

	public void setCodStatoRes(String codStatoRes) {
		this.codStatoRes = codStatoRes;
	}

	public String getCogDenom() {
		return this.cogDenom;
	}

	public void setCogDenom(String cogDenom) {
		this.cogDenom = cogDenom;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDesComNsc() {
		return this.desComNsc;
	}

	public void setDesComNsc(String desComNsc) {
		this.desComNsc = desComNsc;
	}

	public String getDesComRes() {
		return this.desComRes;
	}

	public void setDesComRes(String desComRes) {
		this.desComRes = desComRes;
	}

	public String getDesIndRes() {
		return this.desIndRes;
	}

	public void setDesIndRes(String desIndRes) {
		this.desIndRes = desIndRes;
	}

	public String getDesProvNsc() {
		return this.desProvNsc;
	}

	public void setDesProvNsc(String desProvNsc) {
		this.desProvNsc = desProvNsc;
	}

	public String getDesProvRes() {
		return this.desProvRes;
	}

	public void setDesProvRes(String desProvRes) {
		this.desProvRes = desProvRes;
	}

	public String getDesStatoNsc() {
		return this.desStatoNsc;
	}

	public void setDesStatoNsc(String desStatoNsc) {
		this.desStatoNsc = desStatoNsc;
	}

	public String getDesStatoRes() {
		return this.desStatoRes;
	}

	public void setDesStatoRes(String desStatoRes) {
		this.desStatoRes = desStatoRes;
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

	public Date getDtNsc() {
		return this.dtNsc;
	}

	public void setDtNsc(Date dtNsc) {
		this.dtNsc = dtNsc;
	}

	public String getEspCivRes() {
		return this.espCivRes;
	}

	public void setEspCivRes(String espCivRes) {
		this.espCivRes = espCivRes;
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

	public String getFlgTrf() {
		return this.flgTrf;
	}

	public void setFlgTrf(String flgTrf) {
		this.flgTrf = flgTrf;
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

	public String getIdExtViaRes() {
		return this.idExtViaRes;
	}

	public void setIdExtViaRes(String idExtViaRes) {
		this.idExtViaRes = idExtViaRes;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getIdOrigSoggU() {
		return this.idOrigSoggU;
	}

	public void setIdOrigSoggU(String idOrigSoggU) {
		this.idOrigSoggU = idOrigSoggU;
	}

	public String getIndResExt() {
		return this.indResExt;
	}

	public void setIndResExt(String indResExt) {
		this.indResExt = indResExt;
	}

	public String getInternoRes() {
		return this.internoRes;
	}

	public void setInternoRes(String internoRes) {
		this.internoRes = internoRes;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumCivExt() {
		return this.numCivExt;
	}

	public void setNumCivExt(String numCivExt) {
		this.numCivExt = numCivExt;
	}

	public String getNumCivRes() {
		return this.numCivRes;
	}

	public void setNumCivRes(String numCivRes) {
		this.numCivRes = numCivRes;
	}

	public String getPartIva() {
		return this.partIva;
	}

	public void setPartIva(String partIva) {
		this.partIva = partIva;
	}

	public String getPianoRes() {
		return this.pianoRes;
	}

	public void setPianoRes(String pianoRes) {
		this.pianoRes = pianoRes;
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

	public String getScalaRes() {
		return this.scalaRes;
	}

	public void setScalaRes(String scalaRes) {
		this.scalaRes = scalaRes;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSiglaProvNsc() {
		return this.siglaProvNsc;
	}

	public void setSiglaProvNsc(String siglaProvNsc) {
		this.siglaProvNsc = siglaProvNsc;
	}

	public String getSiglaProvRes() {
		return this.siglaProvRes;
	}

	public void setSiglaProvRes(String siglaProvRes) {
		this.siglaProvRes = siglaProvRes;
	}

	public String getTipSogg() {
		return this.tipSogg;
	}

	public void setTipSogg(String tipSogg) {
		this.tipSogg = tipSogg;
	}

	public Date getTmsAgg() {
		return this.tmsAgg;
	}

	public void setTmsAgg(Date tmsAgg) {
		this.tmsAgg = tmsAgg;
	}

	public Date getTmsBon() {
		return this.tmsBon;
	}

	public void setTmsBon(Date tmsBon) {
		this.tmsBon = tmsBon;
	}

}