package it.webred.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class ShellCommand {

	private String command;
	private String workDir;
	private static final Logger	log		= Logger.getLogger(ShellCommand.class);

	
	public ShellCommand(String command) {
		this.command = command;
	}

	
	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}


	/**
	 * esegue un comando shell di sistema e attenda finché viene terminato
	 * @throws Throwable 
	 */
	public void run() throws Throwable {
		try
        {            
            log.info("ShellCommand: " + command);        
            Runtime rt = Runtime.getRuntime();
            
            Process proc = null;
            if (workDir==null)
            	proc = rt.exec(command);
            else
            	proc = rt.exec(command,null, new File(workDir));
            	
            // any error message?
            StreamGobbler errorGobbler = new 
                StreamGobbler(proc.getErrorStream(), "ERROR");            
            
            // any output?
            StreamGobbler outputGobbler = new 
                StreamGobbler(proc.getInputStream(), "OUTPUT");
                
            // kick them off
            errorGobbler.start();
            outputGobbler.start();
                                    
            // any error???
            int exitVal = proc.waitFor();
            
            log.info("ExitValue: " + exitVal);        
            if (exitVal!=0)
            	throw new Exception(command + ": problemi esecuzione");
        } catch (Throwable t)
          {
            throw t;
          }
	}
	public static void main (String args[])
	throws Throwable
	{
		ShellCommand sc = new ShellCommand("cmd start /C a.bat");
		sc.setWorkDir("C:\\");
		sc.run();
	}
	
	
	
}


class StreamGobbler extends Thread
{
    InputStream is;
    String type;
	private static final Logger	log		= Logger.getLogger(StreamGobbler.class);

    
    StreamGobbler(InputStream is, String type)
    {
        this.is = is;
        this.type = type;
    }
    
    public void run()
    {
        try
        {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null) {
                System.out.println(type + ">" + line);    
                log.info(type + ">" + line);
            }
            } catch (IOException ioe)
              {
                ioe.printStackTrace();  
              }
    }
}


