package it.umbriadigitale.interscambio.data.wrapper.encounterevent;

import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ErogazioneServizioWrapper;
import it.umbriadigitale.interscambio.data.wrapper.reference.ErogazioneServizioReferenceWrapper;

/**
 * Raccoglie i dati specifici di un EncounterEvent di tipo "Erogazione del servizio"
 */
public class EncounterEventErogazioneServizioWrapper extends BaseEncounterEventWrapper {
	private ErogazioneServizioReferenceWrapper erogazioneServizioReference;
	private PertinentInformation2ErogazioneServizioWrapper pertinentInformation2Data;
	
	
	public EncounterEventErogazioneServizioWrapper(FaseWrapper fase,
			ErogazioneServizioReferenceWrapper erogazioneServizioReference,
			PertinentInformation2ErogazioneServizioWrapper pertinentInformation2Data) {
		
		super(fase);
		
		this.erogazioneServizioReference = erogazioneServizioReference;
		this.pertinentInformation2Data = pertinentInformation2Data;
	}


	public ErogazioneServizioReferenceWrapper getErogazioneServizioReference() {
		return erogazioneServizioReference;
	}

	public PertinentInformation2ErogazioneServizioWrapper getPertinentInformation2Data() {
		return pertinentInformation2Data;
	}
}
