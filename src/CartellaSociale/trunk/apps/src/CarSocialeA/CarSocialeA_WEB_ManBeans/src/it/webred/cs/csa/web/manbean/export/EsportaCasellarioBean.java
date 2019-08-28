package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.csa.ejb.dto.EsportazioneTestataDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.ListaBeneficiari;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsIPsExportMast;
import it.webred.cs.data.model.CsIQuotaRipartiz;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.VErogExportHelp;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.io.FileUtils;
import org.jboss.logging.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.osmosit.siso.flussoinps.logic.Cost;

@ManagedBean
@ViewScoped
public class EsportaCasellarioBean extends CsUiCompBaseBean {
	public static Logger logger = Logger.getLogger("carsociale.log");
	Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	private CsOOperatoreSettore opSettore;
	private Boolean showDlgEsporta=true;
	
	private Boolean showPnlEsporta;
	private Boolean showPnlVisualizza;
	
	private Date dataDA;
	private Date dataA;
	private int numErogDaEsportare = 0;
	File tempFile;
	
	//SISO-738 filtri ricerca
	private String cf;
	private String nome;
	private String cognome;
	private String prestazione;
	private String areaTarget;
	private String numeroProtocollo;
	private String statoEsportazione;
	private String filtroFrequenza;
	
  /* SISO-719 Refactoring, rimosse variabili globali per idFlusso (si usa solo quello vero) */
  //	String idFlusso;
  //	static final String idFlussoFake = "XXXX.XXX.XXXX.XXXXXXXX.XXXXXX"; // SISO-586 
	
	protected AccessTablePsExportSessionBeanRemote psExportService =
		(AccessTablePsExportSessionBeanRemote) getCarSocialeEjb("AccessTablePsExportSessionBean");
	
	/*SISO-738*/
	protected AccessTableInterventoSessionBeanRemote interventoService = 
			(AccessTableInterventoSessionBeanRemote) getCarSocialeEjb( "AccessTableInterventoSessionBean");
	
