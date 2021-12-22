package it.webred.cs.csa.web.manbean.interscambio;

import java.util.ArrayList;
import java.util.List;

import it.umbriadigitale.interscambio.data.wrapper.AssistentePersonaleWrapper;
import it.umbriadigitale.interscambio.data.wrapper.AssistitoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.AttenderWrapper;
import it.umbriadigitale.interscambio.data.wrapper.CartellaSocialeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.FaseWrapper;
import it.umbriadigitale.interscambio.data.wrapper.MedicoPediatraWrapper;
import it.umbriadigitale.interscambio.data.wrapper.ObiettivoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.PersonalRelationshipWrapper;
import it.umbriadigitale.interscambio.data.wrapper.PrestazioneWrapper;
import it.umbriadigitale.interscambio.data.wrapper.ProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.SegnalazioneWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventAccessoOrientamentoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventElaborazioneProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventErogazioneServizioWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventValutazioneBisognoWrapper;
import it.umbriadigitale.interscambio.data.wrapper.encounterevent.EncounterEventValutazioneFinaleWrapper;
import it.umbriadigitale.interscambio.data.wrapper.observation.ElaborazioneProgettoIndividualeObservationWrapper;
import it.umbriadigitale.interscambio.data.wrapper.observation.ErogazioneServizioObservationWrapper;
import it.umbriadigitale.interscambio.data.wrapper.observation.ValutazioneFinaleObservationWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ElaborazioneProgettoIndividualeWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ErogazioneServizioWrapper;
import it.umbriadigitale.interscambio.data.wrapper.pertinentinformation2.PertinentInformation2ValutazioneFinaleWrapper;
import it.umbriadigitale.interscambio.data.wrapper.reference.AccessoOrientamentoReferenceWrapper;
import it.umbriadigitale.interscambio.data.wrapper.reference.ErogazioneServizioReferenceWrapper;
import it.umbriadigitale.interscambio.data.wrapper.reference.ValutazioneBisognoReferenceWrapper;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsACaso;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.ejb.dto.BaseDTO;

/**
 * Classe di utilità per la costruzione di un oggetto {@link it.umbriadigitale.interscambio.data.wrapper.CartellaSocialeWrapper CartellaSocialeWrapper}
 * a partire da un caso, da utilizzare per le operazioni di Interscambio Cartella Sociale
 * 
 * @author Iacopo Sorce
 */
class CartellaSocialeDataBuilder extends BaseInterscambioCartellaSociale {
	private static final String UNKNOWN_VALUE = "XXX";
	
	CartellaSocialeWrapper buildCartellaSocialeData(CsACaso casoSelezionato) {
		// costruisco il contesto con tutte le strutture dati utili
		SsScheda mostRecentScheda = ottieniUltimaSchedaPerCodiceFiscale(casoSelezionato.getCsASoggetto().getCsAAnagrafica().getCf());
		
		CartellaSocialeDataContext context = new CartellaSocialeDataContext(casoSelezionato, mostRecentScheda);
		
		
		// costruisco CartellaSocialeWrapper un pezzo per volta
		
		// FaseWrapper è comune a tutti gli EncounterEvent
		FaseWrapper fase = new FaseWrapper(
			UNKNOWN_VALUE,	// idFase
			UNKNOWN_VALUE,	// codiceFase
			UNKNOWN_VALUE,	// annotazioni
			UNKNOWN_VALUE,	// statoFase
			UNKNOWN_VALUE,	// dataInizio
			UNKNOWN_VALUE,	// dataFine
			UNKNOWN_VALUE);	// codiceEsito
		
		// EncounterEvent "Accesso e Orientamento"
		EncounterEventAccessoOrientamentoWrapper accessoOrientamentoData = buildAccessoOrientamentoData(fase, context);
		
		// EncounterEvent "Valutazione del bisogno"
		EncounterEventValutazioneBisognoWrapper valutazioneBisognoData = buildValutazioneBisognoData(fase, context);
		
		// EncounterEvent "Elaborazione del progetto individuale"
		EncounterEventElaborazioneProgettoIndividualeWrapper elaborazioneProgettoIndividualeData =
			buildElaborazioneProgettoIndividualeData(fase, context);
		
		// EncounterEvent "Erogazione del servizio"
		EncounterEventErogazioneServizioWrapper erogazioneServizioData = buildErogazioneServizioData(fase, context);
		
		// EncounterEvent "Valutazione finale e conclusione del processo di aiuto"
		EncounterEventValutazioneFinaleWrapper valutazioneFinaleData = buildValutazioneFinaleData(fase, context);
		
		
		return new CartellaSocialeWrapper(
			accessoOrientamentoData,
			valutazioneBisognoData,
			elaborazioneProgettoIndividualeData,
			erogazioneServizioData,
			valutazioneFinaleData);
	}
	
