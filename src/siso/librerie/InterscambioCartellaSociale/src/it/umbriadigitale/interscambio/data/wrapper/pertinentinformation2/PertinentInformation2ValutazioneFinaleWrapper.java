package it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2;

import it.umbriadigitale.interscambio.data.wrapper.observation.ValutazioneFinaleObservationWrapper;

/**
 * Raccoglie i dati per la costruzione dell'elemento <code>pertinentInformation2</code> per un EncounterEvent di tipo
 * "Valutazione finale e conclusione del processo di aiuto"
 */
public class PertinentInformation2ValutazioneFinaleWrapper {
	private String dataChiusuraProgettoText;
	private ValutazioneFinaleObservationWrapper valutazioneFinaleObservation;
	
	
	public PertinentInformation2ValutazioneFinaleWrapper(String dataChiusuraProgettoText, ValutazioneFinaleObservationWrapper valutazioneFinaleObservation) {
		this.dataChiusuraProgettoText = dataChiusuraProgettoText;
		this.valutazioneFinaleObservation = valutazioneFinaleObservation;
	}
	
	
	public String getDataChiusuraProgettoText() {
		return dataChiusuraProgettoText;
	}

	public ValutazioneFinaleObservationWrapper getValutazioneFinaleObservation() {
		return valutazioneFinaleObservation;
	}
}
