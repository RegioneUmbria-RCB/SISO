package it.webred.ct.data.model.scuole;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_SCL_ISTITUTI database table.
 * 
 */
@Entity
@Table(name="SIT_SCL_ISTITUTI")
public class SitSclIstituti implements Serializable {
	private static final long serialVersionUID = 1L;

	private String civico;

	@Column(name="COD_COMUNE")
	private String codComune;

	@Column(name="COD_INDIRIZZO")
	private String codIndirizzo;

	@Id
	@Column(name="COD_ISTITUTO")
	private String codIstituto;

	private String comune;

	@Column(name="CTR_HASH")
	private String ctrHash;

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

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String lettera;

	@Column(name="NOME_ISTITUTO")
	private String nomeIstituto;

	@Column(name="NR_ISCRITTI")
	private BigDecimal nrIscritti;

	private String processid;

	@Column(name="TIPO_ISTITUTO")
	private String tipoIstituto;

	private String toponimo;

	private String via;

    public SitSclIstituti() {
    }

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodComune() {
		return this.codComune;
	}

	public void setCodComune(String codComune) {
		this.codComune = codComune;
	}

	public String getCodIndirizzo() {
		return this.codIndirizzo;
	}

	public void setCodIndirizzo(String codIndirizzo) {
		this.codIndirizzo = codIndirizzo;
	}

	public String getCodIstituto() {
		return this.codIstituto;
	}

	public void setCodIstituto(String codIstituto) {
		this.codIstituto = codIstituto;
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

	public String getLettera() {
		return this.lettera;
	}

	public void setLettera(String lettera) {
		this.lettera = lettera;
	}

	public String getNomeIstituto() {
		return this.nomeIstituto;
	}

	public void setNomeIstituto(String nomeIstituto) {
		this.nomeIstituto = nomeIstituto;
	}

	public BigDecimal getNrIscritti() {
		return this.nrIscritti;
	}

	public void setNrIscritti(BigDecimal nrIscritti) {
		this.nrIscritti = nrIscritti;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getTipoIstituto() {
		return this.tipoIstituto;
	}

	public void setTipoIstituto(String tipoIstituto) {
		this.tipoIstituto = tipoIstituto;
	}

	public String getToponimo() {
		return this.toponimo;
	}

	public void setToponimo(String toponimo) {
		this.toponimo = toponimo;
	}

	public String getVia() {
		return this.via;
	}

	public void setVia(String via) {
		this.via = via;
	}

}