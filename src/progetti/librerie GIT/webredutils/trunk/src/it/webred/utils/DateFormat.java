package it.webred.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dan
 */
public class DateFormat
{
	public static java.sql.Date stringToDate(java.lang.String s, String formato)
	{
		if (s == null || s.trim().equals(""))
			return null;
		SimpleDateFormat df = new SimpleDateFormat(formato);
		df.setLenient(false);
		try
		{
			return new java.sql.Date(df.parse(s).getTime());
		}
		catch (ParseException e)
		{
			return null;
		}
	}
	
	public static boolean compare(Date d1, Date d2) {
		if (d1==null && d2==null)
			return true;
		
		if (d1!=null && d2==null)
			return false;
		if (d1==null && d2!=null)
			return false;

		if (d1.compareTo(d2)==0)
			return true;
		else
			return false;
	}

	public static java.lang.String dateToString(java.util.Date d, String formato)
	{
		try
		{
			String dataOut = "";
			if ((d != null) && (d.toString().length() > 0))
			{
				dataOut = new java.text.SimpleDateFormat(formato).format(d);
			}
			return dataOut;
		}
		catch (Exception e)
		{
			return "";
		}

	}

	public static boolean maggiore(java.util.Date a, java.util.Date b)
	{
		if (a == null || b == null)
			return false;
		return a.after(b);
	}

	public static String genDataAttuale()
	{

		return dateToString(new Date(), "yyyyMMdd");
	}

	public static String genDataPerHl7(java.sql.Date data)
	{
		return dateToString(data, "yyyyMMdd");
	}

	public static String genDataPerHl7ConOra(java.sql.Date data)
	{
		return dateToString(data, "yyyyMMddHHmm");
	}

	public static boolean provaData(String s)
	{
		boolean valida = false;
		s = s.replace('-', '/');
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false);
		try
		{
			df.parse(s);
			valida = true;
		}
		catch (Exception e)
		{
		}
		return valida;
	}

}
