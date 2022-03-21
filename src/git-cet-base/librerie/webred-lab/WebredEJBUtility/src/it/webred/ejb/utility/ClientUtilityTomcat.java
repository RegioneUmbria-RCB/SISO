package it.webred.ejb.utility;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;

/**
 * 
 * @author 
 * 
 * Classe per fare lookup degli EJB da applicazioni deployate su Tomcat
 * 
 */
public class ClientUtilityTomcat {

	private static Logger log = Logger.getLogger(ClientUtilityTomcat.class.getSimpleName());

	private static Context context = ClientUtilityTomcat.init();

	private static Context init() {
		
		Hashtable<String, String> initialContextProperties = new Hashtable<String, String>();

		initialContextProperties.put(Context.INITIAL_CONTEXT_FACTORY,
				"org.jboss.naming.remote.client.InitialContextFactory");

		initialContextProperties.put(Context.PROVIDER_URL,
				"remote://localhost:4447");

		initialContextProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");

		initialContextProperties.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOPLAINTEXT",
				"false");

		initialContextProperties.put("jboss.naming.client.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS",
				"false");

		initialContextProperties.put("jboss.naming.client.ejb.context", "true");

		initialContextProperties.put("jboss.naming.client.connect.options.org.xnio.Options.SSL_ENABLED", "false");

		initialContextProperties.put(Context.SECURITY_PRINCIPAL, "XXX");
		initialContextProperties.put(Context.SECURITY_CREDENTIALS, "XXX");

		try {
			return new InitialContext(initialContextProperties);
		} catch (NamingException e) {
			e.printStackTrace();
			log.error("Errore durante inizializzazione context", e);
			return null;
		}
	}

	public static <T> Object getEjbInterface(String ear, String module, String ejbName, Class<T> clazz) throws NamingException {
		try {
			return context.lookup("/" + ear + "/" + module + "/" + ejbName + "!" + clazz.getName());
		} catch (NamingException e) {
			e.printStackTrace();
			log.error("Ejb remoto non accessibile", e);
			throw(e);
		}
	}    
    
}