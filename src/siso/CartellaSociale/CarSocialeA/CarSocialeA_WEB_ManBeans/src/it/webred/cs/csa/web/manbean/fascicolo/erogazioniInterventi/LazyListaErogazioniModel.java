package it.webred.cs.csa.web.manbean.fascicolo.erogazioniInterventi;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ejb.utility.ClientUtility;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * <h1>LazyListaErogazioniModel.java</h1>
 *
 * <p>
 * </p>
 *
 * @since 1.26.12
 * @version 1.0.2
 * 
 * @lastUpdate 2025-02-04 - DDV
 */
public class LazyListaErogazioniModel extends LazyDataModel<ErogInterventoRowBean> {

	private int tipoFiltroInterventiSelezionato;
	private Date dataUltimaErogazione;
	private Date dataEventoUltimaErogazione;
	private Long[] selectedTipoIntervento;
	private Long[] selectedTipoInterventoCustom;
	private String[] selectedTipoBeneficiario;
	private String[] selectedTipoInterventoInps; //SISO-1162
	private String selectedSoggettoErogazioneCF;
	
	private boolean loadAll = false;

	// Usato per gestire i filtri, se inseriti o meno
	private boolean firstLoad = false;
	
	// Usato per gestire se il tab può essere caricato tutto, se true non ho blocchi nel tab
	public boolean loadTab = false;
	
	// Usato per gestire il messaggio di return in emptyMessage
	public boolean empyMessageReturn = false;
	
	public boolean downloadFromExcel = false;
	
	//SISO-381
	String sortField;
	SortOrder sortOrder;
	Map filters;

	public LazyListaErogazioniModel(int preselected) {
		tipoFiltroInterventiSelezionato = preselected;
	}

	@Override
	public ErogInterventoRowBean getRowData(String rowKey) {
		//TODO
		return null;
	}

	@Override
	public Object getRowKey(ErogInterventoRowBean erogazione) {
		return erogazione.getIdRow();
	}

