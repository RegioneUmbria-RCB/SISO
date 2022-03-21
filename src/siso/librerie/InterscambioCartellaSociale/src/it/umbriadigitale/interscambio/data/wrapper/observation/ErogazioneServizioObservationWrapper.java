package it.umbriadigitale.interscambio.data.wrapper.observation;

import it.umbriadigitale.interscambio.enums.EObservation;

/**
 * Raccoglie le proprietà per gli elementi <code>observation</code> di un EncounterEvent di tipo "Erogazione del servizio"
 */
public class ErogazioneServizioObservationWrapper extends BaseObservationDataWrapper {
	private String udoSocialeCode;
	private String soggettoErogatoreCode;
	private String modalitaErogazioneCode;
	private String areaInterventoSocialeCode;
	private String servizioErogatoCode;
	private String dataAttivazioneServizioText;
	private String dataConclusioneServizioText;
	private String dataInizioSospensioneText;
	private String dataFineSospensioneText;
	private String motivazioneSospensioneCode;
	private String valoreMensileBuonoSocialeAmount;
	private String tipologiaVoucherSocialeCode;
	private String valoreVoucherAmount;
	private String importoErogatoAmount;
	private String riprogrammazioneRisorseEconomicheAmount;
	
	
	public ErogazioneServizioObservationWrapper(String udoSocialeCode, String soggettoErogatoreCode,
			String modalitaErogazioneCode, String areaInterventoSocialeCode, String servizioErogatoCode,
			String dataAttivazioneServizioText, String dataConclusioneServizioText, String dataInizioSospensioneText,
			String dataFineSospensioneText, String motivazioneSospensioneCode, String valoreMensileBuonoSocialeAmount,
			String tipologiaVoucherSocialeCode, String valoreVoucherAmount, String importoErogatoAmount,
			String riprogrammazioneRisorseEconomicheAmount) {
		
		this.udoSocialeCode = udoSocialeCode;
		this.soggettoErogatoreCode = soggettoErogatoreCode;
		this.modalitaErogazioneCode = modalitaErogazioneCode;
		this.areaInterventoSocialeCode = areaInterventoSocialeCode;
		this.servizioErogatoCode = servizioErogatoCode;
		this.dataAttivazioneServizioText = dataAttivazioneServizioText;
		this.dataConclusioneServizioText = dataConclusioneServizioText;
		this.dataInizioSospensioneText = dataInizioSospensioneText;
		this.dataFineSospensioneText = dataFineSospensioneText;
		this.motivazioneSospensioneCode = motivazioneSospensioneCode;
		this.valoreMensileBuonoSocialeAmount = valoreMensileBuonoSocialeAmount;
		this.tipologiaVoucherSocialeCode = tipologiaVoucherSocialeCode;
		this.valoreVoucherAmount = valoreVoucherAmount;
		this.importoErogatoAmount = importoErogatoAmount;
		this.riprogrammazioneRisorseEconomicheAmount = riprogrammazioneRisorseEconomicheAmount;
		
		populateMap();
	}


	@Override
	protected void populateMap() {
		dataMap.put(EObservation.UDO_SOCIALE, udoSocialeCode);
		dataMap.put(EObservation.SOGGETTO_EROGATORE, soggettoErogatoreCode);
		dataMap.put(EObservation.MODALITA_EROGAZIONE, modalitaErogazioneCode);
		dataMap.put(EObservation.AREA_INTERVENTO_SOCIALE, areaInterventoSocialeCode);
		dataMap.put(EObservation.SERVIZIO_EROGATO, servizioErogatoCode);
		dataMap.put(EObservation.DATA_ATTIVAZIONE_SERVIZIO, dataAttivazioneServizioText);
		dataMap.put(EObservation.DATA_CONCLUSIONE_SERVIZIO, dataConclusioneServizioText);
		dataMap.put(EObservation.DATA_INIZIO_SOSPENSIONE, dataInizioSospensioneText);
		dataMap.put(EObservation.DATA_FINE_SOSPENSIONE, dataFineSospensioneText);
		dataMap.put(EObservation.MOTIVAZIONE_SOSPENSIONE, motivazioneSospensioneCode);
		dataMap.put(EObservation.VALORE_MENSILE_BUONO_SOCIALE, valoreMensileBuonoSocialeAmount);
		dataMap.put(EObservation.TIPOLOGIA_VOUCHER_SOCIALE, tipologiaVoucherSocialeCode);
		dataMap.put(EObservation.VALORE_VOUCHER, valoreVoucherAmount);
		dataMap.put(EObservation.IMPORTO_EROGATO, importoErogatoAmount);
		dataMap.put(EObservation.RIPROGRAMMAZIONE_RISORSE_ECONOMICHE, riprogrammazioneRisorseEconomicheAmount);
	}
}
