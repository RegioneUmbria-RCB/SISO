package it.webred.cs.sample.ejb.test;

import static org.junit.Assert.*;

import java.util.List;




import it.webred.ejb.utility.ClientUtility;

import javax.naming.NamingException;

import org.junit.Test;

public class AccessDataTableAppClientTest {

	
	@Test
	public void testGetTableData() {
	    try {
	    	AccessTableSoggettoSessionBeanRemote bean = lookupRemoteEJB();
				CeTBaseObject dataIn = new CeTBaseObject();
				dataIn.setEnteId("F704");
				List<String> lista = bean.getTableData(dataIn);
				for(String data: lista){
					assertNotNull(data);
					System.out.println(data);
				}
				
				dataIn.setEnteId("F205");
				lista = bean.getTableData(dataIn);
				for(String data: lista){
					assertNotNull(data);
					System.out.println(data);
				}
				
		} catch (NamingException e) {
			fail(e.getMessage());
		}
	}
	
	
	
    private static AccessTableSoggettoSessionBeanRemote lookupRemoteEJB() throws NamingException {

    	
    	AccessTableDataSessionBeanRemote ejb = ClientUtility.getEJBInterfaceForRemoteClient(AccessTableDataSessionBeanRemote.class , 
    			"TrainingPrj", "TrainingPrj_EJB", "AccessTableDataSessionBean", "");
    	
    	return ejb;
    	 
    }
	    		


}
