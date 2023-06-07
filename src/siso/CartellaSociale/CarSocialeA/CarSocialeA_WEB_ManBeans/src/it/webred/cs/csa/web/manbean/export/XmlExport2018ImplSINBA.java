package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.dto.SinbaDTO;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
				
				
				prestazioniMinoreSinba = (List<Map<String, Object>>) sinbaMinore.get(Cost.LIST_CODICI_PRESTAZIONE);
				
				if (prestazioniMinoreSinba == null) {
					prestazioniMinoreSinba = new ArrayList<Map<String, Object>>();
				}
				
				List<Map<String, Object>> prestazioni = estraiDatiPrestazione(e);
				prestazioniMinoreSinba.addAll(prestazioni);
				sinbaMinore.put(Cost.LIST_CODICI_PRESTAZIONE,prestazioniMinoreSinba);
				
				//TODO fai cosa analoga per i famigliari
				componentiFamigliaMinoreSinba = (List<Map<String, Object>>) sinbaMinore.get(Cost.LIST_COMPOSIZIONE_FAMIGLIA);
				
				if (componentiFamigliaMinoreSinba == null) {
					componentiFamigliaMinoreSinba = new ArrayList<Map<String, Object>>();
				}
				
				List<Map<String, Object>> familiari = estraiDatiFamigliaBeneficiario(e);
				componentiFamigliaMinoreSinba.addAll(familiari);
				
				sinbaMinore.put(Cost.LIST_COMPOSIZIONE_FAMIGLIA,componentiFamigliaMinoreSinba);
				
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
		
		//Composizione famiglia caricata in forma di lista
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.BENEFICIARIO_ID, sinbaDaEsportare.getCodiceAnonimoBeneficiario());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.ANNO_NASCITA, sinbaDaEsportare.getAnnoNascita());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.RESIDENZA_NAZIONE, sinbaDaEsportare.getCodNazioneResidenza());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.RESIDENZA_REGIONE, sinbaDaEsportare.getCodRegioneResidenza());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.MINORE_STRANIERO_NON_ACCOMPAGNATO, sinbaDaEsportare.getMinoreStranieroNonAccompagnato());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.CONDIZIONE_MINORE, sinbaDaEsportare.getCondizioneMinore());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.LUOGO_VITA, sinbaDaEsportare.getLuogoVita());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.SCUOLA_FREQUENTATA, sinbaDaEsportare.getScuolaFrequentata());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.CONDIZIONE_LAVORO, sinbaDaEsportare.getCondizioneLavoro());		
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.DISABILE, sinbaDaEsportare.getDisabile());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.TIPO_DISABILITA, sinbaDaEsportare.getTipoDisabilita());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.CERTIFICAZIONE_INVALIDITA_CIVILE, sinbaDaEsportare.getInvCivCertificazioni());
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.CITTADINANZA_MADRE, sinbaDaEsportare.getCittadinanzaMadre());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.CITTADINANZA_PADRE, sinbaDaEsportare.getCittadinanzaPadre());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.REGIONE_RESIDENZA_MADRE, sinbaDaEsportare.getRegioneResidenzaMadre());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.REGIONE_RESIDENZA_PADRE, sinbaDaEsportare.getRegioneResidenzaPadre());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.TITOLO_STUDIO_MADRE, sinbaDaEsportare.getTitoloStudioMadre());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.TITOLO_STUDIO_PADRE, sinbaDaEsportare.getTitoloStudioPadre());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.OCCUPAZIONE_MADRE, sinbaDaEsportare.getOccupazioneMadre());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.OCCUPAZIONE_PADRE, sinbaDaEsportare.getOccupazionePadre());
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.DATA_PRIMA_SEGNALAZIONE, sinbaDaEsportare.getDataPrimaSegnalazione());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.FONTE_SEGNALAZIONE, sinbaDaEsportare.getFonteSegnalazione());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.VALUTAZIONE_MINORE, sinbaDaEsportare.getValutazioneMinore());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.VALUTAZIONE_FAMIGLIA_MINORE, sinbaDaEsportare.getValutazioneFamigliaMinore());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.SEGNALAZIONE_AUTORITA_GIUDIZIARIA, sinbaDaEsportare.getSegnalazioneAutoritaGiudiziaria());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.DATA_SEGNALAZIONE, sinbaDaEsportare.getDataSegnalazione());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.PROVVEDIMENTO_GIUDIZIARIO, sinbaDaEsportare.getProvvedimentoGiudiziario());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.DATA_PROVVEDIMENTO_GIUDIZIARIO, sinbaDaEsportare.getDataProvvedimentoGiudiziario());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.AUTORITA_PROVVEDIMENTO_GIUDIZIARIO, sinbaDaEsportare.getAutoritaProvvedimentoGiudiziario());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.POTESTA_TUTELA, sinbaDaEsportare.getPotestaTutela());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.TIPO_PROVVEDIMENTO, sinbaDaEsportare.getTipoProvvedimento());
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.FORMA_INTERVENTO, sinbaDaEsportare.getFormaInterventoAffido());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.TIPO_INTERVENTO, sinbaDaEsportare.getTipoInterventoAffido());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.DURATA_INTERVENTO, sinbaDaEsportare.getDurataAffido());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.CARATTERE_INTERVENTO, sinbaDaEsportare.getCarattereAffido());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.ESITO_INTERVENTO, sinbaDaEsportare.getEsitoAffido());
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.CARATTERE_INSERIMENTO, sinbaDaEsportare.getCarattereInserimentoResidenziale());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.FORMA_INSERIMENTO, sinbaDaEsportare.getFormaInserimentoResidenziale());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.TIPO_INSERIMENTO, sinbaDaEsportare.getTipoInserimentoResidenziale());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.ESITO_INSERIMENTO_STRUTTURA, sinbaDaEsportare.getEsitoInserimentoStruttura());
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.MOTIVAZIONE_CHIUSURA_CARICO, sinbaDaEsportare.getMotivazioneChiusuraCarico());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.SITUAZIONE_CHIUSURA_CARICO, sinbaDaEsportare.getSituazioneChiusuraCarico());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.COLLABORAZIONE_INTERVENTI, sinbaDaEsportare.getCollaborazioneInterventi());
		
		this.popolaElementoMappa(MappaDatiGenBene, Cost.NUMERO_COMPONENTI_ISEE, sinbaDaEsportare.getNumeroCompIsee());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.FASCIA_ETA_BENEFICIARIO, sinbaDaEsportare.getFasciaEtaBeneficiario());
		this.popolaElementoMappa(MappaDatiGenBene, Cost.FASCIA_ISEE_BENEFICIARIO, sinbaDaEsportare.getFasciaIseeBeneficiario());
		
		return MappaDatiGenBene;
	}
	
	private void popolaElementoMappa(Map<String,Object> MappaDatiGenBene, String key, String value){
		if (!StringUtils.isBlank(value))
			MappaDatiGenBene.put(key, value);
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
				mappaDatiFamiglia.put(Cost.DETT_COMPOSIZIONE_FAMIGLIA, componente);
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
