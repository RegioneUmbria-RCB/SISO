package it.umbriadigitale.soclav.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Qualifier("appConfig")
public class AppConfig {
	
	public final Logger logger = Logger.getLogger(this.getClass());

	@Value("${soclav.version}")
	private String version;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
