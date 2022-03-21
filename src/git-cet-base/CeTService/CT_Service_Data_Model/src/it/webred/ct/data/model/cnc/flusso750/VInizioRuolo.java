package it.webred.ct.data.model.cnc.flusso750;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the RE_CNC_R5A_INIZIO_RUOLO database table.
 * 
 */
@Entity
@Table(name="RE_CNC_R5A_INIZIO_RUOLO")
public class VInizioRuolo implements Serializable {
	private static final long serialVersionUID = 1L;

	// Surrogate Key
	@Id
	@Column(name="ID")
	private Long id;

	@Embedded
	private ChiaveULRuolo chiaveRuolo;

	@Column(name="ANNO_RUOLO_AVVISI")
	private String annoRuoloAvvisi;

	@Column(name="COD_AMBITO_AVVISI")
	private String codAmbitoAvvisi;

	@Column(name="COD_CONVENZIONE")
	private String codConvenzione;


	@Column(name="COD_ESCLUSIONE_AGOSTO")
	private String codEsclusioneAgosto;

	@Column(name="COD_RUOLO_INFRARATA")
	private String codRuoloInfrarata;

	@Column(name="COD_SPECIE_RUOLO")
	private String codSpecieRuolo;

	@Column(name="COD_TIPO_CARTELLAZIONE")
	private String codTipoCartellazione;

	@Column(name="COD_TIPO_ISCRIZIONE")
	private String codTipoIscrizione;

	@Column(name="COD_TIPO_UFFICIO")
	private String codTipoUfficio;

