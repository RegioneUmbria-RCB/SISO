package it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2;

import java.util.List;

import it.umbriadigitale.interscambio.data.wrapper.ObiettivoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.PrestazioneWrapper;
import it.umbriadigitale.interscambio.data.wrapper.ProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.observation.ElaborazioneProgettoIndividualeObservationWrapper;

/**
 * Raccoglie i dati per la costruzione dell'elemento <code>pertinentInformation2</code> per un EncounterEvent di tipo
 * "Elaborazione del progetto individuale"
 */
public class PertinentInformation2ElaborazioneProgettoIndividualeWrapper {
	private ProgettoIndividualeWrapper progettoIndividuale;
	private List<ObiettivoWrapper> listaObiettivi;
	private ElaborazioneProgettoIndividualeObservationWrapper elaborazioneProgettoIndividualeObservation;
	private List<PrestazioneWrapper> listaPrestazioni;
	
	
	public PertinentInformation2ElaborazioneProgettoIndividualeWrapper(ProgettoIndividualeWrapper progettoIndividuale,
			List<ObiettivoWrapper> listaObiettivi,
			ElaborazioneProgettoIndividualeObservationWrapper elaborazioneProgettoIndividualeObservation,
			List<PrestazioneWrapper> listaPrestazioni) {
		super();
		this.progettoIndividuale = progettoIndividuale;
		this.listaObiettivi = listaObiettivi;
		this.elaborazioneProgettoIndividualeObservation = elaborazioneProgettoIndividualeObservation;
		this.listaPrestazioni = listaPrestazioni;
	}
	
	
	public ProgettoIndividualeWrapper getProgettoIndividuale() {
		return progettoIndividuale;
	}

	public List<ObiettivoWrapper> getListaObiettivi() {
		return listaObiettivi;
	}

	public ElaborazioneProgettoIndividualeObservationWrapper getElaborazioneProgettoIndividualeObservation() {
		return elaborazioneProgettoIndividualeObservation;
	}

	public List<PrestazioneWrapper> getListaPrestazioni() {
		return listaPrestazioni;
	}
}