	private List<SsScheda> ottieniSchedePerCodiceFiscale(String codiceFiscale) {
		BaseDTO codiceFiscaleWrapper = new BaseDTO();
		fillEnte(codiceFiscaleWrapper);
		codiceFiscaleWrapper.setObj(codiceFiscale);
		
		return schedaSessionService.readSchedeByCF(codiceFiscaleWrapper);
	}
	
	private SsScheda ottieniUltimaSchedaPerCodiceFiscale(String codiceFiscale) {
		List<SsScheda> listaSchede = ottieniSchedePerCodiceFiscale(codiceFiscale);
		
		return listaSchede.size() > 0 ? listaSchede.get(0) : null;
	}
	

	private EncounterEventAccessoOrientamentoWrapper buildAccessoOrientamentoData(FaseWrapper fase, CartellaSocialeDataContext context) {
		AssistitoWrapper assistito = buildAssistito(context);
		MedicoPediatraWrapper mmgpdf = buildMedicoPediatra(context);
		List<AssistentePersonaleWrapper> assistentiPersonali = buildListaAssistentiPersonali(context);
		SegnalazioneWrapper segnalazione = buildSegnalazione(context);
		AccessoOrientamentoReferenceWrapper accessoOrientamentoReference = buildAccessoOrientamentoReference(context);
		
		return new EncounterEventAccessoOrientamentoWrapper(fase, assistito, mmgpdf, assistentiPersonali, segnalazione, accessoOrientamentoReference);
	}
	
	private AssistitoWrapper buildAssistito(CartellaSocialeDataContext context) {
		// salvo in variabili locali per comodità
		CsAAnagrafica anagraficaAssistito = context.getCaso().getCsASoggetto().getCsAAnagrafica();
		
		// familiari
		List<PersonalRelationshipWrapper> familiari = new ArrayList<PersonalRelationshipWrapper>();
		
		// TODO fillFamiliari
		
		// rete sociale
		List<PersonalRelationshipWrapper> reteSociale = new ArrayList<PersonalRelationshipWrapper>();
		
		// TODO fillReteSociale
		
		
		// TODO gestione dell'id se Codice Fiscale o meno
		
		
		return new AssistitoWrapper(
			UNKNOWN_VALUE,		// id
			UNKNOWN_VALUE,		// comuneResidenza
			UNKNOWN_VALUE,		// regioneResidenza
			UNKNOWN_VALUE,		// nazioneResidenza
			UNKNOWN_VALUE,		// comuneDomicilio
			anagraficaAssistito.getTel(),		// telefono
			anagraficaAssistito.getCell(),		// cellulare
			anagraficaAssistito.getEmail(),		// mail
			UNKNOWN_VALUE,		// tipoDocumento
			UNKNOWN_VALUE,		// estremiDocumento
			anagraficaAssistito.getNome(),		// nome
			anagraficaAssistito.getCognome(),	// cognome
			anagraficaAssistito.getSesso(),		// genere
			UNKNOWN_VALUE,		// dataNascita
			UNKNOWN_VALUE,		// statoCivile
			UNKNOWN_VALUE,		// titoloStudio
			UNKNOWN_VALUE,		// cittadinanza
			UNKNOWN_VALUE,		// secondaCittadinanza
			UNKNOWN_VALUE,		// tesseraTEAM
			UNKNOWN_VALUE,		// codiceFiscale (se non già nell'id)
			UNKNOWN_VALUE,		// atsResidenza
			UNKNOWN_VALUE,		// asstResidenza
			UNKNOWN_VALUE,		// ambitoResidenza
			UNKNOWN_VALUE,		// comuneNascita
			familiari,				// nucleoFamiliare
			reteSociale);			// reteSociale
	}

	private MedicoPediatraWrapper buildMedicoPediatra(CartellaSocialeDataContext context) {
		return new MedicoPediatraWrapper(
			UNKNOWN_VALUE,		// nome
			UNKNOWN_VALUE,		// cognome
			UNKNOWN_VALUE);		// codiceFiscale
	}

	private List<AssistentePersonaleWrapper> buildListaAssistentiPersonali(CartellaSocialeDataContext context) {
		List<AssistentePersonaleWrapper> assistentiPersonali = new ArrayList<AssistentePersonaleWrapper>();
		
		// TODO fillAssistentiPersonali
		
		return assistentiPersonali;
	}

	private SegnalazioneWrapper buildSegnalazione(CartellaSocialeDataContext context) {
		return new SegnalazioneWrapper(
			UNKNOWN_VALUE,		// nome
			UNKNOWN_VALUE,		// cognome
			UNKNOWN_VALUE,		// codiceFiscale
			UNKNOWN_VALUE,		// dataSegnalazione
			UNKNOWN_VALUE,		// tipologiaEnteSegnalante
			UNKNOWN_VALUE,		// codiceEnteSegnalante
			UNKNOWN_VALUE,		// denominazioneEnteSegnalante
			UNKNOWN_VALUE);		// bisognoEspresso
	}

