package eu.smartpeg.rilevazionepresenze.web.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ferdinando.gargiulo
 *
 */
public class Utils {
	/**
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int daysBetween(Date d1, Date d2) {
		double millisDiffDays = d2.getTime() - d1.getTime();
		double millisInDay = 1000 * 60 * 60 * 24;
		double days = millisDiffDays / millisInDay + 1;
		return (int) Math.ceil(days);
	}

	/**
	 * @param dateToCompare
	 * @param min
	 * @param max
	 * @return
	 */
	public static boolean isBeetween(Date dateToCompare, Date min, Date max) {
		if (dateToCompare.compareTo(min) == 0 || dateToCompare.compareTo(max) == 0) {
			return true;
		}
		return dateToCompare.after(min) && dateToCompare.before(max);
	}

	/**
	 * @param date
	 * @return
	 */
	public static String formatDate(String format, Date date) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static void main(String[] args) {
		Date from = new Date();
		from.setMonth(4);
		from.setYear(2021);
		from.setDate(1);
		
		Date to = new Date();
		to.setMonth(4);
		to.setYear(2021);
		to.setDate(31);
		System.out.println(daysBetween(from, to));
    }
}
