package it.umbriadigitale.interscambio.data.wrapper.encounterevent;

import java.util.List;

import it.umbriadigitale.interscambio.data.wrapper.AssistentePersonaleWrapper;
import it.umbriadigitale.interscambio.data.wrapper.AssistitoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;
import it.umbriadigitale.interscambio.data.wrapper.MedicoPediatraWrapper;
import it.umbriadigitale.interscambio.data.wrapper.SegnalazioneWrapper;
import it.umbriadigitale.interscambio.data.wrapper.reference.AccessoOrientamentoReferenceWrapper;

/**
 * Raccoglie i dati specifici di un EncounterEvent di tipo "Accesso e orientamento"
 */
public class EncounterEventAccessoOrientamentoWrapper extends BaseEncounterEventWrapper {
	private AssistitoWrapper assistito;
	private MedicoPediatraWrapper mmgpdf;
	private List<AssistentePersonaleWrapper> listaAssistentiPersonali;
	private SegnalazioneWrapper segnalazione;
	private AccessoOrientamentoReferenceWrapper accessoOrientamentoReference;
	
	
	public EncounterEventAccessoOrientamentoWrapper(FaseWrapper fase, AssistitoWrapper assistito,
			MedicoPediatraWrapper mmgpdf, List<AssistentePersonaleWrapper> listaAssistentiPersonali,
			SegnalazioneWrapper segnalazione, AccessoOrientamentoReferenceWrapper accessoOrientamentoReference) {
		
		super(fase);
		
		this.assistito = assistito;
		this.mmgpdf = mmgpdf;
		this.listaAssistentiPersonali = listaAssistentiPersonali;
		this.segnalazione = segnalazione;
		this.accessoOrientamentoReference = accessoOrientamentoReference;
	}
	
	
	public AssistitoWrapper getAssistito() {
		return assistito;
	}

	public MedicoPediatraWrapper getMmgpdf() {
		return mmgpdf;
	}

	public List<AssistentePersonaleWrapper> getListaAssistentiPersonali() {
		return listaAssistentiPersonali;
	}

	public SegnalazioneWrapper getSegnalazione() {
		return segnalazione;
	}

	public AccessoOrientamentoReferenceWrapper getAccessoOrientamentoReference() {
		return accessoOrientamentoReference;
	}
}
