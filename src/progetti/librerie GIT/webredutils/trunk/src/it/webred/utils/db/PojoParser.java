package it.webred.utils.db;


import it.webred.utils.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;


public class PojoParser {

	public static Map<Class,String[]> _fieldNames=new HashMap<Class,String[]>();
	public static Map<Class,HashMap<String,Method>> _setsTabella =new HashMap<Class,HashMap<String,Method>>();


	
	public static HashMap<String,Method> getSetsMethods(Class pojoClass) {
		
		HashMap<String,Method> sets = _setsTabella.get(pojoClass);
		if (sets==null)
		{
			sets = new HashMap<String,Method>();
			Method[] mts = pojoClass.getMethods();
			// la hash serve per togliere la duplicazione di eventuali doppi metodi set sullo stesso campo
			for(Method m :mts )
			{ 
				String mname = m.getName();
				String campoLower =  mname.substring(3).substring(0, 1).toLowerCase() +  mname.substring(3).substring(1,  mname.substring(3).length());
				if (mname.substring(0,3).equals("set"))
				{
					Field[] fields1 = pojoClass.getDeclaredFields();
					Field[] fields2 = pojoClass.getSuperclass().getDeclaredFields();
					Field[] fields = (Field[])ArrayUtils.addAll(fields1,fields2);
					
					for(Field f : fields) {
						if (f.getName().equalsIgnoreCase(campoLower)) {
							Method method = null;
							try {
								method = pojoClass.getMethod(mname,pojoClass.getDeclaredField(campoLower).getType());
							} catch (SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (NoSuchFieldException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
								sets.put(mname,method);
								break;
						}
					}
						
				}
			}
			_setsTabella.put(pojoClass,sets);
		}
		return sets;
	}
	
	
	public static String[] getDbFieldNames(Class pojoClass) 
	{
		String[] fields = getFieldNames(pojoClass);
		String campi[] = new String[fields.length]; 
		int i = 0;
		for (String s:fields) {
			String nomeCampo = StringUtils.JavaName2NomeCampo(s);
			campi[i] = nomeCampo;
			i = i++;
		}
		return campi;
	}
	
	public static String[] getFieldNames(Class pojoClass) {
		Class theClass =null;
		String[] res = _fieldNames.get(pojoClass);
		if (res == null) {
			String baseClassName = pojoClass.getName()+"Base";
			try {
				theClass = Class.forName(baseClassName);
			} catch (ClassNotFoundException e) {
				theClass = pojoClass;
			}
			Field[] fields = theClass.getDeclaredFields();
			List<String> fieldNames= new ArrayList<String>();
			for (int i = 0; i < fields.length; i++) {
				if (fields[i].getType().equals(String.class) && !fields[i].getName().endsWith("NotParsed")) {
					fieldNames.add(fields[i].getName());
				}
			}
			res = new String[fieldNames.size()];
			fieldNames.toArray(res);
			_fieldNames.put(pojoClass,res);
		} 
		return res;
	}
	
	public static Object rsToBean(ResultSet rs, IRsPrcessor rsProcessor) throws Exception {
		
		return rsProcessor.toBean(rs);
		
	}

	
}
