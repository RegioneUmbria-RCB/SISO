package it.webred.ct.rulengine.synchronizer;

import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.controller.model.RSchedulerTime;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.queue.exception.BusyQueueException;
import it.webred.ct.rulengine.queue.exception.QueueAttemptLimitException;
import it.webred.ct.rulengine.queue.job.thread.QueueJobThread;
import it.webred.ct.rulengine.service.bean.QuartzService;
import it.webred.ct.rulengine.service.bean.QueueService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.ct.rulengine.service.bean.SchedulerTimeService;
import it.webred.rulengine.ServiceLocator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;

/**
 * Ogni minuto
 * 
 * @author webred
 * 
 */
public class SynchronizerTimerTask extends TimerTask {

	private static Logger log = Logger
			.getLogger(SynchronizerTimerTask.class.getName());

	private SchedulerTimeService schedulerTimeService;

	private QuartzService quartzService;

	public SynchronizerTimerTask() {
		super();

		schedulerTimeService = (SchedulerTimeService) ServiceLocator
				.getInstance().getService("CT_Controller","CT_Controller_EJB","SchedulerTimeServiceBean");

		quartzService = (QuartzService) ServiceLocator.getInstance()
				.getService("CT_Controller","CT_Controller_EJB","QuartzServiceBean");
	}

	@Override
	public void run() {
		log.debug("Synchronizer Monitor: task di gestione sincronizzazione processi schedulati");

		try {
			log.debug("Recupero dalla coda del job da startare");
			List<RSchedulerTime> listaRTime = new ArrayList<RSchedulerTime>();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			listaRTime = schedulerTimeService.getSchedulerProcesses();

			log.debug("__ Sincronizzazione Schedulatore INIZIO __");

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
					Calendar c = quartzService.getNextExecutionTime(rst);
					if (c == null) {
						// il job non è presente in quartz, lo sincronizzo
						quartzService.addJob(rst);
						c = quartzService.getNextExecutionTime(rst);
						log.info("__ Eseguita sincronizzazione per COMANDO: "
								+ rst.getRCommand().getCodCommand() + " ID: "
								+ rst.getRCommand().getId() + " PROX EXEC: "
								+ sdf.format(c.getTime()));
					}
				}
			}

			log.debug("__ Sincronizzazione Schedulatore FINE __");

		} catch (Exception e) {
			log.error("Eccezione: " + e.getMessage(), e);
		}
	}

}
