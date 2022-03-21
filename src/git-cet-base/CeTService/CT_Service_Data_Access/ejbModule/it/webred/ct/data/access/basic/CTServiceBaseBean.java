package it.webred.ct.data.access.basic;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;


public class CTServiceBaseBean implements Serializable {
	
	protected static Logger logger = Logger.getLogger("ctservice.log");
	protected SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

}
