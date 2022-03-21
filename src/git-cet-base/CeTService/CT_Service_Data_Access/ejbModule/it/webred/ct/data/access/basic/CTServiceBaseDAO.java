package it.webred.ct.data.access.basic;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public class CTServiceBaseDAO implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName="CT_Diogene")
	protected EntityManager manager_diogene;
	
	protected static Logger logger = Logger.getLogger("ctservice.log");
	
	protected Date getCurrentDate(){

		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

}
