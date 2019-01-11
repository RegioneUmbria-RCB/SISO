package it.webred.utilities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Andrea
 * 
 */
public class CommonUtils implements Serializable {

	private static final long serialVersionUID = 1L;

	public static List<String> splitString(String string2split, String splitString) {
		if (StringUtils.isEmpty(string2split) || StringUtils.isEmpty(splitString))
			return new LinkedList<String>();

		List<String> retList = Arrays.asList(string2split.split(splitString));
		return retList;
	}

	public static String Join( String separator, Object[] list)
	{
		String sValue = "";
		
		if( list != null ) {
			for( int i = 0; i < list.length; i++ ) {
				if( list[i] != null) {
					sValue += list[i].toString();
					if( (i + 1) < list.length )
						sValue += separator;
				}
			}
		}
		
		return sValue;
	}

	public static boolean isNotEmptyString(String value) {
		return value != null && value != "";
	}
	public static boolean isEmptyString(String value) {
		return !isNotEmptyString(value);
	}
}
