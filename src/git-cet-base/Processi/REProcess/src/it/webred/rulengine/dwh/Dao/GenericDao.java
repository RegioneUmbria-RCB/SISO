package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.Tabella;
import it.webred.rulengine.dwh.table.TabellaDwh;

import java.sql.Connection;
import java.util.LinkedHashSet;

/**
 * Dao generico per le operazione di ricerca su tabelle
 * @author marcoric
 * @version $Revision: 1.2 $ $Date: 2007/09/26 12:41:14 $
 */
public interface GenericDao 
{

	public Tabella getTabella();
	
	public void setConnection(Connection conn);

}
