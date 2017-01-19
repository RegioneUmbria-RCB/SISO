package it.webred.cs.csa.web.manbean.fascicolo;

import it.webred.cs.csa.ejb.client.*;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RelSettCatsocEsclusivaDTO;
import it.webred.cs.csa.web.manbean.fascicolo.altri.AltriSoggettiBean;
import it.webred.cs.csa.web.manbean.fascicolo.colloquio.ColloquioBean;
import it.webred.cs.csa.web.manbean.fascicolo.docIndividuali.DocIndividualiBean;
import it.webred.cs.csa.web.manbean.fascicolo.interventi.InterventiBean;
import it.webred.cs.csa.web.manbean.fascicolo.isee.IseeFascBean;
import it.webred.cs.csa.web.manbean.fascicolo.pai.PaiBean;
import it.webred.cs.csa.web.manbean.fascicolo.presaincarico.PresaInCaricoBean;
import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ListaProvvedimentiBean;
//import it.webred.cs.csa.web.manbean.fascicolo.provvedimentiMinori.ProvedimentiMinoriTabManBean;
import it.webred.cs.csa.web.manbean.fascicolo.relazioni.RelazioniBean;
import it.webred.cs.csa.web.manbean.fascicolo.schedaMultidimAnz.ManSchedaMultidimAnzBean;
import it.webred.cs.csa.web.manbean.fascicolo.schedeSegr.SchedeSegrBean;
import it.webred.cs.csa.web.manbean.fascicolo.scuola.DatiScuolaBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.*;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
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
	protected List<CsCCategoriaSocialeBASIC> catsocCorrenti;
	protected CsOOperatoreSettore opSettore;
	protected boolean isResponsabile;

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

	protected boolean renderVisualizzaFascicolo;
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

	protected PresaInCaricoBean presaInCaricoBean;
	protected InterventiBean interventiBean;
	protected ColloquioBean colloquioBean;
	protected RelazioniBean relazioniBean;
	protected PaiBean paiBean;
	protected DocIndividualiBean docIndividualiBean;
	protected ManSchedaMultidimAnzBean schedaMultidimAnzBean;
	protected DatiScuolaBean datiScuolaBean;
	protected IseeFascBean iseeBean;
	protected SchedeSegrBean schedeSegrBean;
	protected ListaProvvedimentiBean listaProvvedimentiBean;

	protected boolean altroSoggetto;
	protected AltriSoggettiBean altriSoggettiBean;

	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");

	public void carica(Object soggetto) {
		if (soggetto != null) {
			try {
				boolean redirect = initializeFascicoloCartellaUtente(soggetto, false, null);
				if (redirect)
					FacesContext.getCurrentInstance().getExternalContext().redirect("fascicoloCartellaUtente.faces");
			} catch (IOException e) {
				logger.error("Errore durante il reindirizzamento al Fascicolo Cartella Utente", e);
				addError("Errore", "Errore durante il reindirizzamento al Fascicolo Cartella Utente");
			}
		} else
			addWarningFromProperties("seleziona.warning");

	}

	public boolean initializeFascicoloCartellaUtente(Object soggettoObj, boolean ignoreWarning, List<String> filtroSchede) {
		boolean redirect = false;
		if (soggettoObj != null) {

			AccessTableSchedaSessionBeanRemote schedaService = (AccessTableSchedaSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSessionBean");

			soggetto = (CsASoggettoLAZY) soggettoObj;
			getSession().setAttribute("soggetto", soggettoObj);

			boolean attivi = false;
			boolean casoOpe = false;
			if (soggetto != null) {
				BaseDTO dto = new BaseDTO();
				fillEnte(dto);
				dto.setObj(soggetto.getAnagraficaId());
				dto.setObj2(new CsADatiSociali());
				List<?> listaCs = schedaService.findCsBySoggettoId(dto);

				int i = 0;
				while (i < listaCs.size() && !attivi) {
					CsADatiSociali cs = (CsADatiSociali) listaCs.get(i);
					if (cs.getDataFineApp().after(new Date()))
						attivi = true;
					i++;
				}

				BaseDTO dto1 = new BaseDTO();
				fillEnte(dto1);
				dto1.setObj(soggetto.getCsACaso().getId());
				List<CsACasoOpeTipoOpe> op = schedaService.findCasoOpeTipoOpeByCasoId(dto1);
				for (CsACasoOpeTipoOpe li : op) {
					if (li.getFlagResponsabile() != null && li.getFlagResponsabile())
						casoOpe = true;
				}
			}

			if (attivi) {
				if (casoOpe) {
					opSettore = (CsOOperatoreSettore) getSession().getAttribute("operatoresettore");
					caricaFascicolo(filtroSchede);
					redirect = true;
				} else {
					if (!ignoreWarning)
						addWarningFromProperties("fascicolo.operatoreResponsabile.nonpresente");
				}
			} else {
				if (!ignoreWarning)
					addWarningFromProperties("fascicolo.datisociali.nonpresenti");
			}

		} else
			addWarningFromProperties("seleziona.warning");
		return redirect;
	}

	protected void loadCatSocialiAttuali() {
		// Recupero la categoria sociale
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		b.setObj(soggetto.getAnagraficaId());
		catsocCorrenti = soggettoService.getCatSocAttualiBySoggetto(b);
	}

	private void caricaFascicolo(List<String> filtroSchede) {

		try {

			resetTabPermission();

			// controllo responsabile
			isResponsabile = false;
			AccessTableCasoSessionBeanRemote casoSessionBean = getCasoSessioBean();
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(soggetto.getCsACaso().getId());
			CsOOperatoreBASIC coto = casoSessionBean.findResponsabileBASIC(dto);
			if (coto != null && coto.getUsername().equals(dto.getUserId()))
				isResponsabile = true;
			// se non esiste resp ma ho creato il caso
			if (coto == null && soggetto.getCsACaso().getUserIns().equals(dto.getUserId()))
				isResponsabile = true;

			// Carico le categorieSocialiCorrenti
			loadCatSocialiAttuali();

			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.ITER))
				initializePresaInCaricoTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.FOGLI_AMMINISTRATIVI))
				initializeFogliAmmTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.COLLOQUIO))
				initializeColloquioTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.RELAZIONI))
				initializeRelazioniTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.PAI))
				initializePaiTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.DOC_INDIVIDUALI))
				initializeDocIndividualiTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.SCHEDA_MULTIDIMENSIONALE))
				initializeSchedaMultidimAnzTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.DATI_SCUOLA))
				initializeDatiScuolaTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.SCHEDE_SEGRETARIATO))
				initializeSchedeSegrTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.DATI_ISEE))
				initializeIseeTab(soggetto);
			if (checkFiltroSchede(filtroSchede, DataModelCostanti.TabFascicolo.PROVVEDIMENTI_MINORI))
				initializeProvvMinoriTab(soggetto);

			initializeAltriSoggetti(soggetto);

			isDocIndivReadOnly = !this.isRenderVisualizzaDocIndividuali();

			isIsee = true;
			isIseeReadOnly = false;

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	private boolean checkFiltroSchede(List<String> filtroSchede, String tab) {
		boolean render = true;
		if (filtroSchede != null && !filtroSchede.isEmpty() && !filtroSchede.contains(tab))
			render = false;

		return render;
	}

	public void onTabChange(TabChangeEvent tab) throws Exception {
		String tabName = tab.getTab().getId();
		String clientId = tab.getComponent().getClientId();

		String name = "";

		// se ho caricato un altro soggetto, al cambio tab inizializzo quello
		// originale
		if (tabName.equals(datiPresaCaricoName + "Tab")) {
			name = datiPresaCaricoName;
			if (presaInCaricoBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializePresaInCaricoTab(soggetto);
		}

		if (tabName.equals(fogliAmmName + "Tab")) {
			name = fogliAmmName;
			if (interventiBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeFogliAmmTab(soggetto);
		}

		if (tabName.equals(colloquioName + "Tab")) {
			name = colloquioName;
			if (colloquioBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeColloquioTab(soggetto);
		}

		if (tabName.equals(relazName + "Tab")) {
			name = relazName;
			if (relazioniBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeRelazioniTab(soggetto);
		}

		if (tabName.equals(paiName + "Tab")) {
			name = paiName;
			if (paiBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializePaiTab(soggetto);
		}

		if (tabName.equals(docIndivName + "Tab")) {
			name = docIndivName;
			if (docIndividualiBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeDocIndividualiTab(soggetto);
		}

		if (tabName.equals(schedeSegrName + "Tab")) {
			name = schedeSegrName;
			if (schedeSegrBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeSchedeSegrTab(soggetto);
		}

		if (tabName.equals(schedaMultidimAnzName + "Tab")) {
			name = schedaMultidimAnzName;
			if (schedaMultidimAnzBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeSchedaMultidimAnzTab(soggetto);
		}

		if (tabName.equals(datiScuolaName + "Tab")) {
			name = datiScuolaName;
			if (datiScuolaBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeDatiScuolaTab(soggetto);
		}

		if (tabName.equals(iseeName + "Tab")) {
			name = iseeName;
			if (iseeBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeIseeTab(soggetto);
		}
		if (tabName.equals(provvTribName + "Tab")) {
			name = provvTribName;
			if (this.listaProvvedimentiBean.getCsASoggetto().getAnagraficaId() != soggetto.getAnagraficaId())
				initializeProvvMinoriTab(soggetto);
		}

		getAltriSoggettiBean().setLabelSoggetto(soggetto.getCsAAnagrafica().getCognome() + " " + soggetto.getCsAAnagrafica().getNome());
		RequestContext.getCurrentInstance().update(clientId + ":" + name + "Form");

	}

	private List<CsCCategoriaSocialeBASIC> getCatSocIfSoggPrincipale(CsASoggettoLAZY s) {
		List<CsCCategoriaSocialeBASIC> cslist = this.catsocCorrenti;
		if (s.getAnagraficaId().longValue() != soggetto.getAnagraficaId().longValue())
			cslist = null;
		return cslist;
	}

	public void initializePresaInCaricoTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza PresaInCarico per il caso:" + s.getAnagraficaId());
		presaInCaricoBean = new PresaInCaricoBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		presaInCaricoBean.initialize(s, cslist);
		isDatiPresaCarico = true;
		isDatiPresaCaricoReadOnly = false;
		presaInCaricoBean.setReadOnly(isDatiPresaCaricoReadOnly);
	}

	public void initializeFogliAmmTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza FogliAmm per il caso:" + s.getAnagraficaId());
		interventiBean = new InterventiBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		interventiBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.FOGLIO_AMM_ID, s);
		interventiBean.setReadOnly(isFogliAmmReadOnly);
	}

	public void initializeColloquioTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza Colloquio per il caso:" + s.getAnagraficaId());
		colloquioBean = new ColloquioBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		colloquioBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.COLLOQUIO_ID, s);
		colloquioBean.setReadOnly(isColloquioReadOnly);
	}

	public void initializeRelazioniTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza Relazioni per il caso:" + s.getAnagraficaId());
		relazioniBean = new RelazioniBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		relazioniBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.RELAZIONE_ID, s);
		checkTabPermission(DataModelCostanti.TipoDiario.PAI_ID, s);
		relazioniBean.setReadOnly(isRelazReadOnly);
	}

	public void initializePaiTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza PAI per il caso:" + s.getAnagraficaId());
		paiBean = new PaiBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		paiBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.PAI_ID, s);
		paiBean.setReadOnly(isPaiReadOnly);
	}

	public void initializeDocIndividualiTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza DocIndividuali per il caso:" + s.getAnagraficaId());
		docIndividualiBean = new DocIndividualiBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		docIndividualiBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID, s);
		docIndividualiBean.setReadOnly(isDocIndivReadOnly);
	}

	public void initializeSchedaMultidimAnzTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza SchedaMultidimAnzTab per il caso:" + s.getAnagraficaId());
		schedaMultidimAnzBean = new ManSchedaMultidimAnzBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		schedaMultidimAnzBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID, s);
		schedaMultidimAnzBean.setReadOnly(isSchedaMultidimAnzReadOnly);
	}

	public void initializeIseeTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza SchedaMultidimAnz per il caso:" + s.getAnagraficaId());
		iseeBean = new IseeFascBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		iseeBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.ISEE_ID, s);
		iseeBean.setReadOnly(isIseeReadOnly);
	}

	public void initializeProvvMinoriTab(CsASoggettoLAZY s) {
		logger.info("Inizializza Provvedimenti tribunale per il caso:" + s.getAnagraficaId());
		listaProvvedimentiBean = new ListaProvvedimentiBean(s);
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		listaProvvedimentiBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE, s);
		this.listaProvvedimentiBean.setReadOnly(isProvedimentiMinoriReadOnly);
	}

	public void initializeDatiScuolaTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza DatiScuola per il caso:" + s.getAnagraficaId());
		datiScuolaBean = new DatiScuolaBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		datiScuolaBean.initialize(s, cslist);
		checkTabPermission(DataModelCostanti.TipoDiario.DATI_SCUOLA_ID, s);
		datiScuolaBean.setReadOnly(isDatiScuolaReadOnly);
	}

	public void initializeSchedeSegrTab(CsASoggettoLAZY s) throws Exception {
		logger.info("Inizializza SchedeSegr per il caso:" + s.getAnagraficaId());
		schedeSegrBean = new SchedeSegrBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		schedeSegrBean.initialize(s, cslist);
		isSchedeSegr = true;
		isSchedeSegrReadOnly = false;
		schedeSegrBean.setReadOnly(isSchedeSegrReadOnly);
	}

	public void initializeAltriSoggetti(CsASoggettoLAZY s) {
		logger.info("Inizializza AltriSoggetti");
		altriSoggettiBean = new AltriSoggettiBean();
		List<CsCCategoriaSocialeBASIC> cslist = this.getCatSocIfSoggPrincipale(s);
		altriSoggettiBean.initialize(s, cslist);
	}

	private void resetTabPermission() {
		isFogliAmm = false;
		isColloquio = false;
		isRelaz = false;
		isPai = false;
		isDocIndiv = false;
		isSchedaMultidimAnz = false;
		isDatiScuola = false;
		isProvvedimentiMinori = false;
	}

	/*
	 * protected String getLstIdCatSoc(List<CsCCategoriaSociale>
	 * catsocCorrenti){ String s=null; if(catsocCorrenti!=null &&
	 * !catsocCorrenti.isEmpty()){ for(CsCCategoriaSociale c : catsocCorrenti)
	 * s+=","+c.getId(); s=s.substring(1); } return s; }
	 */

	private void checkTabPermission(int tipoDiarioId, CsASoggettoLAZY sogg) {

		try {

			AccessTableDiarioSessionBeanRemote diarioSessionBean = getDiarioSessioBean();

			BaseDTO dtoSogg = new BaseDTO();
			fillEnte(dtoSogg);
			dtoSogg.setObj(sogg.getAnagraficaId());

			RelSettCatsocEsclusivaDTO relDto = new RelSettCatsocEsclusivaDTO();
			fillEnte(relDto);
			relDto.setCategoriaSocialeId(catsocCorrenti.get(0).getId());
			relDto.setSettoreId(opSettore.getCsOSettore().getId());
			relDto.setTipoDiarioId(new Long(tipoDiarioId));
			CsRelSettCatsocEsclusiva relEsclusivaIds = diarioSessionBean.findRelSettCatsocEsclusivaByIds(relDto);
			List<CsRelSettCatsocEsclusiva> listaRelEsclusivaTipoD = diarioSessionBean.findRelSettCatsocEsclusivaByTipoDiarioId(relDto);

			boolean funzionePresente = listaRelEsclusivaTipoD != null && !listaRelEsclusivaTipoD.isEmpty();
			boolean funzioneSettCatsocPresente = relEsclusivaIds != null;
			boolean canModifica = checkPermesso(DataModelCostanti.PermessiCaso.MODIFICA_CASI_SETTORE) || isResponsabile;
			boolean existsDatiStorici = false;

			// il Tab è visibile solo se non sono specificati permessi per il
			// tipo di diario o sono già presenti dei dati
			// la modifica è abilitata solo se è presente il permesso composto
			// da TipoDiario/CatSociale/Settore
			switch (tipoDiarioId) {
			case DataModelCostanti.TipoDiario.FOGLIO_AMM_ID:

				isFogliAmmReadOnly = true;
				existsDatiStorici = interventiBean.getListaInterventi() != null && !interventiBean.getListaInterventi().isEmpty();
				if (funzioneSettCatsocPresente || !funzionePresente) {
					isFogliAmm = true;
					if (canModifica)
						isFogliAmmReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isFogliAmm = true;

				break;

			case DataModelCostanti.TipoDiario.COLLOQUIO_ID:

				isColloquioReadOnly = true;
				existsDatiStorici = colloquioBean.getListaColloquios() != null && !colloquioBean.getListaColloquios().isEmpty();
				if (funzioneSettCatsocPresente || !funzionePresente) {
					isColloquio = true;
					if (canModifica)
						isColloquioReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isColloquio = true;

				break;

			case DataModelCostanti.TipoDiario.RELAZIONE_ID:

				isRelazReadOnly = true;
				existsDatiStorici = relazioniBean.getListaRelazioniDTO() != null && !relazioniBean.getListaRelazioniDTO().isEmpty();
				if (funzioneSettCatsocPresente || !funzionePresente) {
					isRelaz = true;
					if (canModifica)
						isRelazReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isRelaz = true;

				break;

			case DataModelCostanti.TipoDiario.PAI_ID:

				isPaiReadOnly = true;
				if (funzioneSettCatsocPresente || !funzionePresente) {
					isPai = true;
					if (canModifica)
						isPaiReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isPai = true;

				/*
				 relazioniBean.setDisableNuovoPAI(true);
				 if ((funzioneSettCatsocPresente || !funzionePresente) && canModifica)
					relazioniBean.setDisableNuovoPAI(false);
				*/
				break;

			case DataModelCostanti.TipoDiario.DOC_INDIVIDUALE_ID:

				isDocIndivReadOnly = true;
				existsDatiStorici = (docIndividualiBean.getListaDocIndividualiPubblica() != null && !docIndividualiBean.getListaDocIndividualiPubblica().isEmpty()) || 
						(docIndividualiBean.getListaDocIndividualiPrivata() != null && !docIndividualiBean.getListaDocIndividualiPrivata().isEmpty());

				if (funzioneSettCatsocPresente || !funzionePresente) {
					isDocIndiv = true;
					if (canModifica)
						isDocIndivReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isDocIndiv = true;

				break;

			case DataModelCostanti.TipoDiario.VALUTAZIONE_MDS_ID:

				isSchedaMultidimAnzReadOnly = true;
				existsDatiStorici = schedaMultidimAnzBean.getListaSchedeMultidims() != null && !schedaMultidimAnzBean.getListaSchedeMultidims().isEmpty();
				if (funzioneSettCatsocPresente || !funzionePresente) {
					isSchedaMultidimAnz = true;
					if (canModifica)
						isSchedaMultidimAnzReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isSchedaMultidimAnz = true;

				break;

			case DataModelCostanti.TipoDiario.DATI_SCUOLA_ID:

				isDatiScuolaReadOnly = true;
				existsDatiStorici = (datiScuolaBean.getListaScuole() != null && !datiScuolaBean.getListaScuole().isEmpty());

				if (funzioneSettCatsocPresente || !funzionePresente) {
					isDatiScuola = true;
					if (canModifica)
						isDatiScuolaReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isDatiScuola = true;

				break;

			case DataModelCostanti.TipoDiario.ISEE_ID:

				isIseeReadOnly = true;
				existsDatiStorici = (iseeBean.getListaIsee() != null && !iseeBean.getListaIsee().isEmpty());

				if (funzioneSettCatsocPresente || !funzionePresente) {
					isIsee = true;
					if (canModifica)
						isIseeReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isIsee = true;

				break;

			case DataModelCostanti.TipoDiario.PROVVEDIMENTI_TRIBUNALE:

				isProvedimentiMinoriReadOnly = true;
				existsDatiStorici = this.listaProvvedimentiBean.existsDatiStorici();

				if (funzioneSettCatsocPresente || !funzionePresente) {
					isProvvedimentiMinori = true;
					if (canModifica)
						isProvedimentiMinoriReadOnly = false;
				} else if (funzionePresente && existsDatiStorici)
					isProvvedimentiMinori = true;

				break;
			}

		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(), e);
		}
	}

	protected AccessTableDiarioSessionBeanRemote getDiarioSessioBean() throws NamingException {
		AccessTableDiarioSessionBeanRemote sessionBean = (AccessTableDiarioSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableDiarioSessionBean");
		return sessionBean;
	}

	protected AccessTableCasoSessionBeanRemote getCasoSessioBean() throws NamingException {
		AccessTableCasoSessionBeanRemote sessionBean = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
		return sessionBean;
	}

	protected AccessTableConfigurazioneSessionBeanRemote getConfigurazioneSessioBean() throws NamingException {
		AccessTableConfigurazioneSessionBeanRemote sessionBean = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
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

	public ManSchedaMultidimAnzBean getSchedaMultidimAnzBean() {
		return schedaMultidimAnzBean;
	}

	public void setSchedaMultidimAnzBean(ManSchedaMultidimAnzBean schedaMultidimAnzBean) {
		this.schedaMultidimAnzBean = schedaMultidimAnzBean;
	}

	public AltriSoggettiBean getAltriSoggettiBean() {
		return altriSoggettiBean;
	}

	public void setAltriSoggettiBean(AltriSoggettiBean altriSoggettiBean) {
		this.altriSoggettiBean = altriSoggettiBean;
	}

	public boolean isRenderVisualizzaFascicolo() {
		return checkPermesso(DataModelCostanti.PermessiFascicolo.VISUALIZZAZIONE_FASCICOLO);
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

	/*********** End Generic ******************/

}
