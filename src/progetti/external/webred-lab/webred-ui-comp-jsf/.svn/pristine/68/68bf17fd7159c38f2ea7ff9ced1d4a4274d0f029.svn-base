package it.webred.jsf.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessoBean {

	private String sesso;
	private Boolean transessuale;
	
	public SessoBean(){
		sesso = null;
		transessuale=false;
	}
	
	public SessoBean(String sesso, boolean transessuale){
		this.sesso = sesso;
		this.transessuale=transessuale;
	}

	public String getSesso() {
		return sesso;
	}

	public Boolean getTransessuale() {
		return transessuale;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public void setTransessuale(Boolean transessuale) {
		this.transessuale = transessuale;
	}

}
