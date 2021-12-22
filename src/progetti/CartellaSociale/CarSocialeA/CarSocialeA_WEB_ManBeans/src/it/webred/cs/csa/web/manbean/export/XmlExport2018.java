package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.CSIPs.FLAG_IN_CARICO;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.osmosit.siso.flussoinps.logic.Cost;
import com.osmosit.siso.flussoinps.psa_2016.XmlExporter;

final class XmlExport2018 extends PsaXmlExporter {

	@Override
	File doExport(
			String XML_PATH,
			List<EsportazioneDTO> erogazioni,
			String idFlusso,
			String denomEnte,
			String codEnte,
			String cfOperatore,
			String indirEnte) throws Exception {

		// SISO-538 ordino per codice fiscale, propedeutico per l'aggregazione
		// delle prestazioni sotto lo stesso beneficiario
		Collections.sort(erogazioni, new Comparator<EsportazioneDTO>() {
			@Override
			public int compare(EsportazioneDTO o1, EsportazioneDTO o2) {
				return o1.getSoggettoCodiceFiscale().compareTo(o2.getSoggettoCodiceFiscale());
			}
		});

		int numErogazioni = erogazioni.size();
		if (numErogazioni > 0) {

			// per ogni beneficiario esiste almeno una erogazione
			Map<String, Map<String, Object>> listaBeneficiariConErogazione = new HashMap<String, Map<String, Object>>();
			List<Map<String, Object>> prestazioniBeneficiario = null;
			List<Map<String, Object>> sinaType = null;
			List<String> necessitaIntSociali = null;


			for (EsportazioneDTO e : erogazioni) {
				String key = e.getSoggettoCodiceFiscale().toUpperCase();
				Map<String, Object> beneficiario = listaBeneficiariConErogazione.get(key);
				
				//SISO-1162 non devono essere esportate gli interventi con codice prestazioneinps nulla
				if( e.getCodPrestazione() != null){
					if (beneficiario == null) {
						beneficiario = estraiDatiBeneficiario(e);
					}
					
					
					prestazioniBeneficiario = (List<Map<String, Object>>) beneficiario.get("listaPrestazioni");
					
					if (prestazioniBeneficiario == null) {
						prestazioniBeneficiario = new ArrayList<Map<String, Object>>();
					}
				
					Map<String, Object> prestazione = estraiDatiPrestazione(e);
					prestazioniBeneficiario.add(prestazione);
					
					
					if (prestazioniBeneficiario.size()>0){
					beneficiario.put("listaPrestazioni",prestazioniBeneficiario);
					
					//AGGIORNA BENEFICIARIO
					listaBeneficiariConErogazione.put(key,beneficiario);
					}
				}
				//FINE SISO-1162
			}
				
			// il beneficiario contiene anche una lista di prestazioni
			
			/*	for (int i = 1; i < numErogazioni; i++) {
					if (erogazioni.get(i).getSoggettoCodiceFiscale()
							.equalsIgnoreCase(erogazioni.get(i - 1).getSoggettoCodiceFiscale())) {
						// STESSO BENEFICIARIO
						prestazioniBeneficiario.add(prestazione);
						prestazione = estraiDatiPrestazione(erogazioni.get(i));
					} else {
						// NUOVO BENEFICIARIO
						prestazioniBeneficiario.add(prestazione);
						beneficiario.put("listaPrestazioni", prestazioniBeneficiario);
						listaBeneficiariConErogazione.add(beneficiario);

						prestazioniBeneficiario = new ArrayList<Map<String, Object>>();
						beneficiario = estraiDatiBeneficiario(erogazioni.get(i));
						prestazione = estraiDatiPrestazione(erogazioni.get(i));
					}
				}
			}

			
			 * all'ultimo devo aggiungere l'ultimo beneficiario a cui ho aggiunto
			 * l'ultima prestazione
			 
			prestazioniBeneficiario.add(prestazione);
			beneficiario.put("listaPrestazioni", prestazioniBeneficiario);
			listaBeneficiariConErogazione.add(beneficiario);*/

			logger.debug("Conversione dati completata. Invocazione exporter...");
			XmlExporter exporter = new XmlExporter(XML_PATH);
			exporter.exportToXml(idFlusso, denomEnte, codEnte, cfOperatore, indirEnte, new ArrayList<Map<String, Object>>(listaBeneficiariConErogazione.values()));
			logger.debug("Esportazione completata.");
		}
		return new File(XML_PATH);
	}



