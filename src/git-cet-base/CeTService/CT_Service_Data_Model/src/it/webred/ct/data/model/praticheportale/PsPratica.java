package it.webred.ct.data.model.praticheportale;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PRATICA database table.
 * 
 */
@Entity
@Table(name="PS_PRATICA")
public class PsPratica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="ALERTING_STATO_PRATICA")
	private BigDecimal alertingStatoPratica;

    @Temporal( TemporalType.DATE)
	@Column(name="ALERTING_TIME")
	private Date alertingTime;

	@Column(name="ANAGRAFICA_FRUITORE_ID")
	private BigDecimal anagraficaFruitoreId;

	@Column(name="ANAGRAFICA_RICHIEDENTE_ID")
	private BigDecimal anagraficaRichiedenteId;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_CREAZIONE")
	private Date dataCreazione;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_MODIFICA")
	private Date dataModifica;

    @Temporal( TemporalType.DATE)
	@Column(name="DATA_RICHIESTA")
	private Date dataRichiesta;

    @Lob()
	@Column(name="DATI_PRATICA_XML")
	private String datiPraticaXml;

	@Column(name="ESITO_PAGAMENTO_ID")
	private BigDecimal esitoPagamentoId;

	@Column(name="ID_STATO")
	private BigDecimal idStato;

	@Column(name="NOME_STATO")
	private String nomeStato;

	@Column(name="NOTE_STATO")
	private String noteStato;

	@Column(name="PER_ALTRA_PERSONA")
	private BigDecimal perAltraPersona;

	@Column(name="SOTTO_TIPO_SERVIZIO")
	private String sottoTipoServizio;

	@Column(name="STATO_SCAMBIO_BUSTA_DESC")
	private String statoScambioBustaDesc;

	@Column(name="STATO_SCAMBIO_BUSTA_ID")
	private BigDecimal statoScambioBustaId;

    @Temporal( TemporalType.DATE)
	@Column(name="STATO_SCAMBIO_BUSTA_TIME")
	private Date statoScambioBustaTime;

	@Column(name="TIPO_CONSEGNA_ALLEGATI")
	private String tipoConsegnaAllegati;

	@Column(name="TIPO_CONSEGNA_RISULTATI")
	private String tipoConsegnaRisultati;

	@Column(name="TIPO_SERVIZIO")
	private String tipoServizio;

	@Column(name="UTENTE_CREAZIONE_ID")
	private BigDecimal utenteCreazioneId;

	@Column(name="UTENTE_DESTINATARIO")
	private String utenteDestinatario;

	@Column(name="UTENTE_MODIFICA_ID")
	private BigDecimal utenteModificaId;

    public PsPratica() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getAlertingStatoPratica() {
		return this.alertingStatoPratica;
	}

	public void setAlertingStatoPratica(BigDecimal alertingStatoPratica) {
		this.alertingStatoPratica = alertingStatoPratica;
	}

	public Date getAlertingTime() {
		return this.alertingTime;
	}

	public void setAlertingTime(Date alertingTime) {
		this.alertingTime = alertingTime;
	}

	public BigDecimal getAnagraficaFruitoreId() {
		return this.anagraficaFruitoreId;
	}

	public void setAnagraficaFruitoreId(BigDecimal anagraficaFruitoreId) {
		this.anagraficaFruitoreId = anagraficaFruitoreId;
	}

	public BigDecimal getAnagraficaRichiedenteId() {
		return this.anagraficaRichiedenteId;
	}

	public void setAnagraficaRichiedenteId(BigDecimal anagraficaRichiedenteId) {
		this.anagraficaRichiedenteId = anagraficaRichiedenteId;
	}

	public Date getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Date getDataModifica() {
		return this.dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Date getDataRichiesta() {
		return this.dataRichiesta;
	}

	public void setDataRichiesta(Date dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public String getDatiPraticaXml() {
		return this.datiPraticaXml;
	}

	public void setDatiPraticaXml(String datiPraticaXml) {
		this.datiPraticaXml = datiPraticaXml;
	}

	public BigDecimal getEsitoPagamentoId() {
		return this.esitoPagamentoId;
	}

	public void setEsitoPagamentoId(BigDecimal esitoPagamentoId) {
		this.esitoPagamentoId = esitoPagamentoId;
	}

	public BigDecimal getIdStato() {
		return this.idStato;
	}

	public void setIdStato(BigDecimal idStato) {
		this.idStato = idStato;
	}

	public String getNomeStato() {
		return this.nomeStato;
	}

	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}

	public String getNoteStato() {
		return this.noteStato;
	}

	public void setNoteStato(String noteStato) {
		this.noteStato = noteStato;
	}

	public BigDecimal getPerAltraPersona() {
		return this.perAltraPersona;
	}

	public void setPerAltraPersona(BigDecimal perAltraPersona) {
		this.perAltraPersona = perAltraPersona;
	}

	public String getSottoTipoServizio() {
		return this.sottoTipoServizio;
	}

	public void setSottoTipoServizio(String sottoTipoServizio) {
		this.sottoTipoServizio = sottoTipoServizio;
	}

	public String getStatoScambioBustaDesc() {
		return this.statoScambioBustaDesc;
	}

	public void setStatoScambioBustaDesc(String statoScambioBustaDesc) {
		this.statoScambioBustaDesc = statoScambioBustaDesc;
	}

	public BigDecimal getStatoScambioBustaId() {
		return this.statoScambioBustaId;
	}

	public void setStatoScambioBustaId(BigDecimal statoScambioBustaId) {
		this.statoScambioBustaId = statoScambioBustaId;
	}

	public Date getStatoScambioBustaTime() {
		return this.statoScambioBustaTime;
	}

	public void setStatoScambioBustaTime(Date statoScambioBustaTime) {
		this.statoScambioBustaTime = statoScambioBustaTime;
	}

	public String getTipoConsegnaAllegati() {
		return this.tipoConsegnaAllegati;
	}

	public void setTipoConsegnaAllegati(String tipoConsegnaAllegati) {
		this.tipoConsegnaAllegati = tipoConsegnaAllegati;
	}

	public String getTipoConsegnaRisultati() {
		return this.tipoConsegnaRisultati;
	}

	public void setTipoConsegnaRisultati(String tipoConsegnaRisultati) {
		this.tipoConsegnaRisultati = tipoConsegnaRisultati;
	}

	public String getTipoServizio() {
		return this.tipoServizio;
	}

	public void setTipoServizio(String tipoServizio) {
		this.tipoServizio = tipoServizio;
	}

	public BigDecimal getUtenteCreazioneId() {
		return this.utenteCreazioneId;
	}

	public void setUtenteCreazioneId(BigDecimal utenteCreazioneId) {
		this.utenteCreazioneId = utenteCreazioneId;
	}

	public String getUtenteDestinatario() {
		return this.utenteDestinatario;
	}

	public void setUtenteDestinatario(String utenteDestinatario) {
		this.utenteDestinatario = utenteDestinatario;
	}

	public BigDecimal getUtenteModificaId() {
		return this.utenteModificaId;
	}

	public void setUtenteModificaId(BigDecimal utenteModificaId) {
		this.utenteModificaId = utenteModificaId;
	}

}