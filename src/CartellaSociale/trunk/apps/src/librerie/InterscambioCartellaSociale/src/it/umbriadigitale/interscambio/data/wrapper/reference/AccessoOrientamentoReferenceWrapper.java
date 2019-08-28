package it.umbriadigitale.interscambio.data.wrapper.reference;

import it.umbriadigitale.interscambio.enums.EReference;

/**
 * Raccoglie le proprietà per gli elementi <code>reference</code> di un EncounterEvent di tipo "Accesso e orientamento"
 */
public class AccessoOrientamentoReferenceWrapper extends BaseReferenceDataWrapper {
	private String tipologiaRichiestaCode;
	private String modalitaContattoCode;
	private String ambitoInterventoRichiestoCode;
	private String composizioneNucleoFamiliareCode;
	private String numerositaNucleoFamiliareCode;
	private String numerositaReteSocialeCode;
	private String provenienzaCode;
	private String areaRedditualeCode;
	private String dsuCode;
	private String tipologiaIseeCode;
	private String iseeAmount;
	private String occupazioneAssistitoCode;
	private String codiceEnteRilevatore;
	private String denominazioneEnteRilevatore;
	
	
	public AccessoOrientamentoReferenceWrapper(String tipologiaRichiestaCode, String modalitaContattoCode,
			String ambitoInterventoRichiestoCode, String composizioneNucleoFamiliareCode,
			String numerositaNucleoFamiliareCode, String numerositaReteSocialeCode, String provenienzaCode,
			String areaRedditualeCode, String dsuCode, String tipologiaIseeCode, String iseeAmount,
			String occupazioneAssistitoCode, String codiceEnteRilevatore, String denominazioneEnteRilevatore) {
		
		this.tipologiaRichiestaCode = tipologiaRichiestaCode;
		this.modalitaContattoCode = modalitaContattoCode;
		this.ambitoInterventoRichiestoCode = ambitoInterventoRichiestoCode;
		this.composizioneNucleoFamiliareCode = composizioneNucleoFamiliareCode;
		this.numerositaNucleoFamiliareCode = numerositaNucleoFamiliareCode;
		this.numerositaReteSocialeCode = numerositaReteSocialeCode;
		this.provenienzaCode = provenienzaCode;
		this.areaRedditualeCode = areaRedditualeCode;
		this.dsuCode = dsuCode;
		this.tipologiaIseeCode = tipologiaIseeCode;
		this.iseeAmount = iseeAmount;
		this.occupazioneAssistitoCode = occupazioneAssistitoCode;
		this.codiceEnteRilevatore = codiceEnteRilevatore;
		this.denominazioneEnteRilevatore = denominazioneEnteRilevatore;
		
		populateMap();
	}
	
	@Override
	protected void populateMap() {
		dataMap.put(EReference.TIPOLOGIA_RICHIESTA, tipologiaRichiestaCode);
		dataMap.put(EReference.MODALITA_DI_CONTATTO, modalitaContattoCode);
		dataMap.put(EReference.AMBITO_INTERVENTO_RICHIESTO, ambitoInterventoRichiestoCode);
		dataMap.put(EReference.COMPOSIZIONE_NUCLEO_FAMILIARE, composizioneNucleoFamiliareCode);
		dataMap.put(EReference.NUMEROSITA_NUCLEO_FAMILIARE, numerositaNucleoFamiliareCode);
		dataMap.put(EReference.NUMEROSITA_RETE_SOCIALE, numerositaReteSocialeCode);
		dataMap.put(EReference.PROVENIENZA, provenienzaCode);
		dataMap.put(EReference.AREA_REDDITUALE, areaRedditualeCode);
		dataMap.put(EReference.DSU, dsuCode);
		dataMap.put(EReference.TIPOLOGIA_ISEE, tipologiaIseeCode);
		dataMap.put(EReference.ISEE, iseeAmount);
		dataMap.put(EReference.OCCUPAZIONE_ASSISTITO, occupazioneAssistitoCode);
		dataMap.put(EReference.CODICE_ENTE_RILEVATORE, codiceEnteRilevatore);
		dataMap.put(EReference.DENOMINAZIONE_ENTE_RILEVATORE, denominazioneEnteRilevatore);
	}
}
