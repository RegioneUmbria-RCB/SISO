package it.webred.ct.rulengine.listener;

import it.webred.ct.rulengine.synchronizer.SynchronizerTimerTask;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class SynchronizerListener extends AbstractControllerListener implements ServletContextListener {
	
	private Logger log = Logger.getLogger(SynchronizerListener.class.getName());
	
	private static Long _delay = null;
	private static Long _period = null;
		
	public SynchronizerListener() {
		super();
		
		_delay = new Long(_cfg.getProperty("synchronizer.timer.delay"));
		_period = new Long(_cfg.getProperty("synchronizer.timer.period"));
	}

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
	}
	

	public void contextInitialized(ServletContextEvent arg0) {
		if(_cfg.getProperty("synchronizer.listener.active").equals("Y")) {
			
			log.debug("[LISTENER SynchronizerListener ON] ");
			Timer timer = new Timer();

			initializeSynchronizer();
			
			timer.schedule(new SynchronizerTimerTask(), _delay, _period);
		}
		else {
			log.warn("[LISTENER SynchronizerListener OFF] ");
		}
		
		
	}
	
	private void initializeSynchronizer() {
		log.info("Inizializzazione sincronizzazione processi schedulati");
		
		try {
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
		}
	}

}
