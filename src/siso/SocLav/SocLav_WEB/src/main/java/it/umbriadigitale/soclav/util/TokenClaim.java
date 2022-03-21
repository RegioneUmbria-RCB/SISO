package it.umbriadigitale.soclav.util;

public class TokenClaim extends EsitoAut{

	private String login;
	private String originalString;
	private String linkToProfiler;
	private String ente;
	

	
	
	
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getOriginalString() {
		return originalString;
	}
	public void setOriginalString(String originalString) {
		this.originalString = originalString;
	}
	public String getLinkToProfiler() {
		return linkToProfiler;
	}
	public void setLinkToProfiler(String linkToProfiler) {
		this.linkToProfiler = linkToProfiler;
	}
	
	
}
