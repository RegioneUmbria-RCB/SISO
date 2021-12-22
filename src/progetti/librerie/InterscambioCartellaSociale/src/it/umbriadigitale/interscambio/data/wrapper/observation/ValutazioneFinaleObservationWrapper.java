package it.umbriadigitale.interscambio.data.wrapper.observation;

import it.umbriadigitale.interscambio.enums.EObservation;

/**
 * Raccoglie le proprietà per gli elementi <code>observation</code> di un EncounterEvent di tipo
 * "Valutazione finale e conclusione del processo di aiuto"
 */
public class ValutazioneFinaleObservationWrapper extends BaseObservationDataWrapper {
	private String dataValutazioneFinaleText;
	private String valutazioneFinale;
	private String motivazioneChiusuraProgettoCode;
	private String risorseEconomicheFinaliAmount;
	private String risultatiRaggiunti;
	private String risultatiNonRaggiunti;
	private String nuovoProgettoCode;
	
	
	public ValutazioneFinaleObservationWrapper(String dataValutazioneFinaleText, String valutazioneFinale,
			String motivazioneChiusuraProgettoCode, String risorseEconomicheFinaliAmount, String risultatiRaggiunti,
			String risultatiNonRaggiunti, String nuovoProgettoCode) {
		
		this.dataValutazioneFinaleText = dataValutazioneFinaleText;
		this.valutazioneFinale = valutazioneFinale;
		this.motivazioneChiusuraProgettoCode = motivazioneChiusuraProgettoCode;
		this.risorseEconomicheFinaliAmount = risorseEconomicheFinaliAmount;
		this.risultatiRaggiunti = risultatiRaggiunti;
		this.risultatiNonRaggiunti = risultatiNonRaggiunti;
		this.nuovoProgettoCode = nuovoProgettoCode;
		
		populateMap();
	}
	
	
	@Override
	protected void populateMap() {
		dataMap.put(EObservation.DATA_VALUTAZIONE_FINALE, dataValutazioneFinaleText);
		dataMap.put(EObservation.VALUTAZIONE_FINALE, valutazioneFinale);
		dataMap.put(EObservation.MOTIVAZIONE_CHIUSURA_PROGETTO, motivazioneChiusuraProgettoCode);
		dataMap.put(EObservation.RISORSE_ECONOMICHE_FINALI, risorseEconomicheFinaliAmount);
		dataMap.put(EObservation.RISULTATI_RAGGIUNTI, risultatiRaggiunti);
		dataMap.put(EObservation.RISULTATI_NON_RAGGIUNTI, risultatiNonRaggiunti);
		dataMap.put(EObservation.NUOVO_PROGETTO, nuovoProgettoCode);
	}
}