	/*SISO-738*/
	protected AccessTableCatSocialeSessionBeanRemote catSocService = 
			(AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb( "AccessTableCatSocialeSessionBean");
	

	/* SISO-719 aggiunti dei commenti per chiarire meglio il funzionamento del Bean
	 * 
	 * erogDaEsportareVisualizzazioneList contiene le righe che vengono visualizzate nella view - la corrispondenza è 1:1 con
	 *   i contenuti di CS_I_INTERVENTO_ESEG
	 * erogDaEsportareList contiene le righe utilizzate per la composizione del messaggio XML - la corrispondenza è 1:1 con
	 *   gli elementi PrestazioniSociali (più righe di CS_I_INTERVENTO_ESEG possono confluire in un elemento PrestazioniSociali)
	 * erogGiaInviate contiene le righe delle erogazioni che risultano già inviate. In precedenza non era una variabile
	 *   globale, ma a fronte del refactoring (in particolare per costruire la lista erogTestataVisualizzazioneList) è stato
	 *   utile portarla fuori
	 * erogTestataVisualizzazioneList contiene le righe che vengono visualizzate nella view, secondo la gerarchia a due livelli
	 *   di testata e dettaglio - viene popolata a partire da erogDaEsportareVisualizzazioneList
	 */
	private List<EsportazioneDTO> erogDaEsportareList;
	private List<EsportazioneDTOView> erogDaEsportareVisualizzazioneList; //modifica SISO-538
	private List<EsportazioneDTOView> erogGiaInviateList;	// SISO-719
	private List<EsportazioneTestataDTO> erogTestataVisualizzazioneList;	// SISO-719
	
	private String avvisoErogazioniNonEsportate; 					//modifica SISO-538
	
	private List<String> listaCategorieSociali; //SISO-738
	
	private EsportazioneDTOView erogDaRevocare = null; //SISO-780
	

	@PostConstruct
	public void init() {
		dataA = new Date();
		showPnlEsporta = false;
		showPnlVisualizza = false;
		opSettore = getCurrentOpSettore();
		statoEsportazione = "a"; //SISO-738
		caricaCategorieSociali(); //SISO-738
	}
	
	//SISO-738
	private void caricaCategorieSociali(){
		listaCategorieSociali = new ArrayList<String>();
		CeTBaseObject b = new CeTBaseObject();
		fillEnte(b);
		 List<CsCCategoriaSociale> categorieSocialiAll = catSocService.getCategorieSocialiAll(b);
		 for (CsCCategoriaSociale ccs : categorieSocialiAll) {
			listaCategorieSociali.add(ccs.getDescrizione().toLowerCase());
		}
	}
		
	public void onDownloadRefresh() {
		logger.debug("onDownloadRefresh - Ricarico le liste");
		onBtnVerificaCasellarioClick();
	}
	
	// al click su "Verifica", di fatto il punto di partenza dell'interazione utente
	public Boolean onBtnVerificaCasellarioClick() {
		findErogDaEsportare();
		findErogGiaInviate();
		
		//SISO-780
		findErogPeriodiche();
		
		buildErogTestataList();
		
		//SISO-738 applicazione filtri
		filtraRisultato();
		
		//SISO-738 aggiorno il numero dopo l'applicazione dei filtri
		numErogDaEsportare = calcolaNumErogDaEsportare(erogDaEsportareVisualizzazioneList);
		
		showPnlEsporta = true;
		showPnlVisualizza = true; //SISO-738
		
		return showPnlEsporta;
	}
	
	/**
	 * SISO-738 
	 * Il metodo in questione filtra i risultati ottenuti dalla query in base ai filtri selezionati
	 * metodo TOP-DOWN:
	 * prima viene effettuata la pulizia della lista che gestisce la tabella in view (erogTestataVisualizzazioneList) in base ai filtri
	 * poi vengono pulite anche le liste necessarie alla estrapolazione (erogDaEsportareList, erogDaEsportareVisualizzazioneList) in base ai CF presenti nella lista erogTestataVisualizzazioneList
	 */
	private void filtraRisultato(){
        Iterator<EsportazioneTestataDTO> it = erogTestataVisualizzazioneList.iterator();
		
		while(it.hasNext()){
			
			EsportazioneTestataDTO el = it.next();
			
			if(
					//FILTRO CF
					!cf.isEmpty() && !el.getSoggettoCodiceFiscale().toLowerCase().contains(cf)
					//FILTRO COGNOME
					|| !cognome.isEmpty() && !el.getSoggettoCognome().toLowerCase().contains(cognome)
					//FILTRO NOME
					|| !nome.isEmpty() && !el.getSoggettoNome().toLowerCase().contains(nome)
					//FILTRO DENOMINAZIONE PRESTAZIONE 
					|| !prestazione.isEmpty() && !el.getDenominazionePrestazione().toLowerCase().contains(prestazione)
					//FILTRO AREA TARGET
					|| !areaTarget.isEmpty() && !el.getCategoriaSocialeDescrizione().toLowerCase().contains(areaTarget)
					//FILTRO NUMERO PROTOCOLLO
					|| !numeroProtocollo.isEmpty() && !el.getPrestazioneProtocolloEnte().toLowerCase().contains(numeroProtocollo) 
					
			  )
			{
			   //rimuovo il nodo dalla lista se non rispetta i filtri
			   it.remove();	
			}else{
				//rimuovo i dettagli se il filtro è selezionato
				if(!statoEsportazione.equals("a")){
					
					Iterator<EsportazioneDTOView> itd = el.getDettagli().iterator();
					
					while (itd.hasNext()) {
						EsportazioneDTOView eld = itd.next();
						
						if(statoEsportazione.equals("e") && !eld.isEsportata() || statoEsportazione.equals("ne") && eld.isEsportata()){
							itd.remove();
						}
					}
					
					//se non ci sono più dettagli elimino la testata a cui è riferita
					if(el.getDettagli().isEmpty()){
						it.remove();
					}
				}
				//SET FREQUENZA
				if(el.getInterventoEsegMastId() != null){
					
					BaseDTO b = new BaseDTO();
					fillEnte(b);
					b.setObj(el.getInterventoEsegMastId());
					
					try {
						CsIInterventoEsegMast interventoEsegMast = interventoService.getErogazioneMasterById(b);
						CsIQuotaRipartiz quotaRip = interventoEsegMast.getCsIQuota().getCsIQuotaRipartiz();
						
						if(quotaRip != null){
							
							if(quotaRip.isFlagIrregolare()){
								el.setFrequenza(EsportazioneTestataDTO.FREQUENZA_IRREGOLARE);
							}
							
							if(quotaRip.isFlagRegolare()){
								el.setFrequenza(EsportazioneTestataDTO.FREQUENZA_REGOLARE);
							}
							
							if(quotaRip.isFlagUnatantum()){
								el.setFrequenza(EsportazioneTestataDTO.FREQUENZA_UNATANTUM);
							}
							
						}
						
						if(filtroFrequenza != null && !filtroFrequenza.isEmpty()){
							if(!el.getFrequenza().equals(filtroFrequenza)){
								it.remove();
							}
						}
						
						//SISO-809
						if(interventoEsegMast.getTipoBeneficiario().equals(ListaBeneficiari.GRUPPO)){
							it.remove();
						}
						//fine SISO-809
					} catch (Exception e) {	}
				}
			}
		}
		
		//pulisco la lista da esportare in base ai filtri
		Iterator<EsportazioneDTO> ite = erogDaEsportareList.iterator();
		while(ite.hasNext()){
			EsportazioneDTO exp = ite.next();
			if (!esisteCfinTestataView(exp.getSoggettoCodiceFiscale())){
				ite.remove();
			}
		}
		
		//pulisco la lista da esportare visualizzazione in base ai filtri
		Iterator<EsportazioneDTOView> itev = erogDaEsportareVisualizzazioneList.iterator();
		while(itev.hasNext()){
			EsportazioneDTOView edv = itev.next();
			if (!esisteCfinTestataView(edv.getSoggettoCodiceFiscale())){
				itev.remove();
			}
		}
	}
	
	//SISO-738 metodo ausiliario per pulizia lista
	private boolean esisteCfinTestataView(String cf){
		
		boolean trovato = false;
		
		for (EsportazioneTestataDTO et : erogTestataVisualizzazioneList) {
			if(et.getSoggettoCodiceFiscale().equalsIgnoreCase(cf)){
				trovato = true;
				break;
			}
		}
		
		return trovato;
	}
	
	private String getCodRoutingCorrente(){
		return opSettore.getCsOSettore().getCsOOrganizzazione().getBelfiore();
	}
	
	private String getCodCatastaleCorrente(){
		return opSettore.getCsOSettore().getCsOOrganizzazione().getBelfiore();
	}
	
	private void findErogDaEsportare() {
		numErogDaEsportare = 0;
		
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		fillEnte(bDto);
		bDto.setDataInizio(formatter.format(dataDA));
		bDto.setDataFine(formatter.format(dataA));
//		bDto.setOperatoreId(opSettore.getId());
		bDto.setOrganizzazioneBelfiore(getCodRoutingCorrente());
		
//INIZIO modifica SISO-538
		erogDaEsportareVisualizzazioneList = psExportService.findErogazDaInviareInPeriodo(bDto);
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(getIds(erogDaEsportareVisualizzazioneList));
		
		List<VErogExportHelp> listaVErogExportHelp = psExportService.findVErogExportHelp(dto);
		
		erogDaEsportareList =
			EsportaCasellarioUtils.filtraVErogExport(erogDaEsportareVisualizzazioneList, listaVErogExportHelp, psExportService);
		
		//numErogDaEsportare = calcolaNumErogDaEsportare(erogDaEsportareVisualizzazioneList);
		
		List<EsportazioneDTOView> listaErogazioniMasterChiusuraInPeriodo =
			psExportService.findErogazioniMasterChiusuraInPeriodo(bDto);
		
		avvisoErogazioniNonEsportate =
			EsportaCasellarioUtils.avvisoErogazioniNonEsportate(
				listaErogazioniMasterChiusuraInPeriodo, erogDaEsportareVisualizzazioneList);
//FINE  modifica SISO-538
	}
	
	private void findErogGiaInviate() {
		
		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		fillEnte(bDto);
		bDto.setDataInizio(formatter.format(dataDA));
		bDto.setDataFine(formatter.format(dataA));
//		bDto.setOperatoreId(opSettore.getId());
		bDto.setOrganizzazioneBelfiore(getCodRoutingCorrente());
		
		// SISO-719 flussiInviati diventa variabile globale
//		List<EsportazioneDTOView> flussiInviati = psExportService.findErogazGiaInviateInPeriodo(bDto);
//		numErogGiaInviate = flussiInviati.size();
		
		erogGiaInviateList = psExportService.findErogazGiaInviateInPeriodo(bDto);
	}
	
	//SISO-780
	public void findErogPeriodiche(){
		//garantisco l'univocità degli id
		Set<BigDecimal> mastIdsPeriodiche = new HashSet<BigDecimal>();
		
		//DA ESPORTARE
		Iterator<EsportazioneDTOView> evi = erogDaEsportareVisualizzazioneList.iterator();
		while(evi.hasNext()){
			EsportazioneDTOView ev = evi.next();
			
			if(ev.getCarattere().equalsIgnoreCase(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)){
				mastIdsPeriodiche.add(new BigDecimal(ev.getInterventoEsegMastId()));
				evi.remove();
			}
		}
		Iterator<EsportazioneDTO> edit = erogDaEsportareList.iterator();
		while(edit.hasNext()){
			EsportazioneDTO ex = edit.next();
			if(ex.getCarattere().equalsIgnoreCase(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)){
				mastIdsPeriodiche.add(new BigDecimal(ex.getInterventoEsegMastId()));
				edit.remove();
			}
		}
		
		//ESPORTATE
		evi = erogGiaInviateList.iterator();
		while(evi.hasNext()){
			EsportazioneDTOView ev = evi.next();
			
			if(ev.getCarattere().equalsIgnoreCase(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)){
				mastIdsPeriodiche.add(new BigDecimal(ev.getInterventoEsegMastId()));
				evi.remove();
			}
		}
				
		
		if(mastIdsPeriodiche.size() > 0){
			ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
			fillEnte(bDto);
			bDto.setDataFine(formatter.format(dataA));
			bDto.setLstMasterId(new ArrayList<BigDecimal>(mastIdsPeriodiche));
			List<EsportazioneDTOView> periodiche =  psExportService.findErogazPeriodicheByMast(bDto);
			
			List<EsportazioneDTOView> daErogare = new ArrayList<EsportazioneDTOView>();
			List<EsportazioneDTOView> erogate = new ArrayList<EsportazioneDTOView>();
			for(EsportazioneDTOView ev : periodiche){
				
				//esportate
				if(ev.getDataEsportazione() != null)
				{
					erogate.add(ev);
				}
				//da esportare
				else
				{
					daErogare.add(ev);
				}
			}
			
			erogGiaInviateList.addAll(erogate);
			erogDaEsportareVisualizzazioneList.addAll(daErogare);
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(getIds(daErogare));
			
			List<VErogExportHelp> listaVErogExportHelp = psExportService.findVErogExportHelp(dto);
			
			erogDaEsportareList.addAll(
				EsportaCasellarioUtils.filtraVErogExport(daErogare, listaVErogExportHelp, psExportService)
				);
		}
	}
	
	/* SISO-719
	 * 
	 * Popolamento erogTestataVisualizzazioneList, effettuato a partire da erogDaEsportareVisualizzazioneList
	 * e erogGiaInviateList. */
	private void buildErogTestataList() {
		erogTestataVisualizzazioneList =
			EsportaCasellarioUtils.costruisciListaTestateErogazione(erogDaEsportareVisualizzazioneList, erogGiaInviateList);
	}
	
	
	private List<BigDecimal> getIds(List<EsportazioneDTOView> listaEsportazioneDTOview) {
		Set<BigDecimal> ids = new HashSet<BigDecimal>();
		
		for (EsportazioneDTOView e : listaEsportazioneDTOview) { 
			ids.add(new BigDecimal(e.getInterventoEsegMastId()));
		}
		
		return new ArrayList<BigDecimal>(ids);
	}

	
	public Boolean onBtnResetClick() {
		showPnlEsporta = false;
		showPnlVisualizza = false;
		dataDA = null;
		
		//SISO-738
		cf = null;
		cognome = null;
		nome = null;
		prestazione = null;
		areaTarget=null;
		numeroProtocollo=null;
		statoEsportazione="a";
		numErogDaEsportare = 0;
		
		return showPnlEsporta;
	}
	
	/* SISO-719 Revoca di un'erogazione
	 * 
	 * NB: se viene richiesta la revoca di un'erogazione che era confluita in un unico elemento "PrestazioniSociali" assieme
	 *     ad altre erogazioni, allora la revoca viene automaticamente estesa a tutte le erogazioni
	 */
	public Boolean onBtnRevocaClick() {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(erogDaRevocare);
		
		psExportService.updateCsIPsExportRevocaEsportazione(dto);
		
		return onBtnVerificaCasellarioClick();	// refresh e return
	}
	
	private HashMap<Object, Object> insertDatiBeneficiario(EsportazioneDTO erogDaEsportare){		
		HashMap<Object, Object> mappaDatiSogg= new HashMap<Object, Object>();
		if(erogDaEsportare.getSoggettoCodiceFiscale()!=null)
		mappaDatiSogg.put(Cost.BENEFICIARIO_CF,erogDaEsportare.getSoggettoCodiceFiscale());
		if(erogDaEsportare.getBenefRegione()!=null)
		mappaDatiSogg.put(Cost.RESIDENZA_REGIONE, erogDaEsportare.getBenefRegione());
		if(erogDaEsportare.getBenefComune()!=null)
		mappaDatiSogg.put(Cost.RESIDENZA_COMUNE, erogDaEsportare.getBenefComune());
		if(erogDaEsportare.getBenefNazione()!=null)
		mappaDatiSogg.put(Cost.RESIDENZA_NAZIONE, erogDaEsportare.getBenefNazione());
		if(erogDaEsportare.getSoggettoNome()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_NOME,erogDaEsportare.getSoggettoNome());
		if(erogDaEsportare.getSoggettoCognome()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_COGNOME, erogDaEsportare.getSoggettoCognome());
		if(erogDaEsportare.getBenefAnnoNascita()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_ANNONASCITA, Integer.toString(erogDaEsportare.getBenefAnnoNascita()));
		if(erogDaEsportare.getBenefLuogoNascita()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_LUOGONASCITA, erogDaEsportare.getBenefLuogoNascita());
		if(erogDaEsportare.getBenefSesso()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_SESSO,Integer.toString(erogDaEsportare.getBenefSesso()));
		if(erogDaEsportare.getBenefCittadinanza()!=null)
		mappaDatiSogg.put(Cost.ANAGRAFICA_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefCittadinanza()));
//		if(erogDaEsportare.getBenefSecCittadinanza()!=null)
//		mappaDatiSogg.put(Cost.ANAGRAFICA_SEC_CITTAD_ISO, Integer.toString(erogDaEsportare.getBenefSecCittadinanza()));		
		
		return mappaDatiSogg;
	}
	
