package it.webred.rulengine.dwh.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.log4j.Logger;

import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.Tabella;
import it.webred.rulengine.dwh.table.SitEnteSorgente;

public class NormalTableDao  implements GenericDao
{
	private static final Logger log = Logger.getLogger(NormalTableDao.class.getName());
	Connection conn=null;

	
	Tabella tabella;

	public NormalTableDao(Tabella tab) {
		tabella = tab;
	}
	
	public Tabella getTabella()
	{
		return tabella;
	}
	
	public Tabella findByIdentificativo(Identificativo id)
	{
		Tabella tab =null;
		String tableName = DaoFactory.getTableNameFromDao(this.getClass());
		if (id==null ||id.getValore()==null)
			return null;
		String sql = "select * from " + tableName + " WHERE  id="+id.getValore().intValue();
		try
		{
			ResultSet rs = DwhUtils.executeQuery(conn,sql);
			LinkedHashSet<Tabella> tabs = DwhUtils.resultsetToTable(rs,this.getTabella().getClass());
			if (tabs.isEmpty())
				return null;
			else {
				Iterator it = tabs.iterator();
				while (it.hasNext())
				{
					Tabella t = (Tabella) it.next();
					return t;
				}
			}
			return null;	
		}
		catch (Exception e) {
			log.error("Errore nella ricerca per identificativo:"+sql,e);
			return null;
		}
	}
	
	
	



	


	public void setConnection(Connection conn)
	{
		this.conn= conn;
		
	}
	




}
