package it.webred.ct.aggregator.ejb.utils;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	public static final String FMT_DATE_VIS = "dd/MM/yyyy";
	public static final String FMT_DATE_HOUR_VIS = "dd/MM/yyyy HH:mm"; 
	public static final String DT_FIN_VAL_CAT="31/12/9999";
	
	public static String formatta(java.util.Date data, String fmt) {
		if (data==null)
			return "";
		String dataStr="";
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		dataStr = df.format(data);
		return dataStr;
	}
	
	public static Date faiParse(String dataStr, String fmt) {
		if (dataStr==null)
			return null;
		Date data=null;
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		try {
			data=df.parse(dataStr);
		}catch(java.text.ParseException pe){}
		return data;
	}
	
	public static  int annoData(String sData, String fmt){
		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date1  = null;
		try {
			date1 = df.parse(sData); 
		}catch(java.text.ParseException pe){
			return -1;
		}
		/*Calendar cal1 = null; 
		cal1=Calendar.getInstance(); 
		cal1.setTime(date1);  
		int anno = cal1.get(Calendar.YEAR);
		*/
		return annoData(date1);
	}
	public static  int annoData(Date data){
		if (data==null)
			return -1;
		Calendar cal1 = null; 
		cal1=Calendar.getInstance(); 
		cal1.setTime(data);  
		int anno = cal1.get(Calendar.YEAR);
		return anno;
	}
	
	public static Date dataInizioFineAnno(java.util.Date data, String tipo) {
		if (data==null)
			return null;
		Calendar cal = null; 
		cal=Calendar.getInstance(); 
		cal.setTime(data);  
		int anno = cal.get(Calendar.YEAR);
		return dataInizioFineAnno(anno, tipo);
	}
	
	public static Date dataInizioFineAnno(int anno, String tipo) {
		if (anno==0)
			return null;
		Date dtRet=null;
		String dataStr = "31/12/" + anno;
		if (tipo!=null) {
			if (tipo.equalsIgnoreCase("I")) {
				dataStr = "01/01/" + anno;
			}
			if (tipo.equalsIgnoreCase("F")) {
				dataStr = "31/12/" + anno;
			}
		}
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		try {
			dtRet = df.parse(dataStr); 
		}catch(java.text.ParseException pe){}
		return dtRet;
	}
	
}
