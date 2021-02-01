package it.webred.cs.sample.ejb;


import javax.ejb.Remote;
import javax.naming.NamingException;



@Remote
public interface HelloWorldManSessionBeanRemote {

	public String salutareSpringAOP(String qualcosAltro);
	
	public void salutareAspectJ();
	

	
}
