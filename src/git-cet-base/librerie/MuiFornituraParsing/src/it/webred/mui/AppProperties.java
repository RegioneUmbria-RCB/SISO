package it.webred.mui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import javax.naming.NamingException;

import net.skillbill.logging.Logger;

public class AppProperties {
	
	public static final String BELFIORE = "belfiore";
	public static final String DRIVER_CLASS = "driverClass";
	public static final String CONN_STRING = "connString";
	public static final String USER_NAME = "userName";
	public static final String PASSWORD = "password";
	public static final String XSL_PATH = "xslPath";
	
	public static HashMap<String, String> appProperties = null;
	
	public static void setProperties(HashMap<String, String> newAppProperties) {
		appProperties = newAppProperties;
	}
	
	public static HashMap<String, String> getProperties() {
		return appProperties;
	}
	
	public static Connection getConnection() throws SQLException, NamingException {
		try {
			Class.forName(appProperties.get(DRIVER_CLASS));
			return DriverManager.getConnection(appProperties.get(CONN_STRING), appProperties.get(USER_NAME),appProperties.get(PASSWORD));
		} catch (ClassNotFoundException cnfe) {
			Logger.log().debug(AppProperties.class.getName(), cnfe.getMessage());
		}
		return null;
	}

}
