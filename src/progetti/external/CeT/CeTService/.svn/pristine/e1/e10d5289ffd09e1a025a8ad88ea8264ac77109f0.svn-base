package it.webred.ct.data.model.cosap;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the SIT_T_COSAP_TASSA database table.
 * 
 */
@Entity
@Table(name="SIT_T_COSAP_TASSA")
@IndiceEntity(sorgente="14")
public class SitTCosapTassa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@IndiceKey(pos="1")
	private String id;

	@Column(name="ANNO_CONTABILE_DOCUMENTO")
	private String annoContabileDocumento;

	@Column(name="ANNO_DOCUMENTO")
	private String annoDocumento;

	private String civico;

	@Column(name="COD_UNIVOCO_CANONE")
	private String codUnivocoCanone;

	@Column(name="CODICE_ESENZIONE")
	private String codiceEsenzione;

	@Column(name="CODICE_IMMOBILE")
	private String codiceImmobile;

	@Column(name="CODICE_VIA")
	private String codiceVia;

	@Column(name="CTR_HASH")
	private String ctrHash;

	private String descrizione;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FIN_SCONTO")
	private Date dtFinSconto;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FIN_VALIDITA")
	private Date dtFinValidita;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FIN_VALIDITA_TARIFFA")
	private Date dtFinValiditaTariffa;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_DATO")
	private Date dtFineDato;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_FINE_VAL")
	private Date dtFineVal;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INI_SCONTO")
	private Date dtIniSconto;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INI_VALIDITA")
	private Date dtIniValidita;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_INI_VALIDITA_TARIFFA")
	private Date dtIniValiditaTariffa;

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

    @Temporal( TemporalType.DATE)
	@Column(name="DT_PROTOCOLLO")
	private Date dtProtocollo;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_RICHIESTA")
	private Date dtRichiesta;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	private BigDecimal foglio;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_CONTRIB")
	private String idExtContrib;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IMPORTO_CANONE")
	private BigDecimal importoCanone;

	private String indirizzo;

	private String note;

	@Column(name="NUMERO_DOCUMENTO")
	private String numeroDocumento;

	@Column(name="NUMERO_PROTOCOLLO")
	private String numeroProtocollo;

	private String particella;

	@Column(name="PERCENTUALE_SCONTO")
	private BigDecimal percentualeSconto;

	private String processid;

	private String provenienza;

	private BigDecimal quantita;

	@Column(name="SCONTO_ASSOLUTO")
	private BigDecimal scontoAssoluto;

	private String sedime;

	@Column(name="STATO_DOCUMENTO")
	private String statoDocumento;

	private BigDecimal subalterno;

	@Column(name="TARIFFA_PER_UNITA")
	private BigDecimal tariffaPerUnita;

	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	@Column(name="TIPO_OCCUPAZIONE")
	private String tipoOccupazione;

	@Column(name="UNITA_MISURA_QUANTITA")
	private String unitaMisuraQuantita;

	private String zona;

	@Transient
	private String tariffaPerUnitaStr;
	
	@Transient
	private String importoCanoneStr;
	
	@Transient
	private String dtIniValiditaStr;
	
	@Transient
	private String dtFinValiditaStr;

	@Transient
	private String dtRichiestaStr;
	
	@Transient
	private String dtIniValiditaTariffaStr;
	
	@Transient
	private String dtFinValiditaTariffaStr;
	
    public SitTCosapTassa() {
    }

	public String getAnnoContabileDocumento() {
		return this.annoContabileDocumento;
	}

	public void setAnnoContabileDocumento(String annoContabileDocumento) {
		this.annoContabileDocumento = annoContabileDocumento;
	}

	public String getAnnoDocumento() {
		return this.annoDocumento;
	}

	public void setAnnoDocumento(String annoDocumento) {
		this.annoDocumento = annoDocumento;
	}

	public String getCivico() {
		return this.civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodUnivocoCanone() {
		return this.codUnivocoCanone;
	}

	public void setCodUnivocoCanone(String codUnivocoCanone) {
		this.codUnivocoCanone = codUnivocoCanone;
	}

	public String getCodiceEsenzione() {
		return this.codiceEsenzione;
	}

	public void setCodiceEsenzione(String codiceEsenzione) {
		this.codiceEsenzione = codiceEsenzione;
	}

	public String getCodiceImmobile() {
		return this.codiceImmobile;
	}

	public void setCodiceImmobile(String codiceImmobile) {
		this.codiceImmobile = codiceImmobile;
	}

	public String getCodiceVia() {
		return this.codiceVia;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
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

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public Date getDtFinSconto() {
		return this.dtFinSconto;
	}

	public void setDtFinSconto(Date dtFinSconto) {
		this.dtFinSconto = dtFinSconto;
	}

	public Date getDtFinValidita() {
		return this.dtFinValidita;
	}

	public void setDtFinValidita(Date dtFinValidita) {
		this.dtFinValidita = dtFinValidita;
	}

	public Date getDtFinValiditaTariffa() {
		return this.dtFinValiditaTariffa;
	}

	public void setDtFinValiditaTariffa(Date dtFinValiditaTariffa) {
		this.dtFinValiditaTariffa = dtFinValiditaTariffa;
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

	public Date getDtIniSconto() {
		return this.dtIniSconto;
	}

	public void setDtIniSconto(Date dtIniSconto) {
		this.dtIniSconto = dtIniSconto;
	}

	public Date getDtIniValidita() {
		return this.dtIniValidita;
	}

	public void setDtIniValidita(Date dtIniValidita) {
		this.dtIniValidita = dtIniValidita;
	}

	public Date getDtIniValiditaTariffa() {
		return this.dtIniValiditaTariffa;
	}

	public void setDtIniValiditaTariffa(Date dtIniValiditaTariffa) {
		this.dtIniValiditaTariffa = dtIniValiditaTariffa;
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

	public Date getDtProtocollo() {
		return this.dtProtocollo;
	}

	public void setDtProtocollo(Date dtProtocollo) {
		this.dtProtocollo = dtProtocollo;
	}

	public Date getDtRichiesta() {
		return this.dtRichiesta;
	}

	public void setDtRichiesta(Date dtRichiesta) {
		this.dtRichiesta = dtRichiesta;
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

	public BigDecimal getFoglio() {
		return this.foglio;
	}

	public void setFoglio(BigDecimal foglio) {
		this.foglio = foglio;
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

	public String getIdExtContrib() {
		return this.idExtContrib;
	}

	public void setIdExtContrib(String idExtContrib) {
		this.idExtContrib = idExtContrib;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public BigDecimal getImportoCanone() {
		return this.importoCanone;
	}

	public void setImportoCanone(BigDecimal importoCanone) {
		this.importoCanone = importoCanone;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNumeroProtocollo() {
		return this.numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public BigDecimal getPercentualeSconto() {
		return this.percentualeSconto;
	}

	public void setPercentualeSconto(BigDecimal percentualeSconto) {
		this.percentualeSconto = percentualeSconto;
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

	public BigDecimal getQuantita() {
		return this.quantita;
	}

	public void setQuantita(BigDecimal quantita) {
		this.quantita = quantita;
	}

	public BigDecimal getScontoAssoluto() {
		return this.scontoAssoluto;
	}

	public void setScontoAssoluto(BigDecimal scontoAssoluto) {
		this.scontoAssoluto = scontoAssoluto;
	}

	public String getSedime() {
		return this.sedime;
	}

	public void setSedime(String sedime) {
		this.sedime = sedime;
	}

	public String getStatoDocumento() {
		return this.statoDocumento;
	}

	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}

	public BigDecimal getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(BigDecimal subalterno) {
		this.subalterno = subalterno;
	}

	public BigDecimal getTariffaPerUnita() {
		return this.tariffaPerUnita;
	}

	public void setTariffaPerUnita(BigDecimal tariffaPerUnita) {
		this.tariffaPerUnita = tariffaPerUnita;
	}

	public String getTipoDocumento() {
		return this.tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getTipoOccupazione() {
		return this.tipoOccupazione;
	}

	public void setTipoOccupazione(String tipoOccupazione) {
		this.tipoOccupazione = tipoOccupazione;
	}

	public String getUnitaMisuraQuantita() {
		return this.unitaMisuraQuantita;
	}

	public void setUnitaMisuraQuantita(String unitaMisuraQuantita) {
		this.unitaMisuraQuantita = unitaMisuraQuantita;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getTariffaPerUnitaStr() {
		return tariffaPerUnitaStr;
	}

	public void setTariffaPerUnitaStr(String tariffaPerUnitaStr) {
		this.tariffaPerUnitaStr = tariffaPerUnitaStr;
	}

	public String getImportoCanoneStr() {
		return importoCanoneStr;
	}

	public void setImportoCanoneStr(String importoCanoneStr) {
		this.importoCanoneStr = importoCanoneStr;
	}

	public String getDtIniValiditaStr() {
		
		
		return dtIniValiditaStr;
	}
	
	
	public String getDtIniValiditaStr2() {
		
		String dtIniValiditaStr="";
		DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
		if (dtIniValidita!= null)
			dtIniValiditaStr=df.format(dtIniValidita);
		
		return dtIniValiditaStr;
	}

	public void setDtIniValiditaStr(String dtIniValiditaStr) {
		this.dtIniValiditaStr = dtIniValiditaStr;
	}

	public String getDtRichiestaStr() {
		return dtRichiestaStr;
	}

	public void setDtRichiestaStr(String dtRichiestaStr) {
		this.dtRichiestaStr = dtRichiestaStr;
	}

	public String getDtIniValiditaTariffaStr() {
		return dtIniValiditaTariffaStr;
	}
	
	

	public void setDtIniValiditaTariffaStr(String dtIniValiditaTariffaStr) {
		this.dtIniValiditaTariffaStr = dtIniValiditaTariffaStr;
	}

	public String getDtFinValiditaTariffaStr() {
		return dtFinValiditaTariffaStr;
	}

	public void setDtFinValiditaTariffaStr(String dtFinValiditaTariffaStr) {
		this.dtFinValiditaTariffaStr = dtFinValiditaTariffaStr;
	}

	public String getDtFinValiditaStr() {
		
		String dtFinValiditaStr="";
		DateFormat df= new SimpleDateFormat("dd/MM/yyyy");
		if (dtFinValidita!= null)
			dtFinValiditaStr=df.format(dtFinValidita);
		
		return dtFinValiditaStr;
	}

	public void setDtFinValiditaStr(String dtFinValiditaStr) {
		this.dtFinValiditaStr = dtFinValiditaStr;
	}

}