package it.webred.ct.data.access.basic.indice.ricerca.util;

import it.webred.ct.data.access.basic.indice.ricerca.IndiceCorrelazioneEntityException;
import it.webred.ct.data.model.annotation.IndiceEntity;
import it.webred.ct.data.model.annotation.IndiceKey;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class IndiceKeyUtil implements Serializable {

	protected Logger logger = Logger.getLogger("ctservice.log");

	public static final String SEP = "sep";
	public static final String SORGENTE = "sor";

	public String createKey(Object o, String sep) {
		String key = "";

		try {
			List<FieldInfo> keys = getFields(o);
			logger.debug("---keys.size(): " + keys.size());
			Collections.sort(keys);

			for (FieldInfo fi : keys) {
				key = key + fi.getValue() + sep;
				
			}

			key = key.substring(0, key.length() - 1);
			
			logger.debug("ID DWH Key ["+key+"]");

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(),e);
			return null;
		}

		return key;
	}

	public Map<String, String> getEntityInfo(Object o) {

		Map<String, String> result = new HashMap<String, String>();

		Class c = o.getClass();

		Annotation a = c.getAnnotation(IndiceEntity.class);

		if (a == null)
			throw new IndiceCorrelazioneEntityException();

		IndiceEntity ie = (IndiceEntity) a;

		result.put(SEP, ie.sep());
		result.put(SORGENTE, ie.sorgente());

		return result;
	}

	public Object createObject(String className, String key, String fonteFK,
			String progEs) {
		Object result = null;
		logger.debug("createObject()--className ["+ className +"];key["+ key +"];fonteFK["+ fonteFK +"];progEs["+ progEs +"]" );
		try {
			Class c = Thread.currentThread().getContextClassLoader().loadClass(
					className);
			result = c.newInstance();

			Map<String, String> info = getEntityInfo(result);

			StringTokenizer st = new StringTokenizer(key, info.get(SEP));
			String[] parts = new String[st.countTokens()];
			int pos = 0;

			while (st.hasMoreTokens()) {
				String part = st.nextToken();
				parts[pos++] = part;
			}

			Field fields[] = c.getDeclaredFields();

			logger.debug("\tFields [" + fields.length + "]");

			for (Field f : fields) {

				// logger.info("Field ["+f.getName()+"]");
				f.setAccessible(true);
				Annotation a = f.getAnnotation(IndiceKey.class);

				if (a != null) {

					IndiceKey ik = (IndiceKey) a;
					
					
					int fpos = Integer.parseInt(ik.pos()) - 1;

					String type = f.getType().toString();
					
					//logger.debug("\tAttribute: Type ["+type+"] - Name ["+f.getName()+"] - Pos ["+fpos+"] - Value ["+parts[fpos]+"]");
					
					if (fpos ==  parts.length)
						f.set(result,null);
					else {
						if (type.indexOf("String") != -1)
							f.set(result, parts[fpos]);
						else if (type.indexOf("BigDecimal") != -1) {
							BigDecimal bd = new BigDecimal(parts[fpos]);
							f.set(result, bd);
						}
						else if (type.indexOf("Date") != -1) {
							String pat = ik.pattern();
							SimpleDateFormat sdf = new SimpleDateFormat(pat);
							Date d = sdf.parse(parts[fpos]);
							f.set(result, d);
						}
						else
							logger.error("Unable to set value for ["+type+"]");	
					}
					
					
				}
			}

		} catch (Throwable t) {
			logger.error("ERRORE "+t.getMessage(), t ); 
		}
		
		return result;
	}

	private List<FieldInfo> getFields(Object o) throws IllegalAccessException {

		List<FieldInfo> keys = new ArrayList<FieldInfo>();

		Class c = o.getClass();

		// logger.info("Class ["+c+"]");
		Field fields[] = c.getDeclaredFields();
		
		logger.debug("Fields [" + fields.length + "]");

		for (Field f : fields) {
			
			f.setAccessible(true);
			Annotation a = f.getAnnotation(IndiceKey.class);

			if (a != null) {
				FieldInfo fi = new FieldInfo();
				IndiceKey ik = (IndiceKey) a;
				fi.setName(f.getName());
				fi.setPos(ik.pos());
				Object r = f.get(o);
				if (r!=null) {
					if (r instanceof String)
						fi.setValue((String) r);
					else if (r instanceof BigDecimal)
						fi.setValue(((BigDecimal) r).toString());
					else if (r instanceof Long) 
						fi.setValue(((Long) r).toString());
					else if (r instanceof Date) {
						SimpleDateFormat sdf = new SimpleDateFormat(ik.pattern());					
						fi.setValue(sdf.format((Date) r));
					}
					
					else
						throw new IndiceCorrelazioneEntityException("TIPO NON GESTITO: " + r.getClass().getName());
				} else {
					//throw new IndiceCorrelazioneEntityException();
				}

				keys.add(fi);
			}
		}

		return keys;
	}

	public void print(Object o) {

		List<FieldInfo> keys = new ArrayList<FieldInfo>();
		try {

			Class c = o.getClass();

			logger.debug("--- Print Object Info -----");
			logger.debug("Class [" + c + "]");
			Field fields[] = c.getDeclaredFields();

			logger.debug("Fields [" + fields.length + "]");

			for (Field f : fields) {				
				f.setAccessible(true);
				Annotation a = f.getAnnotation(IndiceKey.class);
				if (a != null) {
					logger.debug("Field [" + f.getName() + "] - Value [" + f.get(o) + "]");					
				}

			}
		} catch (Throwable t) {
		}

	}

}
