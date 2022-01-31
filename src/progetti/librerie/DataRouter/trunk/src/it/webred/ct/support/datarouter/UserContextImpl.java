package it.webred.ct.support.datarouter;

import java.io.Serializable;

/**
 * Implementazione del contesto dell'utente
 * 
 * @author Francesco Azzola
 * */
public class UserContextImpl implements UserContext,Serializable {

	private String enteId;
	private String userId;
	private String sessionId;
	
	public String getEnteId() {
		return enteId;
	}

	public void setEnteId(String enteId) {
		this.enteId = enteId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
