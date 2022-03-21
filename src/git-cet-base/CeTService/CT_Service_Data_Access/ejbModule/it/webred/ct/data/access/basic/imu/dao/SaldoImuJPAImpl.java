package it.webred.ct.data.access.basic.imu.dao;

import it.webred.ct.data.access.basic.CTServiceBaseDAO;
import it.webred.ct.data.access.basic.imu.SaldoImuServiceException;
import it.webred.ct.data.model.imu.SaldoImuDatiElab;
import it.webred.ct.data.model.imu.SaldoImuStorico;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

public class SaldoImuJPAImpl extends CTServiceBaseDAO implements SaldoImuDAO {

	@Override
	public void salvaStorico(SaldoImuStorico storico){
		
	try{
			manager_diogene.persist(storico);
		
		} catch (Throwable t) {
			logger.error("storicizza", t);
			throw new SaldoImuServiceException(t);
		}
		
	}

	@Override
	public void salvaElaborazione(SaldoImuDatiElab jpa){
		
		try{
			
			manager_diogene.persist(jpa);
		
		} catch (Throwable t) {
			logger.error("salvaElaborazione", t);
			throw new SaldoImuServiceException(t);
		}
		
	}
	

	@Override
	public List<SaldoImuDatiElab> getDatiElaborazione(String codfisc){
		
		List<SaldoImuDatiElab> result = new ArrayList<SaldoImuDatiElab>();
		
		try {
			
			logger.debug("getDatiElaborazione - cod.fiscale["+codfisc+"]");
			Query q = manager_diogene.createNamedQuery("SaldoImuDatiElab.getDatiElabByCF");
			q.setParameter("codfisc", codfisc);
		
			result =  q.getResultList();
			
		} catch (Throwable t) {
			logger.error("getDatiElaborazione", t);
			throw new SaldoImuServiceException(t);
		}

		return result;
	}
	
	@Override
	public List<SaldoImuStorico> getStorico(String codfisc){
		
		List<SaldoImuStorico> result = new ArrayList<SaldoImuStorico>();
		
		try {
			
			logger.debug("getStorico - cod.fiscale["+codfisc+"]");
			Query q = manager_diogene.createNamedQuery("SaldoImuStorico.getStoricoByCF");
			q.setParameter("codfisc", codfisc);
		
			result =  q.getResultList();
			
		} catch (Throwable t) {
			logger.error("getStorico", t);
			throw new SaldoImuServiceException(t);
		}
		
		return result;
	}
	

}
