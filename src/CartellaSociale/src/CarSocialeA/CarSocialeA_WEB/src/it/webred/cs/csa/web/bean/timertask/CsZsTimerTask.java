package it.webred.cs.csa.web.bean.timertask;

import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsIInterventoEsegMastSogg;
import it.webred.ejb.utility.ClientUtility;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.jboss.logging.Logger;

public class CsZsTimerTask extends TimerTask {

	private static Logger logger;
	private String enteId;

	public CsZsTimerTask(String enteId) {
		super();
		this.enteId = enteId;
	}

	@Override
	public void run() {

		try {
			
			logger.debug("__ INIZIO Esecuzione CsZsTimerTask ENTEID " + enteId +" __");
			
			associaCasoASoggettiErogazione();
			
			logger.debug("__ FINE Esecuzione CsZsTimerTask __");

		} catch (Exception e) {
			logger.error("__ CsZsTimerTask: Eccezione su " + e.getMessage(), e);
		}
	}
	
	private void associaCasoASoggettiErogazione(){
	
    	try {
    		
    		AccessTableInterventoSessionBeanRemote intService = (AccessTableInterventoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");
    		AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
    		
    		BaseDTO bDto = new BaseDTO();
        	bDto.setEnteId(enteId);
        	
    		
    	    List<CsIInterventoEsegMastSogg> lst = intService.findSoggettiErogazioneSenzaCaso(bDto);
    	    for(CsIInterventoEsegMastSogg se : lst){
    	    	if(se.getCf()!=null && !se.getCf().trim().isEmpty()){
    	    		String cf = se.getCf()!=null ? se.getCf().toUpperCase() : null;
    	    		
    	    		bDto.setObj(cf.toUpperCase());
    	    		CsASoggettoLAZY s = soggettoService.getSoggettoByCF(bDto);
    	    		if(s!=null && s.getCsACaso()!=null){
    	    			logger.debug("CsZsTimerTask_Individuato caso per il soggetto: "+cf);
    	    			se.setCaso(s.getCsACaso());
    	    			se.setDtMod(new Date());
    	    			se.setUserMod("CsZsTimerTask");
    	    			bDto.setObj(se);
    	    			intService.updateSoggettoErogazione(bDto);
    	    		}
    	    	}
    	    }
		
    	} catch (Exception e2) {
			logger.error("__ CsZsTimerTask: Eccezione " + e2.getMessage(), e2);
		}
	}
	
	public static void setLogger(Logger logger) {
		CsZsTimerTask.logger = logger;
	}

}
