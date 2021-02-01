package it.webred.ct.support.datarouter.context;

import java.util.HashMap;
import java.util.Map;

  
/** 
 * Francesco Azzola - 2010
 * 
 * */

public class DataContextHolder {
	
	private static final ThreadLocal<Map<String, Object>> localData = 
		new ThreadLocal<Map<String,Object>>();
	
	private DataContextHolder() {};
	
	public static void put(String key, Object obj) {
		if (localData.get() == null)
			localData.set(new HashMap<String, Object>());
		
		localData.get().put(key, obj);
	}
	
	public static Object get(String key) {
		if (localData.get() == null)
			return null;
		
		return localData.get().get(key);
	}
	
	public static void clean() {
		localData.remove();
	}

}
