package it.umbriadigitale.argo.data;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

public class DataModelCostanti {

	public static final String NOME_APPLICAZIONE = "Argo";
	public static final Date END_DATE = new GregorianCalendar(9999, 11, 31).getTime();
	public static final String END_DATE_STRING = "31/12/9999";
	
	public static class Segnalibri implements Serializable {

		private static final long serialVersionUID = 1L;
		
	}


}
