package it.webred.ejb.utility;
 
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.jboss.ejb.client.ContextSelector;
import org.jboss.ejb.client.EJBClientConfiguration;
import org.jboss.ejb.client.EJBClientContext;
import org.jboss.ejb.client.PropertiesBasedEJBClientConfiguration;
import org.jboss.ejb.client.StatelessEJBLocator;
import org.jboss.ejb.client.remoting.ConfigBasedEJBClientContextSelector;
 
public class ClientUtility {
 
	private  static Logger log = Logger.getLogger(ClientUtility.class.getSimpleName());
	//protected static Logger logger = Logger.getLogger(ClientUtility.class.getName());
    
	/*
	 * Fornisce l'interfaccia remota di un ejb nello stesso AS J2EE
	 */
    public static Object getEjbInterface(String ear, String module, String ejbName) throws NamingException {		
		Context cont;
		cont = new InitialContext();
		return cont.lookup("java:global/" + ear + "/" + module + "/" + ejbName);
	}
	
    public static <ejbRemoteInterfaceClass> ejbRemoteInterfaceClass getEJBInterfaceForRemoteJBOSSClient(Class<ejbRemoteInterfaceClass> ejbRemoteInterfaceClass, String appName, String moduleName, String EJBName) throws NamingException {

    	// TUTTE LE PROPRIETA' DELLA CHIAMATA VENGONO AUTMATICAMENTE PESCATE DAL STANDALONE.XML
    	// NON C'è BISOGNO DI SPECIFICARE QUI , UTENTE, PASSWORD, HOST PORT ECC.
        final Properties ejbProperties = new Properties();
        ejbProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        ejbProperties.put("jboss.naming.client.ejb.context", true);

        final Context ejbContext = new InitialContext(ejbProperties);
        return (ejbRemoteInterfaceClass) ejbContext.lookup("ejb:"+appName + "/" + moduleName + "/" + EJBName + "!" + ejbRemoteInterfaceClass.getCanonicalName()); 
        

    	
    }   
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
     * Fornisce l'interfaccia remota di un EJB 
     * MA FUNZIONA SOLO PER CLIENT STANDALONE !!!!! NON USARE SU APPLICATION SERVER ...!!!
     */
    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
    @Deprecated
	public  static <ejbRemoteInterfaceClass> ejbRemoteInterfaceClass getEJBInterfaceForSTANDALONERemoteClient(Class<ejbRemoteInterfaceClass> ejbRemoteInterfaceClass, String appName, String moduleName, String EJBName, String distinctName) throws NamingException {
    	Properties pr = new Properties();
    	 
    	pr.put("endpoint.name", "client-endpoint");
    	pr.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
    	pr.put("remote.connections", "default");
    	pr.put("remote.connection.default.port", "4447");
    	pr.put("remote.connection.default.host", "localhost");
  	 
    	
    	EJBClientConfiguration cc = new PropertiesBasedEJBClientConfiguration(pr);
    	 
    	final ContextSelector<EJBClientContext> ejbClientContextSelector = new ConfigBasedEJBClientContextSelector(cc);
    	 
    	// Now let's setup the EJBClientContext to use this selector
    	
    	try {
        	final ContextSelector<EJBClientContext> previousSelector = EJBClientContext.setSelector(ejbClientContextSelector);             
    		
    	} catch (SecurityException se) {
    		log.warn("Non si possiedono i permessi setEJBClientContextSelector, usare altro metodo per la lookup oppure settare i permessi nel security manager DEL CLIENT ");
    	}

    	 
    	StatelessEJBLocator<ejbRemoteInterfaceClass> locator = new StatelessEJBLocator(ejbRemoteInterfaceClass, appName,  moduleName,  EJBName,  distinctName);              
    	 
    	return org.jboss.ejb.client.EJBClient.createProxy(locator);
        
    	
    	 
    }
	
     /*
     * Fornisce l'interfaccia remota di un EJB 
     * MA FUNZIONA SOLO PER CLIENT STANDALONE!!!!!
     */
    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
    @Deprecated
	public  static <ejbRemoteInterfaceClass> ejbRemoteInterfaceClass getEJBInterfaceForSTANDALONERemoteClient(Class<ejbRemoteInterfaceClass> ejbRemoteInterfaceClass, String appName, String moduleName, String EJBName, String distinctName, String host) throws NamingException {
    	Properties pr = new Properties();
    	 
    	pr.put("endpoint.name", "client-endpoint");
    	pr.put("remote.connectionprovider.create.options.org.xnio.Options.SSL_ENABLED", "false");
    	pr.put("remote.connections", "default");
    	pr.put("remote.connection.default.port", "4447");
    	pr.put("remote.connection.default.host", host);	 
    	
    	EJBClientConfiguration cc = new PropertiesBasedEJBClientConfiguration(pr);
    	 
    	final ContextSelector<EJBClientContext> ejbClientContextSelector = new ConfigBasedEJBClientContextSelector(cc);
    	 
    	// Now let's setup the EJBClientContext to use this selector
    	
    	try {
        	final ContextSelector<EJBClientContext> previousSelector = EJBClientContext.setSelector(ejbClientContextSelector);             
    		
    	} catch (SecurityException se) {
    		log.warn("Non si possiedono i permessi setEJBClientContextSelector, usare altro metodo per la lookup oppure settare i permessi nel security manager DEL CLIENT ");
    	}

    	 
    	StatelessEJBLocator<ejbRemoteInterfaceClass> locator = new StatelessEJBLocator(ejbRemoteInterfaceClass, appName,  moduleName,  EJBName,  distinctName);              
    	 
    	return org.jboss.ejb.client.EJBClient.createProxy(locator);
        
    	
    	 
    }
	
    
    
}