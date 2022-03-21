package it.webred.ct.data.model.redditi.analitici;

import it.webred.ct.data.model.annotation.IndiceEntity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RED_AN_FABBRICATI database table.
 * 
 */
@Entity
@Table(name="RED_AN_FABBRICATI")
@IndiceEntity(sorgente="31")
public class RedAnFabbricati implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ANNO_IMPOSTA")
	private String annoImposta;

	@Column(name="CANONE_LOC")
	private String canoneLoc;

	@Column(name="CASI_PART")
	private String casiPart;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	private String comune;

	private String continuazione;

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

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	private String giorni;

	private String ici;

	@Id
	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IDE_TELEMATICO")
	private String ideTelematico;

	private String imponibile;
	
	private String modulo;

	@Column(name="NUM_FABB")
	private String numFabb;

	private String possesso;

	private String processid;

	private String rendita;

	@Column(name="TIPO_MODELLO")
	private String tipoModello;

	private String utilizzo;
	
	@Column(name="CEDOLARE_AQ")
	private String cedolareAq;

	public String getCedolareAq() {
		return cedolareAq;
	}

	public void setCedolareAq(String cedolareAq) {
		this.cedolareAq = cedolareAq;
	}

	public RedAnFabbricati() {
	}

	public String getAnnoImposta() {
		return this.annoImposta;
	}

	public void setAnnoImposta(String annoImposta) {
		this.annoImposta = annoImposta;
	}

	public String getCanoneLoc() {
		return this.canoneLoc;
	}

	public void setCanoneLoc(String canoneLoc) {
		this.canoneLoc = canoneLoc;
	}

	public String getCasiPart() {
		return this.casiPart;
	}

	public void setCasiPart(String casiPart) {
		this.casiPart = casiPart;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getContinuazione() {
		return this.continuazione;
	}

	public void setContinuazione(String continuazione) {
		this.continuazione = continuazione;
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

	public String getGiorni() {
		return this.giorni;
	}

	public void setGiorni(String giorni) {
		this.giorni = giorni;
	}

	public String getIci() {
		return this.ici;
	}

	public void setIci(String ici) {
		this.ici = ici;
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

	public String getIdeTelematico() {
		return this.ideTelematico;
	}

	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}

	public String getImponibile() {
		return this.imponibile;
	}

	public void setImponibile(String imponibile) {
		this.imponibile = imponibile;
	}

	public String getNumFabb() {
		return this.numFabb;
	}

	public void setNumFabb(String numFabb) {
		this.numFabb = numFabb;
	}

	public String getPossesso() {
		return this.possesso;
	}

	public void setPossesso(String possesso) {
		this.possesso = possesso;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getRendita() {
		return this.rendita;
	}

	public void setRendita(String rendita) {
		this.rendita = rendita;
	}

	public String getTipoModello() {
		return this.tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

	public String getUtilizzo() {
		return this.utilizzo;
	}

	public void setUtilizzo(String utilizzo) {
		this.utilizzo = utilizzo;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

}