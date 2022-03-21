package it.webred.ct.config.parameters.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class IstanzaApplicazioneDTO extends CeTBaseObject {
	private static final long serialVersionUID = 1L;
	
	private String appName;
	private String appTipo;
	private String appCategory;
	private String enteDesc;
	private String enteCod;
	private String url;
	private String uri;
	private String portRewrite;
	private Boolean concatUrl;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppCategory() {
		return appCategory;
	}
	public void setAppCategory(String appCategory) {
		this.appCategory = appCategory;
	}
	public String getEnteDesc() {
		return enteDesc;
	}
	public void setEnteDesc(String enteDesc) {
		this.enteDesc = enteDesc;
	}
	public String getEnteCod() {
		return enteCod;
	}
	public void setEnteCod(String enteCod) {
		this.enteCod = enteCod;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getPortRewrite() {
		return portRewrite;
	}
	public void setPortRewrite(String portRewrite) {
		this.portRewrite = portRewrite;
	}
	public Boolean getConcatUrl() {
		return concatUrl;
	}
	public void setConcatUrl(Boolean concatUrl) {
		this.concatUrl = concatUrl;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getAppTipo() {
		return appTipo;
	}
	public void setAppTipo(String appTipo) {
		this.appTipo = appTipo;
	}
	
}
