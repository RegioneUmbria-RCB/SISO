package it.umbriadigitale.soclav.timertask;

import java.util.TimerTask;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import it.umbriadigitale.soclav.service.interfaccia.ICentriImpiegoService;

public class SocLavTimerTaskGiornaliero extends TimerTask {

	public final Logger logger = Logger.getLogger("SocLav");
	
	@Autowired 
	ICentriImpiegoService centriPerImpiegoService;
		
	public SocLavTimerTaskGiornaliero(AutowireCapableBeanFactory factory) {
		super();
		factory.autowireBean(this);
	}

	@Override
	public void run() {
		logger.debug("__ INIZIO Esecuzione SocLavTimerTaskGiornaliero");
		try {
			
			this.aggiornaElenchiGePI();
	    	this.aggiornaElenchiANPAL();
	    	
			logger.debug("__ FINE Esecuzione SocLavTimerTaskGiornaliero");

		} catch (Exception e) {
			logger.error("__ SocLavTimerTaskGiornaliero: Eccezione su : " + e.getMessage(), e);
		}
	}
	
	private void aggiornaElenchiGePI() {
		logger.info("_INIZIO aggiornaElenchiGePI_");
		try {
			logger.debug("TODO: Da Implementare!");
		}catch(Exception e) {
			logger.error("Errore elaborazione aggiornaElenchiGePI:"+e.getMessage(), e);
		}
		logger.info("_FINE aggiornaElenchiGePI_");
	}
	
	private void aggiornaElenchiANPAL() {
		logger.info("_INIZIO aggiornaElenchiANPAL_");
		try {
				centriPerImpiegoService.aggiornaFlussoANPAL();	
		}catch(Exception e) {
			logger.error("Errore elaborazione aggiornaElenchiANPAL:"+e.getMessage(), e);
		}
		logger.info("_FINE aggiornaElenchiANPAL_");
	}
}
