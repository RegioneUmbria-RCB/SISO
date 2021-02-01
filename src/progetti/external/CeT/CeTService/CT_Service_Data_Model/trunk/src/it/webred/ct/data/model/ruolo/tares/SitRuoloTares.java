package it.webred.ct.data.model.ruolo.tares;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_RUOLO_TARES database table.
 * 
 */
@Entity
@Table(name="SIT_RUOLO_TARES")
public class SitRuoloTares implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	@Column(name="ADD_IVA")
	private BigDecimal addIva;

	private String anno;

	@Column(name="ATT_COMUNE")
	private BigDecimal attComune;

	@Column(name="ATT_PRO")
	private BigDecimal attPro;

	private String cap;

	private String codfisc;

	@Column(name="COGNOME_RAGSOC")
	private String cognomeRagsoc;

	private String comune;

	@Column(name="COMUNE_NASC")
	private String comuneNasc;

	@Column(name="CTR_HASH")
	private String ctrHash;

	private BigDecimal cu;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_ACC")
	private Date dataAcc;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FATT")
	private Date dataFatt;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASC")
	private Date dataNasc;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NOTIFICA")
	private Date dataNotifica;

	@Column(name="DIFFERENZA_LORDO")
	private BigDecimal differenzaLordo;

	@Column(name="DIFFERENZA_NETTO")
	private BigDecimal differenzaNetto;

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

	@Column(name="FLAG_ADESIONE")
	private String flagAdesione;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IMP_NETTO_ACCONTO")
	private BigDecimal impNettoAcconto;

	@Column(name="IMPORTO_NOTIFICA")
	private BigDecimal importoNotifica;

	private String indirizzo;

	private BigDecimal interessi;

	private BigDecimal nimm;

	private String nome;

	@Column(name="NOMINATIVO_CONTRIB")
	private String nominativoContrib;

	@Column(name="NUM_FATT")
	private String numFatt;

	@Column(name="NUM_PROVV")
	private String numProvv;

	@Column(name="OLD_PRO")
	private BigDecimal oldPro;

	@Column(name="OLD_TOT")
	private BigDecimal oldTot;

	@Column(name="PERC_IVA")
	private BigDecimal percIva;

	@Column(name="PERC_TRIB_PROV")
	private BigDecimal percTribProv;

	private String processid;

	private String prov;

	@Column(name="PROV_NASC")
	private String provNasc;

	private BigDecimal sanzione;

	private String sesso;

	@Column(name="SGR_ANNO")
	private String sgrAnno;

	@Column(name="SUP_ANNO")
	private String supAnno;

	@Column(name="SUP_ANNO_PREC")
	private String supAnnoPrec;

	private String tipo;

	@Column(name="TIPO_DOC")
	private String tipoDoc;

	@Column(name="TOT_NETTO")
	private BigDecimal totNetto;

	private BigDecimal totale;

	@Column(name="TOTALE_ENTE")
	private BigDecimal totaleEnte;

	@Column(name="TOTALE_STATO")
	private BigDecimal totaleStato;

	@Column(name="TR_QUOTA_TIA")
	private String trQuotaTia;

	@Column(name="TRIB_NETTO_COMUNE")
	private BigDecimal tribNettoComune;

	@Column(name="TRIB_PROV")
	private BigDecimal tribProv;

	@Column(name="TRIB_PROVINCIALE")
	private BigDecimal tribProvinciale;

	public SitRuoloTares() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getAddIva() {
		return this.addIva;
	}

	public void setAddIva(BigDecimal addIva) {
		this.addIva = addIva;
	}

	public String getAnno() {
		return this.anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public BigDecimal getAttComune() {
		return this.attComune;
	}

	public void setAttComune(BigDecimal attComune) {
		this.attComune = attComune;
	}

	public BigDecimal getAttPro() {
		return this.attPro;
	}

	public void setAttPro(BigDecimal attPro) {
		this.attPro = attPro;
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

	public String getCognomeRagsoc() {
		return this.cognomeRagsoc;
	}

	public void setCognomeRagsoc(String cognomeRagsoc) {
		this.cognomeRagsoc = cognomeRagsoc;
	}

	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getComuneNasc() {
		return this.comuneNasc;
	}

	public void setComuneNasc(String comuneNasc) {
		this.comuneNasc = comuneNasc;
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

	public Date getDataFatt() {
		return this.dataFatt;
	}

	public void setDataFatt(Date dataFatt) {
		this.dataFatt = dataFatt;
	}

	public Date getDataNasc() {
		return this.dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Date getDataNotifica() {
		return this.dataNotifica;
	}

	public void setDataNotifica(Date dataNotifica) {
		this.dataNotifica = dataNotifica;
	}

	public BigDecimal getDifferenzaLordo() {
		return this.differenzaLordo;
	}

	public void setDifferenzaLordo(BigDecimal differenzaLordo) {
		this.differenzaLordo = differenzaLordo;
	}

	public BigDecimal getDifferenzaNetto() {
		return this.differenzaNetto;
	}

	public void setDifferenzaNetto(BigDecimal differenzaNetto) {
		this.differenzaNetto = differenzaNetto;
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

	public String getFlagAdesione() {
		return this.flagAdesione;
	}

	public void setFlagAdesione(String flagAdesione) {
		this.flagAdesione = flagAdesione;
	}

	public BigDecimal getFlagDtValDato() {
		return this.flagDtValDato;
	}

	public void setFlagDtValDato(BigDecimal flagDtValDato) {
		this.flagDtValDato = flagDtValDato;
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

	public BigDecimal getImpNettoAcconto() {
		return this.impNettoAcconto;
	}

	public void setImpNettoAcconto(BigDecimal impNettoAcconto) {
		this.impNettoAcconto = impNettoAcconto;
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

	public BigDecimal getNimm() {
		return this.nimm;
	}

	public void setNimm(BigDecimal nimm) {
		this.nimm = nimm;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNominativoContrib() {
		return this.nominativoContrib;
	}

	public void setNominativoContrib(String nominativoContrib) {
		this.nominativoContrib = nominativoContrib;
	}

	public String getNumFatt() {
		return this.numFatt;
	}

	public void setNumFatt(String numFatt) {
		this.numFatt = numFatt;
	}

	public String getNumProvv() {
		return this.numProvv;
	}

	public void setNumProvv(String numProvv) {
		this.numProvv = numProvv;
	}

	public BigDecimal getOldPro() {
		return this.oldPro;
	}

	public void setOldPro(BigDecimal oldPro) {
		this.oldPro = oldPro;
	}

	public BigDecimal getOldTot() {
		return this.oldTot;
	}

	public void setOldTot(BigDecimal oldTot) {
		this.oldTot = oldTot;
	}

	public BigDecimal getPercIva() {
		return this.percIva;
	}

	public void setPercIva(BigDecimal percIva) {
		this.percIva = percIva;
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

	public String getProvNasc() {
		return this.provNasc;
	}

	public void setProvNasc(String provNasc) {
		this.provNasc = provNasc;
	}

	public BigDecimal getSanzione() {
		return this.sanzione;
	}

	public void setSanzione(BigDecimal sanzione) {
		this.sanzione = sanzione;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSgrAnno() {
		return this.sgrAnno;
	}

	public void setSgrAnno(String sgrAnno) {
		this.sgrAnno = sgrAnno;
	}

	public String getSupAnno() {
		return this.supAnno;
	}

	public void setSupAnno(String supAnno) {
		this.supAnno = supAnno;
	}

	public String getSupAnnoPrec() {
		return this.supAnnoPrec;
	}

	public void setSupAnnoPrec(String supAnnoPrec) {
		this.supAnnoPrec = supAnnoPrec;
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

	public BigDecimal getTotaleEnte() {
		return this.totaleEnte;
	}

	public void setTotaleEnte(BigDecimal totaleEnte) {
		this.totaleEnte = totaleEnte;
	}

	public BigDecimal getTotaleStato() {
		return this.totaleStato;
	}

	public void setTotaleStato(BigDecimal totaleStato) {
		this.totaleStato = totaleStato;
	}

	public String getTrQuotaTia() {
		return this.trQuotaTia;
	}

	public void setTrQuotaTia(String trQuotaTia) {
		this.trQuotaTia = trQuotaTia;
	}

	public BigDecimal getTribNettoComune() {
		return this.tribNettoComune;
	}

	public void setTribNettoComune(BigDecimal tribNettoComune) {
		this.tribNettoComune = tribNettoComune;
	}

	public BigDecimal getTribProv() {
		return this.tribProv;
	}

	public void setTribProv(BigDecimal tribProv) {
		this.tribProv = tribProv;
	}

	public BigDecimal getTribProvinciale() {
		return this.tribProvinciale;
	}

	public void setTribProvinciale(BigDecimal tribProvinciale) {
		this.tribProvinciale = tribProvinciale;
	}

}