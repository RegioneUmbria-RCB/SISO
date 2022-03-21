package it.webred.ct.support.datarouter;

import java.io.Serializable;

/**
 * Questa è la classe base che deve essere estesa
 * dalle altre classi di CeT per permettere il routing del data source
 * 
 * @author Francesco Azzola
 * */
public class CeTBaseObject implements Serializable {

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
