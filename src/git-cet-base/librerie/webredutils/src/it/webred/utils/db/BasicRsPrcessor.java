package it.webred.utils.db;


import it.webred.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;


/**
 * Riesce a processar una riga di un resultset e a metterla in un bean
 * @author marcoric
 *
 */
public class BasicRsPrcessor extends IRsPrcessor {

	public BasicRsPrcessor(Class clazz) {
		super(clazz);
	}
	
				
		
	public Object toBean(ResultSet rs) throws Exception {
		try {

			Object bean;
			bean = pojoClass.newInstance();
			ResultSetMetaData rsMeta = rs.getMetaData();
			HashMap<String,Method> sets = PojoParser.getSetsMethods(pojoClass);
			int numeroColonne = rsMeta.getColumnCount();
			for (int i=1;i<=numeroColonne;i++) {
				Object fieldValue=null;
				if (rs.getObject(i)!=null) {
					String fieldName = StringUtils.nomeCampo2JavaName(rsMeta.getColumnName(i));
					
					String campoUpper =  fieldName.substring(0, 1).toUpperCase() +  fieldName.substring(1,  fieldName.length());
	
					Method mset = sets.get("set" + campoUpper);
					try {
						if (mset.getParameterTypes()[0].getName().equals("java.lang.String"))
							fieldValue = rs.getString(i);
						else if (mset.getParameterTypes()[0].getName().equals("java.lang.BigDecimal"))
							fieldValue = rs.getBigDecimal(i);
						else if (mset.getParameterTypes()[0].getName().equals("java.sql.Date")) {
							Date dt  = null;
							if (rs.getDate(i) != null)
								dt = rs.getDate(i);
							fieldValue = dt;
						}
						else if (mset.getParameterTypes()[0].getName().equals("java.sql.Timestamp")) {
							Timestamp dt  = null;
							if (rs.getTimestamp(i) != null)
								dt = rs.getTimestamp(i);
							fieldValue = dt;
						}
						else 
							fieldValue = rs.getObject(i);
						mset.invoke(bean, fieldValue);
					} catch (Exception e) {
						throw e;
					}
				}
			}
			return bean;
	} catch (InstantiationException e1) {
		throw e1;
	} catch (IllegalAccessException e1) {
		throw e1;
	}


	}
}
