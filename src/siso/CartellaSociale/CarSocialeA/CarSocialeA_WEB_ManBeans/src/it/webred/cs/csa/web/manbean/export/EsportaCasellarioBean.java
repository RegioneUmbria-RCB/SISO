package it.webred.cs.csa.web.manbean.export;

import it.webred.cs.csa.ejb.client.AccessTableInterventoErogazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSinaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.csa.ejb.dto.EsportazioneTestataDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.sina.SinaEsegDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.CSIPs;
import it.webred.cs.data.DataModelCostanti.CSIPs.FLAG_IN_CARICO;
import it.webred.cs.data.DataModelCostanti.EsportazioneSIUSS;
import it.webred.cs.data.DataModelCostanti.EsportazioneSIUSS.FREQUENZA;
import it.webred.cs.data.DataModelCostanti.EsportazioneSIUSS.STATO;
import it.webred.cs.data.DataModelCostanti.ListaBeneficiari;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsDSinaLIGHT;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIInterventoEseg;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsIPsExportMast;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.VErogExportHelp;
import it.webred.cs.jsf.bean.erogazioneIntervento.ErogazioneInterventoBean;
import it.webred.cs.jsf.bean.erogazioneIntervento.InterventoErogazHistoryRowBean;
import it.webred.cs.jsf.manbean.SinaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@ViewScoped
public class EsportaCasellarioBean extends CsUiCompBaseBean {
	public static Logger logger = Logger.getLogger("carsociale.log");
	Format formatter = new SimpleDateFormat("dd/MM/yyyy");
	private CsOOperatoreSettore opSettore;
	private Boolean showDlgEsporta = true;

	private Boolean showPnlEsporta;
	private Boolean showPnlVisualizza;

	private Date dataDA;
	private Date dataA;
	private int numErogDaEsportare = 0;
	File tempFile;

	// SISO-738 filtri ricerca
	private String cf;
	private String nome;
	private String cognome;
	private String prestazione;
	private String areaTarget;
	private String numeroProtocollo;
	private String statoEsportazione;
	private String filtroFrequenza;
	private Long tipoIntervento;
	private Long tipoInterventoCustom;

	/*SISO-719 Refactoring, rimosse variabili globali per idFlusso (si usa solo quello vero) */
	// String idFlusso;
	// static final String idFlussoFake = "XXXX.XXX.XXXX.XXXXXXXX.XXXXXX"; // SISO-586

	protected AccessTablePsExportSessionBeanRemote psExportService = 
			(AccessTablePsExportSessionBeanRemote) getCarSocialeEjb("AccessTablePsExportSessionBean");

	/* SISO-738 */
	protected AccessTableInterventoSessionBeanRemote interventoService = 
			(AccessTableInterventoSessionBeanRemote) getCarSocialeEjb("AccessTableInterventoSessionBean");

	/* SISO-738 */
	protected AccessTableCatSocialeSessionBeanRemote catSocService = 
			(AccessTableCatSocialeSessionBeanRemote) getCarSocialeEjb("AccessTableCatSocialeSessionBean");
	/* SISO-784 */
	protected AccessTableSinaSessionBeanRemote sinaService = 
			(AccessTableSinaSessionBeanRemote) getCarSocialeEjb("AccessTableSinaSessionBean");

	/* SISO-886 */
	protected AccessTableInterventoErogazioneSessionBeanRemote erogInterventoService = 
			(AccessTableInterventoErogazioneSessionBeanRemote) getCarSocialeEjb("AccessTableInterventoErogazioneSessionBean");
	// ** mod. SISO-886 **//
	private ErogazioneInterventoBean erogazioneInterventoBean = new ErogazioneInterventoBean();
	// ** viene verificata e valorizzata al caricamento iniziale e poi viene
	// impostata a true se tutti i vincoli all'esportazione sono OK **//
	private Boolean esportazioneValida = true;

	/*
	 * SISO-719 aggiunti dei commenti per chiarire meglio il funzionamento del
	 * Bean
	 * 
	 * erogDaEsportareVisualizzazioneList contiene le righe che vengono
	 * visualizzate nella view - la corrispondenza è 1:1 con i contenuti di
	 * CS_I_INTERVENTO_ESEG erogDaEsportareList contiene le righe utilizzate per
	 * la composizione del messaggio XML - la corrispondenza è 1:1 con gli
	 * elementi PrestazioniSociali (più righe di CS_I_INTERVENTO_ESEG possono
	 * confluire in un elemento PrestazioniSociali) erogGiaInviate contiene le
	 * righe delle erogazioni che risultano già inviate. In precedenza non era
	 * una variabile globale, ma a fronte del refactoring (in particolare per
	 * costruire la lista erogTestataVisualizzazioneList) è stato utile
	 * portarla fuori erogTestataVisualizzazioneList contiene le righe che
	 * vengono visualizzate nella view, secondo la gerarchia a due livelli di
	 * testata e dettaglio - viene popolata a partire da
	 * erogDaEsportareVisualizzazioneList
	 */
	private List<EsportazioneDTO> erogDaEsportareList;
	private List<EsportazioneDTOView> erogDaEsportareVisualizzazioneList; // modifica SISO-538
	private List<EsportazioneDTOView> erogGiaInviateList; // SISO-719
	private List<EsportazioneTestataDTO> erogTestataVisualizzazioneList; // SISO-719
	private List<EsportazioneDTOView> erogDaEsportareVisualizzazioneConCodPrestazioneList;//SISO-1162
	
	private String avvisoErogazioniNonEsportate; // modifica SISO-538

	private List<SelectItem> listaCategorieSociali; // SISO-738
	private List<SelectItem> listaTipoIntervento; 		// SISO-1159
	private List<SelectItem> listaTipoInterventoCustom; // SISO-1159
	private List<SelectItem> listaStatoEsportazione;
	private List<SelectItem> listaFrequenza;

	private EsportazioneDTOView erogDaRevocare = null; // SISO-780

