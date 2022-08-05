package it.webred.cs.csa.web.manbean.configurazione;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SelectableDataModel;
import org.primefaces.model.SortOrder;

import it.umbriadigitale.argo.ejb.client.cs.bean.ArConfigurazioneService;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ArFonteDTO;
import it.umbriadigitale.argo.ejb.client.cs.dto.configurazione.ProgettiSearchCriteria;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;

public class FontiTableDataModel extends LazyDataModel<ArFonteDTO> implements SelectableDataModel<ArFonteDTO>{
	
	private String zsCorrente;
	
    
    @Override
    public List<ArFonteDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
        List<ArFonteDTO> lst = new ArrayList<ArFonteDTO>();
       
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
			lst = confArService.getListaFonti(sc);
			setRowCount(confArService.countFonti(sc)); 
			
		} catch (Exception e) {
			CsUiCompBaseBean.addErrorFromProperties("caricamento.error");
			CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
		
		return lst;
    }

	
	public String getZsCorrente() {
		return zsCorrente;
	}

	public void setZsCorrente(String zsCorrente) {
		this.zsCorrente = zsCorrente;
	}

	@Override
	public ArFonteDTO getRowData(String arg) {
		List<ArFonteDTO> fonti = (List<ArFonteDTO>) getWrappedData();
		for(ArFonteDTO p: fonti)
			if(arg.equals(getRowKey(p)))
				return p;
		
		return null;
	}
  
    @Override
    public Object getRowKey(ArFonteDTO dati) {
        return dati.getId();
    }
}
