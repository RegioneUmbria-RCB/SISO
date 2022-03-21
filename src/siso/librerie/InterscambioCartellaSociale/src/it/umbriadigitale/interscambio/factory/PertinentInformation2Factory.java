package it.umbriadigitale.interscambio.factory;

import static it.umbriadigitale.interscambio.factory.DatatypesModFactory.*;
import static it.umbriadigitale.interscambio.factory.DefaultElementFactory.*;
import static it.umbriadigitale.interscambio.factory.ElementFactory.*;

import org.hl7.v3.request.PRSSMT001004ZZPertinentInformation2;
import org.hl7.v3.request.PRSSMT999005ZZCarePlan;

import it.umbriadigitale.interscambio.data.wrapper.ProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ElaborazioneProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ErogazioneServizioWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ValutazioneFinaleWrapper;

class PertinentInformation2Factory {

	/*
		<v3:pertinentInformation2 typeCode="PERT">
			<v3:carePlan classCode="PCPR" moodCode="INT">
				[...]
			</v3:carePlan>
		</v3:pertinentInformation2>
	 */
	private static PRSSMT001004ZZPertinentInformation2 createPertinentInformation2() {
		PRSSMT001004ZZPertinentInformation2 pertinentInformation2 = createDefaultPertinentInformation2();	// oggetto di ritorno
		
		PRSSMT999005ZZCarePlan carePlan = createDefaultCarePlan();
		pertinentInformation2.setCarePlan(carePlan);
		
		return pertinentInformation2;
	}
	
	/*
		<v3:id root="2.16.840.1.113883.2.9.2.30.3.2.3.1.3.1" extension="Codice_del_sprogetto"/> <!-- Codice progetto -->
		<v3:title>oggetto del progetto</v3:title> <!-- Oggetto del progetto individuale -->
		<v3:effectiveTime>
			<v3:low value="12092016"/> <!-- Data inizio progetto -->
			<v3:high value="12092017"/> <!-- Data fine progetto -->
		</v3:effectiveTime>
		<v3:finalGoal> <!-- Obiettivi -->
			[...]
		</v3:finalGoal>
		<v3:component2 typeCode="COMP">
			<v3:observation> <!-- Frequenza prestazioni-->
				[...]
			</v3:observation>
		</v3:component2>
		<v3:component2 typeCode="COMP">  <!-- servizi da erogare -->
			<v3:act>
				[...]
			</v3:act>
		</v3:component2>
	 */
	static PRSSMT001004ZZPertinentInformation2 createPertinentInformation2ElaborazioneProgettoIndividuale(
			PertinentInformation2ElaborazioneProgettoIndividualeWrapper elaborazioneProgettoIndividualeData) {
		
		PRSSMT001004ZZPertinentInformation2 pertinentInformation2 = createPertinentInformation2();	// oggetto di ritorno
		
		// salvo in variabile locale per comodità
		PRSSMT999005ZZCarePlan carePlan = pertinentInformation2.getCarePlan();
		ProgettoIndividualeWrapper progettoIndividuale = elaborazioneProgettoIndividualeData.getProgettoIndividuale();
		
		/* Inserisco il contenuto specifico di questa pertinentInformation2.carePlan:
		 * 
		 * 		0...1 id -> ProgettoIndividualeWrapper
		 * 		0...1 title -> ProgettoIndividualeWrapper
		 * 		0...1 effectiveTime (LowHigh) -> ProgettoIndividualeWrapper
		 * 		0...* finalGoal -> List<ObiettivoWrapper>
		 * 		n 0...1 component2.observation -> ElaborazioneProgettoIndividualeObservationWrapper
		 * 		0...* component2.act -> List<PrestazioneWrapper>
		 */
		
		
		// pertinentInformation2.carePlan.id
		carePlan.getId().add(createIIRootExtension(OID_CODICE_PROGETTO, progettoIndividuale.getCodiceProgetto()));
		
		// pertinentInformation2.carePlan.title
		carePlan.setTitle(createSTWithText(progettoIndividuale.getOggetto()));
		
		// pertinentInformation2.carePlan.effectiveTime (LowHigh)
		carePlan.setEffectiveTime(
			EffectiveTimeFactory.createEffectiveTimeLowHigh(progettoIndividuale.getDataInizioText(), progettoIndividuale.getDataFineText()));
		
		// pertinentInformation2.carePlan.finalGoal
		carePlan.getFinalGoal().addAll(buildFinalGoals(elaborazioneProgettoIndividualeData.getListaObiettivi()));
		
		// pertinentInformation2.carePlan.component2.observation
		carePlan.getComponent2().addAll(
			buildComponent2ObservationElements(elaborazioneProgettoIndividualeData.getElaborazioneProgettoIndividualeObservation().getDataMap()));
		
		// pertinentInformation2.carePlan.component2.act
		carePlan.getComponent2().addAll(buildComponent2Acts(elaborazioneProgettoIndividualeData.getListaPrestazioni()));
		
		
		return pertinentInformation2;
	}
	