	private Map<String, Object> estraiDatiBeneficiario(EsportazioneDTO erogDaEsportare) {
		Map<String, Object> mappaDatiSogg = new HashMap<String, Object>();
		if (erogDaEsportare.getSoggettoCodiceFiscale() != null)
			mappaDatiSogg.put(Cost.BENEFICIARIO_CF, erogDaEsportare.getSoggettoCodiceFiscale());
		if (erogDaEsportare.getBenefRegione() != null)
			mappaDatiSogg.put(Cost.RESIDENZA_REGIONE, erogDaEsportare.getBenefRegione());
		if (erogDaEsportare.getBenefComune() != null)
			mappaDatiSogg.put(Cost.RESIDENZA_COMUNE, erogDaEsportare.getBenefComune());
		if (erogDaEsportare.getBenefNazione() != null)
			mappaDatiSogg.put(Cost.RESIDENZA_NAZIONE, erogDaEsportare.getBenefNazione());
		if (erogDaEsportare.getSoggettoNome() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_NOME, erogDaEsportare.getSoggettoNome());
		if (erogDaEsportare.getSoggettoCognome() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_COGNOME, erogDaEsportare.getSoggettoCognome());
		if (erogDaEsportare.getBenefAnnoNascita() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_ANNONASCITA, erogDaEsportare.getBenefAnnoNascita());
		if (erogDaEsportare.getBenefLuogoNascita() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_LUOGONASCITA, erogDaEsportare.getBenefLuogoNascita());
		if (erogDaEsportare.getBenefSesso() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_SESSO, erogDaEsportare.getBenefSesso());
		if (erogDaEsportare.getBenefCittadinanza() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_CITTAD_ISO, erogDaEsportare.getBenefCittadinanza());
		
		//SISO-962
		if (erogDaEsportare.getBenefSecCittadinanza()  != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO, erogDaEsportare.getBenefSecCittadinanza());
		//SISO-962 Fine
		return mappaDatiSogg;
	}



	private Map<String, Object> estraiDatiPrestazione(EsportazioneDTO erogDaEsportare) {
		Map<String, Object> mappaDatiPrestazione = new HashMap<String, Object>();

		/* prestazione periodica */
		logger.debug(erogDaEsportare);

		if (erogDaEsportare.getCarattere().equals(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)) {
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_INIZIO, datef.format(erogDaEsportare.getDataInizio()));
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_FINE, datef.format(erogDaEsportare.getDataFine()));
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_PERIOD_EROG, String.valueOf(erogDaEsportare.getPeriodoErogazione()));
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_IMPORTO_MENS, df.format(erogDaEsportare.getImportoMensile()));
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()));
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_EVENTO, "");

			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_INIZIO, erogDaEsportare.getDataInizio());
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_FINE, erogDaEsportare.getDataFine());
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_PERIOD_EROG, erogDaEsportare.getPeriodoErogazione());
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_IMPORTO_MENS, erogDaEsportare.getImportoMensile());
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_ENTE, erogDaEsportare.getPercGestitaEnte());
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_ORE_MINUTI_MENS, erogDaEsportare.getOreServizioMensile());
		}

		/* prestazione occasionale */

