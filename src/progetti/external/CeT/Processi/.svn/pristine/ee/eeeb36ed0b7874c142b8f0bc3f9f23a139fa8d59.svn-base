package it.webred.rulengine;


import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.AmKeyValueDTO;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

public class Utils {

	private static final Logger log = Logger.getLogger(Utils.class.getName());
	
	


	public static String getConfigProperty(String key) {
		String value = null;
		
		try {
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setKey(key);
			AmKeyValueExt param = cdm.getAmKeyValueExt(criteria);
			value = param.getValueConf();
		}catch(Exception e) {
			log.error("Eccezione config: "+e.getMessage(),e);
		}
		
		return value;
	}
	
	public static String getConfigProperty(String key, String belfiore, String section) {
		String value = null;
		log.debug("getConfigCCProperty-key: " + key);
		try {
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setComune(belfiore);
			criteria.setKey(key);
			criteria.setSection(section);
			List<AmKeyValueDTO> l = cdm.getListaKeyValue(criteria);
			if (l!=null && l.size()>0)
				value= ((AmKeyValueDTO)l.get(0)).getAmKeyValueExt().getValueConf();
		}catch(Exception e) {
			log.error("Eccezione config: "+e.getMessage(),e);
		}
		log.debug("getConfigProperty-value: " + key);
		return value;
	}
	
	
	public static String getConfigProperty(String key, String belfiore) {
		String value = null;
		
		try {
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			ParameterSearchCriteria criteria = new ParameterSearchCriteria();
			criteria.setComune(belfiore);
			criteria.setKey(key);
			AmKeyValueExt param = cdm.getAmKeyValueExt(criteria);
			value = param.getValueConf();
		}catch(Exception e) {
			log.error("Eccezione config: "+e.getMessage(),e);
		}
		
		return value;
	}
	
	public static String getConfigProperty(String key, String belfiore, Long idFonte) {
		String value = null;
		
		try {
			//recupero del path locale dell ente/fd
			ParameterService cdm = (ParameterService)ServiceLocator.getInstance().getService("CT_Service", "CT_Config_Manager", "ParameterBaseService");
			AmKeyValueExt param = cdm.getAmKeyValueExtByKeyFonteComune(key, belfiore, idFonte.toString());
			log.debug("Parametro richiesto ["+key+"]: "+param.getValueConf());
			value = param.getValueConf();
		}catch(Exception e) {
			log.error("Eccezione config: "+e.getMessage(),e);
		}
		
		return value;
	}
	
	/**
	 * Metodo che ritorna una proprietà da un file 
	 * che ha coe nome ilnomedellaclasse.properties
	 * @param propName
	 * @return
	 */
	public static String getProperty(Class clazz, String propName) {
		String fileName = clazz.getName() + ".properties";
		ClassLoader cl = clazz.getClassLoader();
		InputStream is = cl.getResourceAsStream(fileName);
		if (is==null)
			is = new Utils().getClass().getClassLoader().getResourceAsStream(fileName);
		
		Properties props = new Properties();
		try {
			props.load(is);
		} catch (Exception e) {
			log.error("errore recupero proprietà:" + propName);
			return null;
		}

		String p = props.getProperty(propName);
		return p;
	}
	
	
	public static String getProperty(String propName) {
        String fileName =  "re.properties";
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream is = cl.getResourceAsStream(fileName);
        Properties props = new Properties();        
        try {
        	props.load(is);
        } catch (Exception e) {
        	log.error("errore recupero proprietà:" +propName);
			return null;
		}
        
        String p = props.getProperty(propName);
        return p;
	}
	
	public static int getYear(Date inData){
		int aaaa = 0;
		try{
			Calendar cal = null; 
			cal=Calendar.getInstance(); 
			cal.setTime(inData);  
			aaaa = cal.get(Calendar.YEAR);
			
		}catch(Exception e ){
			aaaa = 0;
		}
			
		return aaaa;
	}//-------------------------------------------------------------------------
	