	private EsportazioneTestataDTO testataDaGestire = null; // SISO-882

	private EsportazioneDTOView dettaglioDaGestire; // SISO-882

	// @ManagedProperty(value="#{fascicoloBean}")
	// private FascicoloBean fascicoloBean; //SISO-882

	@ManagedProperty(value = "#{fglInterventoBean}")
	private FglInterventoBean fglInterventoBean;

	// SISO-871
	private List<EsportazioneTestataDTO> erogTestataNonEsportabiliList = new ArrayList<EsportazioneTestataDTO>(); // lista																				// esportabili
	private String widgetVar;

	protected static final DecimalFormat df;

	static { // SISO-606
		df = new DecimalFormat("#0.00");
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(custom);
	}
	
	public EsportaCasellarioBean() {
		fglInterventoBean = (FglInterventoBean) getBeanReference("fglInterventoBean");

		fglInterventoBean.setSinaMan(new SinaMan());
	}

	@PostConstruct
	public void init() {
		dataA = new Date();
		showPnlEsporta = false;
		showPnlVisualizza = false;
		opSettore = getCurrentOpSettore();
		statoEsportazione = DataModelCostanti.EsportazioneSIUSS.STATO.TUTTO.getCodice(); // SISO-738
		caricaCategorieSociali(); // SISO-738
		caricaTipiIntervento();
		
		listaStatoEsportazione = new ArrayList<SelectItem>();
			for(STATO s : EsportazioneSIUSS.STATO.values())
				listaStatoEsportazione.add(new SelectItem(s.getCodice(),s.getDescrizione()));
		
		listaFrequenza = new ArrayList<SelectItem>();
		for(FREQUENZA s : EsportazioneSIUSS.FREQUENZA.values())
			listaFrequenza.add(new SelectItem(s.getCodice(),s.getDescrizione()));
			
		dettaglioDaGestire = new EsportazioneDTOView();
		dettaglioDaGestire.setPresaInCarico(new BigDecimal(CSIPs.FLAG_IN_CARICO.NON_SO.getCodice()));
		// setTempPresaInCarico(new BigDecimal(2));

	}

	// SISO-738
	private void caricaCategorieSociali() {
		listaCategorieSociali = new ArrayList<SelectItem>();
		CeTBaseObject b = new CeTBaseObject();
		fillEnte(b);
		 List<CsCCategoriaSociale> categorieSocialiAll = catSocService.getCategorieSocialiAll(b);
		 for (CsCCategoriaSociale ccs : categorieSocialiAll) {
			SelectItem si = new SelectItem(ccs.getDescrizione().toLowerCase(), ccs.getDescrizione());
			listaCategorieSociali.add(si);
		}
	}
	
	private void caricaTipiIntervento() {
		listaTipoIntervento = new ArrayList<SelectItem>();
		listaTipoInterventoCustom = new ArrayList<SelectItem>();
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		b.setObj("STANDARD");
		List<KeyValueDTO> standard = confService.findTipiInterventoAbilitati(b);
		for (KeyValueDTO tipoInt : standard)
			listaTipoIntervento.add(new SelectItem(tipoInt.getCodice(), tipoInt.getDescrizione()));
		
		b.setObj("CUSTOM");
		List<KeyValueDTO> custom = confService.findTipiInterventoAbilitati(b);
		for (KeyValueDTO tipoInt : custom)
			listaTipoInterventoCustom.add(new SelectItem(tipoInt.getCodice(), tipoInt.getDescrizione()));
	}

	public void onDownloadRefresh() {
		logger.debug("onDownloadRefresh - Ricarico le liste");
		onBtnVerificaCasellarioClick();
	}
	
	// al click su "Verifica", di fatto il punto di partenza dell'interazione utente
	public Boolean onBtnVerificaCasellarioClick() {
		
		logger.debug("***onBtnVerificaCasellarioClick*** 1.Inizio setEsportazioneValida");
		
		this.setEsportazioneValida(true);
		
		logger.debug("***onBtnVerificaCasellarioClick*** 2.Inizio findErogDaEsportare");
		
		findErogDaEsportare(); //Valorizza: erogDaEsportareVisualizzazioneList e erogDaEsportareList
		
		logger.debug("***onBtnVerificaCasellarioClick*** 3.Inizio findErogGiaInviate");
		
		findErogGiaInviate();  //Valorizza: erogGiaInviateList
		
		logger.debug("***onBtnVerificaCasellarioClick*** 4.Inizio findErogPeriodiche");
		
		//SISO-780
		findErogPeriodiche(); //Valorizza: erogDaEsportareVisualizzazioneList
		
		//Deve essere richiamato a questo punto in cui erogDaEsportareList è definitiva altrimenti per le periodiche non ho il collegamento al SINA
		logger.debug("***onBtnVerificaCasellarioClick*** 5.Inizio loadCollegamentoSina erogDaEsportareList");
		loadCollegamentoSina(erogDaEsportareList); 
		
		logger.debug("***onBtnVerificaCasellarioClick*** 6.Inizio loadCollegamentoSina erogGiaInviateList");
		loadCollegamentoSina(erogGiaInviateList);
		
		/* SISO-719
		 * 
		 * Popolamento erogTestataVisualizzazioneList, effettuato a partire da erogDaEsportareVisualizzazioneList
		 * e erogGiaInviateList. */
		logger.debug("***onBtnVerificaCasellarioClick*** 7.Inizio costruisciListaTestateErogazione");
		erogTestataVisualizzazioneList =EsportaCasellarioUtils.costruisciListaTestateErogazione(erogDaEsportareVisualizzazioneList, erogGiaInviateList,erogDaEsportareList);
	
		logger.debug("***onBtnVerificaCasellarioClick*** 8.Inizio filtraRisultato");
		//SISO-738 applicazione filtri
		filtraRisultato();

		logger.debug("***onBtnVerificaCasellarioClick*** 9.Inizio calcolaNumErogDaEsportare");
		// SISO-738 aggiorno il numero dopo l'applicazione dei filtri
		numErogDaEsportare = calcolaNumErogDaEsportare(erogDaEsportareVisualizzazioneList);

		showPnlEsporta = true;
		showPnlVisualizza = true; // SISO-738

		logger.debug("***onBtnVerificaCasellarioClick*** 10.Inizio setErogTestataNonEsportabiliList");
		// SISO-871
		this.setErogTestataNonEsportabiliList(new ArrayList<EsportazioneTestataDTO>());

		logger.debug("***onBtnVerificaCasellarioClick*** 11.Inizio checkPresaInCarico");
		List<EsportazioneTestataDTO> result = CasellarioValidator.checkPresaInCarico(erogTestataVisualizzazioneList,getErogTestataNonEsportabiliList());
		if (result != null && result.size() > 0)
			setEsportazioneValida(false);

		return showPnlEsporta;
	}

