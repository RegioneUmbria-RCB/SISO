package it.webred.cs.sociosan.ejb.client;

import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.mailing.MailUtils.MailParamBean;

import javax.ejb.Remote;

@Remote
public interface CTConfigClientSessionBeanRemote {

	public MailParamBean getSISOMailParametres() throws SocioSanitarioException;

	public String getGlobalParameter(String paramName);

}
