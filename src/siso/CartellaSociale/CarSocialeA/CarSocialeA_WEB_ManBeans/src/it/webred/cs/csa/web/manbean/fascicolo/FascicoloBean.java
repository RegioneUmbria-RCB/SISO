package it.webred.cs.csa.web.manbean.fascicolo;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableDiarioSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableExportValutazioniSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiOperatoreDTO;
import it.webred.cs.csa.ejb.dto.PresaInCaricoDTO;
import it.webred.cs.csa.ejb.dto.RelSettCatsocEsclusivaDTO;
import it.webred.cs.csa.ejb.dto.listaCasi.UnitaOrganizzativaDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.web.manbean.fascicolo.altri.AltriSoggettiBean;
import it.webred.cs.csa.web.manbean.fascicolo.colloquio.ColloquioBean;
import it.webred.cs.csa.web.manbean.fascicolo.docIndividuali.DocIndividualiBean;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.InitAltriSoggetti;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.InitCategorieSocialiSoggetto;
import it.webred.cs.csa.web.manbean.fascicolo.initialize.InitInterventi;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.InterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.isee.IseeFascBean;
import it.webred.cs.csa.web.manbean.fascicolo.pai.PaiBean;
import it.webred.cs.csa.web.manbean.fascicolo.presaincarico.PresaInCaricoBean;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ListaProvvedimentiBean;
import it.webred.cs.csa.web.manbean.fascicolo.relazioni.RelazioniBean;
import it.webred.cs.csa.web.manbean.fascicolo.schedeSegr.SchedeSegrBean;
import it.webred.cs.csa.web.manbean.fascicolo.scuola.DatiScuolaBean;
import it.webred.cs.csa.web.manbean.fascicolo.valMultidimensionale.ListaValMultidimensionaliBean;
import it.webred.cs.csa.web.manbean.fascicolo.valSinba.ListaValSinbaBean;
import it.webred.cs.csa.web.manbean.scheda.SchedaDatiEsterniSoggettoBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.PermessiFascicolo;
import it.webred.cs.data.model.CsADatiSociali;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.utils.TaskPoolExecutor;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;

@ManagedBean
@SessionScoped
public class FascicoloBean extends CsUiCompBaseBean {

	/*********** Start Generic ******************/
	protected String username;
	protected CsASoggettoLAZY soggetto;
	protected List<CsCCategoriaSociale> catsocCorrenti;
	protected CsOOperatoreSettore opSettore;
	private Boolean canModifica;
	private Boolean isResponsabile;

	protected String datiPresaCaricoName = "datiPresaCarico";
	protected String fogliAmmName = "fogliAmm";
	protected String colloquioName = "colloquio";
	protected String iseeName = "isee";
	protected String relazName = "relaz";
	protected String paiName = "pai";
	protected String schedeSegrName = "schedeSegr";
	protected String docIndivName = "docIndiv";
	protected String schedaMultidimAnzName = "schedamultidimAnz";
	protected String datiScuolaName = "datiScuola";
	protected String provvTribName = "provTrib";
	protected String schedaValSinbaName = "schedaValSinba";

	protected String currTabName;
	
	protected String descrizioneSettoreResponsabile; //SISO-812
	protected String descrizioneOrganizzazioneResponsabile;

	protected boolean isDatiPresaCarico;
	protected boolean isFogliAmm;
	protected boolean isColloquio;
	protected boolean isIsee;
	protected boolean isRelaz;
	protected boolean isPai;
	protected boolean isSchedeSegr;
	protected boolean isDocIndiv;
	protected boolean isSchedaMultidimAnz;
	protected boolean isDatiScuola;
	protected boolean isProvvedimentiMinori;
	protected boolean isValSinba;

	protected boolean isModificaFascicolo;
	protected boolean isDatiPresaCaricoReadOnly;
	protected boolean isFogliAmmReadOnly;
	protected boolean isColloquioReadOnly;
	protected boolean isIseeReadOnly;
	protected boolean isRelazReadOnly;
	protected boolean isPaiReadOnly;
	protected boolean isSchedeSegrReadOnly;
	protected boolean isDocIndivReadOnly;
	protected boolean isSchedaMultidimAnzReadOnly;
	protected boolean isDatiScuolaReadOnly;
	protected boolean isProvedimentiMinoriReadOnly;
	protected boolean isValSinbaReadOnly;
	
	
	private Boolean renderInfoResponsabileCaso=false; //SISO-812

	protected PresaInCaricoBean presaInCaricoBean;
	protected InterventiBean interventiBean;
	protected ColloquioBean colloquioBean;
	protected RelazioniBean relazioniBean;
	protected PaiBean paiBean;
	protected DocIndividualiBean docIndividualiBean;
	protected ListaValMultidimensionaliBean listaValMultidimensionaliBean;
	protected ListaValSinbaBean listaValSinbaBean;

	protected DatiScuolaBean datiScuolaBean;
	protected IseeFascBean iseeBean;
	protected SchedeSegrBean schedeSegrBean;
	protected ListaProvvedimentiBean listaProvvedimentiBean;

	protected boolean altroSoggetto;
	protected AltriSoggettiBean altriSoggettiBean;

	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb("AccessTableSoggettoSessionBean");
	protected AccessTableIterStepSessionBeanRemote iterService = (AccessTableIterStepSessionBeanRemote) getCarSocialeEjb("AccessTableIterStepSessionBean");
	protected AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getCarSocialeEjb("AccessTableSchedaSessionBean");
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getCarSocialeEjb("AccessTableCasoSessionBean");
	protected AccessTableExportValutazioniSinbaSessionBeanRemote sinbaExportService = (AccessTableExportValutazioniSinbaSessionBeanRemote) getCarSocialeEjb("AccessTableExportValutazioniSinbaSessionBean");

	//SISO-1526
	@ManagedProperty(value="#{schedaDatiEsterniSoggettoBean}")
	private SchedaDatiEsterniSoggettoBean schedaDatiEsterni;
	
	List<UnitaOrganizzativaDTO> lstAccessoFascicolo;	
	
	
	
	
	public SchedaDatiEsterniSoggettoBean getSchedaDatiEsterni() {
		return schedaDatiEsterni;
	}

