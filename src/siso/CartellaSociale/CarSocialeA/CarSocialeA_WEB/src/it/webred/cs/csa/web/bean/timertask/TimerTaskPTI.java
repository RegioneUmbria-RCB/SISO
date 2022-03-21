package it.webred.cs.csa.web.bean.timertask;

import it.webred.cs.csa.ejb.client.AccessTableAlertSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePaiPTISessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.csa.ejb.dto.alert.AlertDTO;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiInfoSinteticheDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.ArCsPaiPTIDocumentoDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPTIDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.CsPaiPtiFaseDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoMinoreStrutturaEnum;
import it.webred.cs.csa.ejb.dto.pai.pti.PaiPTIFaseEnum;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipiAlertCod;
import it.webred.cs.data.DataModelCostanti.TipoSecondoLivello;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsPaiMastSogg;
import it.webred.cs.data.model.CsTbTipoPai;
import it.webred.cs.data.model.pti.CsPaiPTI;
import it.webred.cs.data.model.pti.CsPaiPTIFase;
import it.webred.cs.data.model.pti.CsPaiPtiDocumento;
import it.webred.cs.data.model.pti.InserimentoMinoreDaStruttura;
import it.webred.cs.data.model.pti.RichiestaDisponibilitaPaiPti;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.jboss.logging.Logger;
import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class TimerTaskPTI {
	private String enteId;
	private final String UTENTE_MINORI_TIMERTASK = "USR_MINORI_TTASK";

	private AccessTableConfigurazioneEnteSessionBeanRemote confEnteService;

	private AccessTablePaiPTISessionBeanRemote paiPTIService;

	private AccessTableDiarioSessionBeanRemote diarioService;

	private AccessTableAlertSessionBeanRemote alertService;

	private static Logger logger = Logger.getLogger("carsociale_timertask.log");

	public TimerTaskPTI(String enteId) throws NamingException {
		super();
		this.enteId = enteId;
		confEnteService = (AccessTableConfigurazioneEnteSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA",
				"CarSocialeA_EJB", "AccessTableConfigurazioneEnteSessionBean");
		paiPTIService = (AccessTablePaiPTISessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA",
				"CarSocialeA_EJB", "AccessTablePaiPTISessionBean");
		diarioService = (AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA",
				"CarSocialeA_EJB", "AccessTableDiarioSessionBean");
		alertService = (AccessTableAlertSessionBeanRemote) ClientUtility
				.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableAlertSessionBean");
	}

	public void gestisciInsMinoriStruttura() throws Exception {
		// recupero i minori inseriti dalla struttura non ancora processati
		CeTBaseObject cet = new CeTBaseObject();
		cet.setEnteId(enteId);
		List<InserimentoMinoreDaStruttura> listInserimentiDaProcessare;
		try {
			listInserimentiDaProcessare = paiPTIService.findInsMinoriInStruttura(cet);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return;
		}

		if (listInserimentiDaProcessare != null) {
			for (InserimentoMinoreDaStruttura ins : listInserimentiDaProcessare) {
				if (InserimentoMinoreStrutturaEnum.INS.toString().equals(ins.getStato())) {
					processaNuovoInserimento(ins);
				}
			}
		}
		// creo una notifica
	}

	private void processaNuovoInserimento(InserimentoMinoreDaStruttura ins) throws Exception {

		CsPaiMastSogg minore;
		CsDPai pai;
		List<CsPaiMastSogg> lstBeneficiari;
		CsPaiPTI pti;
		try {
			
			//JT - QUESTO MECCANISMO FUNZIONA SOLO SE ESISTE UN UNICO SETTORE DI TIPO MINORI PER CIASCUNA ORGANIZZAZIONE
			BaseDTO opDto = new BaseDTO();
			opDto.setEnteId(enteId);
			opDto.setObj(TipoSecondoLivello.MINORI);// Va ricercato come II livello = equipe?
			opDto.setObj2(ins.getCodRouting());
			opDto.setObj3(UTENTE_MINORI_TIMERTASK);
			CsOOperatoreSettore opFittizio = confEnteService.findOperatoreSettore2LivByUsername(opDto);
			if(opFittizio==null){
				String msg = "Impossibile elaborare il nuovo Inserimento del minore in struttura: operatore fittizio "+UTENTE_MINORI_TIMERTASK+" non configurato per TipoSecondoLivello["+TipoSecondoLivello.MINORI+"] ente["+enteId+"]";
				logger.error(msg);
				throw new Exception(msg);
			}
			// recupero l'eventuale caso da ins.getCf()
			Long idCaso = null;
			CsASoggettoLAZY sogg = recuperaSoggetto(ins.getCf());
			if (sogg != null && sogg.getCsACaso() != null) {
				idCaso = sogg.getCsACaso().getId();
			}

			/*
			 * 
			 * Gestione PAI (Progetto Individuale)
			 * 
			 */
			BaseDTO dto = new BaseDTO();
			dto.setEnteId(enteId);
			pai = new CsDPai();
			initializePai(pai);

			dto.setObj(pai);
			dto.setObj2(idCaso);
			dto.setObj3(opFittizio);

			// visualizzazione secondo livello
			pai = diarioService.savePai(dto);

			/*
			 * 
			 * Gestione Minore
			 * 
			 */
			Long diarioId = pai.getDiarioId();
			ins.setDiarioPaiId(diarioId);
			minore = recuperaMinore(ins, idCaso);

			lstBeneficiari = new ArrayList<CsPaiMastSogg>();
			lstBeneficiari.add(minore);

			BaseDTO dtoBeneficiari = new BaseDTO();
			dtoBeneficiari.setEnteId(enteId);
			dtoBeneficiari.setObj(lstBeneficiari);
			// salvo il minore
			diarioService.saveBeneficiariPai(dtoBeneficiari);
			
			/*
			 * Gestione PTI Gestione Richiesta Disponibilita "fake"
			 * 
			 */
			pti = new CsPaiPTI();
			pti.setCodRouting(enteId);
			pti.setFlgEmergenza(false);
			pti.setDiarioPaiId(diarioId);
			pti.setDataRedazione(new Date());
			pti.setTipoStruttura(ins.getTipoStruttura());

			String stato = InserimentoMinoreStrutturaEnum.PRESA_IN_CARICO.toString();

			pti.setIdStruttura(ins.getStruttura());
			pti.setPeriodoInsPianificazioneDa(ins.getDataInizioPermamenza());
			pti.setPeriodoInsPianificazioneA(ins.getDataFinePermanenza());

			RichiestaDisponibilitaPaiPti rich = new RichiestaDisponibilitaPaiPti();
			rich.setCodRouting(enteId);
			rich.setDataRichiesta(new Date());
			rich.setDataAccStruttura(new Date());
			rich.setDataInizioPermamenza(ins.getDataInizioPermamenza());
			rich.setDataFinePermanenza(ins.getDataFinePermanenza());
			rich.setIdStruttura(ins.getStruttura());
			rich.setDocumento(ins.getDocumentoPai());
			rich.setNomeDocumento(ins.getNomeDocPai());
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

			try {
				rich.setDettaglioMinore((ow.writeValueAsString(toMinoreDTO(minore))));
			} catch (Exception e) {
				logger.error(e);
			}

			try {
				rich.setDettaglioPTI(ow.writeValueAsString(toDTO(pti)));
			} catch (Exception e) {
				logger.error(e);
			}

			rich.setStatoRichiesta("ACCETTATA");
			pti.addRichiestaDisponibilita(rich);

			BaseDTO bdto = new BaseDTO();
			bdto.setEnteId(enteId);
			bdto.setObj(pti);
			bdto.setObj2(PaiPTIFaseEnum.STRUTT_OK.getId());
			pti = paiPTIService.saveNuovoPTIFromBatch(bdto);

			savePaiPtiDocumentoFromBatch(ins.getDocumentoPai(),ins.getNomeDocPai(),"PAI_DOC", pti.getId());
			savePaiPtiDocumentoFromBatch(ins.getDocumentoPtiEqui(),ins.getNomeDocPtiEqui(),"PTI_DOC", pti.getId());
			
			/*
			 * Aggiornamento Inserimento
			 * 
			 */
			ins.setStato(stato);
			BaseDTO insDto = new BaseDTO();
			insDto.setEnteId(enteId);
			insDto.setObj(ins);
			paiPTIService.saveInsMinoreStruttura(insDto);

			// TODO-ADD addAlert nel batch gia esistente per settore struttura e tutti gli
			// operatori della zona
			BaseDTO dtoS = new BaseDTO();
			dtoS.setEnteId(enteId);
			dtoS.setObj(ins.getStruttura());
			CsOSettore settoreStruttura = confEnteService.findSettoreByRelStruttura(dtoS);

			String descrizione = "Inserimento nella Struttura " + settoreStruttura.getNome() + " di "+ minore.getCognome() + " " + minore.getNome() + " <br/><br/>";
			String titDescrizione = "Inserimento in Struttura di " + minore.getCognome() + " " + minore.getNome();

			// ricerca i
			BaseDTO dtoDest = new BaseDTO();
			dtoDest.setEnteId(enteId);
			dtoDest.setObj(DataModelCostanti.TipoSecondoLivello.MINORI);// Va ricercato come II livello = equipe?
			dtoDest.setObj2(ins.getCodRouting());// Va verificato se è codRouting su cui insiste struttura
			List<CsOOperatoreSettore> opSets = confEnteService.findOperatoreSettoreByCodStruttura(dtoDest);

			for (CsOOperatoreSettore opSett : opSets) {
				addAlert(TipiAlertCod.PAI, null, titDescrizione, descrizione, null, null, settoreStruttura,
						settoreStruttura.getCsOOrganizzazione(), opSett, null, null);
			}

		} catch (Exception e) {
			logger.error("Errore processo di inserimento per il minore " + ins.getCf(), e);
			ins.setStato("INS_KO");
			BaseDTO insDto = new BaseDTO();
			insDto.setEnteId(enteId);
			insDto.setObj(ins);
			try {
				paiPTIService.saveInsMinoreStruttura(insDto);
			} catch (Exception e1) {
				logger.error("Errore durante l'aggiornamento dell'inserimento");
			}
		}

	}

	private void savePaiPtiDocumentoFromBatch(byte[] documento, String nome, String tipo, Long ptiId ) throws Exception{
		if (documento != null) {
			CsPaiPtiDocumento docPai = new CsPaiPtiDocumento();
			docPai.setDocumento(documento);
			docPai.setDtIns(new Date());
			InputStream is = new ByteArrayInputStream(documento);
			String mimeType = URLConnection.guessContentTypeFromStream(is);
			docPai.setTipo(mimeType);
			docPai.setNome(nome);
			docPai.setTipoDocumento(tipo);
			docPai.setIdPaiPTI(ptiId);
			docPai.setCodRouting(enteId);
			docPai.setFlgNotifica(true);
			docPai.setValidoDa(new Date());
            
			BaseDTO bdtoDoc = new BaseDTO();
			bdtoDoc.setEnteId(enteId);
			bdtoDoc.setObj(docPai);

			paiPTIService.saveDocumentoFromBatch(bdtoDoc);

		}
	}
	
	private CsPaiPTIDTO toDTO(CsPaiPTI source) {
		if (source == null) {
			return null;
		}

		CsPaiPTIDTO target = new CsPaiPTIDTO();
		String[] ignore = { "fasiPTI", "richiesteDisponibilita", "documentiPTI" };
		BeanUtils.copyProperties(source, target, ignore);

		if (source.getFasiPTI() != null && !source.getFasiPTI().isEmpty()) {
			target.setFasiPTI(new ArrayList<CsPaiPtiFaseDTO>());
			for (CsPaiPTIFase fase : source.getFasiPTI()) {
				target.getFasiPTI().add(toDTO(fase));
			}
		}

		if (source.getDocumenti() != null && !source.getDocumenti().isEmpty()) {
			target.setDocumentiPTI(new ArrayList<ArCsPaiPTIDocumentoDTO>());
			for (CsPaiPtiDocumento doc : source.getDocumenti()) {
				target.getDocumentiPTI().add(toDTO(doc));
			}
		}

		return target;
	}

	private CsPaiPtiFaseDTO toDTO(CsPaiPTIFase source) {
		if (source == null) {
			return null;
		}

		CsPaiPtiFaseDTO target = new CsPaiPtiFaseDTO();
		String[] ignore = { "paiPTI", "fase" };
		BeanUtils.copyProperties(source, target, ignore);

		return target;
	}

	private ArCsPaiPTIDocumentoDTO toDTO(CsPaiPtiDocumento source) {
		if (source == null) {
			return null;
		}

		ArCsPaiPTIDocumentoDTO target = new ArCsPaiPTIDocumentoDTO();
		String[] ignore = { "paiPTI" };
		BeanUtils.copyProperties(source, target, ignore);

		if (source.getArCsPaiInfoSintetiche() != null) {
			String[] ignoreInfo = { "csPaiPtiDocumento" };
			ArCsPaiInfoSinteticheDTO infoDTO = new ArCsPaiInfoSinteticheDTO();
			BeanUtils.copyProperties(source.getArCsPaiInfoSintetiche(), infoDTO, ignoreInfo);

			target.setArCsPaiInfoSinteticheDTO(infoDTO);
		}
		return target;
	}

	private CsPaiMastSoggDTO toMinoreDTO(CsPaiMastSogg source) {
		if (source == null) {
			return null;
		}
		CsPaiMastSoggDTO target = new CsPaiMastSoggDTO();
		String[] ignore = { "pai" };
		BeanUtils.copyProperties(source, target, ignore);
		return target;
	}

	public void gestisciAcquisizionePai() throws Exception {
		// recupero i documenti inseriti dalla struttura non ancora processati
		BaseDTO bDto = new BaseDTO();
		bDto.setEnteId(enteId);
		List<ArCsPaiPTIDocumentoDTO> listDocumentiDaProcessare;
		try {
			listDocumentiDaProcessare = paiPTIService.findDocumentiDaProcessare(bDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return;
		}

		if (listDocumentiDaProcessare != null) {
			for (ArCsPaiPTIDocumentoDTO doc : listDocumentiDaProcessare) {

				notificaNuovoDocumento(doc);

			}
		}

	}

	private void notificaNuovoDocumento(ArCsPaiPTIDocumentoDTO doc) throws Exception {
		// devo creare l'alert per ogni nuovo documento non ancora notificato flgNotifica =0
		// dopo aver creato l'alert aggiorna la tabella dei documenti inserendo FlgNotifica = 1
		// l'alert va inviato al case manager del progetto PTI
		// ricerca i

		try {

			BaseDTO dto = new BaseDTO();
			dto.setEnteId(enteId);
			dto.setObj(doc.getIdPaiPTI());
			CsPaiPTI pti = paiPTIService.findPTIById(dto);

			Long idCaseManager = pti.getIdCaseManager();
			
			// prima devo trovare il PTI a cui è collegato il documento per poi recuperare
			// il caseManager
			BaseDTO dtoDest = new BaseDTO();
			dtoDest.setEnteId(enteId);
			dtoDest.setObj(DataModelCostanti.TipoSecondoLivello.MINORI);// Va ricercato come II livello = equipe?
			dtoDest.setObj2(pti.getCodRouting());
			dtoDest.setObj3(idCaseManager);// Va verificato se è codRouting su cui insiste struttura
			CsOOperatoreSettore opSet = confEnteService.findOperatoreSettore2LivByIdOperatore(dtoDest);
			

            // recupero il minore dalla PAI MAST SOGG	
			CsPaiMastSogg minore = new CsPaiMastSogg();
			BaseDTO pdto = new BaseDTO();
			pdto.setEnteId(enteId);
			pdto.setObj(pti.getDiarioPaiId());
			minore = diarioService.findSoggettoPaiByDiarioId(pdto);
			


			// QUI cerco la struttura che ha revisionato il PAI
			CsOSettore csSOSettoreStruttura = new CsOSettore();
			BaseDTO dtoS = new BaseDTO();
			dtoS.setEnteId(enteId);
			dtoS.setObj(pti.getIdStruttura());
			csSOSettoreStruttura = confEnteService.findSettoreByRelStruttura(dtoS);

			String descrizione = "Revisione del PAI per " + minore.getCognome() + " "+ minore.getNome() + "<br/><br/>";
			String titDescrizione = "Revisione del PAI per " + minore.getCognome() + " "+ minore.getNome();

			addAlert(TipiAlertCod.PAI, null, titDescrizione, descrizione, null, null, csSOSettoreStruttura,
					csSOSettoreStruttura.getCsOOrganizzazione(), opSet, null, null);

			// aggiorno il flgNotifica del documento:
			doc.setFlgNotifica(true);
			BaseDTO bdto = new BaseDTO();
			bdto.setEnteId(enteId);
			bdto.setObj(doc);

			paiPTIService.saveDocumento(bdto);

		} catch (Exception e) {
			logger.error("Errore durante la notifica del nuovo", e);

		}

	}

	public void gestisciAcquisizioneConsuntivazioni() throws Exception {
		AccessTablePaiPTISessionBeanRemote paiPTIService = (AccessTablePaiPTISessionBeanRemote) ClientUtility
				.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTablePaiPTISessionBean");

		// recuperole consuntivazioni inserite dalla struttura non ancora processate

		BaseDTO bDto = new BaseDTO();
		bDto.setEnteId(enteId);
		List<InserimentoConsuntivazioneDTO> listConsuntivazioneDaProcessare;
		try {
			listConsuntivazioneDaProcessare = paiPTIService.findConsuntiDaProcessare(bDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return;
		}

		if (listConsuntivazioneDaProcessare != null) {
			for (InserimentoConsuntivazioneDTO consuntivazione : listConsuntivazioneDaProcessare) {

				notificaNuovaConsuntivazione(consuntivazione);

			}
		}
	}

	private void notificaNuovaConsuntivazione(InserimentoConsuntivazioneDTO consuntivazione) throws Exception {
		// devo creare l'alert per ogni nuova consuntivazione non ancora notificata
		// flgNotifica =0
		// dopo aver creato l'alert aggiorna la tabella delle consuntivazioni inserendo
		// FlgNotifica = 1
		// l'alert va inviato al case manager del progetto PTI

		try {
			
			BaseDTO dto = new BaseDTO();
			dto.setEnteId(enteId);
			dto.setObj(consuntivazione.getIdPaiPTI());
			CsPaiPTI pti = paiPTIService.findPTIById(dto);

			Long idCaseManager = pti.getIdCaseManager();

			// prima devo trovare il PTI a cui è collegato il documento per poi recuperare
			// il caseManager
			BaseDTO dtoDest = new BaseDTO();
			dtoDest.setEnteId(enteId);
			dtoDest.setObj(DataModelCostanti.TipoSecondoLivello.MINORI);// Va ricercato come II livello = equipe?
			dtoDest.setObj2(pti.getCodRouting());
			dtoDest.setObj3(idCaseManager);// Va verificato se è codRouting su cui insiste struttura
			CsOOperatoreSettore opSet = confEnteService.findOperatoreSettore2LivByIdOperatore(dtoDest);

			 // recupero il minore dalla PAI MAST SOGG	
			CsPaiMastSogg minore = new CsPaiMastSogg();
			BaseDTO pdto = new BaseDTO();
			pdto.setEnteId(enteId);
			pdto.setObj(pti.getDiarioPaiId());
			minore = diarioService.findSoggettoPaiByDiarioId(pdto);
			
			// QUI cerco la struttura che ha revisionato il PAI
			CsOSettore csSOSettoreStruttura = new CsOSettore();
			BaseDTO dtoS = new BaseDTO();
			dtoS.setEnteId(enteId);
			dtoS.setObj(pti.getIdStruttura());
			csSOSettoreStruttura = confEnteService.findSettoreByRelStruttura(dtoS);

			String descrizione = "Inserita nuova consuntivazione per  " + minore.getCognome() + " "+ minore.getNome() + "<br/><br/>";
			String titDescrizione = "Nuova consuntivazione per  " + minore.getCognome() + " "+ minore.getNome();

			addAlert(TipiAlertCod.PAI, null, titDescrizione, descrizione, null, null, csSOSettoreStruttura, 
					 csSOSettoreStruttura.getCsOOrganizzazione(), opSet, null, null);

			// aggiorno il flgNotifica del documento:
			consuntivazione.setFlagNotifica(true);
			consuntivazione.setEnteId(enteId);
			paiPTIService.salvaConsuntivazione(consuntivazione);

		} catch (Exception e) {
			logger.error("Errore durante l'aggiornamento della consuntivazione", e);
		}

	}

	private CsASoggettoLAZY recuperaSoggetto(String cf) throws Exception {
		AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility
				.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

		BaseDTO dto = new BaseDTO();
		dto.setEnteId(enteId);
		dto.setObj(cf);
		return soggettoService.getSoggettoByCF(dto);

	}

	private CsPaiMastSogg recuperaMinore(InserimentoMinoreDaStruttura ins, Long idCaso) {
		CsPaiMastSogg minore = new CsPaiMastSogg();
		minore.setAnnoNascita(ins.getAnnoNascita());
		minore.setCasoId(idCaso);
		minore.setCf(ins.getCf());
		minore.setCittadinanza(ins.getCittadinanza());
		minore.setCognome(ins.getCognome());
		minore.setComuneResidenza(ins.getComuneResidenza());
		// inserisco diario id dopo aver salvato il progetto individuale
		minore.setDiarioId(ins.getDiarioPaiId());
		minore.setDtIns(new Date());
		minore.setDtMod(new Date());
		minore.setIntestatario(true);
		//Se da Rilevazione presenze ho inserito il comune di residenza la Nazione di residenza è Italia
		//ma in CS se viene valorizzato il comune di residenza la nazione è a null
		if(ins.getComuneResidenza()!=null && !ins.getComuneResidenza().isEmpty()) {
			if(ins.getNazioneResidenza()!=null && !ins.getNazioneResidenza().isEmpty()) {
				if(ins.getNazioneResidenza().contentEquals("1")) {
					minore.setNazioneResidenza(null);
				}
			}
		}else{
			if(ins.getNazioneResidenza()!=null && !ins.getNazioneResidenza().isEmpty()) {
		
			   minore.setNazioneResidenza(ins.getNazioneResidenza());
			}
		}
		
		minore.setNome(ins.getNome());
		minore.setSesso(ins.getSesso());
		minore.setUserIns("ZsTimerTask");
		minore.setViaResidenza(ins.getViaResidenza());
		return minore;
	}

	private void initializePai(CsDPai pai) {
		pai.setTipoBeneficiario(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);
		pai.setTotBeneficiari(Integer.valueOf(1));
		pai.setMonitoraggioObiettivi(false);
		pai.setVerificaOgni(BigDecimal.ZERO);
		pai.setVerificaUnitaMisura("Giorni");
		pai.setObiettiviBreve("");
		pai.setObiettiviMedio("");
		pai.setObiettiviLungo("");
		pai.setDataMonitoraggio(null);
		pai.setMotivazioniProgetto("");
		pai.setTipoProgettoId(new Long(11));
		CsTbTipoPai tipoPai = new CsTbTipoPai();
		// pti equivalente
		tipoPai.setId(11);
		pai.setCsTbTipoPai(tipoPai);
		pai.getCsDDiario().setUserIns("ZsTimerTask");
		pai.getCsDDiario().setDtAttivazioneDa(new Date());
		pai.getCsDDiario().setDtAmministrativa(new Date());
	}

	private void addAlert(String tipo, CsACaso caso, String oggetto, String descrizione, String url,
			CsOOperatore opFrom, CsOSettore settFrom, CsOOrganizzazione orgFrom, CsOOperatoreSettore opTo,
			CsOSettore settTo, CsOOrganizzazione orgTo) throws Exception {

		AlertDTO newAlert = new AlertDTO();
		newAlert.setEnteId(enteId);

		OperatoreDTO opdto = new OperatoreDTO();
		opdto.setEnteId(enteId);

		if (opFrom != null)
			newAlert.setOperatoreFrom(opFrom);
		if (settFrom != null) {
			newAlert.setSettoreFrom(settFrom);
			newAlert.setOrganizzazioneFrom(settFrom.getCsOOrganizzazione());
		} else
			newAlert.setOrganizzazioneFrom(orgFrom);

		newAlert.setCaso(caso);
		newAlert.setTipo(tipo);

		newAlert.setOpSettoreTo(opTo);
		newAlert.setSettoreTo(settTo);
		newAlert.setOrganizzazioneTo(orgTo);

		newAlert.setDescrizione(descrizione);
		newAlert.setUrl(url);
		newAlert.setTitolo(oggetto);

		alertService.addAlert(newAlert);
	}

	public String getEnteId() {
		return enteId;
	}

	public void setEnteId(String enteId) {
		this.enteId = enteId;
	}

}
