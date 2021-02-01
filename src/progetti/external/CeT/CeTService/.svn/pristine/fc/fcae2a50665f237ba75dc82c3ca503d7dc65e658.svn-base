package it.webred.ct.deployer;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

//CLASSE CHE REGOLA L'ORDINE DI DEPLOY PER JBOSS 7
public class DeployManager implements ServletContextListener {

	protected static Logger logger = Logger.getLogger("ctservice.log");

	public DeployManager() {
		super();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent arg0) {
		
		try {
			
			new Timer().schedule(new TimerTask() {
				
				@Override
				public void run() {
					try {
						File deployPath = new File(
								System.getProperty("jboss.server.base.dir")
										+ File.separator + "deployments");
	
						File f = new File(deployPath.getAbsolutePath() + File.separator + "CT_Service.ear.dodeploy");
						f.createNewFile();
						f = new File(deployPath.getAbsolutePath() + File.separator + "AmProfiler.ear.dodeploy");
						f.createNewFile();
					
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
					}
				}	

			}, 4000);
			
			logger.debug("creato TimerTask per creazioen file .dodeploy");

			
			/*File[] fList = deployPath.listFiles();

			for (File file : fList) {
				if (!file.getName().startsWith("CT_Service_Client")) {
					if (file.getName().endsWith("failed") || file.getName().endsWith("deployed"))
						file.delete();
					if (file.getName().endsWith("war") || file.getName().endsWith("ear")){
						File fi = new File(file.getAbsolutePath() + ".dodeploy");
						fi.createNewFile();
					}
				}
			}*/

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
