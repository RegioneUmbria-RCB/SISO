package it.webred.cs.csa.web.manbean.export.fse;

import it.webred.cs.csa.ejb.client.AccessTableDatiPorSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.fse.FseSearchCriteria;
import it.webred.cs.csa.ejb.dto.fse.ListaFseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.FiltroFse;
import it.webred.cs.data.DataModelCostanti.PermessiCartella;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

public class LazyListaFseModel extends LazyDataModel<ListaFseDTO>  {
     
	private static final long serialVersionUID = 1L;

	// SISO-641
	String sortField;
	SortOrder sortOrder;
	Map filters;
	
	@Override
    public ListaFseDTO getRowData(String rowKey) { 
		//TODO
        return null;
    }
  
    @Override
    public Object getRowKey(ListaFseDTO dati) {
        return dati.getIdentificativo(); //Concatenazione di TIPO e IDENTIFICATIVO 
    }
    
	@Override
	public List<ListaFseDTO> load(int first, int pageSize, String sortField,SortOrder sortOrder, Map filters) {
		CsUiCompBaseBean.logger.debug("*** Load List<ListaFseDTO> "+ new Date());

		List<ListaFseDTO> data = new ArrayList<ListaFseDTO>();

		// SISO-641
		this.sortField = sortField;
		this.sortOrder = sortOrder;
		this.filters = filters;

		FseSearchCriteria searchCriteria = new FseSearchCriteria();
		CsUiCompBaseBean.fillEnte(searchCriteria);
		
		boolean canViewLista = CsUiCompBaseBean.checkPermesso(PermessiCartella.ITEM, PermessiCartella.VISUALIZZAZIONE_LISTA_FSE);
		canViewLista = true;
		
		if(canViewLista) {
			
			searchCriteria.setOrganizzazioneId(CsUiCompBaseBean.getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId());
			
			String filterCF = (String) filters.get("codiceFiscale");
			String filterDenominazione = (String) filters.get("denominazione");
			String filterProgetto = (String) filters.get("progetto");
			String filterAttivita = (String) filters.get("attivita");
			
			searchCriteria.setDenominazione(filterDenominazione);
			searchCriteria.setCodiceFiscale(filterCF);
			searchCriteria.setProgetto(filterProgetto);
			searchCriteria.setCodAttivita(filterAttivita);
	
			Date criteriaDataDa = (Date) CsUiCompBaseBean.getSession().getAttribute(FiltroFse.DATA_DA);
			searchCriteria.setDataInizio(criteriaDataDa);
			
			Date criteriaDataA = (Date) CsUiCompBaseBean.getSession().getAttribute(FiltroFse.DATA_A);
			searchCriteria.setDataFine(criteriaDataA);
		
			String criteriaTipo = (String) CsUiCompBaseBean.getSession().getAttribute(FiltroFse.TIPO);
			if (!StringUtils.isBlank(criteriaTipo))
				searchCriteria.setTipoFse(new Long(criteriaTipo));
			
			String criteriaResidenza = (String) CsUiCompBaseBean.getSession().getAttribute(FiltroFse.RESIDENZA_COMUNE);
			if (!StringUtils.isBlank(criteriaResidenza))
				searchCriteria.setResidenzaIstat(criteriaResidenza);
			
			searchCriteria.setFirst(first);
			searchCriteria.setPageSize(pageSize);

			try {
				
				AccessTableDatiPorSessionBeanRemote porService = (AccessTableDatiPorSessionBeanRemote) CsUiCompBaseBean.getCarSocialeEjb("AccessTableDatiPorSessionBean");
				Integer dataSize = porService.getListaFseCount(searchCriteria);
				this.setRowCount(dataSize);
				
				data = porService.getListaFse(searchCriteria);
		
			} catch (Exception e) {
				CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
				CsUiCompBaseBean.logger.error(e.getMessage(), e);
			} catch (Throwable e1) {
				CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
				CsUiCompBaseBean.logger.error(e1.getMessage(), e1);
			}

			CsUiCompBaseBean.logger.debug("Fine Load<DatiFseBean>:" + new Date());
		}
		
		return data;
	}

}
