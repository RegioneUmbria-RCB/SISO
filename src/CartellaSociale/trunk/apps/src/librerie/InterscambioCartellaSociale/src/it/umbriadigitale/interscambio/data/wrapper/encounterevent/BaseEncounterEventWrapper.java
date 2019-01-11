package it.umbriadigitale.interscambio.data.wrapper.encounterevent;

import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;

/**
 * Raccoglie i dati comuni a qualsiasi tipologia di EncounterEvent. Le implementazioni concrete estendono questa classe,
 * aggiungendo i dati dello specifico EncounterEvent.
 */
public abstract class BaseEncounterEventWrapper {
	private FaseWrapper fase;
	
	
	public BaseEncounterEventWrapper(FaseWrapper fase) {
		this.fase = fase;
	}
	
	
	public FaseWrapper getFase() {
		return fase;
	}
}
