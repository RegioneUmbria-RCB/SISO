package it.webred.utils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Metodi statici di utilit&agrave;
 * per le stringhe.
 * @author Giulio Quaresima
 * @version $Revision: 1.8 $ $Date: 2010/01/28 15:07:24 $
 */
public class StringUtils
{
	public static String CRLF = System.getProperty("line.separator");
	Random ran = null;
	
	public StringUtils()
	{
		ran = new Random();
		ran.setSeed(System.currentTimeMillis());		
	}
	
	
	
	/**
	 * @param valore
	 * @return true se l striga passata corrisponde a '0'
	 */
	public static boolean isZero(String valore) {
		try {
			BigDecimal subZero = new BigDecimal(valore);
			if (BigDecimal.ZERO.compareTo(subZero)==0)
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}
	   /**
	    * Ritorna la stringa in formato esadecimale
	    *
	    * @param bytes I bytes da convertire
	    * @return La stringa esadecimale che rappresenta i bytes di dati
	    * @throws IllegalArgumentException Se il byte array è nullo
	    */
	   public static String toHexString(byte[] bytes)
	   {
	      if (bytes == null)
	      {
	         throw new IllegalArgumentException("byte array must not be null");
	      }
	      StringBuffer hex = new StringBuffer(bytes.length * 2);
	      for (int i = 0; i < bytes.length; i++)
	      {
	         hex.append(Character.forDigit((bytes[i] & 0XF0) >> 4, 16));
	         hex.append(Character.forDigit((bytes[i] & 0X0F), 16));
	      }
	      return hex.toString();
	   }

	   
	   
	private static String privateQuoteSpecialCharacters(String input, String regexpQuotedCharacterToQuote, String quotingString) 
	{
		Matcher m = Pattern.compile("[" + regexpQuotedCharacterToQuote + "]").matcher(input);
		StringBuffer sb = new StringBuffer((int) (((double) input.length()) * 1.1D));
		while (m.find()) {
			m.appendReplacement(sb, quotingString + "$0");
		}
		m.appendTail(sb);
		
		return sb.toString();			
	}
	
	/**
	 * Utilit&agrave; per quotare una stringa con una 
	 * stringa jolly specificata. <br />
	 * Nell'esempio qui sotto si vede il modo di
	 * utilizzo di questo metodo. <br />
	 * <br />
	 * Codice: 
	 * <tt><pre>
	 * String CRLF = System.getProperty("line.separator");
	 * if (CRLF == null)
	 *   CRLF = "\r\n";
	 *   
	 * String str = "L'ultimo verso della divina commedia e':" +
	 *   CRLF +
	 *   "\"e quindi uscimmo a riveder le stelle\"" + CRLF; 
	 *   
	 * System.out.println(str);
	 * System.out.println(StringUtils.quoteSpecialCharacters(str, "\"'", "\\"));
	 * </pre></tt>
	 * <br />
	 * Risultato:
	 * <tt><pre>
	 * L'ultimo verso della divina commedia e':
	 * "e quindi uscimmo a riveder le stelle"
	 * 
	 * L\'ultimo verso della divina commedia e\':
	 * \"e quindi uscimmo a riveder le stelle\"
	 * </pre></tt>
	 * 
	 * @param input La stringa che si vuole quotare.
	 * Se <code>null</code>, il metodo restituir&agrave; <code>null</code>.
	 * 
	 * @param charactersToQuote La concatenazione dei caratteri
	 * che si vogliono quotare. Se <code>null</code>, la stringa
	 * passata non verr&agrave; modificata.
	 * 
	 * @param quotingString La stringa jolly che
	 * sostituir&agrave; i caratteri da quotare.
	 * 
	 * @return
	 * La stringa quotata, o <code>null</code> nei casi detti sopra.
	 */
	public static String quoteSpecialCharacters(CharSequence input, String charactersToQuote, String quotingString)
	{
		if (quotingString == null)
			quotingString = "\\";
		
		if (input != null && charactersToQuote != null && charactersToQuote.length() > 0)
		{
			if ("\\".equals(quotingString))
				quotingString += quotingString;

			String specialRegularExpressionGroupingCharacters = 
				"\\\\" +
				"\\["  +
				"\\]"  +
				"\\^"  +
				"\\-"  +
				"\\&"  ;
			
			String regexpQuotedCharacterToQuote = privateQuoteSpecialCharacters(charactersToQuote, specialRegularExpressionGroupingCharacters, "\\\\");
			return privateQuoteSpecialCharacters(input.toString(), regexpQuotedCharacterToQuote, quotingString);
		}
		else
		{
			if (input != null)
				return input.toString();
			return null;
		}		
	}
	
	/**
	 * Restituisce una stringa lunga <code>size</code> costituita da spazi 
	 * (UNICODE HEX 0020), ovvero una tabulazione lunga size
	 * @param size
	 * La lunghezza della tabulazione. Se minore o uguale a 0
	 * restituisce la stringa vuota
	 * @return
	 */
	public static String getTab(int size)
	{
		return getTab(size, '\u0020');
	}
	/**
	 * Restituisce una stringa lunga <code>size</code> costituita dal
	 * carattere fillChar passato come argomento 
	 * @param size
	 * La lunghezza della tabulazione. Se minore o uguale a 0
	 * restituisce la stringa vuota
	 * @param fillChar
	 * Il carattere di riempimento della tabulazione
	 * @return
	 */
	public static String getTab(int size, char fillChar)
	{
		if (size > 0)
		{
			char[] tab = new char[size];
			Arrays.fill(tab, ' ');
			return new String(tab);
		}
		else
			return "";
	}
	/**
	 * Restituisce una stringa lunga size, costituita dalla
	 * stringa di lunghezza size costituita dalla stringa
	 * passata come argomento seguita da un numero di spazi sufficiente
	 * per farle raggiungere la lunghezza definita in size.
	 * Nel caso in cui la lunghezza della stringa passata ecceda size, 
	 * se il booleano truncateIfNecessary viene passato a true
	 * viene restituita la stringa precedingString troncata
	 * a lunghezza size, se il boolean truncateIfNecessary viene
	 * passato a false, viene restituita la stringa precedingString
	 * così com'è.
	 * @param size
	 * @param precedingString
	 * @param truncateIfNecessary
	 * @return
	 */
	public static String getTab(int size, String precedingString, boolean truncateIfNecessary)
	{
		if (precedingString == null)
			precedingString = "";
		if (size < 0)
			size = 0;
		if (size > precedingString.length())
			return precedingString + getTab(size - precedingString.length());
		else if (size < precedingString.length() && truncateIfNecessary)
			return precedingString.substring(0, size);
		else
			return precedingString;
	}
	/**
	 * Restituisce una stringa lunga size, costituita dalla
	 * stringa di lunghezza size costituita dalla stringa
	 * passata come argomento seguita da un numero di fillChar sufficiente
	 * per farle raggiungere la lunghezza definita in size.
	 * Se la lunghezza della stringa passata eccede size, 
	 * se il booleano truncateIfNecessary viene passato a true
	 * viene restituita la stringa precedingString troncata
	 * a lunghezza size, se il boolean truncateIfNecessary viene
	 * passato a false, viene restituita la stringa precedingString
	 * così com'è
	 * @param size
	 * @param fillChar
	 * @param precedingString
	 * @param truncateIfNecessary
	 * @return
	 */
	public static String getTab(int size, char fillChar, String precedingString, boolean truncateIfNecessary)
	{
		if (precedingString == null)
			precedingString = "";
		if (size < 0)
			size = 0;
		if (size > precedingString.length())
			return precedingString + getTab(size - precedingString.length(), fillChar);
		else if (size < precedingString.length() && truncateIfNecessary)
			return precedingString.substring(0, size);
		else
			return precedingString;
	}
	
	/**
	 * Restituisce una stringa casuale, di lunghezza casualmente
	 * compresa tra 1 e 256
	 * @return
	 */
	public String getRandomString()
	{
		int len = ran.nextInt(256); len++; // dopo l'incremento len appartiene a {x: 1 <= x < 257}, poiché Random.nextInt(int n) restituisce un numero {x: 0 <= x < n}
		return getRandomString(len, false);
	}
	/**
	 * Restituisce una stringa casuale, di lunghezza casualmente
	 * compresa tra minLen e maxLen
	 * @param minLen
	 * La lunghezza minima (inclusivo)
	 * @param maxLen
	 * La lunghezza massima (inclusivo)
	 * @return
	 */
	public String getRandomString(int minLen, int maxLen)
	{
		return getRandomString(minLen, maxLen, false);
	}
	/**
	 * Restituisce una stringa casuale, di lunghezza len
	 * @param len
	 * @return
	 */
	public String getRandomString(int len)
	{
		return getRandomString(len, false);
	}
	
	/**
	 * Restituisce una stringa casuale, di lunghezza casualmente
	 * compresa tra minLen e maxLen
	 * @param minLen
	 * La lunghezza minima (inclusivo)
	 * @param maxLen
	 * La lunghezza massima (inclusivo)
	 * @param ascii7Only
	 * Se vero, la stringa casuale conterr&agrave; soltanto caratteri
	 * dell'insieme ASCII-7 (UNICODE HEX 0000 - 007F)
	 * @return
	 */
	public String getRandomString(int minLen, int maxLen, boolean ascii7Only)
	{
		if (minLen < 0)
			throw new IllegalArgumentException("La lunghezza minima deve essere maggiore o uguale a 0");
		if (maxLen <= minLen)
			throw new IllegalArgumentException("La lunghezza massima deve essere maggiore della lunghezza minima");
		
		int len = ran.nextInt(++maxLen - minLen); // incremento maxLen perché il limite maggiore è esclusivo, vedi API reference 
		len += minLen;
		return getRandomString(len, ascii7Only);
	}
	/**
	 * Restituisce una stringa casuale, di lunghezza len
	 * @param len
	 * @param ascii7Only
	 * Se vero, la stringa casuale conterr&agrave; soltanto caratteri
	 * dell'insieme ASCII-7 (UNICODE HEX 0000 - 007F)
	 * @return
	 */
	public String getRandomString(int len, boolean ascii7Only)
	{
		if (ascii7Only)
		{
			char[] chars = new char[len];
			for (int i = 0; i < len; i++)
				chars[i] = (char) ran.nextInt(0x80);
			return new String(chars);
		}
		else
		{
			byte[] bytes = new byte[len];
			ran.nextBytes(bytes);
			return new String(bytes);			
		}
	}
	/**
	 * Restituisce una stringa ASCII-7 di 32 caratteri, casuale, e differente
	 * da tutte le altre stringhe passate come array
	 * @param alreadyKeys
	 * L'array di stringhe da cui la stringa restituita deve
	 * essere differente
	 * @param ignoreCase
	 * Stabilisce se la differenza debba tenere conto o meno
	 * della differenza maiuscole / minuscole
	 * @return
	 */
	public String getDifferentKey(boolean ignoreCase, String... alreadyKeys)
	{
		String key = null;
		int i = 0;
		while (!isDifferent((key = getRandomString(32, true)), ignoreCase, alreadyKeys) && i++ < 1000);
		return key;
	}
	private boolean isDifferent(String key, boolean ignoreCase, String... alreadyKeys) throws NullPointerException
	{
		if (alreadyKeys == null || alreadyKeys.length == 0)
			return true;
		for (String item : alreadyKeys)
			if (ignoreCase)
			{
				if (key.equalsIgnoreCase(item))
					return false;
			}
			else
				if (key.equals(item))
					return false;
		return true;
	}
	/**
	 * Restituisce la concatenazione delle stringhe
	 * risultanti dalle chiamate a toString() dei singoli
	 * oggetti componenti l'Array. Il metodo 
	 * toString() dei singoli oggetti non viene espli-
	 * citamente chiamato, si utilizza l'operatore di
	 * concatenazione delle stringhe (+). Perci&ograve;, 
	 * in caso di oggetti null, risulta in concatenazione
	 * il risultato di <code>"" + null</code>.
	 * Un Array null o vuoto restituisce stringa vuota.  
	 * @param objects
	 * @return
	 */
	public static String concatToString(Object... objects)
	{
		String result = "";
		if (objects != null)
			for (Object item : objects)
				result += item;
		return result;
	}
	
	/**
	  ** pad a string S with a size of N with char C 
	  ** on the left (True) or on the right(flase)
	  **/
	  public static String padding( String s, int n, char c , boolean paddingLeft  ) {
	    if (s==null)
	    	s="";
		StringBuffer str = new StringBuffer(s);
	    int strLength  = str.length();
	    if ( n > 0 && n > strLength ) {
	      for ( int i = 0; i <= n ; i ++ ) {
	            if ( paddingLeft ) {
	              if ( i < n - strLength ) str.insert( 0, c );
	            }
	            else {
	              if ( i > strLength ) str.append( c );
	            }
	      }
	    }
	    return str.toString();
	  }

	
	private static char[] capitals = new char[26];
	static {
	"ABCDEFGHIJKLMNOPQRSTUVWXYZ".getChars(0, 26, capitals, 0);
	}
	/**
	 * @param n
	 * Indice che si vuole rappresentare
	 * @return un indice in lettere maiuscole
	 * corrispondente all'indice passato:
	 * <code><pre>
	 * 0: A
	 * 1: B
	 * 2: C
	 * ...
	 * 24: Y
	 * 25: Z
	 * 26: AA
	 * 27: AB
	 * </pre></code>
	 * @throws IllegalArgumentException
	 * Se si passa un numero negativo
	 */
	public static String getLatinCapitalIndex(int n)
	throws IllegalArgumentException
	{
		if (n < 0)
			throw new IllegalArgumentException();
		
		int quoziente = (int) n / 26;
		int resto = n % 26;
		if (quoziente == 0)
			return capitals[resto] + "";
		else
			return getLatinCapitalIndex(--quoziente) + capitals[resto];
	}
	
	
	/**
	 * @param s
	 * @return
	 * <code>true</code> se la {@link String}
	 * passata &egrave; <code>null</code> o vuota.
	 */
	public static boolean isEmpty(String s)
	{
		return s == null || "".equals(s.trim());
	}
	
	public static String nomeCampo2JavaName(String s)
	{
		s = s.toLowerCase();

		int pos =0;
		while((pos = s.indexOf("_"))>0)
		{			
			s=s.substring(0,pos)+s.substring(pos+1,pos+2).toUpperCase()+ s.substring(pos+2,s.length());
		}
		return s;
	}
	public static String JavaName2NomeCampo(String s)
	{
		StringBuffer rit = new StringBuffer();
		rit.append(s.substring(0,1));
		for(int i=1;i<s.length();i++)
		{
			if(Character.isUpperCase(s.charAt(i)))
				rit.append("_");
			rit.append(s.substring(i,i+1));
		}
		return rit.toString().toUpperCase();
	}
	
	
	/**
	 * Restituisce una stringa dalla quale vengono eliminate
	 * le occorrenze a sinistra del carattere indicato 
	 * @param s
	 * @return
	 */
	public static String trimLeftChar(String s,Character c)
	{
		if (s==null)
			return null;
		StringBuffer rit = new StringBuffer();
		boolean fineTrim=false;
		for(int i=0;i<s.length();i++)
		{
			if (s.charAt(i)!=c || fineTrim==true) {
				rit.append(s.charAt(i));
				fineTrim=true;
			}
		}
		return rit.toString();
	}
	
	/**
	 * prende in input la lunghezza di tutti i campi 
	 * @param string : stringa in input
	 * @param i : elenco delle lunghezze di ogni campo
	 * @return
	 */
	public static String[] getFixedFieldsFromString(String string, int ... lunghezze ) {
		String[] ret = new String[lunghezze.length];
		String riga = string;
		int beginIndex = 0;
		for (int j = 0; j < lunghezze.length; j++) {
			int endIndex = beginIndex + lunghezze[j] ;
			ret[j] = riga.substring(beginIndex, endIndex);
            //ret[j]= org.apache.commons.lang.StringUtils.trim(ret[j]);
			ret[j] = ret[j] == null ? null : ret[j].trim();
			                                    
			beginIndex = endIndex;
		}
		return ret;
	}
	public static void main(String args[]) {
		String[] a = StringUtils.getFixedFieldsFromString("1G2008F70400484960588     GRDGRS23B49A063O0GARDONI                 GIULIA ROSA RAFFAELLF09/02/1923AFFORI                 MIMIG_1-CVUN2-2-3UXIR           0000VIA CENTEMERO 17                        +000000000000000000000001                                                                             *", 1,1,4,4,16,16,1,80,30,3,1,35,5,13,10,2,77,1);
		
		StringUtils.getFixedFieldsFromString(a[7] ,24,20,1,10,23,2);
		
	}
}