	/**
	 * 
	 * <h1>load</h1>
	 *
	 * <p>
	 * Metodo main per il caricamento della lista del risultato del tab "Erogazioni Interventi"
	 * </p>
	 *
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return
	 *
	 * @since 1.26.12
	 * @version 1.0.2
	 * 
	 * @author DDV
	 * @lastUpdate 2025-03-14 - DDV
	 */
	@Override
	public List<ErogInterventoRowBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {

		//SISO-381
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.filters = filters;

		String codiceBelfiore = StringUtils.EMPTY;
		
		boolean loadAllTab = true;
		
		List<ErogInterventoRowBean> listaInterventi = new LinkedList<ErogInterventoRowBean>();

		CsOOperatoreSettore csOOperatoreSettore = CsUiCompBaseBean.getCurrentOpSettore();
		
		ErogazioniSearchCriteria erogazioniSearchCriteria = new ErogazioniSearchCriteria();
		CsUiCompBaseBean.fillEnte(erogazioniSearchCriteria);
		
		erogazioniSearchCriteria.setSettoreId(csOOperatoreSettore.getCsOSettore().getId());
		erogazioniSearchCriteria.setOrganizzazioneId(csOOperatoreSettore.getCsOSettore().getCsOOrganizzazione().getId());
		erogazioniSearchCriteria.setFirst(first);
		erogazioniSearchCriteria.setPageSize(pageSize);
		erogazioniSearchCriteria.setPermessoAutorizzativo(CsUiCompBaseBean.isPermessoAutorizzativo());
		erogazioniSearchCriteria.setSearchConRichiesta(this.tipoFiltroInterventiSelezionato == DataModelCostanti.ListaErogazioni.CON_RICHIESTA);
		erogazioniSearchCriteria.setSearchErogatiNoIntervento(this.tipoFiltroInterventiSelezionato == DataModelCostanti.ListaErogazioni.SENZA_RICHIESTA);

		List<ErogInterventoRowBean> listaInterventiAll = new LinkedList<ErogInterventoRowBean>();

		this.elaboraCriteriFiltro(filters, erogazioniSearchCriteria);

		if (!StringUtils.isBlank(this.selectedSoggettoErogazioneCF))
			erogazioniSearchCriteria.setCodiceFiscale(this.selectedSoggettoErogazioneCF);

		if (this.dataUltimaErogazione != null)
			erogazioniSearchCriteria.setDataErogazione(this.dataUltimaErogazione);

		if (this.dataEventoUltimaErogazione != null)
			erogazioniSearchCriteria.setDataEvento(this.dataEventoUltimaErogazione);

		if (this.selectedTipoIntervento != null && this.selectedTipoIntervento.length > 0)
			erogazioniSearchCriteria.setLstTipoIntervento(this.selectedTipoIntervento);

		if (this.selectedTipoInterventoCustom != null && this.selectedTipoInterventoCustom.length > 0)
			erogazioniSearchCriteria.setLstTipoInterventoCustom(this.selectedTipoInterventoCustom);
			
		//SISO-1162
		if (this.selectedTipoInterventoInps != null && this.selectedTipoInterventoInps.length > 0)
			erogazioniSearchCriteria.setLstTipoInterventoInps(this.selectedTipoInterventoInps);

		if (this.selectedTipoBeneficiario != null && this.selectedTipoBeneficiario.length > 0)
			erogazioniSearchCriteria.setLstTipoBeneficiario(this.selectedTipoBeneficiario);
		
		try {
			
			AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA",
					"CarSocialeA_EJB", "AccessTableInterventoSessionBean");
			
			ParameterService parameterService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			
			/**
			 * Recupero gli interventi eseguiti, privi di foglio amministrativo che abbiano una delle seguenti caratteristiche:
			 * 1. settore dell'operatore che l'ha inserito = corrente
			 * 2. organizzazione dell'erogazione = corrente
			 * 3. organizzazione del caso (recuperabile mediante CF) = corrente
			 */
			boolean loadDettaglioErogazione = true;
			erogazioniSearchCriteria.setLoadDettaglioErogazione(loadDettaglioErogazione);

			AmKeyValueExt amKeyValueExt = this.getAmKeyValueExt(parameterService);
			
			// Check che esista il valore e non sia vuoto, altrimenti il default è false
			if (amKeyValueExt != null && amKeyValueExt.getValueConf() != null && !amKeyValueExt.getValueConf().trim().isEmpty()) {
			    
			    // Splitto la lista di codici Belfiore
			    List<String> excludedCodesForLoadingResult = Arrays.asList(amKeyValueExt.getValueConf().split(";"));
			    
			    if (csOOperatoreSettore.getCsOSettore() != null && csOOperatoreSettore.getCsOSettore().getCsOOrganizzazione() != null) {
			    	codiceBelfiore = this.getCodiceBelfioreFromOrganizzazione(csOOperatoreSettore);
			    }
		    	
			    // Controlla se la lista contiene il codice, se è presente non carico il tab
			    if (excludedCodesForLoadingResult.contains(codiceBelfiore)) {
			        loadAllTab = false;
			    }
			    
			}
			
			// Check se non ha i filtri
			this.firstLoad = !this.hasCriteria(erogazioniSearchCriteria);

			boolean hasFilter = this.hasCriteria(erogazioniSearchCriteria);
			
			boolean hasFilterSelect = this.hasCriteriaSelect(erogazioniSearchCriteria);
			
			// Se esistono i filtri e / o ho cliccato carica tutti, proseguo con le logiche già esistenti
			if (this.downloadFromExcel || (this.isLoadAll() || hasFilter || this.loadTab(this.firstLoad, loadAllTab))) {
				
				if (this.isLoadAll() && hasFilterSelect) {
					erogazioniSearchCriteria = this.clearFilters(erogazioniSearchCriteria);
				}
				
				if (this.isLoadAll() && this.firstLoad) {
					erogazioniSearchCriteria = this.clearFilters(erogazioniSearchCriteria);
				}
				
				//rowCount
				Integer dataSize = interventoService.countListaErogInterventiBySettore(erogazioniSearchCriteria);
				setRowCount(dataSize);

				List<ErogazioneMasterDTO> listErogazioneMasterDTO = interventoService.searchListaErogInterventi(erogazioniSearchCriteria);
				for (ErogazioneMasterDTO erogazioneMasterDTO : listErogazioneMasterDTO) {
					ErogInterventoRowBean erogInterventoRowBean = new ErogInterventoRowBean(erogazioneMasterDTO, true);
					listaInterventiAll.add(erogInterventoRowBean);
				}
	
				listaInterventi = listaInterventiAll;
				
			}
			
			if (!loadAllTab) {
				this.loadTab = false;
				//this.setLoadAll(false);
			}
			
			if (hasFilter && !this.firstLoad) {
				this.setLoadAll(false);
			}

			// Viene gestita la visibilità del messaggio, non ci sono result o devono essere selezionati i filtri
			this.empyMessageReturn = this.checkMessageReturn(listaInterventi);
			
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}

