package it.webred.rulengine.test;

import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.webred.rulengine.entrypoint.Launcher;
import it.webred.rulengine.entrypoint.impl.JellyLauncher;

import org.apache.log4j.Logger;

public class TestMainJelly {

	private static final Logger logger = Logger.getLogger(TestMainJelly.class.getName());
	
	private EntityManager emRengine = null;
	
	public static void main(String[] args) {
		
		
		(new TestMainJelly()).test();
	}
	
	private void testSplit() {
		
		String aaa = "TRTRIBICIDICH|814@173@0@814@0|173||||1993||||||||||||11000,00|50|T|||||12|||S|||||||||ALLERONA LOC BANDITELLA|||||||20110128121723|";
		String[] ss = aaa.split("[|]");
		System.out.println(ss.length);
		
		//String bbb = "D279| |1093937|PG0046799|10022009|2|23|D|||";
		String[] ss2 = aaa.split("\\|");
		System.out.println(ss2.length);
		
		StringTokenizer st = new StringTokenizer(aaa, "\\|");
		System.out.println(ss2.length);
		/*
		while(st.hasMoreTokens()) {
			System.out.println("token: "+st.nextToken());
		}
		*/
		
	}
	
	
	private void test() {
		
		logger.debug("Inizio test");
		
		try {
			long t = System.currentTimeMillis();
			
			String processId = "SINT-PROC@"+t;
			
			//Laucher
			Launcher lch = new JellyLauncher("D279",null,null);
			lch.executeCommand("SINT-PROC", processId);
			
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
		}
		
		logger.debug("Fine test");
	}
	
	
	private void writeFiles() {
		int i = 1;
		while (i <= 37) {
			java.io.File fp = new java.io.File("/Sviluppo/REProcess/src/config/CHAINS/car-"+i+".properties");
			java.io.File fx = new java.io.File("/Sviluppo/REProcess/src/config/CHAINS/car-"+i+".xml");
			
			java.io.FileWriter fw = null;
			
			try {
				fw = new java.io.FileWriter(fp);
				fw.flush();
				fw.close();
				
				fw = new java.io.FileWriter(fx);
				fw.flush();
				fw.close();
				
			}catch (Exception e) {}
			
			i++;
		}
	}

}
