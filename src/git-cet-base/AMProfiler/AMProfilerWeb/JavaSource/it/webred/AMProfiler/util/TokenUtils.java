package it.webred.AMProfiler.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class TokenUtils {
  
	private LinkedHashMap<String,String> listTokenEnte = null;
	
	public TokenUtils() {
		listTokenEnte = new LinkedHashMap<String, String>();
	}
	
	public void addTokenEnte(String token, String ente) {
		this.listTokenEnte.put(ente, token);
	}
	public String getToken(String ente) {
		return this.listTokenEnte.get(ente);
	}
	public String getFirstToken() {
		if(!listTokenEnte.isEmpty()){
			Entry<String, String> mapEntry = listTokenEnte.entrySet().iterator().next();
			return   mapEntry.getValue();
		} 
		return null;
	}
	public String getFirstEnte() {
		if(!listTokenEnte.isEmpty()){
			Entry<String, String> mapEntry = listTokenEnte.entrySet().iterator().next();
			return   mapEntry.getKey();
		}
		return null;
	}
}
