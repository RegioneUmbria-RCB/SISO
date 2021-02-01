package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinbaMinoriSearchResultDTO;
import it.webred.cs.csa.ejb.dto.SinbaSearchCriteria;
import it.webred.cs.data.model.CsDExportSinba;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTableExportValutazioniSinbaSessionBeanRemote {
	
	
	public List<SinbaMinoriSearchResultDTO> findMinoriNelPeriodo(SinbaSearchCriteria bDto);
	
	public List<SinbaMinoriSearchResultDTO> findSchedeSinbaPerMinore(BaseDTO bDto);
	
	public long getProgressivoCsDExportSinbaPSA(BaseDTO dto) throws CarSocialeServiceException; 
	
	public CsDExportSinba saveCsDExportSinba(BaseDTO dto) throws CarSocialeServiceException;

	
}
