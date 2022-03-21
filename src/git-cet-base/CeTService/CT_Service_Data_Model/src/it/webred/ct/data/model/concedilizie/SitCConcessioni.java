package it.webred.ct.data.model.concedilizie;

import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the SIT_C_CONCESSIONI database table.
 * 
 */
@Entity
@Table(name="SIT_C_CONCESSIONI")
@IndiceEntity(sorgente="3")
public class SitCConcessioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@IndiceKey(pos="1")
	private String id;

	@Column(name="CONCESSIONE_NUMERO")
	private String concessioneNumero;

	@Column(name="CTR_HASH")
	private String ctrHash;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_FINE_LAVORI")
	private Date dataFineLavori;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_INIZIO_LAVORI")
	private Date dataInizioLavori;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_PROROGA_LAVORI")
	private Date dataProrogaLavori;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_RILASCIO")
	private Date dataRilascio;

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

	private String esito;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	private String oggetto;

	@Column(name="POSIZIONE_CODICE")
	private String posizioneCodice;

    @Temporal( TemporalType.DATE)
	@Column(name="POSIZIONE_DATA")
	private Date posizioneData;

	@Column(name="POSIZIONE_DESCRIZIONE")
	private String posizioneDescrizione;

	private String procedimento;

	private String processid;

	@Column(name="PROGRESSIVO_ANNO")
	private String progressivoAnno;

	@Column(name="PROGRESSIVO_NUMERO")
	private String progressivoNumero;

    @Temporal( TemporalType.DATE)
	@Column(name="PROTOCOLLO_DATA")
	private Date protocolloData;

	@Column(name="PROTOCOLLO_NUMERO")
	private String protocolloNumero;

	private String provenienza;

	@Column(name="TIPO_INTERVENTO")
	private String tipoIntervento;

	private String zona;

    public SitCConcessioni() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConcessioneNumero() {
		return this.concessioneNumero;
	}

	public void setConcessioneNumero(String concessioneNumero) {
		this.concessioneNumero = concessioneNumero;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public Date getDataFineLavori() {
		return this.dataFineLavori;
	}

	public void setDataFineLavori(Date dataFineLavori) {
		this.dataFineLavori = dataFineLavori;
	}

	public Date getDataInizioLavori() {
		return this.dataInizioLavori;
	}

	public void setDataInizioLavori(Date dataInizioLavori) {
		this.dataInizioLavori = dataInizioLavori;
	}

	public Date getDataProrogaLavori() {
		return this.dataProrogaLavori;
	}

	public void setDataProrogaLavori(Date dataProrogaLavori) {
		this.dataProrogaLavori = dataProrogaLavori;
	}

	public Date getDataRilascio() {
		return this.dataRilascio;
	}

	public void setDataRilascio(Date dataRilascio) {
		this.dataRilascio = dataRilascio;
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

	public String getEsito() {
		return this.esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
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

	public String getOggetto() {
		return this.oggetto;
	}

	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}

	public String getPosizioneCodice() {
		return this.posizioneCodice;
	}

	public void setPosizioneCodice(String posizioneCodice) {
		this.posizioneCodice = posizioneCodice;
	}

	public Date getPosizioneData() {
		return this.posizioneData;
	}

	public void setPosizioneData(Date posizioneData) {
		this.posizioneData = posizioneData;
	}

	public String getPosizioneDescrizione() {
		return this.posizioneDescrizione;
	}

	public void setPosizioneDescrizione(String posizioneDescrizione) {
		this.posizioneDescrizione = posizioneDescrizione;
	}

	public String getProcedimento() {
		return this.procedimento;
	}

	public void setProcedimento(String procedimento) {
		this.procedimento = procedimento;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoAnno() {
		return this.progressivoAnno;
	}

	public void setProgressivoAnno(String progressivoAnno) {
		this.progressivoAnno = progressivoAnno;
	}

	public String getProgressivoNumero() {
		return this.progressivoNumero;
	}

	public void setProgressivoNumero(String progressivoNumero) {
		this.progressivoNumero = progressivoNumero;
	}

	public Date getProtocolloData() {
		return this.protocolloData;
	}

	public void setProtocolloData(Date protocolloData) {
		this.protocolloData = protocolloData;
	}

	public String getProtocolloNumero() {
		return this.protocolloNumero;
	}

	public void setProtocolloNumero(String protocolloNumero) {
		this.protocolloNumero = protocolloNumero;
	}

	public String getProvenienza() {
		return this.provenienza;
	}

	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}

	public String getTipoIntervento() {
		return this.tipoIntervento;
	}

	public void setTipoIntervento(String tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public String getZona() {
		return this.zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

}