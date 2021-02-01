package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.SoggettoErogazioneBean;
import it.webred.cs.csa.web.manbean.fascicolo.FglInterventoBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventiTreeView.TipoInterventoManBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoRicercaSoggetto;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsIInterventoEsegMast;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.jsf.bean.DatiUserSearchBean;
import it.webred.cs.jsf.manbean.ComuneNascitaMan;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.UserSearchBeanExt;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComponenteFamigliaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.jsf.bean.ComuneBean;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;

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

@ManagedBean
@ViewScoped
public class ErogazioniInterventiBean extends CsUiCompBaseBean {

	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getCarSocialeEjb ("AccessTableInterventoSessionBean");
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb ("AccessTableSoggettoSessionBean");
	protected AnagrafeService anagrafeService = (AnagrafeService) getEjb("CT_Service", "CT_Service_Data_Access", "AnagrafeServiceBean");
	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb ("AccessTableConfigurazioneSessionBean");
	  
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
	private List<SelectItem> tipoInterventosAll = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosRecenti = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosCustom = new LinkedList<SelectItem>();
	private List<SelectItem> tipoInterventosInps = new LinkedList<SelectItem>();
	private List<SelectItem> listaTipoFiltroInterventi = new LinkedList<SelectItem>();
	
	//SISO-945
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
		
	
	private String idRow;

	private SoggettoErogazioneBean soggettoErogazioneSelezionato;
	private Boolean provenienteDaFascicolo = false;
	
	protected boolean isTreeViewIntervento; //SISO-1110
	
	public ErogazioniInterventiBean() {
		fglInterventoBean = (FglInterventoBean)getBeanReference("fglInterventoBean");
	}
	
	@PostConstruct
	public void init() {
		init( DataModelCostanti.ListaErogazioni.TUTTI );
	}

