package it.webred.ct.service.gestioneDBDoc.data.access;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class GestioneDBDocBaseBean implements Serializable {
	
	protected Logger logger = Logger.getLogger(GestioneDBDocBaseBean.class.getName());
	
	protected Date getCurrentDate(){

		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}

}
