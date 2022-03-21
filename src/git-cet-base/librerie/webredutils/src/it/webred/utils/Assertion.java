package it.webred.utils;

import java.io.File;

/**
 * Classe che si occupa di incapsulare alcune verifiche standard
 *
 * @author Giovanni Cuccu
 * @version $Revision: 1.1 $ - $Date: 2007/05/16 07:15:20 $
 */
public class Assertion
{

	/**
	 * il costruttore non fa nulla sono i metodi static quelli di interesse
	 */
	public Assertion()
	{
	}

	public static void checkNull(Object o, String message)
			throws NullPointerException
	{
		if (o == null)
		{
			throw new NullPointerException((message == null ? "" : message) + " can't be null");
		}
	}
	public static void checkNullOrEmpty(Object o, String message)
	{
		checkNull(o,message);
		checkEmpty(o.toString(),message);
	}

	/**
	 * @param aString
	 *            stringa su cui effetturare la verifica
	 * @param message
	 *            messaggio con cui sollevare l'eccezzione nel caso in cui la
	 *            verifica fallisca
	 * @throws IllegalArgumentException
	 *             se (aString==null) || ((aString=="")
	 */
	public static void checkEmpty(String aString, String message)
			throws IllegalArgumentException
	{
		if ((aString == null) || ("".equals(aString)))
		{
			throw new IllegalArgumentException((message == null ? "" : message) + " can't be null");
		}
	}

	/**
	 * @param condition
	 *            condizione su cui effetturare la verifica
	 * @param message
	 *            messaggio con cui sollevare l'eccezzione nel caso in cui la
	 *            verifica fallisca
	 * @throws IllegalArgumentException
	 *             se !condition
	 */
	public static void check(boolean condition, String message)
			throws IllegalArgumentException
	{
		if (!condition)
		{
			throw new IllegalArgumentException(message == null ? "" : message);
		}
	}

	/**
	 * @param aString
	 *            stringa su cui effetturare la verifica
	 * @return boolean true se (aString==null) || ((aString=="")
	 */
	public static boolean isEmpty(String aString)
	{
		return aString == null || "".equals(aString);
	}

	public static int isInteger(String aString)
	{
		Assertion.checkNull(aString, "aString");
		return Integer.parseInt(aString);
	}
	
	public static boolean isIntegerBoolean(String aString)
	{
		try
		{
			isInteger( aString);
			return true;	
		}
		catch ( NumberFormatException e)
		{
			return false;	
		}
	}
	

	public static float isFloat(String aString)
	{
		Assertion.checkNull(aString, "aString");
		return Float.parseFloat(aString);
	}

	public static double isDouble(String aString)
	{
		Assertion.checkNull(aString, "aString");
		return Double.parseDouble(aString);
	}
	public static double isLong(String aString)
	{
		Assertion.checkNull(aString, "aString");
		return Long.parseLong(aString);
	}
	public static boolean isRegExp(String aString, String regexp)
	{
		Assertion.checkNull(aString, "aString");
		Assertion.checkNull(regexp, "regexp");
		return aString.matches(regexp);
	}

	public static boolean fileExists(String fileName)
	{
		File file = new File(fileName);
		if (!file.exists() || !file.canRead())
		{
			throw new IllegalArgumentException("file '" + fileName + "' is does not exists or is not accessible");
		}
		return true;
	}

	public static boolean dirExists(String fileName)
	{
		File file = new File(fileName);
		if (!file.exists() || !file.canRead() || file.isDirectory())
		{
			throw new IllegalArgumentException(fileName + " is does not exists or is not accessible");
		}
		return true;
	}


	public static CharSequence setString(CharSequence x)
	{
		return setString(x, true);
	}
	/**
	 *
	 * @param x
	 * @param usingAnsiMode
	 * Returned by <i>connection.useAnsiQuotedIdentifiers()</i>
	 * @return
	 */
	public static CharSequence setString(CharSequence x, boolean usingAnsiMode)

 {
     if(x == null)
     {
         return "null";
     } else
     {
         StringBuffer buf = new StringBuffer((int)((double)x.length() * 1.1000000000000001D));
         buf.append('\'');
         int stringLength = x.length();
         for(int i = 0; i < stringLength; i++)
         {
             char c = x.charAt(i);
             switch(c)
             {
             case 0: // '\0'
                 buf.append('\\');
                 buf.append('0');
                 break;

             case 10: // '\n'
                 buf.append('\\');
                 buf.append('n');
                 break;

             case 13: // '\r'
                 buf.append('\\');
                 buf.append('r');
                 break;

             case 92: // '\\'
                 buf.append('\\');
                 buf.append('\\');
                 break;

             case 39: // '\''
                 buf.append('\\');
                 buf.append('\'');
                 break;

             case 34: // '"'
                 if(usingAnsiMode)
                     buf.append('\\');
                 buf.append('"');
                 break;

             case 26: // '\032'
                 buf.append('\\');
                 buf.append('Z');
                 break;

             default:
                 buf.append(c);
                 break;
             }
         }

         buf.append('\'');
         return buf.toString();
     }
 }

}