package it.webred.utils;

import java.util.Comparator;

/**
 * Set dipendente da un Comparator specificato
 * @author Giulio Quaresima
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:15:20 $
 * @param <T>
 */
public class DependingOnComparatorSet<T> extends IdentitySet<T>
{
	private static final long serialVersionUID = -1916551380710737466L;
	private Comparator<T> comparator;
	
	private DependingOnComparatorSet() {}
	public DependingOnComparatorSet(Comparator comparator)
	throws NullPointerException
	{
		this();
		setComparator(comparator);
	}
	
	@Override
	public boolean add(T o)
	{
		if (o != null && !contains(o))
			return super.add(o);
		return false;
	}

	/**
	 * @param o
	 * L'oggetto da controllare
	 * @return 
	 * A differenza dal contratto generale di 
	 * {@link java.util.Collection#contains(java.lang.Object)},
	 * restituisce true solo se esiste un oggetto x
	 * in questo Set per cui &egrave; vero:
	 * <code>
	 * o.getClass().equals(x.getClass()) && this.getComparator().compare(x, (T) o) == 0
	 * </code>
	 */
	/* (non-Javadoc)
	 * @see java.util.Collection#contains(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object o)
	{
		if (o != null)
			for (T item : this)
				if (o.getClass().equals(item.getClass()) && getComparator().compare(item, (T) o) == 0)
					return true;
		return false;
	}
	
	
	public Comparator<T> getComparator()
	{
		return comparator;
	}
	public void setComparator(Comparator<T> comparator)
	throws NullPointerException
	{
		if (comparator == null)
			throw new NullPointerException();
		this.comparator = comparator;
	}
	
	/**
	 * @param o
	 * L'oggetto di cui si vuole l'istanza corrispondente
	 * @return
	 * l'istanza dell'oggetto contenuto
	 * se &egrave; vera l'espressione
	 * <code>
	 * contains(o);
	 * </code>
	 * altrimenti null
	 */
	public T get(T o)
	{
		if (o != null)
			for (T item : this)
				if (o.getClass().equals(item.getClass()) && getComparator().compare(item, (T) o) == 0)
					return item;
		return null;
	}
}
