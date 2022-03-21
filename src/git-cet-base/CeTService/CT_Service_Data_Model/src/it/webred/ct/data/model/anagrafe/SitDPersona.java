package it.webred.ct.data.model.anagrafe;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_D_PERSONA database table.
 * 
 */
@Entity
@Table(name="SIT_D_PERSONA")
@IndiceEntity(sorgente="1")
public class SitDPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;

	private String codfisc;

	private String cognome;

	@Column(name="CTR_HASH")
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_EMI")
	private Date dataEmi;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_IMM")
	private Date dataImm;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_RESIDENZA")
	private Date dataInizioResidenza;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_MOR")
	private Date dataMor;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;

	@Column(name="DES_PERSONA")
	private String desPersona;

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

	@Column(name="FLAG_CODICE_FISCALE")
	private String flagCodiceFiscale;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_COMUNE_EMI")
	private String idExtComuneEmi;

	@Column(name="ID_EXT_COMUNE_IMM")
	private String idExtComuneImm;

	@Column(name="ID_EXT_COMUNE_MOR")
	private String idExtComuneMor;

	@Column(name="ID_EXT_COMUNE_NASCITA")
	private String idExtComuneNascita;

	@Column(name="ID_EXT_D_PERSONA_MADRE")
	private String idExtDPersonaMadre;

	@Column(name="ID_EXT_D_PERSONA_PADRE")
	private String idExtDPersonaPadre;

	@Column(name="ID_EXT_PROVINCIA_EMI")
	private String idExtProvinciaEmi;

	@Column(name="ID_EXT_PROVINCIA_IMM")
	private String idExtProvinciaImm;

	@Column(name="ID_EXT_PROVINCIA_MOR")
	private String idExtProvinciaMor;

	@Column(name="ID_EXT_PROVINCIA_NASCITA")
	private String idExtProvinciaNascita;

	@Column(name="ID_EXT_STATO")
	private String idExtStato;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String nome;

	@Column(name="POSIZ_ANA")
	private String posizAna;

	private String processid;

	private String sesso;

	@Column(name="STATO_CIVILE")
	private String statoCivile;

    public SitDPersona() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodfisc() {
		return this.codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataEmi() {
		return this.dataEmi;
	}

	public void setDataEmi(Date dataEmi) {
		this.dataEmi = dataEmi;
	}

	public Date getDataImm() {
		return this.dataImm;
	}

	public void setDataImm(Date dataImm) {
		this.dataImm = dataImm;
	}

	public Date getDataInizioResidenza() {
		return this.dataInizioResidenza;
	}

	public void setDataInizioResidenza(Date dataInizioResidenza) {
		this.dataInizioResidenza = dataInizioResidenza;
	}

	public Date getDataMor() {
		return this.dataMor;
	}

	public void setDataMor(Date dataMor) {
		this.dataMor = dataMor;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDesPersona() {
		return this.desPersona;
	}

	public void setDesPersona(String desPersona) {
		this.desPersona = desPersona;
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

	public String getFlagCodiceFiscale() {
		return this.flagCodiceFiscale;
	}

	public void setFlagCodiceFiscale(String flagCodiceFiscale) {
		this.flagCodiceFiscale = flagCodiceFiscale;
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

	public String getIdExtComuneEmi() {
		return this.idExtComuneEmi;
	}

	public void setIdExtComuneEmi(String idExtComuneEmi) {
		this.idExtComuneEmi = idExtComuneEmi;
	}

	public String getIdExtComuneImm() {
		return this.idExtComuneImm;
	}

	public void setIdExtComuneImm(String idExtComuneImm) {
		this.idExtComuneImm = idExtComuneImm;
	}

	public String getIdExtComuneMor() {
		return this.idExtComuneMor;
	}

	public void setIdExtComuneMor(String idExtComuneMor) {
		this.idExtComuneMor = idExtComuneMor;
	}

	public String getIdExtComuneNascita() {
		return this.idExtComuneNascita;
	}

	public void setIdExtComuneNascita(String idExtComuneNascita) {
		this.idExtComuneNascita = idExtComuneNascita;
	}

	public String getIdExtDPersonaMadre() {
		return this.idExtDPersonaMadre;
	}

	public void setIdExtDPersonaMadre(String idExtDPersonaMadre) {
		this.idExtDPersonaMadre = idExtDPersonaMadre;
	}

	public String getIdExtDPersonaPadre() {
		return this.idExtDPersonaPadre;
	}

	public void setIdExtDPersonaPadre(String idExtDPersonaPadre) {
		this.idExtDPersonaPadre = idExtDPersonaPadre;
	}

	public String getIdExtProvinciaEmi() {
		return this.idExtProvinciaEmi;
	}

	public void setIdExtProvinciaEmi(String idExtProvinciaEmi) {
		this.idExtProvinciaEmi = idExtProvinciaEmi;
	}

	public String getIdExtProvinciaImm() {
		return this.idExtProvinciaImm;
	}

	public void setIdExtProvinciaImm(String idExtProvinciaImm) {
		this.idExtProvinciaImm = idExtProvinciaImm;
	}

	public String getIdExtProvinciaMor() {
		return this.idExtProvinciaMor;
	}

	public void setIdExtProvinciaMor(String idExtProvinciaMor) {
		this.idExtProvinciaMor = idExtProvinciaMor;
	}

	public String getIdExtProvinciaNascita() {
		return this.idExtProvinciaNascita;
	}

	public void setIdExtProvinciaNascita(String idExtProvinciaNascita) {
		this.idExtProvinciaNascita = idExtProvinciaNascita;
	}

	public String getIdExtStato() {
		return this.idExtStato;
	}

	public void setIdExtStato(String idExtStato) {
		this.idExtStato = idExtStato;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPosizAna() {
		return this.posizAna;
	}

	public void setPosizAna(String posizAna) {
		this.posizAna = posizAna;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoCivile() {
		return this.statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

}