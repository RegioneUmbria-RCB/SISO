package it.webred.ct.support.audit;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	//private static List<AmAuditDTO> waitingInsert;
	//private static final int CODA_MAX = 1; //CODA_MAX=1 --> Salva immediatamente, altrimenti attende che si riempa prima di salvare
	
	private static Logger logger = Logger.getLogger("audit.log");
		
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
	public static void auditMethod(String ente, String user, String sessionId,String className, String methodName, 
							Object arguments[], Object returnVal, String ex) throws Throwable {

		logger.debug("AuditDBWriter:auditMethod ["+methodName+"]["+className+"]user["+user+"]ente["+ente+"]sessionId["+sessionId+"]");
		try {
			DumpField df = new DumpField();
			String args = "";
			String result = "";
			String chiave = "";
			String[] val = checkDecode(methodName, className);
			
			String descrizione = val[0];
			String campoChiave = val[1];
			
			//il campo_chiave della tabella di decode può contenere una chiave composta, dove i valori sono separati da |  
			HashMap<String,String> hChiavi = new HashMap<String, String>();
			if(campoChiave != null){
				String[] split = campoChiave.split("\\|");
				for(int i=0; i<split.length; i++){
					hChiavi.put(split[i], "");
				}
			}
			
			if (arguments != null) {
	
				int livello = 1;
				for (int i = 0; i < arguments.length; i++) {
					Object arg = arguments[i];
					df.setParamName("Input" + livello);
					df.sethChiavi(hChiavi);
					args += df.dumpFields(arg, String.valueOf(livello)) + "|||";
					if (arg instanceof CeTBaseObject) {
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
	
			/*Registro il metodo nel database*/
			if(user!=null && sessionId!=null){
				AmAuditDTO dto = new AmAuditDTO();
				dto.setMethodName(methodName);
				dto.setClassName(className);
				dto.setUserId(user);
				dto.setResult(result);
				dto.setEnteId(ente);
				dto.setException(ex);
				dto.setArgs(args);
				dto.setSessionId(sessionId);
				dto.setDescrizione(descrizione);
				dto.setChiave(chiave);
				write(dto);
			}
		} catch (Exception e) {
			logger.error("AuditDBWriter:auditMethod "+e.getMessage(), e);
			e.printStackTrace();
			throw e;
			
		}
	}
	
	private static String[] checkDecode(String methodName, String className) throws Exception {

		/* gestione AM_AUDIT_DECODE:
		 * se esiste una codifica tramite classe-metodo recupero descrizione, fonte, campo chiave
		 * e aggiorno questi valori su eventuali record dello stesso tipo 
		 * altrimenti creo un record in modo che sia visibile il fatto che non esiste una codifica
		 */
		Connection con = null; 
		PreparedStatement st = null;
		PreparedStatement upDecode = null;
		String[] val = new String[2];
		try {

			con = apriConnessione();
			if (con.getTransactionIsolation() == 0) {
				con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
			String sql = "SELECT d.id, d.descrizione, d.campo_chiave, d.fk_am_fonte, f.descrizione AS fonte, d.fk_am_fonte_tipoinfo " +
				       	 "FROM am_audit_decode d, am_fonte f " +
				       	 "WHERE d.method_name = ? AND d.class_name = ? AND f.ID (+)= d.fk_am_fonte";
		
			st = con.prepareStatement(sql);
			st.setString(1, methodName);
			st.setString(2, className);
			BigDecimal idDecode = null;
			String descrizione = null;
			String campoChiave = null;
			ResultSet rs = null;
			try {
				rs = st.executeQuery();
			
				while (rs.next()) {
					idDecode = rs.getBigDecimal("ID");
					descrizione = rs.getString("DESCRIZIONE");
					campoChiave = rs.getString("CAMPO_CHIAVE");
					//idFonte = new BigDecimal(rs.getInt("FK_AM_FONTE"));
					//fonte = rs.getString("FONTE");
					//tipoinfo = rs.getString("FK_AM_FONTE_TIPOINFO");
					val[0] = descrizione;
					val[1] = campoChiave;
				}
			} finally {
				try {
					DbUtils.close(rs);
					DbUtils.close(st);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(idDecode == null){
				String queryIns = "INSERT INTO AM_AUDIT_DECODE (STANDARD, METHOD_NAME, CLASS_NAME) VALUES (?,?,?)";
				upDecode = con.prepareStatement(queryIns);
				int paramIndex = 0;
				upDecode.setString(++paramIndex, "Y");
				upDecode.setString(++paramIndex, methodName);
				upDecode.setString(++paramIndex, className);
				upDecode.executeUpdate();
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
			DbUtils.close(upDecode);
			DbUtils.close(st);
			DbUtils.close(con);
		}

		return val;
	}
	
	
	/*RIMOSSO SYNCHRONIZED verificare eventuali malfunzionamenti*/
	public static void write(AmAuditDTO dto) throws Exception {
		
		Connection con = apriConnessione() ; 
		PreparedStatement prepStatement = null;
		try{
			/*Elaboro e svuoto la coda*/
			if (con.getTransactionIsolation() == 0) {
				con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			}
				
			String queryIns = "INSERT INTO AM_AUDIT "
					+ " (DATA_INS,CLASS_NAME,METHOD_NAME,USER_ID,ENTE_ID,ARGS,RESULT,EXCEPTION,SESSION_ID,DESCRIZIONE, CHIAVE)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
			prepStatement = con.prepareStatement(queryIns);
			
			//taglio le stringhe a 4000 caratteri
			String args = dto.getArgs();
			if(dto.getArgs() != null){
				while(args.length() >= 4000){
					int idx = args.lastIndexOf("|||");
					if(idx>=0) args = args.substring(0, idx);
					else args = args.substring(0, 3999);
				}
				args = args.replaceAll("\'", "\'\'");
			}
			
			String result = dto.getResult();
			if(result != null){
				while(result.length() >= 4000){
					int idx = result.lastIndexOf("|||");
					if(idx>=0) result = result.substring(0, idx);
					else result = result.substring(0,3999);
				}
				result = result.replaceAll("\'", "\'\'");
			}
			if (args==null)
				args="";
			if (result==null)
				result="";

		
			int paramIndex = 0;

			prepStatement.setTimestamp(++paramIndex, new Timestamp(new Date().getTime()));
			prepStatement.setString(++paramIndex, dto.getClassName());
			prepStatement.setString(++paramIndex, dto.getMethodName());
			prepStatement.setString(++paramIndex, dto.getUserId());
			prepStatement.setString(++paramIndex, dto.getEnteId());

			prepStatement.setCharacterStream(++paramIndex, new StringReader(args),args.length());
			prepStatement.setCharacterStream(++paramIndex, new StringReader(result),result.length());
			
			prepStatement.setString(++paramIndex, dto.getException());
			prepStatement.setString(++paramIndex, dto.getSessionId());
			prepStatement.setString(++paramIndex, dto.getDescrizione());
			prepStatement.setString(++paramIndex, dto.getChiave());
			
			prepStatement.executeUpdate();
			
			logger.debug("AuditDBWriter - write completato ["+dto.getMethodName()+"]["+dto.getClassName()+"]["+dto.getUserId()+"]["+dto.getSessionId()+"]["+dto.getEnteId()+"]");
			
		} catch (Exception ex) {
			logger.error(ex.getMessage(),ex);
			ex.printStackTrace();
			throw ex;
		} finally {
			DbUtils.close(prepStatement);
			DbUtils.close(con);
		}
	}
	
	public static void main (String[] args) {
		try {
			
			AmAuditDTO dto = new AmAuditDTO();
			dto.setMethodName("methodName");
			dto.setClassName("className");
			dto.setUserId("monzaM");
			dto.setResult("resultDASDASHHDHADHAIOAIODOAHODHAOH");
			dto.setEnteId("F704");
			dto.setException("e");
			dto.setArgs("resultDASDASHHDHADHAIOAIODOAHODHAOH resultDASDASHHDHADHAIOAIODOAHODHAOH");
			dto.setSessionId("sessionId");
			dto.setDescrizione("descrizione");
			dto.setChiave("chiave");
			dto.setDescrizione("accesso utente");
		
			AuditDBWriter.write(dto);
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
}
