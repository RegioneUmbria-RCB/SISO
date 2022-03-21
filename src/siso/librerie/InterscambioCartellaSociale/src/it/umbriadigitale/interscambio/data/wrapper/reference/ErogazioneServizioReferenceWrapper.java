package it.umbriadigitale.interscambio.data.wrapper.reference;

import it.umbriadigitale.interscambio.enums.EReference;

/**
 * Raccoglie le proprietà per gli elementi <code>reference</code> di un EncounterEvent di tipo "Erogazione del servizio"
 */
public class ErogazioneServizioReferenceWrapper extends BaseReferenceDataWrapper {
	private String denominazioneStrutturaFuoriRete;
	
	public ErogazioneServizioReferenceWrapper(String denominazioneStrutturaFuoriRete) {
		this.denominazioneStrutturaFuoriRete = denominazioneStrutturaFuoriRete;
		
		populateMap();
	}
	
	
	@Override
	protected void populateMap() {
		dataMap.put(EReference.DENOMINAZIONE_STRUTTURA_FUORI_RETE, denominazioneStrutturaFuoriRete);
	}
}
