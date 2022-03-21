package it.webred.rulengine.db.dao;

import java.sql.Connection;

import it.webred.rulengine.db.model.RAbNormal;

public interface IRAbNormalDAO {
	
	/**
	 * 
	 * @param processId
	 * @return
	 * @throws Exception
	 */
	public Long getCountByFilteredProcess(String processId) throws Exception;
	
	/**
	 * 
	 * @param r
	 * @throws Exception
	 */
	public void saveRAbNormal(Connection reconn,RAbNormal r) throws Exception;
	
	
	public Long getNextID(Connection conn) throws Exception;
}