	/*
		<v3:component2 typeCode="COMP">
			<v3:observation> <!-- UdO sociale -->
				[...]
			</v3:observation>
		</v3:component2>
		<v3:component2 typeCode="COMP">  <!-- servizi da erogare -->
			<v3:act>
				[...]
			</v3:act>
		</v3:component2>
	 */
	static PRSSMT001004ZZPertinentInformation2 createPertinentInformation2ErogazioneServizio(
			PertinentInformation2ErogazioneServizioWrapper erogazioneServizioData) {
		
		PRSSMT001004ZZPertinentInformation2 pertinentInformation2 = createPertinentInformation2();	// oggetto di ritorno
		
		// salvo in variabile locale per comodità
		PRSSMT999005ZZCarePlan carePlan = pertinentInformation2.getCarePlan();
		
		
		/* Inserisco il contenuto specifico di questa pertinentInformation2.carePlan:
		 * 
		 * 		n 0...1 component2.observation -> ErogazioneServizioObservationWrapper
		 * 		0...* component2.act -> List<PrestazioneWrapper>
		 */
		
		// pertinentInformation2.carePlan.component2.observation
		carePlan.getComponent2().addAll(
			buildComponent2ObservationElements(erogazioneServizioData.getErogazioneServizioObservation().getDataMap()));
		
		// pertinentInformation2.carePlan.component2.act
		carePlan.getComponent2().addAll(buildComponent2Acts(erogazioneServizioData.getListaPrestazioni()));
		
		
		return pertinentInformation2;
	}
	
	/*
		<v3:effectiveTime>
			<v3:high value="1221"/> <!--Data chiusura progetto-->
		</v3:effectiveTime>
		<v3:component2 typeCode="COMP">
			<v3:observation> <!-- Data valutazione finale -->
				[...]
			</v3:observation>
		</v3:component2>
	 */
	static PRSSMT001004ZZPertinentInformation2 createPertinentInformation2ValutazioneFinale(
			PertinentInformation2ValutazioneFinaleWrapper valutazioneFinaleData) {
		
		PRSSMT001004ZZPertinentInformation2 pertinentInformation2 = createPertinentInformation2();	// oggetto di ritorno
		
		// salvo in variabile locale per comodità
		PRSSMT999005ZZCarePlan carePlan = pertinentInformation2.getCarePlan();
		
		/* Inserisco il contenuto specifico di questa pertinentInformation2.carePlan:
		 * 
		 * 		0...1 effectiveTime (High) -> PertinentInformation2ValutazioneFinaleWrapper
		 * 		n 0...1 component2.observation -> ValutazioneFinaleObservationWrapper
		 */
		
		
		// pertinentInformation2.carePlan.effectiveTime (High)
		carePlan.setEffectiveTime(EffectiveTimeFactory.createEffectiveTimeHigh(valutazioneFinaleData.getDataChiusuraProgettoText()));
		
		// pertinentInformation2.carePlan.component2
		carePlan.getComponent2().addAll(
			buildComponent2ObservationElements(valutazioneFinaleData.getValutazioneFinaleObservation().getDataMap()));
		
		
		return pertinentInformation2;
	}
}
