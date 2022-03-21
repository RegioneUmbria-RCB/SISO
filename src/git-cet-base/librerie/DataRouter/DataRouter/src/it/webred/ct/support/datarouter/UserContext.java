package it.webred.ct.support.datarouter;

/**
 * Contiene il riferimento al contesto dell'utente utilizzato nell'interceptor
 * 
 * @author Francesco Azzola
 * */

public interface UserContext {

	public String getEnteId();
	
	public void setEnteId(String id);
	
	public String getUserId();
	
	public void setUserId(String userId);
	
	public String getSessionId();

	public void setSessionId(String sessionId);
	
}
