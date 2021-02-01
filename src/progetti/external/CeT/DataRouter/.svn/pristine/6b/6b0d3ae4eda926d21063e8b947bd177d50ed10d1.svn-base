package it.webred.ct.support.datarouter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import static it.webred.ct.support.datarouter.context.DataContextHolder.*;
import static it.webred.ct.support.datarouter.ContextKey.*;

/**
 * Questa classe gestisce effettivamente il routing fra i diversi datasource
 * 
 * @author Francesco Azzola
 * 
 * */
public class DatasourceRouterBean extends AbstractRoutingDataSource {

	protected Logger logger = Logger.getLogger("ctservice.log");

	@Override
	protected Object determineCurrentLookupKey() {
		try {
			logger.debug("Routing datasource..." + USER_CONTEXT.name());
			UserContext userCtx = (UserContext) get(USER_CONTEXT.name());
			if(userCtx != null){
				String enteId = userCtx.getEnteId();
				String path = System.getProperty("jboss.server.config.dir")
						+ "\\datarouter.properties";
				String newpath = "file:///" + path.replaceAll("\\\\", "/");
				URL url;
				Properties props = new Properties();
				url = new URL(newpath);
				props.load(url.openStream());
				String route = props.getProperty(enteId);
				if(route == null)
					logger.warn("!Data Source " + enteId + " Not Found, Default Source Will Be Used");
				return route;
			}

		} catch (MalformedURLException e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.error("Eccezione: " + e.getMessage(), e);
		} catch (Throwable t) {
			logger.warn("Error userCtx null",t);
		}

		return null;
	}

	@Override
	public java.util.logging.Logger getParentLogger()
			throws SQLFeatureNotSupportedException {
		
		throw new SQLFeatureNotSupportedException("Il Datarouter non supporto questo metodo (non aggiornato alla interfaccia 1.7 - dalla 1.6)");
		
		
	}


}
