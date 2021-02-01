package it.webred.cs.csa.ejb.ejb;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.ExportCasellarioDAO;
import it.webred.cs.csa.ejb.dao.ExportValutazioneSinbaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTOView;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsIPsExport;
import it.webred.cs.data.model.CsIPsExportMast;
import it.webred.cs.data.model.VErogExportHelp;

@Stateless
public class AccessTablePsExportSessionBean extends CarSocialeBaseSessionBean implements AccessTablePsExportSessionBeanRemote{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExportCasellarioDAO exportCasellarioDao;
	@Autowired
	private ExportValutazioneSinbaDAO exportValutazioneSinbaDAO;
	
	@Override
	public List<CsIPsExportMast> findFlussiInviatiInPeriodo(BaseDTO dto) {				
		
		return exportCasellarioDao.findFlussiInviatiInPeriodo((Long) dto.getObj(), (String)dto.getObj2(), (String)dto.getObj3());
	}

//INIZIO modifica SISO-538
	@Override
	public List<EsportazioneDTOView> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto) {
		List<EsportazioneDTOView> result=exportCasellarioDao.findErogazDaInviareInPeriodo(bDto);
		return result;
	}
	
	@Override
	public List<EsportazioneDTOView> findErogazGiaInviateInPeriodo(ErogazioniSearchCriteria bDto) {
		List<EsportazioneDTOView> result=exportCasellarioDao.findErogazGiaInviateInPeriodo(bDto);
		return result;
	}
	

	public List<EsportazioneDTOView> findErogazioniMasterChiusuraInPeriodo(ErogazioniSearchCriteria bDto){ 
		List<EsportazioneDTOView> result=exportCasellarioDao.findErogazioniMasterChiusuraInPeriodo(bDto);
		return result;
	}
//FINE modifica SISO-538


	
	//INIZIO MOD-RL
	@Override
	public CsIPsExportMast saveCsIPsExportMast(BaseDTO dto) throws CarSocialeServiceException {
		return exportCasellarioDao.saveCsIPsExportMast((CsIPsExportMast) dto.getObj());
	}

	@Override
	public long getProgressivoCsIPsExportMastPSA(BaseDTO dto) throws CarSocialeServiceException {
		String enteTitolare = (String) dto.getObj();
		String flusso = DataModelCostanti.CsIPsExportMast.FLUSSO_PSA;
		 
		Calendar c = Calendar.getInstance();
		Calendar dtInsStart = new GregorianCalendar(
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), 
				c.get(Calendar.DAY_OF_MONTH)
				);
		
		Calendar dtInsEnd = (Calendar) dtInsStart.clone();
		dtInsEnd.add(Calendar.DAY_OF_MONTH, 1);

		long count = exportCasellarioDao.getProgressivoCsIPsExportMast(enteTitolare, flusso, dtInsStart.getTime() ,  dtInsEnd.getTime() );
	
		return count + 1;
	} 
	//FINE MOD-RL
	

	//INIZIO SISO-538
	@Override
	public List<EsportazioneDTOView> findEsportazioniDTOviewPerIdMaster(ErogazioniSearchCriteria bDto) {
		List<EsportazioneDTOView> result= exportCasellarioDao.findEsportazioniDTOviewPerIdMaster(bDto);
		return result;
	}
	//FINE SISO-538

	@Override
	public List<VErogExportHelp> findVErogExportHelp(BaseDTO dto) {
		List<BigDecimal> ids = (List<BigDecimal>) dto.getObj();
		return exportCasellarioDao.findVErogExportHelp(ids);
	}
	

	//INIZIO SISO-524 
	@Override
	public List<CsIPsExport> findCsIPsExportByCsIInterventoEsegIds(BaseDTO dto) {
		List<Long> ids = (List<Long>) dto.getObj();
		return exportCasellarioDao.findCsIPsExportByCsIInterventoEsegIds(ids);
	}
	
	/*
	 * @Override public List<CsIPsExport>
	 * findCsIPsExportByCsIInterventoMastId(BaseDTO dto) { Long id = (Long)
	 * dto.getObj(); return
	 * exportCasellarioDao.findCsIPsExportByCsIInterventoMastId(id); }
	 */
	//FINE SISO-524

	
	// SISO-719
	@Override
	public void updateCsIPsExportRevocaEsportazione(BaseDTO dto) {
		EsportazioneDTOView erogDaRevocare = (EsportazioneDTOView) dto.getObj();
		
		exportCasellarioDao.updateCsIPsExportRevocaEsportazioneByInterventoEsegId(erogDaRevocare.getInterventoEsegId());
	}
	
	//SISO-780
	@Override
	public void updateCsIPsExportRevocaEsportazioneByInterventoEsegMastId(BaseDTO dto) {
		Long idErogMast = (Long) dto.getObj();
		exportCasellarioDao.updateCsIPsExportRevocaEsportazioneByInterventoEsegMastId(idErogMast);
	}
	
	//SISO-780
	@Override
	public List<EsportazioneDTOView> findErogazPeriodicheByMast(ErogazioniSearchCriteria bDto){ 
		List<EsportazioneDTOView> result=exportCasellarioDao.findErogazPeriodicheByMast(bDto);
		return result;
	}
	
	// SISO-884
	@Override
	public List<CsIPsExport> findCsIPsExportByCsIInterventoEsegIdsExported(BaseDTO dto) {
		List<Long> ids = (List<Long>) dto.getObj();
		return exportCasellarioDao.findCsIPsExportByCsIInterventoEsegIdsExported(ids);
	}

	// SISO-884
	@Override
	public List<CsIPsExport> findCsIPsExportByCsIInterventoMastIdExported(BaseDTO dto) {
		Long id = (Long) dto.getObj();
		return exportCasellarioDao.findCsIPsExportByCsIInterventoMastIdExported(id);
	}
	
	//SISO - 884
	public List<CsIPsExport> findCsIPsExportByCsIInterventoEseg(BaseDTO dto) {
		Long intEsegId= (Long) dto.getObj();
		Long intEsegMastId=(Long) dto.getObj2();
		return exportCasellarioDao.findCsIPsExportByIntEsegIdAndIntEsegMastId(intEsegId,intEsegMastId);
	}
	
	// SISO - 884
	public void deleteCsIPsExportByIntEsegIdAndIntEsegMastId(BaseDTO dto) {
        Long intEsegId = (Long) dto.getObj();
		Long intEsegMastId = (Long) dto.getObj2();
		exportCasellarioDao.deleteCsIPsExportByIntEsegIdAndIntEsegMastId(intEsegId, intEsegMastId);
	}
	
	//SISO - 884
	public void deleteCsIPsExportMastById(BaseDTO dto){
		Long id = (Long) dto.getObj();
		exportCasellarioDao.deleteCsIPsExportMastById(id);
	}
	
	//SISO-884
	@Override
	public List<CsIPsExportMast> findCsIPsExportMastByIds(BaseDTO dto) {
		List<Long> ids = (List<Long>) dto.getObj();
		return exportCasellarioDao.findCsIPsExportMastByIds(ids);
	}
	
}
