package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.csa.ejb.dto.EsportazioneSpesaDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneTestataDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.CSIPs.FLAG_IN_CARICO;
import it.webred.cs.data.model.ArTbPrestazioniInps;
import it.webred.cs.data.model.CsDSinaEsegLIGHT;
import it.webred.cs.data.model.CsDSinaLIGHT;
import it.webred.cs.data.model.VErogExportHelp;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jboss.logging.Logger;

public class EsportaCasellarioUtils {

	public static Logger logger = Logger.getLogger("carsociale.log");


//@formatter:off
	public static File esportaCasellario(
														String XML_PATH,
														List<EsportazioneDTO> erogDaEsportareList,
														String idFlusso,
														String denomEnte,
														String codEnte,
														String cfOperatore,
														String indirEnte,
														SchemaVersion schemaVersion) throws Exception {
//@formatter:on

		PsaXmlExporter xmlExporter = PsaExportFactory.getExporter(schemaVersion);
		if (xmlExporter == null)
			throw new Exception("Nessun convertitore XML specificato");

		return xmlExporter.doExport(XML_PATH, erogDaEsportareList, idFlusso, denomEnte, codEnte, cfOperatore, indirEnte);		
		
//		// SISO-538 ordino per codice fiscale, propedeutico per l'aggregazione
//		// delle prestazioni sotto lo stesso beneficiario
//		Collections.sort(erogDaEsportareList, new Comparator<EsportazioneDTO>() {
//			@Override
//			public int compare(EsportazioneDTO o1, EsportazioneDTO o2) {
//				return o1.getSoggettoCodiceFiscale().compareTo(o2.getSoggettoCodiceFiscale());
//			}
//		});
//
//		int numErogDaEsportare = erogDaEsportareList.size();
//		if (numErogDaEsportare > 0) {
//			XMLFactory xmlFactory = new XMLFactory(new File(XML_PATH));
//
//			// per ogni beneficiario,ci possono essere più erogazioni
//			List<HashMap> listBeneficiariErog = new ArrayList<HashMap>();
//			String cfSoggettoPrec = erogDaEsportareList.get(0).getSoggettoCodiceFiscale();
//			// T0DO all'inizio inserisco la riga di esportazione, ovvero
//			// soggetto beneficiario1 e erogazione1
//			HashMap hmBen = insertDatiBeneficiario(erogDaEsportareList.get(0));
//			List<HashMap> listPrestBen = new ArrayList<HashMap>();
//			HashMap prestBen = insertDatiPrestazione(erogDaEsportareList.get(0));
//
//			if (numErogDaEsportare > 1) {
//				for (int i = 1; i < numErogDaEsportare; i++) {
//					if (erogDaEsportareList.get(i).getSoggettoCodiceFiscale()
//							.equalsIgnoreCase(erogDaEsportareList.get(i - 1).getSoggettoCodiceFiscale())) {
//						// STESSO BENEFICIARIO
//						listPrestBen.add(prestBen);
//
//						prestBen = insertDatiPrestazione(erogDaEsportareList.get(i));
//
//					} else {
//						// NUOVO BENEFICIARIO
//
//						listPrestBen.add(prestBen);
//						hmBen.put("listaPrestazioni", listPrestBen);
//						listBeneficiariErog.add(hmBen);
//
//						listPrestBen = new ArrayList<HashMap>();
//						hmBen = insertDatiBeneficiario(erogDaEsportareList.get(i));
//						prestBen = insertDatiPrestazione(erogDaEsportareList.get(i));
//					}
//				}
//			}
//
//			/*
//			 * all'ultimo devo aggiungere l'ultimo beneficiario a cui ho
//			 * aggiunto l'ultima prestazione
//			 */
//			listPrestBen.add(prestBen);
//			hmBen.put("listaPrestazioni", listPrestBen);
//			listBeneficiariErog.add(hmBen);
//
//			xmlFactory.createFlussoXML(idFlusso, denomEnte, codEnte, cfOperatore, indirEnte, listBeneficiariErog);
//
//		}
//		return new File(XML_PATH);
	}
	
//	private static HashMap<Object, Object> insertDatiBeneficiario(EsportazioneDTO erogDaEsportare){		
//		HashMap<Object, Object> mappaDatiSogg= new HashMap<Object, Object>();
//		if(erogDaEsportare.getSoggettoCodiceFiscale()!=null)
//		mappaDatiSogg.put(Cost.BENEFICIARIO_CF,erogDaEsportare.getSoggettoCodiceFiscale());
//		if(erogDaEsportare.getBenefRegione()!=null)
//		mappaDatiSogg.put(Cost.RESIDENZA_REGIONE, erogDaEsportare.getBenefRegione());
//		if(erogDaEsportare.getBenefComune()!=null)
//		mappaDatiSogg.put(Cost.RESIDENZA_COMUNE, erogDaEsportare.getBenefComune());
//		if(erogDaEsportare.getBenefNazione()!=null)
//		mappaDatiSogg.put(Cost.RESIDENZA_NAZIONE, erogDaEsportare.getBenefNazione());
//		if(erogDaEsportare.getSoggettoNome()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_NOME,erogDaEsportare.getSoggettoNome());
//		if(erogDaEsportare.getSoggettoCognome()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_COGNOME, erogDaEsportare.getSoggettoCognome());
//		if(erogDaEsportare.getBenefAnnoNascita()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_ANNONASCITA, Integer.toString(erogDaEsportare.getBenefAnnoNascita()));
//		if(erogDaEsportare.getBenefLuogoNascita()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_LUOGONASCITA, erogDaEsportare.getBenefLuogoNascita());
//		if(erogDaEsportare.getBenefSesso()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_SESSO,Integer.toString(erogDaEsportare.getBenefSesso()));
//		if(erogDaEsportare.getBenefCittadinanza()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefCittadinanza()));
////		if(erogDaEsportare.getBenefSecCittadinanza()!=null)
////		mappaDatiSogg.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefSecCittadinanza()));		
//		
//		return mappaDatiSogg;
//	}
	

//	private static HashMap<Object, Object> insertDatiPrestazione(EsportazioneDTO erogDaEsportare){
//		HashMap<Object, Object> mappaDatiPrest= new HashMap<Object, Object>();
//		
//        /* prestazione periodica */
//        logger.debug(erogDaEsportare);
//        if (erogDaEsportare.getCarattere().equals(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)) {
//    		mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_INIZIO, datef.format(erogDaEsportare.getDataInizio()) );
//    		mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_FINE, datef.format(erogDaEsportare.getDataFine()) );
//    		mappaDatiPrest.put(Cost.PRESTAZIONE_PERIOD_EROG, String.valueOf(erogDaEsportare.getPeriodoErogazione()) ); 
//    		mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO_MENS, df.format( erogDaEsportare.getImportoMensile()) );
//    		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()) );
//    		mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_EVENTO, "" );
//		}			
//		
//		/* prestazione occasionale */ 
//		if(erogDaEsportare.getNumProtDSU()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_NUMPROT_DSU, erogDaEsportare.getNumProtDSU());
//		if(erogDaEsportare.getAnnoProtDSU()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_ANNO_PROT, Integer.toString(erogDaEsportare.getAnnoProtDSU()));
//		if(erogDaEsportare.getDataDSU()!=null){			
//			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_DSU, datef.format(erogDaEsportare.getDataDSU()));
//		}
//		if(erogDaEsportare.getCodPrestazione()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_CODICE, erogDaEsportare.getCodPrestazione());
//		if(erogDaEsportare.getDenomPrestazione()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_DENOMINAZIONE, erogDaEsportare.getDenomPrestazione());
//			
//		if(erogDaEsportare.getDataEsecuzione()!=null){		
//			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_EROG, datef.format(erogDaEsportare.getDataEsecuzione()));
//		}
//		if(erogDaEsportare.getSpesa()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO, df.format(erogDaEsportare.getSpesa()));				
//		
//		
//		
//		
//		/* dati comuni */	
//		if(erogDaEsportare.getCarattere()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_CARATTERE, erogDaEsportare.getCarattere());
//		if(erogDaEsportare.getPercGestitaEnte()!=null){
//			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_ENTE, df.format(erogDaEsportare.getPercGestitaEnte()));
//		}
//		if(erogDaEsportare.getCompartUtenti()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_UTENTE, df.format(erogDaEsportare.getCompartUtenti()));
//		if(erogDaEsportare.getCompartSsn()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_SSN, df.format(erogDaEsportare.getCompartSsn()));
//						
////		if(erogDaEsportare.getSogliaISEE()!=null)
////		mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE, decf.format(erogDaEsportare.getSogliaISEE()));	
//
//		if(erogDaEsportare.getPrestazioneProtocEnte()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_PROTOC_ENTE, erogDaEsportare.getPrestazioneProtocEnte());
//		
//		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_RICHIESTA, "");	
//		mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE, "");	
//		
//		
//		return mappaDatiPrest;
//	}

	
//inizio SISO-538 Questioni propedeutiche alla export xml Inps
	public static List<EsportazioneDTO> filtraVErogExport(List<EsportazioneDTOView> expView, List<VErogExportHelp> help, AccessTablePsExportSessionBeanRemote psExportSessionBean) {
		
		logger.debug("filtraVErogExport -  NUM. ESPORTAZIONI INGRESSO[" + expView.size() + "]");
		List<EsportazioneDTO> result = new ArrayList<EsportazioneDTO>();

		/* SISO-719 commentate perché risultavano inutilizzate */
//		Map<Long, EsportazioneDTOView> esportazioneDTOview = createEsportazioneDTOview(expView); //  idCsInterventoEsegMast
//		Map<BigDecimal, VErogExportHelp> vErogExportHelpMap = createVErogExportHelpMap(help);
		
		/* SISO-719
		 * 
		 * seqExport rappresenta l'indice di raggruppamento. In un elemento PrestazioniSociali possono confluire più righe
		 * di CS_I_INTERVENTO_ESEG. L'operazione di raggruppamento viene effettuata in questo metodo, e si può agire sui
		 * DTOView per salvare l'informazione.
		 * Il meccanismo è complicato perché non è stata alterata la precedente logica che cicla per gli elementi
		 * vErogExportHelp, che sono un altro raggruppamento ancora.
		 */
		int seqExport = 1; // starting value

		for (VErogExportHelp vErogExportHelp : help) {
			try {
				// se l'esportazione è di tipo EXPORT_MASTER (si esportano tutte le righe)
				if (vErogExportHelp.getTipoExport().equals(DataModelCostanti.VErogExportHelp.TIPO_EXPORT_MASTER)) {
					
					/*Recupero il il max del termine intervallo 
					 * (Nella versione precedente per PIU' EROGAZIONI PERIODICHE CHE PRESENTANO IMPORTO SPESA solo in testata l'elaborazione veniva bypassata) 
					 */
					Date maxDataErogHelp = vErogExportHelp.getMaxDataErogazioneA()!=null ? vErogExportHelp.getMaxDataErogazioneA() : vErogExportHelp.getMaxDataErogazione();
					
					//se la prestazione è occasionale
					if (vErogExportHelp.getFlagCaratterePrestazione().equals(DataModelCostanti.VErogExportHelp.FLAG_CARATTERE_PRESTAZIONE_OCCASIONALE)) {
						// dovrebbe esserci una sola riga associata all'erogazione e la sua data fine deve sempre coincidere con quella della vista vErogExportHelp

						EsportazioneDTOView esportazioneDTOView = getLastEsportazioneDTOview(expView, vErogExportHelp.getId() );
						if (!maxDataErogHelp.equals(esportazioneDTOView.getMaxDataEsecuzione())) {
							throw new Exception("DataEsecuzione erogazione ["
											+ esportazioneDTOView.getMaxDataEsecuzione()
											+ "] non corrispondente con quella della vista ["
											+ vErogExportHelp.getMaxDataErogazione()+ "] id[" 
											+ vErogExportHelp.getId()+ "]");
						} else {
							// creo il bean per l'xml
							EsportazioneDTO esportazioneDTO = creaBeanExportMasterOccasionale(esportazioneDTOView, vErogExportHelp);
							result.add(esportazioneDTO);
							
							// SISO-719 assegno l'indice di raggruppamento al DTOView
							esportazioneDTOView.setSeqExport(seqExport);
						}
					}
					// se la prestazione è periodica
					else if (vErogExportHelp.getFlagCaratterePrestazione().equals(DataModelCostanti.VErogExportHelp.FLAG_CARATTERE_PRESTAZIONE_PERIODICA)) {
						EsportazioneDTOView esportazioneDTOView = getLastEsportazioneDTOview(expView, vErogExportHelp.getId());

					
						// controllo inutile, se tipo TIPO_EXPORT_MASTER, è sempre chiusa!!
						// vErogExportHelp.getFlagErogazioneChiusa().equals(DataModelCostanti.VErogExportHelp.FLAG_EROGAZIONE_CHIUSA)
						// il max data dell'ultima riga di erogazione coincide con il max data del vErogExportHelp
												
						if (esportazioneDTOView.getMaxDataEsecuzione().equals(maxDataErogHelp)) {
							// recupero tutte le righe di erogazione per quel master id
							List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster = 
								psExportSessionBean.findEsportazioniDTOviewPerIdMaster(createErogazioniSearchCriteria(vErogExportHelp.getId().longValue()));
							aggiornaListaEsportazioneDTOview(expView, listaEsportazioneDTOviewPerIdMaster);

							// creo il bean per l'xml
							List<EsportazioneDTO> esportazioneDTO = creaBeanExportMasterPeriodica(listaEsportazioneDTOviewPerIdMaster,vErogExportHelp);
							result.addAll(esportazioneDTO);
							
							// SISO-719 assegno l'indice di raggruppamento a ciascuna DTOView confluita in questo DTO
							for (EsportazioneDTOView viewDTO : listaEsportazioneDTOviewPerIdMaster) {
								assegnaSeqExportToListaEsportazioneDTOView(expView, viewDTO.getInterventoEsegId(),seqExport);
								// viewDTO.setSeqExport(seqExport);
							}
						} else {
							// flaggo tutti i bean listaEsportazioneDTOview con questo master id come non esportabili
							aggiornaListaEsportazioneDTOviewNonEsportabili(expView, vErogExportHelp.getId(), "La data dell'ultima erogazione non cade nel periodo selezionato");
						}
					} else {
						throw new Exception("Flag carattere prestazione ["+ vErogExportHelp.getFlagCaratterePrestazione()+ "] non riconosciuto ID ESEG MAST = "+ vErogExportHelp.getId());
					}
				}

				// sono di tipo MASTER ma non sono ancora chiuse
				else if (vErogExportHelp.getTipoExport().equals(DataModelCostanti.VErogExportHelp.TIPO_EXPORT_TO_CLOSE)) {
					// flaggo tutti i bean listaEsportazioneDTOview con questo master id come non esportabili
					aggiornaListaEsportazioneDTOviewNonEsportabili(expView, vErogExportHelp.getId(), "Erogazione ancora aperta, priva di valore di spesa");
				}

				// se l'esportazione è di tipo EXPORT_RIGHE (si esportano solo le righe nell'intervallo di tempo selezionato)
				else if (vErogExportHelp.getTipoExport().equals(DataModelCostanti.VErogExportHelp.TIPO_EXPORT_RIGHE)) {
					// se la prestazione è periodica solo con le righe presenti nella lista (quindi quelle che sono comprese nell intervallo di date della selezione)
					if (vErogExportHelp.getFlagCaratterePrestazione().equals(DataModelCostanti.VErogExportHelp.FLAG_CARATTERE_PRESTAZIONE_PERIODICA)) {
						// creo il bean per l'xml
						List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster = getListaEsportazioneDTOview(expView, vErogExportHelp.getId().longValue());
						List<EsportazioneDTO> esportazioneDTO = creaBeanExportRighePeriodica(listaEsportazioneDTOviewPerIdMaster,vErogExportHelp);
						result.addAll(esportazioneDTO);
						
						// SISO-719 assegno l'indice di raggruppamento a ciascuna DTOView confluita in questo DTO
						for (EsportazioneDTOView viewDTO : listaEsportazioneDTOviewPerIdMaster) {
							assegnaSeqExportToListaEsportazioneDTOView(expView, viewDTO.getInterventoEsegId(), seqExport);
//							viewDTO.setSeqExport(seqExport);
						}
					}
					// se la prestazione è occasionale o non è valorizzata
					else {
						throw new Exception("Flag carattere prestazione ["+ vErogExportHelp.getFlagCaratterePrestazione()+ "] non riconosciuto ID ESEG MAST = "+ vErogExportHelp.getId());
					}
				}
				
				else {
					throw new Exception("Tipo export ["+ vErogExportHelp.getTipoExport()+ "] non riconosciuto ID ESEG MAST = "+ vErogExportHelp.getId());
				}
				//SISO-1162
				if (vErogExportHelp.getCodPrestazione()== null){
					aggiornaListaEsportazioneDTOviewNonEsportabili(expView, vErogExportHelp.getId(), "Codice Prestazione non valorizzato");
				}
				//FINE //SISO-1162
			}
			catch (Exception e) {
				aggiornaListaEsportazioneDTOviewNonEsportabili(expView, vErogExportHelp.getId(), e.getMessage());
				logger.error("filtraVErogExport"+e.getMessage(), e);
			}

			seqExport++; // incremento l'indice di raggruppamento
		}
		
		/* SISO-721
		 * 
		 * Le erogazioni che risultano con presa in carico a "Non so" non vanno esportate. Per comodità, si scansiona la lista
		 * result in questo punto, andando a verificare il valore del DTO.
		 * 
		 * NB: Su DB, il valore si trova in CS_I_PS.FLAG_IN_CARICO, il "Non so" è 2;
		 *     sul DTO è mappato nella proprietà presaInCarico
		 * NB2: Al momento dell'implementazione, il sistema non può imputare il "Non so", quindi la presente è una modifica
		 *      fatta in anticipo
		 */
		scanForPresaInCaricoNonSo(expView, result);

		logger.debug("filtraVErogExport -  NUM. ESPORTAZIONI USCITA[" + result.size() + "]");
		
		return result;
	}
	