	@Column(name="COD_UFFICIO")
	private String codUfficio;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="DT_INOLTRO_RISC_1")
	private String dtInoltroRisc1;

	@Column(name="DT_INOLTRO_RISC_2")
	private String dtInoltroRisc2;

	@Column(name="DT_INOLTRO_RISC_3")
	private String dtInoltroRisc3;

	@Column(name="DT_INOLTRO_RISC_4")
	private String dtInoltroRisc4;

	@Column(name="DT_SCAD_PRIMA_RATA")
	private String dtScadPrimaRata;

	@Column(name="DT_SOTTOSCRIZIONE")
	private String dtSottoscrizione;

	@Column(name="DT_VARIAZIONE_CONVENZIONE")
	private String dtVariazioneConvenzione;

	private String filler;

	@Column(name="FLAG_MAGGIORAZIONE")
	private String flagMaggiorazione;

	@Column(name="NUMERO_RATA_1")
	private String numeroRata1;

	@Column(name="NUMERO_RATA_2")
	private String numeroRata2;

	@Column(name="NUMERO_RATA_3")
	private String numeroRata3;

	@Column(name="NUMERO_RATA_4")
	private String numeroRata4;

	private String processid;


	@Column(name="PROGRESSIVO_RUOLO_AVVISI")
	private String progressivoRuoloAvvisi;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	@Column(name="TASSO_PERCENTUALE")
	private String tassoPercentuale;

    public VInizioRuolo() {
    }


	public String getAnnoRuoloAvvisi() {
		return this.annoRuoloAvvisi;
	}

	public void setAnnoRuoloAvvisi(String annoRuoloAvvisi) {
		this.annoRuoloAvvisi = annoRuoloAvvisi;
	}

	public String getCodAmbitoAvvisi() {
		return this.codAmbitoAvvisi;
	}

	public void setCodAmbitoAvvisi(String codAmbitoAvvisi) {
		this.codAmbitoAvvisi = codAmbitoAvvisi;
	}

	public String getCodConvenzione() {
		return this.codConvenzione;
	}

	public void setCodConvenzione(String codConvenzione) {
		this.codConvenzione = codConvenzione;
	}

	public String getCodEsclusioneAgosto() {
		return this.codEsclusioneAgosto;
	}

	public void setCodEsclusioneAgosto(String codEsclusioneAgosto) {
		this.codEsclusioneAgosto = codEsclusioneAgosto;
	}

	public String getCodRuoloInfrarata() {
		return this.codRuoloInfrarata;
	}

	public void setCodRuoloInfrarata(String codRuoloInfrarata) {
		this.codRuoloInfrarata = codRuoloInfrarata;
	}

	public String getCodSpecieRuolo() {
		return this.codSpecieRuolo;
	}

	public void setCodSpecieRuolo(String codSpecieRuolo) {
		this.codSpecieRuolo = codSpecieRuolo;
	}

	public String getCodTipoCartellazione() {
		return this.codTipoCartellazione;
	}

	public void setCodTipoCartellazione(String codTipoCartellazione) {
		this.codTipoCartellazione = codTipoCartellazione;
	}

	public String getCodTipoIscrizione() {
		return this.codTipoIscrizione;
	}

	public void setCodTipoIscrizione(String codTipoIscrizione) {
		this.codTipoIscrizione = codTipoIscrizione;
	}

	public String getCodTipoUfficio() {
		return this.codTipoUfficio;
	}

	public void setCodTipoUfficio(String codTipoUfficio) {
		this.codTipoUfficio = codTipoUfficio;
	}

	public String getCodUfficio() {
		return this.codUfficio;
	}

	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getDtInoltroRisc1() {
		return this.dtInoltroRisc1;
	}

	public void setDtInoltroRisc1(String dtInoltroRisc1) {
		this.dtInoltroRisc1 = dtInoltroRisc1;
	}

	public String getDtInoltroRisc2() {
		return this.dtInoltroRisc2;
	}

	public void setDtInoltroRisc2(String dtInoltroRisc2) {
		this.dtInoltroRisc2 = dtInoltroRisc2;
	}

	public String getDtInoltroRisc3() {
		return this.dtInoltroRisc3;
	}

	public void setDtInoltroRisc3(String dtInoltroRisc3) {
		this.dtInoltroRisc3 = dtInoltroRisc3;
	}

	public String getDtInoltroRisc4() {
		return this.dtInoltroRisc4;
	}

	public void setDtInoltroRisc4(String dtInoltroRisc4) {
		this.dtInoltroRisc4 = dtInoltroRisc4;
	}

	public String getDtScadPrimaRata() {
		return this.dtScadPrimaRata;
	}

	public void setDtScadPrimaRata(String dtScadPrimaRata) {
		this.dtScadPrimaRata = dtScadPrimaRata;
	}

	public String getDtSottoscrizione() {
		return this.dtSottoscrizione;
	}

	public void setDtSottoscrizione(String dtSottoscrizione) {
		this.dtSottoscrizione = dtSottoscrizione;
	}

	public String getDtVariazioneConvenzione() {
		return this.dtVariazioneConvenzione;
	}

	public void setDtVariazioneConvenzione(String dtVariazioneConvenzione) {
		this.dtVariazioneConvenzione = dtVariazioneConvenzione;
	}

	public String getFiller() {
		return this.filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getFlagMaggiorazione() {
		return this.flagMaggiorazione;
	}

	public void setFlagMaggiorazione(String flagMaggiorazione) {
		this.flagMaggiorazione = flagMaggiorazione;
	}

	public String getNumeroRata1() {
		return this.numeroRata1;
	}

	public void setNumeroRata1(String numeroRata1) {
		this.numeroRata1 = numeroRata1;
	}

	public String getNumeroRata2() {
		return this.numeroRata2;
	}

	public void setNumeroRata2(String numeroRata2) {
		this.numeroRata2 = numeroRata2;
	}

	public String getNumeroRata3() {
		return this.numeroRata3;
	}

	public void setNumeroRata3(String numeroRata3) {
		this.numeroRata3 = numeroRata3;
	}

	public String getNumeroRata4() {
		return this.numeroRata4;
	}

	public void setNumeroRata4(String numeroRata4) {
		this.numeroRata4 = numeroRata4;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}



	public String getProgressivoRuoloAvvisi() {
		return this.progressivoRuoloAvvisi;
	}

	public void setProgressivoRuoloAvvisi(String progressivoRuoloAvvisi) {
		this.progressivoRuoloAvvisi = progressivoRuoloAvvisi;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getTassoPercentuale() {
		return this.tassoPercentuale;
	}

	public void setTassoPercentuale(String tassoPercentuale) {
		this.tassoPercentuale = tassoPercentuale;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public ChiaveULRuolo getChiaveRuolo() {
		return chiaveRuolo;
	}


	public void setChiaveRuolo(ChiaveULRuolo chiaveRuolo) {
		this.chiaveRuolo = chiaveRuolo;
	}

	
}