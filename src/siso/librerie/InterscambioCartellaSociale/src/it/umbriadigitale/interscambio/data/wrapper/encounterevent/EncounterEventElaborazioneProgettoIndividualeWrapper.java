package it.umbriadigitale.interscambio.data.wrapper.encounterevent;

import it.umbriadigitale.interscambio.data.wrapper.AttenderWrapper;
import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ElaborazioneProgettoIndividualeWrapper;

/**
 * Raccoglie i dati specifici di un EncounterEvent di tipo "Elaborazione del progetto individuale"
 */
public class EncounterEventElaborazioneProgettoIndividualeWrapper extends BaseEncounterEventWrapper {
	private AttenderWrapper responsabileProgetto;
	private AttenderWrapper responsabileMonitoraggio;
	private PertinentInformation2ElaborazioneProgettoIndividualeWrapper pertinentInformation2Data;
	
	
	public EncounterEventElaborazioneProgettoIndividualeWrapper(FaseWrapper fase, AttenderWrapper responsabileProgetto,
			AttenderWrapper responsabileMonitoraggio,
			PertinentInformation2ElaborazioneProgettoIndividualeWrapper pertinentInformation2Data) {
		
		super(fase);
		
		this.responsabileProgetto = responsabileProgetto;
		this.responsabileMonitoraggio = responsabileMonitoraggio;
		this.pertinentInformation2Data = pertinentInformation2Data;
	}
	
	
	public AttenderWrapper getResponsabileProgetto() {
		return responsabileProgetto;
	}

	public AttenderWrapper getResponsabileMonitoraggio() {
		return responsabileMonitoraggio;
	}

	public PertinentInformation2ElaborazioneProgettoIndividualeWrapper getPertinentInformation2Data() {
		return pertinentInformation2Data;
	}
}
