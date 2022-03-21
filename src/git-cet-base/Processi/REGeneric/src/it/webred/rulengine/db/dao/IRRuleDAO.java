package it.webred.rulengine.db.dao;

import it.webred.rulengine.db.model.RRule;

import java.util.List;

public interface IRRuleDAO {
	
	/**
	 * Recupero lista di tutte le regole censite
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RRule> getListaRRule() throws Exception;
	
	/**
	 * Recupero lista di tutte le regole censite per un dato
	 * comando
	 * 
	 * @param commandId
	 * @return
	 * @throws Exception
	 */
	public List<RRule> getListaRRule(Long commandId) throws Exception;
}
