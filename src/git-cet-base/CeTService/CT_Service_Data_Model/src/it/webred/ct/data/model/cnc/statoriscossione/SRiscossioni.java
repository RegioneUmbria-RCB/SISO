package it.webred.ct.data.model.cnc.statoriscossione;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_FR3_RISCOSSIONI database table.
 * 
 */
@Entity
@Table(name="RE_CNC_FR3_RISCOSSIONI")
public class SRiscossioni implements Serializable {
	private static final long serialVersionUID = 1L;

	@Embedded
	private ChiaveULStatoRiscossione chiaveRiscossione;
	
	@Column(name="ANNO_FORM_CART")
	private BigDecimal annoFormCart;


	@Column(name="CHECK_DIGIT_ID_CART")
	private BigDecimal checkDigitIdCart;


	@Column(name="COD_AMBITO_ID_AVV_CART")
	private String codAmbitoIdAvvCart;

	@Column(name="COD_DIVISA_OPERAZ")
	private String codDivisaOperaz;


	@Column(name="COD_ENTRATA")
	private String codEntrata;

	@Column(name="COD_FISCALE")
	private String codFiscale;

	@Column(name="COD_MODALITA_PAGAMENTO")
	private String codModalitaPagamento;


	@Column(name="COD_RESTITUZ_RIMB_SPESE")
	private String codRestituzRimbSpese;

	@Column(name="COD_TIPO_COD_ENTRATA")
	private String codTipoCodEntrata;

	@Column(name="DT_DECORRENZA")
	private String dtDecorrenza;

	@Column(name="DT_EVENTO")
	private String dtEvento;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;


	private String filler;

	private String filler1;

	@Id
	private Long id;

	@Column(name="ID_PROVVED_MAGG_RATEAZ")
	private String idProvvedMaggRateaz;

	@Column(name="IMPORTO_AGGIO_CARICO_CONTR")
	private BigDecimal importoAggioCaricoContr;

	@Column(name="IMPORTO_AGGIO_CARICO_ENTE")
	private BigDecimal importoAggioCaricoEnte;

	@Column(name="IMPORTO_CARICO_ORIGIN")
	private BigDecimal importoCaricoOrigin;

	@Column(name="IMPORTO_CARICO_RES_OGG_CON")
	private BigDecimal importoCaricoResOggCon;

	@Column(name="IMPORTO_CARICO_RISC")
	private BigDecimal importoCaricoRisc;

	@Column(name="IMPORTO_INTERESSI_MORA")
	private BigDecimal importoInteressiMora;


	@Column(name="NUMERO_RIVERSAMENTI")
	private BigDecimal numeroRiversamenti;

	private String processid;

	@Column(name="PROGRESSIVO_ARTICOLO_CART")
	private BigDecimal progressivoArticoloCart;


	@Column(name="PROGRESSIVO_CART")
	private BigDecimal progressivoCart;

	@Column(name="PROGRESSIVO_RECORD")
	private BigDecimal progressivoRecord;