	private static void scanForPresaInCaricoNonSo(List<EsportazioneDTOView> expView, List<EsportazioneDTO> result) {
		List<EsportazioneDTO> preseInCaricoNonSo = new ArrayList<EsportazioneDTO>();

		for (EsportazioneDTO esportazione : result) {
			if (FLAG_IN_CARICO.NON_SO.getCodice() == esportazione.getPresaInCarico().intValue()) {
				// aggiorno la lista per la view
				aggiornaListaEsportazioneDTOviewNonEsportabili(expView, esportazione.getInterventoEsegMastId(),
					"E' necessario specificare se il soggetto beneficiario è in carico, correggere l'erogazione e riprovare");
				
				// aggiungo l'erogazione a quelle da rimuovere
				preseInCaricoNonSo.add(esportazione);
			}
		}
		
		// rimuovo tutte le erogazioni che ho trovato con Presa In Carico "Non so"
		result.removeAll(preseInCaricoNonSo);
	}

	/*
	 * SISO-719 Metodo "quick and dirty" per salvare l'indice di raggruppamento
	 * - sarebbe da implementare in maniera più elegante, ma richiede un
	 * refactoring più profondo della logica.
	 * 
	 * In filtraVErogExport, nei casi in cui più DTOView confluiscono nello
	 * stesso DTO vengono create delle liste di nuovi oggetti DTOView. L'indice
	 * di raggruppamento va salvato sulla lista originale (quella di
	 * EsportaCasellarioBean).
	 */
	private static void assegnaSeqExportToListaEsportazioneDTOView(
		List<EsportazioneDTOView> expView, Long interventoEsegId, int seqExport) {
		
		// cerco il DTOView originale per InterventoEsegId e salvo il seqExport
		for (EsportazioneDTOView esportazioneDTOView : expView) {
			if (esportazioneDTOView.getInterventoEsegId().equals(interventoEsegId)) {
				esportazioneDTOView.setSeqExport(seqExport);
				break; // non serve cercare oltre
			}
		}
	}

