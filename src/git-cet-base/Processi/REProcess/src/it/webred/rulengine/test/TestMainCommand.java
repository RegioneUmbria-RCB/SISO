package it.webred.rulengine.test;

import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.entrypoint.impl.CommandLauncher;


import org.apache.log4j.Logger;

public class TestMainCommand {

	private static final Logger logger = Logger.getLogger(TestMainCommand.class.getName());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		(new TestMainCommand()).test();

	}
	
	private void test() {
		logger.debug("Inizio test");
		
		try {
			long t = System.currentTimeMillis();
			
			String processId = "TXT-CONC@"+t;
			
			//Laucher
			//Launcher lch = new CommandLauncher();
			//lch.executeCommand("TXT-CONC", processId);
			
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		logger.debug("Fine test");
	}

}