	private HashMap<Object, Object> insertDatiPrestazione(EsportazioneDTO erogDaEsportare){
		HashMap<Object, Object> mappaDatiPrest= new HashMap<Object, Object>();
		DateFormat datef = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat decf = new DecimalFormat();
		/* prestazione periodica */
		//TODO: mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_INIZIO, xmlDateInizio);
		// mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_FINE, erogDaEsportare.getPrestDataFine());
		// mappaDatiPrest.put(Cost.PRESTAZIONE_PERIOD_EROG, erogDaEsportare.getPrestPeriodoErogaz());
		// mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO_MENS, erogDaEsportare.getPrestImportoMens());				
		
		/* prestazione occasionale */
		
		if(erogDaEsportare.getNumProtDSU()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_NUMPROT_DSU, erogDaEsportare.getNumProtDSU());
		if(erogDaEsportare.getAnnoProtDSU()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_ANNO_PROT, Integer.toString(erogDaEsportare.getAnnoProtDSU()));
		if(erogDaEsportare.getDataDSU()!=null){			
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_DSU, datef.format(erogDaEsportare.getDataDSU()));
		}
		if(erogDaEsportare.getCodPrestazione()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_CODICE, erogDaEsportare.getCodPrestazione());
		if(erogDaEsportare.getDenomPrestazione()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_DENOMINAZIONE, erogDaEsportare.getDenomPrestazione());
			
