package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TriageItemDTO implements Serializable {
	
	
	private int value;
	private String code;
	private String title;
	private String description;
	
	public TriageItemDTO(String code,int value, String title, String description) {
		this.value=value;
		this.code=code;
		this.title=title;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
}
