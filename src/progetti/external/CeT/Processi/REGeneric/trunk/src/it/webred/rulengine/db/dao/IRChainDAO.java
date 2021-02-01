package it.webred.rulengine.db.dao;

import it.webred.rulengine.db.model.RChain;

import java.sql.Connection;
import java.util.List;

public interface IRChainDAO {
	
	/**
	 * Recupero lista rchain di un rcommand
	 * 
	 * @param commandId
	 * @return
	 * @throws Exception
	 */
	public List<RChain> getCommandRChains(Long commandId) throws Exception;
	
	
	/**
	 * Recupero oggetto
	 * 
	 * @param cahinId
	 * @return
	 * @throws Exception
	 */
	public RChain getRChain(Long chainId) throws Exception;
	
	
	/**
	 * Aggiorna oggetto 
	 * 
	 * @param r
	 * @throws Exception
	 */
	public void updateRChain(Connection reconn,RChain r) throws Exception;
}
