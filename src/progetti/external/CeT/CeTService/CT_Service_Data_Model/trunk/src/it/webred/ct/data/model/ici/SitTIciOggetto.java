package it.webred.ct.data.model.ici;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_T_ICI_OGGETTO database table.
 * 
 */
@Entity
@Table(name="SIT_T_ICI_OGGETTO")
@IndiceEntity(sorgente="2")
public class SitTIciOggetto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;

	@Column(name="CAR_IMM")
	private String carImm;

	private String cat;

	private String cls;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DES_IND")
	private String desInd;

	@Column(name="DESC_TIP_DEN")
	private String descTipDen;

	@Column(name="DESC_TIP_VAL")
	private String descTipVal;

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

	@Column(name="DTR_ABI_PRI")
	private BigDecimal dtrAbiPri;

	@Column(name="ESP_CIV")
	private String espCiv;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="FLG_ABI_PRI3112")
	private String flgAbiPri3112;

	@Column(name="FLG_ACQ")
	private String flgAcq;

	@Column(name="FLG_CSS")
	private String flgCss;

	@Column(name="FLG_ESE3112")
	private String flgEse3112;

	@Column(name="FLG_IMM_STO")
	private String flgImmSto;

	@Column(name="FLG_POS3112")
	private String flgPos3112;

	@Column(name="FLG_RID3112")
	private String flgRid3112;

	@Column(name="FLG_TRF")
	private String flgTrf;

	private String foglio;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_VIA")
	private String idExtVia;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String interno;

	@Column(name="MESI_ESE")
	private BigDecimal mesiEse;

	@Column(name="MESI_POS")
	private BigDecimal mesiPos;

	@Column(name="MESI_RID")
	private BigDecimal mesiRid;

	@Column(name="NUM_CIV")
	private String numCiv;

	@Column(name="NUM_DEN")
	private String numDen;

	@Column(name="NUM_MOD")
	private BigDecimal numMod;

	@Column(name="NUM_PRO")
	private String numPro;

	@Column(name="NUM_RIGA")
	private BigDecimal numRiga;

	private String numero;

	@Column(name="PAR_CTS")
	private String parCts;

	private String piano;

	@Column(name="PRC_POSS")
	private BigDecimal prcPoss;

	private String processid;

	private String provenienza;

	private String scala;

	private String sez;

	private String sub;

	@Column(name="SUF_RIGA")
	private BigDecimal sufRiga;

	@Column(name="TIP_DEN")
	private String tipDen;

	@Column(name="TIP_VAL")
	private String tipVal;

    @Temporal( TemporalType.DATE)
	@Column(name="TMS_AGG")
	private Date tmsAgg;

    @Temporal( TemporalType.DATE)
	@Column(name="TMS_BON")
	private Date tmsBon;

	@Column(name="VAL_IMM")
	private BigDecimal valImm;

	@Column(name="YEA_DEN")
	private String yeaDen;

	@Column(name="YEA_PRO")
	private String yeaPro;

	@Column(name="YEA_RIF")
	private String yeaRif;

    public SitTIciOggetto() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCarImm() {
		return this.carImm;
	}

	public void setCarImm(String carImm) {
		this.carImm = carImm;
	}

	public String getCat() {
		return this.cat;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getCls() {
		return this.cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDesInd() {
		return this.desInd;
	}

	public void setDesInd(String desInd) {
		this.desInd = desInd;
	}

	public String getDescTipDen() {
		return this.descTipDen;
	}

	public void setDescTipDen(String descTipDen) {
		this.descTipDen = descTipDen;
	}

	public String getDescTipVal() {
		return this.descTipVal;
	}

	public void setDescTipVal(String descTipVal) {
		this.descTipVal = descTipVal;
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

	public BigDecimal getDtrAbiPri() {
		return this.dtrAbiPri;
	}

	public void setDtrAbiPri(BigDecimal dtrAbiPri) {
		this.dtrAbiPri = dtrAbiPri;
	}

	public String getEspCiv() {
		return this.espCiv;
	}

	public void setEspCiv(String espCiv) {
		this.espCiv = espCiv;
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

	public String getFlgAbiPri3112() {
		return this.flgAbiPri3112;
	}

	public void setFlgAbiPri3112(String flgAbiPri3112) {
		this.flgAbiPri3112 = flgAbiPri3112;
	}

	public String getFlgAcq() {
		return this.flgAcq;
	}

	public void setFlgAcq(String flgAcq) {
		this.flgAcq = flgAcq;
	}

	public String getFlgCss() {
		return this.flgCss;
	}

	public void setFlgCss(String flgCss) {
		this.flgCss = flgCss;
	}

	public String getFlgEse3112() {
		return this.flgEse3112;
	}

	public void setFlgEse3112(String flgEse3112) {
		this.flgEse3112 = flgEse3112;
	}

	public String getFlgImmSto() {
		return this.flgImmSto;
	}

	public void setFlgImmSto(String flgImmSto) {
		this.flgImmSto = flgImmSto;
	}

	public String getFlgPos3112() {
		return this.flgPos3112;
	}

	public void setFlgPos3112(String flgPos3112) {
		this.flgPos3112 = flgPos3112;
	}

	public String getFlgRid3112() {
		return this.flgRid3112;
	}

	public void setFlgRid3112(String flgRid3112) {
		this.flgRid3112 = flgRid3112;
	}

	public String getFlgTrf() {
		return this.flgTrf;
	}

	public void setFlgTrf(String flgTrf) {
		this.flgTrf = flgTrf;
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

	public String getIdExtVia() {
		return this.idExtVia;
	}

	public void setIdExtVia(String idExtVia) {
		this.idExtVia = idExtVia;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getInterno() {
		return this.interno;
	}

	public void setInterno(String interno) {
		this.interno = interno;
	}

	public BigDecimal getMesiEse() {
		return this.mesiEse;
	}

	public void setMesiEse(BigDecimal mesiEse) {
		this.mesiEse = mesiEse;
	}

	public BigDecimal getMesiPos() {
		return this.mesiPos;
	}

	public void setMesiPos(BigDecimal mesiPos) {
		this.mesiPos = mesiPos;
	}

	public BigDecimal getMesiRid() {
		return this.mesiRid;
	}

	public void setMesiRid(BigDecimal mesiRid) {
		this.mesiRid = mesiRid;
	}

	public String getNumCiv() {
		String s = numCiv;
		if (s != null) {
			//s = s.trim();
			while (s.length() > 1 && s.charAt(0) == '0')
				s = s.substring(1);
		}
		return s;
	}
	public String getNumCivDaDB() {
		return numCiv;
		
	}

	public void setNumCiv(String numCiv) {
		this.numCiv = numCiv;
	}

	public String getNumDen() {
		return this.numDen;
	}

	public void setNumDen(String numDen) {
		this.numDen = numDen;
	}

	public BigDecimal getNumMod() {
		return this.numMod;
	}

	public void setNumMod(BigDecimal numMod) {
		this.numMod = numMod;
	}

	public String getNumPro() {
		return this.numPro;
	}

	public void setNumPro(String numPro) {
		this.numPro = numPro;
	}

	public BigDecimal getNumRiga() {
		return this.numRiga;
	}

	public void setNumRiga(BigDecimal numRiga) {
		this.numRiga = numRiga;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getParCts() {
		return this.parCts;
	}

	public void setParCts(String parCts) {
		this.parCts = parCts;
	}

	public String getPiano() {
		return this.piano;
	}

	public void setPiano(String piano) {
		this.piano = piano;
	}

	public BigDecimal getPrcPoss() {
		return this.prcPoss;
	}

	public void setPrcPoss(BigDecimal prcPoss) {
		this.prcPoss = prcPoss;
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

	public String getScala() {
		return this.scala;
	}

	public void setScala(String scala) {
		this.scala = scala;
	}

	public String getSez() {
		return this.sez;
	}

	public void setSez(String sez) {
		this.sez = sez;
	}

	public String getSub() {
		return this.sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public BigDecimal getSufRiga() {
		return this.sufRiga;
	}

	public void setSufRiga(BigDecimal sufRiga) {
		this.sufRiga = sufRiga;
	}

	public String getTipDen() {
		return this.tipDen;
	}

	public void setTipDen(String tipDen) {
		this.tipDen = tipDen;
	}

	public String getTipVal() {
		return this.tipVal;
	}

	public void setTipVal(String tipVal) {
		this.tipVal = tipVal;
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

	public BigDecimal getValImm() {
		return this.valImm;
	}

	public void setValImm(BigDecimal valImm) {
		this.valImm = valImm;
	}

	public String getYeaDen() {
		return this.yeaDen;
	}

	public void setYeaDen(String yeaDen) {
		this.yeaDen = yeaDen;
	}

	public String getYeaPro() {
		return this.yeaPro;
	}

	public void setYeaPro(String yeaPro) {
		this.yeaPro = yeaPro;
	}

	public String getYeaRif() {
		return this.yeaRif;
	}

	public void setYeaRif(String yeaRif) {
		this.yeaRif = yeaRif;
	}
}