	public boolean renderValidator(Long id){
		boolean res = false;

		List<EsportazioneTestataDTO> erogTestataNonEsportabiliList = this.getErogTestataNonEsportabiliList();
		if (erogTestataNonEsportabiliList != null && erogTestataNonEsportabiliList.size() > 0) {
			for (EsportazioneTestataDTO d : erogTestataNonEsportabiliList) {
				if (d.getInterventoEsegMastId().equals(id)) {
					res = true;
					break;
				}
			}
		}

		return res;
	}


	/**
	 * SISO-738 Il metodo in questione filtra i risultati ottenuti dalla query
	 * in base ai filtri selezionati metodo TOP-DOWN: prima viene effettuata la
	 * pulizia della lista che gestisce la tabella in view
	 * (erogTestataVisualizzazioneList) in base ai filtri poi vengono pulite
	 * anche le liste necessarie alla estrapolazione (erogDaEsportareList,
	 * erogDaEsportareVisualizzazioneList) in base ai CF presenti nella lista
	 * erogTestataVisualizzazioneList
	 */
	private void filtraRisultato() {
		Iterator<EsportazioneTestataDTO> it = erogTestataVisualizzazioneList.iterator();

		while (it.hasNext()) {

			EsportazioneTestataDTO el = it.next();

			if (
			// FILTRO CF
			   (!StringUtils.isBlank(cf) && !el.getSoggettoCodiceFiscale().toLowerCase().contains(cf))
			// FILTRO COGNOME
			|| (!StringUtils.isBlank(cognome) && !el.getSoggettoCognome().toLowerCase().contains(cognome))
			// FILTRO NOME
			|| (!StringUtils.isBlank(nome) && !el.getSoggettoNome().toLowerCase().contains(nome))
			// FILTRO DENOMINAZIONE PRESTAZIONE
			|| (!StringUtils.isBlank(prestazione) && (StringUtils.isBlank(el.getDenominazionePrestazione()) || !el.getDenominazionePrestazione().toLowerCase().contains(prestazione.trim().toLowerCase())))
			// FILTRO AREA TARGET
			|| (!StringUtils.isBlank(areaTarget) && !el.getCategoriaSocialeDescrizione().toLowerCase().contains(areaTarget))
			// FILTRO NUMERO PROTOCOLLO
			|| (!StringUtils.isBlank(numeroProtocollo) && (StringUtils.isBlank(el.getPrestazioneProtocolloEnte()) || !el.getPrestazioneProtocolloEnte().toLowerCase().equals(numeroProtocollo)))
			|| (this.tipoIntervento>0 	   && (el.getTipoInterventoId()==null 	  || !el.getTipoInterventoId().equals(this.tipoIntervento)))
			|| (this.tipoInterventoCustom>0 && (el.getTipoInterventoCustom()==null || !el.getTipoInterventoCustom().getId().equals(this.tipoInterventoCustom)))
			//FILTRO FREQUENZA
			|| (!StringUtils.isBlank(filtroFrequenza) && !filtroFrequenza.equals(el.getFrequenza()))
			// SISO-809 RIMUOVO LE EROGAZIONI DI GRUPPO
			|| ListaBeneficiari.GRUPPO.equals(el.getTipoBeneficiario())
			) {
				// rimuovo il nodo dalla lista se non rispetta i filtri
				it.remove();
			} else {
				// rimuovo i dettagli se il filtro è selezionato
				if (!statoEsportazione.equals(DataModelCostanti.EsportazioneSIUSS.STATO.TUTTO.getCodice())) {

					Iterator<EsportazioneDTOView> itd = el.getDettagli().iterator();
					while (itd.hasNext()) {
						EsportazioneDTOView eld = itd.next();
						if (statoEsportazione.equals(EsportazioneSIUSS.STATO.ESPORTATE.getCodice()) && !eld.isEsportata() 
						 || statoEsportazione.equals(EsportazioneSIUSS.STATO.DA_ESPORTARE.getCodice()) && eld.isEsportata()) {
							itd.remove();
						}
					}

					// se non ci sono più dettagli elimino la testata a cui è riferita
					if (el.getDettagli().isEmpty()) {
						it.remove();
					}
				}
			}
		}

		// pulisco la lista da esportare in base ai filtri
		Iterator<EsportazioneDTO> ite = erogDaEsportareList.iterator();
		while (ite.hasNext()) {
			EsportazioneDTO exp = ite.next();
			if (!esisteInterventoEsegMastIdinTestataView(exp.getInterventoEsegMastId())) {
				ite.remove();
			}
		}

		// pulisco la lista da esportare visualizzazione in base ai filtri
		Iterator<EsportazioneDTOView> itev = erogDaEsportareVisualizzazioneList.iterator();
		while (itev.hasNext()) {
			EsportazioneDTOView edv = itev.next();
			if (!esisteInterventoEsegMastIdinTestataView(edv.getInterventoEsegMastId())) {
				itev.remove();
			}
		}
	}

