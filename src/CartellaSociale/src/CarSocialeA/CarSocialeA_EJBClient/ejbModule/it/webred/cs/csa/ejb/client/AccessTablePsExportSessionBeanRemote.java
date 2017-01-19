package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.data.model.CsIPsExportMast;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTablePsExportSessionBeanRemote {
	
	public List<CsIPsExportMast> findFlussiInviatiInPeriodo(BaseDTO dto, Date dataInizio, Date dataFine);

	public List<EsportazioneDTO> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto);

	public CsIPsExportMast saveCsIPsExportMast(BaseDTO dto) throws CarSocialeServiceException;

	public long getProgressivoCsIPsExportMastPSA(BaseDTO dto) 	throws CarSocialeServiceException;

}
