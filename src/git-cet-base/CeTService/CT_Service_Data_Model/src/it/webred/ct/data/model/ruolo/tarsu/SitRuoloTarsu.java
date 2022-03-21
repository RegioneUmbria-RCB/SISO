package it.webred.ct.data.model.ruolo.tarsu;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_RUOLO_TARSU database table.
 * 
 */
@Entity
@Table(name="SIT_RUOLO_TARSU")
public class SitRuoloTarsu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Column(name="ACCONTO_TARSU_ANNO")
	private BigDecimal accontoTarsuAnno;

	@Column(name="ADD_ECA")
	private BigDecimal addEca;

	private String anno;

	private String cap;

	private String codfisc;

	private String comune;

	@Column(name="CTR_HASH")
	private String ctrHash;

	private BigDecimal cu;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ACC")
	private Date dataAcc;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NOTIFICA")
	private Date dataNotifica;

	private String dom;

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

	private String estero;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	private String iban;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IMPORTO_NOTIFICA")
	private BigDecimal importoNotifica;

	private String indirizzo;

	private BigDecimal interessi;

	@Column(name="MAGG_ECA")
	private BigDecimal maggEca;

	@Column(name="NOMINATIVO_CONTRIB")
	private String nominativoContrib;

	@Column(name="NUM_PROVV")
	private String numProvv;

	@Column(name="PERC_ECA")
	private BigDecimal percEca;

	@Column(name="PERC_MAGG_ECA")
	private BigDecimal percMaggEca;

	@Column(name="PERC_TRIB_PROV")
	private BigDecimal percTribProv;

	private String processid;

	private String prov;

	private BigDecimal sanzione;

	@Column(name="SGR_ANNO_SUCC")
	private String sgrAnnoSucc;

	@Column(name="SUP_ANNO")
	private String supAnno;

	@Column(name="SUP_ANNO_SUCC")
	private String supAnnoSucc;

	private String tipo;

	@Column(name="TIPO_DOC")
	private String tipoDoc;

	@Column(name="TOT_NETTO")
	private BigDecimal totNetto;

	private BigDecimal totale;

	@Column(name="TRIB_PROV")
	private BigDecimal tribProv;

	public SitRuoloTarsu() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAccontoTarsuAnno() {
		return this.accontoTarsuAnno;
	}

	public void setAccontoTarsuAnno(BigDecimal accontoTarsuAnno) {
		this.accontoTarsuAnno = accontoTarsuAnno;
	}

	public BigDecimal getAddEca() {
		return this.addEca;
	}

	public void setAddEca(BigDecimal addEca) {
		this.addEca = addEca;
	}

	public String getAnno() {
		return this.anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodfisc() {
		return this.codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
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

	public BigDecimal getCu() {
		return this.cu;
	}

	public void setCu(BigDecimal cu) {
		this.cu = cu;
	}

	public Date getDataAcc() {
		return this.dataAcc;
	}

	public void setDataAcc(Date dataAcc) {
		this.dataAcc = dataAcc;
	}

	public Date getDataNotifica() {
		return this.dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public String getDom() {
		return this.dom;
	}

	public void setDom(String dom) {
		this.dom = dom;
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

	public String getEstero() {
		return this.estero;
	}

	public void setEstero(String estero) {
		this.estero = estero;
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

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public BigDecimal getImportoNotifica() {
		return this.importoNotifica;
	}

	public void setImportoNotifica(BigDecimal importoNotifica) {
		this.importoNotifica = importoNotifica;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public BigDecimal getInteressi() {
		return this.interessi;
	}

	public void setInteressi(BigDecimal interessi) {
		this.interessi = interessi;
	}

	public BigDecimal getMaggEca() {
		return this.maggEca;
	}

	public void setMaggEca(BigDecimal maggEca) {
		this.maggEca = maggEca;
	}

	public String getNominativoContrib() {
		return this.nominativoContrib;
	}

	public void setNominativoContrib(String nominativoContrib) {
		this.nominativoContrib = nominativoContrib;
	}

	public String getNumProvv() {
		return this.numProvv;
	}

	public void setNumProvv(String numProvv) {
		this.numProvv = numProvv;
	}

	public BigDecimal getPercEca() {
		return this.percEca;
	}

	public void setPercEca(BigDecimal percEca) {
		this.percEca = percEca;
	}

	public BigDecimal getPercMaggEca() {
		return this.percMaggEca;
	}

	public void setPercMaggEca(BigDecimal percMaggEca) {
		this.percMaggEca = percMaggEca;
	}

	public BigDecimal getPercTribProv() {
		return this.percTribProv;
	}

	public void setPercTribProv(BigDecimal percTribProv) {
		this.percTribProv = percTribProv;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProv() {
		return this.prov;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public BigDecimal getSanzione() {
		return this.sanzione;
	}

	public void setSanzione(BigDecimal sanzione) {
		this.sanzione = sanzione;
	}

	public String getSgrAnnoSucc() {
		return this.sgrAnnoSucc;
	}

	public void setSgrAnnoSucc(String sgrAnnoSucc) {
		this.sgrAnnoSucc = sgrAnnoSucc;
	}

	public String getSupAnno() {
		return this.supAnno;
	}

	public void setSupAnno(String supAnno) {
		this.supAnno = supAnno;
	}

	public String getSupAnnoSucc() {
		return this.supAnnoSucc;
	}

	public void setSupAnnoSucc(String supAnnoSucc) {
		this.supAnnoSucc = supAnnoSucc;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTipoDoc() {
		return this.tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public BigDecimal getTotNetto() {
		return this.totNetto;
	}

	public void setTotNetto(BigDecimal totNetto) {
		this.totNetto = totNetto;
	}

	public BigDecimal getTotale() {
		return this.totale;
	}

	public void setTotale(BigDecimal totale) {
		this.totale = totale;
	}

	public BigDecimal getTribProv() {
		return this.tribProv;
	}

	public void setTribProv(BigDecimal tribProv) {
		this.tribProv = tribProv;
	}

}