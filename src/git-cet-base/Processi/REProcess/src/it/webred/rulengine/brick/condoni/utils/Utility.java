package it.webred.rulengine.brick.condoni.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import bsh.ParseException;

public class Utility {
// riempie di filler in posizione (ds,sx) specificata da pos, per una lunghezza totale pari a  len la stringa str
//n.b. per il momento usato per riempire a sx di 0	
	public static String fill(String filler, String pos, String str, int len){
		String retVal = new String(str);
		while (retVal.length() < len) {
			if (pos.equals("sx"))
				retVal = filler + retVal;
			else if  (pos.equals("dx"))
				retVal = retVal+retVal;
			
		}
		return retVal;
	}
	
	public static int giorniDifferenza(String sdate1, String sdate2, String fmt, TimeZone tz, boolean valAss){
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date1  = null;
		Date date2  = null;
		try {
			date1 = df.parse(sdate1); 
			date2 = df.parse(sdate2); 
		}catch(java.text.ParseException pe){
			
		}
		Calendar cal1 = null; 
		Calendar cal2 = null;
		if (tz == null){
			cal1=Calendar.getInstance(); 
			cal2=Calendar.getInstance(); 
		}else{
			cal1=Calendar.getInstance(tz); 
			cal2=Calendar.getInstance(tz); 
		}
		// different date might have different offset
		cal1.setTime(date1);          
		long ldate1 = date1.getTime() + cal1.get(Calendar.ZONE_OFFSET) + cal1.get(Calendar.DST_OFFSET);
		cal2.setTime(date2);
		long ldate2 = date2.getTime() + cal2.get(Calendar.ZONE_OFFSET) + cal2.get(Calendar.DST_OFFSET);
		// Use integer calculation, truncate the decimals
		int hr1   = (int)(ldate1/3600000); //60*60*1000
		int hr2   = (int)(ldate2/3600000);
		int days1 = (int)hr1/24;
		int days2 = (int)hr2/24;
		int dateDiff  = days2 - days1;
		int weekOffset = (cal2.get(Calendar.DAY_OF_WEEK) - cal1.get(Calendar.DAY_OF_WEEK))<0 ? 1 : 0;
		int weekDiff  = dateDiff/7 + weekOffset; 
		int yearDiff  = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR); 
		int monthDiff = yearDiff * 12 + cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
        if (valAss) 
          return Math.abs(dateDiff);
        else
        // RITORNA DIFFERENZA DATE
        	return dateDiff;
	}
	
	public static  int annoData(String sData, String fmt){
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date1  = null;
		Date date2  = null;
		try {
			date1 = df.parse(sData); 
		}catch(java.text.ParseException pe){
			
		}
		Calendar cal1 = null; 
		cal1=Calendar.getInstance(); 
		cal1.setTime(date1);  
		int anno = cal1.get(Calendar.YEAR);
		return anno;
	}
	public static  int meseData(String sData, String fmt){
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date1  = null;
		Date date2  = null;
		try {
			date1 = df.parse(sData); 
		}catch(java.text.ParseException pe){
			
		}
		Calendar cal1 = null; 
		cal1=Calendar.getInstance(); 
		cal1.setTime(date1);  
		int anno = cal1.get(Calendar.MONTH);
		return anno;
	}
	
	
}
