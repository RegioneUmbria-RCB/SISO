package it.webred.cs.csa.web.bean.timertask;

import java.util.TimerTask;

import javax.interceptor.ExcludeDefaultInterceptors;

import org.jboss.logging.Logger;

@ExcludeDefaultInterceptors
public class EnteTimerTask extends TimerTask {

	private static Logger logger = Logger.getLogger("carsociale_timertask.log");
	private String enteId;	

	public EnteTimerTask(String enteId) {
		super();
		this.enteId = enteId;
	}

	@Override
	public void run() {

		try {
				
			logger.debug("__ FINE Esecuzione EnteTimerTask ENTEID " + enteId +" __");

		} catch (Exception e) {
			logger.error("__ EnteTimerTask: Eccezione su ENTEID " + enteId + ": " + e.getMessage(), e);
		}
	}

	public static void setLogger(Logger logger) {
		EnteTimerTask.logger = logger;
	}

}
