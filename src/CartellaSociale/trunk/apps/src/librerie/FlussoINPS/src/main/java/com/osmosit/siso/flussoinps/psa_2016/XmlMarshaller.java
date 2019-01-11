package com.osmosit.siso.flussoinps.psa_2016;

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
import com.osmosit.siso.flussoinps.psa_2016.binding.Anagrafica;
import com.osmosit.siso.flussoinps.psa_2016.binding.Beneficiario;
import com.osmosit.siso.flussoinps.psa_2016.binding.Ente;
import com.osmosit.siso.flussoinps.psa_2016.binding.Flusso;
import com.osmosit.siso.flussoinps.psa_2016.binding.Genere;
import com.osmosit.siso.flussoinps.psa_2016.binding.IdentificazioneFlusso;
import com.osmosit.siso.flussoinps.psa_2016.binding.Indirizzo;
import com.osmosit.siso.flussoinps.psa_2016.binding.ObjectFactory;
import com.osmosit.siso.flussoinps.psa_2016.binding.PrestazioniSociali;
import com.osmosit.siso.flussoinps.psa_2016.binding.SINA;

class XmlMarshaller {

	private final File outputFile;

	private final ObjectFactory f;

	final DataTypeUtils u;



	XmlMarshaller(String outputFilePath, DataTypeUtils u) {
		outputFile = new File(outputFilePath);
		f = new ObjectFactory();
		this.u = u;
	}



	void marshalToXml(
			String nomeFlusso,
			String denominazioneEnte,
			String codiceEnte,
			String cfOperatore,
			String indirizzoEnte,
			List<Map<String, Object>> listaBeneficiariConErogazione) throws Exception {

		Flusso flusso = f.createFlusso();

		IdentificazioneFlusso identificazioneFlusso = f.createIdentificazioneFlusso();
		identificazioneFlusso.setNome(nomeFlusso);
		flusso.setIdentificazioneFlusso(identificazioneFlusso);

		Ente ente = f.createEnte();
		ente.setDenominazione(denominazioneEnte);
		ente.setCodice(codiceEnte);
		ente.setIndirizzo(indirizzoEnte);
		ente.setCFOperatore(cfOperatore);
		identificazioneFlusso.setEnte(ente);

		for (Map<String, Object> datiBeneficiario : listaBeneficiariConErogazione) {
			Beneficiario beneficiario = f.createBeneficiario();
			beneficiario.setCodiceFiscale(u.getString(datiBeneficiario, Cost.BENEFICIARIO_CF));

			Anagrafica anagrafica = f.createAnagrafica();
			anagrafica.setAnnoNascita(u.getInteger(datiBeneficiario, Cost.ANAGRAFICA_ANNONASCITA).toString());
			Genere genere = f.createGenere();
			genere.setValue(u.getInteger(datiBeneficiario, Cost.ANAGRAFICA_SESSO).toString());
			anagrafica.setGenere(genere);
			anagrafica.setCodiceCittadinanza(u.getInteger(datiBeneficiario, Cost.ANAGRAFICA_CITTAD_ISO).toString());
			anagrafica.setNome(u.getString(datiBeneficiario, Cost.ANAGRAFICA_NOME));
			anagrafica.setCodiceSecondaCittadinanza(u.getString(datiBeneficiario, Cost.ANAGRAFICA_SEC_CITTAD_ISO));
			anagrafica.setLuogoNascita(u.getString(datiBeneficiario, Cost.ANAGRAFICA_LUOGONASCITA));
			anagrafica.setCognome(u.getString(datiBeneficiario, Cost.ANAGRAFICA_COGNOME));
			beneficiario.setAnagrafica(anagrafica);

			Indirizzo indirizzo = f.createIndirizzo();
			boolean esisteIndirizzo = !u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.RESIDENZA_COMUNE));
			indirizzo.setCodiceComune(u.getString(datiBeneficiario, Cost.RESIDENZA_COMUNE));

