package it.webred.rulengine.diagnostics.dto;

import java.io.Serializable;

public class DiaSubscribedUserDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String emailAddress;
	
	public DiaSubscribedUserDTO(String username, String emailAddress) {
		super();
		this.username = username;
		this.emailAddress = emailAddress;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
}
