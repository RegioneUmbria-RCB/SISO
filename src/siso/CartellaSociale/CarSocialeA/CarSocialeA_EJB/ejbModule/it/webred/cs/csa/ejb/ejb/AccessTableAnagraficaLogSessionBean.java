package it.webred.cs.csa.ejb.ejb;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableAnagraficaLogSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.AnagraficaLogDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnagraficaLog;
import it.webred.ct.support.validation.ValidationStateless;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableAnagraficaLogSessionBean extends CarSocialeBaseSessionBean implements AccessTableAnagraficaLogSessionBeanRemote{
	private static final long serialVersionUID = 1L;

	public static Logger logger = Logger.getLogger("carsociale.log");
	
	
	@Autowired
	private AnagraficaLogDAO anagraficaLogDao;
	
	
	@Override
	public CsAAnagraficaLog findAnagraficaLogById(BaseDTO dto) {	
		
		return anagraficaLogDao.findAnagraficaLogById((Long) dto.getObj());
		
	}
	
	@Override
	public boolean saveAnagraficaLog(BaseDTO dto) {
		try {
			
			//SOLO INSERIMENTO
			CsAAnagraficaLog anaLog = (CsAAnagraficaLog)dto.getObj();
			anagraficaLogDao.saveAnagraficaLog(anaLog);

		return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	return false;
	}


	@Override
	public List<CsAAnagraficaLog> findStoricoBySoggetto(BaseDTO dto) {
		return anagraficaLogDao.findStoricoBySoggetto((Long)dto.getObj());
	}
}