	private static void aggiornaListaEsportazioneDTOviewNonEsportabili(List<EsportazioneDTOView> listaEsportazioneDTOview, BigDecimal id, String causaleMancatoInvio) {
		/* SISO-719
		 * 
		 * Correzione: esportazioneDTOView.getInterventoEsegMastId() restituisce
		 * un Long; se questo viene confrontato con un BigDecimal, il risultato
		 * sarà sempre false, anche quando il valore numerico effettivo è lo
		 * stesso.
		 */
		Long idLong = id.longValue();
		
		aggiornaListaEsportazioneDTOviewNonEsportabili(listaEsportazioneDTOview, idLong, causaleMancatoInvio);
	}
	
	private static void aggiornaListaEsportazioneDTOviewNonEsportabili(List<EsportazioneDTOView> listaEsportazioneDTOview, Long id, String causaleMancatoInvio) {
		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
			if (esportazioneDTOView.getInterventoEsegMastId().equals(id)) {
				esportazioneDTOView.setDaInviare(false);// EsportazioneDTOView.EROGAZIONE_DA_NON_INVIARE);
				esportazioneDTOView.setCausaleMancatoInvio(causaleMancatoInvio);
			}
		}

	}

	private static List<EsportazioneDTOView> getListaEsportazioneDTOview(List<EsportazioneDTOView> listaEsportazioneDTOview, long idMast) {
		List<EsportazioneDTOView> result = new ArrayList<EsportazioneDTOView>();

		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
			if (esportazioneDTOView.getInterventoEsegMastId().equals(idMast)) {
				result.add(esportazioneDTOView);
			}
		}

		return result;
	}

	private static List<EsportazioneDTO> creaBeanExportMasterPeriodica(List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster, VErogExportHelp vErogExportHelp) throws Exception {
	
		EsportazioneDTOView primaEsportazione = listaEsportazioneDTOviewPerIdMaster.get(0);
		
		// DataInizio=> V_EROG_EXPORT_HELP.row .MIN_DATA_EROGAZIONE
		Date dataInizio = vErogExportHelp.getMinDataErogazione();
		
		// DataFine =Se V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE_A <> NULL
		// ALLORA V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE_A ALTRIMENTI
		// V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE
		Date dataFine = vErogExportHelp.getMaxDataErogazioneA() != null ? vErogExportHelp.getMaxDataErogazioneA() : vErogExportHelp.getMaxDataErogazione();
				
		// PeriodoErogazione = Differenza in mesi fra le due date sopra . 2FB-27 GEN = 1MESE
		int periodoErogazioneTotale = differenzaMesi(dataInizio,dataFine);
		
		HashMap<Integer,Date[]> mappaPeriodi = ripartisciAnnuali(dataInizio,dataFine);
		
		BigDecimal spesaTotale = listaEsportazioneDTOviewPerIdMaster.get(0).getSpesaTestata();
		BigDecimal importoMensile = spesaTotale.divide(new BigDecimal(periodoErogazioneTotale), 2, RoundingMode.HALF_UP);
		
		//SISO-806
		Long idUnitaMisura = listaEsportazioneDTOviewPerIdMaster.get(0).getUnitaMisura();
		String oreMinutiServizioMensile = "";
		if(idUnitaMisura == DataModelCostanti.CsTbUnitaMisura.ID_ORE || idUnitaMisura==DataModelCostanti.CsTbUnitaMisura.ID_ORE_MINUTI){
			BigDecimal valQuotaPeriodica = listaEsportazioneDTOviewPerIdMaster.get(0).getValQuota();
			BigDecimal oreMinutiMensili = valQuotaPeriodica.divide(new BigDecimal(periodoErogazioneTotale), 2, RoundingMode.HALF_UP);

			int ore = oreMinutiMensili.intValue();
			
			BigDecimal minutiValQuota = oreMinutiMensili.remainder(BigDecimal.ONE);
			BigDecimal convMinutiValQuota = (minutiValQuota.multiply(new BigDecimal(60))).setScale(0, BigDecimal.ROUND_HALF_UP);
			int minuti = convMinutiValQuota.intValue();
			
			oreMinutiServizioMensile = String.valueOf(ore) + " : " +  String.valueOf(minuti)  ;
		}
		//FINE SISO-806 
		
		BigDecimal percGestitaEnte = getBdNotNull(vErogExportHelp.getValoreGestitaEnteCalc());
		BigDecimal compartUtenti = getBdNotNull(vErogExportHelp.getCompartUtenti());
		BigDecimal compartSsn = getBdNotNull(vErogExportHelp.getCompartSsn());
		
		List<EsportazioneDTO> lstOut = new ArrayList<EsportazioneDTO>();
		List<Integer> sortedYears=new ArrayList<Integer>(mappaPeriodi.keySet());
		Collections.sort(sortedYears);
		for(int anno : sortedYears){
			
			EsportazioneDTO esportazioneDTO = fillDatiComuniExport(primaEsportazione);
			
			Date[] intervallo = mappaPeriodi.get(anno);
			Date dtInizioInt = intervallo[0];
			Date dtFineInt = intervallo[1];

			esportazioneDTO.setDataInizio(dtInizioInt);
			esportazioneDTO.setDataFine(dtFineInt);
			// PeriodoErogazione = Differenza in mesi fra le due date sopra . 2FB-27 GEN = 1MESE
			int intervalloErogazione = differenzaMesi(dtInizioInt, dtFineInt);
			esportazioneDTO.setPeriodoErogazione(intervalloErogazione);
				
			esportazioneDTO.setImportoMensile(importoMensile);
	
			esportazioneDTO.setPercGestitaEnte((percGestitaEnte.multiply(new BigDecimal(intervalloErogazione))).divide(new BigDecimal(periodoErogazioneTotale), 2, RoundingMode.HALF_UP));
			esportazioneDTO.setCompartUtenti((compartUtenti.multiply(new BigDecimal(intervalloErogazione))).divide(new BigDecimal(periodoErogazioneTotale), 2, RoundingMode.HALF_UP)); //Calcolo proporzionalmente
			esportazioneDTO.setCompartSsn((compartSsn.multiply(new BigDecimal(intervalloErogazione))).divide(new BigDecimal(periodoErogazioneTotale), 2, RoundingMode.HALF_UP));
	
			//SISO-806
			esportazioneDTO.setOreServizioMensile(oreMinutiServizioMensile);
			
			//FINE SISO-806
			
			lstOut.add(esportazioneDTO);
		}

		return lstOut;
	}


	private static List<EsportazioneDTO> creaBeanExportRighePeriodica(List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster, VErogExportHelp vErogExportHelp) throws Exception {
		    
		EsportazioneDTOView primaEsportazione = listaEsportazioneDTOviewPerIdMaster.get(0);
		
		logger.debug("creaBeanExportRighePeriodica numero protocollo = "+ primaEsportazione.getPrestazioneProtocEnte());
		
		// DataInizio = LA MIN DATA_ESECUZIONE FRA QUELLE SELEZIONATE
		Date dataInizio = getMinDataEsecuzioneRighePeriodica(listaEsportazioneDTOviewPerIdMaster);
		
		// DataFine = LA MAX DATA_ESECUZIONE_A FRA QUELLE SELEZIONATE. ( SE NULL SI PRENDE LA MAX DI DATA_ESECUZIONE )
		Date dataFine = getMaxDataEsecuzioneRighePeriodica(listaEsportazioneDTOviewPerIdMaster);
		
		HashMap<Integer,Date[]> mappaPeriodi = ripartisciAnnuali(dataInizio,dataFine);
		HashMap<Integer,List<EsportazioneSpesaDTO>> mappaSpese = ripartisciSpeseAnnuali(listaEsportazioneDTOviewPerIdMaster);
		
		List<EsportazioneDTO> lstOut = new ArrayList<EsportazioneDTO>();
		
		List<Integer> sortedYears=new ArrayList<Integer>(mappaPeriodi.keySet());
		Collections.sort(sortedYears);
		for(int anno : sortedYears){
			EsportazioneDTO esportazioneDTO = fillDatiComuniExport(primaEsportazione);
			Date[] intervallo = mappaPeriodi.get(anno);
			Date dtInizioInt = intervallo[0];
			Date dtFineInt = intervallo[1];
			
			esportazioneDTO.setDataInizio(dtInizioInt);
			esportazioneDTO.setDataFine(dtFineInt);
			// PeriodoErogazione = Differenza in mesi fra le due date sopra . 2FB-27 GEN = 1MESE
			int intervalloErogazione = differenzaMesi(dtInizioInt, dtFineInt);
			esportazioneDTO.setPeriodoErogazione(intervalloErogazione);
			
			List<EsportazioneSpesaDTO> lstSpeseAnno = mappaSpese.get(anno);
			
			// ImportoMensile = Si divide la somma di DATASET3.SPESA delle righe PER IL NUMERO DI MESI SOPRA
			BigDecimal spesaTotale = new BigDecimal(0);
			BigDecimal percGestitaEnte = new BigDecimal(0);
			BigDecimal compartUtenti = new BigDecimal(0);
			BigDecimal compartSsn = new BigDecimal(0);
			
			if(lstSpeseAnno!=null){
				for (EsportazioneSpesaDTO e : lstSpeseAnno){
					spesaTotale = spesaTotale.add(getBdNotNull(e.getSpesa()));
					percGestitaEnte = percGestitaEnte.add(getBdNotNull(e.getPercGestitaEnte()));
					compartUtenti = compartUtenti.add(getBdNotNull(e.getCompartUtenti()));
					compartSsn = compartSsn.add(getBdNotNull(e.getCompartSsn()));
				}
			}else
				logger.warn("creaBeanExportRighePeriodica: protocollo["+primaEsportazione.getPrestazioneProtocEnte()+"] nessuna spesa trovata per l'anno ["+anno+"]");
			
			BigDecimal importoMensile = spesaTotale.divide(new BigDecimal(intervalloErogazione), 2, RoundingMode.HALF_UP);
				
			esportazioneDTO.setImportoMensile(importoMensile);
			esportazioneDTO.setPercGestitaEnte(percGestitaEnte);
			esportazioneDTO.setCompartUtenti(compartUtenti);
			esportazioneDTO.setCompartSsn(compartSsn);

			//SISO-806
			
			Long idUnitaMisura = listaEsportazioneDTOviewPerIdMaster.get(0).getUnitaMisura();
			BigDecimal valQuotaPeriodica = new BigDecimal(0);
			
			if(idUnitaMisura == DataModelCostanti.CsTbUnitaMisura.ID_ORE || idUnitaMisura==DataModelCostanti.CsTbUnitaMisura.ID_ORE_MINUTI){
				
				for(EsportazioneDTOView e : listaEsportazioneDTOviewPerIdMaster){
					
					valQuotaPeriodica = valQuotaPeriodica.add(e.getValQuota());
				}
			
				BigDecimal oreMinutiMensili = valQuotaPeriodica.divide(new BigDecimal(intervalloErogazione), 2, RoundingMode.HALF_UP);
	
				int ore = oreMinutiMensili.intValue();
				
				BigDecimal minutiValQuota = oreMinutiMensili.remainder(BigDecimal.ONE);
				BigDecimal convMinutiValQuota = (minutiValQuota.multiply(new BigDecimal(60))).setScale(0, BigDecimal.ROUND_HALF_UP);
				int minuti = convMinutiValQuota.intValue();
				
				esportazioneDTO.setOreServizioMensile(StringUtils.leftPad(String.valueOf(ore), 2, "0")  + ":" + StringUtils.leftPad(String.valueOf(minuti),  2, "0")  );
				//FINE SISO-806
			}
			
			lstOut.add(esportazioneDTO);
		}
		
		return lstOut;
	}
	
	private static EsportazioneDTO fillDatiComuniExport(EsportazioneDTOView primaEsportazione){
		
		EsportazioneDTO esportazioneDTO = new EsportazioneDTO();
		// <Carattere>1</Carattere>
		esportazioneDTO.setCarattere(primaEsportazione.getCarattere());
		// <NumeroProtocolloDSU>INPS-ISEE-2015-01480722D-00</NumeroProtocolloDSU>
		esportazioneDTO.setNumProtDSU(primaEsportazione.getNumProtDSU());
		// <AnnoProtocollo>2015</AnnoProtocollo>
		esportazioneDTO.setAnnoProtDSU(primaEsportazione.getAnnoProtDSU());
		// <DataDSU>2015-09-23</DataDSU>
		esportazioneDTO.setDataDSU(primaEsportazione.getDataDSU());
		// <Codice>A3.04</Codice>
		esportazioneDTO.setCodPrestazione(primaEsportazione.getCodPrestazione());
		// <Denominazione>Edilizia residenziale pubblica</Denominazione>
		esportazioneDTO.setDenomPrestazione(primaEsportazione.getDenomPrestazione());
		// <ProtocolloEnte>2014-789</ProtocolloEnte>
		esportazioneDTO.setProtDomPrest(primaEsportazione.getProtDomPrest());
		// <DataErogazione> V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE</DataErogazione>
		
		esportazioneDTO.setPresenzaProvaMezzi(primaEsportazione.getPresenzaProvaMezzi());
		esportazioneDTO.setPresaInCarico(primaEsportazione.getPresaInCarico());
		esportazioneDTO.setCategoriaSocialeId(primaEsportazione.getCategoriaSocialeId());

		// ** mod. SISO-886 **//
		esportazioneDTO.setIsSinaCollegato(primaEsportazione.getIsSinaCollegato());
		esportazioneDTO.setIsSinaFlagValutaDopo(primaEsportazione.getIsSinaFlagValutaDopo());

		// <QuotaRichiesta>Dato non presente , riportare TAG VUOTO</QuotaRichiesta>
		// <SogliaISEE>valore non presente valorizza TAG VUOTO</SogliaISEE>
		fillBeneficiario(esportazioneDTO, primaEsportazione);
		// fillForeignKey(esportazioneDTO, primaEsportazione);
		
		return esportazioneDTO;
	}
	
	private static void fillBeneficiario(EsportazioneDTO esportazioneDTO, EsportazioneDTOView esportazioneDTOView) {
		esportazioneDTO.setPrestazioneProtocEnte(esportazioneDTOView.getPrestazioneProtocEnte());
		esportazioneDTO.setInterventoEsegMastId(esportazioneDTOView.getInterventoEsegMastId());

		esportazioneDTO.setSoggettoCodiceFiscale(esportazioneDTOView.getSoggettoCodiceFiscale());
		esportazioneDTO.setBenefRegione(esportazioneDTOView.getBenefRegione());
		esportazioneDTO.setBenefComune(esportazioneDTOView.getBenefComune());
		esportazioneDTO.setBenefNazione(esportazioneDTOView.getBenefNazione());
		esportazioneDTO.setSoggettoNome(esportazioneDTOView.getSoggettoNome());
		esportazioneDTO.setSoggettoCognome(esportazioneDTOView.getSoggettoCognome());
		esportazioneDTO.setBenefAnnoNascita(esportazioneDTOView.getBenefAnnoNascita());
		esportazioneDTO.setBenefLuogoNascita(esportazioneDTOView.getBenefLuogoNascita());
		esportazioneDTO.setBenefSesso(esportazioneDTOView.getBenefSesso());
		esportazioneDTO.setBenefCittadinanza(esportazioneDTOView.getBenefCittadinanza());
		
		//SISO-962 Inizio
		esportazioneDTO.setBenefSecCittadinanza(esportazioneDTOView.getBenefSecCittadinanza());
		//SISO-962 Fine

	}

	private static Date getMaxDataEsecuzioneRighePeriodica(
			List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster) {
		Date result = null;
		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOviewPerIdMaster) {
			Date maxDataEsecuzione = esportazioneDTOView.getMaxDataEsecuzione();
					//esportazioneDTOView.getDataEsecuzioneA() == null ? esportazioneDTOView.getDataEsecuzione() : esportazioneDTOView.getDataEsecuzioneA();
			if (result == null || result.before(maxDataEsecuzione)) {
				result = maxDataEsecuzione;
			}
		}
		return result;
	}

	private static Date getMinDataEsecuzioneRighePeriodica(List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster) {
		Date result = null;
		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOviewPerIdMaster) {
			if (result == null || result.after(esportazioneDTOView.getDataEsecuzione())) {
				result = esportazioneDTOView.getDataEsecuzione();
			}
		}

		return result;
	}
	
	private static HashMap<Integer, Date[]> ripartisciAnnuali(Date dataInizio, Date dataFine){
		HashMap<Integer, Date[]> mappaAnnoPeriodo = new HashMap<Integer, Date[]>();
		
		try{
		Calendar dataInizioCalendar = new GregorianCalendar();
		dataInizioCalendar.setTime(dataInizio);
		Calendar dataFineCalendar = new GregorianCalendar();
		dataFineCalendar.setTime(dataFine);
		
		int annoInizio = dataInizioCalendar.get(Calendar.YEAR);
	    int annoFine = dataFineCalendar.get(Calendar.YEAR);
		String ini = "01/01/";
		String fin = "31/12/";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		if(annoInizio==annoFine){
			Date[] intIni = {dataInizio, dataFine};
			mappaAnnoPeriodo.put(annoInizio,intIni);
		}else{	
			
			Date[] intIni = {dataInizio, sdf.parse(fin+annoInizio)};
			Date[] intFin = {sdf.parse(ini+annoFine), dataFine};
			
			mappaAnnoPeriodo.put(annoInizio,intIni);
			
			for(int i = annoInizio+1; i< annoFine; i++){
				Date[] inter = {sdf.parse(ini+i), sdf.parse(fin+i)};
				mappaAnnoPeriodo.put(i,inter);
			}
			
			mappaAnnoPeriodo.put(annoFine, intFin);
		}
		}catch(Exception e){
			logger.error(e);
			Date[] intIni = {dataInizio, dataFine};
			mappaAnnoPeriodo.put(9999, intIni);
		}
		return mappaAnnoPeriodo;
	}
	
	private static HashMap<Integer, List<EsportazioneSpesaDTO>> ripartisciSpeseAnnuali(List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster) throws Exception{
		HashMap<Integer, List<EsportazioneSpesaDTO>> mappaAnnoSpese = new HashMap<Integer, List<EsportazioneSpesaDTO>>();
	
		for(EsportazioneDTOView e : listaEsportazioneDTOviewPerIdMaster){
			HashMap<Integer, Date[]> mappaAP = ripartisciAnnuali(e.getDataEsecuzione(), e.getMaxDataEsecuzione());
			int periodoErogazione = differenzaMesi(e.getDataEsecuzione(), e.getMaxDataEsecuzione());
			
			BigDecimal spesa = new BigDecimal(0);
			BigDecimal percGestitaEnte = new BigDecimal(0);
			BigDecimal compartUtenti = new BigDecimal(0);
			BigDecimal compartSsn = new BigDecimal(0);
			
			if (e.getValoreGestitaEnte() != null) {
				// percGestitaEnte += ValoreGestitaEnte
				percGestitaEnte = e.getValoreGestitaEnte();
			} else if (e.getPercGestitaEnte() != null) {
				// compartUtenti += SPESA * PERC_GESTITA_ENTE / 100
				percGestitaEnte = e.getSpesa().multiply(e.getPercGestitaEnte()).divide(new BigDecimal(100));
			} else { 
				percGestitaEnte = e.getSpesa();
			}

			compartUtenti = getBdNotNull(e.getCompartUtenti());
			compartSsn = getBdNotNull(e.getCompartSsn());
			spesa = getBdNotNull(e.getSpesa());
			
			Iterator<Integer> it = mappaAP.keySet().iterator();
			while(it.hasNext()){
				
				EsportazioneSpesaDTO  esportazioneDTO = new EsportazioneSpesaDTO();
				
				int anno = ((Integer)it.next()).intValue();
			    Date[] intervallo = mappaAP.get(anno);
			    
			    Date dtInizioInt = intervallo[0];
			    Date dtFineInt = intervallo[1];
			    
				int intervalloErogazione = differenzaMesi(dtInizioInt, dtFineInt);
					
				esportazioneDTO.setSpesaDettaglio((spesa.multiply(new BigDecimal(intervalloErogazione))).divide(new BigDecimal(periodoErogazione), 2, RoundingMode.HALF_UP));
				esportazioneDTO.setPercGestitaEnte((percGestitaEnte.multiply(new BigDecimal(intervalloErogazione))).divide(new BigDecimal(periodoErogazione), 2, RoundingMode.HALF_UP));
				esportazioneDTO.setCompartUtenti((compartUtenti.multiply(new BigDecimal(intervalloErogazione))).divide(new BigDecimal(periodoErogazione), 2, RoundingMode.HALF_UP)); //Calcolo proporzionalmente
				esportazioneDTO.setCompartSsn((compartSsn.multiply(new BigDecimal(intervalloErogazione))).divide(new BigDecimal(periodoErogazione), 2, RoundingMode.HALF_UP));
		
				List<EsportazioneSpesaDTO> lstSpeseAnno = mappaAnnoSpese.get(anno);
				if(lstSpeseAnno==null) lstSpeseAnno = new ArrayList<EsportazioneSpesaDTO>();
				
				lstSpeseAnno.add(esportazioneDTO);
				
				mappaAnnoSpese.put(anno, lstSpeseAnno);
				
			}
		}
		
		return mappaAnnoSpese;
	}

	private static int differenzaMesi(Date dataInizio, Date dataFine) throws Exception {
		int diffMesi = 0;

		Calendar dataInizioCalendar = new GregorianCalendar();
		dataInizioCalendar.setTime(dataInizio);
		Calendar dataFineCalendar = new GregorianCalendar();
		dataFineCalendar.setTime(dataFine);

		int diffAnni = dataFineCalendar.get(Calendar.YEAR) - dataInizioCalendar.get(Calendar.YEAR);
		diffMesi = diffAnni * 12 + dataFineCalendar.get(Calendar.MONTH) - dataInizioCalendar.get(Calendar.MONTH);
		
		if(diffMesi<0)
			throw new Exception("Impossibile calcolare l'intervallo di erogazione");
		
		return diffMesi + 1;
	}

	private static EsportazioneDTO creaBeanExportMasterOccasionale( EsportazioneDTOView esportazioneDTOView, VErogExportHelp vErogExportHelp) {
		EsportazioneDTO esportazioneDTO = fillDatiComuniExport(esportazioneDTOView);

		// <DataErogazione> V_EROG_EXPORT_HELP.row
		// .MAX_DATA_EROGAZIONE</DataErogazione>
		esportazioneDTO.setDataEsecuzione(vErogExportHelp.getMaxDataErogazione());
		// <Importo>> V_EROG_EXPORT_HELP.row .SPESA</Importo>

		esportazioneDTO.setSpesaTestata(vErogExportHelp.getSpesa());
		//SISO-900
		esportazioneDTO.setSpesaDettaglio(vErogExportHelp.getSpesaDettaglio());
		
		// <QuotaEnte> V_EROG_EXPORT_HELP.row .VALORE_GESTITA_ENTE_CALC</QuotaEnte>
		esportazioneDTO.setPercGestitaEnte(vErogExportHelp.getValoreGestitaEnteCalc());
		// <QuotaUtente> V_EROG_EXPORT_HELP.row .COMPART_UTENTI</QuotaUtente>
		esportazioneDTO.setCompartUtenti(vErogExportHelp.getCompartUtenti());
		// <QuotaSSN> V_EROG_EXPORT_HELP.row .COMPART_SSN</QuotaSSN>
		esportazioneDTO.setCompartSsn(vErogExportHelp.getCompartSsn());

		return esportazioneDTO;
	}

