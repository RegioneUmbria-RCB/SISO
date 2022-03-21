package it.webred.rulengine.db.dao;

import it.webred.rulengine.db.model.RCommand;

import java.sql.Connection;
import java.util.List;

public interface IRCommandDAO {
		
	/**
	 * Recupero lista comandi
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RCommand> getListaRCommand() throws Exception;
	
	/**
	 * Recupero oggetto
	 * 
	 * @param commandId
	 * @return
	 * @throws Exception
	 */
	public RCommand getRCommandByCommandId(Long commandId) throws Exception;
	
	/**
	 * Recupero oggetto
	 * 
	 * @param commandId
	 * @return
	 * @throws Exception
	 */
	public RCommand getRCommand(Long commandId) throws Exception;
	
	
	/**
	 * Recupero oggetto
	 * 
	 * @param codCommand
	 * @return
	 * @throws Exception
	 */
	public RCommand getRCommandByCodCommand(String codCommand) throws Exception;
	
	/**
	 * Recupero oggetto
	 * 
	 * @param classname
	 * @return
	 * @throws Exception
	 */
	public RCommand getRCommandByRRuleClassname(String classname) throws Exception;
	
	
	/**
	 * Recupero oggetto
	 * 
	 * @param commandId
	 * @return
	 * @throws Exception
	 */
	public RCommand getRCommand2(Long commandId) throws Exception;
	
	/**
	 * Recupero oggetto
	 * 
	 * @param codCommand
	 * @return
	 * @throws Exception
	 */
	public RCommand getRCommandByCodCommand2(String codCommand) throws Exception;
	
	/**
	 * Salvataggio oggetto
	 * 
	 * @param r
	 * @throws Exception
	 */
	public void saveRCommand(Connection reconn,RCommand r) throws Exception;
	
	/**
	 * Aggiornamento oggetto
	 * 
	 * @param r
	 * @throws Exception
	 */
	public void updateRCommand(Connection reconn,RCommand r) throws Exception;
}
