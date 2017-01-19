package it.webred.cs.sociosan.ejb.ejb;


import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.ArgoBufferManagerSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.CTConfigClientSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.SocialeSessionBeanRemote;
import it.webred.cs.sociosan.ejb.exception.SocioSanitarioException;
import it.webred.ejb.utility.ClientUtility;
import it.webred.mailing.MailUtils;
import it.webred.mailing.MailUtils.MailAddressList;
import it.webred.mailing.MailUtils.MailParamBean;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingException;



@Stateless
public class ArgoBufferManagerSessionBean extends BaseSessionBean implements ArgoBufferManagerSessionBeanRemote {

	@EJB(mappedName = "java:global/SocioSanitario/SocioSanitario_EJB/CTConfigClientSessionBean")
	protected CTConfigClientSessionBeanRemote conf;

	protected AccessTableConfigurazioneSessionBeanRemote configurazioneSession;

	public ArgoBufferManagerSessionBean() throws NamingException {
		super();
		 configurazioneSession = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendSimpleMailFromSISO(String emailDest, String oggetto, String corpo) throws SocioSanitarioException {
		
		MailParamBean mailParams;
		mailParams = conf.getSISOMailPatametres();
		MailUtils.sendEmail(mailParams, new MailAddressList(emailDest), null, null, oggetto, corpo, null);
	}


	
	
	

	
}
