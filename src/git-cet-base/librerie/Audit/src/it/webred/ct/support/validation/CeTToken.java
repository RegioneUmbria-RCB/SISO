package it.webred.ct.support.validation;

import java.io.Serializable;

public class CeTToken  implements Serializable {

	private static final long serialVersionUID = 1L;
	private String ente;
	 private String sessionId;
 
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
 
}
