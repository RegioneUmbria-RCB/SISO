package it.webred.cs.csa.web.manbean.configurazione;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArProgettoDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ProgettiSearchCriteria;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class ProgettiTableDataModel extends LazyDataModel<ArProgettoDTO> implements SelectableDataModel<ArProgettoDTO>{
	
	private String zsCorrente;
	
    
    @Override
    public List<ArProgettoDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        List<ArProgettoDTO> lstProgetti = new ArrayList<ArProgettoDTO>();
       
        String codiceMemo = (String)filters.get("codiceMemo");
    	String descrizione = (String)filters.get("descrizione");
        
        ProgettiSearchCriteria sc = new ProgettiSearchCriteria();
        sc.setFirst(first);
        sc.setPageSize(pageSize);
        sc.setZonaSociale(zsCorrente);
        sc.setCodiceMemo(codiceMemo);
        sc.setDescrizione(descrizione);
        
		try {
			
			ArConfigurazioneService confArService = (ArConfigurazioneService) CsUiCompBaseBean.getArgoEjb( "ArConfigurazioneServiceBean");
			lstProgetti = confArService.getListaProgetti(sc);
			setRowCount(confArService.countProgetti(sc)); 
			
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		return lstProgetti;
    }

	
	public String getZsCorrente() {
		return zsCorrente;
	}

	public void setZsCorrente(String zsCorrente) {
		this.zsCorrente = zsCorrente;
	}

	@Override
	public ArProgettoDTO getRowData(String arg) {
		List<ArProgettoDTO> progetti = (List<ArProgettoDTO>) getWrappedData();
		for(ArProgettoDTO p: progetti)
			if(arg.equals(getRowKey(p)))
				return p;
		
		return null;
	}
  
    @Override
    public Object getRowKey(ArProgettoDTO dati) {
        return dati.getId();
    }
}
