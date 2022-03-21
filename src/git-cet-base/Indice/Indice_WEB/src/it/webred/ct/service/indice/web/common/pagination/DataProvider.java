package it.webred.ct.service.indice.web.common.pagination;

import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.SitOperationDTO;
import it.webred.ct.data.model.indice.SitUnico;

import java.util.List;

public interface DataProvider {
    
    public long getUnicoCount();
    
    public List<SitUnico> getUnicoByRange(int start, int rowNumber);
    
    public long getTotaleCount();
    
    public List<SitOperationDTO> getTotaleByRange(int start, int rowNumber);
    
    public void resetData();
}
