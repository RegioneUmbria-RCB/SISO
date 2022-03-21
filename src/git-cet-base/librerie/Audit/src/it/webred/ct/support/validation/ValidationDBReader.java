package it.webred.ct.support.validation;

import it.webred.ct.support.audit.AmAuditDTO;
import it.webred.ct.support.audit.AuditDBWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class ValidationDBReader {

	protected static Logger logger = Logger.getLogger("audit.log");
	private static final long minutiScadenza = 720;
		
	public static boolean validateMethod(String className, String methodName, Object arguments[], String username, String sessionId, String ente , boolean isTokenSessione) throws Throwable {
		//logger.debug("ValidationDBReader:validateMethod ["+methodName+"]["+className+"]["+username+"]["+sessionId+"]["+ente+"]TokenSessione["+isTokenSessione+"] INIT");
		boolean accessGranted = false;

		if (arguments != null) {
			
			// se non c'è nel metodo il parametro CeTToken allora lo faccio passare in quanto il metodo non è compatibile con la gestione degli interceptors 
			if (!isTokenSessione)
				accessGranted=  true;
			else{
				// deve essere per forza valorizzato ente altrimenti non si sa su che banca dati andare a cercare!!!
				if(sessionId != null && ente != null){
					accessGranted = checkLogin(sessionId, ente, username);
					if(!accessGranted)
						logger.info("ValidationDBReader:validateMethod - CHIAMATA BLOCCATA PER IL METODO ["+methodName+"]["+className+"]");
				}else{
					logger.info("ValidationDBReader:validateMethod - SESSION ID["+sessionId+"] o COD.ENTE["+ente+"] NON DEFINITO PER CHIAMATA AL METODO ["+methodName+"]["+className+"]");
					if (sessionId==null)
						throw new Exception("ValidationDBReader:validateMethod - sessionId non valorizzata - impossibile inviare la chiamata al servizio");

					if (ente==null)
						throw new Exception("ValidationDBReader:validateMethod - ente non valorizzata - impossibile inviare la chiamata al servizio");
				}
			}
		}
		
		logger.debug("ValidationDBReader:validateMethod ["+methodName+"]["+className+"]["+username+"]["+ente+"]["+sessionId+"]TokenSessione["+isTokenSessione+"] --> AUTORIZZATO["+accessGranted+"]");
		
		return accessGranted;
	}
	
	public boolean checkLogin(CeTToken token) throws Exception {
		return checkLogin(token.getSessionId(), token.getEnte(), null);
	}

		
	private static boolean checkLogin(String sessionId, String ente, String username) throws Exception {
		
		logger.debug("ValidationDBReader:checkLogin ["+username+"]["+ente+"]["+sessionId+"]");
		
		Connection con = null; 
		
		String sqlData = "select max(data_ins) as data_max from am_audit where session_id = ?";
		String sqlUser = "select user_id from am_audit where session_id = ? and user_id is not null group by user_id";
		
		PreparedStatement stData = null;
		PreparedStatement stUser = null;
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
			
			stData = con.prepareStatement(sqlData);
			stData.setString(1, sessionId);
			ResultSet rs = stData.executeQuery();
			if (rs.next()) {
				dataMax = rs.getTimestamp("DATA_MAX");
				if(dataMax != null){
			        long minuteDiff = ((now.getTime()/60000) - (dataMax.getTime()/60000));
					if(minuteDiff <= minutiScadenza)
						ret = true;
					else logger.info("ValidationDBReader:checkLogin - ["+sessionId+"] MINUTI TRASCORSI DA ULTIMA CHIAMATA SUPERIORI A " + minutiScadenza);
				}else {
					logger.info("ValidationDBReader:checkLogin - ["+sessionId+"] NON TROVATO");
					// per il primo accesso inserisco il record in AM_AUDIT
					if (username!=null) {
						
						AmAuditDTO dto = new AmAuditDTO();
						dto.setMethodName("checkLogin");
						dto.setClassName(ValidationDBReader.class.getName());
						dto.setUserId(username);
						dto.setEnteId(ente);
						dto.setSessionId(sessionId);
						dto.setDescrizione("accesso utente");
					
						AuditDBWriter.write(dto);
						logger.info("ValidationDBReader:checkLogin - ["+username+"]["+ente+"]["+sessionId+"] inserito in audit");
						ret = true;
					}
				}
			} 
			
			stUser = con.prepareStatement(sqlUser);
			stUser.setString(1, sessionId);
			rs = stUser.executeQuery();
			while (rs.next()) {
				usernameDb = rs.getString("USER_ID");
			}
			
			//Verifico se l'utente recuperato tramite sessionId è abilitato ad accedere all'ente (gruppi, permessi individuali)
			ret2 = checkEnte(con, ente, usernameDb);
			
		} catch (Exception ex) {
			throw ex;
		} finally {
			DbUtils.close(stData);
			DbUtils.close(stUser);
			DbUtils.close(con);
		}
		return ret && ret2;
	}
	
	private static boolean checkEnte(Connection con, String ente, String username) throws Exception{
		
		boolean ret = false;
		PreparedStatement st = null;
		if(ente!=null && username!=null){
			try {
				
				String sql = "select ente FROM ( " +
							"select distinct ente, descrizione FROM ( " +
				            "select FK_AM_COMUNE ente, c.DESCRIZIONE, FK_AM_USER FROM AM_GROUP G, AM_USER U, AM_USER_GROUP UG, AM_COMUNE C " + 
				            "WHERE u.name=UG.FK_AM_USER AND G.NAME=UG.FK_AM_GROUP and g.FK_AM_COMUNE = c.BELFIORE " +
				            "union " +
				            "select ua.FK_AM_COMUNE,c.DESCRIZIONE, ua.FK_AM_USER " +
				            "from am_user_air ua, AM_COMUNE c where ua.FK_AM_COMUNE = c.BELFIORE " + 
				            ")  " +
				            "WHERE UPPER(FK_AM_USER)= ? AND ENTE = ? " +
				            "ORDER BY descrizione asc)";
				st = con.prepareStatement(sql);
				st.setString(1, username.toUpperCase());
				st.setString(2, ente);
				ResultSet rs = st.executeQuery();
				if (rs.next()) {
					ret = true;
				}
				
			} catch (Exception ex) {
				throw ex;
			}finally{
				DbUtils.close(st);
			}
			
			if(!ret)
				logger.info("ValidationDBReader: checkEnte - L'ENTE ["+ ente +"] NON E' ACCESSIBILE ALL'UTENTE ["+ username +"]");
		}
		return ret;
		
	}
	
	public static Connection apriConnessione() throws Exception {
		Context initContext = new InitialContext();
		DataSource ds = (DataSource) initContext.lookup("java:/AMProfiler");
		Connection conn = ds.getConnection();
		return conn;
	}
}
