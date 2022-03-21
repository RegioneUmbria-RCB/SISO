package it.webred.rulengine.db;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import it.webred.rulengine.ServiceLocator;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.apache.log4j.Logger;


public abstract class REngineBaseDAO {
	
	private static final Logger logger = Logger.getLogger(REngineBaseDAO.class.getName());
	
	protected EntityManager emRengine;
	protected Properties sql;
	
	public REngineBaseDAO() {
		super();
		
		try {
			try{
				emRengine = ServiceLocator.getInstance().getJpa("java:jboss/RE_JPAFactory");
			}catch(Exception e) {
				logger.error("Eccezione Creazione EntityManager: "+e.getMessage(),e);
				System.out.println("Eccezione Creazione EntityManager: "+e.getMessage());
			}
			sql = new Properties();
			java.io.InputStream is = REngineBaseDAO.class.getResourceAsStream("/resqlcommands.properties");
			sql.load(is);
			
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage(),e);
		}
		
		
	}
	
	
	
	protected Long getNextSequenceId(Connection conn,String seq_name) throws Exception {
		Long nextId = null;
		String query = "select "+seq_name+".nextval as nextID from dual";
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(query);
		if(rs.next()) {
			nextId = rs.getLong("nextID");
		}
		
		logger.debug("Next ID: "+nextId);
		return nextId;
	}
	
	
}
