package it.webred.ct.data.model.gas;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_U_GAS database table.
 * 
 */
@Entity
@Table(name="SIT_U_GAS")
@IndiceEntity(sorgente="12")
public class SitUGas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ANNO_RIFERIMENTO")
	private String annoRiferimento;

	@Column(name="CAP_UTENZA")
	private String capUtenza;

	@Column(name="CF_EROGANTE")
	private String cfErogante;

	@Column(name="CF_TITOLARE_UTENZA")
	private String cfTitolareUtenza;

	@Column(name="CODICE_CATASTALE_UTENZA")
	private String codiceCatastaleUtenza;

	@Column(name="COGNOME_UTENTE")
	private String cognomeUtente;

	// Chiave surrogata		
	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DATA_NASCITA")
	private String dataNascita;

	@Column(name="DESC_COMUNE_NASCITA")
	private String descComuneNascita;

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

	@Id
	@IndiceKey(pos="1")
	private String id;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IDENTIFICATIVO_UTENZA")
	private String identificativoUtenza;

	@Column(name="INDIRIZZO_UTENZA")
	private String indirizzoUtenza;

	@Column(name="N_MESI_FATTURAZIONE")
	private String nMesiFatturazione;

	@Column(name="NOME_UTENTE")
	private String nomeUtente;

	private String processid;

	private String provenienza;

	@Column(name="RAGIONE_SOCIALE")
	private String ragioneSociale;

	private String sesso;

	@Column(name="SIGLA_PROV_NASCITA")
	private String siglaProvNascita;

	@Column(name="SPESA_CONSUMO_NETTO_IVA")
	private String spesaConsumoNettoIva;

	@Column(name="TIPO_SOGGETTO")
	private String tipoSoggetto;

	@Column(name="TIPO_UTENZA")
	private String tipoUtenza;

	@Column(name="TIPOLOGIA_FORNITURA")
	private String tipologiaFornitura;

    public SitUGas() {
    }

	public String getAnnoRiferimento() {
		return this.annoRiferimento;
	}

	public void setAnnoRiferimento(String annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public String getCapUtenza() {
		return this.capUtenza;
	}

	public void setCapUtenza(String capUtenza) {
		this.capUtenza = capUtenza;
	}

	public String getCfErogante() {
		return this.cfErogante;
	}

	public void setCfErogante(String cfErogante) {
		this.cfErogante = cfErogante;
	}

	public String getCfTitolareUtenza() {
		return this.cfTitolareUtenza;
	}

	public void setCfTitolareUtenza(String cfTitolareUtenza) {
		this.cfTitolareUtenza = cfTitolareUtenza;
	}

	public String getCodiceCatastaleUtenza() {
		return this.codiceCatastaleUtenza;
	}

	public void setCodiceCatastaleUtenza(String codiceCatastaleUtenza) {
		this.codiceCatastaleUtenza = codiceCatastaleUtenza;
	}

	public String getCognomeUtente() {
		return this.cognomeUtente;
	}

	public void setCognomeUtente(String cognomeUtente) {
		this.cognomeUtente = cognomeUtente;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public String getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(String dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getDescComuneNascita() {
		return this.descComuneNascita;
	}

	public void setDescComuneNascita(String descComuneNascita) {
		this.descComuneNascita = descComuneNascita;
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

	public String getIdentificativoUtenza() {
		return this.identificativoUtenza;
	}

	public void setIdentificativoUtenza(String identificativoUtenza) {
		this.identificativoUtenza = identificativoUtenza;
	}

	public String getIndirizzoUtenza() {
		return this.indirizzoUtenza;
	}

	public void setIndirizzoUtenza(String indirizzoUtenza) {
		this.indirizzoUtenza = indirizzoUtenza;
	}

	public String getNMesiFatturazione() {
		return this.nMesiFatturazione;
	}

	public void setNMesiFatturazione(String nMesiFatturazione) {
		this.nMesiFatturazione = nMesiFatturazione;
	}

	public String getNomeUtente() {
		return this.nomeUtente;
	}

	public void setNomeUtente(String nomeUtente) {
		this.nomeUtente = nomeUtente;
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

	public String getRagioneSociale() {
		return this.ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getSiglaProvNascita() {
		return this.siglaProvNascita;
	}

	public void setSiglaProvNascita(String siglaProvNascita) {
		this.siglaProvNascita = siglaProvNascita;
	}

	public String getSpesaConsumoNettoIva() {
		return this.spesaConsumoNettoIva;
	}

	public void setSpesaConsumoNettoIva(String spesaConsumoNettoIva) {
		this.spesaConsumoNettoIva = spesaConsumoNettoIva;
	}

	public String getTipoSoggetto() {
		return this.tipoSoggetto;
	}

	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}

	public String getTipoUtenza() {
		return this.tipoUtenza;
	}

	public void setTipoUtenza(String tipoUtenza) {
		this.tipoUtenza = tipoUtenza;
	}

	public String getTipologiaFornitura() {
		return this.tipologiaFornitura;
	}

	public void setTipologiaFornitura(String tipologiaFornitura) {
		this.tipologiaFornitura = tipologiaFornitura;
	}

}