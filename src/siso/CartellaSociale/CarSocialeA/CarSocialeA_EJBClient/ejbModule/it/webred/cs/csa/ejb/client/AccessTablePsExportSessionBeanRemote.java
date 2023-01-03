package it.webred.cs.csa.ejb.client;

import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsIPsExportMast;
import it.webred.cs.data.model.VErogExportHelp;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface AccessTablePsExportSessionBeanRemote {
	
	public List<CsIPsExportMast> findFlussiInviatiInPeriodo(BaseDTO dto);

	public List<EsportazioneDTOView> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto); //INIZIO modifica SISO-538

	public List<EsportazioneDTOView> findErogazGiaInviateInPeriodo(ErogazioniSearchCriteria bDto);
	
	public CsIPsExportMast saveCsIPsExportMast(BaseDTO dto) throws CarSocialeServiceException;

	public long getProgressivoCsIPsExportMastPSA(BaseDTO dto) 	throws CarSocialeServiceException;

	public List<EsportazioneDTOView> findEsportazioniDTOviewPerIdMaster(ErogazioniSearchCriteria bDto); //SISO-538
	
	public List<VErogExportHelp> findVErogExportHelp(BaseDTO dto); //SISO-538
	
	public List<EsportazioneDTOView> findErogazioniMasterChiusuraInPeriodo(ErogazioniSearchCriteria bDto); //SISO-538
	
	public void updateCsIPsExportRevocaEsportazione(BaseDTO dto);	// SISO-719
	
	public void updateCsIPsExportRevocaEsportazioneByInterventoEsegMastId(BaseDTO dto); //SISO-780
	
	public List<EsportazioneDTOView> findErogazPeriodicheByMast(ErogazioniSearchCriteria bDto); //SISO-780
	
	public Boolean verificaErogazioniEsportateByEsegIds(BaseDTO dto);  //INIZIO SISO-524 
	
	public List<CsIPsExport> findCsIPsExportByCsIInterventoMastIdExported(BaseDTO dto); //SISO-884

	public Boolean verificaErogazioneEsportataByEsegId(BaseDTO dto);
	
}
