package it.webred.ct.data.model.tarsu;

import it.webred.ct.data.model.catasto.Sitidstr;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_T_TAR_VIA database table.
 * 
 */
@NamedNativeQueries({
	@NamedNativeQuery (
		   name = "SitTTarVia.getListaVieLike", 
		   query = "select * from SitTTarVia via " +
		   		"where sitidstr.DATA_FINE_VAL = TO_DATE('99991231', 'yyyymmdd') " +
		   		"and SitTTarVia.DESCRIZIONE LIKE '%'||:via||'%'",
		   		resultClass = SitTTarVia.class
	),
	
	@NamedNativeQuery (
		   name = "SitTTarVia.getListaVieEquals", 
		   query = "select * from SitTTarVia " +
		   		"where sitidstr.DATA_FINE_VAL  = TO_DATE('99991231', 'yyyymmdd') " +
		   		"and SITIDSTR.COD_NAZIONALE = :cod_nazionale " +
		   		"and SitTTarVia.DESCRIZIONE = :via ",
		   		resultClass = SitTTarVia.class
	)
				
})


@Entity
@Table(name="SIT_T_TAR_VIA")
public class SitTTarVia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CTR_HASH")
	private String ctrHash;

	private String descrizione;

	@Column(name="DESCRIZIONE_ALT")
	private String descrizioneAlt;

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
	@Column(name="DT_INI")
	private Date dtIni;

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

	@Id
	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="ID_ORIG_VIA_U")
	private String idOrigViaU;

	private String prefisso;

	@Column(name="PREFISSO_ALT")
	private String prefissoAlt;

	private String processid;

	private String provenienza;

	private String tipo;

    public SitTTarVia() {
    }

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizioneAlt() {
		return this.descrizioneAlt;
	}

	public void setDescrizioneAlt(String descrizioneAlt) {
		this.descrizioneAlt = descrizioneAlt;
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

	public Date getDtIni() {
		return this.dtIni;
	}

	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
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

	public String getIdOrigViaU() {
		return this.idOrigViaU;
	}

	public void setIdOrigViaU(String idOrigViaU) {
		this.idOrigViaU = idOrigViaU;
	}

	public String getPrefisso() {
		return this.prefisso;
	}

	public void setPrefisso(String prefisso) {
		this.prefisso = prefisso;
	}

	public String getPrefissoAlt() {
		return this.prefissoAlt;
	}

	public void setPrefissoAlt(String prefissoAlt) {
		this.prefissoAlt = prefissoAlt;
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

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}