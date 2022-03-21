package it.webred.AMProfiler.deployer;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

//CLASSE CHE REGOLA L'ORDINE DI DEPLOY PER JBOSS 7
public class DeployManager implements ServletContextListener {

	protected static Logger logger = Logger.getLogger("am.log");

	public DeployManager() {
		super();
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	public void contextInitialized(ServletContextEvent arg0) {

		try {

			File deployPath = new File(
					System.getProperty("jboss.server.base.dir")
							+ File.separator + "deployments");
			
			File[] fList = deployPath.listFiles();

			for (File file : fList) {
				if (!file.getName().startsWith("CT_Service") && !file.getName().startsWith("AmProfiler")) {
					if (file.getName().endsWith("war") || file.getName().endsWith("ear")){
						File fi = new File(file.getAbsolutePath() + ".dodeploy");
						fi.createNewFile();
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