	private AccessoOrientamentoReferenceWrapper buildAccessoOrientamentoReference(CartellaSocialeDataContext context) {
		return new AccessoOrientamentoReferenceWrapper(
			"tipologiaRichiestaCode",
			"modalitaContattoCode",
			"ambitoInterventoRichiestoCode",
			"composizioneNucleoFamiliareCode",
			"numerositaNucleoFamiliareCode",
			"numerositaReteSocialeCode",
			"provenienzaCode",
			"areaRedditualeCode",
			"dsuCode",
			"tipologiaIseeCode",
			"iseeAmount",
			"occupazioneAssistitoCode",
			"codiceEnteRilevatore",
			"denominazioneEnteRilevatore");
	}
	

	private EncounterEventValutazioneBisognoWrapper buildValutazioneBisognoData(FaseWrapper fase, CartellaSocialeDataContext context) {
		String dataValutazione = buildDataValutazione(context);
		AttenderWrapper responsabileValutazione = buildResponsabileValutazione(context);
		AttenderWrapper caseManager = buildCaseManager(context);
		ValutazioneBisognoReferenceWrapper valutazioneBisognoReference = buildValutazioneBisognoReference(context);
		
		return new EncounterEventValutazioneBisognoWrapper(fase, dataValutazione, responsabileValutazione, caseManager, valutazioneBisognoReference);
	}
	
	private String buildDataValutazione(CartellaSocialeDataContext context) {
		return UNKNOWN_VALUE;
	}

	private AttenderWrapper buildResponsabileValutazione(CartellaSocialeDataContext context) {
		return new AttenderWrapper(
			UNKNOWN_VALUE,		// id
			UNKNOWN_VALUE);		// code
	}

	private AttenderWrapper buildCaseManager(CartellaSocialeDataContext context) {
		return new AttenderWrapper(
			UNKNOWN_VALUE,		// id
			UNKNOWN_VALUE);		// code
	}

	private ValutazioneBisognoReferenceWrapper buildValutazioneBisognoReference(CartellaSocialeDataContext context) {
		List<String> protesi = new ArrayList<String>();
		
		// TODO fillProtesi
		
		return new ValutazioneBisognoReferenceWrapper(
			"areeInterventoSocialeCode",
			"esenzioniTicketCode",
			"denominazioneServiziSociosanitariPersonaCarico",
			"serviziSociosanitariGiaErogatiCode",
			"invaliditaCode",
			"indennitaFrequenzaCode",
			"previdenzeSocialiINPSCode",
			"assegnoPensioneInvaliditaCivileCode",
			"utenteConPatologiaPsichiatricaCode",
			"patologieCorrenti",
			"portatoreProtesiAusiliCode",
			"motivazioneAffidoCode",
			"valutazioneNecessitaCureCode",
			"necessitaInterventiSociosanitariCode",
			"necessitaInterventiSocialiCode",
			"fonteDerivazioneValutazioneCode",
			protesi);
	}
	

	private EncounterEventElaborazioneProgettoIndividualeWrapper buildElaborazioneProgettoIndividualeData(FaseWrapper fase, CartellaSocialeDataContext context) {
		AttenderWrapper responsabileProgetto = buildResponsabileProgetto(context);
		AttenderWrapper responsabileMonitoraggio = buildResponsabileMonitoraggio(context);
		PertinentInformation2ElaborazioneProgettoIndividualeWrapper elaborazioneProgettoIndividualeObservation =
			buildElaborazioneProgettoIndividualeObservation(context);
		
		return new EncounterEventElaborazioneProgettoIndividualeWrapper(fase, responsabileProgetto, responsabileMonitoraggio, elaborazioneProgettoIndividualeObservation);
	}
	
	private AttenderWrapper buildResponsabileProgetto(CartellaSocialeDataContext context) {
		return new AttenderWrapper(
			UNKNOWN_VALUE,		// id
			UNKNOWN_VALUE);		// code
	}

	private AttenderWrapper buildResponsabileMonitoraggio(CartellaSocialeDataContext context) {
		return new AttenderWrapper(
			UNKNOWN_VALUE,		// id
			UNKNOWN_VALUE);		// code
	}

