package it.webred.ct.support.audit;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;

public class AuditDBWriter {

	protected Logger logger = Logger.getLogger("CTservice_log");
	private String descrizione = null;
	private String campoChiave = null;
	private BigDecimal idFonte = null;
	private String fonte = null;
	private String tipoinfo = null;
		
	/**
	 * ESEMPIO D'USO	
			AuditDBWriter dbWriter = new AuditDBWriter();
			CeTUser cetUser = getEnvUtente().getUtente();
			String className = Thread.currentThread().getStackTrace()[1].getClassName();
			String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
			Object[] arguments = new Object[2];
			arguments[0] = listaPar;
			arguments[1] = finder;
			Object returnVal = ht;
			try {
				dbWriter.auditMethod(cetUser.getCurrentEnte(), cetUser.getUsername(), cetUser.getSessionId(), className, methodName, arguments, returnVal, null);
			} catch (Throwable e) {				
				e.printStackTrace();
			}
	 */
	public void auditMethod(String ente, String user, String sessionId,
			String className, String methodName, Object arguments[],
			Object returnVal, String ex) throws Throwable {

		try {
		DumpField df = new DumpField();
		String args = "";
		String result = "";
		String chiave = "";
		checkDecode(methodName, className);
		
		//il campo_chiave della tabella di decode può contenere una chiave composta, dove i valori sono separati da |  
		HashMap<String,String> hChiavi = new HashMap<String, String>();
		if(campoChiave != null){
			String[] split = campoChiave.split("\\|");
			for(int i=0; i<split.length; i++){
				hChiavi.put(split[i], "");
			}
		}
		
		if (arguments != null) {

			logger.debug("Audit interceptor. method --> " + methodName);
			
			int livello = 1;
			for (int i = 0; i < arguments.length; i++) {
				Object arg = arguments[i];
				df.setParamName("Input" + livello);
				df.sethChiavi(hChiavi);
				args += df.dumpFields(arg, String.valueOf(livello)) + "|||";
				if (arg instanceof CeTBaseObject) {
					logger.debug("Audit interceptor. argument extends CeTBaseObject");
					CeTBaseObject cetObj = (CeTBaseObject) arg;
					user = cetObj.getUserId();
					ente = cetObj.getEnteId();
					sessionId = cetObj.getSessionId();
					break;
				}else{
					if(df.getEnteId() != null)
						ente = df.getEnteId();
					if(df.getUserId() != null)
						user = df.getUserId();
					if(df.getSessionId() != null)
						sessionId = df.getSessionId();
				}
				livello++;
			}
			//ricostruisco i valori dei campi chiave
			Iterator it = hChiavi.keySet().iterator();
			while(it.hasNext()) {
				String key = (String) it.next();
				chiave += key + ">>>" + hChiavi.get(key) + "|||";
			}
		}

		if (returnVal != null) {
				df.setParamName("Output");
				result += df.dumpFields(returnVal, "1");
				if(result.length() > 15000){
					df.setMaxProfondita(2);
					result = df.dumpFields(returnVal, "1");
				}
				if(result.length() > 15000){
					df.setMaxProfondita(1);
					result = df.dumpFields(returnVal, "1");
				}
		} else
			result = "null";

		write(methodName, className, user, result, ente, ex, args,
				sessionId, descrizione, chiave, idFonte, fonte, tipoinfo);
		} catch (Exception e) {
			logger.error("auditMethod", e);
			e.printStackTrace();
			throw e;
			
		}
	}
	
