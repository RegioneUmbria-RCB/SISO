package com.osmosit.siso.flussoinps.sinba_2018;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Utilities di supporto
 * 
 * @author developer1
 *
 */
class DataTypeSinbaUtils {

	private static final DateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd");



	/**
	 * Normalizza stringa. Restituisce la stringa passata senza spazi prefissi o
	 * postfissi, o una stringa vuota in caso di parametro nullo.
	 * 
	 * @param s
	 *           Stringa da normalizzare
	 * @return <code>s.trim()</code> se la stringa non è <code>null</code>,
	 *         oppure una stringa vuota
	 */
	String normalize(String s) {
		return s != null ? s.trim() : "";
	}



	/**
	 * Controlla se la stringa passata è nulla o vuota
	 * 
	 */
	boolean isNullOrEmpty(String s) {
		return normalize(s).isEmpty();
	}



	/**
	 * Metodo di supporto. Da una mappa String -> Object recupera il valore
	 * mappato dalla chiave e lo restituisce come String.
	 * 
	 * @param m
	 *           mappa
	 * @param key
	 *           chiave
	 * @return il valore di m(key) come String
	 */
	String getString(Map<String, Object> m, String key) {
		return (String) m.get(key);
	}
	
	/**
	 * Metodo di supporto. Da una mappa String -> Object recupera il valore
	 * mappato dalla chiave e lo restituisce come String.
	 * Se l'intero è 0 restituisce una stringa vuota
	 * 
	 * @param m
	 *           mappa
	 * @param key
	 *           chiave
	 * @return il valore di m(key) come String
	 */
	String getStringFromInteger(Map<String, Object> m, String key) {
		String result =  (String) m.get(key);
		return "0".equals(result) ? null : result;
	}



	/**
	 * Metodo di supporto. Da una mappa String -> Object recupera il valore
	 * mappato dalla chiave e lo restituisce come Integer.
	 * 
	 * @param m
	 *           mappa
	 * @param key
	 *           chiave
	 * @return il valore di m(key) come Integer
	 */
	Integer getInteger(Map<String, Object> m, String key) {
		return (Integer) m.get(key);
	}



	/**
	 * Metodo di supporto. Da una mappa String -> Object recupera il valore
	 * mappato dalla chiave e lo restituisce come BigDecimal.
	 * 
	 * @param m
	 *           mappa
	 * @param key
	 *           chiave
	 * @return il valore di m(key) come BigDecimal
	 */
	BigDecimal getBigDecimal(Map<String, Object> m, String key) {
		BigDecimal result; 
		if (m.get(key) instanceof BigDecimal) {
			result = (BigDecimal) m.get(key);
		} else {
				String stringValue = getString(m, key);
				if ("".equals(stringValue) || stringValue==null) {
					result = null;
				} else {
					result = new BigDecimal(stringValue); 
				}
		}

		return result;
	}



	/**
	 * Metodo di supporto. Da una mappa String -> Object recupera il valore
	 * mappato dalla chiave e lo restituisce come {@link Date}.
	 * 
	 * @param m
	 *           mappa
	 * @param key
	 *           chiave
	 * @return il valore di m(key) come {@link Date}
	 */
	Date getDate(Map<String, Object> m, String key) {
		return (Date) m.get(key);
	}



	/**
	 * Metodo di supporto. Da una mappa String -> Object recupera il valore
	 * mappato dalla chiave e lo restituisce come {@link XMLGregorianCalendar} .
	 * 
	 * @param m
	 *           mappa
	 * @param key
	 *           chiave
	 * @return il valore di m(key) come XMLGregorianCalendar
	 * @throws Exception 
	 */
	XMLGregorianCalendar getXmlGregorianCalendar(Map<String, Object> m, String key) throws Exception {
	    String stringDate=null;
	    Object o = m.get(key);
	    if(o!=null){
			if ( o instanceof Date){
				stringDate = fmtDate.format(o);
			}
			else{
				stringDate= getString(m, key);
			}
			
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(stringDate);
	    }
		
	    return null;
	}
}