	// SISO-738 metodo ausiliario per pulizia lista in base all'InterventoEsegId
	private boolean esisteInterventoEsegMastIdinTestataView(Long intEsegMastId) {

		boolean trovato = false;

		for (EsportazioneTestataDTO et : erogTestataVisualizzazioneList) {

			List<EsportazioneDTOView> dettagli = et.getDettagli();
			for (EsportazioneDTOView dettaglio : dettagli) {
				if (dettaglio.getInterventoEsegMastId().equals(intEsegMastId)) {
					trovato = true;
					break;
				}
				break;
			}
		}
		return trovato;
	}

	private String getCodRoutingCorrente() {
		return opSettore.getCsOSettore().getCsOOrganizzazione().getCodRouting();
	}
	
	private String getCodExportFlussoCorrente(){
		return opSettore.getCsOSettore().getCsOOrganizzazione().getCodExportFlusso();
	}
	
	private String getCodSedeLegaleCorrente() {
		return opSettore.getCsOSettore().getCsOOrganizzazione().getCodSedeLegale();
	}
	
	private void findErogDaEsportare() {
		numErogDaEsportare = 0;

		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		fillEnte(bDto);
		bDto.setDataInizio(dataDA!=null ? formatter.format(dataDA) : null);
		bDto.setDataFine(dataA!=null ? formatter.format(dataA) : null);
		// bDto.setOperatoreId(opSettore.getId());
		bDto.setOrganizzazioneBelfiore(getCodRoutingCorrente());

		// INIZIO modifica SISO-538
		erogDaEsportareVisualizzazioneList = psExportService.findErogazDaInviareInPeriodo(bDto);

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(getIds(erogDaEsportareVisualizzazioneList));
		List<VErogExportHelp> listaVErogExportHelp = psExportService.findVErogExportHelp(dto);

		erogDaEsportareList =
				EsportaCasellarioUtils.filtraVErogExport(erogDaEsportareVisualizzazioneList, listaVErogExportHelp, psExportService);
		
		List<EsportazioneDTOView> listaErogazioniMasterChiusuraInPeriodo =  psExportService.findErogazioniMasterChiusuraInPeriodo(bDto);

		avvisoErogazioniNonEsportate =  EsportaCasellarioUtils.avvisoErogazioniNonEsportate(listaErogazioniMasterChiusuraInPeriodo,erogDaEsportareVisualizzazioneList);
		// FINE modifica SISO-538
	}

	private void findErogGiaInviate() {

		ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
		fillEnte(bDto);
		bDto.setDataInizio(dataDA!=null ? formatter.format(dataDA) : null);
		bDto.setDataFine(dataA!=null ? formatter.format(dataA) : null);
		// bDto.setOperatoreId(opSettore.getId());
		bDto.setOrganizzazioneBelfiore(getCodRoutingCorrente());

		// SISO-719 flussiInviati diventa variabile globale
		// List<EsportazioneDTOView> flussiInviati =
		// psExportService.findErogazGiaInviateInPeriodo(bDto);
		// numErogGiaInviate = flussiInviati.size();

		erogGiaInviateList = psExportService.findErogazGiaInviateInPeriodo(bDto);
		
		/*		//SISO-784 collegamento SINA --spostato a valle
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		List<Long> idsMast = EsportaCasellarioUtils.extractMastIdsDaView(erogGiaInviateList);
		if(idsMast.size() > 0){
			dto.setObj(idsMast);
			List<CsDSina> listaSinaErogInviate = sinaService.getSinaByMastIds(dto); 
	
			//SISO-784
			for(EsportazioneDTOView erogazione :erogGiaInviateList){
				for(CsDSina sina: listaSinaErogInviate){
					if(erogazione.getInterventoEsegMastId().equals(sina.getCsIInterventoEsegMast().getId())){
						erogazione.setIsSinaCollegato(true);
					}
				}
			}
		}*/
	}

	private void loadCollegamentoSina(List<? extends EsportazioneDTO> lista){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		List<Long> idsMast = EsportaCasellarioUtils.extractMastIds(lista);
		if(idsMast.size() > 0){
			dto.setObj(idsMast);
			HashMap<Long, CsDSinaLIGHT> mappaSina = sinaService.getSinaByMastIds(dto);

			for(EsportazioneDTO esportazione :lista){
				CsDSinaLIGHT sina = mappaSina.get(esportazione.getInterventoEsegMastId());
				if(sina!=null){
					esportazione.setIsSinaCollegato(true);
					if(!(esportazione instanceof EsportazioneDTOView))
						EsportaCasellarioUtils.caricaDatiSina(esportazione, sina);
				}
			}
		 }
	}
	
	// SISO-780
	public void findErogPeriodiche() {
		// garantisco l'univocità degli id
		Set<BigDecimal> mastIdsPeriodiche = new HashSet<BigDecimal>();

		// DA ESPORTARE
		Iterator<EsportazioneDTOView> evi = erogDaEsportareVisualizzazioneList.iterator();
		while (evi.hasNext()) {
			EsportazioneDTOView ev = evi.next();
			if(ev.getCarattere().equalsIgnoreCase(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)){
				mastIdsPeriodiche.add(new BigDecimal(ev.getInterventoEsegMastId()));
				evi.remove();
			}
		}
		Iterator<EsportazioneDTO> edit = erogDaEsportareList.iterator();
		while (edit.hasNext()) {
			EsportazioneDTO ex = edit.next();
			if (ex.getCarattere().equalsIgnoreCase(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)) {
				mastIdsPeriodiche.add(new BigDecimal(ex.getInterventoEsegMastId()));
				edit.remove();
			}
		}

		// ESPORTATE
		evi = erogGiaInviateList.iterator();
		while (evi.hasNext()) {
			EsportazioneDTOView ev = evi.next();
			if(ev.getCarattere().equalsIgnoreCase(DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO)){
				mastIdsPeriodiche.add(new BigDecimal(ev.getInterventoEsegMastId()));
				evi.remove();
			}
		}

		if (mastIdsPeriodiche.size() > 0) {
			ErogazioniSearchCriteria bDto = new ErogazioniSearchCriteria();
			fillEnte(bDto);
			bDto.setDataFine(formatter.format(dataA));
			bDto.setLstMasterId(new ArrayList<BigDecimal>(mastIdsPeriodiche));
			List<EsportazioneDTOView> periodiche =  psExportService.findErogazPeriodicheByMast(bDto);
			
			List<EsportazioneDTOView> daErogare = new ArrayList<EsportazioneDTOView>();
			List<EsportazioneDTOView> erogate = new ArrayList<EsportazioneDTOView>();
			for (EsportazioneDTOView ev : periodiche) {
				// esportate
				if (ev.getDataEsportazione() != null) erogate.add(ev);
				// da esportare
				else daErogare.add(ev);
			}

			erogGiaInviateList.addAll(erogate);
			erogDaEsportareVisualizzazioneList.addAll(daErogare);

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(getIds(daErogare));

			List<VErogExportHelp> listaVErogExportHelp = psExportService.findVErogExportHelp(dto);
			erogDaEsportareList.addAll(EsportaCasellarioUtils.filtraVErogExport(daErogare, listaVErogExportHelp, psExportService));
		}
	}
	
