package it.webred.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * Questa implementazione di {@link java.util.Set}
 * controlla l'identit&agrave; referenziale, e non
 * l'uguaglianza quando controlla se un oggetto
 * sia gi&agrave; presente nel set. In latre parole,
 * mentre secondo le specifiche di {@link java.util.Set}
 * un oggetto si utilizza il metodo equals, qui si utilizza
 * l'operatore di identità ==.
 * </p><p>
 * This java.util.Set implementation use reference-equality 
 * in place of object-equality when check if an object is
 * already present in the Set. In other words, in an IdentitySet, 
 * two objects o1 and o2 are considered equal if and only if (o1 == o2).
 * </p>
 * @see java.util.IdentityHashMap
 * @author Giulio Quaresima
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 */
public class IdentitySet<T> implements Set<T>
{
	private static final int DEFAULT_START_LEN = 16;
	private float loadFactor = 1.5f;
	private int startLen;
	private int actualPosition = -1;
	private Object[] elements;
	
	public IdentitySet(int startLen)
	{
		if (startLen < 1)
			throw new IllegalArgumentException("The initialization lenght should be a positive non-zero integer");
		this.startLen = startLen;
		elements = new Object[this.startLen];
	}
	public IdentitySet()
	{
		this(DEFAULT_START_LEN);
	}
	public boolean add(T o)
	{
		if (o != null && !contains(o))
		{
			if (!(++actualPosition < elements.length))			
			{
				Object[] newElements = new Object[((int) Math.floor(elements.length * loadFactor))];
				System.arraycopy(elements, 0, newElements, 0, elements.length);
				elements = newElements;
			}
			elements[actualPosition] = o;
			return true;
		}
		return false;
	}

	public boolean addAll(Collection c)
	throws UnsupportedOperationException
	{
		throw new UnsupportedOperationException();
	}

	public void clear()
	{
		elements = new Object[startLen];
	}

	public boolean contains(Object o)
	{
		if (o != null)
			for (Object item : elements)
				if (o == item)
					return true;
		return false;
	}

	public boolean containsAll(Collection c)
	{
		if (c != null)
		{
			int count = 0;
			Iterator it = c.iterator();
			while (it.hasNext())
				if (contains(it.next())) count++;
			if (count == c.size())
				return true;
		}
		return false;
	}

	public boolean isEmpty()
	{
		return actualPosition == 0;
	}

	@SuppressWarnings("unchecked")
	public Iterator<T> iterator()
	{
		List<T> result = new ArrayList<T>(actualPosition + 1);
		for (int i = 0; i <= actualPosition; result.add((T) elements[i++]));
		return result.iterator();
	}

	public boolean remove(Object o)
	{
		int pos;
		if ((pos = whereIs(o)) != -1)
		{
			Object[] newElements = new Object[elements.length];
			System.arraycopy(elements, 0, newElements, 0, pos);
			System.arraycopy(elements, pos + 1, newElements, pos, (actualPosition - pos));
			elements = newElements;
			actualPosition--;
			return true;
		}
		return false;
	}

	public boolean removeAll(Collection c)
	{
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection c)
	{
		throw new UnsupportedOperationException();
	}

	public int size()
	{
		return elements.length;
	}

	public Object[] toArray()
	{
		return elements;
	}

	public Object[] toArray(Object[] a)
	{
		throw new UnsupportedOperationException();
	}
	
	private int whereIs(Object o)
	{
		if (o != null)
			for (int i = 0; i < elements.length; i++)
			{
				if (o == elements[i])
					return i;
			}
		return -1;		
	}
	@Override
	public String toString()
	{
		if (elements != null)
		{
			Object[] result = new Object[actualPosition + 1];
			System.arraycopy(elements, 0, result, 0, result.length);
			return Arrays.deepToString(result);			
		}
		return Arrays.deepToString(null);		
	}
	
}