	protected void init(int defaultTipoFiltroIntervento ){
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.TUTTI, "Tutti"));
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.CON_RICHIESTA, "Richiesti"));
		listaTipoFiltroInterventi.add(new SelectItem(DataModelCostanti.ListaErogazioni.SENZA_RICHIESTA, "Erogati senza richiesta "));

		// tipoFiltroInterventiSelezionato =
		// DataModelCostanti.ListaErogazioni.TUTTI;
		lazyListaErogazioniModel = new LazyListaErogazioniModel();
		lazyListaErogazioniModel.Setup(defaultTipoFiltroIntervento, soggettoErogazioneSelezionato);

		// loadListaInterventi();
		loadTipoInterventi();
		
		//SISO-745
		//la componente treeview se provengo dal fascicolo è presente nel bean InterventiBean, non va caricata di nuovo
		if(!provenienteDaFascicolo){
			this.tipoIntTreeView = new TipoInterventoManBean(tipoInterventosAll,"");	
		}
		
		tipoInterventoId = 0L;
		// rowIndex = null;
		idRow = null;
	}
	
	public void SetFromFascicolo(CsASoggettoLAZY soggetto)
	{
		provenienteDaFascicolo = true;
		CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
		String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
		soggettoErogazioneSelezionato = new SoggettoErogazioneBean(soggetto,via, getCasoComuneResidenza(residenza),  true);

		init(DataModelCostanti.ListaErogazioni.TUTTI);
	}

	public void inizializzaSoggettoDialog(DatiUserSearchBean sbean){
		this.nuovoSoggetto=sbean;
		this.inizializzaDialogo(null);
	}
	
	protected void loadSoggettoErogazioneSelezionato() {
		ObjectMapper om = new ObjectMapper();
		DatiUserSearchBean sbean = this.nuovoSoggetto;
		String idPersonaSelezionata = sbean!=null ? sbean.getId() : null;
		if (StringUtils.isNotEmpty(idPersonaSelezionata)) {
			if (sbean.isAnagrafeSanitaria() || sbean.isAnagrafeSigess()) {
				PersonaDettaglio p = null;
				if(sbean.getSoggetto()!=null)
				  p = (PersonaDettaglio)sbean.getSoggetto();
				
				//Provenendo dall'anagrafe marche i dati sono già completi, non serve richiamare il WS
				if(idPersonaSelezionata.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA))
						p = getPersonaDaAnagEsterna(TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, idPersonaSelezionata.replace(DataModelCostanti.TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA, ""));
				if(idPersonaSelezionata.trim().startsWith(DataModelCostanti.TipoRicercaSoggetto.SIGESS))
						p = getPersonaDaAnagEsterna(TipoRicercaSoggetto.SIGESS, idPersonaSelezionata.replace(DataModelCostanti.TipoRicercaSoggetto.SIGESS, ""));
				
				if (p != null){
					//SISO 945 
					comuneNazioneNascitaMan.init(p.getComuneNascita(), p.getNazioneNascita());
					//SISO 945 FINE
					
					String jsonResidenza=null;
					if(p.getComuneResidenza()!=null){
						ComuneBean comuneResidenza = new ComuneBean(p.getComuneResidenza());
						try {
							jsonResidenza = om.writeValueAsString(comuneResidenza);
						} catch (JsonProcessingException e1) {}
					}
					soggettoErogazioneSelezionato = new SoggettoErogazioneBean(p.getCognome(), p.getNome(), p.getCodfisc(), p.getCittadinanza(), p.getDataNascita(), p.getIndirizzoCivicoResidenza(), jsonResidenza, p.getSesso(),  true);
					
					//SISO-962 fine
				}
			}
			else
			{
				RicercaSoggettoAnagrafeDTO ricercaDto = new RicercaSoggettoAnagrafeDTO();
				fillEnte(ricercaDto);
				ricercaDto.setIdVarSogg(idPersonaSelezionata);
				SitDPersona persona = anagrafeService.getPersonaById(ricercaDto);
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				String codFisPersona = null == persona ? "" : persona.getCodfisc();
				dto.setObj(codFisPersona);
				CsASoggettoLAZY soggetto = soggettoService.getSoggettoByCF(dto);
				if (soggetto != null){
					CsAAnaIndirizzo residenza = findIndirizzoResidenzaCaso(soggetto);
					String via = residenza!=null ? residenza.getLabelIndirizzo() : null;
					soggettoErogazioneSelezionato = new SoggettoErogazioneBean(soggetto,via, getCasoComuneResidenza(residenza), true);
					//SISO-945
				    CsAAnagrafica csaAnagrafica =  soggetto.getCsAAnagrafica();
				    this.comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
				     
				    if (csaAnagrafica.getStatoNascitaCod() != null) {
								AmTabNazioni amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(csaAnagrafica.getStatoNascitaCod(),csaAnagrafica.getStatoNascitaDes());
								this.comuneNazioneNascitaMan.setNazioneValue();
								this.comuneNazioneNascitaMan.getNazioneMan().setNazione(amTabNazioni);
							}
					    	if (csaAnagrafica.getComuneNascitaCod() != null) {
								ComuneBean comuneBean = new ComuneBean(
										csaAnagrafica.getComuneNascitaCod(),
										csaAnagrafica.getComuneNascitaDes(),
										csaAnagrafica.getProvNascitaCod());
								this.comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
							}
	    			//siso - 945 fine
					
				}else if (persona != null){
					
					//siso - 945
					ComponenteFamigliaDTO compDto = new ComponenteFamigliaDTO();
					compDto.setPersona(persona);
					fillEnte(compDto);
					compDto = anagrafeService.fillInfoAggiuntiveComponente(compDto);
					
					String cittadinanza = compDto.getCittadinanza();//getCittadinanzaByIdExtStato(persona.getIdExtStato());
					
					String viaResidenza = null;
					String jsonResidenza = null;
					if(!StringUtils.isBlank(compDto.getCodComRes())){
						ComuneBean comuneResidenza =  new ComuneBean(compDto.getCodComRes(), compDto.getDesComRes(),compDto.getSiglaProvRes());
						try {
							jsonResidenza = om.writeValueAsString(comuneResidenza);
						} catch (JsonProcessingException e1) {}
					}
					
					viaResidenza =  !StringUtils.isBlank(compDto.getIndirizzoResidenza()) ? compDto.getIndirizzoResidenza() : "";
					viaResidenza += !StringUtils.isBlank(compDto.getCivicoResidenza()) ? ", "+compDto.getCivicoResidenza() : "";
					
					soggettoErogazioneSelezionato = new SoggettoErogazioneBean(persona.getCognome(), persona.getNome(), persona.getCodfisc(), cittadinanza, persona.getDataNascita(), viaResidenza, jsonResidenza, persona.getSesso(), true);

					
					AmTabNazioni amTabNazioni =  null;
					if (compDto.getDesStatoNas() != null) {
						amTabNazioni = CsUiCompBaseBean.getNazioneByIstat(compDto.getIstatStatoNas(),compDto.getDesStatoNas());
						this.comuneNazioneNascitaMan.setNazioneValue();
						this.comuneNazioneNascitaMan.getNazioneMan().setNazione(amTabNazioni);
						}  
					if(!StringUtils.isBlank(compDto.getCodComNas())) {
							ComuneNascitaMan  comuneNascitaBean = new ComuneNascitaMan();
							comuneNascitaBean.comune = new ComuneBean(compDto.getCodComNas(), compDto.getDesComNas(),compDto.getSiglaProvNas());
							this.comuneNazioneNascitaMan.setComuneNascitaMan(comuneNascitaBean);
						}
					
					}
				
				}
			
			//SISO-945 valorizza comune di nascita
			
		}else
			soggettoErogazioneSelezionato = null;
		
		UserSearchBeanExt ubean = (UserSearchBeanExt)getReferencedBean("userSearchBeanExt");
		ubean.clearParameters();
		nuovoSoggetto=null;
	}

	
	protected void loadTipoInterventi() {

		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		List<CsCTipoIntervento> lstTipoInterventi = interventoService.findAllTipiIntervento(dto);
		List<KeyValueDTO> lstTipoInterventiRecenti = interventoService.findTipiInterventoRecenti(dto);
		List<KeyValueDTO> lstTipoInterventiInps = interventoService.findTipiInterventoInps(dto);//SISO-1162
		List<KeyValueDTO> lstTipoInterventiCustom = interventoService.findTipiInterventoCustomRecenti(dto);
		this.tipoInterventosAll = new LinkedList<SelectItem>();
		this.tipoInterventosRecenti = new LinkedList<SelectItem>();
		this.tipoInterventosCustom = new LinkedList<SelectItem>();
		this.tipoInterventosInps = new LinkedList<SelectItem>(); //SISO-1162
		for (CsCTipoIntervento i : lstTipoInterventi){ 
			boolean abilitato = i.getAbilitato()!=null && "1".equalsIgnoreCase(i.getAbilitato());
			SelectItem si = new SelectItem((Long)i.getId(), i.getDescrizione());
			si.setDisabled(!abilitato);
			this.tipoInterventosAll.add(si);
		}
		for (KeyValueDTO i : lstTipoInterventiRecenti)
			this.tipoInterventosRecenti.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));

		for (KeyValueDTO i : lstTipoInterventiCustom)
			this.tipoInterventosCustom.add(new SelectItem((Long)i.getCodice(), i.getDescrizione()));
      
		for (KeyValueDTO i : lstTipoInterventiInps)
           this.tipoInterventosInps.add(new SelectItem((String)i.getCodice(), (String)i.getCodice() + " - " + i.getDescrizione()));
		this.tipoInterventosInps.add(new SelectItem( "Non definito", "Non definito"));
	}
	
	/*
	 * protected void loadListaInterventi() { this.listaInterventi=null;
	 * this.listaInterventiFiltro=null;
	 * 
	 * CsOOperatoreSettore opSettore = getCurrentOpSettore(); String permesso =
	 * getPermessoErogazioneInterventi(); ErogazioniSearchCriteria bDto = new
	 * ErogazioniSearchCriteria(); fillEnte(bDto);
	 * bDto.setSettoreId(opSettore.getCsOSettore().getId());
	 * bDto.setOrganizzazioneId
	 * (opSettore.getCsOSettore().getCsOOrganizzazione().getId());
	 * bDto.setPermessoAutorizzativo
	 * (TipoPermessoErogazioneInterventi.PERMESSO_AUTORIZZATIVO
	 * .equals(getPermessoErogazioneInterventi()));
	 * 
	 * listaInterventiAll = new LinkedList<ErogInterventoRowBean>();
	 *//**
	 * Recupero gli interventi eseguiti, privi di foglio ammnistrativo che
	 * abbiano una delle seguenti caratteristiche: 1. settore dell'operatore che
	 * l'ha inserito = corrente 2. organizzazione dell'erogazione = corrente 3.
	 * organizzazione del caso (recuperabile mediante CF) = corrente
	 */
	/*
	 * 
	 * List<ErogazioneDTO> lstInterventi =
	 * interventoService.searchListaErogInterventiBySettore(bDto); for
	 * (ErogazioneDTO csIIntervento : lstInterventi) { ErogInterventoRowBean row
	 * = new ErogInterventoRowBean(csIIntervento, permesso);
	 * this.listaInterventiAll.add(row); }
	 * 
	 * this.listaInterventiRichiesti = new LinkedList<ErogInterventoRowBean>();
	 * this.listaInterventiSenzaRichiesta = new
	 * LinkedList<ErogInterventoRowBean>(); for (ErogInterventoRowBean row :
	 * this.listaInterventiAll) { if (row.isRenderBtnAvviaErog())
	 * this.listaInterventiRichiesti.add(row); else if
	 * (row.isRenderBtnEliminaErog())
	 * this.listaInterventiSenzaRichiesta.add(row); }
	 * 
	 * }
	 */

	// /******PUBLIC METHODS*********

	//SISO-748
	public void inizializzaDialogoByTreeView(TipoInterventoManBean tipoIntTreeView){
		this.tipoInterventoId = tipoIntTreeView.getSelTipoInterventoId();
		if (tipoInterventoId == null || tipoInterventoId <= 0) {
			addError("Selezionare un tipo di intervento", "Selezionare un tipo di intervento");
			return;
		}
		
		//boolean datiErogazioniTabRendered = !provenienteDaFascicolo;
		fglInterventoBean.inizializzaErogazione(soggettoErogazioneSelezionato);
		fglInterventoBean.inizializzaDialog(false, true, 0L, 0L, tipoInterventoId, tipoIntTreeView.getSelTipoInterventoCutomId(), tipoIntTreeView.getSelCatSocialeId(), true, true, "Nuova Erogazione", null, true);
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
	
	public void inizializzaDialogo(Object obj) {

		if (obj == null) {
			
			this.tipoInterventoId = tipoIntTreeView.getSelTipoInterventoId();
			if (tipoInterventoId == null || tipoInterventoId <= 0) {
				addError("Selezionare un tipo di intervento", "Selezionare un tipo di intervento");
				return;
			}

			if( !provenienteDaFascicolo )
				loadSoggettoErogazioneSelezionato();
			//SISO 945 - Inizio
			else{
				//proviene da fascicolo, devo recuperare dal soggetto i dati anagrafici
				 
			}
			//SISO 945 - Fine
			//boolean datiErogazioniTabRendered = !provenienteDaFascicolo;
			fglInterventoBean.inizializzaErogazione(soggettoErogazioneSelezionato, comuneNazioneNascitaMan);
			fglInterventoBean.inizializzaDialog(false, true, 0L, 0L, tipoInterventoId, tipoIntTreeView.getSelTipoInterventoCutomId(), tipoIntTreeView.getSelCatSocialeId(), true, true, "Nuova Erogazione", null, provenienteDaFascicolo);
			
		}else{
			ErogInterventoRowBean row = (ErogInterventoRowBean) obj;
			logger.info("Carico un'erogazione esistente per modificarla:"+row.getIdRow());
			boolean datiErogazioniTabRendered = row.isRenderBtnEroga() || row.isRenderBtnAvviaErog();
			fglInterventoBean.inizializzaRiferimentoErogazione(row);
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(row.getMaster().getIdInterventoEsegMaster());
			CsIInterventoEsegMast master = interventoService.getErogazioneMasterById(dto);
		
			fglInterventoBean.inizializzaDialog(false, datiErogazioniTabRendered, row.getIdIntervento(), row.getDiarioId(), row.getIdTipoIntervento(), row.getIdTipoInterventoCustom(), row.getIdCatSociale(), true, true, "Modifica Erogazione", master, provenienteDaFascicolo);
		}
		
		fglInterventoBean.setDatiInterventoTabRendered(false);
		this.nuovoSoggetto=null;
		//overwrite header
		fglInterventoBean.setHeaderDialogo( "Erogazione - " + fglInterventoBean.getErogazioneInterventoBean().getNomeTipoErogazione());
		
		this.tipoInterventoId=0L;
		this.tipoIntCustomName="";
		
		if (isTreeViewTipoIntervento())// SISO-1110
			this.tipoIntTreeView.reset();
		else
			this.tipoIntTreeView.resetCustomIstat();
		
		//TODO:Reset ricerca
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
			
			try{
						
		    	interventoService.rimuoviInterventoEseguitoMast(dto);
		    	
			}catch(Exception t){
				logger.error(t);
				addError("Eliminazione non riuscita", "Errore nell'eliminazione dell'intervento");
				return;
				
			}
		
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
	private static final int EXCEL_COLUMN_INDEX_RICHIESTA = 2;
	private static final int EXCEL_COLUMN_INDEX_TIPO_INTERVENTO = 3;
	private static final int EXCEL_COLUMN_INDEX_TIPO_INTERVENTO_CUSTOM = 4;
	private static final int EXCEL_COLUMN_INDEX_NOTE = 5;
	private static final int EXCEL_COLUMN_INDEX_PROGETTO = 6;
	private static final int EXCEL_COLUMN_INDEX_SETT_TITOLARE = 7;
	private static final int EXCEL_COLUMN_INDEX_GESTORE = 8;
	private static final int EXCEL_COLUMN_INDEX_SETT_EROGANTE = 9;
	private static final int EXCEL_COLUMN_INDEX_DATA_ULTIMA_EROG = 10;
	private static final int EXCEL_COLUMN_INDEX_STATO_ULTIMA_EROG = 11;
	private static final int EXCEL_COLUMN_INDEX_TOT_SPESA = 12;
	private static final int EXCEL_COLUMN_INDEX_TOT_SERVIZIO = 13;
	private static final int EXCEL_COLUMN_INDEX_TOT_ATTRIBUTI = 14;
	
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
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_RICHIESTA, "Richiesta");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO, "Tipo Intervento");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO_CUSTOM, "Tipo intervento Custom");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_NOTE, "Note");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_PROGETTO, "Progetto");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_SETT_TITOLARE, "Sett. Titolare");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_GESTORE, "Gestore");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_SETT_EROGANTE, "Sett. Erogante");
		createAndPopulateCell(headerRow, EXCEL_COLUMN_INDEX_DATA_ULTIMA_EROG, "Data Ultima Erog.");
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
		
		List<ErogInterventoRowBean> listaErogs = lazyListaErogazioniModel.load(erogRows, lazyListaErogazioniModel.getPageSize(), lazyListaErogazioniModel.sortField, lazyListaErogazioniModel.sortOrder, lazyListaErogazioniModel.filters);
		
		while(erogRows < rowCount){
		
			
		for(ErogInterventoRowBean rowListaErogazioni : listaErogs){
			excelRow++;
			
			// creo la i-esima riga, con l'altezza predefinita (in modo che si capisca se una cella ha più righe di contenuto)
			HSSFRow row = sheet.createRow(excelRow);
			row.setHeightInPoints(30);
			
			// popolo le colonne (replicando di fatto la logica della view)
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TIPO_BENEFICIARIO,rowListaErogazioni.getMaster().getTipoBeneficiario());
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_BENEFICIARI,beneficiariValueExtraction(rowListaErogazioni.getBeneficiari()));	
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_RICHIESTA,richiestaValueExtraction(rowListaErogazioni.isRichiestaIntervento()));
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO,rowListaErogazioni.getMaster().getTipoIntervento().getDescrizione());
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TIPO_INTERVENTO_CUSTOM, rowListaErogazioni.getMaster().getTipoInterventoCustom());
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_NOTE,rowListaErogazioni.getMaster().getDescrIntervento());
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_PROGETTO,rowListaErogazioni.getDescrizioneProgetto());
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_SETT_TITOLARE,settoreValueExtraction(rowListaErogazioni.getSettoreTitolare()));
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_GESTORE,settoreValueExtraction(rowListaErogazioni.getSettoreGestore()));
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_SETT_EROGANTE,settoreValueExtraction(rowListaErogazioni.getSettoreErogante()));
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_DATA_ULTIMA_EROG,dataUltimaErogValueExtraction(rowListaErogazioni.getMaster().getDataUltimaErogazione()));
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_STATO_ULTIMA_EROG, rowListaErogazioni.getMaster().getStatoUltimaErogazione());
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TOT_SPESA, totSpesaValueExtraction(rowListaErogazioni.getMaster()));
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TOT_SERVIZIO, rowListaErogazioni.getSommaUnitaMisura());
			createAndPopulateCell(row, EXCEL_COLUMN_INDEX_TOT_ATTRIBUTI, rowListaErogazioni.getDettaglioTotaleErogazione());
		
		}
		   erogRows += lazyListaErogazioniModel.getPageSize();
		   listaErogs = lazyListaErogazioniModel.load(excelRow, lazyListaErogazioniModel.getPageSize(), lazyListaErogazioniModel.sortField, lazyListaErogazioniModel.sortOrder, lazyListaErogazioniModel.filters);
		}
		
		// imposto la larghezza delle colonne
		for (int i = 0; i <= 14; i++) {
			sheet.setColumnWidth(i, 20 * 256);	// la larghezza è in 1/256 di carattere
		}
		
		
		// scateno la response con il download dell'Excel creato
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		
		String fileName = "erogazioni.xls";
		String contentType = "application/vnd.ms-excel";
		
		ec.responseReset();
		ec.setResponseContentType(contentType);
		ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		OutputStream responseOutputStream = ec.getResponseOutputStream();
		workbook.write(responseOutputStream);	// scrivo il file direttamente sull'outputStream senza salvarlo
		
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

	public void setTipoInterventosInps(List<SelectItem> tipoInterventosInps) {
		this.tipoInterventosInps = tipoInterventosInps;
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