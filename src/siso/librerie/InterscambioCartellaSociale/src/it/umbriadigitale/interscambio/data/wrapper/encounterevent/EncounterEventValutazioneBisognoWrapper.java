package it.umbriadigitale.interscambio.data.wrapper.encounterevent;

import it.umbriadigitale.interscambio.data.wrapper.AttenderWrapper;
import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;
import it.umbriadigitale.interscambio.data.wrapper.reference.ValutazioneBisognoReferenceWrapper;

/**
 * Raccoglie i dati specifici di un EncounterEvent di tipo "Valutazione del bisogno"
 */
public class EncounterEventValutazioneBisognoWrapper extends BaseEncounterEventWrapper {
	private String dataValutazioneText;
	private AttenderWrapper responsabileValutazione;
	private AttenderWrapper caseManager;
	private ValutazioneBisognoReferenceWrapper valutazioneBisognoReference;
	
	
	public EncounterEventValutazioneBisognoWrapper(FaseWrapper fase, String dataValutazioneText,
			AttenderWrapper responsabileValutazione, AttenderWrapper caseManager,
			ValutazioneBisognoReferenceWrapper valutazioneBisognoReference) {
		
		super(fase);
		
		this.dataValutazioneText = dataValutazioneText;
		this.responsabileValutazione = responsabileValutazione;
		this.caseManager = caseManager;
		this.valutazioneBisognoReference = valutazioneBisognoReference;
	}
	
	
	public String getDataValutazioneText() {
		return dataValutazioneText;
	}

	public AttenderWrapper getResponsabileValutazione() {
		return responsabileValutazione;
	}

	public AttenderWrapper getCaseManager() {
		return caseManager;
	}

	public ValutazioneBisognoReferenceWrapper getValutazioneBisognoReference() {
		return valutazioneBisognoReference;
	}
}