	public void checkDecode(String methodName, String className) throws Exception {

		/*gestione audit decode:
		 * se esiste una codifica tramite classe-metodo recupero descrizione, fonte, campo chiave
		 * e aggiorno questi valori su eventuali record dello stesso tipo 
		 * altrimenti creo un record in modo che sia visibile il fatto che non esiste una codifica
		 */
		
		Connection con = null; 
		Statement st = null;
		

		try {

			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			
			st = con.createStatement();
			BigDecimal idDecode = null;
			String sql = "SELECT d.id, d.descrizione, d.campo_chiave, d.fk_am_fonte, f.descrizione AS fonte, " +
					       "d.fk_am_fonte_tipoinfo " +
					       "FROM am_audit_decode d, am_fonte f " +
					       "WHERE d.method_name = '"+methodName+"' " +
					       "AND d.class_name = '"+className+"' AND f.ID (+)= d.fk_am_fonte";
			
			ResultSet rs = null;
			try {
				rs = st.executeQuery(sql);
				while (rs.next()) {
					idDecode = rs.getBigDecimal("ID");
					descrizione = rs.getString("DESCRIZIONE");
					campoChiave = rs.getString("CAMPO_CHIAVE");
					idFonte = new BigDecimal(rs.getInt("FK_AM_FONTE"));
					fonte = rs.getString("FONTE");
					tipoinfo = rs.getString("FK_AM_FONTE_TIPOINFO");
				}
			} finally {
				try {
					DbUtils.close(rs);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(idDecode == null){

				String queryIns = "INSERT INTO AM_AUDIT_DECODE "
						+ "(STANDARD, METHOD_NAME, CLASS_NAME)"
						+ " VALUES (?,?,?)";
				PreparedStatement prepStatement = con.prepareStatement(queryIns);
				int paramIndex = 0;
				prepStatement.setString(++paramIndex, "Y");
				prepStatement.setString(++paramIndex, methodName);
				prepStatement.setString(++paramIndex, className);

				prepStatement.executeUpdate();
				
				try {
					DbUtils.close(prepStatement);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			//dovrei ritrovare su args il campochiave e valorizzarlo su chiave
			/*else{
				//aggiorno eventuali record senza decodifica
				String queryUpd = "UPDATE AM_AUDIT SET DESCRIZIONE = ?, "
						+ "CHIAVE = ?, FK_AM_FONTE = ?, DESCRIZIONE_FONTE = ?, "
						+ "FK_AM_FONTE_TIPOINFO = ? WHERE DESCRIZIONE IS NULL "
						+ "AND METHOD_NAME = ? AND CLASS_NAME = ?";
				PreparedStatement prepStatement = con.prepareStatement(queryUpd);
				int paramIndex = 0;
				prepStatement.setString(++paramIndex, descrizione);
				prepStatement.setString(++paramIndex, campoChiave);
				prepStatement.setInt(++paramIndex, idFonte);
				prepStatement.setString(++paramIndex, fonte);
				prepStatement.setString(++paramIndex, tipoinfo);
				prepStatement.setString(++paramIndex, methodName);
				prepStatement.setString(++paramIndex, className);

				prepStatement.executeUpdate();
			}
			*/
			
		} catch (Exception ex) {
			logger.error("checkDecode in errore",ex);
			ex.printStackTrace();
			throw ex;
		} finally {
			chiudiConnessione(con, st);
		}

	}
	
	
	public synchronized void write(String methodName, String className, String userId,
			String result, String enteId, String e, String args,
			String sessionId, String descrizione, String chiave,
			BigDecimal idFonte, String fonte, String tipoinfo) throws Exception {

		//taglio le stringhe a 4000 caratteri
		if(args != null){
			while(args.length() >= 4000){
				int idx = args.lastIndexOf("|||");
				args = args.substring(0, idx);
			}
			args = args.replaceAll("\'", "\'\'");
		}
		if(result != null){
			while(result.length() >= 4000){
				int idx = result.lastIndexOf("|||");
				result = result.substring(0, idx);
			}
			result = result.replaceAll("\'", "\'\'");
		}
		if (args==null)
			args="";
		if (result==null)
			result="";

		Connection con = apriConnessione() ; 

		PreparedStatement prepStatement = null;



		try {
			if (con.getTransactionIsolation() == 0) {
				con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}

			
			String queryIns = "INSERT INTO AM_AUDIT "
					+ " (DATA_INS,CLASS_NAME,METHOD_NAME,USER_ID,ENTE_ID,ARGS,RESULT,EXCEPTION"
					+ " ,SESSION_ID,DESCRIZIONE, CHIAVE)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			prepStatement = con.prepareStatement(queryIns);
			int paramIndex = 0;

			prepStatement.setTimestamp(++paramIndex,
					new Timestamp(new Date().getTime()));
			prepStatement.setString(++paramIndex, className);
			prepStatement.setString(++paramIndex, methodName);
			prepStatement.setString(++paramIndex, userId);
			prepStatement.setString(++paramIndex, enteId);

			
			prepStatement.setCharacterStream(++paramIndex, new StringReader(args),args.length());
			prepStatement.setCharacterStream(++paramIndex, new StringReader(result),result.length());
			
			
			
			prepStatement.setString(++paramIndex, e);
			prepStatement.setString(++paramIndex, sessionId);
			prepStatement.setString(++paramIndex, descrizione);
			prepStatement.setString(++paramIndex, chiave);

			
			prepStatement.executeUpdate();
	
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			ex.printStackTrace();
			throw ex;
		} finally {
			chiudiConnessione(con, prepStatement);
		}

	}
	
	public static void main (String[] args) {
		try {
			new AuditDBWriter().write("methodName", "className", "monzaM", "resultDASDASHHDHADHAIOAIODOAHODHAOH", "F704", "e", "resultDASDASHHDHADHAIOAIODOAHODHAOH  resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH ", "sessionId", "descrizione", "chiave", new BigDecimal(1), "f", "tipoinfo");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Connection apriConnessione() throws Exception {
		Context initContext = new InitialContext();
		DataSource ds = (DataSource) initContext.lookup("java:/AMProfiler");
		Connection conn = ds.getConnection();
		return conn;
	}

	public static void chiudiConnessione(Connection con, Statement st) {
		try {
			DbUtils.close(st);
			DbUtils.close(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
