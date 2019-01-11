package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper (DTO) per i dati da visualizzare in Esporta Casellario secondo la struttura a due livelli:
 * testata e righe di dettaglio.
 * <p>
 * L'ordine naturale dell'oggetto è su {@link #getNomeBeneficiario()}, ossia la concatenazione di Cognome e Nome.
 * <p>
 * <i>SISO-719</i>
 */
public class EsportazioneTestataDTO implements Serializable, Comparable<EsportazioneTestataDTO> {
	private static final long serialVersionUID = 1L;
	
	public static final String FREQUENZA_REGOLARE = "REGOLARE";
	public static final String FREQUENZA_IRREGOLARE = "IRREGOLARE";
	public static final String FREQUENZA_UNATANTUM = "UNATANTUM";
	
	// dati testata
	private Long interventoEsegMastId;
	
	private String categoriaSocialeDescrizione;
	private String prestazioneProtocolloEnte;	// Numero protocollo
	private String denominazionePrestazione;
	private String soggettoNome;
	private String soggettoCognome;
	private String soggettoCodiceFiscale;
	private BigDecimal spesaTestata;
	
	/*SISO-738 
	 * campo frequenza: IRREGOLARE, REGOLARE, UNATANTUM
	*/
	private String frequenza="";
	
	// elenco delle righe di dettaglio per questa testata
	private List<EsportazioneDTOView> dettagli = new ArrayList<EsportazioneDTOView>();
	
	
	// ordine alfabetico per nomeBenificiario
	@Override
	public int compareTo(EsportazioneTestataDTO other) {
		return getNomeBeneficiario().compareTo(other.getNomeBeneficiario());
	}
	
	
	/**
	 * Un'istanza di <code>EsportazioneTestataDTO</code> può essere costruita solo a partire da un
	 * <code>dettaglioEsportazione</code>. I dati al livello della testata vengono salvati nelle rispettive proprietà,
	 * e l'oggetto <code>dettaglioEsportazione</code> viene aggiunto alla lista <code>dettagli</code> dell'istanza,
	 * che contiene tutti i dettagli della testata.
	 * <p>
	 * Successivi oggetti {@link EsportazioneDTOView} da aggiungere a questa testata vanno aggiunti tramite il metodo
	 * {@link #aggiungiDettaglioAllaTestata(EsportazioneDTOView)}.
	 * 
	 * @param dettaglioEsportazione	riga di dettaglio della testata da utilizzare per la costruzione dell'istanza
	 */
	public EsportazioneTestataDTO(EsportazioneDTOView dettaglioEsportazione) {
		this.interventoEsegMastId = dettaglioEsportazione.getInterventoEsegMastId();
		
		this.categoriaSocialeDescrizione = dettaglioEsportazione.getCategoriaSocialeDescrizione();
		this.prestazioneProtocolloEnte = dettaglioEsportazione.getPrestazioneProtocEnte();
		this.denominazionePrestazione = dettaglioEsportazione.getDenomPrestazione();
		this.soggettoNome = dettaglioEsportazione.getSoggettoNome();
		this.soggettoCognome = dettaglioEsportazione.getSoggettoCognome();
		this.soggettoCodiceFiscale = dettaglioEsportazione.getSoggettoCodiceFiscale();
		this.spesaTestata = dettaglioEsportazione.getSpesaTestata();
		
		aggiungiDettaglioAllaTestata(dettaglioEsportazione);
	}
	
	/* Metodi di utilità */
	/**
	 * Aggiunge <code>dettaglioEsportazione</code> alla lista dei dettagli per questa testata
	 */
	public void aggiungiDettaglioAllaTestata(EsportazioneDTOView dettaglioEsportazione) {
		dettagli.add(dettaglioEsportazione);
	}

	
	/* GETTERS ad hoc */
	/**
	 * Restituisce "COGNOME NOME"
	 */
	public String getNomeBeneficiario() {
		return String.format("%s %s", soggettoCognome, soggettoNome);
	}
	
	/* GETTERS - non servono Setter */
	public Long getInterventoEsegMastId() {
		return interventoEsegMastId;
	}

	public String getCategoriaSocialeDescrizione() {
		return categoriaSocialeDescrizione;
	}

	public String getPrestazioneProtocolloEnte() {
		return prestazioneProtocolloEnte;
	}

	public String getDenominazionePrestazione() {
		return denominazionePrestazione;
	}

	public String getSoggettoCodiceFiscale() {
		return soggettoCodiceFiscale;
	}

	public BigDecimal getSpesaTestata() {
		return spesaTestata;
	}

	public List<EsportazioneDTOView> getDettagli() {
		return dettagli;
	}
	
	public String getSoggettoNome() {
		return soggettoNome;
	}

	public String getSoggettoCognome() {
		return soggettoCognome;
	}


	@Override
	public String toString() {
		return "EsportazioneTestataDTO [interventoEsegMastId="
				+ interventoEsegMastId + ", categoriaSocialeDescrizione="
				+ categoriaSocialeDescrizione + ", prestazioneProtocolloEnte="
				+ prestazioneProtocolloEnte + ", denominazionePrestazione="
				+ denominazionePrestazione + ", soggettoNome=" + soggettoNome
				+ ", soggettoCognome=" + soggettoCognome
				+ ", soggettoCodiceFiscale=" + soggettoCodiceFiscale
				+ ", spesaTestata=" + spesaTestata + ", dettagli=" + dettagli
				+ "]";
	}


	public String getFrequenza() {
		return frequenza;
	}


	public void setFrequenza(String frequenza) {
		this.frequenza = frequenza;
	}

}
