package it.umbriadigitale.interscambio.data.wrapper.reference;

import java.util.List;

import it.umbriadigitale.interscambio.enums.EReference;

/**
 * Raccoglie le proprietà per gli elementi <code>reference</code> di un EncounterEvent di tipo "Valutazione del bisogno"
 */
public class ValutazioneBisognoReferenceWrapper extends BaseReferenceDataWrapper {
	private String areeInterventoSocialeCode;
	private String esenzioniTicketCode;
	private String denominazioneServiziSociosanitariPersonaCarico;
	private String serviziSociosanitariGiaErogatiCode;
	private String invaliditaCode;
	private String indennitaFrequenzaCode;
	private String previdenzeSocialiINPSCode;
	private String assegnoPensioneInvaliditaCivileCode;
	private String utenteConPatologiaPsichiatricaCode;
	private String patologieCorrenti;
	private String portatoreProtesiAusiliCode;
	private String motivazioneAffidoCode;
	private String valutazioneNecessitaCureCode;
	private String necessitaInterventiSociosanitariCode;
	private String necessitaInterventiSocialiCode;
	private String fonteDerivazioneValutazioneCode;
	private List<String> listaCodiceProtesiAusilio;
	
	
	public ValutazioneBisognoReferenceWrapper(String areeInterventoSocialeCode, String esenzioniTicketCode,
			String denominazioneServiziSociosanitariPersonaCarico, String serviziSociosanitariGiaErogatiCode,
			String invaliditaCode, String indennitaFrequenzaCode, String previdenzeSocialiINPSCode,
			String assegnoPensioneInvaliditaCivileCode, String utenteConPatologiaPsichiatricaCode,
			String patologieCorrenti, String portatoreProtesiAusiliCode, String motivazioneAffidoCode,
			String valutazioneNecessitaCureCode, String necessitaInterventiSociosanitariCode,
			String necessitaInterventiSocialiCode, String fonteDerivazioneValutazioneCode,
			List<String> listaCodiceProtesiAusilio) {
		
		this.areeInterventoSocialeCode = areeInterventoSocialeCode;
		this.esenzioniTicketCode = esenzioniTicketCode;
		this.denominazioneServiziSociosanitariPersonaCarico = denominazioneServiziSociosanitariPersonaCarico;
		this.serviziSociosanitariGiaErogatiCode = serviziSociosanitariGiaErogatiCode;
		this.invaliditaCode = invaliditaCode;
		this.indennitaFrequenzaCode = indennitaFrequenzaCode;
		this.previdenzeSocialiINPSCode = previdenzeSocialiINPSCode;
		this.assegnoPensioneInvaliditaCivileCode = assegnoPensioneInvaliditaCivileCode;
		this.utenteConPatologiaPsichiatricaCode = utenteConPatologiaPsichiatricaCode;
		this.patologieCorrenti = patologieCorrenti;
		this.portatoreProtesiAusiliCode = portatoreProtesiAusiliCode;
		this.motivazioneAffidoCode = motivazioneAffidoCode;
		this.valutazioneNecessitaCureCode = valutazioneNecessitaCureCode;
		this.necessitaInterventiSociosanitariCode = necessitaInterventiSociosanitariCode;
		this.necessitaInterventiSocialiCode = necessitaInterventiSocialiCode;
		this.fonteDerivazioneValutazioneCode = fonteDerivazioneValutazioneCode;
		this.listaCodiceProtesiAusilio = listaCodiceProtesiAusilio;
		
		populateMap();
	}

	@Override
	protected void populateMap() {
		dataMap.put(EReference.AREE_INTERVENTO_SOCIALE, areeInterventoSocialeCode);
		dataMap.put(EReference.ESENZIONI_TICKET, esenzioniTicketCode);
		dataMap.put(EReference.DENOMINAZIONE_SSS_PERSONA_CARICO, denominazioneServiziSociosanitariPersonaCarico);
		dataMap.put(EReference.SERVIZI_SOCIOSANITARI_GIA_EROGATI, serviziSociosanitariGiaErogatiCode);
		dataMap.put(EReference.INVALIDITA, invaliditaCode);
		dataMap.put(EReference.INDENNITA_DI_FREQUENZA, indennitaFrequenzaCode);
		dataMap.put(EReference.PREVIDENZE_SOCIALI_INPS, previdenzeSocialiINPSCode);
		dataMap.put(EReference.ASSEGNO_PENSIONE_INVALIDITA_CIVILE, assegnoPensioneInvaliditaCivileCode);
		dataMap.put(EReference.UTENTE_CON_PATOLOGIA_PSICHIATRICA, utenteConPatologiaPsichiatricaCode);
		dataMap.put(EReference.PATOLOGIE_CORRENTI, patologieCorrenti);
		dataMap.put(EReference.PORTATORE_PROTESI_E_AUSILI, portatoreProtesiAusiliCode);
		dataMap.put(EReference.MOTIVAZIONE_AFFIDO, motivazioneAffidoCode);
		dataMap.put(EReference.VALUTAZIONE_NECESSITA_CURE, valutazioneNecessitaCureCode);
		dataMap.put(EReference.NECESSITA_INTERVENTI_SOCIOSANITARI, necessitaInterventiSociosanitariCode);
		dataMap.put(EReference.NECESSITA_INTERVENTI_SOCIALI, necessitaInterventiSocialiCode);
		dataMap.put(EReference.FONTE_DERIVAZIONE_VALUTAZIONE, fonteDerivazioneValutazioneCode);
		
		for (String codiceProtesiAusilioCode : listaCodiceProtesiAusilio) {
			dataMap.put(EReference.CODICE_PROTESI_AUSILIO, codiceProtesiAusilioCode);
		}
	}
}