		if(erogDaEsportare.getDataEsecuzione()!=null){		
			mappaDatiPrest.put(Cost.PRESTAZIONE_DATA_EROG, datef.format(erogDaEsportare.getDataEsecuzione()));
		}
		if(erogDaEsportare.getSpesa()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_IMPORTO, decf.format(erogDaEsportare.getSpesa()));				
		
		/* dati comuni */	
		if(erogDaEsportare.getCarattere()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_CARATTERE, erogDaEsportare.getCarattere());
		if(erogDaEsportare.getPercGestitaEnte()!=null){
			mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_ENTE, decf.format(erogDaEsportare.getPercGestitaEnte()));
		}
		if(erogDaEsportare.getCompartUtenti()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_UTENTE, decf.format(erogDaEsportare.getCompartUtenti()));
		if(erogDaEsportare.getCompartSsn()!=null)
		mappaDatiPrest.put(Cost.PRESTAZIONE_QUOTA_SSN, decf.format(erogDaEsportare.getCompartSsn())); 
		
//		if(erogDaEsportare.getSogliaISEE()!=null)
//		mappaDatiPrest.put(Cost.PRESTAZIONE_SOGLIA_ISEE, decf.format(erogDaEsportare.getSogliaISEE()));	
		
		return mappaDatiPrest;
	}
	 

	public StreamedContent getFile2015() {
		try {
			CsIPsExportMast exportMast = onBtnEsportaCasellarioClick(SchemaVersion.PSA_2015);
			InputStream stream = new FileInputStream(tempFile);
			StreamedContent file = new DefaultStreamedContent(stream, "text/xml", exportMast.getIdentificazioneFlusso() + ".xml");
			return file;
		} catch (FileNotFoundException e) {
			addError("Esportazione fallita", "errore nel recupero del file xml");
			logger.error("Esportazione fallita",e);
			return null;
		} catch (Exception e) {
			addError("Esportazione fallita", "la procedura di esportazione ha segnalato un errore");
			logger.error("Esportazione fallita",e);
			return null;
		}
	}

	public StreamedContent getFile() {
		try {
			//SISO-780
			gestionePeriodiche();
			
			CsIPsExportMast exportMast = onBtnEsportaCasellarioClick(SchemaVersion.PSA_2016_PS);
			InputStream stream = new FileInputStream(tempFile);
			StreamedContent file = new DefaultStreamedContent(stream, "text/xml", exportMast.getIdentificazioneFlusso() + ".xml");
			
			 //Refresh caricamento dati
			onDownloadRefresh();
			return file;
		} catch (FileNotFoundException e) {
			addError("Esportazione fallita", "errore nel recupero del file xml");
			logger.error("Esportazione fallita",e);
			return null;
		} catch (Exception e) {
			addError("Esportazione fallita", "la procedura di esportazione ha segnalato un errore: "+e.getMessage());
			logger.error("Esportazione fallita",e);
			return null;
		}
	}

	public CsIPsExportMast onBtnEsportaCasellarioClick(SchemaVersion schemaVersion) throws Exception {
		CsIPsExportMast exportMasterEntity = null;	// fallback value
		
		if (erogDaEsportareList.size() > 0) {
			
			/*
			 * Validazione esportazione: devono essere presenti i dati DSU: anno, data e protocollo o nessuno di essi
			 */
			for (EsportazioneDTO es : erogDaEsportareList) {
				if(  !(
						(es.getDataDSU() != null && (es.getAnnoProtDSU() != null && es.getAnnoProtDSU()>0) && (es.getNumProtDSU() != null && !es.getNumProtDSU().isEmpty())) ||
						(es.getDataDSU() == null && (es.getAnnoProtDSU() == null || es.getAnnoProtDSU().equals(new Integer(0)))  && (es.getNumProtDSU() == null || es.getNumProtDSU().isEmpty()))
				  ))
				{
				    throw new Exception("controllare i campi anno, data e protocollo DSU");
				}
			}
			
			tempFile = File.createTempFile("EsportaCasellarioBean", "");

			CsOOrganizzazione csOOrganizzazione = opSettore.getCsOSettore().getCsOOrganizzazione();

			String belfiore = this.getCodCatastaleCorrente();
			if(belfiore==null) logger.warn("L'ente corrente non ha un COD_CATASTALE: nell'export verrà utilizzato il codice fittizio (COD_ROUTING)");
				
			String codEnte = belfiore!=null ? belfiore : getCodRoutingCorrente();
			String denomEnte = csOOrganizzazione.getNome();
			String cfOperatore = opSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getCodiceFiscale();
			String indirEnte = belfiore!=null ? belfiore : getCodRoutingCorrente(); // SISO-626
			Date actualDate = new Date();
			

			/* SISO-719
			 * 
			 * Refactoring vecchia soluzione + aggiunta nuovi campi sulle tabelle di export.
			 * 
			 * 1- Viene preparata la Entity Export Mast (CS_I_PS_EXPORT_MAST) e tutte le sue righe figlie (CS_I_PS_EXPORT).
			 *    idFlusso (Attributo Nome di IdentificazioneFlusso sull'XML da generare) è calcolato e salvato nell'Entity.
			 * 2- Viene generato l'XML e salvato in tempFile
			 * 3- L'XML viene aggiunto sull'Entity Export Mast
			 * 4- L'Entity (e quindi anche tutte le sue figlie) viene persistita sul DB */
			exportMasterEntity =
				preparaEntityExport(codEnte, denomEnte, indirEnte, cfOperatore, actualDate, erogDaEsportareVisualizzazioneList);
						
			EsportaCasellarioUtils.esportaCasellario(
				tempFile.getAbsolutePath(), 		// String XML_PATH
				erogDaEsportareList,
				exportMasterEntity.getIdentificazioneFlusso(),	// String idFlusso,
				denomEnte, 							// String denomEnte,
				codEnte, 							// String codEnte,
				cfOperatore, 						// String cfOperatore,
				indirEnte, 							// String indirEnte
				schemaVersion				// selettore della versione
			);
						
			// salvo l'XML generato nell'Entity Export Master
			String xml = FileUtils.readFileToString(tempFile);
			exportMasterEntity.setXml(xml);
			
			// dopo essere riuscito a generare l'XML, posso salvare le Entity
			BaseDTO dto = new BaseDTO(); 
			CsUiCompBaseBean.fillEnte(dto);
			dto.setObj(exportMasterEntity);
			psExportService.saveCsIPsExportMast(dto);
						
			
//			// INIZIO SISO-586 prima creo il file, poi salvo i dati sul db,
//			// infine modifico il nome del flusso all'interno del file
//			EsportaCasellarioUtils.esportaCasellario(
//					tempFile.getAbsolutePath(), 		// String XML_PATH
//					erogDaEsportareList, idFlussoFake, 	// String idFlusso,
//					denomEnte, 							// String denomEnte,
//					codEnte, 							// String codEnte,
//					cfOperatore, 						// String cfOperatore,
//					indirEnte, 							// String indirEnte
//					schemaVersion				// selettore della versione
//					// TODO verificare in quale punto iniettare il parametro
//			); // SISO-538
//
//			long numeroProgressivo = salvaDatiExport(codEnte, denomEnte, indirEnte, cfOperatore, actualDate, erogDaEsportareVisualizzazioneList);
//			idFlusso = creaIdFlusso(codEnte, actualDate, numeroProgressivo);
//
//			EsportaCasellarioUtils.impostaIdentificazioneFlusso(tempFile.getAbsolutePath(), idFlusso);
//			// FINE SISO-586
		}
		else {
			logger.debug("onBtnEsportaCasellarioClick: niente da esportare");
		}
		
		return exportMasterEntity;
	}
	

	// SISO-719 vecchia firma del metodo
