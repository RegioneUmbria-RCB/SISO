package it.webred.ct.rulengine.controller.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the R_CONNECTION database table.
 * 
 */
@Entity
@Table(name="R_CONNECTION")
public class RConnection implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Column(name="CONN_STRING")
	private String connString;

	@Column(name="DRIVER_CLASS")
	private String driverClass;

	private String name;

	private String password;

	@Column(name="SYSTEM_CONNECTION")
	private BigDecimal systemConnection;

	@Column(name="USER_NAME")
	private String userName;

    public RConnection() {
    }

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getConnString() {
		return this.connString;
	}

	public void setConnString(String connString) {
		this.connString = connString;
	}

	public String getDriverClass() {
		return this.driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public BigDecimal getSystemConnection() {
		return this.systemConnection;
	}

	public void setSystemConnection(BigDecimal systemConnection) {
		this.systemConnection = systemConnection;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}