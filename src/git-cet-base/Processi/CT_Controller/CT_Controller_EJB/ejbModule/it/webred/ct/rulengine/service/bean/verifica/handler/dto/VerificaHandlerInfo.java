package it.webred.ct.rulengine.service.bean.verifica.handler.dto;

import java.io.Serializable;

public class VerificaHandlerInfo implements Serializable {
	
	private boolean esito;
	private String message;
	
	
	public VerificaHandlerInfo(boolean esito, String message) {
		super();
		this.esito = esito;
		this.message = message;
	}
	
	
	public boolean getEsito() {
		return esito;
	}
	public void setEsito(boolean esito) {
		this.esito = esito;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
