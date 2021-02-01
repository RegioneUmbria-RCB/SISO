package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_R7E_ARTICOLO database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R7E_ARTICOLO")
public class VArticolo implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Embedded
	private ChiaveULPartita chiavePartita;

	@Column(name="ANNO_VETUSTA")
	private String annoVetusta;

	private String articolo;

	@Column(name="CADENZA_RATA")
	private String cadenzaRata;

	private String capitolo;

	private String capo;

	
	@Column(name="COD_ENTRATA")
	private String codEntrata;


	@Column(name="COD_TIPO_ENTRATA")
	private String codTipoEntrata;

	@Column(name="CODICE_ENTE_BENEFICIARIO")
	private String codiceEnteBeneficiario;

	@Column(name="CODICE_UFFICIO_BENEFICIARIO")
	private String codiceUfficioBeneficiario;

	@Column(name="COMPETENZA_RESIDUI")
	private String competenzaResidui;

	@Column(name="DESCR_ULT_ARTICOLO")
	private String descrUltArticolo;

	@Column(name="DESCRIZIONE_ARTICOLO")
	private String descrizioneArticolo;

	@Column(name="DESCRIZIONE_ARTICOLO_BILING")
	private String descrizioneArticoloBiling;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	private String filler;

	@Column(name="FLAG_RATE")
	private String flagRate;

	@Column(name="FLAG_REGIME_SANZ")
	private String flagRegimeSanz;

	@Column(name="FLAG_ULT_BENEF")
	private String flagUltBenef;

	@Column(name="IMPORTO_AGGIO_ANT_BENEF")
	private String importoAggioAntBenef;

	@Column(name="IMPORTO_AGGIO_BENEF")
	private String importoAggioBenef;

	@Column(name="IMPORTO_AGIO")
	private String importoAgio;

	@Column(name="IMPORTO_AGIO_DA_ANTICIPARE")
	private String importoAgioDaAnticipare;

	@Column(name="IMPORTO_ARROTONDAMENTO")
	private String importoArrotondamento;

	@Column(name="IMPORTO_ARTICOLI_RUOLO")
	private String importoArticoliRuolo;

	@Column(name="IMPORTO_ARTICOLO_RUOLO_BENEF")
	private String importoArticoloRuoloBenef;

	@Column(name="IMPORTO_CARICO")
	private String importoCarico;

	@Column(name="IMPORTO_CARICO_BENEF")
	private String importoCaricoBenef;

	@Column(name="IMPORTO_CARICO_ORIGINARIO")
	private String importoCaricoOriginario;

	@Column(name="NUMERO_RATE")
	private String numeroRate;

	@Column(name="PERIODO_RIFERIMENTO_ANNO")
	private String periodoRiferimentoAnno;

	@Column(name="PERIODO_RIFERIMENTO_PERIODO")
	private String periodoRiferimentoPeriodo;

	private String processid;

	@Column(name="PROGRESSIVO_ARTICOLO_RUOLO")
	private String progressivoArticoloRuolo;

	@Column(name="PROGRESSIVO_RECORD")
	private BigDecimal progressivoRecord;



	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="SEGNO_IMPORTO_ARROT")
	private String segnoImportoArrot;

	@Column(name="TETTO_SOMME_AGGIUNTIVE")
	private String tettoSommeAggiuntive;

	@Column(name="TIPO_ENTRATA")
	private String tipoEntrata;

	@Column(name="TIPO_UFFICIO_BENEFICIARIO")
	private String tipoUfficioBeneficiario;

	@Column(name="TIPO_VERSAMENTO")
	private String tipoVersamento;

    public VArticolo() {
    }

	public String getAnnoVetusta() {
		return this.annoVetusta;
	}

	public void setAnnoVetusta(String annoVetusta) {
		this.annoVetusta = annoVetusta;
	}

	public String getArticolo() {
		return this.articolo;
	}

	public void setArticolo(String articolo) {
		this.articolo = articolo;
	}

	public String getCadenzaRata() {
		return this.cadenzaRata;
	}

	public void setCadenzaRata(String cadenzaRata) {
		this.cadenzaRata = cadenzaRata;
	}

	public String getCapitolo() {
		return this.capitolo;
	}

	public void setCapitolo(String capitolo) {
		this.capitolo = capitolo;
	}

	public String getCapo() {
		return this.capo;
	}

	public void setCapo(String capo) {
		this.capo = capo;
	}

	
	public String getCodEntrata() {
		return this.codEntrata;
	}

	public void setCodEntrata(String codEntrata) {
		this.codEntrata = codEntrata;
	}
	

	public String getCodTipoEntrata() {
		return this.codTipoEntrata;
	}

	public void setCodTipoEntrata(String codTipoEntrata) {
		this.codTipoEntrata = codTipoEntrata;
	}



	public String getCodiceEnteBeneficiario() {
		return this.codiceEnteBeneficiario;
	}

	public void setCodiceEnteBeneficiario(String codiceEnteBeneficiario) {
		this.codiceEnteBeneficiario = codiceEnteBeneficiario;
	}

	public String getCodiceUfficioBeneficiario() {
		return this.codiceUfficioBeneficiario;
	}

	public void setCodiceUfficioBeneficiario(String codiceUfficioBeneficiario) {
		this.codiceUfficioBeneficiario = codiceUfficioBeneficiario;
	}

	public String getCompetenzaResidui() {
		return this.competenzaResidui;
	}

	public void setCompetenzaResidui(String competenzaResidui) {
		this.competenzaResidui = competenzaResidui;
	}

	public String getDescrUltArticolo() {
		return this.descrUltArticolo;
	}

	public void setDescrUltArticolo(String descrUltArticolo) {
		this.descrUltArticolo = descrUltArticolo;
	}

	public String getDescrizioneArticolo() {
		return this.descrizioneArticolo;
	}

	public void setDescrizioneArticolo(String descrizioneArticolo) {
		this.descrizioneArticolo = descrizioneArticolo;
	}

	public String getDescrizioneArticoloBiling() {
		return this.descrizioneArticoloBiling;
	}

	public void setDescrizioneArticoloBiling(String descrizioneArticoloBiling) {
		this.descrizioneArticoloBiling = descrizioneArticoloBiling;
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

	public String getFlagRate() {
		return this.flagRate;
	}

	public void setFlagRate(String flagRate) {
		this.flagRate = flagRate;
	}

	public String getFlagRegimeSanz() {
		return this.flagRegimeSanz;
	}

	public void setFlagRegimeSanz(String flagRegimeSanz) {
		this.flagRegimeSanz = flagRegimeSanz;
	}

	public String getFlagUltBenef() {
		return this.flagUltBenef;
	}

	public void setFlagUltBenef(String flagUltBenef) {
		this.flagUltBenef = flagUltBenef;
	}

	public String getImportoAggioAntBenef() {
		return this.importoAggioAntBenef;
	}

	public void setImportoAggioAntBenef(String importoAggioAntBenef) {
		this.importoAggioAntBenef = importoAggioAntBenef;
	}

	public String getImportoAggioBenef() {
		return this.importoAggioBenef;
	}

	public void setImportoAggioBenef(String importoAggioBenef) {
		this.importoAggioBenef = importoAggioBenef;
	}

	public String getImportoAgio() {
		return this.importoAgio;
	}

	public void setImportoAgio(String importoAgio) {
		this.importoAgio = importoAgio;
	}

	public String getImportoAgioDaAnticipare() {
		return this.importoAgioDaAnticipare;
	}

	public void setImportoAgioDaAnticipare(String importoAgioDaAnticipare) {
		this.importoAgioDaAnticipare = importoAgioDaAnticipare;
	}

	public String getImportoArrotondamento() {
		return this.importoArrotondamento;
	}

	public void setImportoArrotondamento(String importoArrotondamento) {
		this.importoArrotondamento = importoArrotondamento;
	}

	public String getImportoArticoliRuolo() {
		return this.importoArticoliRuolo;
	}

	public void setImportoArticoliRuolo(String importoArticoliRuolo) {
		this.importoArticoliRuolo = importoArticoliRuolo;
	}

	public String getImportoArticoloRuoloBenef() {
		return this.importoArticoloRuoloBenef;
	}

	public void setImportoArticoloRuoloBenef(String importoArticoloRuoloBenef) {
		this.importoArticoloRuoloBenef = importoArticoloRuoloBenef;
	}

	public String getImportoCarico() {
		return this.importoCarico;
	}

	public void setImportoCarico(String importoCarico) {
		this.importoCarico = importoCarico;
	}

	public String getImportoCaricoBenef() {
		return this.importoCaricoBenef;
	}

	public void setImportoCaricoBenef(String importoCaricoBenef) {
		this.importoCaricoBenef = importoCaricoBenef;
	}

	public String getImportoCaricoOriginario() {
		return this.importoCaricoOriginario;
	}

	public void setImportoCaricoOriginario(String importoCaricoOriginario) {
		this.importoCaricoOriginario = importoCaricoOriginario;
	}

	public String getNumeroRate() {
		return this.numeroRate;
	}

	public void setNumeroRate(String numeroRate) {
		this.numeroRate = numeroRate;
	}

	public String getPeriodoRiferimentoAnno() {
		return this.periodoRiferimentoAnno;
	}

	public void setPeriodoRiferimentoAnno(String periodoRiferimentoAnno) {
		this.periodoRiferimentoAnno = periodoRiferimentoAnno;
	}

	public String getPeriodoRiferimentoPeriodo() {
		return this.periodoRiferimentoPeriodo;
	}

	public void setPeriodoRiferimentoPeriodo(String periodoRiferimentoPeriodo) {
		this.periodoRiferimentoPeriodo = periodoRiferimentoPeriodo;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoArticoloRuolo() {
		return this.progressivoArticoloRuolo;
	}

	public void setProgressivoArticoloRuolo(String progressivoArticoloRuolo) {
		this.progressivoArticoloRuolo = progressivoArticoloRuolo;
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

	public String getSegnoImportoArrot() {
		return this.segnoImportoArrot;
	}

	public void setSegnoImportoArrot(String segnoImportoArrot) {
		this.segnoImportoArrot = segnoImportoArrot;
	}

	public String getTettoSommeAggiuntive() {
		return this.tettoSommeAggiuntive;
	}

	public void setTettoSommeAggiuntive(String tettoSommeAggiuntive) {
		this.tettoSommeAggiuntive = tettoSommeAggiuntive;
	}

	public String getTipoEntrata() {
		return this.tipoEntrata;
	}

	public void setTipoEntrata(String tipoEntrata) {
		this.tipoEntrata = tipoEntrata;
	}

	public String getTipoUfficioBeneficiario() {
		return this.tipoUfficioBeneficiario;
	}

	public void setTipoUfficioBeneficiario(String tipoUfficioBeneficiario) {
		this.tipoUfficioBeneficiario = tipoUfficioBeneficiario;
	}

	public String getTipoVersamento() {
		return this.tipoVersamento;
	}

	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ChiaveULPartita getChiavePartita() {
		return chiavePartita;
	}

	public void setChiavePartita(ChiaveULPartita chiavePartita) {
		this.chiavePartita = chiavePartita;
	}
	
	

}