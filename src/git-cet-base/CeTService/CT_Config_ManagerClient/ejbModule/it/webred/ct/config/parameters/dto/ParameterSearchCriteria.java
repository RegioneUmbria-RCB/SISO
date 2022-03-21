package it.webred.ct.config.parameters.dto;

import java.io.Serializable;
import java.util.Date;

public class ParameterSearchCriteria implements Serializable{
	
	//AmKeyValueExt
	private String type;
	private String comune;
	private String instance;
	private String fonte;
	private String section;
	private String application;
	private String key;
	
	//AmKeyValue
	private String applicationKv;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getFonte() {
		return fonte;
	}

	public void setFonte(String fonte) {
		this.fonte = fonte;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getApplicationKv() {
		return applicationKv;
	}

	public void setApplicationKv(String applicationKv) {
		this.applicationKv = applicationKv;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