//	/**
//	 * restituisce il progressivo dell'elaborazione
//	 * @param enteErogatore
//	 * @param denominazEnte
//	 * @param indirizzoEnte
//	 * @param cfOperatore
//	 * @param actualDate 
//	 * @throws CarSocialeServiceException
//	 */
	//	private long salvaDatiExport(String enteErogatore, String denominazEnte, String indirizzoEnte, String cfOperatore, Date actualDate, List<EsportazioneDTOView> erogazioni) throws CarSocialeServiceException { 
	
	private CsIPsExportMast preparaEntityExport(String enteErogatore, String denominazEnte, String indirizzoEnte,
			String cfOperatore, Date actualDate, List<EsportazioneDTOView> erogazioni) throws CarSocialeServiceException {
		
		logger.debug("Inizio preparaEntityExport");
		
		// Entity dell'export della testata erogazione (andrà in CS_I_PS_EXPORT_MAST)
		CsIPsExportMast csIPsExportMast = new CsIPsExportMast(); 
		
		csIPsExportMast.setEnteErogatore(enteErogatore);
		csIPsExportMast.setDenominazEnte(denominazEnte);
		csIPsExportMast.setIndirizzoEnte(indirizzoEnte);
		csIPsExportMast.setCfOperatore(cfOperatore);
		csIPsExportMast.setFlusso("PSA");	// costante
		csIPsExportMast.setDataInvio(actualDate);
		
		// generazione e salvataggio del NumeroProgressivo
		BaseDTO progressivoCsIPsExportMastPSA = new BaseDTO();  
		CsUiCompBaseBean.fillEnte(progressivoCsIPsExportMastPSA);
		progressivoCsIPsExportMastPSA.setObj(csIPsExportMast.getEnteErogatore()); 
		csIPsExportMast.setNumProgressivo(psExportService.getProgressivoCsIPsExportMastPSA(progressivoCsIPsExportMastPSA));
		
		// generazione e salvataggio dell'idFlusso (IdentificazioneFlusso.Nome nell'XML da generare)
		String idFlusso = creaIdFlusso(enteErogatore, actualDate, csIPsExportMast.getNumProgressivo());
		csIPsExportMast.setIdentificazioneFlusso(idFlusso);
		
		csIPsExportMast.setDtIns(actualDate);
		csIPsExportMast.setDtMod(actualDate);
		
		// SISO-719 salvataggio posticipato
//			BaseDTO dto = new BaseDTO(); 
//			CsUiCompBaseBean.fillEnte(dto);
//			dto.setObj(csIPsExportMast);
//			csIPsExportMast = psExportService.saveCsIPsExportMast(dto);
		
		// generazione delle Entity dell'export delle singole erogazioni (andranno in tabella CS_I_PS_EXPORT) della testata
		Set<CsIPsExport> csIPsExportList = new HashSet<CsIPsExport>();
		for (EsportazioneDTOView esportazioneDTO : erogazioni) {
			if (esportazioneDTO.isDaInviare()) {
				logger.debug("InterventoEsegId " + esportazioneDTO.getInterventoEsegId());
				CsIPsExport psExport = new CsIPsExport(); 
				
				CsIIntervento csIIntervento = new CsIIntervento();
				if (esportazioneDTO.getInterventoId() != null) {
					csIIntervento.setId(esportazioneDTO.getInterventoId().longValue());   
					psExport.setCsIIntervento(csIIntervento);
				}
				
				if (esportazioneDTO.getInterventoEsegId() != null) {
					CsIInterventoEseg csIInterventoEseg = new CsIInterventoEseg();
					csIInterventoEseg.setId(esportazioneDTO.getInterventoEsegId()); 
					psExport.setCsIInterventoEseg(csIInterventoEseg); 
				}
			
				if (esportazioneDTO.getInterventoEsegMastId() != null) {
					CsIInterventoEsegMast csIInterventoEsegMast = new CsIInterventoEsegMast();
					csIInterventoEsegMast.setId(esportazioneDTO.getInterventoEsegMastId());   
					psExport.setCsIInterventoEsegMast(csIInterventoEsegMast); 
				}
				
				psExport.setCsIPsExportMast(csIPsExportMast);
				psExport.setDtIns(actualDate);
				psExport.setDtMod(actualDate);
				
				/* SISO-719 nuovi campi
				 * 
				 * Ogni riga inserita è considerata esportata.
				 * La data di esportazione è la data odierna, la data di revoca è inizialmente null.
				 * L'indice di raggruppamento è stato già calcolato e salvato nella DTOView (esportazioneDTO) al click
				 * su "Verifica" */
				psExport.setFlagEsportato(1);
				psExport.setDtExport(actualDate);
				psExport.setDtRevocaExport(null);
				psExport.setXmlElementGrouping(esportazioneDTO.getSeqExport());
				
				
				csIPsExportList.add(psExport);
			}
		} 
		
		csIPsExportMast.setCsIPsExportList(csIPsExportList);
		
		
		// SISO-719 salvataggio posticipato
//			dto.setObj(csIPsExportMast);
//			psExportService.saveCsIPsExportMast(dto);
		
		
//			return csIPsExportMast.getNumProgressivo();
		return csIPsExportMast;
	}
	
	//SISO-780
	public void gestionePeriodiche(){
		
		boolean gestitePeridiche = false;
		
		for(EsportazioneTestataDTO etdto : erogTestataVisualizzazioneList){
			if(etdto.getFrequenza().equalsIgnoreCase(etdto.FREQUENZA_REGOLARE)){
				
				int daGestire = 0;
				
				for(EsportazioneDTOView ev : etdto.getDettagli()){
					if(ev.isDaInviare()){
						daGestire ++;
					}
				}
				
				//gestisco solo se ho almeno più di una erogazione da inviare
				if(daGestire > 0 && etdto.getDettagli().size() > 1){
					gestitePeridiche = true;
					//revoca tutte e aggiunge il resto a quelle da erogare
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(etdto.getInterventoEsegMastId());
					
					//vengono revocate TUTTE le eorgazioni associate alla MAST anche quelle fuori dal range di date selezionato
					psExportService.updateCsIPsExportRevocaEsportazioneByInterventoEsegMastId(dto);
				}
			}
		}
		
		//se sono state gestite almeno una periodica, ricarico le liste per l'esportazione
		if(gestitePeridiche){
			onBtnVerificaCasellarioClick();
		}
	}
	
	public void impostaErogDaRevocare(EsportazioneDTOView erog){
		this.erogDaRevocare = erog;
	}
	

	public Boolean getShowDlgEsporta() {
		return showDlgEsporta;
	}
	
	public void setShowDlgEsporta(Boolean showDlgEsporta) {
		this.showDlgEsporta = showDlgEsporta;
	}

	public Date getDataDA() {
		return dataDA;
	}

	public void setDataDA(Date dataDA) {
		this.dataDA = dataDA;
	}

	public Date getDataA() {
		return dataA;
	}

	public void setDataA(Date dataA) {
		this.dataA = dataA;
	}

	public Boolean getShowPnlEsporta() {
		return showPnlEsporta;
	}

	public void setShowPnlEsporta(Boolean showPnlEsporta) {
		this.showPnlEsporta = showPnlEsporta;
	}

	public Boolean getShowPnlVisualizza() {
		return showPnlVisualizza;
	}

	public void setShowPnlVisualizza(Boolean showPnlVisualizza) {
		this.showPnlVisualizza = showPnlVisualizza;
	}

	public int getNumErogDaEsportare() {
		return numErogDaEsportare;
	}

	public void setNumErogDaEsportare(int numErogDaEsportare) {
		this.numErogDaEsportare = numErogDaEsportare;
	}

	public List<EsportazioneDTO> getErogDaEsportareList() {
		return erogDaEsportareList;
	}

	public void setErogDaEsportareList(List<EsportazioneDTO> erogDaEsportareList) {
		this.erogDaEsportareList = erogDaEsportareList;
	}
	
	
