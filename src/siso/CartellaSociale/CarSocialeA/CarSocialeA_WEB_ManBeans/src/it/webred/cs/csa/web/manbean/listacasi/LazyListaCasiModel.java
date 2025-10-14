package it.webred.cs.csa.web.manbean.listacasi;

import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.configurazione.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.FiltroCasi;
import it.webred.cs.data.DataModelCostanti.PermessiCartella;
import it.webred.cs.data.model.CsACasoAccessoFascicolo;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 * 
 * <h1>LazyListaCasiModel.java</h1>
 *
 * <p>
 * </p>
 *
 * @since 1.26.13
 * @version 1.0.0
 * 
 * @lastUpdate 2025-09-18 - DDV
 */
public class LazyListaCasiModel extends LazyDataModel<DatiCasoBean> {
     
	private static final long serialVersionUID = 1L;

	// SISO-641
	String sortField;
	SortOrder sortOrder;
	Map filters;

	private boolean loadAll = false;

	// Usato per gestire i filtri, se inseriti o meno
	private boolean firstLoad = false;
	
	// Usato per gestire se il tab può essere caricato tutto, se true non ho blocchi nel tab
	public boolean loadTab = false;
	
	// Usato per gestire il messaggio di return in emptyMessage
	public boolean empyMessageReturn = false;
	
	public boolean downloadFromExcel = false;
	
	@Override
    public DatiCasoBean getRowData(String rowKey) { 
		//TODO
        return null;
    }
  
    @Override
    public Object getRowKey(DatiCasoBean datiCaso) {
        return datiCaso.getAnagraficaId();
    }
    
    private String getCategorieId(String desc) {
    	try {

			AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableCatSocialeSessionBean");
			BaseDTO bdto = new BaseDTO();
			CsUiCompBaseBean.fillEnte(bdto);
			bdto.setObj(desc);
			List<CsCCategoriaSociale> lstCatSoc = (List<CsCCategoriaSociale>) catSocService.getCategorieSocialiByDesc(bdto);

			if (lstCatSoc != null && !lstCatSoc.isEmpty()) {
				String lstIds = "";
				for (CsCCategoriaSociale cs : lstCatSoc)
					lstIds += "," + Long.toString(cs.getId());
				lstIds = lstIds.substring(1);
				return lstIds;
			}

		} catch (Exception e) {
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}

		return null;
	}
    
