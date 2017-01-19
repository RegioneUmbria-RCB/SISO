package it.webred.cs.csa.ejb.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTablePsExportSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.ExportCasellarioDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ErogazioniSearchCriteria;
import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsIPsExportMast;

@Stateless
public class AccessTablePsExportSessionBean extends CarSocialeBaseSessionBean implements AccessTablePsExportSessionBeanRemote{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExportCasellarioDAO exportCasellarioDao;
	
	@Override
	public List<CsIPsExportMast> findFlussiInviatiInPeriodo(BaseDTO dto, Date dataInizio, Date dataFine) {				
		
		return exportCasellarioDao.findFlussiInviatiInPeriodo((Long) dto.getObj(),dataInizio, dataFine);
	}

	@Override
	public List<EsportazioneDTO> findErogazDaInviareInPeriodo(ErogazioniSearchCriteria bDto) {
		List<EsportazioneDTO> result=exportCasellarioDao.findErogazDaInviareInPeriodo(bDto);
		return result;
	}


	//INIZIO MOD-RL
	@Override
	public CsIPsExportMast saveCsIPsExportMast(BaseDTO dto) throws CarSocialeServiceException {
		return exportCasellarioDao.saveCsIPsExportMast((CsIPsExportMast) dto.getObj());
	}

	@Override
	public long getProgressivoCsIPsExportMastPSA(BaseDTO dto) throws CarSocialeServiceException {
		String enteErogatore = (String) dto.getObj();
		String flusso = DataModelCostanti.CsIPsExportMast.FLUSSO_PSA;
		 
		Calendar c = Calendar.getInstance();
		Calendar dtInsStart = new GregorianCalendar(
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), 
				c.get(Calendar.DAY_OF_MONTH)
				);
		
		Calendar dtInsEnd = (Calendar) dtInsStart.clone();
		dtInsEnd.add(Calendar.DAY_OF_MONTH, 1);

		long count = exportCasellarioDao.getProgressivoCsIPsExportMast(enteErogatore, flusso, dtInsStart.getTime() ,  dtInsEnd.getTime() );
	
		return count + 1;
	}
	
	
	//FINE MOD-RL
}
