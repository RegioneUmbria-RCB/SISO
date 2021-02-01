package it.webred.cs.csa.ejb.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * SISO-538
 * bean che tiene i dati da visualizzare nella lista delle erogazioni da esportare
 *
 */
public class EsportazioneDTOView extends EsportazioneDTO { 
/*	public static int EROGAZIONE_DA_INVIARE = 0;
	public static int EROGAZIONE_DA_NON_INVIARE = 1;*/
	
	private static final long serialVersionUID = 1L;
	private static final String STYLE_DA_INVIARE = "";	// nessuno stile
	private static final String STYLE_DA_NON_INVIARE = "color: gray;";
	private static final String STYLE_ESPORTATO = "color: gray; font-style: italic;";
	
	
	private SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd/MM/yyyy");
	

	private boolean daInviare = true;
	
	private String causaleMancatoInvio;
	
	/* SISO-719 */
	private Integer seqExport;
	private boolean esportata;
	private String idFlusso;
	private Date dataEsportazione;
	private boolean revocabile;
	
	
	/* Metodi di utilità */
	public String getStyle() {
		if (isEsportata()) {
			return STYLE_ESPORTATO;
		}
		
		// NB: qui ci arriviamo solo se non esportata!
		if (!isDaInviare()) {
			return STYLE_DA_NON_INVIARE;
		}
		
		// tutti i casi rimanenti ricadono in "da inviare"
		return STYLE_DA_INVIARE;
	}
	

	

	
	public String dataEsportazioneFormatShort() {
		return shortDateFormat.format(dataEsportazione);
	}
	
	/**
	 * Restituisce <code>true</code> se l'erogazione risulta esportata e non daInviare; <code>false</code> altrimenti
	 * <p>
	 * <i>Questa logica serve in fase di view: si vuole dare evidenza di ciò che è stato già inviato. Un'eventuale erogazione
	 * che risulti da non inviare però non ancora esportata (ad esempio perché ancora aperta) deve restituire false!</i> 
	 */
	public boolean isInviata() {
		return !daInviare && esportata;
	}
	
	
	/* GETTER AND SETTER */
	public boolean isDaInviare() {
		return daInviare;
	}

	public void setDaInviare(boolean daInviare) {
		this.daInviare = daInviare;
	}

	public String getCausaleMancatoInvio() {
		return causaleMancatoInvio;
	}

	public void setCausaleMancatoInvio(String causaleMancatoInvio) {
		this.causaleMancatoInvio = causaleMancatoInvio;
	}

	public Integer getSeqExport() {
		return seqExport;
	}

	public void setSeqExport(Integer seqExport) {
		this.seqExport = seqExport;
	}
	
	public boolean isEsportata() {
		return esportata;
	}

	public void setEsportata(boolean esportata) {
		this.esportata = esportata;
	}
	
	public String getIdFlusso() {
		return idFlusso;
	}

	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}

	public Date getDataEsportazione() {
		return dataEsportazione;
	}

	public void setDataEsportazione(Date dataEsportazione) {
		this.dataEsportazione = dataEsportazione;
	}

	public boolean isRevocabile() {
		return revocabile;
	}

	public void setRevocabile(boolean revocabile) {
		this.revocabile = revocabile;
	}


	@Override
	public String toString() {
		return "EsportazioneDTOView [getStyle()=" + getStyle()
				+ ", isDaInviare()=" + isDaInviare()
				+ ", getCausaleMancatoInvio()=" + getCausaleMancatoInvio()
				+ ", getSeqExport()=" + getSeqExport() + ", isEsportata()="
				+ isEsportata() + ", getIdFlusso()=" + getIdFlusso()
				+ ", getDataEsportazione()=" + getDataEsportazione()
				+ ", isRevocabile()=" + isRevocabile()
				+ ", getBenefAnnoNascita()=" + getBenefAnnoNascita()
				+ ", getBenefLuogoNascita()=" + getBenefLuogoNascita()
				+ ", getBenefSesso()=" + getBenefSesso()
				+ ", getBenefCittadinanza()=" + getBenefCittadinanza()
				+ ", getBenefRegione()=" + getBenefRegione()
				+ ", getBenefComune()=" + getBenefComune()
				+ ", getBenefNazione()=" + getBenefNazione()
				+ ", getNumProtDSU()=" + getNumProtDSU()
				+ ", getAnnoProtDSU()=" + getAnnoProtDSU() + ", getDataDSU()="
				+ getDataDSU() + ", getCodPrestazione()=" + getCodPrestazione()
				+ ", getDenomPrestazione()=" + getDenomPrestazione()
				+ ", getProtDomPrest()=" + getProtDomPrest()
				+ ", getSoggettoCodiceFiscale()=" + getSoggettoCodiceFiscale()
				+ ", getSoggettoCognome()=" + getSoggettoCognome()
				+ ", getSoggettoNome()=" + getSoggettoNome()
				+ ", getCompartAltre()=" + getCompartAltre()
				+ ", getCompartSsn()=" + getCompartSsn()
				+ ", getCompartUtenti()=" + getCompartUtenti()
				+ ", getPercGestitaEnte()=" + getPercGestitaEnte()
				+ ", getValoreGestitaEnte()=" + getValoreGestitaEnte()
				+ ", getDataEsecuzione()=" + getDataEsecuzione()
				+ ", getEnteOperatoreErogante()=" + getEnteOperatoreErogante()
				+ ", getNomeOperatoreErog()=" + getNomeOperatoreErog()
				+ ", getNote()=" + getNote() + ", getNoteAltreCompart()="
				+ getNoteAltreCompart() + ", getCarattere()=" + getCarattere()
				+ ", getNomeBeneficiario()=" + getNomeBeneficiario()
				+ ", getPrestazioneProtocEnte()=" + getPrestazioneProtocEnte()
				+ ", getInterventoId()=" + getInterventoId()
				+ ", getInterventoEsegId()=" + getInterventoEsegId()
				+ ", getInterventoEsegMastId()=" + getInterventoEsegMastId()
				+ ", getDataEsecuzioneA()=" + getDataEsecuzioneA()
				//+ ", getMinDataEsecuzione()=" + getMinDataEsecuzione()
				+ ", getMaxDataEsecuzione()=" + getMaxDataEsecuzione()
				+ ", getDataInizio()=" + getDataInizio() + ", getDataFine()="
				+ getDataFine() + ", getPeriodoErogazione()="
				+ getPeriodoErogazione() + ", getImportoMensile()="
				+ getImportoMensile() + ", getSpesaTestata()="
				+ getSpesaTestata() + ", getSpesaDettaglio()="
				+ getSpesaDettaglio() + ", getSpesa()=" + getSpesa()
				+ ", getPresenzaProvaMezzi()=" + getPresenzaProvaMezzi()
				+ ", getPresaInCarico()=" + getPresaInCarico()
				+ ", getCategoriaSocialeId()=" + getCategoriaSocialeId()
				+ ", getCategoriaSocialeDescrizione()="
				+ getCategoriaSocialeDescrizione() + "]";
	}


	
	
}
