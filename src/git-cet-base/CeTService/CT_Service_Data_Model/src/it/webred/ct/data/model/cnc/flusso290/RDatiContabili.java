package it.webred.ct.data.model.cnc.flusso290;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the RE_CNC_N4_DATI_CONT database table.
 * 
 */
@Entity
@Table(name="RE_CNC_N4_DATI_CONT")
public class RDatiContabili implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="ANNO_RIF_TRIBUTO")
	private String annoRifTributo;

	@Column(name="COD_COMUNE_ISCR_RUOLO")
	private String codComuneIscrRuolo;

	@Column(name="COD_PARTITA")
	private String codPartita;

	@Column(name="COD_REGIME_SANZION_M")
	private String codRegimeSanzionM;

	@Column(name="COD_REPARTO")
	private String codReparto;

	@Column(name="COD_S")
	private String codS;

	@Column(name="COD_TIPO_TITOLO")
	private String codTipoTitolo;

	@Column(name="COD_TRIBUTO")
	private String codTributo;

	@Column(name="DESCRIZIONE_E")
	private String descrizioneE;

	@Column(name="DT_DECORRENZA_INTERESSI")
	private String dtDecorrenzaInteressi;

    @Temporal( TemporalType.DATE)
	@Column(name="DT_EXP_DATO")
	private Date dtExpDato;

	@Column(name="DT_S")
	private String dtS;

	@Id
	@Column(name="FILE_ID")
	private String fileId;

	private String imponibile;

	@Column(name="IMPORTO_TETTO_SOMME_AGG_M")
	private String importoTettoSommeAggM;

	private String imposta;

	@Column(name="INFO_CARTELLA")
	private String infoCartella;

	@Column(name="MATRICOLA_M")
	private String matricolaM;

	private String processid;

	@Column(name="PROGRESSIVO_MINUTA")
	private String progressivoMinuta;

	@Column(name="RE_FLAG_ELABORATO")
	private String reFlagElaborato;

	private String semestri;

	@Column(name="TARGA_AUTO_S")
	private String targaAutoS;

	@Column(name="TITOLO_E")
	private String titoloE;

	@Column(name="TITOLO_S")
	private String titoloS;

    public RDatiContabili() {
    }

	public String getAnnoRifTributo() {
		return this.annoRifTributo;
	}

	public void setAnnoRifTributo(String annoRifTributo) {
		this.annoRifTributo = annoRifTributo;
	}

	public String getCodComuneIscrRuolo() {
		return this.codComuneIscrRuolo;
	}

	public void setCodComuneIscrRuolo(String codComuneIscrRuolo) {
		this.codComuneIscrRuolo = codComuneIscrRuolo;
	}

	public String getCodPartita() {
		return this.codPartita;
	}

	public void setCodPartita(String codPartita) {
		this.codPartita = codPartita;
	}

	public String getCodRegimeSanzionM() {
		return this.codRegimeSanzionM;
	}

	public void setCodRegimeSanzionM(String codRegimeSanzionM) {
		this.codRegimeSanzionM = codRegimeSanzionM;
	}

	public String getCodReparto() {
		return this.codReparto;
	}

	public void setCodReparto(String codReparto) {
		this.codReparto = codReparto;
	}

	public String getCodS() {
		return this.codS;
	}

	public void setCodS(String codS) {
		this.codS = codS;
	}

	public String getCodTipoTitolo() {
		return this.codTipoTitolo;
	}

	public void setCodTipoTitolo(String codTipoTitolo) {
		this.codTipoTitolo = codTipoTitolo;
	}

	public String getCodTributo() {
		return this.codTributo;
	}

	public void setCodTributo(String codTributo) {
		this.codTributo = codTributo;
	}

	public String getDescrizioneE() {
		return this.descrizioneE;
	}

	public void setDescrizioneE(String descrizioneE) {
		this.descrizioneE = descrizioneE;
	}

	public String getDtDecorrenzaInteressi() {
		return this.dtDecorrenzaInteressi;
	}

	public void setDtDecorrenzaInteressi(String dtDecorrenzaInteressi) {
		this.dtDecorrenzaInteressi = dtDecorrenzaInteressi;
	}

	public Date getDtExpDato() {
		return this.dtExpDato;
	}

	public void setDtExpDato(Date dtExpDato) {
		this.dtExpDato = dtExpDato;
	}

	public String getDtS() {
		return this.dtS;
	}

	public void setDtS(String dtS) {
		this.dtS = dtS;
	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getImponibile() {
		return this.imponibile;
	}

	public void setImponibile(String imponibile) {
		this.imponibile = imponibile;
	}

	public String getImportoTettoSommeAggM() {
		return this.importoTettoSommeAggM;
	}

	public void setImportoTettoSommeAggM(String importoTettoSommeAggM) {
		this.importoTettoSommeAggM = importoTettoSommeAggM;
	}

	public String getImposta() {
		return this.imposta;
	}

	public void setImposta(String imposta) {
		this.imposta = imposta;
	}

	public String getInfoCartella() {
		return this.infoCartella;
	}

	public void setInfoCartella(String infoCartella) {
		this.infoCartella = infoCartella;
	}

	public String getMatricolaM() {
		return this.matricolaM;
	}

	public void setMatricolaM(String matricolaM) {
		this.matricolaM = matricolaM;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProgressivoMinuta() {
		return this.progressivoMinuta;
	}

	public void setProgressivoMinuta(String progressivoMinuta) {
		this.progressivoMinuta = progressivoMinuta;
	}

	public String getReFlagElaborato() {
		return this.reFlagElaborato;
	}

	public void setReFlagElaborato(String reFlagElaborato) {
		this.reFlagElaborato = reFlagElaborato;
	}

	public String getSemestri() {
		return this.semestri;
	}

	public void setSemestri(String semestri) {
		this.semestri = semestri;
	}

	public String getTargaAutoS() {
		return this.targaAutoS;
	}

	public void setTargaAutoS(String targaAutoS) {
		this.targaAutoS = targaAutoS;
	}

	public String getTitoloE() {
		return this.titoloE;
	}

	public void setTitoloE(String titoloE) {
		this.titoloE = titoloE;
	}

	public String getTitoloS() {
		return this.titoloS;
	}

	public void setTitoloS(String titoloS) {
		this.titoloS = titoloS;
	}

}