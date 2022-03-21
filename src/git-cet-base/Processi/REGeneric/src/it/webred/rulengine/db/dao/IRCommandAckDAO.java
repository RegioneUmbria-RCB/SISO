package it.webred.rulengine.db.dao;

import java.sql.Connection;

import it.webred.rulengine.db.model.RCommandAck;

public interface IRCommandAckDAO {

	/**
	 * 
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	public Long getCountByFilteredProcess(String processId) throws Exception;
	
	
	/**
	 * 
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	public Long getCountRjcAckByFilteredProcess(String processId) throws Exception;
	
	
	/**
	 * Salvataggio oggetto
	 * @param r
	 * @throws Exception
	 */
	public void saveRCommandAck(Connection reconn,RCommandAck r) throws Exception;
	
	
	
	public Long getNextID(Connection conn) throws Exception;
}