	public static int getMonth(Date inData){
		int mm = 0;
		try{
			Calendar cal = null; 
			cal=Calendar.getInstance(); 
			cal.setTime(inData);  
			mm = cal.get(Calendar.MONTH);
			mm++;
		}catch(Exception e ){
			mm = 0;
		}
			
		return mm;
	}//-------------------------------------------------------------------------
	
	public static Date fromAAAAMMGGToDate(String inData){
		log.info("Parsing data from: " + inData);
		Date outData = null;
		try{
			GregorianCalendar gc = new GregorianCalendar();
			String aaaa = inData.substring(0, 4);
			String mm = inData.substring(4, 6);
			String gg = inData.substring(6, 8);
			
			gc.set(Integer.parseInt(aaaa), Integer.parseInt(mm)-1, Integer.parseInt(gg));
			outData = gc.getTime();
			
		}catch(Exception e ){
			outData = null;
		}
			
		return outData;
	}//-------------------------------------------------------------------------
	
	public static Date fromGGMMAAAAToDate(String inData){
		log.info("Parsing data from: " + inData);
		Date outData = null;
		try{
			GregorianCalendar gc = new GregorianCalendar();
			String aaaa = inData.substring(4, 8);
			String mm = inData.substring(2, 4);
			String gg = inData.substring(0, 2);
			
			gc.set(Integer.parseInt(aaaa), Integer.parseInt(mm)-1, Integer.parseInt(gg));
			outData = gc.getTime();
			
		}catch(Exception e ){
			outData = null;
		}
			
		return outData;
	}//-------------------------------------------------------------------------
	
	public static Date fromGGMMAAToDate(String inData) {
		log.info("Parsing data from: " + inData);
		Date outData = null;
		try{
			GregorianCalendar gc = new GregorianCalendar();
			String aa = inData.substring(4, 6);
			String mm = inData.substring(2, 4);
			String gg = inData.substring(0, 2);
			/*
			 * L'anno è completato utilizzando il corrente
			 */
			Integer yyyy = Integer.parseInt(aa)+2000;
			gc.set(yyyy, Integer.parseInt(mm)-1, Integer.parseInt(gg));
			outData = gc.getTime();
			
		}catch(Exception e ){
			outData = null;
		}
			
		return outData;
	}//-------------------------------------------------------------------------
	
	public static boolean doZip(String zipFilePath, ArrayList<String> alFilesPath) throws Exception {
        try {
            boolean retVal = false;
            byte[] buf = new byte[1024];
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFilePath));   
            for (int i=0; i<alFilesPath.size(); i++){
            	String filePath = alFilesPath.get(i);
                FileInputStream in = new FileInputStream(filePath);
                String entryFileName = filePath;
                if (filePath.lastIndexOf("/") > -1) {
                    entryFileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
                } 
                out.putNextEntry(new ZipEntry(entryFileName));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();           
            retVal = true;
            return retVal;
        } catch (Exception e) {
            throw e;
        }
   }//--------------------------------------------------------------------------
	
	public static boolean deleteDirTree (File dir){
	    boolean r = true;

	    File[] files = dir.listFiles ();

	    if (files != null){
	        for (File f : files){
	            if (f.isDirectory ())
	                r &= deleteDirTree (f);

	            r &= f.delete ();
	        }
	    }else
	        r = false;

	    return r;
	}//-------------------------------------------------------------------------
	
	public static String eliminaZeriSx(String str) {
		String retVal = new String(str);
		retVal= str;
		int lun=str.length()-1;
		for (int i= 0; i< lun; i++) {
			if (str.charAt(i) == '0') {
				retVal = str.substring(i+1) ;
			}else
				break;
		}
		return retVal;
	}//-------------------------------------------------------------------------
	
	public static String fill(String filler, String pos, String str, int len){
		String retVal = new String(str);
		while (retVal.length() < len) {
			if (pos.equals("sx"))
				retVal = filler + retVal;
			else if  (pos.equals("dx"))
				retVal = retVal+retVal;
			
		}
		return retVal;
	}//-------------------------------------------------------------------------
	
	public static void main(String[] args) {
		Utils.getProperty("server.posta");
	}//-------------------------------------------------------------------------
}

