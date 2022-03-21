package it.webred.ct.data.model.traffico;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the SIT_TRFF_MULTE database table.
 * 
 */
@Entity
@Table(name="SIT_TRFF_MULTE")
public class SitTrffMulte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="ANNO_VERBALE")
	private String annoVerbale;

	@Column(name="COD_FISC")
	private String codFisc;

	@Column(name="CODICE_PERSONA")
	private String codicePersona;

	private String cognome;

	@Column(name="COMUNE_ENTE")
	private String comuneEnte;

	@Column(name="CTR_HASH")
	private String ctrHash;

	@Column(name="DATA_MULTA")
	private BigDecimal dataMulta;

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

	@Column(name="DT_NASCITA")
	private String dtNascita;

	@Column(name="DT_PAGAMENTO")
	private BigDecimal dtPagamento;

	@Column(name="DT_RILASCIO_PATENTE")
	private BigDecimal dtRilascioPatente;

	@Column(name="DT_SCADENZA_PAGAM")
	private BigDecimal dtScadenzaPagam;

	@Column(name="ESTREMI_PAGAMENTO")
	private String estremiPagamento;

	@Column(name="FK_ENTE_SORGENTE")
	private BigDecimal fkEnteSorgente;

	@Column(name="FLAG_DT_VAL_DATO")
	private BigDecimal flagDtValDato;

	@Column(name="FLAG_PAGAMENTO")
	private String flagPagamento;

	@Column(name="ID_EXT")
	private String idExt;

	@Column(name="ID_ORIG")
	private String idOrig;

	@Column(name="IMPORTO_DOVUTO")
	private BigDecimal importoDovuto;

	@Column(name="IMPORTO_MULTA")
	private BigDecimal importoMulta;

	@Column(name="IMPORTO_PAGATO")
	private BigDecimal importoPagato;
	
	@Column(name="IMPORTO_SCONTATO")
	private BigDecimal importoScontato;

	@Column(name="INDIRIZZO_RESIDENZA")
	private String indirizzoResidenza;

	@Column(name="LUOGO_INFRAZIONE")
	private String luogoInfrazione;

	@Column(name="LUOGO_NASCITA")
	private String luogoNascita;

	@Column(name="LUOGO_RESIDENZA")
	private String luogoResidenza;

	private String marca;

	private String modello;

	private String nome;

	private String note;

	@Column(name="NR_PATENTE")
	private String nrPatente;

	@Column(name="NR_VERBALE")
	private String nrVerbale;

	private String processid;

	@Column(name="PROV_RILASCIO_PATENTE")
	private String provRilascioPatente;

	@Column(name="SERIE_VERBALE")
	private String serieVerbale;

	@Column(name="SISTEMA_PAGAMENTO")
	private String sistemaPagamento;

	private String targa;

	@Column(name="TIPO_ENTE")
	private String tipoEnte;

	//uni-directional many-to-one association to SitTrffMulteStato
    @ManyToOne
	@JoinColumn(name="STATO_VERBALE")
	private SitTrffMulteStato sitTrffMulteStato;

	//uni-directional many-to-one association to SitTrffMulteTipo
    @ManyToOne
	@JoinColumn(name="TIPO_VERBALE")
	private SitTrffMulteTipo sitTrffMulteTipo;

    public SitTrffMulte() {
    }

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnnoVerbale() {
		return this.annoVerbale;
	}

	public void setAnnoVerbale(String annoVerbale) {
		this.annoVerbale = annoVerbale;
	}

	public String getCodFisc() {
		return this.codFisc;
	}

	public void setCodFisc(String codFisc) {
		this.codFisc = codFisc;
	}

	public String getCodicePersona() {
		return this.codicePersona;
	}

	public void setCodicePersona(String codicePersona) {
		this.codicePersona = codicePersona;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComuneEnte() {
		return this.comuneEnte;
	}

	public void setComuneEnte(String comuneEnte) {
		this.comuneEnte = comuneEnte;
	}

	public String getCtrHash() {
		return this.ctrHash;
	}

	public void setCtrHash(String ctrHash) {
		this.ctrHash = ctrHash;
	}

	public BigDecimal getDataMulta() {
		return this.dataMulta;
	}
	
	public String getDataMultaFormatted() {
		String ret = null;
		SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yyyy");
		if(this.dataMulta != null){
			try {
				Date d = sdfFrom.parse(this.dataMulta.toString());
				ret = sdfTo.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public void setDataMulta(BigDecimal dataMulta) {
		this.dataMulta = dataMulta;
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

	public String getDtNascita() {
		return this.dtNascita;
	}

	public void setDtNascita(String dtNascita) {
		this.dtNascita = dtNascita;
	}

	public BigDecimal getDtPagamento() {
		return this.dtPagamento;
	}

	public String getDtPagamentoFormatted() {
		String ret = null;
		SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yyyy");
		if(this.dtPagamento != null){
			try {
				Date d = sdfFrom.parse(this.dtPagamento.toString());
				ret = sdfTo.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	public void setDtPagamento(BigDecimal dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public BigDecimal getDtRilascioPatente() {
		return this.dtRilascioPatente;
	}

	public void setDtRilascioPatente(BigDecimal dtRilascioPatente) {
		this.dtRilascioPatente = dtRilascioPatente;
	}

	public BigDecimal getDtScadenzaPagam() {
		return this.dtScadenzaPagam;
	}
	
	public String getDtScadenzaPagamFormatted() {
		String ret = null;
		SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yyyy");
		if(this.dtScadenzaPagam != null){
			try {
				Date d = sdfFrom.parse(this.dtScadenzaPagam.toString());
				ret = sdfTo.format(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	public void setDtScadenzaPagam(BigDecimal dtScadenzaPagam) {
		this.dtScadenzaPagam = dtScadenzaPagam;
	}

	public String getEstremiPagamento() {
		return this.estremiPagamento;
	}

	public void setEstremiPagamento(String estremiPagamento) {
		this.estremiPagamento = estremiPagamento;
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

	public String getFlagPagamento() {
		return this.flagPagamento;
	}

	public void setFlagPagamento(String flagPagamento) {
		this.flagPagamento = flagPagamento;
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

	public BigDecimal getImportoDovuto() {
		return this.importoDovuto;
	}

	public void setImportoDovuto(BigDecimal importoDovuto) {
		this.importoDovuto = importoDovuto;
	}

	public BigDecimal getImportoMulta() {
		return this.importoMulta;
	}

	public void setImportoMulta(BigDecimal importoMulta) {
		this.importoMulta = importoMulta;
	}

	public BigDecimal getImportoPagato() {
		return this.importoPagato;
	}

	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}

	public String getIndirizzoResidenza() {
		return this.indirizzoResidenza;
	}

	public void setIndirizzoResidenza(String indirizzoResidenza) {
		this.indirizzoResidenza = indirizzoResidenza;
	}

	public String getLuogoInfrazione() {
		return this.luogoInfrazione;
	}

	public void setLuogoInfrazione(String luogoInfrazione) {
		this.luogoInfrazione = luogoInfrazione;
	}

	public String getLuogoNascita() {
		return this.luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	public String getLuogoResidenza() {
		return this.luogoResidenza;
	}

	public void setLuogoResidenza(String luogoResidenza) {
		this.luogoResidenza = luogoResidenza;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModello() {
		return this.modello;
	}

	public void setModello(String modello) {
		this.modello = modello;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getNrPatente() {
		return this.nrPatente;
	}

	public void setNrPatente(String nrPatente) {
		this.nrPatente = nrPatente;
	}

	public String getNrVerbale() {
		return this.nrVerbale;
	}

	public void setNrVerbale(String nrVerbale) {
		this.nrVerbale = nrVerbale;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProvRilascioPatente() {
		return this.provRilascioPatente;
	}

	public void setProvRilascioPatente(String provRilascioPatente) {
		this.provRilascioPatente = provRilascioPatente;
	}

	public String getSerieVerbale() {
		return this.serieVerbale;
	}

	public void setSerieVerbale(String serieVerbale) {
		this.serieVerbale = serieVerbale;
	}

	public String getSistemaPagamento() {
		return this.sistemaPagamento;
	}

	public void setSistemaPagamento(String sistemaPagamento) {
		this.sistemaPagamento = sistemaPagamento;
	}

	public String getTarga() {
		return this.targa;
	}

	public void setTarga(String targa) {
		this.targa = targa;
	}

	public String getTipoEnte() {
		return this.tipoEnte;
	}

	public void setTipoEnte(String tipoEnte) {
		this.tipoEnte = tipoEnte;
	}

	public SitTrffMulteStato getSitTrffMulteStato() {
		return this.sitTrffMulteStato;
	}

	public void setSitTrffMulteStato(SitTrffMulteStato sitTrffMulteStato) {
		this.sitTrffMulteStato = sitTrffMulteStato;
	}
	
	public SitTrffMulteTipo getSitTrffMulteTipo() {
		return this.sitTrffMulteTipo;
	}

	public void setSitTrffMulteTipo(SitTrffMulteTipo sitTrffMulteTipo) {
		this.sitTrffMulteTipo = sitTrffMulteTipo;
	}

	public BigDecimal getImportoScontato() {
		return importoScontato;
	}

	public void setImportoScontato(BigDecimal importoScontato) {
		this.importoScontato = importoScontato;
	}
	
}