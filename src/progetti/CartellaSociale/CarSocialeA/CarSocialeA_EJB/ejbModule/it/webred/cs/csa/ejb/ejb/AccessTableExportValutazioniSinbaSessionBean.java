package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableExportValutazioniSinbaSessionBeanRemote;
import it.webred.cs.csa.ejb.client.CarSocialeServiceException;
import it.webred.cs.csa.ejb.dao.ExportValutazioneSinbaDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.SinbaMinoriSearchResultDTO;
import it.webred.cs.csa.ejb.dto.SinbaSearchCriteria;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsDExportSinba;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;

import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableExportValutazioniSinbaSessionBean extends CarSocialeBaseSessionBean implements AccessTableExportValutazioniSinbaSessionBeanRemote{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ExportValutazioneSinbaDAO exportValutazioneSinbaDao;
	
	

	//TASK SISO-777
	@Override
	public List<SinbaMinoriSearchResultDTO> findMinoriNelPeriodo(SinbaSearchCriteria bDto) {
		List<SinbaMinoriSearchResultDTO> result=exportValutazioneSinbaDao.findMinoriNelPeriodo(bDto);
		return result;
	}

	@Override
	public List<SinbaMinoriSearchResultDTO> findSchedeSinbaPerMinore(BaseDTO bDto) {
		List<SinbaMinoriSearchResultDTO> result=exportValutazioneSinbaDao.findSchedeSinbaPerMinore(bDto);
		return result;
	}
	
	@Override
	public long getProgressivoCsDExportSinbaPSA(BaseDTO dto) throws CarSocialeServiceException {
		String enteErogatore = (String) dto.getObj();
		String flusso = DataModelCostanti.CsSinbasExportMast.FLUSSO_SINBA;
		 
		Calendar c = Calendar.getInstance();
		Calendar dtInsStart = new GregorianCalendar(
				c.get(Calendar.YEAR),
				c.get(Calendar.MONTH), 
				c.get(Calendar.DAY_OF_MONTH)
				);
		
		Calendar dtInsEnd = (Calendar) dtInsStart.clone();
		dtInsEnd.add(Calendar.DAY_OF_MONTH, 1);

		long count = exportValutazioneSinbaDao.getProgressivoCsDExportSinba(enteErogatore, flusso, dtInsStart.getTime() ,  dtInsEnd.getTime() );
	
		return count + 1;
	}


	@Override
	public CsDExportSinba saveCsDExportSinba(BaseDTO dto) throws CarSocialeServiceException 
	{
		return exportValutazioneSinbaDao.saveCsDExportSinba((CsDExportSinba) dto.getObj());
	} 
	
}
