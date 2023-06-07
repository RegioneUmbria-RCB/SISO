package com.osmosit.siso.flussoinps.sinba_2018;

import java.util.List;
import java.util.Map;

import com.osmosit.siso.flussoinps.logic.Cost;

class ValiditySinbaChecker {

	private final DataTypeSinbaUtils u;



	public ValiditySinbaChecker(DataTypeSinbaUtils utils) {
		this.u = utils;
	}



	CheckValiditySinbaResponse checkValidity(
			String codiceEnte,
			String indirizzoEnte,
			String codiceFiscaleOperatore,
			List<Map<String, Object>> listaBeneficiariConErogazione) {
		CheckValiditySinbaResponse response = new CheckValiditySinbaResponse(); 

		response.check(!u.isNullOrEmpty(codiceEnte), "codiceEnte"); 
		response.check(!u.isNullOrEmpty(indirizzoEnte), "indirizzoEnte");
		response.check(!u.isNullOrEmpty(codiceFiscaleOperatore), "codiceFiscaleOperatore");

		for (Map<String, Object> datiBeneficiario : listaBeneficiariConErogazione) {
			
			String soggetto = u.getString(datiBeneficiario, Cost.ANAGRAFICA_COGNOME)+" "+u.getString(datiBeneficiario, Cost.ANAGRAFICA_NOME);
			
			response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.ANNO_NASCITA)), "ANNO_NASCITA");
			response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.RESIDENZA_NAZIONE)), "RESIDENZA_NAZIONE");
			
			//response.check(u.getInteger(datiBeneficiario, Cost.COMPOSIZIONE_FAMIGLIA) != 0, "COMPOSIZIONE_FAMIGLIA");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.MINORE_STRANIERO_NON_ACCOMPAGNATO)), "MINORE_STRANIERO_NON_ACCOMPAGNATO");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.CONDIZIONE_MINORE)), "CONDIZIONE_MINORE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.LUOGO_VITA)), "LUOGO_VITA");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.SCUOLA_FREQUENTATA)), "SCUOLA_FREQUENTATA");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.CONDIZIONE_LAVORO)), "CONDIZIONE_LAVORO");
			
			String disabile = u.getString(datiBeneficiario, Cost.DISABILE);
			response.check(!"0".equals(disabile), "DISABILE");
			if("1".equals(disabile)) {
				response.check(!"0".equals(u.getString(datiBeneficiario, Cost.TIPO_DISABILITA)), "TIPO_DISABILITA");
				response.check(!"0".equals(u.getString(datiBeneficiario, Cost.CERTIFICAZIONE_INVALIDITA_CIVILE)), "CERTIFICAZIONE_INVALIDITA_CIVILE");
			}
			
			response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.DATA_PRIMA_SEGNALAZIONE)), "DATA_PRIMA_SEGNALAZIONE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.FONTE_SEGNALAZIONE)), "FONTE_SEGNALAZIONE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.VALUTAZIONE_MINORE)), "VALUTAZIONE_MINORE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.VALUTAZIONE_FAMIGLIA_MINORE)), "VALUTAZIONE_FAMIGLIA_MINORE");
			
			String segnalazioneAutoritaGiudiziaria = u.getString(datiBeneficiario, Cost.SEGNALAZIONE_AUTORITA_GIUDIZIARIA);
			response.check(!u.isNullOrEmpty(segnalazioneAutoritaGiudiziaria), "SEGNALAZIONE_AUTORITA_GIUDIZIARIA");
			if("1".equals(segnalazioneAutoritaGiudiziaria)) {
				response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.DATA_SEGNALAZIONE)), "DATA_SEGNALAZIONE");
			}
			
			String provvGiudiziario = u.getString(datiBeneficiario, Cost.PROVVEDIMENTO_GIUDIZIARIO);
			response.check(!u.isNullOrEmpty(segnalazioneAutoritaGiudiziaria), "PROVVEDIMENTO_GIUDIZIARIO");
			if("1".equals(provvGiudiziario)) {
				response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.DATA_PROVVEDIMENTO_GIUDIZIARIO)), "DATA_PROVVEDIMENTO_GIUDIZIARIO");
			}
			
			//Verifica compilazione CodicePrestazione
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> prestazioni = (List<Map<String, Object>>) datiBeneficiario.get(Cost.LIST_CODICI_PRESTAZIONE);
			if (prestazioni.size() > 0)
				response.check(true, "PRESTAZIONE_CODICE [beneficiario:"+soggetto+"]");
			else
				response.check(false, "CODICE PRESTAZIONE");
			
			response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.FASCIA_ETA_BENEFICIARIO)), "FASCIA_ETA_BENEFICIARIO");
		}

		return response;
	}

}