//		if (erogDaEsportare.getNumProtDSU() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_NUMPROT_DSU, erogDaEsportare.getNumProtDSU());
//		if (erogDaEsportare.getAnnoProtDSU() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_ANNO_PROT, Integer.toString(erogDaEsportare.getAnnoProtDSU()));
//		if (erogDaEsportare.getDataDSU() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_DSU, datef.format(erogDaEsportare.getDataDSU()));
//		if (erogDaEsportare.getCodPrestazione() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_CODICE, erogDaEsportare.getCodPrestazione());
//		if (erogDaEsportare.getDenomPrestazione() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DENOMINAZIONE, erogDaEsportare.getDenomPrestazione());
//		if (erogDaEsportare.getDataEsecuzione() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_EROG, datef.format(erogDaEsportare.getDataEsecuzione()));
//		if (erogDaEsportare.getSpesa() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_IMPORTO, df.format(erogDaEsportare.getSpesa()));

		if (erogDaEsportare.getNumProtDSU() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_NUMPROT_DSU, erogDaEsportare.getNumProtDSU());
		if (erogDaEsportare.getAnnoProtDSU() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_ANNO_PROT, erogDaEsportare.getAnnoProtDSU());
		if (erogDaEsportare.getDataDSU() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_DSU, erogDaEsportare.getDataDSU());
		if (erogDaEsportare.getCodPrestazione() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_CODICE, erogDaEsportare.getCodPrestazione());
		if (erogDaEsportare.getDenomPrestazione() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DENOMINAZIONE, erogDaEsportare.getDenomPrestazione());
		if (erogDaEsportare.getDataEsecuzione() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_EROG, erogDaEsportare.getDataEsecuzione());
		if (erogDaEsportare.getSpesa() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_IMPORTO, df.format(erogDaEsportare.getSpesa()));

		/* dati comuni */

