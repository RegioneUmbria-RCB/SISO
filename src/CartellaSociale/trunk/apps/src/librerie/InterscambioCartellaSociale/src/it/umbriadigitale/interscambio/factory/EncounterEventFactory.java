package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DatatypesModFactory.*;
import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;
import static it.umbriadigitale.interscambio.factory.ElementFactory.*;

import org.hl7.v3.request.PRSSMT001004ZZEncounterEvent;

import it.umbriadigitale.interscambio.constants.OIDDefaults;
import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.BaseEncounterEventWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventAccessoOrientamentoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventElaborazioneProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventErogazioneServizioWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventValutazioneBisognoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventValutazioneFinaleWrapper;
import it.umbriadigitale.interscambio.enums.EAttender;
import it.umbriadigitale.interscambio.enums.EEncounterEvent;

class EncounterEventFactory implements OIDDefaults {

	/*
		<v3:encounterEvent classCode="ENC" moodCode="EVN"> <!-- Accesso e orientamento -->
			<v3:id root="2.16.840.1.113883.2.9.2.30.3.2" extension="sss"></v3:id> <!-- Identificativo univoco della fase -->
			<v3:code code="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.1" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.1"/> <!-- codice che identifica la fase del processo cui si riferisce il messaggio-->
			<v3:text>Annotazioni testuali che riguardano il contatto</v3:text> <!-- Annotazioni testuali che riguardano il contatto -->
			<v3:statusCode code="Stato_Fase"/> <!-- Stato della fase -->
			<v3:effectiveTime >
				<v3:low value="123"/> <!-- Data ora inizio fase -->
				<v3:high value="123"/> <!-- Data ora fine fase -->
				<v3:dischargeDispositionCode code="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.2" codeSystem="2.16.840.1.113883.2.9.2.30.3.2.3.1.1.2"/> <!-- codice dell'esito della fase -->
			</v3:effectiveTime>
			[...]
		</v3:encounterEvent>
	 */
	private static PRSSMT001004ZZEncounterEvent createEncounterEvent(EEncounterEvent encounterEventType, BaseEncounterEventWrapper data) {
		PRSSMT001004ZZEncounterEvent encounterEvent = createDefaultEncounterEvent();		// oggetto di ritorno
		
		// salvo in variabile locale per comodità
		FaseWrapper fase = data.getFase();
		
		// encounterEvent.id
		encounterEvent.getId().add(createIIRootExtension(OID_IDENTIFICATIVO_FASE, fase.getIdFaseText()));
		
		// encounterEvent.code
		encounterEvent.setCode(createCVCodeCodeSystem(fase.getCodiceFase(), OID_ENCOUNTEREVENT_CODE));
		
		// encounterEvent.text
		encounterEvent.setText(createEDWithText(fase.getAnnotazioni()));
		
		// encounterEvent.statusCode
		encounterEvent.setStatusCode(createCSCode(fase.getStatoFaseCode()));
		
		// encounterEvent.effectiveTime
		encounterEvent.getEffectiveTime().add(
			EffectiveTimeFactory.createEffectiveTimeLowHighDischargeDispositionCodeValue(
				fase.getDataInizioText(), fase.getDataFineText(), fase.getCodiceEsito()));
		
		
		return encounterEvent;
	}
	
	// 3.3.1
	static PRSSMT001004ZZEncounterEvent createEncounterEventAccessoOrientamento(EncounterEventAccessoOrientamentoWrapper accessoOrientamentoData) {
		PRSSMT001004ZZEncounterEvent encounterEvent = createEncounterEvent(EEncounterEvent.ACCESSO_E_ORIENTAMENTO, accessoOrientamentoData);	// oggetto di ritorno
		
		/* Inserisco il contenuto specifico di questo EncounterEvent:
		 * 
		 * 		0...1 subject -> AssistitoWrapper
		 * 		0...1 consultant di tipo MMGPDF -> MedicoPediatraWrapper
		 * 		0...* consultant di tipo Assistente -> AssistentePersonaleWrapper
		 * 		0...1 reason -> SegnalazioneWrapper
		 * 		n 0...1 reference -> AccessoOrientamentoReferenceWrapper
		 */
		
		
		// encounterEvent.subject
		encounterEvent.getSubject().add(SubjectFactory.createSubject(accessoOrientamentoData.getAssistito()));
		
		// encounterEvent.consultant (MMGPDF)
		encounterEvent.getConsultant().add(createConsultantMMGPDF(accessoOrientamentoData.getMmgpdf()));
		
		// encounterEvent.consultant (Assistente)
		encounterEvent.getConsultant().addAll(buildConsultantsAssistentePersonale(accessoOrientamentoData.getListaAssistentiPersonali()));
		
		// encounterEvent.reason
		encounterEvent.getReason().add(createReason(accessoOrientamentoData.getSegnalazione()));
		
		// encounterEvent.reference
		encounterEvent.getReference().addAll(buildReferenceElements(accessoOrientamentoData.getAccessoOrientamentoReference().getDataMap()));
		
		
		return encounterEvent;
	}
	