	@Column(name="PROGRESSIVO_SOGG_CART")
	private BigDecimal progressivoSoggCart;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="SPESE_PROC_ESECUTIVE_PIE")
	private BigDecimal speseProcEsecutivePie;

	@Column(name="SPESE_PROC_ESECUTIVE_TAB")
	private BigDecimal speseProcEsecutiveTab;

    public SRiscossioni() {
    }

	public BigDecimal getAnnoFormCart() {
		return this.annoFormCart;
	}

	public void setAnnoFormCart(BigDecimal annoFormCart) {
		this.annoFormCart = annoFormCart;
	}


	public BigDecimal getCheckDigitIdCart() {
		return this.checkDigitIdCart;
	}

	public void setCheckDigitIdCart(BigDecimal checkDigitIdCart) {
		this.checkDigitIdCart = checkDigitIdCart;
	}



	public String getCodAmbitoIdAvvCart() {
		return this.codAmbitoIdAvvCart;
	}

	public void setCodAmbitoIdAvvCart(String codAmbitoIdAvvCart) {
		this.codAmbitoIdAvvCart = codAmbitoIdAvvCart;
	}

	public String getCodDivisaOperaz() {
		return this.codDivisaOperaz;
	}

	public void setCodDivisaOperaz(String codDivisaOperaz) {
		this.codDivisaOperaz = codDivisaOperaz;
	}


	public String getCodEntrata() {
		return this.codEntrata;
	}

	public void setCodEntrata(String codEntrata) {
		this.codEntrata = codEntrata;
	}

	public String getCodFiscale() {
		return this.codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	public String getCodModalitaPagamento() {
		return this.codModalitaPagamento;
	}

	public void setCodModalitaPagamento(String codModalitaPagamento) {
		this.codModalitaPagamento = codModalitaPagamento;
	}


	public String getCodRestituzRimbSpese() {
		return this.codRestituzRimbSpese;
	}

	public void setCodRestituzRimbSpese(String codRestituzRimbSpese) {
		this.codRestituzRimbSpese = codRestituzRimbSpese;
	}

	public String getCodTipoCodEntrata() {
		return this.codTipoCodEntrata;
	}

	public void setCodTipoCodEntrata(String codTipoCodEntrata) {
		this.codTipoCodEntrata = codTipoCodEntrata;
	}

	public String getDtDecorrenza() {
		return this.dtDecorrenza;
	}

	public void setDtDecorrenza(String dtDecorrenza) {
		this.dtDecorrenza = dtDecorrenza;
	}

	public String getDtEvento() {
		return this.dtEvento;
	}

	public void setDtEvento(String dtEvento) {
		this.dtEvento = dtEvento;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}


	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getFiller1() {
		return this.filler1;
	}

	public void setFiller1(String filler1) {
		this.filler1 = filler1;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIdProvvedMaggRateaz() {
		return this.idProvvedMaggRateaz;
	}

	public void setIdProvvedMaggRateaz(String idProvvedMaggRateaz) {
		this.idProvvedMaggRateaz = idProvvedMaggRateaz;
	}

	public BigDecimal getImportoAggioCaricoContr() {
		return this.importoAggioCaricoContr;
	}

	public void setImportoAggioCaricoContr(BigDecimal importoAggioCaricoContr) {
		this.importoAggioCaricoContr = importoAggioCaricoContr;
	}

	public BigDecimal getImportoAggioCaricoEnte() {
		return this.importoAggioCaricoEnte;
	}

	public void setImportoAggioCaricoEnte(BigDecimal importoAggioCaricoEnte) {
		this.importoAggioCaricoEnte = importoAggioCaricoEnte;
	}

	public BigDecimal getImportoCaricoOrigin() {
		return this.importoCaricoOrigin;
	}

	public void setImportoCaricoOrigin(BigDecimal importoCaricoOrigin) {
		this.importoCaricoOrigin = importoCaricoOrigin;
	}

	public BigDecimal getImportoCaricoResOggCon() {
		return this.importoCaricoResOggCon;
	}

	public void setImportoCaricoResOggCon(BigDecimal importoCaricoResOggCon) {
		this.importoCaricoResOggCon = importoCaricoResOggCon;
	}

	public BigDecimal getImportoCaricoRisc() {
		return this.importoCaricoRisc;
	}

	public void setImportoCaricoRisc(BigDecimal importoCaricoRisc) {
		this.importoCaricoRisc = importoCaricoRisc;
	}

	public BigDecimal getImportoInteressiMora() {
		return this.importoInteressiMora;
	}

	public void setImportoInteressiMora(BigDecimal importoInteressiMora) {
		this.importoInteressiMora = importoInteressiMora;
	}


	public BigDecimal getNumeroRiversamenti() {
		return this.numeroRiversamenti;
	}

	public void setNumeroRiversamenti(BigDecimal numeroRiversamenti) {
		this.numeroRiversamenti = numeroRiversamenti;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public BigDecimal getProgressivoArticoloCart() {
		return this.progressivoArticoloCart;
	}

	public void setProgressivoArticoloCart(BigDecimal progressivoArticoloCart) {
		this.progressivoArticoloCart = progressivoArticoloCart;
	}


	public BigDecimal getProgressivoCart() {
		return this.progressivoCart;
	}

	public void setProgressivoCart(BigDecimal progressivoCart) {
		this.progressivoCart = progressivoCart;
	}


	public BigDecimal getProgressivoRecord() {
		return this.progressivoRecord;
	}

	public void setProgressivoRecord(BigDecimal progressivoRecord) {
		this.progressivoRecord = progressivoRecord;
	}

	public BigDecimal getProgressivoSoggCart() {
		return this.progressivoSoggCart;
	}

	public void setProgressivoSoggCart(BigDecimal progressivoSoggCart) {
		this.progressivoSoggCart = progressivoSoggCart;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public BigDecimal getSpeseProcEsecutivePie() {
		return this.speseProcEsecutivePie;
	}

	public void setSpeseProcEsecutivePie(BigDecimal speseProcEsecutivePie) {
		this.speseProcEsecutivePie = speseProcEsecutivePie;
	}

	public BigDecimal getSpeseProcEsecutiveTab() {
		return this.speseProcEsecutiveTab;
	}

	public void setSpeseProcEsecutiveTab(BigDecimal speseProcEsecutiveTab) {
		this.speseProcEsecutiveTab = speseProcEsecutiveTab;
	}

	public ChiaveULStatoRiscossione getChiaveRiscossione() {
		return chiaveRiscossione;
	}

	public void setChiaveRiscossione(ChiaveULStatoRiscossione chiaveRiscossione) {
		this.chiaveRiscossione = chiaveRiscossione;
	}

	
}