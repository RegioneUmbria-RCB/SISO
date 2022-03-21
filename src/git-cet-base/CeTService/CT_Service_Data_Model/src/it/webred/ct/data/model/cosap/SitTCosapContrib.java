package it.webred.ct.data.model.cosap;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_T_COSAP_CONTRIB database table.
 * 
 */
@Entity
@Table(name="SIT_T_COSAP_CONTRIB")
public class SitTCosapContrib implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;

	@Column(name="CAP_RESIDENZA")
	private String capResidenza;

	private String civico;

	@Column(name="COD_BELFIORE_NASCITA")
	private String codBelfioreNascita;

	@Column(name="COD_BELFIORE_RESIDENZA")
	private String codBelfioreResidenza;

	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;

	@Column(name="CODICE_VIA")
	private String codiceVia;

	@Column(name="COG_DENOM")
	private String cogDenom;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DESC_COMUNE_NASCITA")
	private String descComuneNascita;

	@Column(name="DESC_COMUNE_RESIDENZA")
	private String descComuneResidenza;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_COSTIT_SOGGETTO")
	private Date dtCostitSoggetto;

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
	@Column(name="DT_ISCR_ARCHIVIO")
	private Date dtIscrArchivio;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_MOD")
	private Date dtMod;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_NASCITA")
	private Date dtNascita;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String indirizzo;

	private String nome;

	@Column(name="PARTITA_IVA")
	private String partitaIva;

	private String processid;

	private String provenienza;

	private String sedime;

	@Column(name="TIPO_PERSONA")
	private String tipoPersona;

    public SitTCosapContrib() {
    }

	public String getCapResidenza() {
		return this.capResidenza;
	}

	public void setCapResidenza(String capResidenza) {
		this.capResidenza = capResidenza;
	}

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodBelfioreNascita() {
		return this.codBelfioreNascita;
	}

	public void setCodBelfioreNascita(String codBelfioreNascita) {
		this.codBelfioreNascita = codBelfioreNascita;
	}

	public String getCodBelfioreResidenza() {
		return this.codBelfioreResidenza;
	}

	public void setCodBelfioreResidenza(String codBelfioreResidenza) {
		this.codBelfioreResidenza = codBelfioreResidenza;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getCodiceVia() {
		return this.codiceVia;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	public String getCogDenom() {
		return this.cogDenom;
	}

	public void setCogDenom(String cogDenom) {
		this.cogDenom = cogDenom;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDescComuneNascita() {
		return this.descComuneNascita;
	}

	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
	}

	public String getDescComuneResidenza() {
		return this.descComuneResidenza;
	}

	public void setDescComuneResidenza(String descComuneResidenza) {
		this.descComuneResidenza = descComuneResidenza;
	}

	public Date getDtCostitSoggetto() {
		return this.dtCostitSoggetto;
	}

	public void setDtCostitSoggetto(Date dtCostitSoggetto) {
		this.dtCostitSoggetto = dtCostitSoggetto;
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

	public Date getDtIscrArchivio() {
		return this.dtIscrArchivio;
	}

	public void setDtIscrArchivio(Date dtIscrArchivio) {
		this.dtIscrArchivio = dtIscrArchivio;
	}

	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	public Date getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(Date dtNascita) {
		this.dtNascita = dtNascita;
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

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartitaIva() {
		return this.partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
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

	public String getSedime() {
		return this.sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

}