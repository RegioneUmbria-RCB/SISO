package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.dto.SinbaDTO;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.osmosit.siso.flussoinps.logic.Cost;
import com.osmosit.siso.flussoinps.sinba_2018.XmlSinbaExporter;

final class XmlExport2018ImplSINBA extends SinbaXmlExporter {

	
	

	@Override
	File doExport(
			String XML_PATH,
			List<SinbaDTO> sinbaDaEsportareList,
			String idFlusso,
			String denomEnte,
			String codEnte,
			String cfOperatore,
			String indirEnte) throws Exception {

		
		Collections.sort(sinbaDaEsportareList, new Comparator<SinbaDTO>() {
			@Override
			public int compare(SinbaDTO o1, SinbaDTO o2) {
				return o1.getCodiceAnonimoBeneficiario().compareTo(o2.getCodiceAnonimoBeneficiario());
			}
		});

		int numSimba = sinbaDaEsportareList.size();
		if (numSimba > 0) {

			// per ogni beneficiario esiste almeno una SINBA
			//Map di Map chiave CodiceBeneficiario e map nome prametro valore parametro
			Map<String, Map<String, Object>> listaBeneficiariConSinba = new HashMap<String, Map<String, Object>>();
			List<Map<String, Object>> prestazioniMinoreSinba = null;
			List<Map<String, Object>> componentiFamigliaMinoreSinba = null;


			for (SinbaDTO e : sinbaDaEsportareList) {
				String key = e.getCodiceAnonimoBeneficiario().toUpperCase();
				Map<String, Object> sinbaMinore = listaBeneficiariConSinba.get(key);
				
				//vedo se non ho già preso una SINBa per quel beneficiario, se ce l'ho già aggiungo solo le liste (es. Prestazioni e Famigliari)
				if (sinbaMinore == null) {
					sinbaMinore = estraiDatiGeneraliBeneficiario(e);
				}
				
				
				prestazioniMinoreSinba = (List<Map<String, Object>>) sinbaMinore.get("prestazioniSel");
				
				if (prestazioniMinoreSinba == null) {
					prestazioniMinoreSinba = new ArrayList<Map<String, Object>>();
				}
				
				
				List<Map<String, Object>> prestazioni = estraiDatiPrestazione(e);
				prestazioniMinoreSinba.addAll(prestazioni);
				
				sinbaMinore.put("prestazioniSel",prestazioniMinoreSinba);
				
				//TODO fai cosa analoga per i famigliari
				componentiFamigliaMinoreSinba = (List<Map<String, Object>>) sinbaMinore.get("componentiFamigliaSel");
				
				if (componentiFamigliaMinoreSinba == null) {
					componentiFamigliaMinoreSinba = new ArrayList<Map<String, Object>>();
				}
				
				
				List<Map<String, Object>> familiari = estraiDatiFamigliaBeneficiario(e);
				componentiFamigliaMinoreSinba.addAll(familiari);
				
				sinbaMinore.put("componentiFamigliaSel",componentiFamigliaMinoreSinba);
				
				//a questo punto dovrei avere per ogni beneficiarioMinore una Map chiave valore (nome_campo_voluto_da_inps, valore campo))
				
				listaBeneficiariConSinba.put(key,sinbaMinore);
			}
						
			

			logger.debug("Conversione dati completata. Invocazione exporter...");
			//TODO exporter va ancora implementato specificatamente per SINBA
			XmlSinbaExporter exporter = new XmlSinbaExporter(XML_PATH);
			exporter.exportToXml(idFlusso, denomEnte, codEnte, cfOperatore, indirEnte, new ArrayList<Map<String, Object>>(listaBeneficiariConSinba.values()));
			logger.debug("Esportazione completata.");
		}
		return new File(XML_PATH);
	}
	