	private PertinentInformation2ElaborazioneProgettoIndividualeWrapper buildElaborazioneProgettoIndividualeObservation(CartellaSocialeDataContext context) {
		ProgettoIndividualeWrapper progettoIndividuale = new ProgettoIndividualeWrapper(
			UNKNOWN_VALUE,	// codiceProgetto
			UNKNOWN_VALUE,	// oggetto
			UNKNOWN_VALUE,	// dataInizioProgetto
			UNKNOWN_VALUE);	// dataFineProgetto
		
		List<ObiettivoWrapper> obiettivi = new ArrayList<ObiettivoWrapper>();
		
		// TODO fillObiettivi
		
		ElaborazioneProgettoIndividualeObservationWrapper observations = new ElaborazioneProgettoIndividualeObservationWrapper(
			"frequenzaPrestazioniCode",
			"servizioSocialeProfessionaleCode",
			"integrazioneConAssistenzaSanitariaCode",
			"valoreProgettoAmount",
			"compartecipazioneEconomicaAssistitoAmount");
		
		List<PrestazioneWrapper> prestazioni = new ArrayList<PrestazioneWrapper>();
		
		// TODO fillPrestazioni
		
		
		return new PertinentInformation2ElaborazioneProgettoIndividualeWrapper(progettoIndividuale, obiettivi, observations, prestazioni);
	}
	

	private EncounterEventErogazioneServizioWrapper buildErogazioneServizioData(FaseWrapper fase, CartellaSocialeDataContext context) {
		ErogazioneServizioReferenceWrapper erogazioneServizioReference = buildErogazioneServizioReference(context);
		PertinentInformation2ErogazioneServizioWrapper erogazioneServizioObservation = buildErogazioneServizioObservation(context);
		
		return new EncounterEventErogazioneServizioWrapper(fase, erogazioneServizioReference, erogazioneServizioObservation);
	}
	
	private ErogazioneServizioReferenceWrapper buildErogazioneServizioReference(CartellaSocialeDataContext context) {
		return new ErogazioneServizioReferenceWrapper(
			"denominazioneStrutturaFuoriRete");
	}

	private PertinentInformation2ErogazioneServizioWrapper buildErogazioneServizioObservation(CartellaSocialeDataContext context) {
		ErogazioneServizioObservationWrapper observations = new ErogazioneServizioObservationWrapper(
			"udoSocialeCode",
			"soggettoErogatoreCode",
			"modalitaErogazioneCode",
			"areaInterventoSocialeCode",
			"servizioErogatoCode",
			"dataAttivazioneServizioText",
			"dataConclusioneServizioText",
			"dataInizioSospensioneText",
			"dataFineSospensioneText",
			"motivazioneSospensioneCode",
			"valoreMensileBuonoSocialeAmount",
			"tipologiaVoucherSocialeCode",
			"valoreVoucherAmount",
			"importoErogatoAmount",
			"riprogrammazioneRisorseEconomicheAm");
		
		List<PrestazioneWrapper> prestazioni = new ArrayList<PrestazioneWrapper>();
		
		// TODO fillPrestazioni
		
		
		return new PertinentInformation2ErogazioneServizioWrapper(observations, prestazioni);
	}
	
	
	private EncounterEventValutazioneFinaleWrapper buildValutazioneFinaleData(FaseWrapper fase, CartellaSocialeDataContext context) {
		AttenderWrapper responsabileChiusura = buildResponsabileChiusura(context);
		PertinentInformation2ValutazioneFinaleWrapper valutazioneFinaleObservation = buildValutazioneFinaleObservation(context);
		
		return new EncounterEventValutazioneFinaleWrapper(fase, responsabileChiusura, valutazioneFinaleObservation);
	}
	
	private AttenderWrapper buildResponsabileChiusura(CartellaSocialeDataContext context) {
		return new AttenderWrapper(
			UNKNOWN_VALUE,		// id
			null);		// il Responsabile Chiusura non ha code
	}

	private PertinentInformation2ValutazioneFinaleWrapper buildValutazioneFinaleObservation(CartellaSocialeDataContext context) {
		ValutazioneFinaleObservationWrapper observations = new ValutazioneFinaleObservationWrapper(
			"dataValutazioneFinaleText",
			"valutazioneFinale",
			"motivazioneChiusuraProgettoCode",
			"risorseEconomicheFinaliAmount",
			"risultatiRaggiunti",
			"risultatiNonRaggiunti",
			"nuovoProgettoCode");
		
		return new PertinentInformation2ValutazioneFinaleWrapper(
			UNKNOWN_VALUE,	// dataChiusuraProgetto
			observations);
	}
	
	
	// oggetto di contesto per le operazioni di costruzione CartellaSocialeData: in questo modo passo solo questo fra i vari metodi
	private class CartellaSocialeDataContext {
		private CsACaso caso;
		private SsScheda scheda;
		
		
		public CartellaSocialeDataContext(CsACaso caso, SsScheda scheda) {
			this.caso = caso;
			this.scheda = scheda;
		}
		
		
		public CsACaso getCaso() {
			return caso;
		}
		
		public SsScheda getScheda() {
			return scheda;
		}
	}
}