//INIZIO modifica SISO-538
//	public int getNumErogNonDisponibiliAllInvio() {
//		return erogDaEsportareVisualizzazioneList.size() - numErogDaEsportare;
//	}
	
	
	public List<EsportazioneDTOView> getErogDaEsportareVisualizzazioneList() {
		return erogDaEsportareVisualizzazioneList;
	}

	public void setErogDaEsportareVisualizzazioneList(
			List<EsportazioneDTOView> erogDaEsportareVisualizzazioneList) {
		this.erogDaEsportareVisualizzazioneList = erogDaEsportareVisualizzazioneList;
	}
	
	// SISO-719
	public List<EsportazioneTestataDTO> getErogTestataVisualizzazioneList() {
		return erogTestataVisualizzazioneList;
	}
//
//	public void setErogTestataVisualizzazioneList(
//			List<EsportazioneTestataDTO> erogTestataVisualizzazioneList) {
//		this.erogTestataVisualizzazioneList = erogTestataVisualizzazioneList;
//	}

	public boolean isBtnEsportaDisabled(){
		return numErogDaEsportare == 0;
	}
	
	

	private int calcolaNumErogDaEsportare( List<EsportazioneDTOView> erogDaEsportareVisualizzazioneList) {
		int numErogDaEsportare = 0;
		for (EsportazioneDTOView esportazioneDTOView : erogDaEsportareVisualizzazioneList) {
			if (esportazioneDTOView.isDaInviare()) {
				numErogDaEsportare++;
			}else{
				logger.debug("calcolaNumErogDaEsportare NON INVIARE"+esportazioneDTOView.getSoggettoCodiceFiscale());
			}
		}
		return numErogDaEsportare;
	}
	

	public String getAvvisoErogazioniNonEsportate() {
		return avvisoErogazioniNonEsportate;
	}

	public void setAvvisoErogazioniNonEsportate(String avvisoErogazioniNonEsportate) {
		this.avvisoErogazioniNonEsportate = avvisoErogazioniNonEsportate;
	}