	private List<BigDecimal> getIds(List<EsportazioneDTOView> listaEsportazioneDTOview) {
		List<Long> listaIds = EsportaCasellarioUtils.extractMastIds(listaEsportazioneDTOview);
		Set<BigDecimal> ids = new HashSet<BigDecimal>();
		for (Long id : listaIds)
			ids.add(new BigDecimal(id));
		return new ArrayList<BigDecimal>(ids);
	}

	public Boolean onBtnResetClick() {
		showPnlEsporta = false;
		showPnlVisualizza = false;
		dataDA = null;

		// SISO-738
		cf = null;
		cognome = null;
		nome = null;
		prestazione = null;
		areaTarget = null;
		numeroProtocollo = null;
		statoEsportazione = DataModelCostanti.EsportazioneSIUSS.STATO.TUTTO.getCodice();
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

		return onBtnVerificaCasellarioClick(); // refresh e return
	}

	// SISO-882
	public boolean gestioneEsportazioneClick() {
		boolean saved = false;
		try{
		BigDecimal temp = getDettaglioDaGestire().getPresaInCarico();
		List<String> msg = null;
		String s = "";
		// se valore diverso da non so, salvo
		if (temp.intValue()!=DataModelCostanti.CSIPs.FLAG_IN_CARICO.NON_SO.getCodice()) {
			
			// aggiorno flag erogazione sottesa
			getDettaglioDaGestire().setPresaInCarico(temp);
			msg = updateDatiMancanti(getDettaglioDaGestire());
		    saved = msg==null || msg.isEmpty();
			
			s += msg!=null  ? "- SINA:"+msg+"<br/>" : "";
	
		}else{
			s+= "- Specificare se, durante il servizio, il beneficiario è in carico";
			saved=false;
		}
		
		
		if(!saved)
			this.addError("Inserire i dati obbligatori", s);
		else{
			onBtnVerificaCasellarioClick();	
			RequestContext.getCurrentInstance().addCallbackParam("saved", saved);	
		}
		
		} catch (Exception e) {
			saved = false;
			addErrorDialogFromProperties("salva.error");
			logger.error(e.getMessage(), e);
		}
		
		return saved;
	}

	public StreamedContent getFile2015() {
		InputStream stream = null;
		StreamedContent file = null;
		try {
			CsIPsExportMast exportMast = onBtnEsportaCasellarioClick(SchemaVersion.PSA_2015);
			stream = new FileInputStream(tempFile);
			file = new DefaultStreamedContent(stream, "text/xml", exportMast.getIdentificazioneFlusso() + ".xml");
		} catch (FileNotFoundException e) {
			addError("Esportazione fallita", "errore nel recupero del file xml");
			logger.error("Esportazione fallita",e);
		} catch (Exception e) {
			addError("Esportazione fallita", "la procedura di esportazione ha segnalato un errore");
			logger.error("Esportazione fallita",e);
		}
		return file;
	}

	public StreamedContent getFile() {
		InputStream stream = null;
		StreamedContent file = null;
		try {
			// SISO-780
			logger.debug("Avvio esportazione file SIUSS");
			gestionePeriodiche();

			CsIPsExportMast exportMast = onBtnEsportaCasellarioClick(SchemaVersion.PSA_2018_PS_SINA);
			stream = new FileInputStream(tempFile);
			file = new DefaultStreamedContent(stream, "text/xml", exportMast.getIdentificazioneFlusso() + ".xml");
			
			 //Refresh caricamento dati
			onDownloadRefresh();
		} catch (FileNotFoundException e) {
			addError("Esportazione fallita", "errore nel recupero del file xml");
			logger.error("Esportazione fallita", e);
		} catch (Exception e) {
			addError("Esportazione fallita", "la procedura di esportazione ha segnalato un errore: "+e.getMessage());
			logger.error("Esportazione fallita",e);
		}
		return file;
	}

