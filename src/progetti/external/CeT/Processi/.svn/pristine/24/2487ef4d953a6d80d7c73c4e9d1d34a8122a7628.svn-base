package it.webred.rulengine.type.def;

import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.GenericTuples;
import it.webred.rulengine.impl.bean.GenericTuples.T2;
import it.webred.rulengine.type.ArrayOfByte;
import it.webred.rulengine.type.ComplexParam;
import it.webred.rulengine.type.Constant;
import it.webred.rulengine.type.RsIteratorConfig;
import it.webred.rulengine.type.ScriptBeanShell;
import it.webred.rulengine.type.Variable;
import it.webred.rulengine.type.bean.ComplexParamP;
import it.webred.utils.DateFormat;
import it.webred.utils.HashMapUtils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class TypeFactory
{
	protected static HashMap<Class, TypeConstructor> dataTypeList = new HashMap<Class,TypeConstructor>();
	private static TypeFactory tf = new TypeFactory();
	private static final Logger log = Logger.getLogger(TypeFactory.class.getName());
	
	static 
	{	
		dataTypeList.put(java.sql.Date.class,(tf.new DateConstructor()));
		dataTypeList.put(java.sql.ResultSet.class,(tf.new ResultSetConstructor()));
		dataTypeList.put(java.lang.String.class,(tf.new StringConstructor()));
		dataTypeList.put(java.lang.Object.class,(tf.new ObjectConstructor()));
		dataTypeList.put(java.lang.Integer.class,(tf.new IntegerConstructor()));
		dataTypeList.put(java.util.List.class,(tf.new ListConstructor()));
		dataTypeList.put(java.util.Map.class,(tf.new MapConstructor()));
		dataTypeList.put(java.sql.Timestamp.class,(tf.new TimestampConstructor()));
		dataTypeList.put(it.webred.rulengine.type.ComplexParam.class,(tf.new ComplexParamConstructor()));
		dataTypeList.put(it.webred.rulengine.type.RsIteratorConfig.class,(tf.new RsIteratorConfigConstructor()));
		dataTypeList.put(it.webred.rulengine.type.ScriptBeanShell.class,(tf.new ScriptBeanShellConstructor()));
		dataTypeList.put(it.webred.rulengine.type.Constant.class,(tf.new ConstantConstructor()));
		dataTypeList.put(it.webred.rulengine.type.Variable.class,(tf.new VariableConstructor()));
		dataTypeList.put(java.lang.Boolean.class,(tf.new BooleanConstructor()));
		dataTypeList.put(java.math.BigDecimal.class,(tf.new BigDecimalConstructor()));
		dataTypeList.put(java.util.Date.class,(tf.new UtilDateConstructor()));
		dataTypeList.put(ArrayOfByte.class,(tf.new ArrayOfByteConstructor()));
		dataTypeList.put(oracle.sql.CLOB.class,(tf.new ClobConstructor()));
		
	}

	/**
	 * Il metodo restituisce true se il tipo di dato ha un costruttore stringa, sunque è possibile istanziare il tipo
	 * passando una stringa. 
	 * @param type
	 * @return
	 */
	public static boolean isAllowedParamValue(String type) 
	{	
		if (type==null)
			return false;
		
		// se il tipo possiede un costructor allora restituisco true
		
		TypeConstructor constructor;
		try
		{
			constructor = (TypeConstructor) dataTypeList.get(Class.forName(type));
		}
		catch (ClassNotFoundException e)
		{
			return false;
		}
		if (constructor.haveStringConstructor()) 
			return true;
		
		return false;
		
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap<String,String> getTypeList() throws Exception 
	{
		HashMap<String,String> m = new HashMap<String,String>();

		
	
		
		for (Map.Entry<Class, TypeConstructor>  item : dataTypeList.entrySet())
		{
			TypeConstructor tc = (TypeConstructor) item.getValue();
		    m.put(item.getKey().getName(),tc.getDescription());
		}
		
		LinkedHashMap<String,String> lm = new LinkedHashMap<String,String>();
		
		lm = HashMapUtils.sortHashMapByValues(m,true,true);
		
		
		
		return lm;
		
		
	}


	/**
	 * Data la classe di un tipo di dato restituisce la classe costruttore
	 * @param type
	 * @return
	 * @throws Exception
	 */
	protected static TypeConstructor getConstructor(String type) throws RulEngineException 
	{
		try {
		java.lang.Class c = Class.forName(type);
		Object constructor = dataTypeList.get(c);
		while (constructor==null && c.getSuperclass()!=null) {
			c = c.getSuperclass(); 
			constructor = dataTypeList.get(c);
		} 
		
		if (constructor==null) 
			throw new RulEngineException("Costruttore non reperito per il tipo di dato " + type);

		TypeConstructor tc = (TypeConstructor) constructor;
		return tc;

		} catch (Exception e) {
			log.error("Problemi con il tipo di dato " + type, e);
			throw new RulEngineException("Problemi con il tipo di dato " + type);
		}
		
	}

	@SuppressWarnings("unchecked")
	public static List<String> getCompatibleType(String className) throws Exception 
	{
		
		List l = new ArrayList();
		
		Class clazz = Class.forName(className);

		for (Map.Entry<Class, TypeConstructor>  item : dataTypeList.entrySet())
		{
			Class c = null;
			try {
				c = item.getKey();
				c.asSubclass(clazz); // effettua il casting
				l.add(c.getName());
			} catch (Exception e) {
				// le due classi non sono compatibili - comunque se la classe c è declarativeType o Object 
				// ce la posso mettere
				if(c!=null) {
					if (c.getName().equals("java.lang.Object")) {
						l.add(c.getName());
					} else {
						Class superclass = c.getSuperclass();
						if (superclass!=null && superclass.getName().equals("it.webred.rulengine.type.def.DeclarativeType"))
										l.add(c.getName());
					}
				}
			}
			
		}
		
		return l;
		
	}

	
	
		/**
		 * Restituisce il tipo di dato specificato nel parametro type
		 * Generandolo da obj.
		 * Se obj è una stringa viene controllato che il tipo di dato da restituire abbia un costruttore stringa.
		 * Se obj non è una stringa allora viene restituito obj castato a type.
		 * @param obj
		 * @param type
		 * @return
		 * @throws Exception - se obj non è compatibile con il tipo dichiarato nel parametro type
		 */
		public static Object getType(Object obj, String type) throws RulEngineException 
		{
			if (type==null || obj==null)
				if (type!=null && type.indexOf("it.webred.rulengine.dwh.def")==-1) // per i miei tipi so come comportarmi
					return null;

			
				TypeConstructor constructor = getConstructor(type);
				
				// se il tipo non ha un costruttore stringa , ma l'obj passato è una stringa allora scateno un errore
				if(!constructor.haveStringConstructor() && obj instanceof java.lang.String)
					throw new RulEngineException("Si sta cercando di costruire attraverso una stringa un tipo di dato " + type);
		
				
				return constructor.construct(obj);
		
		}
		
		public static Object getType(Object obj, String type, HashMap<String,DeclarativeType> var) throws Exception 
		{
			if (type==null || obj==null)
				return null;
			
			TypeConstructor constructor = getConstructor(type);
			
			// se il tipo non ha un costruttore stringa , ma l'obj passato è una stringa allora scateno un errore
			if(!constructor.haveStringConstructor() && obj instanceof java.lang.String)
				throw new Exception("Si sta cercando di costruire attraverso una stringa un tipo di dato " + type);
	
			Object ret = constructor.construct(obj,var);
			if (ret instanceof ComplexParam)
				((ComplexParam)ret).setVars(var);
			
			return ret;

		}

		
		/**
		 * Effettua la valutazione di un obj rispetto al tipo dichiarato nel parametro type	
		 * Ritorna una tupla con un booleano con false e un messaggio nel caso il parametro non sia compatibile con il suo tipo
		 * @param obj
		 * @param type
		 * @return
		 * @throws Exception
		 */
		public static T2<Boolean,String> evaluateType(Object obj, String type) throws Exception 
		{
			try {
				getType(obj,type);
				return GenericTuples.t2(new Boolean(true),"Valore del dato corretto");
			} catch (Exception e) {
				return GenericTuples.t2(new Boolean(false),e.getMessage());
			}
			
			
		}		
	
	/**
	 * Interfaccia per la definizione dei tipi parametri
	 * @author marcoric
	 * @version $Revision: 1.10 $ $Date: 2010/02/05 13:33:54 $
	 */
	public abstract class TypeConstructor {
		

		/**
		 * Restituisce true se il typo ha un costruttoree con stringa utilizzabile per generare il tipo tramite un input utente.
		 * @return
		 */
		public abstract boolean haveStringConstructor();
		
		/**
		 * Ritorna il tipo di dato -  se obj è una stringa deve costruirlo , altrimenti obj
		 * @param obj
		 * @return
		 * @throws Exception
		 */
		public abstract Object construct(Object obj) throws RulEngineException ;

		public Object construct(Object obj, HashMap<String,DeclarativeType> var) throws Exception {
			// costruisco il tipo poi controllo se l'obj passato era stringa - in caso affermativo provo a vedere se ci sono
			// nella stringa valori di oggetti DeclarativeType da sostituire
			// altrimenti costruisco l'oggetto senza sostituzioni 
				// se il costruttore del tipo ammette un accesso a livello delle variabili e costanti di catena
				if(this instanceof DeclarativeTypeAccessor) {
					obj=((DeclarativeTypeAccessor)this).constructWithDeclarativeType(obj,var);
					
				} else {
					obj =  this.construct(obj);
				}
				return obj;
		}
		
		/**
		 * estrapola il nome di una  variabile tra doppia parentesi quadra
		 * @param nameWithParentesis
		 * @return
		 */
		public String getDesclarTypeName(String nameWithParentesis) {
			int inipos = nameWithParentesis.indexOf("[[");
			int lastpos = 0;
			if (inipos>=0) {
				 lastpos = nameWithParentesis.indexOf("]]",inipos+2);
			}
			String valoreDecT = null;
			
			if (inipos<lastpos && lastpos>0)
				valoreDecT = nameWithParentesis.substring(inipos+2,lastpos);
			
			return valoreDecT;

		}
	
		public abstract String getDescription();
		

		
	}

	public class DateConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof java.sql.Date))
			{
				
				Date d = DateFormat.stringToDate((String) obj, "dd/MM/yyyy");
				if (d==null && obj!=null)
					throw new RulEngineException("Impossibile convertire il parametro data secondo il formato dd-MM-yyyy");
				return d;
	
			} else {
				return (java.sql.Date) obj;
			}

		}

		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
			return "Data SQL";
		}

		
	}
	
	

	

	public class UtilDateConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof java.util.Date))
			{
				
				java.util.Date d = DateFormat.stringToDate((String) obj, "dd/MM/yyyy");
				if (d==null && obj!=null)
					throw new RulEngineException("Impossibile convertire il parametro data secondo il formato dd-MM-yyyy");
				return d;
	
			} else {
				return (java.util.Date) obj;
			}

		}

		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
			return "Data";
		}

		
	}

	
	public class TimestampConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof Timestamp))
			{
				boolean errato = false;
				Date d = DateFormat.stringToDate((String) obj, "dd/MM/yyyy HH:mm:ss");
				if (d==null && obj!=null)
					errato = true;
				else
					errato = false;

				if (errato && d==null)
						d = DateFormat.stringToDate((String) obj, "dd/MM/yyyy HH:mm");
						if (d==null && obj!=null)
							errato = true;
						else
							errato = false;
							
				if (errato && d==null)
					d = DateFormat.stringToDate((String) obj, "yyyyMMdd HH:mm:ss");
					if (d==null && obj!=null)
						errato = true;
					else
						errato = false;

				if (errato && d==null)
					d = DateFormat.stringToDate((String) obj, "dd-MM-yyyy HH:mm:ss");
					if (d==null && obj!=null)
						errato = true;
					else
						errato = false;
				
				if (errato && d==null)
					d = DateFormat.stringToDate((String) obj, "yyyyMMddHHmmss");
					if (d==null && obj!=null)
						errato = true;
					else
						errato = false;

				if (errato && d==null)
						d = DateFormat.stringToDate((String) obj, "dd-MM-yyyy HH:mm");
						if (d==null && obj!=null)
							errato = true;
						else
							errato = false;


			if (errato && d==null)
					d = DateFormat.stringToDate((String) obj, "yyyyMMdd HHmmss");
					if (d==null && obj!=null)
						errato = true;
					else
						errato = false;
						
				if (errato)	
					throw new RulEngineException("Impossibile convertire il parametro data / ora secondo uno dei formati previsti - " + (String) obj);

				Timestamp t = new Timestamp(d.getTime());
				return t;
	
			} else {
				return (java.sql.Timestamp) obj;
			}

		}
		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
			return "Data / ora ";
		}
		


	}	

	
	public class IntegerConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof java.lang.Integer))   
			{
				String s = (String)obj;
				if (s!=null)
					return new Integer(s.trim());
				else
					return null;
			} else {
				return (java.lang.Integer) obj;
			}

		}
		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
			return "Intero";
		}
		
	}		

	public class BigDecimalConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (obj instanceof java.math.BigDecimal)   
			{
				return (java.math.BigDecimal) obj;
			} else if (obj instanceof java.lang.Integer)   {
				return new java.math.BigDecimal((Integer)obj);
			} else {
				String s = (String)obj;
				if (s!=null)
					return new java.math.BigDecimal(s.trim());
				else
					return null;
			}

		}
		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
			return "Numerico Decimale";
		}
		
	}		

	public class ResultSetConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return (ResultSet)obj;
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "Risultato Query Sql";
		}

	}	
	
	public class ClobConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return (oracle.sql.CLOB)obj;
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "CLOB";
		}

	}		
	
	
	public class ArrayOfByteConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return (ArrayOfByte)obj;
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "Informazione binaria";
		}

	}		
	
	public class StringConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return (String)obj;
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "Stringa";
		}

		
	}	

	public class ConstantConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return (Constant)obj;
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "Costante";
		}

		
	}


	
	public class BooleanConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof java.lang.Boolean))   
			{
				String s = (String)obj;
				// ritorna true se s = true (ignorecase) altrimenti false
				if (s!=null)
					return new Boolean(s);
				else
					return null;
			} else {
				return (java.lang.Boolean) obj;
			}	
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "Valore Booleano (true/false)";
		}

		
	}
	
	public class VariableConstructor extends TypeConstructor {


		public Object construct(Object obj) throws RulEngineException
		{
			return (Variable)obj;
		}
		public boolean haveStringConstructor()
		{
			return false;
		}
		public String getDescription()
		{
			return "Variabile";
		}

		
	}
	public class ObjectConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return obj;
		}
		public boolean haveStringConstructor()
		{
			return true;
		}
		public String getDescription()
		{
			return "Oggetto generico";
		}

		
	}
	
	public class ScriptBeanShellConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof ScriptBeanShell))   
			{
				String config = (String) obj; 
				try
				{
					ReType rt = new ScriptBeanShell(config);
					return rt;
				}
				catch (Exception e)
				{
					log.error("Errori ScriptBeanShellConstructor ", e);
					throw new RulEngineException("Creazione del tipo di dato ScriptBeanShell non ruscita - possibili errori di configurazione");
				}
			} else {
				return obj;
			}

		}
		
		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
				return "Script shell";
		}

		
	}	
	
	
	public class ListConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return (java.util.List)obj;
		}
		public boolean haveStringConstructor()
		{
			return false;
		}

		public String getDescription()
		{
			return "Lista di oggetti";
		}
		
	}	

	
	public class MapConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			return (java.util.Map)obj;
		}
		public boolean haveStringConstructor()
		{
			return false;
		}

		public String getDescription()
		{
			return "Map di oggetti";
		}
		
	}	

	
	public class ComplexParamConstructor extends TypeConstructor implements DeclarativeTypeAccessor{

		HashMap<String, DeclarativeType> var = new LinkedHashMap<String, DeclarativeType>();
		
		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof it.webred.rulengine.type.ComplexParam))   
			{
				String config = (String) obj; 
				try
				{
					ReType rt = new ComplexParam(config,var);
					return rt;
				}
				catch (Exception e)
				{
					log.error("Errori ComplexParamConstructor per l'oggetto: " + config);
					throw new RulEngineException("Creazione del tipo di dato ComplexParam non riuscita - possibili errori di configurazione");
				}
			} else {
				return obj;
			}

		}
		
		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
				return "Xml di parametri";
		}

		/* 
		 * Il metodo cerca di sostituire un segnaposto di un tipo dichiarativo negli attributi name, type e valore di ComplexParamP
		 * @see it.webred.rulengine.type.def.DeclarativeTypeAccessor#substituteDeclarativeType(java.util.HashMap)
		 */
		public Object constructWithDeclarativeType(Object obj, HashMap<String, DeclarativeType> var) throws Exception
		{
			this.var = var;
			it.webred.rulengine.type.ComplexParam cp = (it.webred.rulengine.type.ComplexParam)this.construct(obj);
			
			HashMap<String, ComplexParamP> m = cp.getParams();
			for (Map.Entry<String, ComplexParamP>  item : m.entrySet())
			{
				ComplexParamP cpp= item.getValue();
				
				if (cpp.getValore() instanceof String) {
					String valS = (String)cpp.getValore();
					// ottengo il valore della variabile
					
					String nomeDecT = this.getDesclarTypeName(valS);
					if (nomeDecT!=null ) {
						if (var.get(nomeDecT)!=null) {
							cpp.setValore(TypeFactory.getType(var.get(nomeDecT).getValue(),var.get(nomeDecT).getType()) );
							cpp.setType(var.get(nomeDecT).getType());
							
							// sostituisco il parametro con quello riformattato
							cp.addParam(cpp.getType(),cpp.getName(),cpp.getValore());
						} else {
							cp.addParam(null,cpp.getName(),null);
						}
					} 
					
				}

						
			}
			return cp;
		}			

	}		
	
	public class RsIteratorConfigConstructor extends TypeConstructor {

		public Object construct(Object obj) throws RulEngineException
		{
			if (!(obj instanceof it.webred.rulengine.type.RsIteratorConfig))   
			{
				String config = (String) obj; 
				try
				{
					ReType rt = new RsIteratorConfig(config);
					return rt;
				}
				catch (Exception e)
				{
					log.error("RsIteratorConfigConstructor",e);
					throw new RulEngineException("Creazione del tipo di dato RsIteratorConfig non ruscita - possibili errori di configurazione", e);
				}
			} else {
				return obj;
			}

		}
		
		public boolean haveStringConstructor()
		{
			return true;
		}

		public String getDescription()
		{
				return "Parametro di configurazione proprietario ResultSet Iterator";
		}


		
	}		
	
	 public static void main(String[] args) {
			try
			{
				HashMap<String, String> m = TypeFactory.getTypeList();
				for (Map.Entry<String, String>  item : m.entrySet())
				{
				    log.debug(item.getValue());
//				    log.debug((TypeFactory.getType("31-12-2004",item.getKey(),null));
				    List l = TypeFactory.getCompatibleType(item.getKey());
				    log.debug(l);
				}

			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				log.error(e);
			}
			
	 }	
	 
}




