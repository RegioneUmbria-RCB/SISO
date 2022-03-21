package it.webred.ct.rulengine.listener;

import it.webred.ct.rulengine.queue.QueueMonitorTimerTask;
import it.webred.ct.rulengine.service.bean.QueueService;
import it.webred.rulengine.ServiceLocator;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class QueueMonitorListener extends AbstractControllerListener implements ServletContextListener {
	
	private Logger log = Logger.getLogger(QueueMonitorListener.class.getName());
	
	private static Long _delay = null;
	private static Long _period = null;
	
	private QueueService queueService;
	
	public QueueMonitorListener() {
		super();
		
		_delay = new Long(_cfg.getProperty("queue.monitor.timer.delay"));
		_period = new Long(_cfg.getProperty("queue.monitor.timer.period"));
	}

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
	}
	

	public void contextInitialized(ServletContextEvent arg0) {
		if(_cfg.getProperty("queue.monitor.listener.active").equals("Y")) {
			
			log.debug("[LISTENER QueueMonitorListener ON] ");
			Timer timer = new Timer();

			initializeQueue();
			
			timer.schedule(new QueueMonitorTimerTask(), _delay, _period);
		}
		else {
			log.warn("[LISTENER QueueMonitorListener OFF] ");
		}
		
		
	}
	
	private void initializeQueue() {
		log.info("Inizializzazione coda processi");
		
		try {
			queueService = 
				(QueueService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "QueueServiceBean");
			
			queueService.deleteEntireQueue();
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
		}
	}

}
