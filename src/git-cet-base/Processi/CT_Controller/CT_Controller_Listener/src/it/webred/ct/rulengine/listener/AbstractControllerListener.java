package it.webred.ct.rulengine.listener;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class AbstractControllerListener {
	
	protected static Logger logger = Logger.getLogger(AbstractControllerListener.class.getName());
	protected static Properties _cfg;
	
	public AbstractControllerListener() {
		super();
		
		try  {
			if(_cfg == null) {
				InputStream is = this.getClass().getResourceAsStream("/config/listener.properties");
				_cfg = new Properties();
				_cfg.load(is);
				logger.debug("Configurazione listener acquisita");
			}
			
		}catch(Exception e) {
			logger.error("Errore configurazione listener "+ e.getMessage(),e);
		}
	}	
}
