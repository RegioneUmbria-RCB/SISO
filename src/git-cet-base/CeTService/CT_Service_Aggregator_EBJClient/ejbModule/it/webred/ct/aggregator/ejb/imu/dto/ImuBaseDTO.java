package it.webred.ct.aggregator.ejb.imu.dto;

import java.io.Serializable;

public class ImuBaseDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String errorCode="00";
	private String errorMessage="";
	

	public String getErrorCode() {
		return errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String stampaRecord(){
		String r =  errorCode +"|"+ errorMessage;
		return r;
	}
	
	
	
}
