package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;

import org.hl7.v3.request.PRSSIN001004ZZMCAIMT700201UV01ControlActProcess;
import org.hl7.v3.request.PRSSIN001004ZZMCAIMT700201UV01Subject2;

import it.umbriadigitale.interscambio.data.wrapper.CartellaSocialeWrapper;

class ControlActProcessFactory {
	
	/*
		<v3:controlActProcess classCode="CACT" moodCode="EVN">
			<v3:subject typeCode="SUBJ">
				<v3:encounterEvent classCode="ENC" moodCode="EVN"> <!-- Accesso e orientamento -->
					[...]
				</v3:encounterEvent>
				<v3:encounterEvent classCode="ENC" moodCode="EVN"> <!-- Valutazione del bisogno -->
					[...]
				</v3:encounterEvent>
				<v3:encounterEvent classCode="ENC" moodCode="EVN"> <!-- Elaborazione del progetto individuale -->
					[...]
				</v3:encounterEvent>
				<v3:encounterEvent classCode="ENC" moodCode="EVN"> <!-- Erogazione del servizio -->
					[...]
				</v3:encounterEvent>
				<v3:encounterEvent classCode="ENC" moodCode="EVN"> <!-- Valutazione finale e conclusione del processo di aiuto  -->
					[...]
				</v3:encounterEvent>
			</v3:subject>
		</v3:controlActProcess>
	 */
	static PRSSIN001004ZZMCAIMT700201UV01ControlActProcess createControlActProcess(CartellaSocialeWrapper cartellaSocialeData) {
		PRSSIN001004ZZMCAIMT700201UV01ControlActProcess controlActProcess = createDefaultControlActProcess();	// oggetto di ritorno
		
		PRSSIN001004ZZMCAIMT700201UV01Subject2 subject = createDefaultSubject();
		
		// aggiungo i cinque Encounter Event:
		
		// "Accesso e orientamento"
		subject.getRealmCodeAndTypeIdAndTemplateId().add(REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZMCAIMT700201UV01Subject2EncounterEvent(
			EncounterEventFactory.createEncounterEventAccessoOrientamento(cartellaSocialeData.getAccessoOrientamentoData())));
		
		// "Valutazione del bisogno"
		subject.getRealmCodeAndTypeIdAndTemplateId().add(REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZMCAIMT700201UV01Subject2EncounterEvent(
			EncounterEventFactory.createEncounterEventValutazioneBisogno(cartellaSocialeData.getValutazioneBisognoData())));
		
		// "Elaborazione del progetto individuale"
		subject.getRealmCodeAndTypeIdAndTemplateId().add(REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZMCAIMT700201UV01Subject2EncounterEvent(
			EncounterEventFactory.createEncounterEventElaborazioneProgettoIndividuale(cartellaSocialeData.getElaborazioneProgettoIndividualeData())));
		
		// "Erogazione del servizio"
		subject.getRealmCodeAndTypeIdAndTemplateId().add(REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZMCAIMT700201UV01Subject2EncounterEvent(
			EncounterEventFactory.createEncounterEventErogazioneServizio(cartellaSocialeData.getErogazioneServizioData())));
		
		// "Valutazione finale e conclusione del processo di aiuto"
		subject.getRealmCodeAndTypeIdAndTemplateId().add(REQUEST_OBJECT_FACTORY.createPRSSIN001004ZZMCAIMT700201UV01Subject2EncounterEvent(
			EncounterEventFactory.createEncounterEventValutazioneFinale(cartellaSocialeData.getValutazioneFinaleData())));
		
		
		controlActProcess.getSubject().add(subject);
		
		return controlActProcess;
	}
}