	private Map<String, Object> estraiDatiGeneraliBeneficiario(SinbaDTO sinbaDaEsportare) {
		Map<String, Object> MappaDatiGenBene = new HashMap<String, Object>();
		if (sinbaDaEsportare.getDataValutazione() != null)
			MappaDatiGenBene.put(Cost.DATA_VALUTAZIONE, sinbaDaEsportare.getDataValutazione());
		if (sinbaDaEsportare.getScuolaFrequentata() != null)
			MappaDatiGenBene.put(Cost.SCUOLA_FREQUENTATA, sinbaDaEsportare.getScuolaFrequentata());
		if (sinbaDaEsportare.getCondizioneLavoro() != null)
			MappaDatiGenBene.put(Cost.CONDIZIONE_LAVORO, sinbaDaEsportare.getCondizioneLavoro());
		
		
		
		if (sinbaDaEsportare.getCodiceAnonimoBeneficiario() != null)
			MappaDatiGenBene.put(Cost.CODICE_ANONIMO_BENEFICIARIO, sinbaDaEsportare.getCodiceAnonimoBeneficiario());
		if (sinbaDaEsportare.getAnnoNascita() != null)
			MappaDatiGenBene.put(Cost.ANNO_NASCITA, sinbaDaEsportare.getAnnoNascita());
		if (sinbaDaEsportare.getCodNazioneResidenza() != null)
			MappaDatiGenBene.put(Cost.NAZIONE_RESIDENZA, sinbaDaEsportare.getCodNazioneResidenza());
		if (sinbaDaEsportare.getNumeroCompIsee() != null)
			MappaDatiGenBene.put(Cost.NUMERO_COMPONENTI_ISEE, sinbaDaEsportare.getNumeroCompIsee());
		if (sinbaDaEsportare.getFasciaEtaBeneficiario() != null)
			MappaDatiGenBene.put(Cost.FASCIA_ETA_BENEFICIARIO, sinbaDaEsportare.getFasciaEtaBeneficiario());
		if (sinbaDaEsportare.getFasciaIseeBeneficiario() != null)
			MappaDatiGenBene.put(Cost.FASCIA_ISEE_BENEFICIARIO, sinbaDaEsportare.getFasciaIseeBeneficiario());
		if (sinbaDaEsportare.getCondizioneMinore() != null)
			MappaDatiGenBene.put(Cost.CONDIZIONE_MINORE, sinbaDaEsportare.getCondizioneMinore());
		if (sinbaDaEsportare.getLuogoVita() != null)
			MappaDatiGenBene.put(Cost.LUOGO_VITA, sinbaDaEsportare.getLuogoVita());
		if (sinbaDaEsportare.getCodRegioneResidenzaFam() != null)
			MappaDatiGenBene.put(Cost.NAZIONE_RESIDENZA_FAM, sinbaDaEsportare.getCodRegioneResidenzaFam());
		if (sinbaDaEsportare.getCodNazioneResidenzaFam() != null)
			MappaDatiGenBene.put(Cost.REGIONE_RESIDENZA_FAM, sinbaDaEsportare.getCodNazioneResidenzaFam());
		if (sinbaDaEsportare.getMinoreStranieroNonAccompagnato() != null)
			MappaDatiGenBene.put(Cost.MINORE_STRANIERO_NON_ACCOMPAGNATO, sinbaDaEsportare.getMinoreStranieroNonAccompagnato());
		if (sinbaDaEsportare.getCittadinanzaMadre() != null)
			MappaDatiGenBene.put(Cost.CITTADINANZA_MADRE, sinbaDaEsportare.getCittadinanzaMadre());
		if (sinbaDaEsportare.getCittadinanzaPadre() != null)
			MappaDatiGenBene.put(Cost.CITTADINANZA_PADRE, sinbaDaEsportare.getCittadinanzaPadre());
		if (sinbaDaEsportare.getRegioneResidenzaMadre() != null)
			MappaDatiGenBene.put(Cost.REGIONE_RESIDENZA_MADRE, sinbaDaEsportare.getRegioneResidenzaMadre());
		if (sinbaDaEsportare.getRegioneResidenzaPadre() != null)
			MappaDatiGenBene.put(Cost.REGIONE_RESIDENZA_PADRE, sinbaDaEsportare.getRegioneResidenzaPadre());
		if (sinbaDaEsportare.getTitoloStudioMadre() != null)
			MappaDatiGenBene.put(Cost.TITOLO_STUDIO_MADRE, sinbaDaEsportare.getTitoloStudioMadre());
		if (sinbaDaEsportare.getTitoloStudioPadre() != null)
			MappaDatiGenBene.put(Cost.TITOLO_STUDIO_PADRE, sinbaDaEsportare.getTitoloStudioPadre());
		if (sinbaDaEsportare.getOccupazioneMadre() != null)
			MappaDatiGenBene.put(Cost.OCCUPAZIONE_MADRE, sinbaDaEsportare.getOccupazioneMadre());
		if (sinbaDaEsportare.getOccupazionePadre() != null)
			MappaDatiGenBene.put(Cost.OCCUPAZIONE_PADRE, sinbaDaEsportare.getOccupazionePadre());
		if (sinbaDaEsportare.getDisabile() != null)
			MappaDatiGenBene.put(Cost.DISABILE, sinbaDaEsportare.getDisabile());
		if (sinbaDaEsportare.getTipoDisabilita() != null)
			MappaDatiGenBene.put(Cost.TIPO_DISABILITA, sinbaDaEsportare.getTipoDisabilita());
		if (sinbaDaEsportare.getFonteSegnalazione() != null)
			MappaDatiGenBene.put(Cost.FONTE_SEGNALAZIONE, sinbaDaEsportare.getFonteSegnalazione());
		if (sinbaDaEsportare.getDataSegnalazione() != null)
			MappaDatiGenBene.put(Cost.DATA_SEGNALAZIONE, sinbaDaEsportare.getDataSegnalazione());
		if (sinbaDaEsportare.getSegnalazioneAutoritaGiudiziaria() != null)
			MappaDatiGenBene.put(Cost.SEGNALAZIONE_AUTORITA_GIUDIZIARIA, sinbaDaEsportare.getSegnalazioneAutoritaGiudiziaria());
		if (sinbaDaEsportare.getProvvedimentoGiudiziario() != null)
			MappaDatiGenBene.put(Cost.PROVVEDIMENTO_GIUDIZIARIO, sinbaDaEsportare.getProvvedimentoGiudiziario());
		if (sinbaDaEsportare.getValutazioneMinore() != null)
			MappaDatiGenBene.put(Cost.VALUTAZIONE_MINORE, sinbaDaEsportare.getValutazioneMinore());
		if (sinbaDaEsportare.getValutazioneFamigliaMinore() != null)
			MappaDatiGenBene.put(Cost.VALUTAZIONE_FAMIGLIA_MINORE, sinbaDaEsportare.getValutazioneFamigliaMinore());
		if (sinbaDaEsportare.getAutoritaProvvedimentoGiudiziario() != null)
			MappaDatiGenBene.put(Cost.AUTORITA_PROVVEDIMENTO_GIUDIZIARIO, sinbaDaEsportare.getAutoritaProvvedimentoGiudiziario());
		if (sinbaDaEsportare.getDataProvvedimentoGiudiziario() != null)
			MappaDatiGenBene.put(Cost.DATA_PROVVEDIMENTO_GIUDIZIARIO, sinbaDaEsportare.getDataProvvedimentoGiudiziario());
		if (sinbaDaEsportare.getTipoProvvedimento() != null)
			MappaDatiGenBene.put(Cost.TIPO_PROVVEDIMENTO, sinbaDaEsportare.getTipoProvvedimento());
		if (sinbaDaEsportare.getPotestaTutela() != null)
			MappaDatiGenBene.put(Cost.POTESTA_TUTELA, sinbaDaEsportare.getPotestaTutela());
		if (sinbaDaEsportare.getFormaIntervento() != null)
			MappaDatiGenBene.put(Cost.FORMA_INTERVENTO, sinbaDaEsportare.getFormaIntervento());
		if (sinbaDaEsportare.getTipoIntervento() != null)
			MappaDatiGenBene.put(Cost.TIPO_INTERVENTO, sinbaDaEsportare.getTipoIntervento());
		if (sinbaDaEsportare.getDurataIntervento() != null)
			MappaDatiGenBene.put(Cost.DURATA_INTERVENTO, sinbaDaEsportare.getDurataIntervento());
		if (sinbaDaEsportare.getCarattereIntervento() != null)
			MappaDatiGenBene.put(Cost.CARATTERE_INTERVENTO, sinbaDaEsportare.getCarattereIntervento());
		if (sinbaDaEsportare.getEsitoIntervento() != null)
			MappaDatiGenBene.put(Cost.ESITO_INTERVENTO, sinbaDaEsportare.getEsitoIntervento());
		if (sinbaDaEsportare.getCarattereInserimento() != null)
			MappaDatiGenBene.put(Cost.CARATTERE_INSERIMENTO, sinbaDaEsportare.getCarattereInserimento());
		if (sinbaDaEsportare.getFormaInserimento() != null)
			MappaDatiGenBene.put(Cost.FORMA_INSERIMENTO, sinbaDaEsportare.getFormaInserimento());
		if (sinbaDaEsportare.getEsitoInserimentoStruttura() != null)
			MappaDatiGenBene.put(Cost.ESITO_INSERIMENTO_STRUTTURA, sinbaDaEsportare.getEsitoInserimentoStruttura());
		if (sinbaDaEsportare.getCollaborazioneInterventi() != null)
			MappaDatiGenBene.put(Cost.COLLABORAZIONE_INTERVENTI, sinbaDaEsportare.getCollaborazioneInterventi());
		if (sinbaDaEsportare.getMotivazioneChiusuraCarico() != null)
			MappaDatiGenBene.put(Cost.MOTIVAZIONE_CHIUSURA_CARICO, sinbaDaEsportare.getMotivazioneChiusuraCarico());
		if (sinbaDaEsportare.getSituazioneChiusuraCarico() != null)
			MappaDatiGenBene.put(Cost.SITUAZIONE_CHIUSURA_CARICO, sinbaDaEsportare.getSituazioneChiusuraCarico());
		
		return MappaDatiGenBene;
	}
	