	public void setSchedaDatiEsterni(SchedaDatiEsterniSoggettoBean schedaDatiEsterni) {
		this.schedaDatiEsterni = schedaDatiEsterni;
	}

	public void carica(Long anagraficaId) {
		CsASoggettoLAZY s = this.getSoggettoById(anagraficaId);
		this.carica(s);
	}
	
	public void carica(Object soggetto) {
		if (soggetto != null) {
			try {
				boolean redirect = initializeFascicoloCartellaUtente(soggetto, false, null);
				if (redirect){
					FacesContext.getCurrentInstance().getExternalContext().redirect("fascicoloCartellaUtente.faces");  
					this.setRenderInfoResponsabileCaso(true);
				}
			} catch (IOException e) {
				logger.error("Errore durante il reindirizzamento al Fascicolo Cartella Utente", e);
				addError("Errore", "Errore durante il reindirizzamento al Fascicolo Cartella Utente");
			}
		} else
			addWarningFromProperties("seleziona.warning");
	}

	public boolean initializeFascicoloCartellaUtente(Object soggettoObj, boolean ignoreWarning, List<String> filtroSchede) {
		boolean redirect = false;
		canModifica = null;
		isModificaFascicolo = true;
		opSettore = getCurrentOpSettore();

		if (soggettoObj != null) {
	        try{
				
				soggetto = (CsASoggettoLAZY) soggettoObj;
				getSession().setAttribute("soggetto", soggettoObj);
				
				boolean datiSocialiAttivi = false;
				boolean presaInCarico = false;

				if (soggetto != null) {
					//SISO-1526
					this.schedaDatiEsterni.initialize(soggetto.getCsAAnagrafica().getCf());
					//SISO-812
					logger.debug("INIT carico informazioni relative al responsabile del fascicolo");
					
					BaseDTO bdto = new BaseDTO();
					fillEnte(bdto);
					bdto.setObj(soggetto.getCsACaso().getId());
					lstAccessoFascicolo = casoService.getListaUnitaOrganizzativeByCasoId(bdto);
					logger.debug("END carico informazioni relative al responsabile del fascicolo");
					
					PresaInCaricoDTO pic=iterService.getLastPICByCaso(bdto);
				    if(pic!=null){
				    	descrizioneSettoreResponsabile =  pic.getSettore()!=null ? pic.getSettore().getDescrizione() : null;
				    	descrizioneOrganizzazioneResponsabile = pic.getOrganizzazione()!=null ? pic.getOrganizzazione().getDescrizione() : null;
				    	presaInCarico = pic.getResponsabile()!=null && pic.getDataAmministrativa()!=null;
				    }
					
					  if(presaInCarico){
						 	
						  //A questo punto verifico se attualmente è preso in carico o no
						  if(isStatoAttuale(DataModelCostanti.IterStatoInfo.PRESO_IN_CARICO)){
							  //Verifico l'esistenza di dati sociali attivi
							    BaseDTO dto = new BaseDTO();
								fillEnte(dto);
								dto.setObj(soggetto.getAnagraficaId());
								dto.setObj2(new CsADatiSociali());
								List<?> listaCs = schedaService.findCsBySoggettoId(dto);
				
								int i = 0;
								while (i < listaCs.size() && !datiSocialiAttivi) {
									CsADatiSociali cs = (CsADatiSociali) listaCs.get(i);
									if (cs.getDataFineApp().after(new Date()))
										datiSocialiAttivi = true;
									i++;
								}
				
					       
								if (datiSocialiAttivi) redirect = true;
								else{
									if (!ignoreWarning) addWarningFromProperties("fascicolo.datisociali.nonpresenti");
								}
							  
						  }else{
							 redirect=true;
							 isModificaFascicolo = false; 	//Permetto il caricamento del fascicolo in sola visualizzazione (pulsanti disabilitati)
							 this.addInfoFromProperties("fascicolo.visualizzazione.info");
						  }
						  
						  }else{
						  if (!ignoreWarning) addWarningFromProperties("fascicolo.PIC.nonpresente");
					  }
				}
	
			}catch(Exception e){
				addErrorFromProperties("fascicoloInit.error");
				logger.error("Errore caricamento fascicolo [initializeFascicoloCartellaUtente]", e);
			}
        
		} else addWarningFromProperties("seleziona.warning");
		
		if(redirect){
			try{
				caricaFascicolo(filtroSchede);
			} catch (Exception e) {
				addErrorFromProperties("fascicoloInit.error");
				logger.error(e.getMessage(), e);
				redirect = false;
			} catch (Throwable e) {
				addErrorFromProperties("fascicoloInit.error");
				logger.error(e.getMessage(), e);
				redirect = false;
			}
		}
		
		return redirect;
	}

	private boolean isStatoAttuale(Long statoRichiesto) throws Exception{
		  BaseDTO itr = new BaseDTO();
		  fillEnte(itr);
		  itr.setObj(soggetto.getCsACaso().getId());
	      CsIterStepByCasoDTO stato = iterService.getLastIterStepByCasoDTO(itr);
		  return statoRichiesto.equals(stato.getIdStatoIter());
	}

