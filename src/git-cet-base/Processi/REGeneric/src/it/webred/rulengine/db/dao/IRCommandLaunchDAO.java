package it.webred.rulengine.db.dao;

import java.sql.Connection;

import it.webred.rulengine.db.model.RCommandLaunch;

public interface IRCommandLaunchDAO {
	
	/**
	 * Recupero RCommandLaunch da un process ID
	 * 
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	public RCommandLaunch getRCommandLaunchByProcessID(String processId) throws Exception;
	
	
	
	/**
	 * Salvataggio oggetto
	 * 
	 * @param r
	 * @throws Exception
	 */
	public void saveRCommandLaunch(Connection reconn,RCommandLaunch r) throws Exception;
	
	
	/**
	 * Aggiorna oggetto
	 * 
	 * @param r
	 * @throws Exception
	 */
	public void updateRCommandLaunch(Connection reconn,RCommandLaunch r) throws Exception;


	public Long getNextID(Connection conn) throws Exception;
}
