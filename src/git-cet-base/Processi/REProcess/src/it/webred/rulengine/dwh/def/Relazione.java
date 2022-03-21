package it.webred.rulengine.dwh.def;

import java.sql.Connection;
import java.util.LinkedHashSet;

import org.apache.log4j.Logger;


import it.webred.rulengine.brick.loadDwh.base.LoadSitStato;
import it.webred.rulengine.dwh.Dao.DaoException;
import it.webred.rulengine.dwh.Dao.DaoFactory;
import it.webred.rulengine.dwh.Dao.GenericDao;
import it.webred.rulengine.dwh.Dao.NormalTableDao;
import it.webred.utils.CollectionsUtils;

public class Relazione 
{
	private static final Logger log = Logger.getLogger(Relazione.class.getName());

	
	private Class tabella;
	private Campo relazione;
	private LinkedHashSet<Tabella> risultato = null;
	
	private Connection conn;
	
	public LinkedHashSet<Tabella> getRisultato(Connection conn) throws DaoException
	{
		if (relazione==null)
			return new LinkedHashSet<Tabella>();
		
		if (risultato==null) {
			GenericDao dao = null;
			try
			{
				dao = DaoFactory.createDao(conn,(Tabella)tabella.newInstance(), null);
			}
			catch (Exception e)
			{
				String msg = "Errore su recupero relazione ";
				log.error(msg,e);
				throw new DaoException(msg);
			}
	
			
			if (dao instanceof NormalTableDao) {
				NormalTableDao normal = (NormalTableDao) dao;
				Tabella t = null;
				if (relazione instanceof Identificativo)
					t = normal.findByIdentificativo((Identificativo)relazione);
				if (t!=null) {
					risultato = new LinkedHashSet<Tabella>();
					risultato.add(t);
				}
			}
				
		}
		
		// null deve essere solo la prima volta , quando ancora la relazione non è stata interrogata
		if (risultato ==null)
			risultato = new LinkedHashSet<Tabella>();
				
		return risultato;
	}



	public Relazione(Class tabella, Campo campo) 
	{
		this.conn = conn;
		this.relazione = campo;
		this.tabella = tabella;
	}
	

	
	public Campo getRelazione()
	{
		return relazione;
	}

	public Class getTabella()
	{
		return tabella;
	}
	
	public Tabella getUniqueResult(Connection conn) {
		Tabella tab;
		try
		{
			tab = CollectionsUtils.getUniqueElement(this.getRisultato(conn));
		}
		catch (DaoException e)
		{
			String msg = "Errore su recupero relazione " + tabella;
			log.error(e.getMessage(),e);
			return null;
		}
		return tab;
	}



	@Override
	public String toString()
	{
		if(this.getRelazione().getValore() != null)
			return this.getRelazione().getValore().toString();
		else
			return "";
	}

	


}
