package it.webred.cs.csa.web.bean.timertask;

import java.net.URL;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.logging.Logger;

public class CSListener implements ServletContextListener {
	
	protected static Logger logger = Logger.getLogger("carsociale.log");

	private static Long _delay =  new Long(300000);  //5 Minuti
	//private static Long _delay =  new Long(60000);    // 1 Minuto

	
	//private static Long _period = new Long(300000);  //5 minuti
	private static Long _period = new Long(900000);  //15 minuti
		
	public CSListener() {
		super();
	}

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
	}
	

	public void contextInitialized(ServletContextEvent arg0) {
			
		//recupero tutti gli enti dal properties per far girare il task su tutti i DB
		String path = System.getProperty("jboss.server.config.dir") + "\\datarouter.properties";
		String newpath = "file:///" + path.replaceAll("\\\\", "/");
		URL url;
		try {
			url = new URL(newpath);

			Properties props = new Properties();
			props.load(url.openStream());
			Enumeration e = props.propertyNames();
			
			boolean zsTimerInit=false;
		    while (e.hasMoreElements()) {
		    
		    	String enteId = (String) e.nextElement();
		    	
		    	if(!zsTimerInit){
		    		initZsTimerTask(enteId);
		    		initZsTimerTaskGiornaliero(enteId);
		    		zsTimerInit=true;
		    	}
		    	
		    	/** AL MOMENTO SOSPESO E SPOSTATO NEL TIMER A LIVELLO DI ZS, SE NECESSARIO RIATTIVARE
				logger.debug("Inizializzazione CSTimerTask per "+enteId);
				Timer timer = new Timer();
			
				EnteTimerTask.setLogger(logger);
				timer.schedule(new EnteTimerTask(enteId), _delay, _period);
				*/
		    }
	    
		} catch (Exception e) {
			logger.error("__ EnteTimerTask: Eccezione: " + e.getMessage(), e);
		}
		
	}
	
	private void initZsTimerTaskGiornaliero(String enteId){
		logger.debug("Inizializzazione ZsTimerTaskGiornaliero (utilizzo il primo ente disponibile per il routing) - "+enteId);
		Timer timer = new Timer();
		
		/* Programmo a cadenza giornaliera - ore 06:00 */
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 5);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		ZsTimerTaskGiornaliero.setLogger(logger);
		timer.schedule(new ZsTimerTaskGiornaliero(enteId), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}
	
	private void initZsTimerTask(String enteId){
		logger.debug("Inizializzazione CsZsTimerTask (utilizzo il primo ente disponibile per il routing) - "+enteId);
		Timer timer = new Timer();
		
		ZsTimerTask.setLogger(logger);
		timer.schedule(new ZsTimerTask(enteId), _delay, _period);
	}

}
