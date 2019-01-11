package it.webred.cs.csa.web.manbean.export;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.osmosit.siso.flussoinps.logic.Cost;
import com.osmosit.siso.flussoinps.logic.XMLFactory;

import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.data.DataModelCostanti;

final class XmlExport2015Impl extends PsaXmlExporter {

	@Override
	File doExport(
			String XML_PATH,
			List<EsportazioneDTO> erogDaEsportareList,
			String idFlusso,
			String denomEnte,
			String codEnte,
			String cfOperatore,
			String indirEnte) {

		// SISO-538 ordino per codice fiscale, propedeutico per l'aggregazione
		// delle prestazioni sotto lo stesso beneficiario
		Collections.sort(erogDaEsportareList, new Comparator<EsportazioneDTO>() {
			@Override
			public int compare(EsportazioneDTO o1, EsportazioneDTO o2) {
				return o1.getSoggettoCodiceFiscale().compareTo(o2.getSoggettoCodiceFiscale());
			}
		});

		int numErogDaEsportare = erogDaEsportareList.size();
		if (numErogDaEsportare > 0) {
			XMLFactory xmlFactory = new XMLFactory(new File(XML_PATH));

			// per ogni beneficiario,ci possono essere più erogazioni
			List<HashMap> listBeneficiariErog = new ArrayList<HashMap>();
			String cfSoggettoPrec = erogDaEsportareList.get(0).getSoggettoCodiceFiscale();
			// TODO all'inizio inserisco la riga di esportazione, ovvero
			// soggetto beneficiario1 e erogazione1
			HashMap hmBen = insertDatiBeneficiario(erogDaEsportareList.get(0));
			List<HashMap> listPrestBen = new ArrayList<HashMap>();
			HashMap prestBen = insertDatiPrestazione(erogDaEsportareList.get(0));

			if (numErogDaEsportare > 1) {
				for (int i = 1; i < numErogDaEsportare; i++) {
					if (erogDaEsportareList.get(i).getSoggettoCodiceFiscale()
							.equalsIgnoreCase(erogDaEsportareList.get(i - 1).getSoggettoCodiceFiscale())) {
						// STESSO BENEFICIARIO
						listPrestBen.add(prestBen);

						prestBen = insertDatiPrestazione(erogDaEsportareList.get(i));

					} else {
						// NUOVO BENEFICIARIO

						listPrestBen.add(prestBen);
						hmBen.put("listaPrestazioni", listPrestBen);
						listBeneficiariErog.add(hmBen);

						listPrestBen = new ArrayList<HashMap>();
						hmBen = insertDatiBeneficiario(erogDaEsportareList.get(i));
						prestBen = insertDatiPrestazione(erogDaEsportareList.get(i));
					}
				}
			}

			/*
			 * all'ultimo devo aggiungere l'ultimo beneficiario a cui ho aggiunto
			 * l'ultima prestazione
			 */
			listPrestBen.add(prestBen);
			hmBen.put("listaPrestazioni", listPrestBen);
			listBeneficiariErog.add(hmBen);

			xmlFactory.createFlussoXML(idFlusso, denomEnte, codEnte, cfOperatore, indirEnte, listBeneficiariErog);

		}
		return new File(XML_PATH);
	}



	private HashMap<Object, Object> insertDatiBeneficiario(EsportazioneDTO erogDaEsportare) {
		HashMap<Object, Object> mappaDatiSogg = new HashMap<Object, Object>();
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
			mappaDatiSogg.put(Cost.ANAGRAFICA_ANNONASCITA, Integer.toString(erogDaEsportare.getBenefAnnoNascita()));
		if (erogDaEsportare.getBenefLuogoNascita() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_LUOGONASCITA, erogDaEsportare.getBenefLuogoNascita());
		if (erogDaEsportare.getBenefSesso() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_SESSO, Integer.toString(erogDaEsportare.getBenefSesso()));
		if (erogDaEsportare.getBenefCittadinanza() != null)
			mappaDatiSogg.put(Cost.ANAGRAFICA_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefCittadinanza()));
		// if(erogDaEsportare.getBenefSecCittadinanza()!=null)
		// mappaDatiSogg.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO,
		// Integer.toString(erogDaEsportare.getBenefSecCittadinanza()));

		return mappaDatiSogg;
	}



	private HashMap<Object, Object> insertDatiPrestazione(EsportazioneDTO erogDaEsportare) {
		HashMap<Object, Object> mappaDatiPrest = new HashMap<Object, Object>();

		/* prestazione periodica */
		logger.debug(erogDaEsportare);
		if (erogDaEsportare.getCarattere().equals(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)) {
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_INIZIO, datef.format(erogDaEsportare.getDataInizio()));
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_FINE, datef.format(erogDaEsportare.getDataFine()));
			mappaDatiPrest.put(Cost.PRESTAZIONE_PERIOD_EROG, String.valueOf(erogDaEsportare.getPeriodoErogazione()));
			mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO_MENS, df.format(erogDaEsportare.getImportoMensile()));
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()));
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_EVENTO, "");
		}

		/* prestazione occasionale */
		if (erogDaEsportare.getNumProtDSU() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_NUMPROT_DSU, erogDaEsportare.getNumProtDSU());
		if (erogDaEsportare.getAnnoProtDSU() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_ANNO_PROT, Integer.toString(erogDaEsportare.getAnnoProtDSU()));
		if (erogDaEsportare.getDataDSU() != null) {
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_DSU, datef.format(erogDaEsportare.getDataDSU()));
		}
		if (erogDaEsportare.getCodPrestazione() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_CODICE, erogDaEsportare.getCodPrestazione());
		if (erogDaEsportare.getDenomPrestazione() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_DENOMINAZIONE, erogDaEsportare.getDenomPrestazione());

		if (erogDaEsportare.getDataEsecuzione() != null) {
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_EROG, datef.format(erogDaEsportare.getDataEsecuzione()));
		}
		if (erogDaEsportare.getSpesa() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO, df.format(erogDaEsportare.getSpesa()));

		/* dati comuni */
		if (erogDaEsportare.getCarattere() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_CARATTERE, erogDaEsportare.getCarattere());
		if (erogDaEsportare.getPercGestitaEnte() != null) {
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()));
		}
		if (erogDaEsportare.getCompartUtenti() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_UTENTE, df.format(erogDaEsportare.getCompartUtenti()));
		if (erogDaEsportare.getCompartSsn() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_SSN, df.format(erogDaEsportare.getCompartSsn()));

		// if(erogDaEsportare.getSogliaISEE()!=null)
		// mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE,
		// decf.format(erogDaEsportare.getSogliaISEE()));

		if (erogDaEsportare.getPrestazioneProtocEnte() != null)
			mappaDatiPrest.put(Cost.PRESTAZIONE_PROTOC_ENTE, erogDaEsportare.getPrestazioneProtocEnte());

		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_RICHIESTA, "");
		mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE, "");

		return mappaDatiPrest;
	}

}