	// 3.3.2
	static PRSSMT001004ZZEncounterEvent createEncounterEventValutazioneBisogno(EncounterEventValutazioneBisognoWrapper valutazioneBisognoData) {
		// oggetto di ritorno
		PRSSMT001004ZZEncounterEvent encounterEvent = createEncounterEvent(EEncounterEvent.VALUTAZIONE_DEL_BISOGNO, valutazioneBisognoData);
		
		
		/* Inserisco il contenuto specifico di questo EncounterEvent:
		 * 
		 * 		0...1 effectiveTime (Low) -> dataValutazione (String) - NB: questo elemento è in aggiunta a quello di default
		 * 		2x 0...1 attender -> AttenderWrapper
		 * 		n 0...1 reference -> ValutazioneBisognoReferenceWrapper
		 */
		
		
		// encounterEvent.effectiveTime (aggiuntiva rispetto a quella di default, e solamente con un elemento low)
		encounterEvent.getEffectiveTime().add(EffectiveTimeFactory.createEffectiveTimeLow(valutazioneBisognoData.getDataValutazioneText()));
		
		// encounterEvent.attender
		encounterEvent.getAttender().add(createAttender(EAttender.RESPONSABILE_VALUTAZIONE, valutazioneBisognoData.getResponsabileValutazione()));
		encounterEvent.getAttender().add(createAttender(EAttender.CASE_MANAGER, valutazioneBisognoData.getCaseManager()));
		
		// encounterEvent.reference
		encounterEvent.getReference().addAll(buildReferenceElements(valutazioneBisognoData.getValutazioneBisognoReference().getDataMap()));
		
		
		return encounterEvent;
	}
	
	// 3.3.3
	static PRSSMT001004ZZEncounterEvent createEncounterEventElaborazioneProgettoIndividuale(
			EncounterEventElaborazioneProgettoIndividualeWrapper elaborazioneProgettoIndividualeData) {
		
		// oggetto di ritorno
		PRSSMT001004ZZEncounterEvent encounterEvent =
			createEncounterEvent(EEncounterEvent.ELABORAZIONE_PROGETTO_INDIVIDUALE, elaborazioneProgettoIndividualeData);
		
		/* Inserisco il contenuto specifico di questo EncounterEvent:
		 * 
		 * 		2x 0...1 attender -> AttenderWrapper
		 * 		0...1 pertinentInformation2 -> PertinentInformation2ElaborazioneProgettoIndividualeWrapper
		 */
		
		
		// encounterEvent.attender
		encounterEvent.getAttender().add(createAttender(EAttender.RESPONSABILE_PROGETTO, elaborazioneProgettoIndividualeData.getResponsabileProgetto()));
		encounterEvent.getAttender().add(createAttender(EAttender.RESPONSABILE_MONITORAGGIO, elaborazioneProgettoIndividualeData.getResponsabileMonitoraggio()));
		
		// encounterEvent.pertinentInformation2
		encounterEvent.getPertinentInformation2().add(
			PertinentInformation2Factory.createPertinentInformation2ElaborazioneProgettoIndividuale(
				elaborazioneProgettoIndividualeData.getPertinentInformation2Data()));
		
		
		return encounterEvent;
	}
	
	// 3.3.4
	static PRSSMT001004ZZEncounterEvent createEncounterEventErogazioneServizio(EncounterEventErogazioneServizioWrapper erogazioneServizioData) {
		// oggetto di ritorno
		PRSSMT001004ZZEncounterEvent encounterEvent = createEncounterEvent(EEncounterEvent.EROGAZIONE_DEL_SERVIZIO, erogazioneServizioData);
		
		/* Inserisco il contenuto specifico di questo EncounterEvent:
		 * 
		 * 		0...1 reference -> ErogazioneServizioReferenceWrapper
		 * 		0...1 pertinentInformation2 -> PertinentInformation2ErogazioneServizioWrapper
		 */
		
		
		// encounterEvent.reference
		encounterEvent.getReference().addAll(buildReferenceElements(erogazioneServizioData.getErogazioneServizioReference().getDataMap()));
		
		// encounterEvent.pertinentInformation2
		encounterEvent.getPertinentInformation2().add(
			PertinentInformation2Factory.createPertinentInformation2ErogazioneServizio(
				erogazioneServizioData.getPertinentInformation2Data()));
		
		
		return encounterEvent;
	}
	
	// 3.3.5
	static PRSSMT001004ZZEncounterEvent createEncounterEventValutazioneFinale(EncounterEventValutazioneFinaleWrapper valutazioneFinaleData) {
		// oggetto di ritorno
		PRSSMT001004ZZEncounterEvent encounterEvent =
			createEncounterEvent(EEncounterEvent.VALUTAZIONE_FINALE_E_CONCLUSIONE_PROCESSO_AIUTO, valutazioneFinaleData);
		
		/* Inserisco il contenuto specifico di questo EncounterEvent:
		 * 
		 * 		0...1 attender -> AttenderWrapper
		 * 		0...1 pertinentInformation2 -> PertinentInformation2ValutazioneFinaleWrapper
		 */
		
		
		// encounterEvent.attender
		encounterEvent.getAttender().add(createAttender(EAttender.RESPONSABILE_CHIUSURA, valutazioneFinaleData.getResponsabileChiusura()));
		
		// encounterEvent.pertinentInformation2
		encounterEvent.getPertinentInformation2().add(
			PertinentInformation2Factory.createPertinentInformation2ValutazioneFinale(
				valutazioneFinaleData.getPertinentInformation2Data()));
		
		
		return encounterEvent;
	}
}
