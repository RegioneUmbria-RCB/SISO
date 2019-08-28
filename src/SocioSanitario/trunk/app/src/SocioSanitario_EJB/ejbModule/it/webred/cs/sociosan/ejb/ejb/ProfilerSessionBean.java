package it.webred.cs.sociosan.ejb.ejb;


import it.webred.amprofiler.ejb.AmProfilerSessionFacadeBeanRemote;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.cs.sociosan.ejb.client.ProfilerSessionBeanRemote;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import javax.ejb.Stateless;
import javax.naming.NamingException;

@Stateless
public class ProfilerSessionBean extends BaseSessionBean implements ProfilerSessionBeanRemote {

	
	protected AmProfilerSessionFacadeBeanRemote amprofilerSession; 
	protected LoginBeanService loginBean;
	
	public ProfilerSessionBean() throws NamingException {
		super();
		try{
		 amprofilerSession = (AmProfilerSessionFacadeBeanRemote) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "AmProfilerSessionFacadeBean!it.webred.amprofiler.ejb.AmProfilerSessionFacadeBeanRemote");
		 loginBean =    (LoginBeanService) ClientUtility.getEjbInterface("AmProfiler", "AmProfilerEjb", "LoginBean");
		}catch(Exception e){
			logger.error(e);
		}
	}

	@Override
	public String getUsernameUtente(CeTBaseObject dto){	
		String username = dto.getUserId();
		if(loginBean!=null)
		     username = loginBean.findUtenteBySessionId((String)dto.getSessionId());
		 return username;
	 }
	
}
