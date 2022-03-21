package it.webred.siso.ws.client.anag.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "OpenSession")
//@XmlType(propOrder = { "user", "password", "entita" })
public class OpenSession {
	private String sessionID;
	private String user;
	private String password;
	private String azienda;
	private String ufficio;
	private String ruolo;
	private String dataLavoro;
	private String entita;
	
	@XmlElement(name = "SessionID")
	public String getSessionID() {
		return sessionID;
	}
	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAzienda() {
		return azienda;
	}
	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}
	public String getUfficio() {
		return ufficio;
	}
	public void setUfficio(String ufficio) {
		this.ufficio = ufficio;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getDataLavoro() {
		return dataLavoro;
	}
	public void setDataLavoro(String dataLavoro) {
		this.dataLavoro = dataLavoro;
	}
	public String getEntita() {
		return entita;
	}
	public void setEntita(String entita) {
		this.entita = entita;
	}
	
	
	
}
