package it.webred.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Metodi statici di utilit&agrave; che utilizzano
 * la riflessione per compiere operazioni dinamiche
 * su oggetti.
 * @author Giulio Quaresima
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 */
public class ReflectionUtils
{
	static private byte Bye;
	static private char Cha;
	static private short Sho;
	static private int Int;
	static private long Lon;
	static private float Flo;
	static private double Dbl;
	static private boolean Ble;
	
	/**
	 * Cerca di resettare tutti i campi di un oggetto.
	 * L'inizializzazione verr&agrave; tentata per tutte le
	 * propriet&agrave; di classe che risultino, 
	 * contemporaneamente, non <code>static</code> e non 
	 * <code>final</code>. Le propriet&agrave; di tipo non
	 * primitivo verranno inizializzate a <code>null</code>, le propriet&agrave;
	 * di tipo primitivo verranno invece inizializzate con lo
	 * stesso valore a cui viene inizializzata una variabile 
	 * primitiva dichiarata ma non inizializzata esplicitamente, 
	 * ovvero al valore di default assegnato dalla Java Virtual Machine.
	 * @param instance
	 * L'oggetto le cui propriet&agrave; si vogliono
	 * resettare.
	 * @param recurseToParents
	 * Se vero, indica che si vogliono resettare
	 * anche tutte le propriet&agrave; della (eventuale) classe
	 * genitore di <i>instance</i>, e cos&igrave; via ricorsivamente
	 * fino alla classe <code>java.lang.Object</code>.
	 * @param arrayToNull
	 * Se <code>true</code>, ogni propriet&agrave; che sia
	 * un'array verr&agrave; semplicemente portata a null, 
	 * altrimenti l'array verr&agrave; lasciato inizializzato,
	 * ma tutti i suoi campi verranno tutti resettati
	 * con le stesse regole con cui vengono resettate le 
	 * propriet&agrave;. In caso di array multidimensionali,
	 * tutto ci&ograve; viene ripetuto ricorsivamente.
	 * @param doNotClearThese
	 * L'elenco dei nomi delle propriet&agrave; 
	 * che non si vuole vengano resettate.
	 * @throws IllegalAccessException
	 */
	public static void clearAllFields(Object instance, boolean recurseToParents, boolean arrayToNull, String... doNotClearThese) 
	throws IllegalAccessException
	{
		synchronized (instance)
		{
			if (instance != null)
				clearAllFields(instance.getClass(), instance, recurseToParents, arrayToNull, doNotClearThese);			
		}
	}
	private static void clearAllFields(Class cl, Object instance, boolean recurseToParents, boolean arrayToNull, String... doNotClearThese) 
	throws IllegalAccessException
	{
		Field[] fields = cl.getDeclaredFields();
		AccessibleObject.setAccessible(fields, true);
		try
		{
			for (Field field : fields)
				if (((field.getModifiers() & (Modifier.FINAL | Modifier.STATIC)) == 0) && !isOneOf(field.getName(), doNotClearThese))
				{
					if (field.getType().isPrimitive())
						clearPrimitive(field, instance);
					else if (field.getType().isArray() && !arrayToNull)
						clearArray(field.get(instance));
					else
						field.set(instance, null);			
				}
		}
		catch (IllegalAccessException e) {throw e;}
		
		if (recurseToParents && cl.getSuperclass() != null)
			clearAllFields(cl.getSuperclass(), instance, recurseToParents, arrayToNull);
	}
	static private void clearPrimitive(Class cls, int index, Object instance) 
	throws IllegalArgumentException, IllegalAccessException
	{
		if (cls.equals(byte.class))
			Array.setByte(instance, index, Bye);
		else if (cls.equals(char.class))
			Array.setChar(instance, index, Cha);
		else if (cls.equals(short.class))
			Array.setShort(instance, index, Sho);
		else if (cls.equals(int.class))
			Array.setInt(instance, index, Int);
		else if (cls.equals(long.class))
			Array.setLong(instance, index, Lon);
		else if (cls.equals(float.class))
			Array.setFloat(instance, index, Flo);
		else if (cls.equals(double.class))
			Array.setDouble(instance, index, Dbl);
		else if (cls.equals(boolean.class))
			Array.setBoolean(instance, index, Ble);		
	}
	static private void clearPrimitive(Field field, Object instance) 
	throws IllegalArgumentException, IllegalAccessException
	{
		if (field.getType().equals(byte.class))
			field.setByte(instance, Bye);
		else if (field.getType().equals(char.class))
			field.setChar(instance, Cha);
		else if (field.getType().equals(short.class))
			field.setShort(instance, Sho);
		else if (field.getType().equals(int.class))
			field.setInt(instance, Int);
		else if (field.getType().equals(long.class))
			field.setLong(instance, Lon);
		else if (field.getType().equals(float.class))
			field.setFloat(instance, Flo);
		else if (field.getType().equals(double.class))
			field.setDouble(instance, Dbl);
		else if (field.getType().equals(boolean.class))
			field.setBoolean(instance, Ble);		
	}
	static private void clearArray(Object array) 
	throws IllegalArgumentException, IllegalAccessException
	{
		if (array != null)
		{
			for (int i = 0; i < Array.getLength(array); i++)
			{
				Object item = Array.get(array, i);
				if (item != null)
				{
					if (item.getClass().isArray())
						clearArray(item);
					else if (item.getClass().isPrimitive())
						clearPrimitive(item.getClass(), i, array);
					else
						Array.set(array, i, null);					
				}
			}
		}		
	}
	static private boolean isOneOf(String item, String... items) 
	{
		if (item == null || items == null)	return false;
		boolean result = false;
		for (int i = 0; i < items.length && !result; result = item.equals(items[i++]));
		return result;
	}
	
	
	
	public static Cloneable deepClone(Cloneable instanceToClone) throws IllegalArgumentException, Throwable
	{
		Cloneable result = null;
		if (instanceToClone != null)
		{
			Class cl = instanceToClone.getClass();
			try
			{
				result = (Cloneable) cl.getClass().getMethod("clone").invoke(instanceToClone);
			}
			catch (IllegalArgumentException e) {return null;}
			catch (SecurityException e) {throw new IllegalArgumentException(e);}
			catch (IllegalAccessException e) {throw new IllegalArgumentException(e);}
			catch (InvocationTargetException e) 
			{
				throw new IllegalArgumentException("Perch\u00E9 mai implementare l'interfaccia Cloneable (classe " + cl.getName() + ") e non implementare correttamente il metodo clone() ?!?!?", e.getCause());
			}
			catch (NoSuchMethodException e)
			{
				throw new IllegalArgumentException("Perch\u00E9 mai implementare l'interfaccia Cloneable (classe " + cl.getName() + ") e non implementare il metodo clone() ?!?!?", e);
			}
			Field[] fields = cl.getDeclaredFields();
			AccessibleObject.setAccessible(fields, true);
			for (Field field : fields)
				if ((field.getModifiers() & Modifier.STATIC) == 0)
				{
					if (field.getType().isPrimitive())
						continue;
					if (field.getType().isArray())
					{
						for (int i = 0; i < Array.getLength(field.get(result));)
						{
							//TODO
						}
						continue;
					}
				}
		}
		return result;
	}
}
