package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.csa.ejb.dto.EsportazioneTestataDTO;
import it.webred.cs.data.DataModelCostanti;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		int seqExport = 1;	// starting value
		
		for (VErogExportHelp vErogExportHelp : help) {
			try {
				// se l'esportazione è di tipo EXPORT_MASTER (si esportano tutte le righe)
				if (vErogExportHelp.getTipoExport().equals(DataModelCostanti.VErogExportHelp.TIPO_EXPORT_MASTER)) {
		
					//se la prestazione è occasionale
					if (vErogExportHelp.getFlagCaratterePrestazione().equals(DataModelCostanti.VErogExportHelp.FLAG_CARATTERE_PRESTAZIONE_OCCASIONALE)) {
						// dovrebbe esserci una sola riga associata all'erogazione e la sua data fine deve sempre coincidere con quella della vista vErogExportHelp

						EsportazioneDTOView esportazioneDTOView = getLastEsportazioneDTOview(expView, vErogExportHelp.getId() );
						if (!vErogExportHelp.getMaxDataErogazione().equals(esportazioneDTOView.getMaxDataEsecuzione())) {
							throw new Exception("DataEsecuzione erogazione ["
								+ esportazioneDTOView.getMaxDataEsecuzione()+ "] non corrispondente con quella della vista ["
								+ vErogExportHelp.getMaxDataErogazione() +"] id["
								+ vErogExportHelp.getId() + "]");
						} else {  
							//creo il bean per l'xml 
							EsportazioneDTO esportazioneDTO = creaBeanExportMasterOccasionale(esportazioneDTOView, vErogExportHelp); 
							result.add(esportazioneDTO);
							
							// SISO-719 assegno l'indice di raggruppamento al DTOView
							esportazioneDTOView.setSeqExport(seqExport);
						}
					}
					//se la prestazione è periodica
					else if (vErogExportHelp.getFlagCaratterePrestazione().equals(DataModelCostanti.VErogExportHelp.FLAG_CARATTERE_PRESTAZIONE_PERIODICA)) {
						EsportazioneDTOView esportazioneDTOView = getLastEsportazioneDTOview(expView, vErogExportHelp.getId() );

					
						// controllo inutile, se tipo TIPO_EXPORT_MASTER, è sempre chiusa!!
						// vErogExportHelp.getFlagErogazioneChiusa().equals(DataModelCostanti.VErogExportHelp.FLAG_EROGAZIONE_CHIUSA)
						// il max data dell'ultima riga di erogazione coincide con il max data del vErogExportHelp
						if (esportazioneDTOView.getMaxDataEsecuzione().equals(vErogExportHelp.getMaxDataErogazione())) {
							// recupero tutte le righe di erogazione per quel master id
							List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster = 
								psExportSessionBean.findEsportazioniDTOviewPerIdMaster(createErogazioniSearchCriteria(vErogExportHelp.getId().longValue()));
							aggiornaListaEsportazioneDTOview(expView, listaEsportazioneDTOviewPerIdMaster);

							// creo il bean per l'xml
							EsportazioneDTO esportazioneDTO = creaBeanExportMasterPeriodica(listaEsportazioneDTOviewPerIdMaster, vErogExportHelp);
							result.add(esportazioneDTO);
							
							// SISO-719 assegno l'indice di raggruppamento a ciascuna DTOView confluita in questo DTO
							for (EsportazioneDTOView viewDTO : listaEsportazioneDTOviewPerIdMaster) {
								assegnaSeqExportToListaEsportazioneDTOView(expView, viewDTO.getInterventoEsegId(), seqExport);
//								viewDTO.setSeqExport(seqExport);
							}
						} else {
							//flaggo tutti i bean listaEsportazioneDTOview con questo master id come non esportabili
							aggiornaListaEsportazioneDTOviewNonEsportabili(expView, vErogExportHelp.getId(), "La data dell'ultima erogazione non cade nel periodo selezionato");
						}
					} else {
						throw new Exception("Flag carattere prestazione [" + vErogExportHelp.getFlagCaratterePrestazione()+ "] non riconosciuto ID ESEG MAST = " + vErogExportHelp.getId());
					}
				}
				
				//sono di tipo MASTER ma non sono ancora chiuse 
				else if ( vErogExportHelp.getTipoExport().equals(DataModelCostanti.VErogExportHelp.TIPO_EXPORT_TO_CLOSE)) { 
					//flaggo tutti i bean listaEsportazioneDTOview con questo master id come non esportabili
					aggiornaListaEsportazioneDTOviewNonEsportabili(expView, vErogExportHelp.getId(), "Erogazione ancora aperta"); 
				}
				
				//se l'esportazione è di tipo  EXPORT_RIGHE (si esportano solo le righe nell'intervallo di tempo selezionato)
				else if (vErogExportHelp.getTipoExport().equals(DataModelCostanti.VErogExportHelp.TIPO_EXPORT_RIGHE)) {
					//se la prestazione è periodica solo con le righe presenti nella lista (quindi quelle che sono comprese nell intervallo di date della selezione)
					if (vErogExportHelp.getFlagCaratterePrestazione().equals(DataModelCostanti.VErogExportHelp.FLAG_CARATTERE_PRESTAZIONE_PERIODICA)) {
						//creo il bean per l'xml							
						List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster = getListaEsportazioneDTOview(expView ,vErogExportHelp.getId().longValue());
						EsportazioneDTO esportazioneDTO = creaBeanExportRighePeriodica(listaEsportazioneDTOviewPerIdMaster, vErogExportHelp); 
						result.add(esportazioneDTO);
						
						// SISO-719 assegno l'indice di raggruppamento a ciascuna DTOView confluita in questo DTO
						for (EsportazioneDTOView viewDTO : listaEsportazioneDTOviewPerIdMaster) {
							assegnaSeqExportToListaEsportazioneDTOView(expView, viewDTO.getInterventoEsegId(), seqExport);
//							viewDTO.setSeqExport(seqExport);
						}
					}
					//se la prestazione è occasionale o non è valorizzata
					else {
						throw new Exception("Flag carattere prestazione [" + vErogExportHelp.getFlagCaratterePrestazione() + "] non riconosciuto ID ESEG MAST = " + vErogExportHelp.getId());
					}
				}
				
				else {
					throw new Exception("Tipo export [" + vErogExportHelp.getTipoExport() + "] non riconosciuto ID ESEG MAST = " + vErogExportHelp.getId());
				}
			}
			catch (Exception e) {
				aggiornaListaEsportazioneDTOviewNonEsportabili(expView, vErogExportHelp.getId(), e.getMessage());
				logger.error(e);
			}
			
			seqExport++;	// incremento l'indice di raggruppamento
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
	
		// stampava la dimensione della lista sbagliata...
//		logger.debug("filtraVErogExport -  NUM. ESPORTAZIONI USCITA[" + expView.size() + "]");
		logger.debug("filtraVErogExport -  NUM. ESPORTAZIONI USCITA[" + result.size() + "]");
		
		return result;
	}
	
	private static void scanForPresaInCaricoNonSo(List<EsportazioneDTOView> expView, List<EsportazioneDTO> result) {
		List<EsportazioneDTO> preseInCaricoNonSo = new ArrayList<EsportazioneDTO>();
		
		for (EsportazioneDTO esportazione : result) {
			if (esportazione.getPresaInCarico().equals(new BigDecimal(2))) {
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

	/* SISO-719 Metodo "quick and dirty" per salvare l'indice di raggruppamento - sarebbe da implementare in maniera più
	 * elegante, ma richiede un refactoring più profondo della logica.
	 * 
	 * In filtraVErogExport, nei casi in cui più DTOView confluiscono nello stesso DTO vengono create delle liste di
	 * nuovi oggetti DTOView. L'indice di raggruppamento va salvato sulla lista originale (quella di EsportaCasellarioBean). */
	private static void assegnaSeqExportToListaEsportazioneDTOView(
		List<EsportazioneDTOView> expView, Long interventoEsegId, int seqExport) {
		
		// cerco il DTOView originale per InterventoEsegId e salvo il seqExport
		for (EsportazioneDTOView esportazioneDTOView : expView) {
			if (esportazioneDTOView.getInterventoEsegId().equals(interventoEsegId)) {
				esportazioneDTOView.setSeqExport(seqExport);
				break;	// non serve cercare oltre
			}
		}
	}

	private static void aggiornaListaEsportazioneDTOviewNonEsportabili(List<EsportazioneDTOView> listaEsportazioneDTOview, BigDecimal id, String causaleMancatoInvio) {
		/* SISO-719
		 * 
		 * Correzione: esportazioneDTOView.getInterventoEsegMastId() restituisce un Long; se questo viene confrontato con un
		 * BigDecimal, il risultato sarà sempre false, anche quando il valore numerico effettivo è lo stesso. */
		Long idLong = id.longValue();
		
		aggiornaListaEsportazioneDTOviewNonEsportabili(listaEsportazioneDTOview, idLong, causaleMancatoInvio);
	}
	
	private static void aggiornaListaEsportazioneDTOviewNonEsportabili(List<EsportazioneDTOView> listaEsportazioneDTOview, Long id, String causaleMancatoInvio) {
		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
			if (esportazioneDTOView.getInterventoEsegMastId().equals(id)) {
				esportazioneDTOView.setDaInviare(false);//EsportazioneDTOView.EROGAZIONE_DA_NON_INVIARE);
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

	private static EsportazioneDTO creaBeanExportMasterPeriodica( List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster, 
																  VErogExportHelp vErogExportHelp) {
		
		EsportazioneDTOView primaEsportazione = listaEsportazioneDTOviewPerIdMaster.get(0);
 
		
		EsportazioneDTO esportazioneDTO = new EsportazioneDTO(); 
//      <Carattere>1</Carattere>
		esportazioneDTO.setCarattere(primaEsportazione.getCarattere());
//      <NumeroProtocolloDSU>INPS-ISEE-2015-01480722D-00</NumeroProtocolloDSU>
		esportazioneDTO.setNumProtDSU(primaEsportazione.getNumProtDSU());
//      <AnnoProtocollo>2015</AnnoProtocollo>
		esportazioneDTO.setAnnoProtDSU(primaEsportazione.getAnnoProtDSU());
//      <DataDSU>2015-09-23</DataDSU>
		esportazioneDTO.setDataDSU(primaEsportazione.getDataDSU());
//      <Codice>A3.04</Codice>
		esportazioneDTO.setCodPrestazione(primaEsportazione.getCodPrestazione());
//      <Denominazione>Edilizia residenziale pubblica</Denominazione>
		esportazioneDTO.setDenomPrestazione(primaEsportazione.getDenomPrestazione());
//      <ProtocolloEnte>2014-789</ProtocolloEnte>
		esportazioneDTO.setProtDomPrest(primaEsportazione.getProtDomPrest());
//      <DataErogazione> V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE</DataErogazione>
		
//		 DataInizio=> V_EROG_EXPORT_HELP.row .MIN_DATA_EROGAZIONE
		 esportazioneDTO.setDataInizio(vErogExportHelp.getMinDataErogazione());	
//		 DataFine =Se V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE_A <> NULL ALLORA V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE_A ALTRIMENTI V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE
		 esportazioneDTO.setDataFine(vErogExportHelp.getMaxDataErogazioneA()!=null? vErogExportHelp.getMaxDataErogazioneA(): vErogExportHelp.getMaxDataErogazione());	
//		 PeriodoErogazione = Differenza in mesi fra le due date sopra . 2FB-27 GEN = 1MESE
		 int periodoErogazione = differenzaMesi(esportazioneDTO.getDataInizio(), esportazioneDTO.getDataFine());
		  
		 
		 esportazioneDTO.setPeriodoErogazione(periodoErogazione);
//		 ImportoMensile = Si divide la somma di DATASET3.SPESA delle righe PER IL NUMERO DI MESI SOPRA
		 BigDecimal spesaTotale = listaEsportazioneDTOviewPerIdMaster.get(0).getSpesaTestata(); 
		 BigDecimal importoMensile = spesaTotale.divide(new BigDecimal(periodoErogazione), 2, RoundingMode.HALF_UP); 
		 esportazioneDTO.setImportoMensile(importoMensile);
		  
//		 QuotaEnte = V_EROG_EXPORT_HELP.row .VALORE_GESTITA_ENTE_CALC
		 esportazioneDTO.setPercGestitaEnte(vErogExportHelp.getValoreGestitaEnteCalc());
//		 QuotaUtente = V_EROG_EXPORT_HELP.row .COMPART_UTENTI
		 esportazioneDTO.setCompartUtenti(vErogExportHelp.getCompartUtenti());
//		 QuotaSSN = V_EROG_EXPORT_HELP.row .COMPART_SSN
		 esportazioneDTO.setCompartSsn(vErogExportHelp.getCompartSsn());

		 esportazioneDTO.setPresenzaProvaMezzi(primaEsportazione.getPresenzaProvaMezzi());
		 esportazioneDTO.setPresaInCarico(primaEsportazione.getPresaInCarico());
		 esportazioneDTO.setCategoriaSocialeId(primaEsportazione.getCategoriaSocialeId());

//       <QuotaRichiesta>Dato non presente , riportare TAG VUOTO</QuotaRichiesta>
//       <SogliaISEE>valore non presente valorizza TAG VUOTO</SogliaISEE>		  


		fillBeneficiario(esportazioneDTO, primaEsportazione);
//		fillForeignKey(esportazioneDTO, primaEsportazione);
		 
		return esportazioneDTO;
	}


	private static EsportazioneDTO creaBeanExportRighePeriodica(List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster, VErogExportHelp vErogExportHelp) {
		    
		EsportazioneDTOView primaEsportazione = listaEsportazioneDTOviewPerIdMaster.get(0);

		logger.debug("creaBeanExportRighePeriodica numero protocollo = " + primaEsportazione.getPrestazioneProtocEnte());
		
		EsportazioneDTO esportazioneDTO = new EsportazioneDTO(); 
//      <Carattere>1</Carattere>
		esportazioneDTO.setCarattere(primaEsportazione.getCarattere());
//      <NumeroProtocolloDSU>INPS-ISEE-2015-01480722D-00</NumeroProtocolloDSU>
		esportazioneDTO.setNumProtDSU(primaEsportazione.getNumProtDSU());
//      <AnnoProtocollo>2015</AnnoProtocollo>
		esportazioneDTO.setAnnoProtDSU(primaEsportazione.getAnnoProtDSU());
//      <DataDSU>2015-09-23</DataDSU>
		esportazioneDTO.setDataDSU(primaEsportazione.getDataDSU());
//      <Codice>A3.04</Codice>
		esportazioneDTO.setCodPrestazione(primaEsportazione.getCodPrestazione());
//      <Denominazione>Edilizia residenziale pubblica</Denominazione>
		esportazioneDTO.setDenomPrestazione(primaEsportazione.getDenomPrestazione());
//      <ProtocolloEnte>2014-789</ProtocolloEnte>
		esportazioneDTO.setProtDomPrest(primaEsportazione.getProtDomPrest());
//      <DataErogazione> V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE</DataErogazione>

		
//		 DataInizio = LA MIN DATA_ESECUZIONE FRA QUELLE SELEZIONATE
		 esportazioneDTO.setDataInizio(  getMinDataEsecuzioneRighePeriodica(listaEsportazioneDTOviewPerIdMaster) );	
//			DataFine = LA MAX DATA_ESECUZIONE_A FRA QUELLE SELEZIONATE. ( SE NULL SI PRENDE LA MAX DI DATA_ESECUZIONE )
		 esportazioneDTO.setDataFine( getMaxDataEsecuzioneRighePeriodica(listaEsportazioneDTOviewPerIdMaster)  );	
//		 PeriodoErogazione = Differenza in mesi fra le due date sopra . 2FB-27 GEN = 1MESE
		 int periodoErogazione = differenzaMesi(esportazioneDTO.getDataInizio(), esportazioneDTO.getDataFine());
		 esportazioneDTO.setPeriodoErogazione(periodoErogazione);
//		 ImportoMensile = Si divide la somma di DATASET3.SPESA delle righe PER IL NUMERO DI MESI SOPRA
		 BigDecimal spesaTotale = new BigDecimal(0);
		 for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOviewPerIdMaster) {
			spesaTotale = spesaTotale.add( getBdNotNull(esportazioneDTOView.getSpesa())  );
		 }
		  
		 BigDecimal importoMensile = spesaTotale.divide(new BigDecimal(periodoErogazione), 2, RoundingMode.HALF_UP); 
		 esportazioneDTO.setImportoMensile(importoMensile);
		  
//		 QuotaEnte = PER OGNI RIGA si somma dataset3 .VALORE_GESTITA_ENTE, SE NULL e dataset3 .PERC_GESTITA_ENTE NOT NULL allora si fa il calcolo SPESA * PERC_GESTITA_ENTE / 100 e si somma a QuotaEnte
		 
		 BigDecimal percGestitaEnte = new BigDecimal(0);
		 BigDecimal compartUtenti = new BigDecimal(0);
		 BigDecimal compartSsn = new BigDecimal(0);
		 for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOviewPerIdMaster) {
			 if (esportazioneDTOView.getValoreGestitaEnte()!=null) {
				// percGestitaEnte += ValoreGestitaEnte
				 percGestitaEnte = percGestitaEnte.add(esportazioneDTOView.getValoreGestitaEnte());
			} else if (esportazioneDTOView.getPercGestitaEnte()!=null) { 	 
				// compartUtenti += SPESA * PERC_GESTITA_ENTE / 100
				percGestitaEnte = percGestitaEnte.add(
						 esportazioneDTOView.getSpesa().multiply(   
								 esportazioneDTOView.getPercGestitaEnte()).
								 	divide(  new BigDecimal(100) ));
			} else { 
				percGestitaEnte =  percGestitaEnte.add( esportazioneDTOView.getSpesa() );
			}

			 compartUtenti = compartUtenti.add( getBdNotNull(esportazioneDTOView.getCompartUtenti()) );
			 compartSsn = compartSsn.add( getBdNotNull(esportazioneDTOView.getCompartSsn()) );
		 }
		 esportazioneDTO.setPercGestitaEnte( percGestitaEnte  );
//		 QuotaUtente = somma di dataset3 .COMPART_UTENTI
		 esportazioneDTO.setCompartUtenti( compartUtenti );
//		 QuotaSSN = somma di dataset3 .COMPART_SSN
		 esportazioneDTO.setCompartSsn( compartSsn );
		 
		 esportazioneDTO.setPresenzaProvaMezzi(primaEsportazione.getPresenzaProvaMezzi());
		 esportazioneDTO.setPresaInCarico(primaEsportazione.getPresaInCarico());
		 esportazioneDTO.setCategoriaSocialeId(primaEsportazione.getCategoriaSocialeId());
		 
//       <QuotaRichiesta>Dato non presente , riportare TAG VUOTO</QuotaRichiesta>
//       <SogliaISEE>valore non presente valorizza TAG VUOTO</SogliaISEE>		  
		 fillBeneficiario(esportazioneDTO, primaEsportazione);
//		 fillForeignKey(esportazioneDTO, primaEsportazione);

		return esportazioneDTO;
	}
	
	
	
	private static Date getMaxDataEsecuzioneRighePeriodica(List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster) {
		Date result = null;
		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOviewPerIdMaster) {
			Date maxDataEsecuzione = esportazioneDTOView.getDataEsecuzioneA()==null?esportazioneDTOView.getDataEsecuzione(): esportazioneDTOView.getDataEsecuzioneA();
			if (result == null || result.before(maxDataEsecuzione) ) {
				result = maxDataEsecuzione;
			}
		} 
		return result;
	}

	private static Date getMinDataEsecuzioneRighePeriodica(List<EsportazioneDTOView> listaEsportazioneDTOviewPerIdMaster) {
		Date result = null;
		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOviewPerIdMaster) {
			if (result == null || result.after(esportazioneDTOView.getDataEsecuzione()) ) {
				result = esportazioneDTOView.getDataEsecuzione();
			}
		} 
		
		return result;
	}

	private static int differenzaMesi(Date dataInizio, Date dataFine) {
		int diffMesi = 0; 

			Calendar dataInizioCalendar = new GregorianCalendar();
			dataInizioCalendar.setTime(dataInizio);
			Calendar dataFineCalendar = new GregorianCalendar();
			dataFineCalendar.setTime(dataFine);

			int diffAnni = dataFineCalendar.get(Calendar.YEAR) - dataInizioCalendar.get(Calendar.YEAR);
			diffMesi = diffAnni * 12 + dataFineCalendar.get(Calendar.MONTH) - dataInizioCalendar.get(Calendar.MONTH); 
		return diffMesi+1;
	}

	private static EsportazioneDTO creaBeanExportMasterOccasionale( EsportazioneDTOView esportazioneDTOView, VErogExportHelp vErogExportHelp) {
		EsportazioneDTO esportazioneDTO = new EsportazioneDTO(); 
//        <Carattere>2</Carattere>
		esportazioneDTO.setCarattere(esportazioneDTOView.getCarattere());
//        <NumeroProtocolloDSU>INPS-ISEE-2015-01480722D-00</NumeroProtocolloDSU>
		esportazioneDTO.setNumProtDSU(esportazioneDTOView.getNumProtDSU());
//        <AnnoProtocollo>2015</AnnoProtocollo>
		esportazioneDTO.setAnnoProtDSU(esportazioneDTOView.getAnnoProtDSU());
//        <DataDSU>2015-09-23</DataDSU>
		esportazioneDTO.setDataDSU(esportazioneDTOView.getDataDSU());
//        <Codice>A3.04</Codice>
		esportazioneDTO.setCodPrestazione(esportazioneDTOView.getCodPrestazione());
//        <Denominazione>Edilizia residenziale pubblica</Denominazione>
		esportazioneDTO.setDenomPrestazione(esportazioneDTOView.getDenomPrestazione());
//        <ProtocolloEnte>2014-789</ProtocolloEnte>
		esportazioneDTO.setProtDomPrest(esportazioneDTOView.getProtDomPrest());
//        <DataErogazione> V_EROG_EXPORT_HELP.row .MAX_DATA_EROGAZIONE</DataErogazione>
		esportazioneDTO.setDataEsecuzione(vErogExportHelp.getMaxDataErogazione());
//        <Importo>> V_EROG_EXPORT_HELP.row .SPESA</Importo>

		esportazioneDTO.setSpesaTestata(vErogExportHelp.getSpesa());
		
//        <QuotaEnte> V_EROG_EXPORT_HELP.row .VALORE_GESTITA_ENTE_CALC</QuotaEnte>
		esportazioneDTO.setPercGestitaEnte(vErogExportHelp.getValoreGestitaEnteCalc());
//        <QuotaUtente> V_EROG_EXPORT_HELP.row .COMPART_UTENTI</QuotaUtente>
		esportazioneDTO.setCompartUtenti(vErogExportHelp.getCompartUtenti());
//        <QuotaSSN> V_EROG_EXPORT_HELP.row .COMPART_SSN</QuotaSSN>
		esportazioneDTO.setCompartSsn(vErogExportHelp.getCompartSsn());
//        <QuotaRichiesta>LASCIARE VUOTO</QuotaRichiesta>
//        <SogliaISEE>LASCIARE VUOTO</SogliaISEE>

		esportazioneDTO.setPresenzaProvaMezzi(esportazioneDTOView.getPresenzaProvaMezzi());
		esportazioneDTO.setPresaInCarico(esportazioneDTOView.getPresaInCarico()); 
		esportazioneDTO.setCategoriaSocialeId(esportazioneDTOView.getCategoriaSocialeId());
		
		//fill dati beneficiario  
		fillBeneficiario(esportazioneDTO, esportazioneDTOView);
//		fillForeignKey(esportazioneDTO, esportazioneDTOView);  
		
		return esportazioneDTO;
	}

//	private static void fillForeignKey(EsportazioneDTO esportazioneDTO, EsportazioneDTOView esportazioneDTOView) {
//		 esportazioneDTO.setInterventoEsegId (esportazioneDTOView.getInterventoEsegId() );
//		 esportazioneDTO.setInterventoEsegMastId (esportazioneDTOView.getInterventoEsegMastId() );
//		 esportazioneDTO.setInterventoId (esportazioneDTOView.getInterventoId() );
//		
//	}

	private static void fillBeneficiario(EsportazioneDTO esportazioneDTO, EsportazioneDTOView esportazioneDTOView) { 
		 esportazioneDTO.setPrestazioneProtocEnte(esportazioneDTOView.getPrestazioneProtocEnte());
		 esportazioneDTO.setInterventoEsegMastId(esportazioneDTOView.getInterventoEsegMastId());
		
		 esportazioneDTO.setSoggettoCodiceFiscale(   esportazioneDTOView.getSoggettoCodiceFiscale()  );
		 esportazioneDTO.setBenefRegione(            esportazioneDTOView.getBenefRegione()           );
		 esportazioneDTO.setBenefComune(             esportazioneDTOView.getBenefComune()            );
		 esportazioneDTO.setBenefNazione(            esportazioneDTOView.getBenefNazione()           );
		 esportazioneDTO.setSoggettoNome(            esportazioneDTOView.getSoggettoNome()           );
		 esportazioneDTO.setSoggettoCognome(         esportazioneDTOView.getSoggettoCognome()        );
		 esportazioneDTO.setBenefAnnoNascita(        esportazioneDTOView.getBenefAnnoNascita()       );
		 esportazioneDTO.setBenefLuogoNascita(       esportazioneDTOView.getBenefLuogoNascita()      );
		 esportazioneDTO.setBenefSesso(              esportazioneDTOView.getBenefSesso()             );
		 esportazioneDTO.setBenefCittadinanza(       esportazioneDTOView.getBenefCittadinanza()      );
		
	}

	private static void aggiornaListaEsportazioneDTOview(List<EsportazioneDTOView>  listaEsportazioneDTOview, List<EsportazioneDTOView> daAggiungereList) {
		for (EsportazioneDTOView daAggiungere : daAggiungereList) {
			if (!isPresente(listaEsportazioneDTOview, daAggiungere) ) {
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

	private static ErogazioniSearchCriteria createErogazioniSearchCriteria( long idMaster) {
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();  
		try { 
			CsUiCompBaseBean.fillEnte(bDto);
		} catch (Exception e) {
			//in test fuori dal contesto del server questo metodo va in errore
		}
		bDto.setIdMaster(idMaster);
		return bDto;
	}

	private static EsportazioneDTOView getLastEsportazioneDTOview(List<EsportazioneDTOView> listaEsportazioneDTOview, BigDecimal masterId) {
		EsportazioneDTOView result = null;

		for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
			if (esportazioneDTOView.getInterventoEsegMastId().equals(masterId.longValue()) ) {
				if (result==null || result.getMaxDataEsecuzione().before(esportazioneDTOView.getMaxDataEsecuzione())) {
					result = esportazioneDTOView;
				}
			}
		}

		logger.debug("getLastEsportazioneDTOview master_id["+masterId+"], numero protocollo = " + result.getProtDomPrest());
		
		return result;
	}

	/* SISO-719 commentate perché risultavano inutilizzate */
//	private static Map<BigDecimal, VErogExportHelp> createVErogExportHelpMap( List<VErogExportHelp> listaVErogExportHelp) {
//		Map<BigDecimal,VErogExportHelp> result = new HashMap<BigDecimal, VErogExportHelp>();
//	
//		for (VErogExportHelp vErogExportHelp : listaVErogExportHelp) {
//			result.put(vErogExportHelp.getId(), vErogExportHelp);
//		}
//		
//		return result;
//	}
//	private static Map<Long, EsportazioneDTOView> createEsportazioneDTOview( List<EsportazioneDTOView> listaEsportazioneDTOview) {
//		 Map<Long, EsportazioneDTOView>  result = new HashMap<Long, EsportazioneDTOView>();
//		 
//		 for (EsportazioneDTOView esportazioneDTOView : listaEsportazioneDTOview) {
//			 result.put(esportazioneDTOView.getInterventoEsegMastId(), esportazioneDTOView);
//		 }
//		return result;
//	}
	

	public static String avvisoErogazioniNonEsportate( 
			List<EsportazioneDTOView> listaErogazioniMasterChiusuraInPeriodo, 
			List<EsportazioneDTOView> listaEsportazioneDTOview ) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
			masterIds.add( esportazioneDTOView.getInterventoEsegMastId() );
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

	

	//INIZIO SISO-586 Erogazione pronte per export ma non esportate effettivamente
	static BigDecimal getBdNotNull(BigDecimal  bd){
		return bd==null?BigDecimal.ZERO:bd;
	}
	//FINE SISO-586 Erogazione pronte per export ma non esportate effettivamente
	
//fine SISO-538	
	

	/* SISO-719 Costruzione lista delle Testate Erogazione da visualizzare */
	public static List<EsportazioneTestataDTO> costruisciListaTestateErogazione(
			List<EsportazioneDTOView> daEsportareList,
			List<EsportazioneDTOView> giaInviateList) {
		
		List<EsportazioneTestataDTO> testate = new ArrayList<EsportazioneTestataDTO>();	// oggetto di ritorno
		
		/* Per ciascuna erogazione in daEsportareList, cerco la testata di appartenenza. Se la trovo, aggiungo
		 * l'erogazione alla lista dei dettagli per quella testata, altrimenti la uso per creare una nuova testata. */
		for (EsportazioneDTOView erogazioneDaEsportare : daEsportareList) {
			aggiornaTestate(testate, erogazioneDaEsportare);
		}
		
		/* Per ciascuna erogazione in giaInviateList:
		 *   -flaggo opportunamente le proprietà dell'oggetto DTOView, contrassegnando l'erogazione come esportata, non daInviare
		 *    e impostando come messaggio di mancato invio "Erogazione esportata in data GG/MM/YYYY"
		 *   -cerco la testata di appartenenza: se la trovo, aggiungo l'erogazione alla lista dei dettagli per quella testata,
		 *    altrimenti la uso per creare una nuova testata. */
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

	// quick and dirty
	private static EsportazioneTestataDTO cercaTestata(List<EsportazioneTestataDTO> testate, Long interventoEsegMastId) {
		for (EsportazioneTestataDTO testata : testate) {
			if (testata.getInterventoEsegMastId().equals(interventoEsegMastId)) {
				return testata;
			}
		}
		
		return null;	// se non trovo il dettaglio, restituisco null
	}
	
	// quick and dirty
	private static void aggiornaTestate(List<EsportazioneTestataDTO> testate, EsportazioneDTOView erogazioneDaAggiungere) {
		EsportazioneTestataDTO testata = cercaTestata(testate, erogazioneDaAggiungere.getInterventoEsegMastId());
		
		if (testata == null) {
			testate.add(new EsportazioneTestataDTO(erogazioneDaAggiungere));
		}
		else {
			testata.aggiungiDettaglioAllaTestata(erogazioneDaAggiungere);
		}
	}
	
	public void controllaErogPeriodiche(List<EsportazioneDTO> daEsportare, List<EsportazioneDTOView> giaInviate){
		
		for(EsportazioneDTO de : daEsportare){
			
		}
		
	}

	
	/* SISO-719 Metodo impostaIdentificazioneFlusso rimosso perché obsoleto */
	
//	//INIZIO SISO-586
//	public static void impostaIdentificazioneFlusso(
//			String absolutePath,
//			String idFlusso) throws Exception {
//			   	  String oldFileName = absolutePath;
//			      String tmpFileName = absolutePath + ".temporary";  
//
//		      BufferedReader br = null;
//		      BufferedWriter bw = null;
//		      try {
//		         br = new BufferedReader(new FileReader(oldFileName));
//		         bw = new BufferedWriter(new FileWriter(tmpFileName));
//		         String line;
//		         while ((line = br.readLine()) != null) {
//		            if (line.contains("IdentificazioneFlusso")){ 	            	
//			               line = line.replace(EsportaCasellarioBean.idFlussoFake, idFlusso);
//		            }
//		            bw.write(line+"\n");
//		         }
//		      } catch (Exception e) {
//		         throw e;
//		      } finally {
//		         try {
//		            if(br != null)
//		               br.close();
//		         } catch (IOException e) {
//		         }
//		         try {
//		            if(bw != null)
//		               bw.close();
//		         } catch (IOException e) {
//		         }
//		      }
//		      // Once everything is complete, delete old file..
//		      File oldFile = new File(oldFileName);
//		      oldFile.delete();
//		
//		      // And rename tmp file's name to old file name
//		      File newFile = new File(tmpFileName);
//		      newFile.renameTo(oldFile);  
//		
//	}
//	//FINE SISO-586

}