//FINE modifica SISO-538
	

	//INIZIO MOD-RL  
	private String creaIdFlusso(String codEnte, Date actualDate, long numeroProgressivo) { 
		String EEEE = codEnte;
		String aaaammgg = new SimpleDateFormat("yyyyMMdd").format(actualDate);
		String nnnnnnn = String.format("%07d", numeroProgressivo);
		
		return EEEE + ".PSA.INPS." + aaaammgg + "." + nnnnnnn;
	}


	public String getCf() {
		return cf;
	}


	public void setCf(String cf) {
		this.cf = cf.trim().toLowerCase();
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome.trim().toLowerCase();
	}


	public String getCognome() {
		return cognome;
	}


	public void setCognome(String cognome) {
		this.cognome = cognome.trim().toLowerCase();
	}


	public String getPrestazione() {
		return prestazione;
	}


	public void setPrestazione(String prestazione) {
		this.prestazione = prestazione.trim().toLowerCase();
	}


	public String getAreaTarget() {
		return areaTarget;
	}


	public void setAreaTarget(String areaTarget) {
		this.areaTarget = areaTarget.trim().toLowerCase();
	}


	public String getStatoEsportazione() {
		return statoEsportazione;
	}


	public void setStatoEsportazione(String statoEsportazione) {
		this.statoEsportazione = statoEsportazione.trim().toLowerCase();
	}


	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}


	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo.trim().toLowerCase();
	}
	
	public List<String> getListaCategorieSociali() {
		return listaCategorieSociali;
	}

	public void setListaCategorieSociali(List<String> listaCategorieSociali) {
		this.listaCategorieSociali = listaCategorieSociali;
	}

	public String getFiltroFrequenza() {
		return filtroFrequenza;
	}

	public void setFiltroFrequenza(String filtroFrequenza) {
		this.filtroFrequenza = filtroFrequenza;
	}