//	private static void fillForeignKey(EsportazioneDTO esportazioneDTO, EsportazioneDTOView esportazioneDTOView) {
//	 esportazioneDTO.setInterventoEsegId (esportazioneDTOView.getInterventoEsegId() );
//	 esportazioneDTO.setInterventoEsegMastId (esportazioneDTOView.getInterventoEsegMastId() );
//	 esportazioneDTO.setInterventoId (esportazioneDTOView.getInterventoId() );
//	
//}



	private static void aggiornaListaEsportazioneDTOview(List<EsportazioneDTOView>  listaEsportazioneDTOview, List<EsportazioneDTOView> daAggiungereList) {
		for (EsportazioneDTOView daAggiungere : daAggiungereList) {
			if (!isPresente(listaEsportazioneDTOview, daAggiungere)) {
				listaEsportazioneDTOview.add(daAggiungere);
			}
		}

	}
	
	private static boolean isPresente( List<EsportazioneDTOView> listaEsportazioneDTOview, EsportazioneDTOView daAggiungere) {
		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
			if (esportazioneDTOView.getInterventoEsegId().equals(daAggiungere.getInterventoEsegId())) {
				return true;
			}
		}
		return false;
	}

	private static ErogazioniSearchCriteria createErogazioniSearchCriteria(long idMaster) {
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		try {
			CsUiCompBaseBean.fillEnte(bDto);
		} catch (Exception e) {
			// in test fuori dal contesto del server questo metodo va in errore
		}
		bDto.setIdMaster(idMaster);
		return bDto;
	}

	private static EsportazioneDTOView getLastEsportazioneDTOview(List<EsportazioneDTOView> listaEsportazioneDTOview, BigDecimal masterId) {
		EsportazioneDTOView result = null;

		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
			if (esportazioneDTOView.getInterventoEsegMastId().equals(masterId.longValue())) {
				if (result == null || result.getMaxDataEsecuzione().before(esportazioneDTOView.getMaxDataEsecuzione())) {
					result = esportazioneDTOView;
				}
			}
		}

		return result;
	}

	public static String avvisoErogazioniNonEsportate(
			List<EsportazioneDTOView> listaErogazioniMasterChiusuraInPeriodo,
			List<EsportazioneDTOView> listaEsportazioneDTOview) {
		
		String result = "";
		
		//controllo che per ogni master della listaErogazioniMasterChiusuraInPeriodo ci sia almeno una erogazione nella lista di quelle 
		//potenzialmente esportabili; se non c'è significa che ci sono erogazioni che sono state saltate e quindi stampo un messaggio 
		//di avvertimento
		Set<Long> masterIds = getMasterIds(listaErogazioniMasterChiusuraInPeriodo);

//		logger.debug("Lista master id erogazioni ChiusuraInPeriod");
//		printMasteIds(listaErogazioniMasterChiusuraInPeriodo);
//		logger.debug("Lista master id erogazioni da esportare");
//		printMasteIds(listaEsportazioneDTOview);
		Set<Long> masterIdsNonPresenti = getMasterIdsNonPresenti(masterIds, listaEsportazioneDTOview);

		Date minDataEsecuzione = null;
		Date maxDataEsecuzione = null;

		for (EsportazioneDTOView erog : listaErogazioniMasterChiusuraInPeriodo) {
			if (masterIdsNonPresenti.contains(erog.getInterventoEsegMastId())) {

				if (minDataEsecuzione==null || minDataEsecuzione.after(erog.getDataEsecuzione())) {
					minDataEsecuzione = erog.getDataEsecuzione();
				}
				
				Date maxTemp = erog.getDataEsecuzioneA()!=null?erog.getDataEsecuzioneA():erog.getDataEsecuzione();

				if (maxDataEsecuzione==null || maxDataEsecuzione.before(maxTemp)) {
					maxDataEsecuzione = maxTemp;
				}
			}
		}
		
		if (masterIdsNonPresenti.size()>0) {
			/*result = "Attenzione, ci sono erogazioni non esportate per il periodo che va da " + 
					 sdf.format(minDataEsecuzione) + " a " +  sdf.format(maxDataEsecuzione) ; */
		}

		return result;

	}

	private static Set<Long> getMasterIds( List<EsportazioneDTOView> listaErogazioniMasterChiusuraInPeriodo) {
		Set<Long> masterIds = new HashSet<Long>();

		for (EsportazioneDTOView esportazioneDTOView : listaErogazioniMasterChiusuraInPeriodo) {
			masterIds.add(esportazioneDTOView.getInterventoEsegMastId());
		}

		return masterIds;
	}

	private static Set<Long> getMasterIdsNonPresenti(Set<Long> masterIds, List<EsportazioneDTOView> listaEsportazioneDTOview) {
		Set<Long> result = new HashSet<Long>();
		result.addAll(masterIds);

		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
			if ( result.contains( esportazioneDTOView.getInterventoEsegMastId() ) ) {
				logger.debug("Remove " + esportazioneDTOView.getInterventoEsegMastId());
				result.remove(esportazioneDTOView.getInterventoEsegMastId());
			}
		}

		return result;
	}

	// INIZIO SISO-586 Erogazione pronte per export ma non esportate effettivamente
	static BigDecimal getBdNotNull(BigDecimal bd) {
		return bd == null ? BigDecimal.ZERO : bd;
	}
	// FINE SISO-586 Erogazione pronte per export ma non esportate effettivamente

	// fine SISO-538

	/* SISO-719 Costruzione lista delle Testate Erogazione da visualizzare */
	public static List<EsportazioneTestataDTO> costruisciListaTestateErogazione(
			List<EsportazioneDTOView> daEsportareList,
			List<EsportazioneDTOView> giaInviateList,
			List<EsportazioneDTO> daEsportareDTOList) {
		
		List<EsportazioneTestataDTO> testate = new ArrayList<EsportazioneTestataDTO>();	// oggetto di ritorno
				
		/* Per ciascuna erogazione in daEsportareList, cerco la testata di appartenenza. Se la trovo, aggiungo
		 * l'erogazione alla lista dei dettagli per quella testata, altrimenti la uso per creare una nuova testata. */
		for (EsportazioneDTOView erogazioneDaEsportare : daEsportareList) {

			for (EsportazioneDTO erogazione : daEsportareDTOList) {

				if (erogazioneDaEsportare.getInterventoEsegMastId().equals(erogazione.getInterventoEsegMastId())) {
					erogazioneDaEsportare.setIsSinaCollegato(erogazione.getIsSinaCollegato());
					// ** mod. SISO-886 **//
					erogazioneDaEsportare.setIsSinaFlagValutaDopo(erogazione.getIsSinaFlagValutaDopo());
				}
			}

			aggiornaTestate(testate, erogazioneDaEsportare);
		}

		/*
		 * Per ciascuna erogazione in giaInviateList: -flaggo opportunamente le
		 * proprietà dell'oggetto DTOView, contrassegnando l'erogazione come
		 * esportata, non daInviare e impostando come messaggio di mancato invio
		 * "Erogazione esportata in data GG/MM/YYYY" -cerco la testata di
		 * appartenenza: se la trovo, aggiungo l'erogazione alla lista dei
		 * dettagli per quella testata, altrimenti la uso per creare una nuova
		 * testata.
		 */
		for (EsportazioneDTOView erogazioneGiaInviata : giaInviateList) {
			String causale = String.format("Erogazione esportata in data %s", erogazioneGiaInviata.dataEsportazioneFormatShort());
			
			erogazioneGiaInviata.setEsportata(true);
			erogazioneGiaInviata.setDaInviare(false);
			erogazioneGiaInviata.setCausaleMancatoInvio(causale);

			aggiornaTestate(testate, erogazioneGiaInviata);
		}

		// prima di restituire la lista, ne faccio l'ordinamento naturale
		Collections.sort(testate);

		return testate;
	}

	//SISO-784 estrazione mast id da lista
	public static List<Long> extractMastIds(List<? extends EsportazioneDTO> lista){
		List<Long> listaIds = new ArrayList<Long>();
		for(Object dto : lista){
			EsportazioneDTO edto = (EsportazioneDTO)dto;
			Long idMast = edto.getInterventoEsegMastId()!=null ? edto.getInterventoEsegMastId().longValue() : null;
			if(idMast!=null && !listaIds.contains(idMast.longValue())){
				listaIds.add(edto.getInterventoEsegMastId());
			}
		}
		return listaIds;
	}

	//SISO-784 assegnazione Sina collegati
	public static void caricaDatiSina(EsportazioneDTO exp, CsDSinaLIGHT sina){

			exp.setIsSinaCollegato(true);
			// ** mod. SISO-886 **//
			exp.setIsSinaFlagValutaDopo(sina.getFlagValutaDopo());

			exp.setMobilita(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.MOBILITA));
			exp.setAttivitaVitaQuotidiana(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.ATTIVITA_VITA_QUOTIDIANA));
			exp.setDisturbiAreaCognitiva(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.DISTURBI_AREA_COGNITIVA));
			exp.setDisturbiComportamentali(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.DISTURBI_COMPORTAMENTALI));
			exp.setNecessitaCureSanitarie(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.NECESSITA_CURE_SANITARIE));
			exp.setAreaReddituale(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.AREA_REDDITUALE));
			exp.setAreaSupporto(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.AREA_SUPPORTO));
			exp.setFonteDerivazioneValutazione(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.FONTE_DERIVAZIONE_VALUTAZIONE));
			exp.setStrumentoValutazione(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.STRUMENTO_VALUTAZIONE));
			//exp.setInvCiv(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.INVALIDITA_CIVILE));
			exp.setFonteDerivazioneInvalidita(EsportaCasellarioUtils.getValutazione(sina.getCsDSinaEseg(), DataModelCostanti.TipoSinaDomanda.FONTE_DERIVAZIONE_INVALIDITA));
			
			exp.getInvCiv().clear();
			for(CsDSinaEsegLIGHT eseg : sina.getCsDSinaEseg()){
				if(eseg.getCsTbSinaDomanda().getId() == Long.parseLong("10")){
					exp.getInvCiv().add(eseg.getCsTbSinaRisposta().getValore().toString());
				}
			}
			
			exp.getCodiciPrestazione().clear();
			for (ArTbPrestazioniInps inps : sina.getArTbPrestazioniInps()) {
				exp.getCodiciPrestazione().add(inps.getCodice());
		}
	}

	// quick and dirty
	private static EsportazioneTestataDTO cercaTestata(List<EsportazioneTestataDTO> testate, Long interventoEsegMastId) {
		for (EsportazioneTestataDTO testata : testate) {
			if (testata.getInterventoEsegMastId().equals(interventoEsegMastId)) {
				return testata;
			}
		}

		return null; // se non trovo il dettaglio, restituisco null
	}

	// quick and dirty
	private static void aggiornaTestate(List<EsportazioneTestataDTO> testate, EsportazioneDTOView erogazioneDaAggiungere) {
		EsportazioneTestataDTO testata = cercaTestata(testate, erogazioneDaAggiungere.getInterventoEsegMastId());
		
		if (testata == null) {
			testate.add(new EsportazioneTestataDTO(erogazioneDaAggiungere));
		} else {
			testata.aggiungiDettaglioAllaTestata(erogazioneDaAggiungere);
		}
	}
	
	public void controllaErogPeriodiche(List<EsportazioneDTO> daEsportare, List<EsportazioneDTOView> giaInviate){
		
		for(EsportazioneDTO de : daEsportare){
			
		}

	}

	// quick and dirty
	private static Long getValutazione(List<CsDSinaEsegLIGHT> sinaEsegList, Long domanda){
		Long valutazione = null;
		for (CsDSinaEsegLIGHT eseg : sinaEsegList) {
			if (eseg.getCsTbSinaDomanda().getId() == domanda.longValue()) {
				valutazione = eseg.getCsTbSinaRisposta().getValore().longValue();
			}
		}

		return valutazione;
	}


}
