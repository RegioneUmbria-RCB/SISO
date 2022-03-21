package it.webred.rulengine.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import it.webred.rulengine.Context;
import it.webred.rulengine.db.REngineBaseDAO;
import it.webred.rulengine.db.dao.IRConnectionDAO;
import it.webred.rulengine.db.model.RConnection;
import it.webred.rulengine.exception.RulEngineException;


public class RConnectionDAOImpl extends REngineBaseDAO implements IRConnectionDAO {

	private static final Logger logger = Logger.getLogger(RConnectionDAOImpl.class.getName());
	
	public static Properties props = null;
	
	public List<RConnection> getListaRConnection(Connection conn) throws Exception {
		List<RConnection> rconns = new ArrayList<RConnection>();
		
		try {
			if(emRengine!=null){
				logger.debug("Recupero elenco connessioni");
				Query q = emRengine.createNamedQuery("RConnection.getListaRConnection");
				rconns = q.getResultList();
				logger.debug("Result size ["+rconns.size()+"]");
			}else{
				//Utilizza quando lanciato da console
				String sql = "SELECT rc.* FROM R_Connection rc ORDER BY rc.id ASC";
				PreparedStatement pst = conn.prepareStatement(sql);
				ResultSet rs = pst.executeQuery(sql);
				while(rs.next()){
					RConnection r = new RConnection();
					r.setId(rs.getInt("ID"));
					r.setConnString(rs.getString("CONN_STRING"));
					r.setDriverClass(rs.getString("DRIVER_CLASS"));
					r.setName(rs.getString("NAME"));
					r.setPassword(rs.getString("PASSWORD"));
					r.setSystemConnection(rs.getInt("SYSTEM_CONNECTION"));
					r.setUserName(rs.getString("USER_NAME"));
					rconns.add(r);
				}
			}
		}catch(Exception e) {
			logger.error("Eccezione: "+e.getMessage());
			throw new RulEngineException("Eccezione: "+e.getMessage(),e);
		}
		
		return rconns;
	}


}
