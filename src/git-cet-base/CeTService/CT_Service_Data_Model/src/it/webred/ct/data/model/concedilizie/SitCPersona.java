package it.webred.ct.data.model.concedilizie;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_C_PERSONA database table.
 * 
 */
@Entity
@Table(name="SIT_C_PERSONA")
public class SitCPersona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String albo;

	private String cap;

	@Column(name="CAP_DITTA")
	private String capDitta;

	@Column(name="CAP_STUDIO")
	private String capStudio;

	@Column(name="CF_DITTA")
	private String cfDitta;

	private String civico;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	private String cognome;

	@Column(name="COMUNE_DITTA")
	private String comuneDitta;

	@Column(name="COMUNE_NASCITA")
	private String comuneNascita;

	@Column(name="COMUNE_RESIDENZA")
	private String comuneResidenza;

	@Column(name="COMUNE_STUDIO")
	private String comuneStudio;

	@Column(name="CTR_HASH")
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INI_RES")
	private Date dataIniRes;

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

	private String email;

	private String fax;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String indirizzo;

	@Column(name="INDIRIZZO_DITTA")
	private String indirizzoDitta;

	@Column(name="INDIRIZZO_STUDIO")
	private String indirizzoStudio;

	private String nome;

	@Column(name="PI_DITTA")
	private String piDitta;

	private String piva;

	private String processid;

	@Column(name="PROV_DITTA")
	private String provDitta;

	@Column(name="PROV_NASCITA")
	private String provNascita;

	@Column(name="PROV_RESIDENZA")
	private String provResidenza;

	@Column(name="PROV_STUDIO")
	private String provStudio;

	private String provenienza;

	private String qualita;

	@Column(name="RAGSOC_DITTA")
	private String ragsocDitta;

	private String telefono;

	@Column(name="TIPO_PERSONA")
	private String tipoPersona;

	@Column(name="TIPO_SOGGETTO")
	private String tipoSoggetto;

	private String titolo;

    public SitCPersona() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAlbo() {
		return this.albo;
	}

	public void setAlbo(String albo) {
		this.albo = albo;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCapDitta() {
		return this.capDitta;
	}

	public void setCapDitta(String capDitta) {
		this.capDitta = capDitta;
	}

	public String getCapStudio() {
		return this.capStudio;
	}

	public void setCapStudio(String capStudio) {
		this.capStudio = capStudio;
	}

	public String getCfDitta() {
		return this.cfDitta;
	}

	public void setCfDitta(String cfDitta) {
		this.cfDitta = cfDitta;
	}

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneDitta() {
		return this.comuneDitta;
	}

	public void setComuneDitta(String comuneDitta) {
		this.comuneDitta = comuneDitta;
	}

	public String getComuneNascita() {
		return this.comuneNascita;
	}

	public void setComuneNascita(String comuneNascita) {
		this.comuneNascita = comuneNascita;
	}

	public String getComuneResidenza() {
		return this.comuneResidenza;
	}

	public void setComuneResidenza(String comuneResidenza) {
		this.comuneResidenza = comuneResidenza;
	}

	public String getComuneStudio() {
		return this.comuneStudio;
	}

	public void setComuneStudio(String comuneStudio) {
		this.comuneStudio = comuneStudio;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataIniRes() {
		return this.dataIniRes;
	}

	public void setDataIniRes(Date dataIniRes) {
		this.dataIniRes = dataIniRes;
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

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getIndirizzoDitta() {
		return this.indirizzoDitta;
	}

	public void setIndirizzoDitta(String indirizzoDitta) {
		this.indirizzoDitta = indirizzoDitta;
	}

	public String getIndirizzoStudio() {
		return this.indirizzoStudio;
	}

	public void setIndirizzoStudio(String indirizzoStudio) {
		this.indirizzoStudio = indirizzoStudio;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPiDitta() {
		return this.piDitta;
	}

	public void setPiDitta(String piDitta) {
		this.piDitta = piDitta;
	}

	public String getPiva() {
		return this.piva;
	}

	public void setPiva(String piva) {
		this.piva = piva;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProvDitta() {
		return this.provDitta;
	}

	public void setProvDitta(String provDitta) {
		this.provDitta = provDitta;
	}

	public String getProvNascita() {
		return this.provNascita;
	}

	public void setProvNascita(String provNascita) {
		this.provNascita = provNascita;
	}

	public String getProvResidenza() {
		return this.provResidenza;
	}

	public void setProvResidenza(String provResidenza) {
		this.provResidenza = provResidenza;
	}

	public String getProvStudio() {
		return this.provStudio;
	}

	public void setProvStudio(String provStudio) {
		this.provStudio = provStudio;
	}

	public String getProvenienza() {
		return this.provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getQualita() {
		return this.qualita;
	}

	public void setQualita(String qualita) {
		this.qualita = qualita;
	}

	public String getRagsocDitta() {
		return this.ragsocDitta;
	}

	public void setRagsocDitta(String ragsocDitta) {
		this.ragsocDitta = ragsocDitta;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public String getTipoSoggetto() {
		return this.tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getTitolo() {
		return this.titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

}