		return listaInterventi;
	}

	/**
	 * 
	 * <h1>checkMessageReturn</h1>
	 *
	 * <p>
	 * Metodo per gestire il messaggio di return:<br>
	 * TRUE: Nessun intervento erogato o da erogare<br>
	 * FALSE: Selezionare almeno un filtro di ricerca per mostrare gli interventi
	 * </p>
	 *
	 * @return
	 *
	 * @since 1.26.12
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-02-03 - DDV
	 */
	private boolean checkMessageReturn(List<ErogInterventoRowBean> listaInterventi) {
		
		boolean empyMessageReturn = false;
		
		if (this.loadTab) {
			empyMessageReturn = true;
			if (listaInterventi == null || listaInterventi.size() <= 0) {
				empyMessageReturn = true;
			}
		} else if (!this.loadTab && !this.firstLoad && (listaInterventi == null || listaInterventi.size() <= 0)) {
			empyMessageReturn = true;
		} else {
			empyMessageReturn = false;
		}
		
		return empyMessageReturn;
		
	}
	
	/**
	 * 
	 * <h1>clearFilters</h1>
	 *
	 * <p>
	 * Metodo per pulire i filtri
	 * </p>
	 *
	 * @param searchCriteria
	 * @return
	 *
	 * @since 1.26.12
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-02-03 - DDV
	 */
	public ErogazioniSearchCriteria clearFilters(ErogazioniSearchCriteria searchCriteria) {

		searchCriteria.setDenominazione(null);
		searchCriteria.setStatoErogazione(null);
		searchCriteria.setLineaFinanziamento(null);
		searchCriteria.setDescCatSociale(null);
		searchCriteria.setCodiceFiscale(null);
		
		searchCriteria.setDataErogazione(null);
		searchCriteria.setDataEvento(null);
		searchCriteria.setLstTipoIntervento(null);
		searchCriteria.setLstTipoInterventoCustom(null);
		searchCriteria.setLstTipoInterventoInps(null);
		searchCriteria.setLstTipoBeneficiario(null);
		
		return searchCriteria;
	}
	
	/**
	 * 
	 * <h1>loadTab</h1>
	 *
	 * <p>
	 * Metodo per gestire se il tab deve caricare tutti i risultati o meno
	 * </p>
	 *
	 * @param firstLoad
	 * @param loadAllTab
	 * @return
	 *
	 * @since 1.26.12
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-01-27 - DDV
	 */
	private boolean loadTab(boolean firstLoad, boolean loadAllTab) {
		
		this.loadTab = !firstLoad || loadAllTab;
		
		return this.loadTab;
	}
	
	/**
	 * 
	 * <h1>getCodiceBelfioreFromOrganizzazione</h1>
	 *
	 * <p>
	 * Recupero il codice belfiore, se esiste dalla sede legale altrimento dal codice routing
	 * </p>
	 *
	 * @param opSettore
	 * @return
	 *
	 * @since 1.26.12
	 * @version 1.0.1
	 * 
	 * @author DDV
	 * @lastUpdate 2025-01-28 - DDV
	 */
	private String getCodiceBelfioreFromOrganizzazione(CsOOperatoreSettore opSettore) {
		
		return StringUtils.isBlank(opSettore.getCsOSettore().getCsOOrganizzazione().getCodCatastale())
				? opSettore.getCsOSettore().getCsOOrganizzazione().getCodRouting()
				: opSettore.getCsOSettore().getCsOOrganizzazione().getCodCatastale();
				
	}

	/**
	 * 
	 * <h1>getAmKeyValueExt</h1>
	 *
	 * <p>
	 * Recupero variabile smartwelfare.codiciEsclusi.ricercaErogazioniInterventi in AM
	 * </p>
	 *
	 * @param paramService
	 * @return
	 *
	 * @since 1.26.12
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-01-22 - DDV
	 */
	private AmKeyValueExt getAmKeyValueExt(ParameterService paramService) {
		
		ParameterSearchCriteria parameterSearchCriteria = new ParameterSearchCriteria();
		parameterSearchCriteria.setKey(DataModelCostanti.AmParameterKey.CODICI_ESCLUSI_TAB_EROGAZIONI_INTERVENTI);

		AmKeyValueExt amKeyValueExt = paramService.getAmKeyValueExt(parameterSearchCriteria);
	
		return amKeyValueExt;
	
	}

	private boolean hasCriteriaSelect(ErogazioniSearchCriteria searchCriteria) {
		
		if ((searchCriteria.getLstTipoIntervento() != null && searchCriteria.getLstTipoIntervento().length > 0)
				|| (searchCriteria.getLstTipoInterventoCustom() != null && searchCriteria.getLstTipoInterventoCustom().length > 0)
				|| (searchCriteria.getLstTipoInterventoInps() != null && searchCriteria.getLstTipoInterventoInps().length > 0)
				|| (searchCriteria.getLstTipoBeneficiario() != null && searchCriteria.getLstTipoBeneficiario().length > 0)) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/**
	 * 
	 * <h1>hasCriteria</h1>
	 *
	 * <p>
	 * Metodo per il check dei criteria inseriti nella form.
	 * Viene chiamato per controllare che se non sono inseriti i filtri non devo eseguire delle logiche.
	 * </p>
	 *
	 * @param searchCriteria
	 * @return
	 *
	 * @since 1.26.12
	 * @version 1.0.1
	 * 
	 * @author DDV
	 * @lastUpdate 2025-01-23 - DDV
	 */
	private boolean hasCriteria(ErogazioniSearchCriteria searchCriteria) {
		
		if (!StringUtils.isBlank(searchCriteria.getCodiceFiscale())
				|| searchCriteria.getDataErogazione() != null
				|| searchCriteria.getDataEvento() != null
				|| searchCriteria.getDescCatSociale() != null
				|| (searchCriteria.getLstTipoIntervento() != null && searchCriteria.getLstTipoIntervento().length > 0)
				|| (searchCriteria.getLstTipoInterventoCustom() != null && searchCriteria.getLstTipoInterventoCustom().length > 0)
				|| (searchCriteria.getLstTipoInterventoInps() != null && searchCriteria.getLstTipoInterventoInps().length > 0)
				|| (searchCriteria.getLstTipoBeneficiario() != null && searchCriteria.getLstTipoBeneficiario().length > 0)
				|| searchCriteria.getDenominazione() != null
				|| searchCriteria.getStatoErogazione() != null
				|| searchCriteria.getLineaFinanziamento() != null) {
			return true;
		} else {
			return false;
		}
		
	}
	
	protected void elaboraCriteriFiltro(Map filters, ErogazioniSearchCriteria searchCriteria) {
		
		if (filters != null) {
			
			String denominazione = (String) filters.get("denominazione");
			String cf = (String) filters.get("cf");
			String statoErogazione = (String) filters.get("statoErogazione");
			String lineaFinanziamento = (String) filters.get("lineaFinanziamento");
			String catSociale = (String) filters.get("descCategoriaSociale");

			searchCriteria.setDenominazione(denominazione != null ? denominazione : null);
			searchCriteria.setStatoErogazione(statoErogazione != null ? statoErogazione : null);
			searchCriteria.setLineaFinanziamento(lineaFinanziamento != null ? lineaFinanziamento : null);
			searchCriteria.setDescCatSociale(catSociale);
			searchCriteria.setCodiceFiscale(cf);
			
		}
		
	}

	public int getTipoFiltroInterventiSelezionato() {
		return tipoFiltroInterventiSelezionato;
	}

	public void setTipoFiltroInterventiSelezionato(int tipoFiltroInterventiSelezionato) {
		this.tipoFiltroInterventiSelezionato = tipoFiltroInterventiSelezionato;
	}

	public Long[] getSelectedTipoIntervento() {
		return selectedTipoIntervento;
	}

	public void setSelectedTipoIntervento(Long[] selectedTipoIntervento) {
		this.selectedTipoIntervento = selectedTipoIntervento;
	}

	public Long[] getSelectedTipoInterventoCustom() {
		return selectedTipoInterventoCustom;
	}

	public void setSelectedTipoInterventoCustom(Long[] selectedTipoInterventoCustom) {
		this.selectedTipoInterventoCustom = selectedTipoInterventoCustom;
	}

	public Date getDataUltimaErogazione() {
		return dataUltimaErogazione;
	}

	public void setDataUltimaErogazione(Date dataUltimaErogazione) {
		this.dataUltimaErogazione = dataUltimaErogazione;
	}

	//SISO-1162
	public String[] getSelectedTipoInterventoInps() {
		return selectedTipoInterventoInps;
	}

	public void setSelectedTipoInterventoInps(String[] selectedTipoInterventoInps) {
		this.selectedTipoInterventoInps = selectedTipoInterventoInps;
	}

	public String[] getSelectedTipoBeneficiario() {
		return selectedTipoBeneficiario;
	}

	public void setSelectedTipoBeneficiario(String[] selectedTipoBeneficiario) {
		this.selectedTipoBeneficiario = selectedTipoBeneficiario;
	}

	public String getSelectedSoggettoErogazioneCF() {
		return selectedSoggettoErogazioneCF;
	}

	public void setSelectedSoggettoErogazioneCF(String selectedSoggettoErogazioneCF) {
		this.selectedSoggettoErogazioneCF = selectedSoggettoErogazioneCF;
	}

	public Date getDataEventoUltimaErogazione() {
		return dataEventoUltimaErogazione;
	}

	public void setDataEventoUltimaErogazione(Date dataEventoUltimaErogazione) {
		this.dataEventoUltimaErogazione = dataEventoUltimaErogazione;
	}

	public boolean isLoadTab() {
		return loadTab;
	}

	public void setLoadTab(boolean loadTab) {
		this.loadTab = loadTab;
	}

	public boolean isLoadAll() {
		return loadAll;
	}

	public void setLoadAll(boolean loadAll) {
		this.loadAll = loadAll;
	}
	
	public boolean getEmpyMessageReturn() {
		return empyMessageReturn;
	}

	public void setEmpyMessageReturn(boolean empyMessageReturn) {
		this.empyMessageReturn = empyMessageReturn;
	}
	
}