package it.webred.cs.sociosan.ejb.ejb;


import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.SocialeSessionBeanRemote;
import it.webred.ejb.utility.ClientUtility;

import javax.ejb.Stateless;
import javax.naming.NamingException;

@Stateless
public class SocialeSessionBean extends BaseSessionBean implements SocialeSessionBeanRemote {

	protected AccessTableConfigurazioneSessionBeanRemote configurazioneSession;

	public SocialeSessionBean() throws NamingException {
		super();
		 configurazioneSession = (AccessTableConfigurazioneSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		// TODO Auto-generated constructor stub
	}
	

	
}
