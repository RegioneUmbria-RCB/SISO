package it.webred.ct.rulengine.listener;

import java.util.Timer;

import it.webred.ct.rulengine.event.EventScannerTimerTask;
import it.webred.ct.rulengine.service.bean.EventLaunchService;
import it.webred.rulengine.ServiceLocator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class EventLaunchListener extends AbstractControllerListener implements ServletContextListener {

	private Logger log = Logger.getLogger(EventLaunchListener.class.getName());
	
	private static Long _delay = null;
	private static Long _period = null;
	private static String _yn = null;
	
	private EventLaunchService eventLaunchService;
	
	
	public EventLaunchListener() {
		super();
		
		_delay = new Long(_cfg.getProperty("event.launch.timer.delay"));
		_period = new Long(_cfg.getProperty("event.launch.timer.period"));
		_yn = _cfg.getProperty("event.launch.listener.active");
	}

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		log.info("[event.launch.listener.active] = " +  _yn);
		
		if (("Y").equalsIgnoreCase(_yn)) {
			
			log.info("[LISTENER EventLaunchListener ON] ");
			Timer timer = new Timer();

			initializeQueue();
			
			timer.schedule(new EventScannerTimerTask(), _delay, _period);
		}
		else {
			log.warn("[LISTENER EventLaunchListener OFF] ");
		}
	}
	
	
	private void initializeQueue() {
		log.info("Inizializzazione scanner eventi");
		
		try {
			eventLaunchService = 
				(EventLaunchService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "EventLaunchServiceBean");
			
			eventLaunchService.deleteAllEventi();
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
		}
	}

}
