package it.umbriadigitale.argo.web.timertask;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.logging.Logger;

public class ArgoListener implements ServletContextListener {
	
	protected static Logger logger = Logger.getLogger("argo.log");

	private static Long _delay =  new Long(300000);  //5 Minuti
	//private static Long _delay =  new Long(60000);    // 1 Minuto

	
	//private static Long _period = new Long(300000);  //5 minuti
	private static Long _period = new Long(900000);  //15 minuti
		
	public ArgoListener() {
		super();
	}

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
	}
	
	private boolean abilita() throws IOException{
		boolean abilita = false;
		String path = System.getProperty("jboss.server.config.dir") + "\\timertask.properties";
		String newpath = "file:///" + path.replaceAll("\\\\", "/");
		URL url ;
		try {
			url= new URL(newpath);
			Properties props = new Properties();
			props.load(url.openStream());
			Object val = props.get("ARGO");
			abilita = val!=null && "ON".equalsIgnoreCase(val.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return abilita;
	}

	public void contextInitialized(ServletContextEvent arg0) {
			
		try {
			
		    if(this.abilita())
		    		initZsTimerTaskGiornaliero();
		    else
		    	logger.warn("I timertask sono stati disattivati: per attivarli impostare ad ARGO=ON il valore nel file timertask.properties (standalone/configuration");
	    
		} catch (Exception e) {
			logger.error("__ EnteTimerTask: Eccezione: " + e.getMessage(), e);
		}
		
	}
	
	private void initZsTimerTaskGiornaliero(){

		Timer timer = new Timer();
		
		/* Programmo a cadenza giornaliera - ore 02:00 */
		Calendar today = Calendar.getInstance();
		
		today.set(Calendar.HOUR_OF_DAY, 2);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.add(Calendar.DAY_OF_MONTH, 1);
		
		logger.debug("initZsTimerTaskGiornaliero "+ today.getTime());
		
		TimerTaskGiornaliero.setLogger(logger);
		timer.schedule(new TimerTaskGiornaliero(),today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}
	
}
