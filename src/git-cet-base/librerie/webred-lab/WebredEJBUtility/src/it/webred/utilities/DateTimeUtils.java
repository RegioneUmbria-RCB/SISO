package it.webred.utilities;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateUtils;

/**
 * @author Andrea
 * 
 */

public class DateTimeUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Date getMinDate() {
		return new Date(-3600000);
	}

	public static Date getTime(Date giorno, Date ora) {
		if (giorno == null)
			return null;
		if (ora == null)
			return null;

		if (giorno != null && ora != null) {

			Calendar cTime = Calendar.getInstance();
			cTime.setTime(giorno);
			Calendar cOraScadenza = Calendar.getInstance();
			cOraScadenza.setTime(ora);

			cTime.set(Calendar.HOUR_OF_DAY, cOraScadenza.get(Calendar.HOUR_OF_DAY));
			cTime.set(Calendar.MINUTE, cOraScadenza.get(Calendar.MINUTE));
			cTime.set(Calendar.SECOND, cOraScadenza.get(Calendar.SECOND));
			cTime.set(Calendar.MILLISECOND, cOraScadenza.get(Calendar.MILLISECOND));

			return cTime.getTime();
		}

		return null;
	}

	public static boolean isTimeNull(Date date) {

		if (date == null)
			return true;

		if (DateUtils.isSameInstant(date, getMinDate()))
			return true;

		return false;
	}

	/**
	 * Ritorna l'inizio del giorno
	 * 
	 * @param date
	 * @return
	 */
	public static Date startDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		return cal.getTime();
	}

	/**
	 * Ritorna la fine del giorno
	 * 
	 * @param date
	 * @return
	 */
	public static Date endDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		return cal.getTime();
	}

	/**
	 * Converte la stringa in Date
	 * 
	 * @param stringDate
	 *            data in formato stringa
	 * @param pattern
	 *            pattern per la formattazione in data ( e.g yyyyMMdd.... )
	 * @return la data
	 * @throws ParseException
	 */
	public static Date stringToDate(String stringDate, String pattern) throws ParseException {
		DateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setTimeZone(TimeZone.getTimeZone("CET"));
		Date date = formatter.parse(stringDate);
		return date;
	}

	/**
	 * Converte la data in stringa
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToString(Date date, String pattern) {
		if( date != null )
		{
			DateFormat formatter = new SimpleDateFormat(pattern);
			formatter.setTimeZone(TimeZone.getTimeZone("CET"));
			String s = formatter.format(date);
			return s;
		}
		else
			return "";
	}

	/**
	 * @param dataNascita
	 * @return
	 */
	public static XMLGregorianCalendar dateToGrogorianCalendar(Date data) {
		try {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(data);
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}

}
