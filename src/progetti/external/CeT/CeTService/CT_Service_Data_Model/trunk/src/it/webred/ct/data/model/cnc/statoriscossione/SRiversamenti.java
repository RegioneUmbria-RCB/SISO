package it.webred.ct.data.model.cnc.statoriscossione;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_FR4_RIVERSAMENTI database table.
 * 
 */
@Entity
@Table(name="RE_CNC_FR4_RIVERSAMENTI")

public class SRiversamenti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Embedded
	private ChiaveULStatoRiscossione chiaveRiscossione;
	
	@Column(name="ARTICOLO_BILANCIO_EFFETT")
	private String articoloBilancioEffett;

	@Column(name="ARTICOLO_BILANCIO_ORIGIN")
	private String articoloBilancioOrigin;

	@Column(name="CAPITOLO_BILANCIO_EFFETT")
	private String capitoloBilancioEffett;

	@Column(name="CAPITOLO_BILANCIO_ORIGIN")
	private String capitoloBilancioOrigin;

	@Column(name="CAPO_BILANCIO_EFFETT")
	private String capoBilancioEffett;

	@Column(name="CAPO_BILANCIO_ORIGIN")
	private String capoBilancioOrigin;


	@Column(name="COD_DIVISA_OPERAZ")
	private String codDivisaOperaz;

	@Column(name="COD_ENTE_BENEF")
	private String codEnteBenef;

	@Column(name="COD_TIPO_UFFICIO_DEST")
	private String codTipoUfficioDest;

	@Column(name="COD_UFFICIO_BENEF")
	private String codUfficioBenef;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;


	@Column(name="DT_RIVERSAMENTO")
	private String dtRiversamento;

	private String filler;

	@Id
	private String id;

	@Column(name="IMPORTO_AGGIO_CARICO_ENTE")
	private BigDecimal importoAggioCaricoEnte;

	@Column(name="IMPORTO_CARICO")
	private BigDecimal importoCarico;

	@Column(name="IMPORTO_DA_VERSARE")
	private BigDecimal importoDaVersare;

	@Column(name="IMPORTO_INTERESSI_MORA")
	private BigDecimal importoInteressiMora;

	@Column(name="IMPORTO_IVA_AGGIO")
	private BigDecimal importoIvaAggio;

	@Column(name="IMPORTO_RIMB_SPESE_REST")
	private BigDecimal importoRimbSpeseRest;


	@Column(name="NUMERO_QUIETANZA_PROVVIS")
	private BigDecimal numeroQuietanzaProvvis;

	@Column(name="NUMERO_RIVERSAMENTO")
	private BigDecimal numeroRiversamento;

	private String processid;


	@Column(name="PROGRESSIVO_RECORD")
	private BigDecimal progressivoRecord;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

    public SRiversamenti() {
    }


	public String getArticoloBilancioEffett() {
		return this.articoloBilancioEffett;
	}

	public void setArticoloBilancioEffett(String articoloBilancioEffett) {
		this.articoloBilancioEffett = articoloBilancioEffett;
	}

	public String getArticoloBilancioOrigin() {
		return this.articoloBilancioOrigin;
	}

	public void setArticoloBilancioOrigin(String articoloBilancioOrigin) {
		this.articoloBilancioOrigin = articoloBilancioOrigin;
	}

	public String getCapitoloBilancioEffett() {
		return this.capitoloBilancioEffett;
	}

	public void setCapitoloBilancioEffett(String capitoloBilancioEffett) {
		this.capitoloBilancioEffett = capitoloBilancioEffett;
	}

	public String getCapitoloBilancioOrigin() {
		return this.capitoloBilancioOrigin;
	}

	public void setCapitoloBilancioOrigin(String capitoloBilancioOrigin) {
		this.capitoloBilancioOrigin = capitoloBilancioOrigin;
	}

	public String getCapoBilancioEffett() {
		return this.capoBilancioEffett;
	}

	public void setCapoBilancioEffett(String capoBilancioEffett) {
		this.capoBilancioEffett = capoBilancioEffett;
	}

	public String getCapoBilancioOrigin() {
		return this.capoBilancioOrigin;
	}

	public void setCapoBilancioOrigin(String capoBilancioOrigin) {
		this.capoBilancioOrigin = capoBilancioOrigin;
	}

	public String getCodDivisaOperaz() {
		return this.codDivisaOperaz;
	}

	public void setCodDivisaOperaz(String codDivisaOperaz) {
		this.codDivisaOperaz = codDivisaOperaz;
	}

	public String getCodEnteBenef() {
		return this.codEnteBenef;
	}

	public void setCodEnteBenef(String codEnteBenef) {
		this.codEnteBenef = codEnteBenef;
	}

	public String getCodTipoUfficioDest() {
		return this.codTipoUfficioDest;
	}

	public void setCodTipoUfficioDest(String codTipoUfficioDest) {
		this.codTipoUfficioDest = codTipoUfficioDest;
	}

	public String getCodUfficioBenef() {
		return this.codUfficioBenef;
	}

	public void setCodUfficioBenef(String codUfficioBenef) {
		this.codUfficioBenef = codUfficioBenef;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getDtRiversamento() {
		return this.dtRiversamento;
	}

	public void setDtRiversamento(String dtRiversamento) {
		this.dtRiversamento = dtRiversamento;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getImportoAggioCaricoEnte() {
		return this.importoAggioCaricoEnte;
	}

	public void setImportoAggioCaricoEnte(BigDecimal importoAggioCaricoEnte) {
		this.importoAggioCaricoEnte = importoAggioCaricoEnte;
	}

	public BigDecimal getImportoCarico() {
		return this.importoCarico;
	}

	public void setImportoCarico(BigDecimal importoCarico) {
		this.importoCarico = importoCarico;
	}

	public BigDecimal getImportoDaVersare() {
		return this.importoDaVersare;
	}

	public void setImportoDaVersare(BigDecimal importoDaVersare) {
		this.importoDaVersare = importoDaVersare;
	}

	public BigDecimal getImportoInteressiMora() {
		return this.importoInteressiMora;
	}

	public void setImportoInteressiMora(BigDecimal importoInteressiMora) {
		this.importoInteressiMora = importoInteressiMora;
	}

	public BigDecimal getImportoIvaAggio() {
		return this.importoIvaAggio;
	}

	public void setImportoIvaAggio(BigDecimal importoIvaAggio) {
		this.importoIvaAggio = importoIvaAggio;
	}

	public BigDecimal getImportoRimbSpeseRest() {
		return this.importoRimbSpeseRest;
	}

	public void setImportoRimbSpeseRest(BigDecimal importoRimbSpeseRest) {
		this.importoRimbSpeseRest = importoRimbSpeseRest;
	}

	public BigDecimal getNumeroQuietanzaProvvis() {
		return this.numeroQuietanzaProvvis;
	}

	public void setNumeroQuietanzaProvvis(BigDecimal numeroQuietanzaProvvis) {
		this.numeroQuietanzaProvvis = numeroQuietanzaProvvis;
	}

	public BigDecimal getNumeroRiversamento() {
		return this.numeroRiversamento;
	}

	public void setNumeroRiversamento(BigDecimal numeroRiversamento) {
		this.numeroRiversamento = numeroRiversamento;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}



	public BigDecimal getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(BigDecimal progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}


	public ChiaveULStatoRiscossione getChiaveRiscossione() {
		return chiaveRiscossione;
	}


	public void setChiaveRiscossione(ChiaveULStatoRiscossione chiaveRiscossione) {
		this.chiaveRiscossione = chiaveRiscossione;
	}

	
}