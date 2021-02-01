package it.webred.rulengine.db.dao;

import it.webred.rulengine.db.model.RConnection;

import java.sql.Connection;
import java.util.List;

public interface IRConnectionDAO {
	
	/**
	 * Recupero lista connessioni esistenti
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<RConnection> getListaRConnection(Connection conn) throws Exception;
}
