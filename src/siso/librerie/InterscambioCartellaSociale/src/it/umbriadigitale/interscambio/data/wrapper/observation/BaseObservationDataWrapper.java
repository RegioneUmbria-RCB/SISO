package it.umbriadigitale.interscambio.data.wrapper.observation;

import java.util.HashMap;
import java.util.Map;

import it.umbriadigitale.interscambio.enums.EObservation;

/**
 * Super classe per la gestione di sequenze di elementi <code>observation</code>. Le implementazioni concrete estendono questa classe definendo
 * la propria lista di proprietà da gestire e la relativa mappatura con le istanze di
 * {@link it.umbriadigitale.interscambio.enums.EObservation EObservation}.
 * 
 * @see	{@link it.umbriadigitale.interscambio.factory.ElementFactory#buildComponent2ObservationElements(Map) ElementFactory.buildComponent2ObservationElements(Map)}
 */
public abstract class BaseObservationDataWrapper {
	protected Map<EObservation, String> dataMap = new HashMap<>();
	
	/**
	 * Definisce come dev'essere popolata la mappatura {@link it.umbriadigitale.interscambio.enums.EObservation EObservation}/proprietà
	 */
	abstract protected void populateMap();
	
	
	public final Map<EObservation, String> getDataMap() {
		return dataMap;
	}
}
