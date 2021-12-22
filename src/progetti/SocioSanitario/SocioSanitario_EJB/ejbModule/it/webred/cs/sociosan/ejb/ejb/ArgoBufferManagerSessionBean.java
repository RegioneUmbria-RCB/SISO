package it.webred.cs.sociosan.ejb.ejb;

import it.webred.cs.sociosan.ejb.client.ArgoBufferManagerSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.mailing.MailUtils;
import it.webred.mailing.MailUtils.MailParamBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;



@Stateless
public class ArgoBufferManagerSessionBean extends BaseSessionBean implements ArgoBufferManagerSessionBeanRemote {

	@EJB
	protected CTConfigClientSessionBeanRemote conf;

	
	public ArgoBufferManagerSessionBean() throws NamingException {
		super();
	}

	@Override
	public void sendSimpleMailFromSISO(String emailDest, String oggetto, String corpo) throws SocioSanitarioException {
		MailParamBean mailParams;
		if(!StringUtils.isBlank(emailDest)){
			mailParams = conf.getSISOMailParametres();
			MailUtils.sendSimpleEmail(mailParams, emailDest, oggetto, corpo);
		}
	}

}
