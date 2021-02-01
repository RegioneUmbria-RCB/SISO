package it.webred.rulengine;

import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public abstract class DbCommand extends Command {
	
	private static final Logger log = Logger.getLogger(DbCommand.class.getName());
	private String connectionName;

	public DbCommand(BeanCommand bc) {
		super(bc);

		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}
	
	
	

	public DbCommand(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
	}


	/**
	 * Sovrascrivo il metodo Run di Command in modo da chiamare il run2
	 * 
	 * @param ctx -
	 *            Context locale che contiene e restituisce i parametri
	 *            specifici di una rule.
	 */
	public CommandAck run(Context ctx) throws CommandException {
		Connection conn = null;
		conn = ctx.getConnection(connectionName);
		if(connectionName==null || conn==null)
			throw new CommandException("Impossibile recuperare la connessione :" + connectionName);
		return ((DbCommand) this).runWithConnection(ctx, conn);
	}



	

	public abstract CommandAck runWithConnection(Context ctx, Connection conn) throws CommandException;
	





	// lista dei parametri per non rifare la query
	protected static List parametriIn = null;
	protected static HashMap<Class, Field[]> fldsCache = new HashMap<Class, Field[]>();
	
	/**
	 *  Cahe degli attributi di un bean
	 * @param c
	 * @return
	 */
	protected static Field[] getClassFields(Class c) {
		Field[] fArray = fldsCache.get(c);
		if (fArray==null) {
			Field[] fields = c.getDeclaredFields();
			fldsCache.put(c,fields);
			return fields;
		}
		return fArray;
	}
	
	private static HashMap<String, java.lang.reflect.Method> mtdsCache = new HashMap<String, java.lang.reflect.Method>();
	/**
	 * Cache dei metodi di un bean
	 * @param c
	 * @param mName
	 * @param param
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 */
	protected static java.lang.reflect.Method getClassMethod(Class c, String mName, Class param) throws SecurityException, NoSuchMethodException {
		java.lang.reflect.Method m = mtdsCache.get(c.getName() + mName);
		if (m==null) {
			m = c.getMethod(mName, param);
			
			
			mtdsCache.put(c.getName() + mName,m);
			return m;
		}
		return m;
	}
	
	
	/**
	 * ritorno metodo con due parametri 
	 * @param c
	 * @param mName
	 * @param param
	 * @param param2
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	protected static java.lang.reflect.Method getClassMethod(Class c, String mName, Class param, Class param2) throws SecurityException, NoSuchMethodException {
		java.lang.reflect.Method m = mtdsCache.get(c.getName() + mName);
		if (m==null) {
			m = c.getMethod(mName, param,param2);
			
			
			mtdsCache.put(c.getName() + mName,m);
			return m;
		}
		return m;
	}
	
	
	/**
	 * Metodo per settare la Connection del DbCommand.
	 * 
	 * @param con
	 */
	public void setConnectionName(String con) {
		connectionName = con;
	}

	public String getConnectionName() {
		return connectionName;
	}

}
