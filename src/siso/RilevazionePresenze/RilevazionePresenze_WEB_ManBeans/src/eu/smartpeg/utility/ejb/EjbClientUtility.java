package eu.smartpeg.utility.ejb;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.logging.Logger;

public class EjbClientUtility {
	
	

    private static final String EJB_CONTEXT;

    static {
        try {
            EJB_CONTEXT = "java:global/" + new InitialContext().lookup("java:app/AppName") + "/";
        } catch (NamingException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private static Context initialContext;
    private static final String PKG_INTERFACES = "org.jboss.ejb.client.naming";
    public static Logger logger = Logger.getLogger("ClientUtility.log");
    
    
    

    public static Context getInitialContext() throws NamingException {
        if (initialContext == null) {
            Properties properties = new Properties();
            properties.put(Context.URL_PKG_PREFIXES, PKG_INTERFACES);
            initialContext = new InitialContext(properties);
         }
        return initialContext;
    }
    
    // Helpers ------------------------------------------------------------------------------------

    /**
     * Lookup the current instance of the given EJB class from the JNDI context. If the given class
     * implements a local or remote interface, you must assign the return type to that interface to
     * prevent ClassCastException. No-interface EJB lookups can just be assigned to own type. E.g.
     * <li><code>IfaceEJB ifaceEJB = EJB.lookup(ConcreteEJB.class);</code>
     * <li><code>NoIfaceEJB noIfaceEJB = EJB.lookup(NoIfaceEJB.class);</code>
     * @param <T> The EJB type.
     * @param ejbClass The EJB class.
     * @return The instance of the given EJB class from the JNDI context.
     * @throws IllegalArgumentException If the given EJB class cannot be found in the JNDI context.
     */
    @SuppressWarnings("unchecked") // Because of forced cast on (T).
    public static <T> T lookup(Class<T> ejbClass) {
        String jndiName = EJB_CONTEXT + ejbClass.getSimpleName();
        
        logger.info("lookup for jndiName: "+jndiName);

        try {
            // Do not use ejbClass.cast(). It will fail on local/remote interfaces.
            return (T) new InitialContext().lookup(jndiName);
        } catch (NamingException e) {
            throw new IllegalArgumentException(
                String.format("Cannot find EJB class %s in JNDI %s", ejbClass, jndiName), e);
        }
    }    
    
	public static Object getEjb(String ear, String module, String ejbName, String interfaceName) {
		Context cont;
		try {
			cont = getInitialContext();
			String lookupName = getLookupName(ear,module,ejbName,interfaceName);
			return cont.lookup(lookupName);
		} catch (NamingException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param ear EAR name of the deployed EJB without .ear suffix
	 * @param module The module name is the JAR name of the deployed EJB without the .jar
	 * @param ejbName The EJB bean implementation class name
	 * @return
	 */
    private static String getLookupName(String ear, String module, String ejbName, String interfaceName) {
       // AS7 allows each deployment to have an (optional) distinct name. This
       // can be an empty string if distinct name is not specified.
       //String distinctName = "";

       // Fully qualified remote interface name
       //final String interfaceName = "com.baeldung.ejb.tutorial.HelloWorldRemote";

       // Create a look up string name
       //       String name = "ejb:" + appName + "/" + moduleName + "/" + distinctName
       //               + "/" + beanName + "!" + interfaceName;
       
       String name = "java:global/" + ear + "/" + module + "/" + ejbName+ "!" + interfaceName;
       
       return name;
   }
}
