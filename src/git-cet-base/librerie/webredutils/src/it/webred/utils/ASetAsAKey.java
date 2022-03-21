package it.webred.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Set utile per generare una chiave univoca formata da
 * pi&ugrave; elementi. A differenza di java.util.Set, 
 * che specifica hashCode() come restituente la somma
 * degli hash code degli elementi contenuti, questa 
 * classe restituisce lo hash code ella stringa risultante
 * dalla concatenazione delle chiamate a toString() 
 * sui singoli elementi. Ereditando da TreeSet, questa
 * classe ristituisce un hash insensibile all'ordine
 * degli elementi.
 * 
 * @author Giulio Quaresima
 *
 * @param <T>
 */
public class ASetAsAKey<T extends Comparable> extends TreeSet<T>
{
	
	public static final String SPLIT_SEPARATOR = ",";
	private static final long serialVersionUID = 6027720529057041971L;

	public ASetAsAKey() {}
	public ASetAsAKey(Collection<? extends T>  c) {super(c);}
	public ASetAsAKey(Set<? extends T>  c) {super(c);}
	public ASetAsAKey(T... keys)
	{
		if (keys != null)
			for (T item : keys)
				add(item);
	}
	
	/**
	 * @return il risultato di <code>hashCode()</code> restituito 
	 * dalla stringa risultante dalla concatenazione delle 
	 * chiamate a toString() sui singoli elementi. 
	 * Ereditando da TreeSet, questa classe ristituisce 
	 * un hash insensibile all'ordine in cui gli elementi
	 * sono stati inserite nel Set.
	 */	
	@Override
	public int hashCode()
	{
		String hash = ""; 
		for (T item : this)
			hash += item;
		return hash.hashCode();
	}
	
	/**
	 * Restituisce la concatenazione di tutti gli
	 * elementi del set, separati da virgole
	 */
	@Override
	public String toString()
	{
		String result = "";
		Iterator<T> iter = iterator();
		if (iter.hasNext())
		{
			result += iter.next();
			while (iter.hasNext())
				result += SPLIT_SEPARATOR + iter.next();
		}
		return result;
	}
	
	/**
	 * Restituisce un ASetAsAKey di i cui membri sono
	 * gli elementi contenuti nell'unica stringa
	 * passata come <code>value</code> separati
	 * dalla costante <code>SPLIT_SEPARATOR</code> di questa classe.
	 * @param value
	 * @return
	 * null se si passa null
	 */
	public static ASetAsAKey<String> parse(String value)
	{
		if (value == null)
			return null;
		else
			return new ASetAsAKey<String>(value.split("\\Q" + SPLIT_SEPARATOR + "\\E"));
	}
}
