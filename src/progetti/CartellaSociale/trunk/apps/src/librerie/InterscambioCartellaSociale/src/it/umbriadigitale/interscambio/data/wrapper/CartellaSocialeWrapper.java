package it.umbriadigitale.interscambio.data.wrapper;

import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventAccessoOrientamentoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventElaborazioneProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventErogazioneServizioWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventValutazioneBisognoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventValutazioneFinaleWrapper;

/**
 * Wrapper di tutti i dati utili di una Cartella Sociale da utilizzare in Interscambio Cartella Sociale
 * 
 * @author Iacopo Sorce
 */
public class CartellaSocialeWrapper {
	private EncounterEventAccessoOrientamentoWrapper accessoOrientamentoData;
	private EncounterEventValutazioneBisognoWrapper valutazioneBisognoData;
	private EncounterEventElaborazioneProgettoIndividualeWrapper elaborazioneProgettoIndividualeData;
	private EncounterEventErogazioneServizioWrapper erogazioneServizioData;
	private EncounterEventValutazioneFinaleWrapper valutazioneFinaleData;
	
	
	public CartellaSocialeWrapper(EncounterEventAccessoOrientamentoWrapper accessoOrientamentoData,
			EncounterEventValutazioneBisognoWrapper valutazioneBisognoData,
			EncounterEventElaborazioneProgettoIndividualeWrapper elaborazioneProgettoIndividualeData,
			EncounterEventErogazioneServizioWrapper erogazioneServizioData,
			EncounterEventValutazioneFinaleWrapper valutazioneFinaleData) {
		
		this.accessoOrientamentoData = accessoOrientamentoData;
		this.valutazioneBisognoData = valutazioneBisognoData;
		this.elaborazioneProgettoIndividualeData = elaborazioneProgettoIndividualeData;
		this.erogazioneServizioData = erogazioneServizioData;
		this.valutazioneFinaleData = valutazioneFinaleData;
	}
	
	
	public EncounterEventAccessoOrientamentoWrapper getAccessoOrientamentoData() {
		return accessoOrientamentoData;
	}

	public EncounterEventValutazioneBisognoWrapper getValutazioneBisognoData() {
		return valutazioneBisognoData;
	}

	public EncounterEventElaborazioneProgettoIndividualeWrapper getElaborazioneProgettoIndividualeData() {
		return elaborazioneProgettoIndividualeData;
	}

	public EncounterEventErogazioneServizioWrapper getErogazioneServizioData() {
		return erogazioneServizioData;
	}

	public EncounterEventValutazioneFinaleWrapper getValutazioneFinaleData() {
		return valutazioneFinaleData;
	}
}
