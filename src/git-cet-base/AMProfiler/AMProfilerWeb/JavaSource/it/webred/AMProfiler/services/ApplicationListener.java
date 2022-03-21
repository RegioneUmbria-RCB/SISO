package it.webred.AMProfiler.services;

import it.webred.utils.RunAntBuild;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

/**
 * La classe predispone e rimuove gli attributi nel Context dell'applicazione
 * recuperando le informazioni dal file di configurazione web.xml
 * 
 * @author Monica
 * @version $Revision: 1.2.2.1 $ $Date: 2010/09/07 15:12:11 $
 */
public class ApplicationListener implements ServletContextListener
{
	private static final Logger log = Logger.getLogger(ApplicationListener.class.getName());

	/**
	 * Effettuo il deploy dei web services
	 * 
	 * @param event
	 *            ServletContextEvent da cui si recupera il context
	 *            dell'applicazione
	 */
	public void contextInitialized(ServletContextEvent event)
	{
		/*try 
		{

			log.info("Effettuo il deploy dei web services di AMProfiler");
			deployWS dws = new deployWS(event.getServletContext());
			
			new Thread(dws).start();
			
		} catch (Exception e) {
			//log.error("Errore in contextInitialized",e);
		}*/
 
		
		
		
	}

	/**
	 * Vengono rimossi dal Context gli attributi 
	 * 
	 * @param event
	 *            ServletContextEvent da cui si recupera il context
	 *            dell'applicazione
	 */
	public void contextDestroyed(ServletContextEvent event)
	{
			// non faccio nulla
	}

	private class deployWS implements Runnable{

		ServletContext sc;
		public deployWS(ServletContext sc) {
			this.sc = sc;
			
		}
		
		public void run()
		{
			
			try 
			{
				log.info("Lancio il build in thread per il deploy dei ws su AMProfiler");
				ServletContext context = sc;
				String dir = context.getRealPath( "/WEB-INF/");
				
				File f = new File(dir, "build.xml");
				log.info("Faccio deploy di " + f.getAbsolutePath());
				
				RunAntBuild runAnt = new RunAntBuild(f,"axis-deploy");
				runAnt.execute();
				
			} catch (Exception e) {
				log.error("Errore nella chiamata a RunAntBuild",e);
			}			
			
		}
		
	}

}