//		if (erogDaEsportare.getCarattere() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_CARATTERE, erogDaEsportare.getCarattere());
//		if (erogDaEsportare.getPercGestitaEnte() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()));
//		if (erogDaEsportare.getCompartUtenti() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_UTENTE, df.format(erogDaEsportare.getCompartUtenti()));
//		if (erogDaEsportare.getCompartSsn() != null)
//			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_SSN, df.format(erogDaEsportare.getCompartSsn()));

		// SISO-657
		mappaDatiPrestazione.put(Cost.PRESTAZIONE_PRESENZA_PROVA_MEZZI, erogDaEsportare.getPresenzaProvaMezzi().intValue());
		

		if (erogDaEsportare.getCarattere() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_CARATTERE, Integer.parseInt(erogDaEsportare.getCarattere()));
		if (erogDaEsportare.getPercGestitaEnte() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()));
		if (erogDaEsportare.getCompartUtenti() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_UTENTE, df.format(erogDaEsportare.getCompartUtenti()));
		if (erogDaEsportare.getCompartSsn() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_SSN, df.format(erogDaEsportare.getCompartSsn()));

		// if(erogDaEsportare.getSogliaISEE()!=null)
		// mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE,
		// decf.format(erogDaEsportare.getSogliaISEE()));

		if (erogDaEsportare.getPrestazioneProtocEnte() != null)
			mappaDatiPrestazione.put(Cost.PRESTAZIONE_PROTOC_ENTE, erogDaEsportare.getPrestazioneProtocEnte());

		mappaDatiPrestazione.put(Cost.PRESTAZIONE_QUOTA_RICHIESTA, "");
		mappaDatiPrestazione.put(Cost.PRESTAZIONE_SOGLIA_ISEE, "");

		mappaDatiPrestazione.put(Cost.PRESTAZIONE_DATA_EVENTO, "");
		
		// (cioè presaInCarico=sì)
		mappaDatiPrestazione.put(Cost.PRESTAZIONE_PRESA_IN_CARICO, erogDaEsportare.getPresaInCarico().intValue());

		String areaUtenza = transcodeCategoriaSocialeId(erogDaEsportare.getCategoriaSocialeId());
		mappaDatiPrestazione.put(Cost.PRESTAZIONE_AREA_UTENZA, areaUtenza);
		
		boolean esportaSina = erogDaEsportare.getIsSinaCollegato() && FLAG_IN_CARICO.SI.getCodice()==erogDaEsportare.getPresaInCarico().intValue();
		mappaDatiPrestazione.put(Cost.SINA_COLLEGATO, esportaSina);
		
		if(esportaSina){
			//Controllo la presenza delle prestazioni e invCiv dato che non sono più obbligatori 
			if(!erogDaEsportare.getCodiciPrestazione().isEmpty()){
				mappaDatiPrestazione.put(Cost.SINA_CODICE_PRESTAZIONE, erogDaEsportare.getCodiciPrestazione());
			}
			if(!erogDaEsportare.getInvCiv().isEmpty()){
				mappaDatiPrestazione.put(Cost.SINA_INV_CIV,erogDaEsportare.getInvCiv());
			}
			if(erogDaEsportare.getMobilita() != null){
				mappaDatiPrestazione.put(Cost.SINA_MOBILITA, String.valueOf(erogDaEsportare.getMobilita()));
			}
			if(erogDaEsportare.getAttivitaVitaQuotidiana() != null){
				mappaDatiPrestazione.put(Cost.SINA_ATTIVITA_VITA_QUOTIDIANA, String.valueOf(erogDaEsportare.getAttivitaVitaQuotidiana()));
			}
			if(erogDaEsportare.getDisturbiAreaCognitiva() != null){
				mappaDatiPrestazione.put(Cost.SINA_DISTURBI_AREA_COGNITIVA, String.valueOf(erogDaEsportare.getDisturbiAreaCognitiva()));
			}
			if(erogDaEsportare.getDisturbiComportamentali() != null){
				mappaDatiPrestazione.put(Cost.SINA_DISTURBI_COMPORTAMENTALI, String.valueOf(erogDaEsportare.getDisturbiComportamentali()));
			}
			if(erogDaEsportare.getNecessitaCureSanitarie() != null){
				mappaDatiPrestazione.put(Cost.SINA_NECESSITA_CURE_SANITARIE, String.valueOf(erogDaEsportare.getNecessitaCureSanitarie()));
			}
			if(erogDaEsportare.getAreaReddituale() != null){
				mappaDatiPrestazione.put(Cost.SINA_AREA_REDDITUALE, String.valueOf(erogDaEsportare.getAreaReddituale()));
			}
			if(erogDaEsportare.getAreaSupporto() != null){
				mappaDatiPrestazione.put(Cost.SINA_AREA_SUPPORTO, String.valueOf(erogDaEsportare.getAreaSupporto()));
			}
			if(erogDaEsportare.getFonteDerivazioneValutazione() != null){
				mappaDatiPrestazione.put(Cost.SINA_FONTE_DERIVAZIONE_VALUTAZIONE, String.valueOf(erogDaEsportare.getFonteDerivazioneValutazione()));
			}
			if(erogDaEsportare.getStrumentoValutazione() != null){
				mappaDatiPrestazione.put(Cost.SINA_STRUMENTO_VALUTAZIONE, String.valueOf(erogDaEsportare.getStrumentoValutazione()));
			}
			/* sono valori multipli, controllo spostato in alto 
			if(erogDaEsportare.getInvCiv() != null){
				mappaDatiPrestazione.put(Cost.SINA_INV_CIV, String.valueOf(erogDaEsportare.getInvCiv()));
			}
			*/
			if(erogDaEsportare.getFonteDerivazioneInvalidita() != null){
				mappaDatiPrestazione.put(Cost.SINA_FONTE_DERIVAZIONE_INVALIDITA, String.valueOf(erogDaEsportare.getFonteDerivazioneInvalidita()));
			}
		}
		
		return mappaDatiPrestazione;
	}



	private String transcodeCategoriaSocialeId(BigDecimal categoriaSocialeId) {
//		"1"  se l’intervento appartiene alla categoria sociale "Famiglia e Minori" , 
//		"2" se l’intervento appartiene alla categoria sociale "Disabili", 
//		"3" in tutti gli altri casi 	
		String areaUtenza=null;
		if(categoriaSocialeId!=null){
			switch (categoriaSocialeId.intValue()) {
			case 1:
				areaUtenza = DataModelCostanti.CSIPs.AREA_UTENZA_FAMIGLIA_E_MINORI; 
				break;
			case 2:
				areaUtenza = DataModelCostanti.CSIPs.AREA_UTENZA_DISABILI;
				break; 
			case 4:
				areaUtenza = DataModelCostanti.CSIPs.AREA_UTENZA_DISABILI;
				break; 	
			default:
				areaUtenza = DataModelCostanti.CSIPs.AREA_UTENZA_GENERICA;
				break;
			}
		}
		return areaUtenza;
	}

}
