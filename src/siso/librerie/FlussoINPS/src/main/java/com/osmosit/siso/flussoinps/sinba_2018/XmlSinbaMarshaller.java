package com.osmosit.siso.flussoinps.sinba_2018;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.osmosit.siso.flussoinps.logic.Cost;
import com.osmosit.siso.flussoinps.sinba_2018.binding.Ente;
import com.osmosit.siso.flussoinps.sinba_2018.binding.Flusso;
import com.osmosit.siso.flussoinps.sinba_2018.binding.IdentificazioneFlusso;
import com.osmosit.siso.flussoinps.sinba_2018.binding.ObjectSinbaFactory;
import com.osmosit.siso.flussoinps.sinba_2018.binding.SINBA;

class XmlSinbaMarshaller {

	private final File outputFile;

	private final ObjectSinbaFactory f;

	final DataTypeSinbaUtils u;

	XmlSinbaMarshaller(String outputFilePath, DataTypeSinbaUtils u) {
		outputFile = new File(outputFilePath);
		f = new ObjectSinbaFactory();
		this.u = u;
	}

	void marshalToXml(String nomeFlusso, String denominazioneEnte,
			String codiceEnte, String cfOperatore, String indirizzoEnte,
			List<Map<String, Object>> listaMinoriConSinba) throws Exception {

		// TODO allineare ai parametri del SINBA
		Flusso flusso = f.createFlusso();

		IdentificazioneFlusso identificazioneFlusso = f
				.createIdentificazioneFlusso();
		identificazioneFlusso.setNome(nomeFlusso);
		flusso.setIdentificazioneFlusso(identificazioneFlusso);

		Ente ente = f.createEnte();
		ente.setDenominazione(denominazioneEnte);
		ente.setCodice(codiceEnte);
		ente.setIndirizzo(indirizzoEnte);
		ente.setCFOperatore(cfOperatore);
		identificazioneFlusso.setEnte(ente);
		
		List<SINBA> sinbaList = new ArrayList<SINBA>();

		// TODO per ogni minoreSinba istanzio l'oggetto XMLmapper SINBA
		for (Map<String, Object> datiBeneficiario : listaMinoriConSinba) {
			SINBA sinbaMinore = f.createSinba();
			SINBA.Prestazioni prestazioniMinore = f.createSinbaPrestazioni();
			SINBA.Famiglia famigliaMinore = f.createSinbaFamiglia();
			
			sinbaMinore.setAnnoNascita(u.getStringFromInteger(datiBeneficiario, Cost.ANNO_NASCITA));
			sinbaMinore.setAutoritaProvvedimento(u.getStringFromInteger(datiBeneficiario, Cost.AUTORITA_PROVVEDIMENTO_GIUDIZIARIO));
			sinbaMinore.setCarattereAffido(u.getStringFromInteger(datiBeneficiario, Cost.CARATTERE_INTERVENTO));
			sinbaMinore.setCarattereInserimentoResidenziale(u.getStringFromInteger(datiBeneficiario, Cost.CARATTERE_INSERIMENTO));
			sinbaMinore.setCodiceCittadinanzaMadre(u.getStringFromInteger(datiBeneficiario, Cost.CITTADINANZA_MADRE));
			sinbaMinore.setCodiceCittadinanzaPadre(u.getStringFromInteger(datiBeneficiario, Cost.CITTADINANZA_PADRE));
			sinbaMinore.setCodiceNazioneResidenza(u.getStringFromInteger(datiBeneficiario, Cost.RESIDENZA_NAZIONE));
			sinbaMinore.setCodiceRegioneResidenza(u.getStringFromInteger(datiBeneficiario, Cost.RESIDENZA_REGIONE));
			sinbaMinore.setCodiceRegioneResidenzaMadre(u.getStringFromInteger(datiBeneficiario, Cost.REGIONE_RESIDENZA_MADRE));
			sinbaMinore.setCodiceRegioneResidenzaPadre(u.getStringFromInteger(datiBeneficiario, Cost.REGIONE_RESIDENZA_PADRE));
			sinbaMinore.setCollaborazioniInterventi(u.getStringFromInteger(datiBeneficiario, Cost.COLLABORAZIONE_INTERVENTI));
			sinbaMinore.setCondizioneLavoro(u.getStringFromInteger(datiBeneficiario, Cost.CONDIZIONE_LAVORO));
			sinbaMinore.setCondizioneMinore(u.getStringFromInteger(datiBeneficiario, Cost.CONDIZIONE_MINORE));
			sinbaMinore.setDataPrimaSegnalazione(u.getString(datiBeneficiario, Cost.DATA_SEGNALAZIONE));
			sinbaMinore.setDataProvvedimentoGiudiziario(u.getString(datiBeneficiario, Cost.DATA_PROVVEDIMENTO_GIUDIZIARIO));
			sinbaMinore.setDataSegnalazione(u.getString(datiBeneficiario, Cost.DATA_SEGNALAZIONE));
			sinbaMinore.setDisabilita(u.getStringFromInteger(datiBeneficiario, Cost.DISABILE));
			sinbaMinore.setDurataAffido(u.getStringFromInteger(datiBeneficiario, Cost.DURATA_INTERVENTO));
			sinbaMinore.setEsitoAffido(u.getStringFromInteger(datiBeneficiario, Cost.ESITO_INTERVENTO));
			sinbaMinore.setEsitoInserimentoStruttura(u.getStringFromInteger(datiBeneficiario, Cost.ESITO_INSERIMENTO_STRUTTURA));
			sinbaMinore.setFasciaEtaBeneficiario(u.getStringFromInteger(datiBeneficiario, Cost.FASCIA_ETA_BENEFICIARIO));
			sinbaMinore.setFasciaISEEBeneficiario(u.getStringFromInteger(datiBeneficiario, Cost.FASCIA_ISEE_BENEFICIARIO));
			sinbaMinore.setFonteSegnalazione(u.getStringFromInteger(datiBeneficiario, Cost.FONTE_SEGNALAZIONE));
			sinbaMinore.setFormaInserimentoResidenziale(u.getStringFromInteger(datiBeneficiario, Cost.FORMA_INSERIMENTO));
			sinbaMinore.setFormaInterventoAffido(u.getStringFromInteger(datiBeneficiario, Cost.FORMA_INTERVENTO));
			sinbaMinore.setLuogoVita(u.getStringFromInteger(datiBeneficiario, Cost.LUOGO_VITA));
			sinbaMinore.setMinoreStranieroNoAcc(u.getStringFromInteger(datiBeneficiario, Cost.MINORE_STRANIERO_ACCOMPAGNATO));
			sinbaMinore.setMotivazioneChiusuraCarico(u.getStringFromInteger(datiBeneficiario, Cost.MOTIVAZIONE_CHIUSURA_CARICO));
			sinbaMinore.setNumeroComponentiISEE(u.getStringFromInteger(datiBeneficiario, Cost.NUMERO_COMPONENTI_ISEE));
			sinbaMinore.setOccupazioneMadre(u.getStringFromInteger(datiBeneficiario, Cost.OCCUPAZIONE_MADRE));
			sinbaMinore.setOccupazionePadre(u.getStringFromInteger(datiBeneficiario, Cost.OCCUPAZIONE_PADRE));
			sinbaMinore.setPotestaTutela(u.getStringFromInteger(datiBeneficiario, Cost.POTESTA_TUTELA));
			sinbaMinore.setProvvedimentoGiudiziario(u.getStringFromInteger(datiBeneficiario, Cost.PROVVEDIMENTO_GIUDIZIARIO));
			sinbaMinore.setScuolaFrequentata(u.getStringFromInteger(datiBeneficiario, Cost.SCUOLA_FREQUENTATA));
			sinbaMinore.setSegnalazioneAutoritaGiudiziaria(u.getStringFromInteger(datiBeneficiario, Cost.SEGNALAZIONE_AUTORITA_GIUDIZIARIA));
			sinbaMinore.setSituazioneChiusuraCarico(u.getStringFromInteger(datiBeneficiario, Cost.SITUAZIONE_CHIUSURA_CARICO));
			sinbaMinore.setTipoDisabilita(u.getStringFromInteger(datiBeneficiario, Cost.TIPO_DISABILITA));
			sinbaMinore.setTipoInserimentoResidenziale(u.getStringFromInteger(datiBeneficiario, Cost.TIPO_INSERIMENTO));
			sinbaMinore.setTipoInterventoAffido(u.getStringFromInteger(datiBeneficiario, Cost.TIPO_INTERVENTO));
			sinbaMinore.setTipoProvvedimentoGiudiziario(u.getStringFromInteger(datiBeneficiario, Cost.TIPO_PROVVEDIMENTO));
			sinbaMinore.setTitoloStudioMadre(u.getStringFromInteger(datiBeneficiario, Cost.TITOLO_STUDIO_MADRE));
			sinbaMinore.setTitoloStudioPadre(u.getStringFromInteger(datiBeneficiario, Cost.TITOLO_STUDIO_PADRE));
			sinbaMinore.setValutazioneFamigliaMinore(u.getStringFromInteger(datiBeneficiario, Cost.VALUTAZIONE_FAMIGLIA_MINORE));
			sinbaMinore.setValutazioneMinore(u.getStringFromInteger(datiBeneficiario, Cost.VALUTAZIONE_MINORE));
			//IDBeneficiario="PROVA BENEFICIARIO
			sinbaMinore.setIDBeneficiario(u.getStringFromInteger(datiBeneficiario, Cost.BENEFICIARIO_ID));

			Collection<String> prestazioniPerMinore = createPrestazioniSociali(datiBeneficiario);
			if (prestazioniPerMinore.size() > 0) {
				prestazioniMinore.getCodicePrestazione().addAll(
						prestazioniPerMinore);
			}

			Collection<String> famigliariPerMinore = createCompFamigliari(datiBeneficiario);
			if (famigliariPerMinore.size() > 0) {
				famigliaMinore.getComposizioneFamiglia().addAll(
						famigliariPerMinore);
			}

			sinbaMinore.setPrestazioni(prestazioniMinore);
			sinbaMinore.setFamiglia(famigliaMinore);
			
			sinbaList.add(sinbaMinore);
		}
		
		flusso.setSINBA(sinbaList);

		JAXBContext context;
		try {
			context = JAXBContext.newInstance(Flusso.class);
			JAXBElement<Flusso> rootElement = f.createFlusso(flusso);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			marshaller.marshal(rootElement, System.out);
			marshaller.marshal(rootElement, outputFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

	}

	private Collection<String> createPrestazioniSociali(
			Map<String, Object> datiBeneficiario) throws Exception {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> prestazioni = (List<Map<String, Object>>) datiBeneficiario
				.get("prestazioniSel");
		if (prestazioni == null || prestazioni.size() == 0)
			return new ArrayList<String>();
		else {
			List<String> prestazioniList = new ArrayList<String>();
			for (Map<String, Object> datiPrestazione : prestazioni) {

				String prestazione = u.getString(datiPrestazione,
						Cost.PRESTAZIONE_CODICE);
				prestazioniList.add(prestazione);
			}
			return prestazioniList;
		}
	}

	private Collection<String> createCompFamigliari(
			Map<String, Object> datiBeneficiario) throws Exception {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> famigliari = (List<Map<String, Object>>) datiBeneficiario
				.get("componentiFamigliaSel");
		if (famigliari == null || famigliari.size() == 0)
			return new ArrayList<String>();
		else {
			List<String> famigliariList = new ArrayList<String>();
			for (Map<String, Object> datiFamigliari : famigliari) {

				String famigliare = u.getString(datiFamigliari,
						Cost.COMPOSIZIONE_FAMIGLIA);
				famigliariList.add(famigliare);
			}
			return famigliariList;
		}
	}

}