	/**
	 * 
	 * <h1>load</h1>
	 *
	 * <p>
	 * Metodo main per il caricamento della lista del risultato del tab "Lista Casi"
	 * </p>
	 *
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return
	 *
	 * @since 1.26.13
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-09-18 - DDV
	 */
	@Override
	public List<DatiCasoBean> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map filters) {
		CsUiCompBaseBean.logger.debug("*** Load List<DatiCasoBean>: " + new Date());

		List<DatiCasoBean> data = new ArrayList<DatiCasoBean>();

		PaginationDTO dto = new PaginationDTO();

		// SISO-641
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.filters = filters;

		CsUiCompBaseBean.fillEnte(dto);
		CasoSearchCriteria searchCriteria = new CasoSearchCriteria();
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore) CsUiCompBaseBean.getSession().getAttribute("operatoresettore");
		if (opSettore != null) {
			//DEFAULT: filtro solo i casi dove sono presente come tipo operatore e con il settore scelto o quelli segnalatimi
			searchCriteria.setUsername(dto.getUserId());
			searchCriteria.setIdOperatore(opSettore.getCsOOperatore().getId());
			searchCriteria.setIdSettore(opSettore.getCsOSettore().getId());
			searchCriteria.setIdOrganizzazione(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
			//PERMESSO CASI SETTORE: 
			if (CsUiCompBaseBean.checkPermesso(PermessiCartella.ITEM, PermessiCartella.VISUALIZZAZIONE_CASI_SETTORE))
				searchCriteria.setPermessoCasiSettore(true);
			//PERMESSO CASI ORGANIZZAZIONE: 
			if (CsUiCompBaseBean.checkPermesso(PermessiCartella.ITEM, PermessiCartella.VISUALIZZAZIONE_CASI_ORG))
				searchCriteria.setPermessoCasiOrganizzazione(true);
		}
		
		String filterSoggetto = (String) filters.get("soggetto");
		String filterDataNascita = (String) filters.get("dataNascita");
		String filterCF = (String) filters.get("codiceFiscale");
		String filterCatSociale = (String) filters.get("catSociale");
		String filterDataApertura = (String) filters.get("dataApertura");
		
		if (filterSoggetto != null)
			searchCriteria.setDenominazione(filterSoggetto);
		if (filterDataNascita != null)
			searchCriteria.setDataNascita(filterDataNascita);
		if (filterCF != null)
			searchCriteria.setCodiceFiscale(filterCF);
		if (filterCatSociale != null) {
			String lstCatSociale = this.getCategorieId(filterCatSociale);
			if (lstCatSociale != null)
				searchCriteria.setLstCatSociale(lstCatSociale);
		}
		if (filterDataApertura != null)
			searchCriteria.setDataApertura(filterDataApertura);

		String criteriaStati = (String) getSession().getAttribute(FiltroCasi.STATO);
		if (criteriaStati != null && !criteriaStati.isEmpty())
			searchCriteria.setLstStati(criteriaStati);
		
		String criteriaOpSegnalazione = (String) getSession().getAttribute(FiltroCasi.STATO_OPERATORE);
		if (criteriaOpSegnalazione != null && !criteriaOpSegnalazione.isEmpty())
			searchCriteria.setIdOperatoreIter(new Long(criteriaOpSegnalazione));
		
 		String tipoRes = (String) getSession().getAttribute(FiltroCasi.RESIDENZA_TIPO);
		String nazioneRes = (String) getSession().getAttribute(FiltroCasi.RESIDENZA_NAZIONE);
		String comuneRes = (String) getSession().getAttribute(FiltroCasi.RESIDENZA_COMUNE);
		if (!StringUtils.isBlank(tipoRes)){
			if (tipoRes.equalsIgnoreCase("NAZIONE") && !StringUtils.isBlank(nazioneRes))
				searchCriteria.setResidenzaNazione(nazioneRes);
			else if (tipoRes.equalsIgnoreCase("COMUNE") && !StringUtils.isBlank(comuneRes))
				searchCriteria.setResidenzaComune(comuneRes);
			searchCriteria.setSenzaFissaDimora(tipoRes.equalsIgnoreCase("SFD"));
		}

		String criteriaOperatore = (String) getSession().getAttribute(FiltroCasi.OPERATORE);
		if (criteriaOperatore != null && !criteriaOperatore.isEmpty()) {
			searchCriteria.setIdOperatoreAltro(new Long(criteriaOperatore));
			String tipoOperatore = (String) getSession().getAttribute(FiltroCasi.TIPO_OPERATORE);
			searchCriteria.setOpResponsabile(null);
			if (tipoOperatore != null && !tipoOperatore.equals("TUTTI"))
				searchCriteria.setOpResponsabile(tipoOperatore.equalsIgnoreCase("RESP"));

			// searchCriteria.setSoloOperatoreNR(nr!=null && nr ? true : false);
		}
		
		Long criteriaStudio = (Long)getSession().getAttribute(FiltroCasi.STUDIO);
		if (criteriaStudio!=null && criteriaStudio>0)
			searchCriteria.setTitStudioId(criteriaStudio);
		
		Long criteriaLavoro = (Long)getSession().getAttribute(FiltroCasi.LAVORO);
		if (criteriaLavoro!=null && criteriaLavoro>0)
			searchCriteria.setCondLavoroId(criteriaLavoro);
		
		String criteriaTutela = (String)getSession().getAttribute(FiltroCasi.TUTELA);
		if (criteriaTutela!=null && !criteriaTutela.isEmpty())
			searchCriteria.setTipoTutela(criteriaTutela);

		String[] criteriaTribunale = (String[]) getSession().getAttribute(FiltroCasi.TRIBUNALE);
		if (criteriaTribunale != null && criteriaTribunale.length > 0)
			searchCriteria.setTribunale(criteriaTribunale);

		// se devo filtrare per ass sociale eseguo manualmente la paginazione
		// if(filterAssSociale == null) {
		dto.setFirst(first);
		dto.setPageSize(pageSize);
		// }

		// SISO-812
		searchCriteria.setPermessiScheda(CsUiCompBaseBean.isProvenienzaCasiAssegnati());
		
		dto.setObj(searchCriteria);

		AmKeyValueExt amKeyValueExt = CsUiCompBaseBean.getAmKeyValueExt(DataModelCostanti.AmParameterKey.CODICI_ESCLUSI_TAB_EROGAZIONI_INTERVENTI);
		
		boolean loadAllTab = true;
		String codiceBelfiore = StringUtils.EMPTY;
		
		// Check che esista il valore e non sia vuoto, altrimenti il default è false
		if (amKeyValueExt != null && amKeyValueExt.getValueConf() != null && !amKeyValueExt.getValueConf().trim().isEmpty()) {
		    
		    // Splitto la lista di codici Belfiore
		    List<String> excludedCodesForLoadingResult = Arrays.asList(amKeyValueExt.getValueConf().split(";"));
		    
		    if (opSettore.getCsOSettore() != null && opSettore.getCsOSettore().getCsOOrganizzazione() != null) {
		    	codiceBelfiore = this.getCodiceBelfioreFromOrganizzazione(opSettore);
		    }
	    	
		    // Controlla se la lista contiene il codice, se è presente non carico il tab
		    if (excludedCodesForLoadingResult.contains(codiceBelfiore)) {
		        loadAllTab = false;
		    }
		    
		}
		
		// Check se non ha i filtri
		this.firstLoad = !this.hasCriteria(searchCriteria);

		boolean hasFilter = this.hasCriteria(searchCriteria);
		
		// Se esistono i filtri e / o ho cliccato carica tutti, proseguo con le logiche già esistenti
		if (this.downloadFromExcel || (this.loadTab(this.firstLoad, loadAllTab) || this.isLoadAll() || hasFilter)) {
			
			if (this.isLoadAll() && this.firstLoad) {
				searchCriteria = this.clearFilters(searchCriteria);
			}
			
			try {
				
				AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
		
				List<DatiCasoListaDTO> list = soggettiService.getListaCasiSoggetto(dto);
				
				//loadDatiCaso(list, data);
				
				for(DatiCasoListaDTO dc: list){
					DatiCasoBean bean = new DatiCasoBean(dc);
					data.add(bean);
				}
				
				// rowCount
				Integer dataSize;
				
				dataSize = soggettiService.getListaCasiSoggettoCount(dto);
				setRowCount(dataSize);
	
		    	CsUiCompBaseBean.logger.debug("Fine Load<DatiCasoBean>:" + new Date());
				
				//filtro lista casi se provengo da lista casi assegnati
				try {
					if (searchCriteria.getPermessiScheda() != null && searchCriteria.getPermessiScheda()) {
						Long idSettore = opSettore.getCsOSettore().getId();
					    Long idOrganizzazione = opSettore.getCsOSettore().getCsOOrganizzazione().getId();

					    AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableCasoSessionBean");
						BaseDTO bDto = new BaseDTO();
						CsUiCompBaseBean.fillEnte(bDto);
						bDto.setObj(idOrganizzazione);
						bDto.setObj2(idSettore);
						List<CsACasoAccessoFascicolo> cf =casoService.findAccessoFascicoloByIdOrganizzazioneAndIdSettore(bDto);
						
						Iterator<DatiCasoBean> dcIterator= data.iterator();
						while(dcIterator.hasNext()){
							Long idCasoTemp = dcIterator.next().getCasoId();
							boolean trovato = false;
							for (CsACasoAccessoFascicolo c: cf) {
								if (c != null && c.getCasoId().equals(idCasoTemp)) {
									trovato = true;
									break;
								}
								
							}
							
							if (!trovato) {
								dcIterator.remove();
							}
						}
						
					}
					
				} catch(Exception e) {
					CsUiCompBaseBean.logger.error(e.getMessage(), e);
				}

			} catch (Exception e) {
				CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
				CsUiCompBaseBean.logger.error(e.getMessage(), e);
			} catch (Throwable e1) {
				CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
				CsUiCompBaseBean.logger.error(e1.getMessage(), e1);
			}
			
		}

		if (!loadAllTab) {
			this.loadTab = false;
			//this.setLoadAll(false);
		}
		
		if (hasFilter && !this.firstLoad) {
			this.setLoadAll(false);
		}

		// Viene gestita la visibilità del messaggio, non ci sono result o devono essere selezionati i filtri
		this.empyMessageReturn = this.checkMessageReturn(data);
		
		CsUiCompBaseBean.logger.debug("Fine Load<DatiCasoBean>: " + new Date());

		return data;
	}
	
    public void caricaTutti() {
        this.loadAll = true;
        this.setLoadAll(true);
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
	 * @since 1.26.13
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-09-18 - DDV
	 */
	private boolean checkMessageReturn(List<DatiCasoBean> listaInterventi) {
		
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
	public CasoSearchCriteria clearFilters(CasoSearchCriteria casoSearchCriteria) {

		casoSearchCriteria.setDenominazione(null);
		casoSearchCriteria.setDataNascita(null);
		casoSearchCriteria.setCodiceFiscale(null);
		casoSearchCriteria.setLstCatSociale(null);
		
		casoSearchCriteria.setLstStati(null);
		casoSearchCriteria.setIdOperatore(null);
		casoSearchCriteria.setResidenzaComune(null);
		casoSearchCriteria.setResidenzaNazione(null);
		casoSearchCriteria.setIdOperatoreAltro(null);
		casoSearchCriteria.setTitStudioId(null);
		casoSearchCriteria.setCondLavoroId(null);
		casoSearchCriteria.setTribunale(null);
		casoSearchCriteria.setTipoTutela(null);
		
		return casoSearchCriteria;
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
	 * @since 1.26.13
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-09-18 - DDV
	 */
	private String getCodiceBelfioreFromOrganizzazione(CsOOperatoreSettore opSettore) {
		
		return StringUtils.isBlank(opSettore.getCsOSettore().getCsOOrganizzazione().getCodCatastale())
				? opSettore.getCsOSettore().getCsOOrganizzazione().getCodRouting()
				: opSettore.getCsOSettore().getCsOOrganizzazione().getCodCatastale();
				
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
	 * @since 1.26.13
	 * @version 1.0.0
	 * 
	 * @author DDV
	 * @lastUpdate 2025-09-18 - DDV
	 */
	private boolean hasCriteria(CasoSearchCriteria casoSearchCriteria) {
		
		if (!StringUtils.isBlank(casoSearchCriteria.getDenominazione())
				|| casoSearchCriteria.getDataNascita() != null
				|| casoSearchCriteria.getCodiceFiscale() != null
				|| casoSearchCriteria.getIdOperatoreIter() != null
				|| casoSearchCriteria.getLstCatSociale() != null
				|| casoSearchCriteria.getLstStati() != null
				|| casoSearchCriteria.getResidenzaComune() != null
				|| casoSearchCriteria.getCondLavoroId() != null
				|| casoSearchCriteria.getTribunale() != null
				|| casoSearchCriteria.getTipoTutela() != null
				|| casoSearchCriteria.getResidenzaNazione() != null
				|| casoSearchCriteria.getTitStudioId() != null
				|| casoSearchCriteria.getIdOperatoreAltro() != null) {
			return true;
		} else {
			return false;
		}
		
	}
	
/*	private void loadDatiCaso(List<DatiCasoListaDTO> in, List<DatiCasoBean> out) throws Exception{
		if(!in.isEmpty()){
			CsUiCompBaseBean.logger.debug("INIZIO caricamento dati per inizializzazione listaCasi con ForkJoinPool");
			
			TaskPoolExecutor pool = new TaskPoolExecutor();
			List<InitDatiCaso> classiInit = new ArrayList<InitDatiCaso>();

			for (DatiCasoListaDTO dc : in) {

				BaseDTO bDto = new BaseDTO();
				CsUiCompBaseBean.fillEnte(bDto);
				bDto.setObj(dc.getCasoId());

				InitDatiCaso init = null;
				init = new InitDatiCaso(dc, bDto);
				classiInit.add(init);
				pool.addTask(init);
			}
			
			boolean abnormal = pool.execute();
			CsUiCompBaseBean.logger.debug("FINE caricamento dati per inizializzazione listaCasi con ForkJoinPool");
			
			if(abnormal){
				for(InitDatiCaso ic : classiInit){
					if(ic.isCompletedAbnormally()){
						Throwable e = ic.getException();
						CsUiCompBaseBean.logger.error("Errore esecuzione InitDatiCaso Identificativo["+ ic.getDc().getIdentificativo()+"], CF["+ic.getDc().getCf()+"]"+e.getMessage(), e);
					}
				}
				throw new Exception("Errore nel recupero della LISTA CASI");
			}
			
			
			for (InitDatiCaso initDatiCaso : classiInit) {
				DatiCasoBean bean = (DatiCasoBean) initDatiCaso.getRawResult();
				if (bean != null) out.add(bean);
			}
		}
	}
*/
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
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
