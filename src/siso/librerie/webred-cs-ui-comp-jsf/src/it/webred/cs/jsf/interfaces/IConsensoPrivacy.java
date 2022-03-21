package it.webred.cs.jsf.interfaces;

import java.util.Date;

public interface IConsensoPrivacy {
		
	public boolean salva();

	public boolean isPrivacySottoscrivi();

	public void setPrivacyDate(Date privacyDate);

	public Date getPrivacyDate();

	public String getPrivacy();

	public boolean isUtenteAnonimo();
}
