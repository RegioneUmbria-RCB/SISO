package it.umbriadigitale.argo.ejb.client.base.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import it.umbriadigitale.argo.ejb.client.base.dto.pass.SpazioDTO;
import it.umbriadigitale.argo.ejb.client.base.ejbclient.BaseSessionBeanRemote;
import it.webred.amprofiler.ejb.user.UserService;
import it.webred.amprofiler.model.AmUser;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;



import javax.naming.NamingException;

import org.junit.Test;

public class BaseSessionBeanTest {

	
	
	
	 @Test
	    public void testLookup() {
		    try {
		    	BaseSessionBeanRemote bean = lookupRemoteEJB();
		    	assertEquals("sss", "sss");   
				
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  

	    
	

	
	public static CeTBaseObject fillEnte(CeTBaseObject ro) {	
		
		ro.setEnteId("10");
		
		return ro;
	}
	
	
	@Test
	public void getSpazioByBelfiore()  {	
		BaseSessionBeanRemote remote = null;
		SpazioDTO dto = new SpazioDTO();
		dto.setBelfiore("G148");
		
		fillEnte(dto);
		
		try {
			remote = lookupRemoteEJB();
		} catch (NamingException e) {
			fail(e.getMessage());
		}
		
		try {
			remote.getSpazioByBelfiore(dto);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
		
	}
	
	@Test
	public void testGetTableData() {
	    try {
	    	
	    	UserService bean = lookupRemoteEJB2();
	    	
	    	List<AmUser> utenti = bean.getUsersByEnteInizialiGruppo("L872", "SSOCIALE");
	    	
				CeTBaseObject dataIn = new CeTBaseObject();
				dataIn.setEnteId("F704");
				
		//		CsACaso caso = bean.findCasoById(1);
				
				
				
		} catch (NamingException e) {
			fail(e.getMessage());
		}
	}
	
	
	
    private static UserService lookupRemoteEJB2() throws NamingException {

    	
    	UserService ejb = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(UserService.class , 
    			"AmProfiler", "AmProfilerEjb", "UserServiceBean", "");
    	
    	return ejb;
    	 
    }
	    
	
    private static BaseSessionBeanRemote lookupRemoteEJB() throws NamingException {

    	BaseSessionBeanRemote ejb = ClientUtility.getEJBInterfaceForSTANDALONERemoteClient(BaseSessionBeanRemote.class , "Argo", "Argo_EJB", "BaseSessionBean", "");
    	
    	return ejb;
    	
    	 
    }
	

	    		


}
