package it.roma.comune.servizi.config;

import java.util.Properties;

public class ConfigClass {

private Properties configFile;

private static ConfigClass instance;

private ConfigClass() {
configFile = new java.util.Properties();
	try {
	configFile.load(this.getClass().getClassLoader().getResourceAsStream("it/roma/comune/servizi/config/config.properties"));
	} catch (Exception eta) {
	eta.printStackTrace();
	}
}

private String getValue(String key) {
return configFile.getProperty(key);
}

public static String getProperty(String key) {
	if (instance == null) instance = new ConfigClass();
	return instance.getValue(key);
	}
}
