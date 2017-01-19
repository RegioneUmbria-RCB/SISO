package it.webred.cs.sociosan.ejb.client;

import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.mailing.MailUtils;
import it.webred.mailing.MailUtils.MailAddressList;
import it.webred.mailing.MailUtils.MailParamBean;

import javax.ejb.Remote;

@Remote
public interface ArgoBufferManagerSessionBeanRemote {


	public void sendSimpleMailFromSISO(String emailDest, String oggetto, String corpo) throws SocioSanitarioException;


}
