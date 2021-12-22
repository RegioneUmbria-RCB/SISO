package it.umbriadigitale.interscambio.data.wrapper.observation;

import it.umbriadigitale.interscambio.enums.EObservation;

/**
 * Raccoglie le proprietà per gli elementi <code>observation</code> di un EncounterEvent di tipo "Elaborazione del progetto individuale"
 */
public class ElaborazioneProgettoIndividualeObservationWrapper extends BaseObservationDataWrapper {
	private String frequenzaPrestazioniCode;
	private String servizioSocialeProfessionaleCode;
	private String integrazioneConAssistenzaSanitariaCode;
	private String valoreProgettoAmount;
	private String compartecipazioneEconomicaAssistitoAmount;
	
	
	public ElaborazioneProgettoIndividualeObservationWrapper(String frequenzaPrestazioniCode,
			String servizioSocialeProfessionaleCode, String integrazioneConAssistenzaSanitariaCode,
			String valoreProgettoAmount, String compartecipazioneEconomicaAssistitoAmount) {
		
		this.frequenzaPrestazioniCode = frequenzaPrestazioniCode;
		this.servizioSocialeProfessionaleCode = servizioSocialeProfessionaleCode;
		this.integrazioneConAssistenzaSanitariaCode = integrazioneConAssistenzaSanitariaCode;
		this.valoreProgettoAmount = valoreProgettoAmount;
		this.compartecipazioneEconomicaAssistitoAmount = compartecipazioneEconomicaAssistitoAmount;
		
		populateMap();
	}


	@Override
	protected void populateMap() {
		dataMap.put(EObservation.FREQUENZA_PRESTAZIONI, frequenzaPrestazioniCode);
		dataMap.put(EObservation.SERVIZIO_SOCIALE_PROFESSIONALE, servizioSocialeProfessionaleCode);
		dataMap.put(EObservation.INTEGRAZIONE_CON_ASSISTENZA_SANITARIA, integrazioneConAssistenzaSanitariaCode);
		dataMap.put(EObservation.VALORE_PROGETTO, valoreProgettoAmount);
		dataMap.put(EObservation.COMPARTECIPAZIONE_ECONOMICA_ASSISTITO, compartecipazioneEconomicaAssistitoAmount);
	}
}
