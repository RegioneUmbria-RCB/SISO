package it.umbriadigitale.soclav.timertask;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.WebApplicationContextUtils;

import it.umbriadigitale.soclav.model.RdCKeyValueExt;
import it.umbriadigitale.soclav.service.interfaccia.IRdCKeyValueExtService;

@WebListener
public class SocLavListener implements ServletContextListener {
	
	public final Logger logger = Logger.getLogger("SocLav");
	
	@Autowired
	protected IRdCKeyValueExtService  keyValueExtService;

	public SocLavListener() {
		super();
	}

	
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub	
	}
	
	public void contextInitialized(ServletContextEvent event) {
		
		
		
		boolean abilitato = true;
	
		try {
			
			AutowireCapableBeanFactory autowireCapableBeanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()).getAutowireCapableBeanFactory();
	        autowireCapableBeanFactory.autowireBean(this);
			
			RdCKeyValueExt amKey = this.keyValueExtService.getKeyValueExtparam("soclav.timertask.abilita"); 
			if(amKey != null && amKey.getKeyValue() != null && !amKey.getKeyValue().trim().isEmpty()) 
				abilitato ="1".equalsIgnoreCase(amKey.getKeyValue());
			 
			if(abilitato)	
				initTimerTaskGiornaliero(autowireCapableBeanFactory);
		
		} catch (Exception e) {
			logger.error("__ EnteTimerTask: Eccezione: " + e.getMessage(), e);
		}
		
	}
	
	private void initTimerTaskGiornaliero(AutowireCapableBeanFactory factory){
		logger.debug("Inizializzazione SocLavTimerTaskGiornaliero");
		Timer timer = new Timer();
		
		/* Programmo a cadenza giornaliera - ore 01:00 o scaglionati a distanza di ore, se diversi*/ 
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 1);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.add(Calendar.DAY_OF_MONTH, 1);
		logger.debug("initZsTimerTaskGiornaliero "+ today.getTime());
		timer.schedule(new SocLavTimerTaskGiornaliero(factory), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}
	
}
