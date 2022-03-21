package it.webred.ct.data.model.fornituraelettrica;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_ENEL_UTENZA database table.
 * 
 */
@Entity
@Table(name="SIT_ENEL_UTENZA")
@IndiceEntity(sorgente="10")
public class SitEnelUtenza implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;

	@Column(name="ANNO_RIFERIMENTO_DATI")
	private String annoRiferimentoDati;

	@Column(name="CAP_UBICAZIONE")
	private String capUbicazione;

	@Column(name="CODICE_ASSENZA_CATASTO")
	private String codiceAssenzaCatasto;

	@Column(name="CODICE_COMUNE_CATASTALE")
	private String codiceComuneCatastale;

	@Column(name="CODICE_FISCALE_EROGANTE")
	private String codiceFiscaleErogante;

	@Column(name="CODICE_MERCEOLOGICO")
	private String codiceMerceologico;

	@Column(name="CODICE_UTENZA")
	private String codiceUtenza;

	@Column(name="COMUNE_AMM_UBICAZIONE")
	private String comuneAmmUbicazione;

	@Column(name="COMUNE_CATASTALE")
	private String comuneCatastale;

	@Column(name="CTR_HASH")
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_PRIMA_ATTIVAZIONE")
	private Date dataPrimaAttivazione;

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

	@Column(name="ESTENSIONE_PARTICELLA")
	private String estensioneParticella;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	private String foglio;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_EXT_ENEL_UTENTE")
	private String idExtEnelUtente;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="INDIRIZZO_UBICAZIONE")
	private String indirizzoUbicazione;

	@Column(name="KWH_FATTURATI")
	private BigDecimal kwhFatturati;

	@Column(name="MESI_FATTURAZIONE")
	private BigDecimal mesiFatturazione;

	private String particella;

	private String processid;

	@Column(name="PROVINCIA_AMM_UBICAZIONE")
	private String provinciaAmmUbicazione;

	@Column(name="SEGNO_IMPORTO")
	private String segnoImporto;

	private String sezione;

	@Column(name="SPESA_ANNUA")
	private BigDecimal spesaAnnua;

	private String subalterno;

	@Column(name="TIPO_PARTICELLA")
	private String tipoParticella;

	@Column(name="TIPO_UNITA")
	private String tipoUnita;

	@Column(name="TIPO_UTENZA")
	private String tipoUtenza;

	@Column(name="TIPOLOGIA_FORNITURA")
	private String tipologiaFornitura;

    public SitEnelUtenza() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnnoRiferimentoDati() {
		return this.annoRiferimentoDati;
	}

	public void setAnnoRiferimentoDati(String annoRiferimentoDati) {
		this.annoRiferimentoDati = annoRiferimentoDati;
	}

	public String getCapUbicazione() {
		return this.capUbicazione;
	}

	public void setCapUbicazione(String capUbicazione) {
		this.capUbicazione = capUbicazione;
	}

	public String getCodiceAssenzaCatasto() {
		return this.codiceAssenzaCatasto;
	}

	public void setCodiceAssenzaCatasto(String codiceAssenzaCatasto) {
		this.codiceAssenzaCatasto = codiceAssenzaCatasto;
	}

	public String getCodiceComuneCatastale() {
		return this.codiceComuneCatastale;
	}

	public void setCodiceComuneCatastale(String codiceComuneCatastale) {
		this.codiceComuneCatastale = codiceComuneCatastale;
	}

	public String getCodiceFiscaleErogante() {
		return this.codiceFiscaleErogante;
	}

	public void setCodiceFiscaleErogante(String codiceFiscaleErogante) {
		this.codiceFiscaleErogante = codiceFiscaleErogante;
	}

	public String getCodiceMerceologico() {
		return this.codiceMerceologico;
	}

	public void setCodiceMerceologico(String codiceMerceologico) {
		this.codiceMerceologico = codiceMerceologico;
	}

	public String getCodiceUtenza() {
		return this.codiceUtenza;
	}

	public void setCodiceUtenza(String codiceUtenza) {
		this.codiceUtenza = codiceUtenza;
	}

	public String getComuneAmmUbicazione() {
		return this.comuneAmmUbicazione;
	}

	public void setComuneAmmUbicazione(String comuneAmmUbicazione) {
		this.comuneAmmUbicazione = comuneAmmUbicazione;
	}

	public String getComuneCatastale() {
		return this.comuneCatastale;
	}

	public void setComuneCatastale(String comuneCatastale) {
		this.comuneCatastale = comuneCatastale;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataPrimaAttivazione() {
		return this.dataPrimaAttivazione;
	}

	public void setDataPrimaAttivazione(Date dataPrimaAttivazione) {
		this.dataPrimaAttivazione = dataPrimaAttivazione;
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

	public String getEstensioneParticella() {
		return this.estensioneParticella;
	}

	public void setEstensioneParticella(String estensioneParticella) {
		this.estensioneParticella = estensioneParticella;
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

	public String getFoglio() {
		return this.foglio;
	}

	public void setFoglio(String foglio) {
		this.foglio = foglio;
	}

	public String getIdExt() {
		return this.idExt;
	}

	public void setIdExt(String idExt) {
		this.idExt = idExt;
	}

	public String getIdExtEnelUtente() {
		return this.idExtEnelUtente;
	}

	public void setIdExtEnelUtente(String idExtEnelUtente) {
		this.idExtEnelUtente = idExtEnelUtente;
	}

	public String getIdOrig() {
		return this.idOrig;
	}

	public void setIdOrig(String idOrig) {
		this.idOrig = idOrig;
	}

	public String getIndirizzoUbicazione() {
		return this.indirizzoUbicazione;
	}

	public void setIndirizzoUbicazione(String indirizzoUbicazione) {
		this.indirizzoUbicazione = indirizzoUbicazione;
	}

	public BigDecimal getKwhFatturati() {
		return this.kwhFatturati;
	}

	public void setKwhFatturati(BigDecimal kwhFatturati) {
		this.kwhFatturati = kwhFatturati;
	}

	public BigDecimal getMesiFatturazione() {
		return this.mesiFatturazione;
	}

	public void setMesiFatturazione(BigDecimal mesiFatturazione) {
		this.mesiFatturazione = mesiFatturazione;
	}

	public String getParticella() {
		return this.particella;
	}

	public void setParticella(String particella) {
		this.particella = particella;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProvinciaAmmUbicazione() {
		return this.provinciaAmmUbicazione;
	}

	public void setProvinciaAmmUbicazione(String provinciaAmmUbicazione) {
		this.provinciaAmmUbicazione = provinciaAmmUbicazione;
	}

	public String getSegnoImporto() {
		return this.segnoImporto;
	}

	public void setSegnoImporto(String segnoImporto) {
		this.segnoImporto = segnoImporto;
	}

	public String getSezione() {
		return this.sezione;
	}

	public void setSezione(String sezione) {
		this.sezione = sezione;
	}

	public BigDecimal getSpesaAnnua() {
		return this.spesaAnnua;
	}

	public void setSpesaAnnua(BigDecimal spesaAnnua) {
		this.spesaAnnua = spesaAnnua;
	}

	public String getSubalterno() {
		return this.subalterno;
	}

	public void setSubalterno(String subalterno) {
		this.subalterno = subalterno;
	}

	public String getTipoParticella() {
		return this.tipoParticella;
	}

	public void setTipoParticella(String tipoParticella) {
		this.tipoParticella = tipoParticella;
	}

	public String getTipoUnita() {
		return this.tipoUnita;
	}

	public void setTipoUnita(String tipoUnita) {
		this.tipoUnita = tipoUnita;
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