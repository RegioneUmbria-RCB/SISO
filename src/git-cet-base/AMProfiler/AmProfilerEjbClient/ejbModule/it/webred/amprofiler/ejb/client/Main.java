package it.webred.amprofiler.ejb.client;

import it.webred.amprofiler.ejb.dto.PermessiDTO;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ejb.utility.ClientUtility;

import java.security.NoSuchAlgorithmException;

import javax.naming.NamingException;

public class Main {
	public static void main(String[] args) throws NamingException, NoSuchAlgorithmException {
		
    	
    	UserService bean2 = ClientUtility.getEJBInterfaceForRemoteJBOSSClient(UserService.class , "AmProfiler", "AmProfilerEjb", "UserServiceBean");
	
	    	LoginBeanService bean = ClientUtility.getEJBInterfaceForRemoteJBOSSClient(LoginBeanService.class,	"AmProfiler", "AmProfilerEjb", "LoginBean");
		    String pass = "profiler";
		    
    	   CeTToken tok = bean.getToken("profiler", "profiler", "G148");
    	   
    	   PermessiDTO dto = new PermessiDTO();
    	   dto.setEnteId("G148");
    	   dto.setSessionId("b1b8622c-1ffS7-416a-9b2f-a13256aa0f42");
    	   bean.getPermissionsByAmInstanceComune(dto);
    	   
    	   
    	   
    	System.out.println(tok.getSessionId());
    	

	}
	

	
}