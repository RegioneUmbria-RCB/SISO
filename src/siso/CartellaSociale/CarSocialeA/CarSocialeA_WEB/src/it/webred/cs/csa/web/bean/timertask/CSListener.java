package it.webred.cs.csa.web.bean.timertask;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jboss.logging.Logger;

import it.webred.cs.csa.ejb.client.configurazione.AccessTableConfigurazioneEnteSessionBeanRemote;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

public class CSListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger("carsociale_timertask.log");
	
	protected AccessTableConfigurazioneEnteSessionBeanRemote confService;
	
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
	
	private boolean abilita() throws IOException{
		boolean abilita = false;
		String path = System.getProperty("jboss.server.config.dir") + "\\timertask.properties";
		String newpath = "file:///" + path.replaceAll("\\\\", "/");
		URL url ;
		try {
			url= new URL(newpath);
			Properties props = new Properties();
			props.load(url.openStream());
			Object val = props.get("CSOCIALE");
			abilita = val!=null && "ON".equalsIgnoreCase(val.toString());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return abilita;
	}

	public void contextInitialized(ServletContextEvent arg0) {
			
		//recupero tutti gli enti dal properties per far girare il task su tutti i DB
		String path = System.getProperty("jboss.server.config.dir") + "\\datarouter.properties";
		String newpath = "file:///" + path.replaceAll("\\\\", "/");
		URL url;
		try {
			
			confService =   (AccessTableConfigurazioneEnteSessionBeanRemote) 
					ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB","AccessTableConfigurazioneEnteSessionBean");
			
			url = new URL(newpath);

			Properties props = new Properties();
			props.load(url.openStream());
			Enumeration e = props.propertyNames();
			
			boolean abilita = this.abilita();
			List<String> capofila = new ArrayList<String>();
			//boolean zsTimerInit=false;
		    while (e.hasMoreElements() && abilita) {
		    	//Recupero il capofila per ogni CS
		    	String enteId = (String) e.nextElement();
		    	CsOOrganizzazione org = null;
		    	try{
			    	CeTBaseObject dto = new CeTBaseObject();
			    	dto.setEnteId(enteId);
			    	org = confService.getOrganizzazioneCapofila(dto);
		    	}catch(Exception err){
		    		logger.error("Errore recupero capofila per:"+enteId, err);
		    	}
		    	
		    	if(org!=null && !capofila.contains(org.getCodRouting())) 
		    		capofila.add(org.getCodRouting());
		    }
		    
		    int incremento = 0;
	    	for(String enteId : capofila){
	    		initZsTimerTask(enteId);
	    		initZsTimerTaskGiornaliero(enteId, incremento);
	    		incremento++;
	    	}
		    
		    if(!abilita) logger.warn("I timertask sono stati disattivati: per attivarli impostare il valore CSOCIALE=ON nel file timertask.properties (standalone/configuration");
	    
		} catch (Exception e) {
			logger.error("__ EnteTimerTask: Eccezione: " + e.getMessage(), e);
		}
		
	}
	
	private void initZsTimerTaskGiornaliero(String enteId, int incremento){
		logger.debug("Inizializzazione ZsTimerTaskGiornaliero (Utilizzo il comune capofila per il routing) - "+enteId);
		Timer timer = new Timer();
		
		/* Programmo a cadenza giornaliera - ore 01:00 o scaglionati a distanza di ore, se diversi*/ 
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 1 +incremento);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.add(Calendar.DAY_OF_MONTH, 1);
		logger.debug("initZsTimerTaskGiornaliero "+enteId+" "+ today.getTime());
		ZsTimerTaskGiornaliero.setLogger(logger);
		timer.schedule(new ZsTimerTaskGiornaliero(enteId), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}
	
	private void initZsTimerTask(String enteId){
		logger.debug("Inizializzazione CsZsTimerTask (Utilizzo il comune capofila per il routing) - "+enteId);
		Timer timer = new Timer();
		
		ZsTimerTask.setLogger(logger);
		timer.schedule(new ZsTimerTask(enteId), _delay, _period);
	}

}
