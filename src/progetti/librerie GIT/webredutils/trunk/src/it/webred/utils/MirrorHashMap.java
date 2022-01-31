package it.webred.utils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Mappa bidirezionale, instaura per ogni
 * relazione chiave -&gt; valore il suo reciproco
 * valore -&gt; chiave, per cui:
 * <ul>
 * 	<li>due chiavi non possono mai puntare ad uno stesso valore;</li>
 * 	<li>per ogni coppia chiave -&gt; valore verr&agrave; inserita ahcne la coppia inversa;</li>
 * 	<li>per ogni chiave eliminata verr&agrave; eliminata anche la coppia chiave -&gt; valore e la coppia inversa.</li>
 * </ul>
 * In altre parole, questa mappa realizza
 * una relazione biunivoca tra i due insiemi A e B,
 * @author Giulio Quaresima
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 * @param <T>
 */
public class MirrorHashMap<T> extends LinkedHashMap<T,T>
{
	private static final long serialVersionUID = 2776302125706331442L;

	public MirrorHashMap()
	{
		super();
	}

	public MirrorHashMap(int initialCapacity, float loadFactor)
	{
		super(initialCapacity, loadFactor);
	}

	public MirrorHashMap(int initialCapacity)
	{
		super(initialCapacity);
	}

	@SuppressWarnings("unchecked")
	public MirrorHashMap(Map<? extends T, ? extends T> m)
	{
		this(m.size());
		for (Map.Entry item : m.entrySet())
		{
			put((T) item.getKey(), (T) item.getValue());
			put((T) item.getValue(), (T) item.getKey());
		}
	}

	/**
	 * Inserisce una nuova coppia key -> value.
	 * Prima di aggiungerla:
	 * <ul>
	 * 	<li>
	 * 		se key -&gt; x esiste, vengono eliminate x -&gt; y e y -&gt; x
	 * 	</li>
	 * 	<li>
	 * 		se value -&gt; x esiste, vengono eliminate x -&gt; y e y -&gt; x 
	 * 	</li>
	 * </ul>
	 * @param key
	 * Elemento dell'insieme A
	 * @param value
	 * Elemento dell'insieme B
	 * @return
	 */
	@Override
	public T put(T key, T value)
	{
		synchronized (this)
		{
			if (containsKey(get(key)))
				remove(key);
			if (containsKey(get(value)))
				remove(value);
			super.put(value, key);
			return super.put(key, value);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(Map<? extends T, ? extends T> m)
	{
		synchronized (this)
		{
			for (Map.Entry item : m.entrySet())
			{
				put((T) item.getKey(), (T) item.getValue());
				put((T) item.getValue(), (T) item.getKey());
			}
		}
	}

	@Override
	public T remove(Object key)
	throws NoSuchElementException
	{
		try
		{
			synchronized (this)
			{
				return super.remove(super.remove(get(key)));			
			}
		}
		catch (NullPointerException npe) {throw new NoSuchElementException();}
	}
	
	@SuppressWarnings("unchecked")
	public LinkedHashSet<T> getASet()
	{
		LinkedHashSet<T> result = new LinkedHashSet<T>();
		synchronized (this)
		{
			Iterator iter = values().iterator();
			while (iter.hasNext())
			{
				result.add((T) iter.next());
				iter.next();
			}
		}
		return result;
	}
	@SuppressWarnings("unchecked")
	public LinkedHashSet<T> getBSet()
	{
		LinkedHashSet<T> result = new LinkedHashSet<T>();
		synchronized (this)
		{
			Iterator iter = values().iterator();
			while (iter.hasNext())
			{
				iter.next();
				result.add((T) iter.next());
			}
		}
		return result;
	}

	public static void main (String[] args)
	{
		Map<String,String> mCapitali = new MirrorHashMap<String>();
		mCapitali.put("Parigi","Francia");
		mCapitali.put("Londra","Gran Bretagna");
		mCapitali.put("Madrid","Spagna");
		mCapitali.put("Bonn","Germania");
		mCapitali.put("Roma","Italia");
		System.out.println("");
		System.out.println("Insieme A: " + ((MirrorHashMap) mCapitali).getASet());
		System.out.println("Insieme B: " + ((MirrorHashMap) mCapitali).getBSet());
		System.out.println("");
		System.out.println(mCapitali);
		mCapitali.remove("Italia");
		System.out.println(mCapitali);
		mCapitali.remove("Parigi");
		System.out.println(mCapitali);
		mCapitali.remove("Spagna");
		System.out.println(mCapitali);
		Map<String,String> mAltreCapitali = new LinkedHashMap<String,String>(); 
		mCapitali.put("Amsterdam","Olanda");
		mCapitali.put("Lisbona","Portogallo");
		mCapitali.putAll(mAltreCapitali);
		System.out.println(mCapitali);
		mCapitali.put("Berlino","Germania");
		System.out.println(mCapitali);
		
		System.out.println("");
		System.out.println("Insieme A: " + ((MirrorHashMap) mCapitali).getASet());
		System.out.println("Insieme B: " + ((MirrorHashMap) mCapitali).getBSet());
		System.out.println("");
	}
}
