package it.webred.utils;

import java.io.*;

import org.apache.tools.ant.*;
import org.apache.tools.ant.helper.ProjectHelperImpl;


/**
 * Classe che è in grado di eseguire un target ant in modo programmatico
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 */
public class RunAntBuild 
{

	 Project project = new Project();
	 ProjectHelper pe = new ProjectHelperImpl();
	 File buildFile;
	 String targetName;
	 
	  
	  /**
		 * @param buildFile
		 * @param target
		 */
	 public RunAntBuild(File buildFile, String targetName) {
		 this.buildFile = buildFile;
		 this.targetName = targetName;
	 }

	 

	  public void setUserProperty (String name, String value)
	  {
		  project.setUserProperty( name, value);
	  }

	  public void setProperty (String name, String value)
	  {
		  project.setProperty( name, value);
	  }

	public void execute()
	{
		  project.addBuildListener( new DefaultLogger()); 
		  project.init();
		  pe.parse( project, buildFile);
		  project.executeTarget( targetName);
	}
	  
}