	public CsIPsExportMast onBtnEsportaCasellarioClick(SchemaVersion schemaVersion) throws Exception {
		CsIPsExportMast exportMasterEntity = null; // fallback value
		
		logger.debug("*** onBtnEsportaCasellarioClick erogDaEsportareList["+erogDaEsportareList.size()+"] esportazioneValida["+getEsportazioneValida()+"]");
		if (erogDaEsportareList.size() > 0 && getEsportazioneValida()) {

			/*
			 * Validazione esportazione: devono essere presenti i dati DSU: anno, data e protocollo o nessuno di essi
			 */
			for (EsportazioneDTO es : erogDaEsportareList) {
				if (!((es.getDataDSU() != null && (es.getAnnoProtDSU() != null && es.getAnnoProtDSU() > 0) && (es.getNumProtDSU() != null && !es.getNumProtDSU().isEmpty())) || 
					  (es.getDataDSU() == null && (es.getAnnoProtDSU() == null || es.getAnnoProtDSU().equals(new Integer(0))) && (es.getNumProtDSU() == null || es.getNumProtDSU().isEmpty()))
				)) {
					throw new Exception( "controllare i campi anno, data e protocollo DSU");
				}
			}

			if (getErogTestataNonEsportabiliList() != null && getErogTestataNonEsportabiliList().size() > 0)
				for (EsportazioneTestataDTO es : getErogTestataNonEsportabiliList()) {
					{
					    throw new Exception("controllare campo preso in carico nell'esportazione: "+es.getInterventoEsegMastId().toString());
					}

			}

			tempFile = File.createTempFile("EsportaCasellarioBean", "");

			CsOOrganizzazione csOOrganizzazione = opSettore.getCsOSettore().getCsOOrganizzazione();

			String codExportFlusso = this.getCodExportFlussoCorrente();
			if(codExportFlusso==null) logger.warn("L'ente corrente non ha un COD_EXPORT_FLUSSO: nell'export verrà utilizzato il codice fittizio (COD_ROUTING)");
			
			String codSedeLegale = this.getCodSedeLegaleCorrente();
			if(codSedeLegale==null) logger.warn("L'ente corrente non ha un COD_SEDE_LEGALE: nell'export verrà utilizzato il codice fittizio (COD_ROUTING)");
			
			
			String codEnte = codExportFlusso!=null ? codExportFlusso : getCodRoutingCorrente();
			String denomEnte = csOOrganizzazione.getNome();
			String cfOperatore = opSettore.getCsOOperatore().getCsOOperatoreAnagrafica().getCodiceFiscale();
			String indirEnte = codSedeLegale!=null ? codSedeLegale : getCodRoutingCorrente(); // SISO-626
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
			exportMasterEntity = preparaEntityExport(codEnte, denomEnte, indirEnte, cfOperatore, actualDate, erogDaEsportareVisualizzazioneList);
						
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
		}else {
			logger.debug("*** onBtnEsportaCasellarioClick: niente da esportare");
			throw new Exception("nessuna erogazione da esportare");
		}

		return exportMasterEntity;
	}

	// SISO-719 vecchia firma del metodo
	// /**
	// * restituisce il progressivo dell'elaborazione
	// * @param enteErogatore
	// * @param denominazEnte
	// * @param indirizzoEnte
	// * @param cfOperatore
	// * @param actualDate
	// * @throws CarSocialeServiceException
	// */
	// private long salvaDatiExport(String enteErogatore, String denominazEnte,
	// String indirizzoEnte, String cfOperatore, Date actualDate,
	// List<EsportazioneDTOView> erogazioni) throws CarSocialeServiceException {

	private CsIPsExportMast preparaEntityExport(String enteTitolare,
			String denominazEnte, String indirizzoEnte, String cfOperatore,
			Date actualDate, List<EsportazioneDTOView> erogazioni)
			throws CarSocialeServiceException {

		logger.debug("Inizio preparaEntityExport");
		
		// Entity dell'export della testata erogazione (andrà in CS_I_PS_EXPORT_MAST)
		CsIPsExportMast csIPsExportMast = new CsIPsExportMast(); 
		
		csIPsExportMast.setEnteTitolare(enteTitolare);
		csIPsExportMast.setDenominazEnte(denominazEnte);
		csIPsExportMast.setIndirizzoEnte(indirizzoEnte);
		csIPsExportMast.setCfOperatore(cfOperatore);
		csIPsExportMast.setFlusso("PSA"); // costante
		csIPsExportMast.setDataInvio(actualDate);

		// generazione e salvataggio del NumeroProgressivo
		BaseDTO progressivoCsIPsExportMastPSA = new BaseDTO();
		CsUiCompBaseBean.fillEnte(progressivoCsIPsExportMastPSA);
		progressivoCsIPsExportMastPSA.setObj(csIPsExportMast.getEnteTitolare());
		csIPsExportMast.setNumProgressivo(psExportService.getProgressivoCsIPsExportMastPSA(progressivoCsIPsExportMastPSA));

		// generazione e salvataggio dell'idFlusso (IdentificazioneFlusso.Nome nell'XML da generare)
		String idFlusso = creaIdFlusso(enteTitolare, actualDate, csIPsExportMast.getNumProgressivo());
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
		// dto.setObj(csIPsExportMast);
		// psExportService.saveCsIPsExportMast(dto);

		// return csIPsExportMast.getNumProgressivo();
		return csIPsExportMast;
	}

	// SISO-780
	public void gestionePeriodiche() {

		boolean gestitePeridiche = false;
		logger.debug("Avvio gestione erogazioni periodiche");
		
		for (EsportazioneTestataDTO etdto : erogTestataVisualizzazioneList) {
			/*SISO-943 SOSTITUITO CON CS_I_PS.CARATTERE PER GESTIRE ANCHE LE IRREGOLARI COME PERIODICHE*/
			// if (etdto.getFrequenza().equalsIgnoreCase(etdto.FREQUENZA_REGOLARE)) { SISO-943 SOSTITUITO CON CS_I_PS.CARATTERE PER GESTIRE ANCHE LE IRREGOLARI COME PERIODICHE
			if (DataModelCostanti.CSIPs.CARATTERE_PRESTAZIONE_DI_TIPO_PERIODICO.equalsIgnoreCase(etdto.getCarattere())) { 
				int daGestire = 0;

				for (EsportazioneDTOView ev : etdto.getDettagli()) {
					if (ev.isDaInviare()) {
						daGestire++;
					}
				}

				// gestisco solo se ho almeno più di una erogazione da inviare
				if (daGestire > 0 && etdto.getDettagli().size() > 1) {
					gestitePeridiche = true;
					// revoca tutte e aggiunge il resto a quelle da erogare
					BaseDTO dto = new BaseDTO();
					fillEnte(dto);
					dto.setObj(etdto.getInterventoEsegMastId());
					
					//vengono revocate TUTTE le eorgazioni associate alla MAST anche quelle fuori dal range di date selezionato
					logger.debug("Gestione periodiche: revoco erogazioni associate alla MAST["+etdto.getInterventoEsegMastId()+"]");
					psExportService.updateCsIPsExportRevocaEsportazioneByInterventoEsegMastId(dto);
				}
			}
		}
		
		//se sono state gestite almeno una periodica, ricarico le liste per l'esportazione
		if(gestitePeridiche){
			logger.debug("Sono state gestite erogazioni periodiche: ricarico le liste per l'esportazione");
			onBtnVerificaCasellarioClick();
		}
	}