	private List<Map<String, Object>> estraiDatiFamigliaBeneficiario(SinbaDTO sinbaDaEsportare) {
		List<Map<String, Object>> mappaDatiFam = new ArrayList<Map<String, Object>>();
		
		//TODO mappa tutti gli altri campi, con aggiunta delle costanti
		/* prestazione periodica */
		logger.debug(sinbaDaEsportare);

		for (String componente : sinbaDaEsportare.getComponentiFamigliaSel()) {
			Map<String, Object> mappaDatiFamiglia = new HashMap<String, Object>();
			if (componente != null)
			{
				mappaDatiFamiglia.put(Cost.COMPOSIZIONE_FAMIGLIA, componente);
				mappaDatiFam.add(mappaDatiFamiglia);
			}
				
			
		}
		
		
		return mappaDatiFam;
	}

	private List<Map<String, Object>> estraiDatiPrestazione(SinbaDTO sinbaDaEsportare) {
		List<Map<String, Object>> mappaDatiPrestazioni =  new ArrayList<Map<String, Object>>();

		/* prestazione periodica */
		logger.debug(sinbaDaEsportare);

		for (String prestazione : sinbaDaEsportare.getPrestazioniSel()) {
			Map<String, Object> mappaDatiPrestazione = new HashMap<String, Object>();
			if (prestazione != null)
			{
				mappaDatiPrestazione.put(Cost.PRESTAZIONE_CODICE, prestazione);
				mappaDatiPrestazioni.add(mappaDatiPrestazione);
			}
				
			
		}
		

		return mappaDatiPrestazioni;
	}
	
}