//	private void salvaDatiExportTest(String enteErogatore, String denominazEnte, String indirizzoEnte, String cfOperatore) throws CarSocialeServiceException { 
//
////		EntityManager em=null;
////		
////		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("test");
////		
////		em = entityManagerFactory.createEntityManager();
////	
////		em.getTransaction().begin();
//		 
//		CsIPsExportMast csIPsExportMast = new CsIPsExportMast();
// 
//		Date actualDate = new Date();
//
//		csIPsExportMast.setEnteErogatore("enteErogatore");
//		csIPsExportMast.setDenominazEnte("denominazEnte");
//		csIPsExportMast.setIndirizzoEnte("indirizzoEnte");
//		csIPsExportMast.setCfOperatore("nexus1");
//		csIPsExportMast.setFlusso("PSA");
//		csIPsExportMast.setDataInvio(null);
//		
//		BaseDTO progressivoCsIPsExportMastPSA = new BaseDTO();  
//		CsUiCompBaseBean.fillEnte(progressivoCsIPsExportMastPSA);
//		progressivoCsIPsExportMastPSA.setObj(csIPsExportMast.getEnteErogatore()); 
//		csIPsExportMast.setNumProgressivo(psExportService.getProgressivoCsIPsExportMastPSA(progressivoCsIPsExportMastPSA));   
//		csIPsExportMast.setDtIns(actualDate);
//		csIPsExportMast.setDtMod(actualDate);
//		
//		BaseDTO dto = new BaseDTO(); 
//		CsUiCompBaseBean.fillEnte(dto);
//		dto.setObj(csIPsExportMast);
//		csIPsExportMast = psExportService.saveCsIPsExportMast(dto);
//		
//		Set<CsIPsExport> csIPsExportList = new HashSet<CsIPsExport>();
//		csIPsExportMast.setCsIPsExportList(csIPsExportList);
//		
////		for (EsportazioneDTO esportazioneDTO : erogDaEsportareList) {
//			CsIPsExport psExport = new CsIPsExport();
// 
//			psExport.setCsIIntervento(null); 
//			psExport.setCsIInterventoEseg(null); 
//			CsIInterventoEsegMast csIInterventoEsegMast = new CsIInterventoEsegMast();
//			csIInterventoEsegMast.setId(361870);  
//			psExport.setCsIInterventoEsegMast(csIInterventoEsegMast); 
//			psExport.setCsIPsExportMast(csIPsExportMast); ;
//			psExport.setDtIns(actualDate); ;
//			psExport.setDtMod(actualDate); ; 
//			
//			csIPsExportList.add(psExport);
////		}  
// 
//			dto.setObj(csIPsExportMast);	
//			psExportService.saveCsIPsExportMast(dto);
//	
//	}
	
	//FINE MOD-RL 
}