	public void impostaErogDaRevocare(EsportazioneDTOView erog) {
		this.erogDaRevocare = erog;
	}

	// SISO 882
	public void impostaTestataDaGestire(EsportazioneTestataDTO test) {
		this.testataDaGestire = test;
		this.dettaglioDaGestire = getTestataDaGestire().getDettagli().get(0);

		BaseDTO dto = new BaseDTO();
		dto.setObj(getDettaglioDaGestire().getInterventoEsegMastId());
		fillEnte(dto);
		List<CsIInterventoEsegMastSogg> soggs = this.interventoService.getBeneficiari(dto);
		CsIInterventoEsegMastSogg sogg = soggs.get(0);
		SoggettoErogazioneBean soggettoErogazioneSelezionato = new SoggettoErogazioneBean(sogg);
		
		fglInterventoBean.inizializzaErogazione(soggettoErogazioneSelezionato);
		fglInterventoBean.setVisualizzaSinaTab(isVisualizzaSinaTab(dettaglioDaGestire.getCategoriaSocialeId()));
		
		CsASoggettoLAZY s = soggettoErogazioneSelezionato.getCsASoggetto();
		Long casoId = s != null ? s.getCsACaso().getId() : null;
		
		String cf = soggettoErogazioneSelezionato.getCf()!=null ? soggettoErogazioneSelezionato.getCf() : ""; //SISO-928		
		
		fglInterventoBean.setSinaMan(buildSinaMan(casoId, getDettaglioDaGestire().getInterventoEsegMastId(), cf));

	}
	
	private boolean isVisualizzaSinaTab(BigDecimal catSocId){
		boolean val =  (DataModelCostanti.TipiCategoriaSociale.ANZIANI_ID.equals(catSocId.longValue()) ||
				DataModelCostanti.TipiCategoriaSociale.DISABILI_ID.equals(catSocId.longValue()));
		return val;
	}

	// ** mod. SISO-886 **//
	// ** estraggo il più recente SINA eventualmente presente **//
	// ** se non presente o presente ma già esportato, l'utente ne dovrà creare
	// uno nuovo da esportare **//
	private SinaMan buildSinaMan(Long casoId, Long mastId, String cf) {
		List<SinaEsegDTO> sinaList = null;
		Boolean lastErogEsportata = false;
		Boolean renderValutaDopo = false;
		
		SinaMan sinaMan = new SinaMan(casoId, mastId,cf, lastErogEsportata, renderValutaDopo);
		if (sinaMan != null && sinaMan.getSinaCollegati().size() > 0) {
			sinaList = sinaMan.getSinaCollegati();
			
			// ** ordino i sina per data descending **//
			Collections.sort(sinaList, new Comparator<SinaEsegDTO>() {
				@Override
				public int compare(SinaEsegDTO left, SinaEsegDTO right) {
					Date oggi = new Date();
					Date rightData = right.getData()!=null ? right.getData() : oggi;
					Date leftData = left.getData()!=null ? left.getData() : oggi;
					return rightData.compareTo(leftData);
				}
			});
			
			// ** prendo il sina più recente (non esportato) **//
			CsIPsExport lastExp = getLastErogazioneEsportata(mastId);
			boolean trovato = false;
			List<SinaEsegDTO> listOut = new ArrayList<SinaEsegDTO>();
			int i = 0;
			while(!trovato && i<sinaList.size()){
				SinaEsegDTO s = sinaList.get(i);
				if (lastExp==null || s.getData().after(lastExp.getDtExport())) {
					listOut.add(s);
					trovato = true;
				}
				i++;
			}
			
			if (!trovato) {
				// ** creo nuovo sina **//
				sinaMan = new SinaMan(casoId, new Long(0), cf, lastErogEsportata, renderValutaDopo);
			}else {
				sinaMan.setSinaCollegati(listOut);
				sinaMan.setSovrascriviSinaCorrente(true);
			}
		} else {
			// ** creo nuovo sina **//
			sinaMan = new SinaMan(casoId, new Long(0), cf, lastErogEsportata, renderValutaDopo);
		}

		return sinaMan;
	}
	
