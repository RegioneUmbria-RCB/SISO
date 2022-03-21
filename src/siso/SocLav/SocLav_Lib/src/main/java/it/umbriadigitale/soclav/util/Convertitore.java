package it.umbriadigitale.soclav.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Convertitore {

	public static Date StringToDate(String dataInput ) {
		
		 Date dataOutput = null; 
		
		try {
		 	dataOutput =Date.valueOf(dataInput);  
			  
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return dataOutput;
	}
}
