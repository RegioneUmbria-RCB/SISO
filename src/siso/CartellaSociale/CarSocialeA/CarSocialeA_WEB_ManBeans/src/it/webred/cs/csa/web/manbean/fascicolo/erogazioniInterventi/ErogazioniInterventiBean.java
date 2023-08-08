package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.ejb.dto.pai.CsPaiMastSoggDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.InserimentoConsuntivazioneDTO;
import it.webred.cs.csa.ejb.dto.pai.pti.StrutturaDisponibilitaDTO;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoRicercaSoggetto;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.UserSearchBeanExt;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.jsf.bean.ComuneBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;

@ManagedBean
@ViewScoped
public class ErogazioniInterventiBean extends CsUiCompBaseBean {

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb ("AccessTableInterventoSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb ("AccessTableSoggettoSessionBean");
  
	@ManagedProperty(value = "#{lazyListaErogazioniModel}")
	private LazyListaErogazioniModel lazyListaErogazioniModel;

	@ManagedProperty( value = "#{fglInterventoBean}")
	private FglInterventoBean fglInterventoBean;
	
	private DatiUserSearchBean nuovoSoggetto;
	
	private TipoInterventoManBean tipoIntTreeView;
	private Long idIntervento = 0L;
	private Long idDiario = 0L;
	private String tipoIntCustomName = "";
	private Long tipoInterventoId = 0L;
	private List<SelectItem> tipoInterventosAll;
	private List<SelectItem> tipoInterventosRecenti;
	private List<SelectItem> tipoInterventosCustom;
	private List<SelectItem> tipoInterventosInps;
	private List<SelectItem> listaTipoFiltroInterventi;
	
	private String idRow;

	private SoggettoErogazioneBean soggettoErogazioneSelezionato;
	private Boolean provenienteDaFascicolo = false;
	
	protected boolean isTreeViewIntervento; //SISO-1110
	
	@PostConstruct
	public void init() {
		logger.debug("ErogazioniInterventiBean: init()");
		fglInterventoBean = (FglInterventoBean)getBeanReference("fglInterventoBean");
		init( DataModelCostanti.ListaErogazioni.TUTTI );
	}

	private void init(int defaultTipoFiltroIntervento ){
		logger.debug("INIT ErogazioniInterventiBean");
		listaTipoFiltroInterventi = new ArrayList<SelectItem>();
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.TUTTI, "Tutti"));
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.CON_RICHIESTA, "Richiesti"));
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.SENZA_RICHIESTA, "Erogati senza richiesta "));

		lazyListaErogazioniModel = new LazyListaErogazioniModel(defaultTipoFiltroIntervento);
		
		loadTipoInterventiAbilitati();
		loadTipoInterventiInps();
		loadTipoInterventiRecenti();
		
		//SISO-745
		//la componente treeview se provengo dal fascicolo è presente nel bean InterventiBean, non va caricata di nuovo
		if(!provenienteDaFascicolo){
			this.tipoIntTreeView = new TipoInterventoManBean(tipoInterventosAll,"");	
		}
		
		tipoInterventoId = 0L;
		// rowIndex = null;
		idRow = null;
		logger.debug("END INIT ErogazioniInterventiBean");
	}
	
	public void SetFromFascicolo(CsASoggettoLAZY soggetto)
	{
		provenienteDaFascicolo = true;
		CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
		String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
		CsAAnagrafica ana = soggetto.getCsAAnagrafica();
		String jsonComuneNascita = getJsonNascitaComuneBean(soggetto);
		soggettoErogazioneSelezionato = new SoggettoErogazioneBean(soggetto,via, getCasoComuneResidenza(residenza), residenza.getStatoCod(), jsonComuneNascita, true);
		lazyListaErogazioniModel.setSelectedSoggettoErogazioneCF(soggettoErogazioneSelezionato.getCf());
		
		//init();
	}

	
	  public void SetFromPai(CsASoggettoLAZY soggetto) { 
		  provenienteDaFascicolo = false; 
		  CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
		  String via = residenza!=null ? residenza.getLabelIndirizzo() : null; String
		  jsonComuneNascita = getJsonNascitaComuneBean(soggetto);
		  soggettoErogazioneSelezionato = new SoggettoErogazioneBean(soggetto,via, getCasoComuneResidenza(residenza), residenza.getStatoCod(),jsonComuneNascita, true);
		  lazyListaErogazioniModel.setSelectedSoggettoErogazioneCF(soggettoErogazioneSelezionato.getCf()); //init();
		  this.fglInterventoBean.setFromPai(Boolean.TRUE); 
	}
	 	
	public void SetFromPai(CsPaiMastSoggDTO soggettoPai)
	{
		provenienteDaFascicolo = false;
		soggettoErogazioneSelezionato = new SoggettoErogazioneBean(soggettoPai);
		lazyListaErogazioniModel.setSelectedSoggettoErogazioneCF(soggettoErogazioneSelezionato.getCf());
		//init();
		fglInterventoBean.setFromPai(Boolean.TRUE);
	}
	
	public void inizializzaSoggettoDialog(DatiUserSearchBean sbean){
		PersonaDettaglio p  = sbean!=null ? sbean.getSoggetto() : null;
		if(p!=null) {
			String titleBlocco = "Non è possibile creare una nuova erogazione";
			String msgBlocco = "Il soggetto selezionato è";
			if (p.isDefunto() && CsUiCompBaseBean.isBloccaUtentiDefunti()) {
				addWarning(titleBlocco,msgBlocco +" deceduto" + (p.getDataMorte()!=null ? "il "+ddMMyyyy.format(p.getDataMorte()) : ""));
				return;
			}
		}
		this.nuovoSoggetto=sbean;
		this.inizializzaDialogo(null, null);
	}
	
	protected void loadSoggettoErogazioneSelezionato() {
		ObjectMapper om = new ObjectMapper();
		DatiUserSearchBean sbean = this.nuovoSoggetto;
		String idPersonaSelezionata = sbean!=null ? sbean.getId() : null;
		ComuneNazioneNascitaMan comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
		if (StringUtils.isNotEmpty(idPersonaSelezionata)) {
				PersonaDettaglio p = null;
				if(sbean.getSoggetto()!=null)
				  p = (PersonaDettaglio)sbean.getSoggetto();
				
				//Provenendo dall'anagrafe marche e diogene i dati sono già completi, non serve richiamare il WS
				if(sbean.isAnagrafeSanitariaUmbria())
					p = getPersonaDaAnagEsterna(TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, idPersonaSelezionata.replace(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, ""));
				if(sbean.isAnagrafeSigess())
					p = getPersonaDaAnagEsterna(TipoRicercaSoggetto.SIGESS, idPersonaSelezionata.replace(DataModelCostanti.TipoRicercaSoggetto.SIGESS, ""));
				
			if (p != null) {
				comuneNazioneNascitaMan.init(p.getComuneNascita(), p.getNazioneNascita());
				String jsonNascita = comuneNazioneNascitaMan.isComune()? comuneNazioneNascitaMan.getComuneMan().getComuneAsJson() : null;
				AmTabNazioni nazioneNascita = comuneNazioneNascitaMan.isNazione() ? comuneNazioneNascitaMan.getNazioneMan().getNazione(): null;
				String nazNascitaCod = nazioneNascita!=null ? nazioneNascita.getCodIstatNazione() : null;
				
				
				
				String jsonResidenza=null;
				if(p.getComuneResidenza()!=null){
					ComuneBean comuneResidenza = new ComuneBean(p.getComuneResidenza());
					try {
						jsonResidenza = om.writeValueAsString(comuneResidenza);
					} catch (JsonProcessingException e1) {}
				}
				String nazioneResidenza = p.getNazioneResidenza()!=null ? p.getNazioneResidenza().getCodIstatNazione() : null;
				soggettoErogazioneSelezionato = new SoggettoErogazioneBean(p.getCognome(), p.getNome(), p.getCodfisc(), p.getCittadinanza(), p.getDataNascita(), p.getIndirizzoCivicoResidenza(), jsonResidenza, nazioneResidenza, jsonNascita, nazNascitaCod, p.getSesso(),  true);
				
				//SISO-962 fine
			}
				
		   /*Verifico la  corrispondenza dei dati anagrafici con quelli del caso*/		
			String codFisPersona = (p!=null ? p.getCodfisc() : null);
			CsASoggettoLAZY soggetto = this.getSchedaCSByCF(codFisPersona);
			if (soggetto != null){
				CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
				String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
				String nazResidenza = residenza!=null ? residenza.getStatoCod() : null;
				String jsonComuneNascita = getJsonNascitaComuneBean(soggetto);
				
				soggettoErogazioneSelezionato.integraDatiMancanti(soggetto, via, getCasoComuneResidenza(residenza), nazResidenza, jsonComuneNascita);
			}
			
		}else
			soggettoErogazioneSelezionato = null;
		
		UserSearchBeanExt ubean = (UserSearchBeanExt)getReferencedBean("userSearchBeanExt");
		ubean.clearParameters();
		nuovoSoggetto=null;
	}

	private void loadTipoInterventiRecenti(){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(tipoInterventosRecenti==null){
			this.tipoInterventosRecenti = new LinkedList<SelectItem>();
			List<KeyValueDTO> lstTipoInterventiRecenti = confService.findTipiInterventoRecenti(dto);
			for (KeyValueDTO i : lstTipoInterventiRecenti)
				this.tipoInterventosRecenti.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));
		}
		if(tipoInterventosCustom==null){
			this.tipoInterventosCustom = new LinkedList<SelectItem>();
			List<KeyValueDTO> lstTipoInterventiCustom = confService.findTipiInterventoCustomRecenti(dto);
			for (KeyValueDTO i : lstTipoInterventiCustom)
				this.tipoInterventosCustom.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));
		}
			
	}
	
	protected void loadTipoInterventiAbilitati() {

		if(tipoInterventosAll == null){
			this.tipoInterventosAll = new LinkedList<SelectItem>();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			logger.debug("START loadTipoInterventiAbilitati");
			List<KeyValueDTO> lstTipoInterventi = confService.findTipiInterventoAbilitati(dto);
			logger.debug("END loadTipoInterventiAbilitati");
			this.tipoInterventosAll = convertiLista(lstTipoInterventi);
		}
	}
	
	protected void loadTipoInterventiInps(){
		if(tipoInterventosInps==null){
			this.tipoInterventosInps = new LinkedList<SelectItem>(); //SISO-1162
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			logger.debug("START loadTipoInterventiInps");
			List<KeyValueDTO> lstTipoInterventiInps = confService.findTipiInterventoInps(dto);//SISO-1162
			logger.debug("END loadTipoInterventiInps");
			for (KeyValueDTO i : lstTipoInterventiInps)
		           this.tipoInterventosInps.add(new SelectItem((String)i.getCodice(), (String)i.getCodice() + " - " + i.getDescrizione()));
			this.tipoInterventosInps.add(new SelectItem( "Non definito", "Non definito"));
		}
	}
	
	public void inizializzaDialogoByTreeView(TipoInterventoManBean tipoIntTreeView, StrutturaDisponibilitaDTO struttDisp){
		this.tipoInterventoId = tipoIntTreeView.getSelTipoInterventoId();
		if (tipoInterventoId == null || tipoInterventoId <= 0) {
			addError("Selezionare un tipo di intervento", "Selezionare un tipo di intervento");
			return;
		}
		Long tipoInterventoCustomId = tipoIntTreeView.getSelTipoInterventoCutomId();
		Long catSoc = tipoIntTreeView.getSelCatSocialeId();
		
		//boolean datiErogazioniTabRendered = !provenienteDaFascicolo;
		fglInterventoBean.inizializzaErogazione(soggettoErogazioneSelezionato);
		fglInterventoBean.inizializzaDialog(false, true, 0L, 0L, tipoInterventoId, tipoInterventoCustomId, catSoc, true, true, "Nuova Erogazione", null, true, struttDisp, null);
		fglInterventoBean.setDatiInterventoTabRendered(false);
		
		//overwrite header
		fglInterventoBean.setHeaderDialogo( "Erogazione - " + fglInterventoBean.getErogazioneInterventoBean().getNomeTipoErogazione());
		
		this.tipoInterventoId=0L;
		this.tipoIntCustomName="";
		
		if (isTreeViewTipoIntervento())// SISO-1110
			this.tipoIntTreeView.reset();
		else
			this.tipoIntTreeView.resetCustomIstat();
		
		UserSearchBeanExt ubean = (UserSearchBeanExt)getReferencedBean("userSearchBeanExt");
		ubean.clearParameters();
		nuovoSoggetto=null;
	}

	public void inizializzaDialogo(Object obj, InserimentoConsuntivazioneDTO consuntivazione) {

		if (obj == null) {

			this.tipoInterventoId = tipoIntTreeView.getSelTipoInterventoId();
			if (tipoInterventoId == null || tipoInterventoId <= 0) {
				addError("Selezionare un tipo di intervento", "Selezionare un tipo di intervento");
				return;
			}

			if (!provenienteDaFascicolo)
				loadSoggettoErogazioneSelezionato();
			// SISO 945 - Inizio
			else {
				// proviene da fascicolo, devo recuperare dal soggetto i dati anagrafici

			}
			// SISO 945 - Fine
			// boolean datiErogazioniTabRendered = !provenienteDaFascicolo;
			Long catSoc = tipoIntTreeView.getSelCatSocialeId();
			Long tipoInterventoCustomId = tipoIntTreeView.getSelTipoInterventoCutomId();
			fglInterventoBean.inizializzaErogazione(soggettoErogazioneSelezionato /*, comuneNazioneNascitaMan*/);
			fglInterventoBean.inizializzaDialog(false, true, 0L, 0L, tipoInterventoId, tipoInterventoCustomId, catSoc, true, true, "Nuova Erogazione", null, provenienteDaFascicolo, null, consuntivazione);
			
		} else {
			ErogInterventoRowBean row = (ErogInterventoRowBean) obj;
			logger.info("Carico un'erogazione esistente per modificarla:" + row.getIdRow());
			boolean datiErogazioniTabRendered = row.isRenderBtnEroga() || row.isRenderBtnAvviaErog();

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(row.getMaster().getIdInterventoEsegMaster());
			CsIInterventoEsegMast master = interventoService.getCsIInterventoEsegMastById(dto);
			if(master==null)
				fglInterventoBean.inizializzaRiferimentoErogazione(row);
			
			fglInterventoBean.inizializzaDialog(false, datiErogazioniTabRendered, row.getIdIntervento(),
					row.getDiarioId(), row.getIdTipoIntervento(), row.getIdTipoInterventoCustom(),
					row.getIdCatSociale(), true, true, "Modifica Erogazione", master, provenienteDaFascicolo, null, consuntivazione);
		}

		fglInterventoBean.setDatiInterventoTabRendered(false);
		this.nuovoSoggetto = null;
		// overwrite header
		fglInterventoBean.setHeaderDialogo("Erogazione - " + fglInterventoBean.getErogazioneInterventoBean().getNomeTipoErogazione());

		this.tipoInterventoId = 0L;
		this.tipoIntCustomName = "";

		if (isTreeViewTipoIntervento())// SISO-1110
			this.tipoIntTreeView.reset();
		else
			this.tipoIntTreeView.resetCustomIstat();

		// TODO:Reset ricerca
	}
	
	public void rimuoviInterventoSenzaRichiesta(ErogInterventoRowBean rowBean) {
		if(rowBean==null){
			addError("Non è stato selezionato alcune elemento da eliminare", "");
			return;
			
		}else{
			idRow = rowBean.getIdRow();
			if(!idRow.startsWith("E")){
				addError("Non è possibile eliminare erogazioni associate a richieste intervento", "");
				return;
			}
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
		    Long idErogazione = new Long(idRow.replaceFirst("E", ""));
			dto.setObj(idErogazione);
			
			AccessTablePsExportSessionBeanRemote psExportService= (AccessTablePsExportSessionBeanRemote)getCarSocialeEjb("AccessTablePsExportSessionBean");
			
			List<CsIPsExport> lstExport = psExportService.findCsIPsExportByCsIInterventoMastIdExported(dto); //SISO-884
			if(lstExport!=null && !lstExport.isEmpty()){
				addError("Eliminazione non consentita", "Le erogazioni sono state già esportate nel flusso INPS");
				return;
			}	
			
			SoggettoErogazioneBean beneficiario = rowBean.getBeneficiarioRiferimento();
			String riferimento = "num. "+ idErogazione +" ["+ beneficiario.getCognome()+" "+beneficiario.getNome()+"]";
			
			try{
						
		    	interventoService.rimuoviInterventoEseguitoMast(dto);
		    	
			}catch(Exception t){
				logger.error(t);
				addError("Eliminazione non riuscita", "Errore nell'eliminazione dell'intervento "+ riferimento);
				return;
				
			}
			
			this.addInfo("", "Eliminazione dell'intervento "+ riferimento +" completata con successo");
		}
	}

	public void chiamataUpdate() {
		logger.debug("Aggiorno la lista!");
	}

	public void onTipoListaInterventiChanged(AjaxBehaviorEvent event) {
		// logger.debug("onchange");
	}
	
	
	/* #SISO-381
	 * 
	 * Non potendo sfruttare il dataExporter di PrimeFaces 4 (poiché non è possibile personalizzare la funzione
	 * di export delle singole colonne), si procede a generare un file Excel direttamente con Apache POI. */
	private static final int EXCEL_COLUMN_INDEX_TIPO_BENEFICIARIO = 0;
	private static final int EXCEL_COLUMN_INDEX_BENEFICIARI = 1;
	private static final int EXCEL_COLUMN_INDEX_BENEFICIARI_CF = 2;
	private static final int EXCEL_COLUMN_INDEX_RICHIESTA = 3;
	private static final int EXCEL_COLUMN_INDEX_TIPO_INTERVENTO = 4;
	private static final int EXCEL_COLUMN_INDEX_TIPO_INTERVENTO_CUSTOM = 5;
	private static final int EXCEL_COLUMN_INDEX_NOTE = 6;
	private static final int EXCEL_COLUMN_INDEX_PROGETTO = 7;
	private static final int EXCEL_COLUMN_INDEX_SETT_TITOLARE = 8;
	//private static final int EXCEL_COLUMN_INDEX_GESTORE = 9;
	private static final int EXCEL_COLUMN_INDEX_SETT_EROGANTE = 9;
	private static final int EXCEL_COLUMN_INDEX_DATA_ULTIMA_EROG = 10;
	private static final int EXCEL_COLUMN_INDEX_DATA_EVENTO_ULTIMA_EROG = 11;
	private static final int EXCEL_COLUMN_INDEX_STATO_ULTIMA_EROG = 12;
	private static final int EXCEL_COLUMN_INDEX_TOT_SPESA = 13;
	private static final int EXCEL_COLUMN_INDEX_TOT_SERVIZIO = 14;
	private static final int EXCEL_COLUMN_INDEX_TOT_ATTRIBUTI = 15;
	
	private CellStyle exportCellStyle;
	
	public void excelExport() throws IOException {
//		System.out.println("******** EXCEL EXPORT ********");
		
		// creo il file Excel
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		/* Imposto il CellStyle da utilizzare per questo documento.
		 * 
		 * wrapText = true permette l'inserimento di contenuti su più righe all'interno delle celle. */
		exportCellStyle = workbook.createCellStyle();
		exportCellStyle.setWrapText(true);
		
		// creo il (solo) foglio del documento
		HSSFSheet sheet = workbook.createSheet("Erogazioni");
		
		// creo la riga di intestazione e popolo i campi a mano
		HSSFRow headerRow = sheet.createRow(0);
		
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TIPO_BENEFICIARIO, "Tipo beneficiario");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_BENEFICIARI, "Beneficiari (cognome e nome)");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_BENEFICIARI_CF, "Beneficiari (cod.fiscale)");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_RICHIESTA, "Richiesta");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO, "Tipo Intervento");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO_CUSTOM, "Tipo intervento Custom");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_NOTE, "Note");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_PROGETTO, "Progetto");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_SETT_TITOLARE, "Sett. Titolare");
		//createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_GESTORE, "Gestore");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_SETT_EROGANTE, "Sett. Erogante");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DATA_ULTIMA_EROG, "Data Ultima Erog.");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DATA_EVENTO_ULTIMA_EROG, "Data Evento Ultima Erog.");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_STATO_ULTIMA_EROG, "Stato Ultima Erog.");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TOT_SPESA, "Tot.Spesa");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TOT_SERVIZIO, "Tot.Servizio");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TOT_ATTRIBUTI, "Tot.Attributi");
		
		//contatore righe tabella erogazioni interventi
		int erogRows = 0;
		//contatore righe excel
		int excelRow = 0;
		//totale righe da esportare
		int rowCount = lazyListaErogazioniModel.getRowCount();
		int pageSize = 100; // lazyListaErogazioniModel.getPageSize();
		List<ErogInterventoRowBean> listaErogs = lazyListaErogazioniModel.load(erogRows, pageSize, lazyListaErogazioniModel.sortField, lazyListaErogazioniModel.sortOrder, lazyListaErogazioniModel.filters);
		
		while(erogRows < rowCount){ 
			for(ErogInterventoRowBean rowListaErogazioni : listaErogs){
				excelRow++;
				
				// creo la i-esima riga, con l'altezza predefinita (in modo che si capisca se una cella ha più righe di contenuto)
				HSSFRow row = sheet.createRow(excelRow);
				row.setHeightInPoints(30);
				
				// popolo le colonne (replicando di fatto la logica della view)
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TIPO_BENEFICIARIO,rowListaErogazioni.getMaster().getTipoBeneficiario());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_BENEFICIARI,beneficiariValueExtraction(rowListaErogazioni.getBeneficiari()));	
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_BENEFICIARI_CF,beneficiariCfValueExtraction(rowListaErogazioni.getBeneficiari()));	
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_RICHIESTA,richiestaValueExtraction(rowListaErogazioni.isRichiestaIntervento()));
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO,rowListaErogazioni.getMaster().getTipoIntervento().getDescrizione());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO_CUSTOM, rowListaErogazioni.getMaster().getTipoInterventoCustom());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_NOTE,rowListaErogazioni.getMaster().getDescrIntervento());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_PROGETTO,rowListaErogazioni.getDescrizioneProgetto());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_SETT_TITOLARE,rowListaErogazioni.getSettoreTitolare());
				//createAndPopulateCell(row, EXCEL_COLUMN_INDEX_GESTORE,settoreValueExtraction(rowListaErogazioni.getSettoreGestore()));
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_SETT_EROGANTE,rowListaErogazioni.getSettoreErogante());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DATA_ULTIMA_EROG,dataUltimaErogValueExtraction(rowListaErogazioni.getMaster().getDataUltimaErogazione()));
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DATA_EVENTO_ULTIMA_EROG,dataUltimaErogValueExtraction(rowListaErogazioni.getMaster().getDataEventoUltimaErogazione()));
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_STATO_ULTIMA_EROG, rowListaErogazioni.getMaster().getStatoUltimaErogazione());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TOT_SPESA, totSpesaValueExtraction(rowListaErogazioni.getMaster()));
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TOT_SERVIZIO, rowListaErogazioni.getSommaUnitaMisura());
				createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TOT_ATTRIBUTI, rowListaErogazioni.getDettaglioTotaleErogazione());
			
			}
			   erogRows += pageSize;
			   listaErogs = lazyListaErogazioniModel.load(excelRow, pageSize, lazyListaErogazioniModel.sortField, lazyListaErogazioniModel.sortOrder, lazyListaErogazioniModel.filters);
		}
		
		// imposto la larghezza delle colonne
		for (int i = 0; i <= 14; i++) {
			sheet.setColumnWidth(i, 20 * 256);	// la larghezza è in 1/256 di carattere
		}
		
		
		// scateno la response con il download dell'Excel creato
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		String cfCaso = !StringUtils.isBlank(lazyListaErogazioniModel.getSelectedSoggettoErogazioneCF()) ? "_"+this.lazyListaErogazioniModel.getSelectedSoggettoErogazioneCF(): "";
		
		String fileName = "erogazioni"+cfCaso+".xls";
		String contentType = "application/vnd.ms-excel";
		
		ec.responseReset();
		ec.setResponseContentType(contentType);
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		OutputStream os = null;
		os = ec.getResponseOutputStream();
		workbook.write(os);	// scrivo il file direttamente sull'outputStream senza salvarlo
		
		fc.responseComplete();
	}

	private void createAndPopulateCell(Row row, int columnIndex, String value) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellStyle(exportCellStyle);
		
		cell.setCellValue(value);
	}

	private String beneficiariValueExtraction(List<SoggettoErogazioneBean> beneficiari) {
		List<String> beneficiariNames = new ArrayList<String>();
		
		for (SoggettoErogazioneBean soggettoErogazione : beneficiari) {
			String beneficiarioName = String.format("%s %s", soggettoErogazione.getCognome(), soggettoErogazione.getNome());
			beneficiariNames.add(beneficiarioName);
		}
		
		return StringUtils.join(beneficiariNames.iterator(), "\n");
	}
	
	private String beneficiariCfValueExtraction(List<SoggettoErogazioneBean> beneficiari) {
		List<String> cfs = new ArrayList<String>();
		
		for (SoggettoErogazioneBean soggettoErogazione : beneficiari) {
			String cf = soggettoErogazione.getCf();
			cfs.add(cf);
		}
		
		return StringUtils.join(cfs.iterator(), "\n");
	}

	private String richiestaValueExtraction(boolean richiestaIntervento) {
		return richiestaIntervento ? "Sì" : "No";
	}
	
	private String settoreValueExtraction(CsOSettore settore) {
		return settore != null
			? String.format("%s (Org.%s)", settore.getNome(), settore.getCsOOrganizzazione().getNome())
			: "";
	}
	
	private String dataUltimaErogValueExtraction(Date dataUltimaErogazione) {
		SimpleDateFormat ddmmyyyy = new SimpleDateFormat("dd/MM/yyyy");
		return dataUltimaErogazione == null ? "" : ddmmyyyy.format(dataUltimaErogazione);
	}
	
	private String totSpesaValueExtraction(ErogazioneMasterDTO master) {
		return String.format("%s\n%s", master.getSpesa().getDescrizione(), master.getCompartecipazioni().getDescrizione());
	}

	
	
	// ********GETTERS AND SETTERS

	public TipoInterventoManBean getTipoIntTreeView() {
		return tipoIntTreeView;
	}

	public void setTipoIntTreeView(TipoInterventoManBean tipoIntTreeView) {
		this.tipoIntTreeView = tipoIntTreeView;
	}

	public Long getIdIntervento() {
		return idIntervento;
	}

	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}

	public Long getIdDiario() {
		return idDiario;
	}

	public void setIdDiario(Long idDiario) {
		this.idDiario = idDiario;
	}


	public List<SelectItem> getTipoInterventosAll() {
		return tipoInterventosAll;
	}

	public List<SelectItem> getTipoInterventosRecenti() {
		return tipoInterventosRecenti;
	}

	public List<SelectItem> getTipoInterventosCustom() {
		return tipoInterventosCustom;
	}
	
	public void setTipoInterventoId(Long tipoInterventoId) {
		this.tipoInterventoId = tipoInterventoId;
	}
    //SISO-1162
	public List<SelectItem> getTipoInterventosInps() {
		return tipoInterventosInps;
	}

	public Long getTipoInterventoId() {
		return tipoIntTreeView.getSelTipoInterventoId();
		//return tipoInterventoId;
	}
	public String getTipoIntCustomName() {
		this.tipoIntCustomName= tipoIntTreeView.getSelTipoInterventoCustom().getDescrizione();
		return this.tipoIntCustomName;
	}

	public void setTipoIntCustomName(String tipoIntCustomName) {
		this.tipoIntCustomName = tipoIntCustomName;
	}

	public void setInterventoService(AccessTableInterventoSessionBeanRemote interventoService) {
		this.interventoService = interventoService;
	}

	public List<SelectItem> getListaTipoFiltroInterventi() {
		return listaTipoFiltroInterventi;
	}

	public LazyListaErogazioniModel getLazyListaErogazioniModel() {
		return lazyListaErogazioniModel;
	}

	public void setLazyListaErogazioniModel(LazyListaErogazioniModel lazyListaErogazioniModel) {
		this.lazyListaErogazioniModel = lazyListaErogazioniModel;
	}

	public String getIdRow() {
		return idRow;
	}

	public void setIdRow(String idRow) {
		this.idRow = idRow;
	}

	public FglInterventoBean getFglInterventoBean() {
		return fglInterventoBean;
	}

	public void setFglInterventoBean(FglInterventoBean fglInterventoBean) {
		this.fglInterventoBean = fglInterventoBean;
	}

	public List<String> getTipoBeneficiarios() {
		return new ArrayList<String>(){{add(DataModelCostanti.ListaBeneficiari.INDIVIDUALE);add(DataModelCostanti.ListaBeneficiari.NUCLEO);add(DataModelCostanti.ListaBeneficiari.GRUPPO);}};
		
	}

	//Inizio SISO-1110
	public boolean isTreeViewIntervento() {
		return isTreeViewTipoIntervento();
	}

	public void setTreeViewIntervento(boolean isTreeViewIntervento) {
		this.isTreeViewIntervento = isTreeViewIntervento;
	}
	//Fine SISO-1110	
}