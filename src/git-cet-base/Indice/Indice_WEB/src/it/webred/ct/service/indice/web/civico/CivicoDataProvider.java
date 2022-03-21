package it.webred.ct.service.indice.web.civico;

import it.webred.ct.data.access.basic.indice.civico.dto.SitCivicoDTO;
import it.webred.ct.data.access.basic.indice.dto.IndiceSearchCriteria;
import it.webred.ct.data.access.basic.indice.dto.SitOperationDTO;
import it.webred.ct.data.model.indice.SitUnico;

import java.util.List;

public interface CivicoDataProvider {
    
	public List<SitCivicoDTO> getListaCiviciByVia(int start, int rowNumber);
	
	public long getListaCiviciByViaRecordCount();
    
    public void resetData();
	
}
