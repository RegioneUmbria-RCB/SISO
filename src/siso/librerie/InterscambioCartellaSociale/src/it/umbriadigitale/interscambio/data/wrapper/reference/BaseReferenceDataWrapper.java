package it.umbriadigitale.interscambio.data.wrapper.reference;

import java.util.HashMap;
import java.util.Map;

import it.umbriadigitale.interscambio.enums.EReference;

/**
 * Super classe per la gestione di sequenze di elementi <code>reference</code>. Le implementazioni concrete estendono questa classe definendo
 * la propria lista di proprietà da gestire e la relativa mappatura con le istanze di {@link it.umbriadigitale.interscambio.enums.EReference EReference}.
 * 
 * @see	{@link it.umbriadigitale.interscambio.factory.ElementFactory#buildReferenceElements(Map) ElementFactory.buildReferenceElements(Map)}
 */
public abstract class BaseReferenceDataWrapper {
	protected Map<EReference, String> dataMap = new HashMap<>();
	
	/**
	 * Definisce come dev'essere popolata la mappatura {@link it.umbriadigitale.interscambio.enums.EReference EReference}/proprietà
	 */
	abstract protected void populateMap();
	
	
	public final Map<EReference, String> getDataMap() {
		return dataMap;
	}
}