	private boolean checkIsResponsabile(){
	try{	
		if(isResponsabile==null){
			// controllo responsabile
			isResponsabile = false;
			AccessTableCasoSessionBeanRemote casoSessionBean = getCasoSessioBean();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggetto.getCsACaso().getId());
			DatiOperatoreDTO coto = casoSessionBean.findResponsabileCaso(dto);
			if(coto != null) logger.debug("checkIsResponsabile: coto["+coto.getUsername()+"], operatore corrente["+dto.getUserId()+"],  creatore["+soggetto.getCsACaso().getUserIns()+"]");
			if (coto != null && coto.getUsername().equals(dto.getUserId())){
				isResponsabile = true;
			// se non esiste resp ma ho creato il caso
			}if (coto == null && soggetto.getCsACaso().getUserIns().equals(dto.getUserId())){
				isResponsabile = true;
			}
		}
		} catch (Exception e) {
			isResponsabile=false;
			logger.error(e.getMessage(), e);
		}
		return isResponsabile;
	}
	
	private void caricaFascicolo(List<String> filtroSchede) throws Throwable {

		//SISO-812
		  if(isProvenienzaCasiAssegnati()){
			  BaseDTO cdto = new BaseDTO();
			  fillEnte(cdto);    
			  cdto.setObj(soggetto.getCsACaso().getId());
			  cdto.setObj2(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
			  cdto.setObj3(opSettore.getCsOSettore().getId()); 
			  this.setFlagGestisciInformazioni(casoService.getFlagNascondiInformazioniAttualeByCasoSettoreOrganizzazione(cdto)); 
			}else{
				this.setFlagGestisciInformazioni(false); 
			}
		
		resetTabBean();
		resetTabPermission();
	
		logger.debug("INIZIO caricamento dati per inizializzazione tab fascicolo con ForkJoinPool");
		TaskPoolExecutor  pool = new TaskPoolExecutor();
	
		InitCategorieSocialiSoggetto initCategorie = null;
		try {
			// Recupero la categoria sociale
			BaseDTO b = new BaseDTO();
			fillEnte(b);
			b.setObj(soggetto.getAnagraficaId());
			initCategorie = new InitCategorieSocialiSoggetto(b);
			pool.addTask(initCategorie);
		} catch (Exception e) {
			addErrorFromProperties("fascicoloInit.error");
			logger.error("Errore caricamento categorie sociali soggetto ad apertura del fascicolo");
		}
	
	//			InitColloquio initColloquio = null;
	//			try {
	//				BaseDTO dtoFork = new BaseDTO();
	//				fillEnte(dtoFork);
	//				dtoFork.setObj(idCaso);
	//				initColloquio = new InitColloquio(dtoFork);
	//				pool.addTask(initColloquio);
	//
	//			} catch (Exception e) {
	//				addErrorFromProperties("fascicoloInit.error");
	//				logger.error("Errore caricamento dei colloqui ad apertura del fascicolo");
	//			}
		
	
		
		InitInterventi initInterventi = null;
		try {
			Long idCaso = soggetto.getCsACaso().getId();
			if (idCaso != null && idCaso > 0) {
				BaseDTO b = new BaseDTO();
				fillEnte(b);
				b.setObj(idCaso);
				initInterventi = new InitInterventi(b);
				pool.addTask(initInterventi);
			}
		} catch (Exception e) {
			addErrorFromProperties("fascicoloInit.error");
			logger.error("Errore caricamento interventi soggetto ad apertura del fascicolo");
		}
		
		InitAltriSoggetti initAltriSoggetti = null;
		try {
			BaseDTO dtoFork = new BaseDTO();
			fillEnte(dtoFork);
			if(soggetto != null) {
				dtoFork.setObj(soggetto.getAnagraficaId());
				dtoFork.setObj2(soggetto.getCsAAnagrafica().getCf());
				dtoFork.setObj3(soggetto.getCsAAnagrafica().getIdOrigWsTipo());
				dtoFork.setObj4(soggetto.getCsAAnagrafica().getIdOrigWsId());
			}
			initAltriSoggetti = new InitAltriSoggetti(dtoFork);
			pool.addTask(initAltriSoggetti);
		} catch (Exception e) {
			addErrorFromProperties("fascicoloInit.error");
			logger.error("Errore caricamento altri soggetti ad apertura del fascicolo",e);
		}
		
	
		boolean abnormal = pool.execute();	
		if(abnormal){
			logger.error("Errore esecuzione TaskPoolExecutor per caricamento fascicolo CF["+soggetto.getCsAAnagrafica().getCf()+"]");
			if(initCategorie.isCompletedAbnormally()){
				Throwable e = initCategorie.getException();
				logger.error("Errore caricamento categorie sociali soggetto ad apertura del fascicolo:"+e.getMessage(), e);
				throw e;
			}
			if(initAltriSoggetti.isCompletedAbnormally()){
				Throwable e = initAltriSoggetti.getException();
				logger.error("Errore caricamento altri soggetti ad apertura del fascicolo:"+e.getMessage(), e);
				throw e;
			}
			if(initInterventi.isCompletedAbnormally()){
				Throwable e = initInterventi.getException();
				logger.error("Errore caricamento interventi ad apertura del fascicolo: "+e.getMessage(), e);
				throw e;
			}
				
		}else{
			logger.debug("FINE caricamento dati per inizializzazione tab fascicolo con ForkJoinPool");
			
			logger.debug("INIZIO VALORIZZAZIONE TAB FASCICOLO");
			if (initCategorie.isCompletedNormally())
				catsocCorrenti = (List<CsCCategoriaSociale>) initCategorie.getRawResult();
			
			if (initAltriSoggetti.isCompletedNormally())
				initializeAltriSoggetti(soggetto, initAltriSoggetti.getRawResult());
						
			if (initInterventi.isCompletedNormally()) {
				if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.FOGLI_AMMINISTRATIVI))
					initializeFogliAmmTab(soggetto, initInterventi.getRawResult());
			}
			
	//			if (initColloquio.isCompletedNormally()) {
	//			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.COLLOQUIO))
	//				initializeColloquioTab(soggetto, initColloquio.getRawResult());
	//		}
	//		else {
	//			logger.error("Errore caricamento dei colloqui ad apertura del fascicolo");
	//			throw e;
	//		}	
	
			
			//SISO-745 permesso visualizzazione e readonly
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.ITER)){
				initializePresaInCaricoTab(soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.FOGLI_AMMINISTRATIVI)){
			    checkTabPermission(DataModelCostanti.TipoDiario.FOGLIO_AMM_ID, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.COLLOQUIO)){
			    checkTabPermission(DataModelCostanti.TipoDiario.COLLOQUIO_ID, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.RELAZIONI)) {
				// SISO-1233 - se si proviene dalla stampa del menu layout deve essere ricaricato il tab
				if (isProvenienzaLayout()) {
					initializeRelazioniTab(soggetto);
				} else {
					checkTabPermission(DataModelCostanti.TipoDiario.RELAZIONE_ID, soggetto);
				}
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.PAI)) {
				// SISO-1233
				if (isProvenienzaLayout()) {
					initializePaiTab(soggetto);// SISO-1233
				} else {
					checkTabPermission(DataModelCostanti.TipoDiario.PAI_ID, soggetto);
				}
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.DOC_INDIVIDUALI)){
			    checkTabPermission(DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.SCHEDA_MULTIDIMENSIONALE)){
			    checkTabPermission(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.SCHEDA_SINBA)){
			    checkTabPermission(DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.DATI_ISEE)){
			    checkTabPermission(DataModelCostanti.TipoDiario.ISEE_ID, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.PROVVEDIMENTI_MINORI)){
			    checkTabPermission(DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.DATI_SCUOLA)){
			    checkTabPermission(DataModelCostanti.TipoDiario.DATI_SCUOLA_ID, soggetto);
			}
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.SCHEDE_SEGRETARIATO)){
				this.isSchedeSegr = this.isVisTabUDC();
				this.isSchedeSegrReadOnly=false;
			}
			
			logger.debug("FINE VALORIZZAZIONE TAB FASCICOLO");
		}
	}

	private boolean checkFiltroSchede(List<String> filtroSchede, String tab) {
		boolean render = true;
		if (filtroSchede != null && !filtroSchede.isEmpty() && !filtroSchede.contains(tab))
			render = false;

		return render;
	}

	//SISO-745
	/*Messe tab in lazy, vengono caricate all'evento se NULL*/
	public void onTabChange(TabChangeEvent tab) throws Exception {
		String tabName = tab.getTab().getId();
		String clientId = tab.getComponent().getClientId();
		logger.debug("START Fascicolo - onTabChange "+tabName);

		// se ho caricato un altro soggetto, al cambio tab inizializzo quello
		// originale
		if (tabName.equals(datiPresaCaricoName + "Tab")) {
			currTabName = datiPresaCaricoName;
			if (presaInCaricoBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializePresaInCaricoTab(soggetto);
		}

		if (tabName.equals(fogliAmmName + "Tab")) {
			currTabName = fogliAmmName;
			if (interventiBean == null || interventiBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeFogliAmmTab(soggetto,null);
		}

		if (tabName.equals(colloquioName + "Tab")) {
			currTabName = colloquioName;
			if (colloquioBean == null || colloquioBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeColloquioTab(soggetto,null);
		}

		if (tabName.equals(relazName + "Tab")) {
			currTabName = relazName;
			//if (relazioniBean == null || relazioniBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeRelazioniTab(soggetto);
		}

		if (tabName.equals(paiName + "Tab")) {
			currTabName = paiName;
			//if (paiBean == null || paiBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId() || paiBean.getSelectedPai().getDiarioId() == null){
				initializePaiTab(soggetto);
				initializeRelazioniTab(soggetto);
			//}
		}

		if (tabName.equals(docIndivName + "Tab")) {
			currTabName = docIndivName;
			if (docIndividualiBean == null || docIndividualiBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeDocIndividualiTab(soggetto);
		}

		if (tabName.equals(schedeSegrName + "Tab")) {
			currTabName = schedeSegrName;
			if (schedeSegrBean == null || schedeSegrBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeSchedeSegrTab(soggetto);
		}
		
		if (tabName.equals(schedaMultidimAnzName + "Tab")) {
			currTabName = schedaMultidimAnzName;
			if (listaValMultidimensionaliBean == null || this.listaValMultidimensionaliBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId()){
				this.initializeValMultidimensionaleTab(soggetto);
				this.initializeIseeTab(soggetto);
			}
		}

		if (tabName.equals(schedaValSinbaName + "Tab")) {
			currTabName = this.schedaValSinbaName;
			if (this.listaValSinbaBean == null || this.listaValSinbaBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				this.initializeValSinbaTab(soggetto);
		}
		
		if (tabName.equals(datiScuolaName + "Tab")) {
			currTabName = datiScuolaName;
			if (datiScuolaBean == null || datiScuolaBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeDatiScuolaTab(soggetto);
		}

		if (tabName.equals(iseeName + "Tab")) {
			currTabName = iseeName;
			if (iseeBean == null || iseeBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeIseeTab(soggetto);
		}
		if (tabName.equals(provvTribName + "Tab")) {
			currTabName = provvTribName;
				initializeProvvMinoriTab(soggetto);
		}

		getAltriSoggettiBean().setLabelSoggetto(soggetto.getCsAAnagrafica().getCognome() + " " + soggetto.getCsAAnagrafica().getNome());
		RequestContext.getCurrentInstance().update(clientId + ":" + currTabName + "Form");
		logger.debug("END Fascicolo - onTabChange "+tabName);

	}
	
	public boolean isPaiTabSelected(){
		return this.getPaiBean()!=null && this.currTabName.equalsIgnoreCase(this.paiName);
	}

	private List<CsCCategoriaSociale> getCatSocIfSoggPrincipale(CsASoggettoLAZY s) {
		List<CsCCategoriaSociale> cslist = this.catsocCorrenti;
		if (s.getAnagraficaId().longValue() != soggetto.getAnagraficaId().longValue())
			cslist = null;
		return cslist;
	}

	public void initializePresaInCaricoTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabPIC()){
			logger.info("*** INIT PresaInCarico per il caso - anagraficaId:" + s.getAnagraficaId());
			presaInCaricoBean = new PresaInCaricoBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			presaInCaricoBean.initialize(s, cslist,null);
			isDatiPresaCarico = true;
			isDatiPresaCaricoReadOnly = false; //Anche se fosse disattivato il tab, non potrei comunque modificare
			presaInCaricoBean.setReadOnly(isDatiPresaCaricoReadOnly);
			logger.info("*** END PresaInCarico per il caso - anagraficaId:" + s.getAnagraficaId());
		}
	}

	private void initializeFogliAmmTab(CsASoggettoLAZY s, Object data) throws Exception {
		if(this.isVisTabAttivitaProfessionali() || this.isVisTabInterventi() || this.isVisTabPAI()){
			logger.info("Inizializza FogliAmm per il caso - anagraficaId:" + s.getAnagraficaId());
			interventiBean = new InterventiBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			interventiBean.initialize(s, cslist,data);
			checkTabPermission(DataModelCostanti.TipoDiario.FOGLIO_AMM_ID, s);
			interventiBean.setReadOnly(isFogliAmmReadOnly);
		}
	}
	
	public void initializeFogliAmmTab(CsASoggettoLAZY s) throws Exception {
		initializeFogliAmmTab(s,null);
	}
	
	private void initializeColloquioTab(CsASoggettoLAZY s, Object data) throws Exception {
		if(this.isVisTabDiario()){
			logger.info("INIT initializeColloquioTab - anagraficaId:" + s.getAnagraficaId());
			colloquioBean = new ColloquioBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			colloquioBean.initialize(s, cslist,data);
			checkTabPermission(DataModelCostanti.TipoDiario.COLLOQUIO_ID, s);
			colloquioBean.setReadOnly(isColloquioReadOnly);
			logger.info("END initializeColloquioTab - anagraficaId:" + s.getAnagraficaId());
		}
	}
	
	public void initializeColloquioTab(CsASoggettoLAZY s) throws Exception {
		initializeColloquioTab(s, null);
	}

	public void initializeRelazioniTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabAttivitaProfessionali() || this.isVisTabInterventi() || this.isVisTabPAI()){
			logger.info("Inizializza Relazioni per il caso - anagraficaId:" + s.getAnagraficaId());
			relazioniBean = new RelazioniBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			relazioniBean.initialize(s, cslist, null);
			checkTabPermission(DataModelCostanti.TipoDiario.RELAZIONE_ID, s);
			relazioniBean.setReadOnly(isRelazReadOnly);
		}
	}

	public void initializePaiTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabAttivitaProfessionali() || this.isVisTabInterventi() || this.isVisTabPAI()){
			logger.info("Inizializza PAI per il caso - anagraficaId:" + s.getAnagraficaId());
			paiBean = new PaiBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			paiBean.initialize(s, cslist, null);
			checkTabPermission(DataModelCostanti.TipoDiario.PAI_ID, s);
			paiBean.setReadOnly(isPaiReadOnly);
		}
	}

	public void initializeDocIndividualiTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisDocIndividuali()){
			logger.info("Inizializza DocIndividuali per il caso - anagraficaId:" + s.getAnagraficaId());
			docIndividualiBean = new DocIndividualiBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			docIndividualiBean.initialize(s, cslist,null);
			checkTabPermission(DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID, s);
			docIndividualiBean.setReadOnly(isDocIndivReadOnly);
		}
	}

	public void initializeValMultidimensionaleTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabMultidim()){
			logger.info("Inizializza SchedaMultidimensionaleTab per il caso - anagraficaId:" + s.getAnagraficaId());
			listaValMultidimensionaliBean = new ListaValMultidimensionaliBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			listaValMultidimensionaliBean.initialize(s, cslist,null);
			checkTabPermission(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID, s);
			listaValMultidimensionaliBean.setReadOnly(isSchedaMultidimAnzReadOnly);
		}
	}
	
	public void initializeValSinbaTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabValSinba()){
			logger.info("Inizializza ValSinbaTab per il caso - anagraficaId:" + s.getAnagraficaId());
			listaValSinbaBean = new ListaValSinbaBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			listaValSinbaBean.initialize(s, cslist,null);
			checkTabPermission(DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA, s);
			listaValSinbaBean.setReadOnly(isValSinbaReadOnly);
		}
	}

	public void initializeIseeTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabISEE() || this.isVisTabMultidim()){
			logger.info("Inizializza ISEE per il caso - anagraficaId:" + s.getAnagraficaId());
			iseeBean = new IseeFascBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			iseeBean.initialize(s, cslist,null);
			checkTabPermission(DataModelCostanti.TipoDiario.ISEE_ID, s);
			iseeBean.setReadOnly(isIseeReadOnly);
		}
	}

	public void initializeProvvMinoriTab(CsASoggettoLAZY s) {
		if(this.isVisTabProvvedimentiMinori()){
			logger.info("Inizializza Provvedimenti tribunale per il caso - anagraficaId:" + s.getAnagraficaId());
			listaProvvedimentiBean = new ListaProvvedimentiBean(s);
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			listaProvvedimentiBean.initialize(s, cslist,null);
			checkTabPermission(DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE, s);
			this.listaProvvedimentiBean.setReadOnly(isProvedimentiMinoriReadOnly);
		}
	}

	public void initializeDatiScuolaTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabScuola()){
			logger.info("Inizializza DatiScuola per il caso - anagraficaId:" + s.getAnagraficaId());
			datiScuolaBean = new DatiScuolaBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			datiScuolaBean.initialize(s, cslist,null);
			checkTabPermission(DataModelCostanti.TipoDiario.DATI_SCUOLA_ID, s);
			datiScuolaBean.setReadOnly(isDatiScuolaReadOnly);
		}
	}

	public void initializeSchedeSegrTab(CsASoggettoLAZY s) throws Exception {
		if(this.isVisTabUDC()){
			logger.info("Inizializza SchedeSegr per il caso - anagraficaId:" + s.getAnagraficaId());
			schedeSegrBean = new SchedeSegrBean();
			List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
			schedeSegrBean.initialize(s, cslist,null);
			isSchedeSegr = this.isVisTabUDC();
			isSchedeSegrReadOnly = false;
			schedeSegrBean.setReadOnly(isSchedeSegrReadOnly);
		}
	}

	public void initializeAltriSoggetti(CsASoggettoLAZY s, Object data) {
		logger.info("INIT Inizializza AltriSoggetti");
		altriSoggettiBean = new AltriSoggettiBean();
		List<CsCCategoriaSociale> cslist = this.getCatSocIfSoggPrincipale(s);
		altriSoggettiBean.initialize(s, cslist, data);
		logger.info("END Inizializza AltriSoggetti");
	}

	private void resetTabPermission() {
		isFogliAmm = false;
		isColloquio = false;
		isRelaz = false;
		isPai = false;
		isDocIndiv = false;
		isSchedaMultidimAnz = false;
		isValSinba = false;
		isDatiScuola = false;
		isProvvedimentiMinori = false;
		isSchedeSegr = false;
		
		isSchedeSegrReadOnly = false;
		isDatiPresaCarico = false;
	}
	
	public void resetTabBean(){
		 presaInCaricoBean=null;
		 interventiBean=null;
		 colloquioBean=null;
		 relazioniBean=null;
		 paiBean=null;
		 docIndividualiBean=null;
		 listaValMultidimensionaliBean=null;
		 datiScuolaBean=null;
		 iseeBean=null;
		 schedeSegrBean=null;
		 listaProvvedimentiBean=null;
	}

	//SISO-745
	/*dato che le tab sono lazy, non posso sapere se ci sono dati storici*/
	private void checkTabPermission(int tipoDiarioId, CsASoggettoLAZY sogg) {

		try {

			BaseDTO dtoSogg = new BaseDTO();
			fillEnte(dtoSogg);
			dtoSogg.setObj(sogg.getAnagraficaId());

			RelSettCatsocEsclusivaDTO relDto = new RelSettCatsocEsclusivaDTO();
			fillEnte(relDto);
			relDto.setCategoriaSocialeId((catsocCorrenti!=null && !catsocCorrenti.isEmpty()) ? catsocCorrenti.get(0).getId() : null);
			relDto.setSettoreId(opSettore!=null ? opSettore.getCsOSettore().getId() : null);
			relDto.setTipoDiarioId(new Long(tipoDiarioId));
	
			boolean funzioneSettCatsocPresente = confEnteService.existsRelSettCatsocEsclusivaByIds(relDto);
			boolean funzioneSempreAttiva = !confEnteService.existsRelSettCatsocEsclusivaByTipoDiarioId(relDto);
			
			boolean canModifica = canModifica();
			//boolean existsDatiStorici = false;

			// il Tab è visibile solo se non sono specificati permessi per il
			// tipo di diario o sono già presenti dei dati
			// la modifica è abilitata solo se è presente il permesso composto
			// da TipoDiario/CatSociale/Settore
			switch (tipoDiarioId) {
			case DataModelCostanti.TipoDiario.FOGLIO_AMM_ID:

				isFogliAmmReadOnly = true;
				if(this.isVisTabInterventi()){
					//List<DatiInterventoBean> listaInterventi = interventiBean.getListaInterventi();
					//existsDatiStorici =  listaInterventi!= null && !listaInterventi.isEmpty();
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isFogliAmm = true;
						if (canModifica)
							isFogliAmmReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isFogliAmm = true;
				}
				break;

			case DataModelCostanti.TipoDiario.COLLOQUIO_ID:

				isColloquioReadOnly = true;
				if(this.isVisTabDiario()){
					//existsDatiStorici = colloquioBean.getListaColloquios() != null && !colloquioBean.getListaColloquios().isEmpty();
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isColloquio = true;
						if (canModifica)
							isColloquioReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isColloquio = true;
				}
				break;

			case DataModelCostanti.TipoDiario.RELAZIONE_ID:

				isRelazReadOnly = false; //TODO: changed
				if(this.isVisTabAttivitaProfessionali()){
					//existsDatiStorici = relazioniBean.getListaRelazioniDTO() != null && !relazioniBean.getListaRelazioniDTO().isEmpty();
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isRelaz = true;
						if (canModifica)
							isRelazReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isRelaz = true;
				}
				break;

			case DataModelCostanti.TipoDiario.PAI_ID:

				isPaiReadOnly = true;
					if(this.isVisTabPAI()){
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isPai = true;
						if (canModifica)
							isPaiReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isPai = true;
				}
				/*
				 relazioniBean.setDisableNuovoPAI(true);
				 if ((funzioneSettCatsocPresente || funzioneSempreAttiva) && canModifica)
					relazioniBean.setDisableNuovoPAI(false);
				*/
				break;

			case DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID:

				isDocIndivReadOnly = true;
				if(this.isVisDocIndividuali()){
					/*existsDatiStorici = (docIndividualiBean.getListaDocIndividualiPubblica() != null && !docIndividualiBean.getListaDocIndividualiPubblica().isEmpty()) || 
										(docIndividualiBean.getListaDocIndividualiPrivata() != null && !docIndividualiBean.getListaDocIndividualiPrivata().isEmpty());
	                */
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isDocIndiv = true;
						if (canModifica || isUploadDocIndividuali())
							isDocIndivReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isDocIndiv = true;
				}
				break;

			case DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID:

				isSchedaMultidimAnzReadOnly = true;
				if(this.isVisTabMultidim()){
					//existsDatiStorici = listaValMultidimensionaliBean.existsDatiStorici();
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isSchedaMultidimAnz = true;
						if (canModifica)
							isSchedaMultidimAnzReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isSchedaMultidimAnz = true;
				}
				break;
				
			case DataModelCostanti.TipoDiario.VALUTAZIONE_SINBA:

				isValSinbaReadOnly = true;
				if(this.isVisTabValSinba()){
					//existsDatiStorici = listaValMultidimensionaliBean.existsDatiStorici();
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isValSinba = true;
						if (canModifica)
							isValSinbaReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isValSinba = true;
				}
				break;

			case DataModelCostanti.TipoDiario.DATI_SCUOLA_ID:

				isDatiScuolaReadOnly = true;
				if(this.isVisTabScuola()){
					//existsDatiStorici = (datiScuolaBean.getListaScuole() != null && !datiScuolaBean.getListaScuole().isEmpty());
	
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isDatiScuola = true;
						if (canModifica)
							isDatiScuolaReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isDatiScuola = true;
				}
				break;

			case DataModelCostanti.TipoDiario.ISEE_ID:

				isIseeReadOnly = true;
				if(this.isVisTabISEE()){
					//existsDatiStorici = (iseeBean.getListaIsee() != null && !iseeBean.getListaIsee().isEmpty());
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isIsee = true;
						if (canModifica)
							isIseeReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isIsee = true;
				}
				break;

			case DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE:

				isProvedimentiMinoriReadOnly = true;
				if(this.isVisTabProvvedimentiMinori()){
					//existsDatiStorici = this.listaProvvedimentiBean.existsDatiStorici();
					if (funzioneSettCatsocPresente || funzioneSempreAttiva) {
						isProvvedimentiMinori = true;
						if (canModifica)
							isProvedimentiMinoriReadOnly = false;
					} else if (!funzioneSempreAttiva && existsDatiStorici(tipoDiarioId))
						isProvvedimentiMinori = true;
				}
				break;
			}

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}
	
	private boolean existsDatiStorici(int tipoDiario){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(tipoDiario);
		dto.setObj2(this.soggetto.getCsACaso().getId());
		int num = casoService.countDatiStorici(dto);
		return num>0;
	}
	
	private boolean canModifica(){
		 Long idOpSett = getCurrentOpSettore().getId();
		 Long idCaso = this.soggetto.getCsACaso().getId();
		 
		 if(canModifica == null){
			 boolean permessoGlobale = checkPermesso(PermessiFascicolo.ITEM,PermessiFascicolo.GESTIONE_ELEM_FASCICOLO);
			 
			 BaseDTO dto = new BaseDTO();
			 fillEnte(dto);
			 dto.setObj(idCaso);
			 dto.setObj2(idOpSett);
			 Boolean permessoCaso = casoService.getFlagGestioneFascicolo(dto);
			 
			 /*TODO: Recuperare l'operartore corrente, per il caso*/
			 
			 /*
			  * permessi=ok, flag_permetti=false --> NON E' CONCESSO INSERIRE / MODIFICARE DATI
				permessi=ok, flag_permetti=true --> E' CONCESSO INSERIRE / MODIFICARE DATI
				permessi=ko, flag_permetti=false--> NON E' CONCESSO INSERIRE / MODIFICARE DATI
				permessi=ko, flag_permetti=true--> E' CONCESSO INSERIRE / MODIFICARE DATI
			  * */
			 boolean abilitato = false;
			 if(checkIsResponsabile()) abilitato = true;
			 else if(permessoCaso==null) abilitato = permessoGlobale;
			 else abilitato = permessoCaso;
			 
			canModifica = isModificaFascicolo && abilitato;
			logger.debug("Permessi Gestione Fascicolo casoId["+idCaso+"], opSettoreId["+idOpSett+"] --> permessi AM["+permessoGlobale+"], permessoCaso["+permessoCaso+"],isResponsabile["+checkIsResponsabile()+"] --> canModifica["+canModifica+"]");
		 }
		return canModifica;
		
	}
	//Inizio SISO-1110
	public boolean viewTreeIntervento(){
		boolean render = true;
		try{
			//TODO Leggere da DB se vanno caricati gli interventi custom o gli istat
			
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
		return render;		
	}
	//Fine SISO-1110
	
	protected AccessTableDiarioSessionBeanRemote getDiarioSessioBean() throws NamingException {
		AccessTableDiarioSessionBeanRemote sessionBean = (AccessTableDiarioSessionBeanRemote) getCarSocialeEjb ("AccessTableDiarioSessionBean");
		return sessionBean;
	}

	protected AccessTableCasoSessionBeanRemote getCasoSessioBean() throws NamingException {
		AccessTableCasoSessionBeanRemote sessionBean = (AccessTableCasoSessionBeanRemote) getCarSocialeEjb("AccessTableCasoSessionBean");
		return sessionBean;
	}

	public String getUsername() {
		return username;
	}

	public boolean isDatiPresaCarico() {
		return isDatiPresaCarico;
	}

	public boolean isFogliAmm() {
		return isFogliAmm;
	}

	public boolean isColloquio() {
		return isColloquio;
	}

	public boolean isIsee() {
		return isIsee;
	}

	public boolean isSchedeSegr() {
		return isSchedeSegr;
	}

	public boolean isDocIndiv() {
		return isDocIndiv;
	}

	public boolean isSchedaMultidimAnz() {
		return isSchedaMultidimAnz;
	}

	public boolean isDatiPresaCaricoReadOnly() {
		return isDatiPresaCaricoReadOnly;
	}

	public boolean isFogliAmmReadOnly() {
		return isFogliAmmReadOnly;
	}

	public boolean isColloquioReadOnly() {
		return isColloquioReadOnly;
	}

	public boolean isIseeReadOnly() {
		return isIseeReadOnly;
	}

	public boolean isSchedeSegrReadOnly() {
		return isSchedeSegrReadOnly;
	}

	public boolean isDocIndivReadOnly() {
		return isDocIndivReadOnly;
	}

	public boolean isSchedaMultidimAnzReadOnly() {
		return isSchedaMultidimAnzReadOnly;
	}

	public ColloquioBean getColloquioBean() {
		return colloquioBean;
	}

	public RelazioniBean getRelazioniBean() {
		return relazioniBean;
	}

	public void setRelazioniBean(RelazioniBean relazioniBean) {
		this.relazioniBean = relazioniBean;
	}

	public PaiBean getPaiBean() {
		return paiBean;
	}

	public void setPaiBean(PaiBean paiBean) {
		this.paiBean = paiBean;
	}

	public InterventiBean getInterventiBean() {
		return interventiBean;
	}

	public void setInterventiBean(InterventiBean interventiBean) {
		this.interventiBean = interventiBean;
	}

	public boolean isRelaz() {
		return isRelaz;
	}

	public boolean isRelazReadOnly() {
		return isRelazReadOnly;
	}

	public boolean isPai() {
		return isPai;
	}

	public boolean isPaiReadOnly() {
		return isPaiReadOnly;
	}

	public void setColloquioBean(ColloquioBean colloquioBean) {
		this.colloquioBean = colloquioBean;
	}

	public AltriSoggettiBean getAltriSoggettiBean() {
		return altriSoggettiBean;
	}

	public void setAltriSoggettiBean(AltriSoggettiBean altriSoggettiBean) {
		this.altriSoggettiBean = altriSoggettiBean;
	}

	public boolean isRenderVisualizzaFascicolo() {
		
		boolean render = false;
				
		render = render || isVisTabPIC();
		render = render || isVisTabInterventi();
		render = render || isVisTabDiario();
		render = render || isVisTabAttivitaProfessionali();
		render = render || isVisTabISEE();
		render = render || isVisTabPAI();
		render = render || isVisTabUDC();
		render = render || isVisTabMultidim();
		render = render || isVisTabScuola();
		render = render || isVisTabProvvedimentiMinori();
		render = render || isVisDocIndividuali();
		
		return render;
	}

	public boolean isVisTabPIC() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_PIC);
	}
	public boolean isVisTabInterventi() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_INTERVENTI);
	}
	public boolean isVisTabDiario() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_DIARIO);
	}
	public boolean isVisTabAttivitaProfessionali() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_ATTIVITA_PROFESSIONALI);
	}
	public boolean isVisTabISEE() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_ISEE);
	}
	public boolean isVisTabPAI() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_PAI);
	}
	public boolean isVisTabUDC() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_SCHEDE_UDC);
	}
	public boolean isVisTabMultidim() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_SCHEDA_MULTIDIMENSIONALE);
	}
	public boolean isVisTabValSinba() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_SCHEDA_SINBA);
	}
	public boolean isVisTabScuola() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_DATI_SCUOLA);
	}
	public boolean isVisTabProvvedimentiMinori() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_PROVVEDIMENTI_MINORI);
	}
	public boolean isUploadDocIndividuali() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_DOC_INDIVIDUALI_UP);
	}
	public boolean isDownloadDocIndividuali() {
		return checkPermesso(PermessiFascicolo.ITEM, PermessiFascicolo.TAB_DOC_INDIVIDUALI_DOWN);
	}
	public boolean isVisDocIndividuali(){
		return this.isUploadDocIndividuali() || this.isDownloadDocIndividuali();
	}

	public String getDatiPresaCaricoName() {
		return datiPresaCaricoName;
	}

	public String getFogliAmmName() {
		return fogliAmmName;
	}

	public String getColloquioName() {
		return colloquioName;
	}

	public String getIseeName() {
		return iseeName;
	}

	public String getRelazName() {
		return relazName;
	}

	public String getPaiName() {
		return paiName;
	}

	public String getSchedeSegrName() {
		return schedeSegrName;
	}

	public String getDocIndivName() {
		return docIndivName;
	}

	public String getSchedaMultidimAnzName() {
		return schedaMultidimAnzName;
	}
	
	public String getSchedaValSinbaName() {
		return schedaValSinbaName;
	}

	public DocIndividualiBean getDocIndividualiBean() {
		return docIndividualiBean;
	}

	public void setDocIndividualiBean(DocIndividualiBean docIndividualiBean) {
		this.docIndividualiBean = docIndividualiBean;
	}

	public boolean isAltroSoggetto() {
		return altroSoggetto;
	}

	public void setAltroSoggetto(boolean altroSoggetto) {
		this.altroSoggetto = altroSoggetto;
	}

	public String getDatiScuolaName() {
		return datiScuolaName;
	}

	public boolean isDatiScuola() {
		return isDatiScuola;
	}

	public boolean isDatiScuolaReadOnly() {
		return isDatiScuolaReadOnly;
	}

	public DatiScuolaBean getDatiScuolaBean() {
		return datiScuolaBean;
	}

	public PresaInCaricoBean getPresaInCaricoBean() {
		return presaInCaricoBean;
	}

	public void setPresaInCaricoBean(PresaInCaricoBean presaInCaricoBean) {
		this.presaInCaricoBean = presaInCaricoBean;
	}

	public SchedeSegrBean getSchedeSegrBean() {
		return schedeSegrBean;
	}

	public void setSchedeSegrBean(SchedeSegrBean schedeSegrBean) {
		this.schedeSegrBean = schedeSegrBean;
	}

	public IseeFascBean getIseeBean() {
		return iseeBean;
	}

	public void setIseeBean(IseeFascBean iseeBean) {
		this.iseeBean = iseeBean;
	}

	public ListaProvvedimentiBean getListaProvvedimentiBean() {
		return listaProvvedimentiBean;
	}

	public void setListaProvvedimentiBean(ListaProvvedimentiBean listaProvvedimentiBean) {
		this.listaProvvedimentiBean = listaProvvedimentiBean;
	}

	public String getProvvTribName() {
		return provvTribName;
	}

	public boolean isProvvedimentiMinori() {
		return isProvvedimentiMinori;
	}

	public void setProvvedimentiMinori(boolean isProvvedimentiMinori) {
		this.isProvvedimentiMinori = isProvvedimentiMinori;
	}
	
	public boolean isValSinba() {
		return isValSinba;
	}

	public void setValSinba(boolean isValSinba) {
		this.isValSinba = isValSinba;
	}

	public ListaValMultidimensionaliBean getListaValMultidimensionaliBean() {
		return listaValMultidimensionaliBean;
	}

	public void setListaValMultidimensionaliBean(
			ListaValMultidimensionaliBean listaValMultidimensionaliBean) {
		this.listaValMultidimensionaliBean = listaValMultidimensionaliBean;
	}

	public boolean isProvedimentiMinoriReadOnly() {
		return isProvedimentiMinoriReadOnly;
	}

	public void setProvedimentiMinoriReadOnly(boolean isProvedimentiMinoriReadOnly) {
		this.isProvedimentiMinoriReadOnly = isProvedimentiMinoriReadOnly;
	}
	
	public ListaValSinbaBean getListaValSinbaBean() {
		return listaValSinbaBean;
	}

	public void setListaValSinbaBean(ListaValSinbaBean listaValSinbaBean) {
		this.listaValSinbaBean = listaValSinbaBean;
	}

	public String getDescrizioneSettoreResponsabile() {
		return descrizioneSettoreResponsabile;
	}

	public void setDescrizioneSettoreResponsabile(String descrizioneSettoreResponsabile) {
		this.descrizioneSettoreResponsabile = descrizioneSettoreResponsabile;
	}

	public String getDescrizioneOrganizzazioneResponsabile() {
		return descrizioneOrganizzazioneResponsabile;
	}

	public void setDescrizioneOrganizzazioneResponsabile(
			String descrizioneOrganizzazioneResponsabile) {
		this.descrizioneOrganizzazioneResponsabile = descrizioneOrganizzazioneResponsabile;
	}

	public Boolean getRenderInfoResponsabileCaso() {
		return renderInfoResponsabileCaso;
	}

	public void setRenderInfoResponsabileCaso(Boolean renderInfoResponsabileCaso) {
		this.renderInfoResponsabileCaso = renderInfoResponsabileCaso;
	}
	
	public List<UnitaOrganizzativaDTO> getLstAccessoFascicolo() {
		return lstAccessoFascicolo;
	}

	public void setLstAccessoFascicolo(List<UnitaOrganizzativaDTO> lstAccessoFascicolo) {
		this.lstAccessoFascicolo = lstAccessoFascicolo;
	}

	/*********** End Generic ******************/


}
