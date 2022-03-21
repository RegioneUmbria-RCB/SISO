package it.webred.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashMapUtils
{
   
 
	public static ArrayList getArrayList(HashMap passedMap) {
		return new ArrayList(passedMap.values());
	}

	
	@SuppressWarnings("unchecked")
	public static LinkedHashMap sortHashMapByValues(HashMap<String,String> passedMap, boolean ascending, boolean ignorecase)
	{

		List mapKeys = new ArrayList(passedMap.keySet());
		List<String> mapValues = new ArrayList<String>(passedMap.values());
		List<String> mapValuesCase = new ArrayList<String>();
		List mapReturn = null;
		
		if (ignorecase) {
			Iterator itmapv = mapValues.iterator();
			while (itmapv.hasNext())
			{
					String val = (String)itmapv.next();
					mapValuesCase.add(val!=null?val.toUpperCase():null);
			}
			mapReturn = mapValuesCase;
		} else {
			mapReturn = mapValues;
		}
		Collections.sort(mapReturn);
		Collections.sort(mapKeys);

		if (!ascending)
			Collections.reverse(mapReturn);

		LinkedHashMap someMap = new LinkedHashMap();
		Iterator valueIt = mapReturn.iterator();
		while (valueIt.hasNext())
		{
			Object val = valueIt.next();
			Iterator keyIt = mapKeys.iterator();
			while (keyIt.hasNext())
			{
				Object key = keyIt.next();
				boolean test = false;
				if (ignorecase)
					test = passedMap.get(key).toString().equalsIgnoreCase(val!=null?val.toString():null);
				else
					test = passedMap.get(key).toString().equals(val!=null?val.toString():null);

				if (test)
				{
					passedMap.remove(key);
					mapKeys.remove(key);
					someMap.put(key, val);
					break;
				}
			}
		}
		return someMap;
	}
	

}
