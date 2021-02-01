package it.webred.ct.support.validation;

import it.webred.ct.support.audit.AuditDBWriter;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ValidationDBReader {

	protected Logger logger = Logger.getLogger("CTservice_log");
	private long minutiScadenza = 720;
		
	public boolean validateMethod(String className, String methodName, Object arguments[], String username, String sessionId, String ente , boolean isTokenSessione) throws Throwable {

		boolean accessGranted = false;

		if (arguments != null) {

			logger.debug("Validation interceptor. method --> " + methodName);


			
			if(sessionId != null && ente != null){
				accessGranted = checkLogin(sessionId, ente, username);
				if(!accessGranted)
					logger.info("CHIAMATA BLOCCATA PER IL METODO--> " + methodName + " CLASSE --> " + className);
			}else{
				
				logger.info("SESSION ID["+sessionId+"] o COD.ENTE["+ente+"] NON DEFINITO PER CHIAMATA AL METODO --> " + methodName + " CLASSE --> " + className);
			}
			
			// se non c'è nel metodo il parametro CeTToken allora lo faccio passare in quanto
			// il metodo non è compatibile con la gestione degli interceptors 
			if (!isTokenSessione)
				accessGranted=  true;
			// deve essere per forza valorizzato ente altrimenti non si sa su che banca dati andare a cercare!!!
			if (isTokenSessione && ente==null)
				accessGranted =  false;
			
		}
		
		if (sessionId==null && isTokenSessione)
			throw new Exception("sessionId non valorizzata - impossibile inviare la chiamata al servizio");

		if (ente==null && isTokenSessione)
			throw new Exception("ente non valorizzata - impossibile inviare la chiamata al servizio");
		
		

		
		return accessGranted;
	}
	
	public boolean checkLogin(String sessionId, String ente, String username) throws Exception {
		
		Connection con = null; 
		Statement st = null;
		boolean ret = false;
		boolean ret2 = false;
		String usernameDb = null;
		Timestamp dataMax = null;
		Timestamp now = new Timestamp(System.currentTimeMillis());

		try {

			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			
			st = con.createStatement();
			String sql = "select max(data_ins) as data_max from am_audit" +
					" where session_id = '"+ sessionId +"'";
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				dataMax = rs.getTimestamp("DATA_MAX");
				if(dataMax != null){
			        long minuteDiff = ((now.getTime()/60000) - (dataMax.getTime()/60000));
					if(minuteDiff <= minutiScadenza)
						ret = true;
					else logger.info("MINUTI TRASCORSI DA ULTIMA CHIAMATA SUPERIORI A " + minutiScadenza);
				}else {
					logger.info("SESSION ID NON TROVATO");
					// per il primo accesso inserico il record in AM_AUDIT
					if (username!=null) {
						AuditDBWriter awrite = new AuditDBWriter();
						awrite.write("checkLogin", this.getClass().getName(), username, null, ente, null, null, sessionId, "accesso utente", null, null, null, null);
						logger.info("username : " + username + "inseirito in audit");
						ret = true;
					}
				}
			} 
			
			sql = "select user_id from am_audit" +
					" where session_id = '"+ sessionId +"' and user_id is not null group by user_id";
			rs = st.executeQuery(sql);
			while (rs.next()) {
				usernameDb = rs.getString("USER_ID");
			}
			
			if(ente != null && usernameDb != null)
				ret2 = checkEnte(con, ente, usernameDb);
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			chiudiConnessione(con, st);
		}

		return ret && ret2;
	}
	
	public boolean checkEnte(Connection con, String ente, String username) throws Exception{
		
		boolean ret = false;
		Statement st = null;
		List<String> listaEnti = new ArrayList<String>();
		try {
			
			st = con.createStatement();
			String sql = "select ente FROM ( " +
			"select distinct ente, descrizione FROM ( " +
			            "select FK_AM_COMUNE ente, c.DESCRIZIONE, FK_AM_USER FROM AM_GROUP G, AM_USER U, AM_USER_GROUP UG, AM_COMUNE C " + 
			            "WHERE u.name=UG.FK_AM_USER AND G.NAME=UG.FK_AM_GROUP and g.FK_AM_COMUNE = c.BELFIORE " +
			            "union " +
			            "select ua.FK_AM_COMUNE,c.DESCRIZIONE, ua.FK_AM_USER " +
			            "from am_user_air ua, AM_COMUNE c where ua.FK_AM_COMUNE = c.BELFIORE " + 
			            ")  " +
			            "WHERE UPPER(FK_AM_USER)= UPPER('"+username+"') " +
			            "ORDER BY descrizione asc)";
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				listaEnti.add(rs.getString("ENTE"));
			}
			
		} catch (Exception ex) {
			throw ex;
		}finally{
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
				}
			}
		}
		for(String e: listaEnti){
			if(e.equals(ente))
				ret = true;
		}
		
		if(!ret)
			logger.info("L'ENTE "+ ente +" NON E' ACCESSIBILE ALL'UTENTE " + username);
		return ret;
		
	}
	
	public static Connection apriConnessione() throws Exception {
		Context initContext = new InitialContext();
		DataSource ds = (DataSource) initContext.lookup("java:/AMProfiler");
		Connection conn = ds.getConnection();
		return conn;
	}

	public static void chiudiConnessione(Connection con, Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}
	}
}
