package it.webred.ct.data.model.ruolo.tares;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_RUOLO_TARES_ST database table.
 * 
 */
@Entity
@Table(name="SIT_RUOLO_TARES_ST")
public class SitRuoloTaresSt implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String id;

	private String anno;

	@Column(name="COD_TRIBUTO")
	private String codTributo;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FATTURA")
	private Date dataFattura;

	@Column(name="DES_TIPO")
	private String desTipo;

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

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_RUOLO")
	private String idExtRuolo;

	@Column(name="ID_ORIG")
	private String idOrig;

	private BigDecimal importo;

	@Column(name="IMPORTO_QF")
	private BigDecimal importoQf;

	@Column(name="IMPORTO_QS")
	private BigDecimal importoQs;

	@Column(name="IMPORTO_QV")
	private BigDecimal importoQv;

	private String indirizzo;

	@Column(name="NUM_FATTURA")
	private String numFattura;

	private String processid;

	private String prog;

	@Column(name="RIDUZIONE_QF")
	private BigDecimal riduzioneQf;

	@Column(name="RIDUZIONE_QS")
	private BigDecimal riduzioneQs;

	@Column(name="RIDUZIONE_QV")
	private BigDecimal riduzioneQv;

	public SitRuoloTaresSt() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnno() {
		return this.anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public String getCodTributo() {
		return this.codTributo;
	}

	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataFattura() {
		return this.dataFattura;
	}

	public void setDataFattura(Date dataFattura) {
		this.dataFattura = dataFattura;
	}

	public String getDesTipo() {
		return this.desTipo;
	}

	public void setDesTipo(String desTipo) {
		this.desTipo = desTipo;
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

	public String getIdExtRuolo() {
		return this.idExtRuolo;
	}

	public void setIdExtRuolo(String idExtRuolo) {
		this.idExtRuolo = idExtRuolo;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public BigDecimal getImportoQf() {
		return this.importoQf;
	}

	public void setImportoQf(BigDecimal importoQf) {
		this.importoQf = importoQf;
	}

	public BigDecimal getImportoQs() {
		return this.importoQs;
	}

	public void setImportoQs(BigDecimal importoQs) {
		this.importoQs = importoQs;
	}

	public BigDecimal getImportoQv() {
		return this.importoQv;
	}

	public void setImportoQv(BigDecimal importoQv) {
		this.importoQv = importoQv;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumFattura() {
		return this.numFattura;
	}

	public void setNumFattura(String numFattura) {
		this.numFattura = numFattura;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProg() {
		return this.prog;
	}

	public void setProg(String prog) {
		this.prog = prog;
	}

	public BigDecimal getRiduzioneQf() {
		return this.riduzioneQf;
	}

	public void setRiduzioneQf(BigDecimal riduzioneQf) {
		this.riduzioneQf = riduzioneQf;
	}

	public BigDecimal getRiduzioneQs() {
		return this.riduzioneQs;
	}

	public void setRiduzioneQs(BigDecimal riduzioneQs) {
		this.riduzioneQs = riduzioneQs;
	}

	public BigDecimal getRiduzioneQv() {
		return this.riduzioneQv;
	}

	public void setRiduzioneQv(BigDecimal riduzioneQv) {
		this.riduzioneQv = riduzioneQv;
	}

}