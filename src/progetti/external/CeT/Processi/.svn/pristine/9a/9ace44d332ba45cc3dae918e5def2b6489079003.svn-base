package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.Command;
import it.webred.rulengine.dwh.def.Campo;
import it.webred.rulengine.dwh.def.Tabella;
import it.webred.rulengine.dwh.table.SitEnteSorgente;
import it.webred.rulengine.dwh.table.SitComune;
import it.webred.rulengine.dwh.table.SitDVia;
import it.webred.rulengine.dwh.table.SitProvincia;
import it.webred.rulengine.dwh.table.SitStato;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;
import it.webred.utils.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class DaoFactory
{

//	private static HashMap<Class<? extends Tabella>, String> dbMapping = new HashMap<Class<? extends Tabella>, String>();
//	private static HashMap<Class<? extends Tabella>, Class<? extends GenericDao>> daoMapping = new HashMap<Class<? extends Tabella>, Class<? extends GenericDao>>();
	private static final Logger log = Logger.getLogger(DaoFactory.class.getName());

	
	private static HashMap<String, Class> daoClasses = new HashMap<String, Class>();
	
	
	/**
	 * Data una classe Dao , fornisce il nome della tabella sulla quale agisce
	 * @param classe
	 * @return
	 */
	public static String getTableNameFromDao(Class<? extends GenericDao> classeDao) {
		
		//Set entries = daoMapping.entrySet();
		
		Class c=null;
		try
		{
			String nomeClasseTabella = classeDao.getName().substring(1,classeDao.getName().length()-3);
			nomeClasseTabella.replaceAll(".Dao.",".table.");
			c = Class.forName(nomeClasseTabella);
		}
		catch (ClassNotFoundException e)
		{
			return null;
		}
	    //Iterator it = entries.iterator();
	    return StringUtils.JavaName2NomeCampo(c.getSimpleName());
//	    while (it.hasNext()) {
//	      Map.Entry entry = (Map.Entry) it.next();
//	      if ( entry.getValue().equals(classeDao)) {
//	    	  Class c = (Class) entry.getKey();
//	    	  return dbMapping.get(c);
//	      }
//	    }
//	    return null;
	}

	
	/**
	 * @param conn - connessione sulla quale opera il componente Dao
	 * @param tab  
	 * @param bes
	 * @param dwhConn - connessione al Dwh (deve essere passata quellapresente in context)
	 * @return
	 * @throws DaoException
	 */
	
	public static GenericDao createDao(Connection conn, Tabella tab , BeanEnteSorgente bes) throws DaoException {
		if(bes==null && tab instanceof TabellaDwh) 
			throw new DaoException("Righe mancanti in SIT_ENTE_SORGENTE");

		try {

			String className = "it.webred.rulengine.dwh.Dao." + tab.getClass().getSimpleName()+ "Dao";
			Class c =null;
			Class classeDao = daoClasses.get(className);
			if (classeDao!=null)
				 c = classeDao;
			else {
				c = Class.forName(className); 
				daoClasses.put(className, c);
			}
				   

			GenericDao dao = null;
			Constructor cnst = getConstructor(tab, c, bes);	

			if (bes!=null)
				dao = (GenericDao) cnst.newInstance(new Object[]{ tab, bes});
			else
				dao = (GenericDao) cnst.newInstance(new Object[]{ tab });
				
			dao.setConnection(conn);

			
			return dao;
				
		} catch (Exception e) {
			log.error("Problemi nel reperire l'oggetto Dao per l'inserimento del record",e);
			throw new DaoException("Problemi nel reperire l'oggetto Dao per l'inserimento del record");
	    }
	}


	/**
	 * @param bes
	 * @throws DaoException 
	 */
	private static Constructor getConstructor(Tabella tab, Class c, BeanEnteSorgente bes) throws DaoException {
		
		
		
		Constructor cnst = null;
		try {
			if (bes!=null) {
					cnst = c.getConstructor(new Class[]{ tab.getClass(), BeanEnteSorgente.class });
					return cnst;
			}
			else {
					cnst = c.getConstructor(new Class[]{ tab.getClass() });
					return cnst;
				
			}
		} catch (NoSuchMethodException e) {
			//log.error("Problemi nel reperire l'oggetto Dao per l'inserimento del record",e);
			//throw new DaoException("Problemi nel reperire il costruttore del Dao per l'inserimento del record");
			
			//in questo caso prova anche con la superclasse di tab.getClass()
			//quindi sostituito (Filippo Mazzini 03.02.10) da:
			try {
				if (bes!=null) {
						cnst = c.getConstructor(new Class[]{ tab.getClass().getSuperclass(), BeanEnteSorgente.class });
						return cnst;
				}
				else {
						cnst = c.getConstructor(new Class[]{ tab.getClass().getSuperclass() });
						return cnst;
					
				}
			} catch (NoSuchMethodException e1) {
				log.error("Problemi nel reperire l'oggetto Dao per l'inserimento del record",e1);
				throw new DaoException("Problemi nel reperire il costruttore del Dao per l'inserimento del record");
			}
			//fine sostituito
		}				
	}
	


	

}
