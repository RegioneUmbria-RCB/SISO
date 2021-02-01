package it.webred.amprofiler.ejb.client;

import it.webred.amprofiler.ejb.AmProfilerSessionFacade;
import it.webred.amprofiler.ejb.dto.PermessiDTO;
import it.webred.amprofiler.ejb.perm.LoginBeanService;
import it.webred.amprofiler.ejb.perm.LoginServiceException;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.ct.config.model.AmKeyValue;
import it.webred.ct.config.model.AmSection;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ejb.utility.ClientUtility;
import it.webred.utilities.CryptoroUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Main {
	public static void main(String[] args) throws NamingException, NoSuchAlgorithmException {
		
    	
    	UserService bean2 = ClientUtility.getEJBInterfaceForRemoteClient(UserService.class , 
    		"AmProfiler", "AmProfilerEjb", "UserServiceBean", "");
	
	    	LoginBeanService bean = ClientUtility.getEJBInterfaceForRemoteClient(LoginBeanService.class , 
    			"AmProfiler", "AmProfilerEjb", "LoginBean", "");
		    String pass = "profiler";
		    
    	   CeTToken tok = bean.getToken("profiler", "profiler", "G148");
    	   
    	   PermessiDTO dto = new PermessiDTO();
    	   dto.setEnteId("G148");
    	   dto.setSessionId("b1b8622c-1ffS7-416a-9b2f-a13256aa0f42");
    	   bean.getPermissionsByAmInstanceComune(dto);
    	   
    	   
    	   
    	System.out.println(tok.getSessionId());
    	

	}
	

	
}