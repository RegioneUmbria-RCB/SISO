package it.webred.rulengine.entrypoint;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.entrypoint.DatiLogProcesso;

import org.apache.log4j.Logger;

public class LogProcessi
{
	private static final Logger log = Logger.getLogger(LogProcessi.class.getName());
	
	
	
	
	private HashMap<String, Object> getProcessoHashMap(DatiLogProcesso processo) 
	throws Exception
	{
		HashMap<String, Object> retVal = new HashMap<String, Object>();
		Class logProcessoClass = DatiLogProcesso.class;
		Field[] fields = logProcessoClass.getDeclaredFields();
		for (Field field : fields) {
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			String chiave = field.getName();
			Object valore = field.get(processo);
			retVal.put(chiave, valore);
			field.setAccessible(accessible);
		}
		return retVal;
	}
	
}

