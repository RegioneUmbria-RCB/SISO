package it.webred.ct.data.model.fornituraelettrica;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_ENEL_UTENTE database table.
 * 
 */
@Entity
@Table(name="SIT_ENEL_UTENTE")
@IndiceEntity(sorgente="10")
public class SitEnelUtente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name="COMUNE_DOM_FISCALE")
	private String comuneDomFiscale;

	@Column(name="COMUNE_NASCITA_SEDE")
	private String comuneNascitaSede;

	@Column(name="CTR_HASH")
	@Id
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;

	private String denominazione;

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

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String processid;

	@Column(name="PROVINCIA_DOM_FISCALE")
	private String provinciaDomFiscale;

	@Column(name="PROVINCIA_NASCITA_SEDE")
	private String provinciaNascitaSede;

	@Column(name="QUALIFICA_TITOLARE")
	private String qualificaTitolare;

	private String sesso;

    public SitEnelUtente() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getComuneDomFiscale() {
		return this.comuneDomFiscale;
	}

	public void setComuneDomFiscale(String comuneDomFiscale) {
		this.comuneDomFiscale = comuneDomFiscale;
	}

	public String getComuneNascitaSede() {
		return this.comuneNascitaSede;
	}

	public void setComuneNascitaSede(String comuneNascitaSede) {
		this.comuneNascitaSede = comuneNascitaSede;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDenominazione() {
		return this.denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
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

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProvinciaDomFiscale() {
		return this.provinciaDomFiscale;
	}

	public void setProvinciaDomFiscale(String provinciaDomFiscale) {
		this.provinciaDomFiscale = provinciaDomFiscale;
	}

	public String getProvinciaNascitaSede() {
		return this.provinciaNascitaSede;
	}

	public void setProvinciaNascitaSede(String provinciaNascitaSede) {
		this.provinciaNascitaSede = provinciaNascitaSede;
	}

	public String getQualificaTitolare() {
		return this.qualificaTitolare;
	}

	public void setQualificaTitolare(String qualificaTitolare) {
		this.qualificaTitolare = qualificaTitolare;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SitEnelUtente)) {
			return false;
		}
		SitEnelUtente castOther = (SitEnelUtente)other;
		return 
			this.id.equals(castOther.id)
			&& this.ctrHash.equals(castOther.ctrHash);
    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.ctrHash.hashCode();
		
		return hash;
    }

}