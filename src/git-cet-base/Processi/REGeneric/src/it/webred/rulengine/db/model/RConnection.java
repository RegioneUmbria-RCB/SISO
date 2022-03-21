package it.webred.rulengine.db.model;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.*;


/**
 * The persistent class for the R_CONNECTION database table.
 * 
 */
@Entity
@Table(name="R_CONNECTION")
public class RConnection  implements java.io.Serializable {


	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;
	
	@Id
    private Integer id;
	
	@Column(name="NAME")
    private String name;
	
	@Column(name="DRIVER_CLASS")
    private String driverClass;
	
	@Column(name="CONN_STRING")
    private String connString;
	
	@Column(name="USER_NAME")
    private String userName;
	
	@Column(name="PASSWORD")
    private String password;
    
    @Column(name="SYSTEM_CONNECTION")
    private Integer systemConnection;
    


    /** default constructor */
    public RConnection() {
    }

	/** minimal constructor */
    public RConnection(String name, String driverClass, String connString, Integer systemConnection) {
        this.name = name;
        this.driverClass = driverClass;
        this.connString = connString;
        this.systemConnection = systemConnection;
    }
    

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getDriverClass() {
        return this.driverClass;
    }
    
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnString() {
        return this.connString;
    }
    
    public void setConnString(String connString) {
        this.connString = connString;
    }

    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getSystemConnection() {
        return this.systemConnection;
    }
    
    public void setSystemConnection(Integer systemConnection) {
        this.systemConnection = systemConnection;
    }

}
