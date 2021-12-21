package it.siso.isee.obj;

import java.io.Serializable;

public class DiscordanzeContestazione implements Serializable {

	private String CDDiscordanza;
	private String tipologiaDiscordanza;
	private String specificaDiscordanza;
	
	public String getCDDiscordanza() {
		return CDDiscordanza;
	}
	public void setCDDiscordanza(String cDDiscordanza) {
		CDDiscordanza = cDDiscordanza;
	}
	public String getTipologiaDiscordanza() {
		return tipologiaDiscordanza;
	}
	public void setTipologiaDiscordanza(String tipologiaDiscordanza) {
		this.tipologiaDiscordanza = tipologiaDiscordanza;
	}
	public String getSpecificaDiscordanza() {
		return specificaDiscordanza;
	}
	public void setSpecificaDiscordanza(String specificaDiscordanza) {
		this.specificaDiscordanza = specificaDiscordanza;
	}
	
}