	public CsIPsExport getLastErogazioneEsportata(Long intEsegMastId) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);		
		dto.setObj(intEsegMastId);
		List<CsIPsExport> exps = psExportService.findCsIPsExportByCsIInterventoMastIdExported(dto);
		return exps!=null && exps.size()> 0 ? exps.get(0) : null;

	}

	public List<String> updateDatiMancanti(EsportazioneDTOView dettaglio) {
		// trovo CsIInterventoEsegMast;
		List<String> msg = new ArrayList<String>();
		Long currentIntEsegMastId = dettaglio.getInterventoEsegMastId().longValue();
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(currentIntEsegMastId);

		CsIInterventoEsegMast interventoEsegMast = interventoService.getCsIInterventoEsegMastById(dto);
		// modifico con valore flag preso in carico nuovo
		interventoEsegMast.getCsIPs().setFlagInCarico(getDettaglioDaGestire().getPresaInCarico().intValue());
		
		dto.setObj(interventoEsegMast);
		interventoEsegMast = interventoService.salvaInterventoEseguitoMast(dto);

		// ** mod. SISO-886 **//
		SinaMan sina = fglInterventoBean.getSinaMan();
		if(dettaglio.getPresaInCarico().intValue()==FLAG_IN_CARICO.SI.getCodice() && fglInterventoBean.getVisualizzaSinaTab()){
			if (sina != null) {
				// ** sto operando da "Esporta casellario" e devo togliere di forza
				// il "Flag valuta dopo" per completare l'esportazione **//
				sina.getSinaDTO().setFlagValutaDopo(false);
				
				Iterator<CsIInterventoEseg> it = interventoEsegMast.getCsIInterventoEsegs().iterator();
				List<InterventoErogazHistoryRowBean> lstRows = new ArrayList<InterventoErogazHistoryRowBean>();
				while(it.hasNext()){
					InterventoErogazHistoryRowBean row = new InterventoErogazHistoryRowBean();
					CsIInterventoEseg eseg = (CsIInterventoEseg)it.next();
					row.setStato(eseg.getStato());
					row.setDataErogazione(eseg.getDataEsecuzione());
					row.setDataErogazioneA(eseg.getDataEsecuzioneA());
					row.setDataEvento(eseg.getDataEvento());
					
					dto.setObj(eseg.getId());
					boolean esportata = psExportService.verificaErogazioneEsportataByEsegId(dto);
					row.setEsportata(esportata);
					
					lstRows.add(row);
				}
				
				msg = sina.validaSinaErogazione(getDettaglioDaGestire().getPresaInCarico().intValue(), lstRows);
				if (msg.isEmpty()){
					boolean saved = sina.salvaDaFglIntervento(interventoEsegMast.getId());
					if(!saved) msg.add("Errore salvataggio SINA");
				}
			}else msg.add("Compilare dati");
		}
		return msg;
	}
	
	// ** mod. SISO-886 **//
	public void addMessage(FacesMessage.Severity tipoMessaggio, String summary) {
		FacesMessage message = new FacesMessage(tipoMessaggio, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	public EsportazioneTestataDTO getTestataDaGestire() {
		return testataDaGestire;
	}

	public EsportazioneDTOView getDettaglioDaGestire() {
		return dettaglioDaGestire;
	}

	public void setDettaglioDaGestire(EsportazioneDTOView dettaglioDaGestire) {
		this.dettaglioDaGestire = dettaglioDaGestire;
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
		return DataModelCostanti.EsportazioneSIUSS.STATO.ESPORTATE.getCodice().equals(this.statoEsportazione) || 
			   numErogDaEsportare == 0;
	}

	private int calcolaNumErogDaEsportare( List<EsportazioneDTOView> erogDaEsportareVisualizzazioneList) {
		int numErogDaEsportare = 0;
		for (EsportazioneDTOView esportazioneDTOView : erogDaEsportareVisualizzazioneList) {
				if (esportazioneDTOView.isDaInviare()) {
					numErogDaEsportare++;
				}else{
					logger.debug("calcolaNumErogDaEsportare NON INVIARE "+esportazioneDTOView.getSoggettoCodiceFiscale());
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
		this.prestazione = prestazione;
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
		this.statoEsportazione = statoEsportazione;
	}

	public String getNumeroProtocollo() {
		return numeroProtocollo;
	}

	public void setNumeroProtocollo(String numeroProtocollo) {
		this.numeroProtocollo = numeroProtocollo.trim().toLowerCase();
	}

	public List<SelectItem> getListaCategorieSociali() {
		return listaCategorieSociali;
	}

	public void setListaCategorieSociali(List<SelectItem> listaCategorieSociali) {
		this.listaCategorieSociali = listaCategorieSociali;
	}

	public String getFiltroFrequenza() {
		return filtroFrequenza;
	}

	public void setFiltroFrequenza(String filtroFrequenza) {
		this.filtroFrequenza = filtroFrequenza;
	}

	// SISO-871

	public List<EsportazioneTestataDTO> getErogTestataNonEsportabiliList() {
		return erogTestataNonEsportabiliList;
	}

	public void setErogTestataNonEsportabiliList(
			List<EsportazioneTestataDTO> erogTestataNonEsportabiliList) {
		this.erogTestataNonEsportabiliList = erogTestataNonEsportabiliList;
	}

	public String getWidgetVar() {
		widgetVar = "gestEsportDialog";
		return widgetVar;
	}

	public FglInterventoBean getFglInterventoBean() {
		return fglInterventoBean;
	}

	public void setFglInterventoBean(FglInterventoBean fglInterventoBean) {
		this.fglInterventoBean = fglInterventoBean;
	}
	
	public Boolean getEsportazioneValida() {
		return esportazioneValida;
	}

	public void setEsportazioneValida(Boolean esportazioneValida) {
		this.esportazioneValida = esportazioneValida;
	}

	public Long getTipoIntervento() {
		return tipoIntervento;
	}

	public void setTipoIntervento(Long tipoIntervento) {
		this.tipoIntervento = tipoIntervento;
	}

	public Long getTipoInterventoCustom() {
		return tipoInterventoCustom;
	}

	public void setTipoInterventoCustom(Long tipoInterventoCustom) {
		this.tipoInterventoCustom = tipoInterventoCustom;
	}

	public List<SelectItem> getListaTipoIntervento() {
		return listaTipoIntervento;
	}

	public void setListaTipoIntervento(List<SelectItem> listaTipoIntervento) {
		this.listaTipoIntervento = listaTipoIntervento;
	}

	public List<SelectItem> getListaTipoInterventoCustom() {
		return listaTipoInterventoCustom;
	}

	public void setListaTipoInterventoCustom(
			List<SelectItem> listaTipoInterventoCustom) {
		this.listaTipoInterventoCustom = listaTipoInterventoCustom;
	}

	public List<SelectItem> getListaStatoEsportazione() {
		return listaStatoEsportazione;
	}

	public void setListaStatoEsportazione(List<SelectItem> listaStatoEsportazione) {
		this.listaStatoEsportazione = listaStatoEsportazione;
	}

	public List<SelectItem> getListaFrequenza() {
		return listaFrequenza;
	}

	public void setListaFrequenza(List<SelectItem> listaFrequenza) {
		this.listaFrequenza = listaFrequenza;
	}
	
}
