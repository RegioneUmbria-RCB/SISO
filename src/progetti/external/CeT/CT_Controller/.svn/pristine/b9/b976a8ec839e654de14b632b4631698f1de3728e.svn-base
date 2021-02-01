package it.webred.ct.rulengine.web.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.service.bean.QuartzService;
import it.webred.ct.rulengine.service.bean.SchedulerTimeService;
import it.webred.ct.rulengine.web.bean.scheduler.SMonitorBean;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class InitServlet
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Logger logger = Logger.getLogger(InitServlet.class
			.getName());
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {
        
    	sinchronizeScheduler();
    	
    }
    
    private void sinchronizeScheduler(){
    	
    	try {

			/*List<RSchedulerTime> listaRTime = new ArrayList<RSchedulerTime>();
			
			Context cont = new InitialContext();
			
			String ear = "CT_Controller";
			String module = "CT_Controller_EJB";
			String ejbName = "SchedulerTimeServiceBean";
			
			SchedulerTimeService schedulerTimeService = (SchedulerTimeService) cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
			
			ejbName = "QuartzServiceBean";
			QuartzService quartzService = (QuartzService) cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
			
			listaRTime = schedulerTimeService.getSchedulerProcesses();

			logger.info("__ Sincronizzazione Schedulatore INIZIO __");
			
			for (RSchedulerTime rst : listaRTime) {
				
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
			
			logger.info("__ Sincronizzazione Schedulatore FINE __");*/
			
    	} catch (Exception e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		}
    	
    }

}
