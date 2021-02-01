package it.webred.cs.sample.ejb.test;

import static org.junit.Assert.*;
import it.webred.cs.sample.ejb.HelloWorldManSessionBeanRemote;

import it.webred.ejb.utility.ClientUtility;

import javax.naming.NamingException;

import org.junit.Test;


public class HelloWorldManAppClientTest {

	@Test
	public void testSalutareSpringAOP() {
	    try {
				HelloWorldManSessionBeanRemote bean = lookupRemoteEJB();
				assertEquals(bean.salutareSpringAOP("Arrivederci"), "A presto!");   
				
		} catch (NamingException e) {
			fail(e.getMessage());
		}
	    
	}
	
	@Test
	public void testsalutareAspectJ() {
		try {
			HelloWorldManSessionBeanRemote bean = lookupRemoteEJB();
			bean.salutareAspectJ();
		} catch (NamingException e) {
			fail(e.getMessage());
		}
		
	}

	
	
    private static HelloWorldManSessionBeanRemote lookupRemoteEJB() throws NamingException {

    	
    	HelloWorldManSessionBeanRemote ejb = ClientUtility.getEJBInterfaceForRemoteClient(HelloWorldManSessionBeanRemote.class , 
    			"TrainingPrj", "TrainingPrj_EJB", "HelloWorldManSessionBean", "");
    	
    	return ejb;
    	 
    }

}
