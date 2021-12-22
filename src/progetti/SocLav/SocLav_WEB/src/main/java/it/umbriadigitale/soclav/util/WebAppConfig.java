package it.umbriadigitale.soclav.util;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import it.umbriadigitale.soclav.util.AppConfig;



@ManagedBean(name = "webAppConfig")
@ApplicationScoped
public class WebAppConfig {
	
	@ManagedProperty("#{appConfig}")
	private AppConfig appConfig;

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}		

}
