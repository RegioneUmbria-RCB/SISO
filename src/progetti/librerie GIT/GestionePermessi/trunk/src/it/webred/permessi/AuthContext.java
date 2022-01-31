package it.webred.permessi;

import java.io.Serializable;
import java.security.Principal;
import java.sql.Connection;

public class AuthContext implements Serializable{

	private static final long serialVersionUID = 1L;
	private Principal user = null;
	private Connection connection = null;
	private String application = null;
	private String item = null;

	public AuthContext(AuthContext src)
	{
		this.user = src.user;
		this.connection = src.connection;
		this.application = src.application;
		this.item = src.item;
	}
	
	public AuthContext(Principal user) {
		super();
		this.user = user;
	}
	public AuthContext(Principal user, Connection conn) {
		super();
		this.user = user;
		this.connection = conn;
	}

	public AuthContext(Principal user, Connection connection, String application, String item) {
		super();
		this.user = user;
		this.connection = connection;
		this.application = application;
		this.item = item;
	}

	public Principal getUser() {
		return user;
	}
	public void setUser(Principal user) {
		this.user = user;
	}
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}

}
