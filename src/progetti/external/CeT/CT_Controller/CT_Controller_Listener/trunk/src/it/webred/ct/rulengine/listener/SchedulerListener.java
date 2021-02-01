package it.webred.ct.rulengine.listener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.service.bean.QuartzService;
import it.webred.ct.rulengine.service.bean.SchedulerTimeService;
import it.webred.rulengine.ServiceLocator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Listener di schedulazione
 * i job hanno un nome che rispecchia la seguente formattazione:
 * 
 * 'REJob:<belfiore>@<cod_command>@<millisecondi relativi a DT_START>'
 * es: 'REJob:F704@REPLOCAL8@1294233487000'
 * 
 * @author webred
 *
 */
public class SchedulerListener extends AbstractControllerListener implements ServletContextListener {
	
	private Logger log = Logger.getLogger(SchedulerListener.class.getName());
	
	
	private SchedulerTimeService schedulerTimeService;
	
	private QuartzService quartzService;
	
	public SchedulerListener() {
		super();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		if(_cfg.getProperty("sched.listener.active").equals("Y")) {
			
			log.info("[LISTENER SchedulerListener ON] ");
			
			try {
				//recupero servizi
				schedulerTimeService = (SchedulerTimeService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "SchedulerTimeServiceBean");
				
				quartzService = (QuartzService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "QuartzServiceBean");
				
				this.startScheduler();
				
			}catch(Exception e) {
				log.error("Eccezione inizializzazione listener scheduler: "+e.getMessage(),e);
			}	
		}
		else {
			log.warn("[LISTENER SchedulerListener OFF] ");
		}
	}
	
	
	
	private void startScheduler() throws Exception {
		try {
			
			log.debug("Inizializzazione SchedulerListener");
			Scheduler sched = StdSchedulerFactory.getDefaultScheduler();
			sched.start();
			
			log.debug("Recupero elenco processi schedulati attivi");
			List<RSchedulerTime> llSchedTime = schedulerTimeService.getSchedulerProcesses();
			
			log.debug("Processi schedulati: "+llSchedTime.size());
			if(llSchedTime.size() > 0) {
				
				log.debug("SchedulerListener inizializzato");
				
				log.debug("Configurazione JobDetailer");
				for(RSchedulerTime rst: llSchedTime) {
					
					String intervallo = "";
					int campoIntervallo = 0;
					int amountIntervallo = 0;
					if (rst.getOgniGiorni() != null) {
						intervallo = rst.getOgniGiorni().toString()
								+ (new Long(1).equals(rst.getOgniGiorni()) ? " giorno"
										: " giorni");
						campoIntervallo = Calendar.DAY_OF_YEAR;
						amountIntervallo = rst.getOgniGiorni().intValue();
					} else if (rst.getOgniMesi() != null) {
						intervallo = rst.getOgniMesi().toString()
								+ (new Long(1).equals(rst.getOgniMesi()) ? " mese"
										: " mesi");
						campoIntervallo = Calendar.MONTH;
						amountIntervallo = rst.getOgniMesi().intValue();
					} else if (rst.getOgniOre() != null) {
						intervallo = rst.getOgniOre().toString()
								+ (new Long(1).equals(rst.getOgniOre()) ? " ora"
										: " ore");
						campoIntervallo = Calendar.HOUR;
						amountIntervallo = rst.getOgniOre().intValue();
					} else if (rst.getOgniSettimane() != null) {
						intervallo = rst.getOgniSettimane().toString()
								+ (new Long(1).equals(rst.getOgniSettimane()) ? " settimana"
										: " settimane");
						campoIntervallo = Calendar.WEEK_OF_YEAR;
						amountIntervallo = rst.getOgniSettimane().intValue();
					} else if (rst.getOgniMinuti() != null) {
						intervallo = rst.getOgniMinuti().toString()
								+ (new Long(1).equals(rst.getOgniMinuti()) ? " minuto"
										: " minuti");
						campoIntervallo = Calendar.MINUTE;
						amountIntervallo = rst.getOgniMinuti().intValue();
					}

					boolean periodico = "".equals(intervallo) ? false : true;
					if (periodico) {
						Calendar c = quartzService
								.getNextExecutionTime(rst);
						if(c == null){
							//il job non è presente in quartz, lo sincronizzo
							quartzService.addJob(rst);
							c = quartzService
									.getNextExecutionTime(rst);
						}
					}				
				}
			}
			

		}catch(Exception e) {
			throw e;
		}
	}
}
