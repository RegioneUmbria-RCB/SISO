package it.umbriadigitale.interscambio.data.wrapper.encounterevent;

import it.umbriadigitale.interscambio.data.wrapper.AttenderWrapper;
import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ValutazioneFinaleWrapper;

/**
 * Raccoglie i dati specifici di un EncounterEvent di tipo "Valutazione finale e conclusione del processo di aiuto"
 */
public class EncounterEventValutazioneFinaleWrapper extends BaseEncounterEventWrapper {
	private AttenderWrapper responsabileChiusura;
	private PertinentInformation2ValutazioneFinaleWrapper pertinentInformation2Data;
	
	
	public EncounterEventValutazioneFinaleWrapper(FaseWrapper fase, AttenderWrapper responsabileChiusura,
			PertinentInformation2ValutazioneFinaleWrapper pertinentInformation2Data) {
		
		super(fase);
		
		this.responsabileChiusura = responsabileChiusura;
		this.pertinentInformation2Data = pertinentInformation2Data;
	}


	public AttenderWrapper getResponsabileChiusura() {
		return responsabileChiusura;
	}

	public PertinentInformation2ValutazioneFinaleWrapper getPertinentInformation2Data() {
		return pertinentInformation2Data;
	}
}
