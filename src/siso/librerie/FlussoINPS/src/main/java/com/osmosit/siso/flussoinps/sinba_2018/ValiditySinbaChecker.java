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
			//response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.RESIDENZA_REGIONE)), "RESIDENZA_REGIONE");
			response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.NAZIONE_RESIDENZA)), "RESIDENZA_NAZIONE");
			//response.check(u.getInteger(datiBeneficiario, Cost.COMPOSIZIONE_FAMIGLIA) != 0, "COMPOSIZIONE_FAMIGLIA");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.MINORE_STRANIERO_NON_ACCOMPAGNATO)), "MINORE_STRANIERO_NON_ACCOMPAGNATO");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.CONDIZIONE_MINORE)), "CONDIZIONE_MINORE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.LUOGO_VITA)), "LUOGO_VITA");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.SCUOLA_FREQUENTATA)), "SCUOLA_FREQUENTATA");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.CONDIZIONE_LAVORO)), "CONDIZIONE_LAVORO");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.DISABILE)), "DISABILE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.TIPO_DISABILITA)), "TIPO_DISABILITA");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.CERTIFICAZIONE_INVALIDITA_CIVILE)), "CERTIFICAZIONE_INVALIDITA_CIVILE");	
			response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.DATA_SEGNALAZIONE)), "DATA_SEGNALAZIONE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.FONTE_SEGNALAZIONE)), "FONTE_SEGNALAZIONE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.VALUTAZIONE_MINORE)), "VALUTAZIONE_MINORE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.VALUTAZIONE_FAMIGLIA_MINORE)), "VALUTAZIONE_FAMIGLIA_MINORE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.SEGNALAZIONE_AUTORITA_GIUDIZIARIA)), "SEGNALAZIONE_AUTORITA_GIUDIZIARIA");
			response.check(!u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.DATA_VALUTAZIONE)), "DATA_VALUTAZIONE");
			response.check(!"0".equals(u.getString(datiBeneficiario, Cost.PROVVEDIMENTO_GIUDIZIARIO)), "PROVVEDIMENTO_GIUDIZIARIO");
			//response.check(u.getDate(datiBeneficiario, Cost.DATA_PROVVEDIMENTO_GIUDIZIARIO) != null, "DATA_PROVVEDIMENTO_GIUDIZIARIO");
			

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> prestazioni = (List<Map<String, Object>>) datiBeneficiario.get("prestazioniSel");
			if (prestazioni.size() > 0) {
					response.check(true, "PRESTAZIONE_CODICE [beneficiario:"+soggetto+"]");
			}
			else
			{
				response.check(false, "CODICE PRESTAZIONE");
			}
		}

		return response;
	}

}
