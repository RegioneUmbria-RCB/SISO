package it.webred.rulengine.db;

import it.webred.rulengine.db.dao.IRConnectionDAO;
import it.webred.rulengine.db.dao.impl.RConnectionDAOImpl;
import it.webred.rulengine.db.model.RConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.dbcp.PoolingDriver;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.log4j.Logger;

/**
 * Classe per la gestione del Connection Poll.
 * Al suo interno ci sono due Hashtable che contengono rispettivamente, il primo
 * due Object dove vengono settate il nome della connection e il GenericObjectPool,
 * il secondo contiene due String che sono tutte e due il nome della connection. 
 * 
 * @author sisani (bravo !!)
 * @version $Revision: 1.3 $ $Date: 2010/01/08 13:54:43 $
 */
@SuppressWarnings("unchecked")
public class RulesConnection extends RulesConnectionBase
{
	//public static String DWH ="DWH";
	//public static String SITI ="SITI";

	public RulesConnection(Connection conn) {
		super(conn);
	}

	private static Hashtable<Object, Object> connectionPools = new Hashtable<Object, Object>();
	private static Hashtable<String, String> connections = new Hashtable<String, String>();
	private static Hashtable<String, String> connectionsFilter = new Hashtable<String, String>();
	
	private static final Logger log = Logger.getLogger(RulesConnection.class.getName());
	
	static{
		init();
	}
	
	private static void init(){
		try
		{
			IRConnectionDAO rcDao = new RConnectionDAOImpl();
			List conns = rcDao.getListaRConnection(reConn);
			for (int i = 0; i < conns.size(); i++)
			{
				RConnection rc = (RConnection) conns.get(i);

				// qui valorizzo tutte le variabili prendendo i dati dalla lista
				String driverClass = rc.getDriverClass();
				String connString = rc.getConnString();
				String connectionName = rc.getName();
				String userName = rc.getUserName();
				String password = rc.getPassword();
				Integer system_connection= rc.getSystemConnection();
				
				try
				{
					Class.forName(driverClass);
				}
				catch (Exception e)
				{
					log.error(driverClass,e);
					throw e;
				}
				GenericObjectPool connectionPool;
				connectionPool = new GenericObjectPool(null);
				connectionPool.setMaxActive(8);
				connectionPool.setMaxIdle(-1);
				connectionPool.setTestOnBorrow(true);
				connectionPool.setTestOnReturn(true);
				connectionPool.setMaxWait(5000);

				connectionPool.setTestWhileIdle(true);
				connectionPool.setTimeBetweenEvictionRunsMillis(1000);
				connectionPool.setNumTestsPerEvictionRun(4);
				connectionPool.setMinEvictableIdleTimeMillis(1000 * 3);

				connectionPool.setWhenExhaustedAction(GenericObjectPool.WHEN_EXHAUSTED_GROW);
				
				@SuppressWarnings("unused")
				ConnectionFactory connectionFactory = null;
				if (userName != null)
					connectionFactory = new DriverManagerConnectionFactory(connString, userName, password);
				else
					connectionFactory = new DriverManagerConnectionFactory(connString, null);

				@SuppressWarnings("unused")
				PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory, connectionPool, null, null, false, true);
				@SuppressWarnings("unused")
				PoolingDataSource dataSource = new PoolingDataSource(connectionPool);
				// System.out.println("CONNECTION NAME = "+connectionName);

				new PoolingDriver().registerPool(connectionName, connectionPool);
				connectionPools.put(connectionName, connectionPool);
				connections.put(connectionName, userName);
				if(system_connection==0)
					connectionsFilter.put(connectionName, connectionName);
			}

		}
		catch (Exception e1)
		{
			throw new RuntimeException(e1);
		}
	}

	/**
	 * Metodo che restituisce una connessione sul database richiesto come
	 * parametro
	 * 
	 * @param database
	 * @return Connection
	 * @throws Exception
	 * @exception
	 */
	public static Connection getConnection(String database) throws Exception	{
		Connection conn = null;
		try
		{
			GenericObjectPool gop = (GenericObjectPool) connectionPools.get(database);
			gop.evict();
			// conn = (Connection) gop.borrowObject();
			Properties p = new Properties();
			p.setProperty("oracle.jdbc.V8Compatible","true");
			System.setProperty("oracle.jdbc.V8Compatible","true");
			conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:" + database,p);

		}
		catch (Exception e)
		{
			log.error("RulesConnection non è in grado di fornire la connessione:" + database,e);
			throw new Exception("Pooler - Impossibile ottenere una connessione al database " + database);
		}
		return conn;
	}

	/**
	 * Metodo che ritorna tutti i nomei delle connessioni presenti nel Connection Pool.
	 * 
	 * @return
	 */
	public static Hashtable<String, String> getConnections()
	{
		return connections;
	}

	
	/**
	 * Metodo che ritorna tutte le connessioni DWH presenti nel connection pool
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static ArrayList<Connection> getAllDWHConnections() throws Exception
	{
		ArrayList<Connection> allConnectionDWH = new ArrayList<Connection>();
		Set<Entry<String, String>> cns = connections.entrySet();
		for (Iterator iterator = cns.iterator(); iterator.hasNext();) {
			Entry<String, String> entry = (Entry<String, String>) iterator.next();
			if (entry.getKey().startsWith("DWH")) {
				Connection c = getConnection(entry.getKey());
				allConnectionDWH.add(c);
			}
		}
		return allConnectionDWH;
	}
	
	/**
	 * Metodo che ritorna nomi delle connessioni che non sono system connection.
	 * 
	 * @return
	 */
	public static Hashtable<String, String> getConnectionsFilter()
	{
		return connectionsFilter;
	}
}
