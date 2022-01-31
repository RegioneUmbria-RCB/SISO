package it.webred.verificaCoordinate.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public abstract class VerificaCoordinateBaseLogic {
	
	private static final Logger log = Logger.getLogger(VerificaCoordinateBaseLogic.class.getName());
	
	protected String codEnte;
	protected Connection extConn;
	protected String dataSource = DEF_DATA_SOURCE; //default
	
	public static final String DEF_COD_ENTE = "F205";
	public static final String DEF_DATA_SOURCE = "jdbc/DIOGENE";
	
	public VerificaCoordinateBaseLogic(String codEnte, Connection extConn) {
		this.codEnte = codEnte;
		this.extConn = extConn;
	}
	
	public VerificaCoordinateBaseLogic(String dataSource, String codEnte) {
		this.dataSource = dataSource;
		this.codEnte = codEnte;
	}
	
	protected Connection getConnection() throws NamingException, SQLException {
		if (extConn != null) {
			return extConn;
		}
		if (codEnte.equalsIgnoreCase("DEFAULT")) { //solo per test
			try {
				return getConnection("jdbc:oracle:thin:@praga:1521:praga", "DIOGENE", "DIOGENE");
			} catch (Exception e) {
				log.error("Errore in fase di connessione al DB", e);
			}
		}
		Context cont = new InitialContext();
		Context datasourceContext = (Context) cont.lookup("java:comp/env");
		DataSource theDataSource = null;
		if (this.dataSource == null || this.dataSource.equals("")) {
			theDataSource = (DataSource) datasourceContext.lookup(DEF_DATA_SOURCE + "_" + this.codEnte);
		} else
			theDataSource = (DataSource) datasourceContext.lookup(this.dataSource + "_" + this.codEnte);

		return theDataSource.getConnection();
	}
	
	/*
	 * si prevede di usare questo metodo solo in fase di test...
	 */
	protected Connection getConnection(String url, String username, String password) throws ClassNotFoundException, SQLException {
		String driverName = "oracle.jdbc.driver.OracleDriver";
	    Class.forName(driverName);
	    return DriverManager.getConnection(url, username, password);
	}
	
	public void closeConnection(Connection conn, boolean all) throws SQLException {
		if (!all && extConn != null) {
			return;
		}
		if (conn != null)
			if (!conn.isClosed())
				conn.close();
	}

}
