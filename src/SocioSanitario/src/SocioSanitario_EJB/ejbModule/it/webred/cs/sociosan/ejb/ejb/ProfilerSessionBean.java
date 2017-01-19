package it.webred.cs.sociosan.ejb.ejb;


import java.util.List;

import it.webred.amprofiler.ejb.AmProfilerSessionFacadeBeanRemote;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.cs.sociosan.ejb.client.ProfilerSessionBeanRemote;
import it.webred.cs.sociosan.ejb.client.SocialeSessionBeanRemote;
import it.webred.ejb.utility.ClientUtility;
import it.webred.utils.GenericTuples.T2;

import javax.ejb.Stateless;
import javax.naming.NamingException;

@Stateless
public class ProfilerSessionBean extends BaseSessionBean implements ProfilerSessionBeanRemote {

	
	protected AmProfilerSessionFacadeBeanRemote amprofilerSession;
	protected LoginBeanService loginBean;

	
	public ProfilerSessionBean() throws NamingException {
		super();
		 amprofilerSession = (AmProfilerSessionFacadeBeanRemote) ClientUtility.getEjbInterface("AmProfiler", "Amprofiler_EJB", "AmProfilerSessionFacadeBean");
		 loginBean = (LoginBeanService) ClientUtility.getEjbInterface("AmProfiler", "Amprofiler_EJB", "LoginBean");

	}
	
	

	

	
}
