package it.webred.ct.rulengine.queue;

import it.webred.ct.rulengine.controller.model.RCoda;
import it.webred.ct.rulengine.controller.model.RFontedatiCommand;
import it.webred.ct.rulengine.dto.Task;
import it.webred.ct.rulengine.queue.exception.BusyQueueException;
import it.webred.ct.rulengine.queue.exception.QueueAttemptLimitException;
import it.webred.ct.rulengine.queue.job.thread.QueueJobThread;
import it.webred.ct.rulengine.service.bean.QueueService;
import it.webred.ct.rulengine.service.bean.RecuperaComandoService;
import it.webred.rulengine.ServiceLocator;

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
public class QueueMonitorTimerTask extends TimerTask {
	
	private static Logger log = Logger.getLogger(QueueMonitorTimerTask.class.getName());
	
	private QueueService queueService;
	
	private RecuperaComandoService recuperaComandoService;
	

	public QueueMonitorTimerTask() {
		super();
		
		queueService = 
			(QueueService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "QueueServiceBean");
		
		recuperaComandoService = 
			(RecuperaComandoService)ServiceLocator.getInstance().getService("CT_Controller", "CT_Controller_EJB", "RecuperaComandoServiceBean");
	}

	@Override
	public void run() {
		log.debug("Queue Monitor: task di gestione code di processi schedulati");
		
		try {
			log.debug("Recupero dalla coda del job da startare");
			List<RCoda> rCode = queueService.getFullRCoda();
			
			if(rCode != null && rCode.size() > 0) {
				
				for(RCoda rCoda: rCode) {
					log.info("Job da elaborare: "+rCoda.getJobname()+ " [ente: "+rCoda.getBelfiore()+"]");

					//controlli pre esecuzione
					if(rCoda.getInizioEsecuzione() != null) {
						//throw new BusyQueueException("Il Job è già in esecuzione");
						log.warn("Il Job è già in esecuzione");
						continue;
					}
					
					//aggiornamento esecuzione e tentativo
					rCoda.setInizioEsecuzione(Calendar.getInstance().getTimeInMillis());
					
					//gestione tentativi
					Long tentativi = rCoda.getNumTentativo();
					if(tentativi != null) {
						//incremento del numero dei tentativi
						tentativi++;
						
						if(tentativi.compareTo(rCoda.getMaxTentativi()) > 0) {
							queueService.deleteProcess(rCoda);  //elimiazione job dalla coda
							//throw new QueueAttemptLimitException("Raggiunto il limite di tentativi per il Job corretnte");
							log.warn("Raggiunto il limite di tentativi per il Job corretnte");
							continue;
						}
					}
					else {
						tentativi = new Long(1);
					}
					
					rCoda.setNumTentativo(tentativi);  //prima volta
					log.info("Refresh informazioni job in coda");
					queueService.saveOrUpdateProcess(rCoda);
					
					log.info("Lancio thread Job della coda");
					new Thread(new QueueJobThread(getTask(rCoda))).start();
					log.info("Thread Job in esecuzione");
				}
			}
			else {
				log.debug("Nessun Job in coda");
			}

		}catch(BusyQueueException bqe) {
			log.warn("Attenzione: "+bqe.getMessage());
		}catch(QueueAttemptLimitException qle) {
			log.warn("Attenzione: "+qle.getMessage());
		}catch(Exception e) {
			log.error("Eccezione: "+e.getMessage(),e);
		}
	}
	
	
	
	private Task getTask(RCoda rCoda) throws Exception {
		
		log.info("Preparazione parametro task per thread Job");
		Long idTipologia = rCoda.getRCommand().getRCommandType().getId();
		Task t = new Task(rCoda.getBelfiore(),null,idTipologia);
		t.setFreeObj(rCoda);
		
		//istante per startDate
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(rCoda.getIstante());
		t.setStartTime(c.getTime());
		
		t.setDescription("Thread Job della coda processi");
		
		
		//modifica per inserimento info fd su cmd di trattamento che ne coinvolgono 1 sola
		List<RFontedatiCommand> fds =  recuperaComandoService.getRCommandFDs(rCoda.getRCommand());
		if(fds.size() == 1) {
			log.debug("Fonti dati recuperata: "+fds.get(0).getId().getIdFonte());
			t.setIdFonte(fds.get(0).getId().getIdFonte());	
		}
		
		
		/*
		//in base alla tipologia va recuperato o meno la FD
		switch(idTipologia.intValue()) {
			case 10:
			case 20: {
				List<RFontedatiCommand> fds =  recuperaComandoService.getRCommandFDs(rCoda.getRCommand());
				log.debug("Fonti dati recuperata: "+fds.get(0).getId().getIdFonte());
				t.setIdFonte(fds.get(0).getId().getIdFonte());
				break;
			}
			default: {
				//do nothing
			}
		}
		*/
		
		return t;
	}

}
