package it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2;

import java.util.List;

import it.umbriadigitale.interscambio.data.wrapper.PrestazioneWrapper;
import it.umbriadigitale.interscambio.data.wrapper.observation.ErogazioneServizioObservationWrapper;

/**
 * Raccoglie i dati per la costruzione dell'elemento <code>pertinentInformation2</code> per un EncounterEvent di tipo
 * "Erogazione del servizio"
 */
public class PertinentInformation2ErogazioneServizioWrapper {
	private ErogazioneServizioObservationWrapper erogazioneServizioObservation;
	private List<PrestazioneWrapper> listaPrestazioni;
	
	
	public PertinentInformation2ErogazioneServizioWrapper(
			ErogazioneServizioObservationWrapper erogazioneServizioObservation,
			List<PrestazioneWrapper> listaPrestazioni) {
		
		this.erogazioneServizioObservation = erogazioneServizioObservation;
		this.listaPrestazioni = listaPrestazioni;
	}
	
	
	public ErogazioneServizioObservationWrapper getErogazioneServizioObservation() {
		return erogazioneServizioObservation;
	}

	public List<PrestazioneWrapper> getListaPrestazioni() {
		return listaPrestazioni;
	}
}