			esisteIndirizzo |= !u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.RESIDENZA_REGIONE));
			indirizzo.setCodiceRegione(u.getString(datiBeneficiario, Cost.RESIDENZA_REGIONE));

			esisteIndirizzo |= !u.isNullOrEmpty(u.getString(datiBeneficiario, Cost.RESIDENZA_NAZIONE));
			indirizzo.setCodiceNazione(u.getString(datiBeneficiario, Cost.RESIDENZA_NAZIONE));

			if (esisteIndirizzo)
				beneficiario.getIndirizzo().add(indirizzo);

			Collection<PrestazioniSociali> prestazioniSocialiPerBeneficiario = createPrestazioniSociali(datiBeneficiario);
			if (prestazioniSocialiPerBeneficiario.size() > 0) {
				beneficiario.getPrestazioniSociali().addAll(prestazioniSocialiPerBeneficiario);
			}

			flusso.getBeneficiario().add(beneficiario);
		}

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



	private Collection<PrestazioniSociali> createPrestazioniSociali(Map<String, Object> datiBeneficiario) throws Exception {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> prestazioni = (List<Map<String, Object>>) datiBeneficiario.get("listaPrestazioni");
		if (prestazioni.size() == 0)
			return new ArrayList<PrestazioniSociali>();
		else {
			List<PrestazioniSociali> prestazioniSocialiList = new ArrayList<PrestazioniSociali>();
			for (Map<String, Object> datiPrestazione : prestazioni) {
				PrestazioniSociali ps = f.createPrestazioniSociali();
 

				Integer presenzaProvaMezzi = u.getInteger(datiPrestazione, Cost.PRESTAZIONE_PRESENZA_PROVA_MEZZI);
				ps.setPresenzaProvaMezzi(Integer.toString(presenzaProvaMezzi));
				
				Integer caratterePrestazione = u.getInteger(datiPrestazione, Cost.PRESTAZIONE_CARATTERE);
				ps.setCaratterePrestazione(Integer.toString(caratterePrestazione));

				if (presenzaProvaMezzi.intValue() == 1) {
					Integer annoProtDSU = u.getInteger(datiPrestazione, Cost.PRESTAZIONE_ANNO_PROT);
					ps.setNumeroProtocolloDSU(u.getString(datiPrestazione, Cost.PRESTAZIONE_NUMPROT_DSU));
					ps.setAnnoProtocolloDSU(annoProtDSU!=null ? Integer.toString(annoProtDSU) : null);
					ps.setDataDSU(u.getXmlGregorianCalendar(datiPrestazione, Cost.PRESTAZIONE_DATA_DSU));
				}

				ps.setCodicePrestazione(u.getString(datiPrestazione, Cost.PRESTAZIONE_CODICE));
				ps.setDenominazionePrestazione(u.getString(datiPrestazione, Cost.PRESTAZIONE_DENOMINAZIONE));
				ps.setProtocolloDomanda(u.getString(datiPrestazione, Cost.PRESTAZIONE_PROTOC_ENTE));
				// data evento - IGNORARE

				if (caratterePrestazione == 1) {
					// prestazione periodica
					ps.setDataInizioPrestazione(u.getXmlGregorianCalendar(datiPrestazione, Cost.PRESTAZIONE_DATA_INIZIO));
					ps.setDataFinePrestazione(u.getXmlGregorianCalendar(datiPrestazione, Cost.PRESTAZIONE_DATA_FINE));
					ps.setPeriodoErogazione(u.getInteger(datiPrestazione, Cost.PRESTAZIONE_PERIOD_EROG));
					ps.setImportoMensile(u.getBigDecimal(datiPrestazione, Cost.PRESTAZIONE_IMPORTO_MENS));
				}
				else {
					// prestazione occasionale
					ps.setDataErogazionePrestazione(u.getXmlGregorianCalendar(datiPrestazione, Cost.PRESTAZIONE_DATA_EROG));
					ps.setImportoPrestazione(u.getBigDecimal(datiPrestazione, Cost.PRESTAZIONE_IMPORTO));
				}

				ps.setImportoQuotaEnte(u.getBigDecimal(datiPrestazione, Cost.PRESTAZIONE_QUOTA_ENTE));
				ps.setImportoQuotaUtente(u.getBigDecimal(datiPrestazione, Cost.PRESTAZIONE_QUOTA_UTENTE));
				ps.setImportoQuotaSSN(u.getBigDecimal(datiPrestazione, Cost.PRESTAZIONE_QUOTA_SSN));
				ps.setImportoQuotaRichiesta(u.getBigDecimal(datiPrestazione, Cost.PRESTAZIONE_QUOTA_RICHIESTA));
				//Riccardini dice di ignorare (vedi doc SISO-433.docx) ps.setImportoSogliaISEE(u.getBigDecimal(datiPrestazione, Cost.PRESTAZIONE_SOGLIA_ISEE));
				// ore servizio mensile - NON RIPORTARE   ps.setOreServizioMensile(value);
				
				//1 se CS_I_PS.FLAG_IN_CARICO =1
				//2 se CS_I_PS.FLAG_IN_CARICO =0  
				if (u.getInteger(datiPrestazione, Cost.PRESTAZIONE_PRESA_IN_CARICO) == 1) { 
					ps.setPresaCarico("1");	
					// AREA UTENZA Obbligatoria se PresaCarico=”1”: 
					//“1”  se l’intervento appartiene alla categoria sociale “Famiglia e Minori”, 
					//“2”  se l’intervento appartiene alla categoria sociale “Disabili”,
					//“3”  in tutti gli altri casi .
					ps.setAreaUtenza(u.getString(datiPrestazione, Cost.PRESTAZIONE_AREA_UTENZA));
				} else {
					ps.setPresaCarico("2");	 
				}
				
				
				//ImportoSogliaISEE - MI SEMBRA NON FOSSE PRESENTE, IGNORARE
				//TipoOperazione IGNORARE ps.setTipoOperazione(value);
				
				SINA sina = createSina(datiBeneficiario, datiPrestazione);
				if (sina != null)
					ps.setSINA(sina);
				
				prestazioniSocialiList.add(ps);
			}
			return prestazioniSocialiList;
		}
	}



	/**
	 * Metodo di supporto per aggiungere i sottoelementi che compongono la parte
	 * SINA. <i>Non implementato in questa versione</i>. Eventuali sottoclassi
	 * possono utilizzare senza variazioni il processo esistente e implementare
	 * solo la parte relativa a SINA.
	 * <p>
	 * La firma del metodo è indicativa. Inizialmente si ipotizza di utilizzare
	 * tutte le informazioni disponibili per il beneficiario e per la specifica
	 * prestazione.
	 * 
	 * 
	 * @param datiBeneficiario
	 * @param datiPrestazione
	 * @return null in questa versione
	 */
	protected SINA createSina(Map<String, Object> datiBeneficiario, Map<String, Object> datiPrestazione) {
		return null;
	}

}
