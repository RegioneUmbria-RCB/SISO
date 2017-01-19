package it.webred.cs.csa.web.manbean.listacasi;

import it.webred.amprofiler.model.AmAnagrafica;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCatSocialeSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.CasoSearchCriteria;
import it.webred.cs.csa.ejb.dto.InterventoBaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.PaginationDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneBaseDTO;
import it.webred.cs.csa.ejb.dto.retvalue.CsIterStepByCasoDTO;
import it.webred.cs.csa.ejb.dto.retvalue.DatiCasoListaDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.IterStatoInfo;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsASoggettoCategoriaSocLAZY;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsCCategoriaSociale;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.jsf.bean.DatiCasoBean;
import it.webred.cs.jsf.manbean.IterInfoStatoMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyListaCasiModel extends LazyDataModel<DatiCasoBean>  {
     
	private static final long serialVersionUID = 1L;
	
	@Override
    public DatiCasoBean getRowData(String rowKey) { 
		//TODO
        return null;
    }
  
    @Override
    public Object getRowKey(DatiCasoBean datiCaso) {
        return datiCaso.getSoggetto().getAnagraficaId();
    }
    
    private String getCategorieId(String desc){
    	try {

    		AccessTableCatSocialeSessionBeanRemote catSocService = (AccessTableCatSocialeSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCatSocialeSessionBean");
		    BaseDTO bdto = new BaseDTO();
		    CsUiCompBaseBean.fillEnte(bdto);
		    bdto.setObj(desc);
    		List<CsCCategoriaSociale> lstCatSoc = (List<CsCCategoriaSociale>) catSocService.getCategorieSocialiByDesc(bdto);
    		
    		if(lstCatSoc!=null && !lstCatSoc.isEmpty()){
    			String lstIds = "";
    			for(CsCCategoriaSociale cs : lstCatSoc)
    			 lstIds += ","+Long.toString(cs.getId());
    			lstIds = lstIds.substring(1);
    			return lstIds;
    		}
    	
    	} catch (Exception e) {
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
    	return null;
    }
 
    @Override
    public List<DatiCasoBean> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		CsUiCompBaseBean.logger.debug("*** Load List<DatiCasoBean> " + new Date());

        List<DatiCasoBean> data = new ArrayList<DatiCasoBean>();
 
		PaginationDTO dto = new PaginationDTO();
		CsUiCompBaseBean.fillEnte(dto);
		CasoSearchCriteria searchCriteria = new CasoSearchCriteria();
		CsOOperatoreSettore opSettore = (CsOOperatoreSettore) CsUiCompBaseBean.getSession().getAttribute("operatoresettore");
		if(opSettore != null) {
			//DEFAULT: filtro solo i casi dove sono presente come tipo operatore e con il settore scelto o quelli segnalatimi
			searchCriteria.setUsername(dto.getUserId());
			searchCriteria.setIdOperatore(opSettore.getCsOOperatore().getId());
			searchCriteria.setIdSettore(opSettore.getCsOSettore().getId());
			searchCriteria.setIdOrganizzazione(opSettore.getCsOSettore().getCsOOrganizzazione().getId());
			//PERMESSO CASI SETTORE: 
			if(CsUiCompBaseBean.checkPermesso(DataModelCostanti.PermessiCaso.VISUALIZZAZIONE_CASI_SETTORE))
				searchCriteria.setPermessoCasiSettore(true);
			//PERMESSO CASI ORGANIZZAZIONE: 
			if(CsUiCompBaseBean.checkPermesso(DataModelCostanti.PermessiCaso.VISUALIZZAZIONE_CASI_ORG))
				searchCriteria.setPermessoCasiOrganizzazione(true);
		}
		String filterSoggetto = (String) filters.get("soggetto");
		String filterDataNascita = (String) filters.get("dataNascita");
		String filterCF = (String) filters.get("codiceFiscale");
		String filterAssSociale = (String) filters.get("assistenteSociale");
		String filterCatSociale = (String) filters.get("catSociale");
		String filterDataApertura = (String) filters.get("dataApertura");
		if(filterSoggetto != null)
			searchCriteria.setDenominazione(filterSoggetto);
		if(filterDataNascita != null)
			searchCriteria.setDataNascita(filterDataNascita);
		if(filterCF != null)
			searchCriteria.setCodiceFiscale(filterCF);
		if(filterCatSociale!=null){
			String lstCatSociale = this.getCategorieId(filterCatSociale);
			if(lstCatSociale!=null)
				searchCriteria.setLstCatSociale(lstCatSociale);
		}
		if(filterDataApertura!=null)
			searchCriteria.setDataApertura(filterDataApertura);
		
		String criteriaStati = (String)getSession().getAttribute("filtroCasi_STATO");
		if(criteriaStati!=null && !criteriaStati.isEmpty())
			searchCriteria.setLstStati(criteriaStati);
			
		String criteriaOperatore = (String)getSession().getAttribute("filtroCasi_OPERATORE");
		if(criteriaOperatore!=null && !criteriaOperatore.isEmpty()){
			searchCriteria.setIdOperatoreAltro(new Long(criteriaOperatore));
			Boolean nr = (Boolean)getSession().getAttribute("filtroCasi_NON_RESPONSABILE");
			searchCriteria.setSoloOperatoreNR(nr!=null && nr ? true : false);
		}
		
		Long criteriaStudio = (Long)getSession().getAttribute("filtroCasi_STUDIO");
		if(criteriaStudio!=null && criteriaStudio>0)
			searchCriteria.setTitStudioId(criteriaStudio);
		
		Long criteriaLavoro = (Long)getSession().getAttribute("filtroCasi_LAVORO");
		if(criteriaLavoro!=null && criteriaLavoro>0)
			searchCriteria.setCondLavoroId(criteriaLavoro);
		
		String criteriaTutela = (String)getSession().getAttribute("filtroCasi_TUTELA");
		if(criteriaTutela!=null && !criteriaTutela.isEmpty())
			searchCriteria.setTipoTutela(criteriaTutela);
			
		//se devo filtrare per ass sociale eseguo manualmente la paginazione
		if(filterAssSociale == null) {
			dto.setFirst(first);
			dto.setPageSize(pageSize);
		}
		
		dto.setObj(searchCriteria);
			
		try {
			
			AccessTableSoggettoSessionBeanRemote soggettiService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
			AccessTableIterStepSessionBeanRemote iterSessionBean = (AccessTableIterStepSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");
			AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableCasoSessionBean");
			AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
			
			List<DatiCasoListaDTO> list = soggettiService.getCasiSoggettoLAZY(dto);
			int index=0;
			CsUiCompBaseBean.logger.debug("Inizio ciclo lista soggetti :" + new Date());
			for(DatiCasoListaDTO dc: list){
				CsUiCompBaseBean.logger.debug("Inizio caricamento dati record n "+ ++index);
				CsASoggettoLAZY sogg = dc.getSoggetto();
				CsACaso caso = sogg.getCsACaso();
				
				BaseDTO bDto = new BaseDTO();
				CsUiCompBaseBean.fillEnte(bDto);
				bDto.setObj(caso.getId());
				CsUiCompBaseBean.logger.debug("*** findResponsabile");
				CsOOperatoreBASIC operatore = casoService.findResponsabileBASIC(bDto);
				CsUiCompBaseBean.logger.debug("*** fine findResponsabile");
				AmAnagrafica operatoreAnagrafica = null;
				CsUiCompBaseBean.logger.debug("*** recupero anagrafica operatore");

				if(operatore != null)
					operatoreAnagrafica = CsUiCompBaseBean.getAnagraficaByUsername(operatore.getUsername());

				CsUiCompBaseBean.logger.debug("*** FINE recupero anagrafica operatore");

				//filter
				boolean match = true;
				if(filterAssSociale != null){
					match = false;
					if(operatoreAnagrafica!=null && (operatoreAnagrafica.getCognome() + " " + operatoreAnagrafica.getNome()).toUpperCase().contains(filterAssSociale.toUpperCase()))
					  match = true;
			    }
				
	            if(match) {
	            	//Carico gli altri dati da mostrare in lista
					IterInfoStatoMan casoInfo = new IterInfoStatoMan();
					
					IterDTO itDto = new IterDTO();
					CsUiCompBaseBean.fillEnte(itDto);
					itDto.setIdCaso(caso.getId());
					CsUiCompBaseBean.logger.debug("*** getLastIterStepByCasoDTO");

					CsIterStepByCasoDTO lastItStep = iterSessionBean.getLastIterStepByCasoDTO(itDto);
					
					CsUiCompBaseBean.logger.debug("*** FINE getLastIterStepByCasoDTO");
					
					
					
					if( lastItStep != null ) {
						casoInfo.initialize( lastItStep);
					}
		
				
					CsUiCompBaseBean.logger.debug("*** countInterventiByCaso");
					DatiCasoBean bean = new DatiCasoBean( sogg, operatore, operatoreAnagrafica, casoInfo);
					if(lastItStep.getCsItStep().getCsCfgItStato().getId()!=IterStatoInfo.APERTO)
						bean.setDataApertura(dc.getDataApertura());
					
					List<InterventoBaseDTO> lstInterventi = interventoService.getListaInfoInterventiByCaso(bDto);
					
					BaseDTO b2 = new BaseDTO();
					CsUiCompBaseBean.fillEnte(b2);
					b2.setObj(sogg.getCsAAnagrafica().getCf());
					b2.setObj2(true);
					List<ErogazioneBaseDTO> lstErogati = interventoService.getListaInterventiErogatiByCF(b2);
					bean.setnInterventi(Integer.toString(lstInterventi.size()));
					bean.setListaInterventi(lstInterventi);
					bean.setListaErogazioni(lstErogati);
					CsUiCompBaseBean.logger.debug("*** FINE countInterventiByCaso");
					
					CsUiCompBaseBean.logger.debug("*** getSoggettoCategorieAttualiBySoggetto");
					bDto.setObj(sogg.getAnagraficaId());
					List<CsASoggettoCategoriaSocLAZY> lstCatSoc = soggettiService.getSoggettoCategorieAttualiBySoggetto(bDto);
					bean.setListaCatSociale(lstCatSoc);
					CsUiCompBaseBean.logger.debug("*** FINE getSoggettoCategorieAttualiBySoggetto");
	            	
	                data.add(bean);
	            }
	        }
			CsUiCompBaseBean.logger.debug("Fine ciclo lista soggetti :" + new Date());
			
	        //rowCount
			Integer dataSize;
			if(filterAssSociale != null)
				dataSize = data.size();
			else 
				dataSize = soggettiService.getCasiSoggettoCount(dto);
			this.setRowCount(dataSize);
			
			//paginate
	        if(filterAssSociale != null && dataSize > pageSize) {
	            try {
	        		CsUiCompBaseBean.logger.debug("Fine Load<DatiCasoBean>:" + new Date());
	                return data.subList(first, first + pageSize);
	            }
	            catch(IndexOutOfBoundsException e) {
	        		CsUiCompBaseBean.logger.debug("Fine Load<DatiCasoBean>:" + new Date());
	                return data.subList(first, first + (dataSize % pageSize));
	            }
	        }
	        else {
	    		CsUiCompBaseBean.logger.debug("Fine Load<DatiCasoBean>:" + new Date());

	    		return data;
	        }
		
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		
		CsUiCompBaseBean.logger.debug("Fine Load<DatiCasoBean>:" + new Date());
		
		return data;
    }

    public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
}
