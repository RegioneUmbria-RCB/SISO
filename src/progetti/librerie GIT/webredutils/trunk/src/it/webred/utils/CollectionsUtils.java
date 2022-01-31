package it.webred.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Giulio Quaresima
 * Classe di utilit&agrave; per gli insiemi
 */
public class CollectionsUtils
{
	/**
	 * Calcola l'intersezione di due insiemi. Se l'intersezione
	 * &egrave; l'insieme vuoto, restituisce un Set vuoto. 
	 * @param <S>
	 * Il tipo del set passato e restituito
	 * @param <T>
	 * Il tipo generico dei set
	 * @param set1
	 * @param set2
	 * @return
	 * Il set con l'intersezione dei due insiemi, 
	 * se esiste, o <code>null</code> se almeno uno dei set passati
	 * &egrave; <code>null</code>. 
	 * @throws InstantiationException
	 * Se il tipo <S> non pu&ograve; essere istanziato
	 * con un costruttore pubblico senza parametri. 
	 */
	@SuppressWarnings("unchecked")
	public static <S extends Set<T>,T> S intersect(S set1, S set2)
	throws InstantiationException, IllegalAccessException
	{
		if (set1 != null && set2 != null)
		{
			S result = (S) set1.getClass().newInstance();
			for (T item : set1)
				if (set2.contains(item))
					result.add(item);
			return result;
		}
		return null;
	}
	/**
	 * Calcola l'unione di due insiemi. Se 
	 * i due Set passati sono vuoti o nulli, restituisce
	 * un Set vuoto. 
	 * @param <S>
	 * Il tipo del set passato e restituito
	 * @param <T>
	 * Il tipo generico dei set
	 * @param set1
	 * @param set2
	 * @return
	 * Il set con l'unione dei due insiemi, 
	 * <code>null</code> se entrambi i set passati
	 * sono <code>null</code>.
	 * @throws InstantiationException
	 * Se il tipo <S> non pu&ograve; essere istanziato
	 * con un costruttore pubblico senza parametri. 
	 */
	@SuppressWarnings("unchecked")
	public static <S extends Set<T>,T> S union(S set1, S set2)
	throws InstantiationException, IllegalAccessException
	{
		S result = null;
		if (set1 != null)
		{
			result = (S) set1.getClass().newInstance();
			for (T item : set1)
				result.add(item);
		}
		if (set2 != null)
		{
			if (result == null) result = (S) set2.getClass().newInstance();
			for (T item : set2)
				result.add(item);
		}
		return result;
	}
	/**
	 * Restituisce l'unico elemento contenuto nel Set.
	 * Se il set &egrave; nullo, o &egrave; vuoto, o se contiene 
	 * pi&ugrave; di un elemento, restituisce null.
	 * Questa utilit&agrave; &egrave; stata pensata per i casi in cui abbiamo
	 * un Set e siamo sicuri che abbia un solo elemento, ad es., 
	 * in una classe di persistenza dei dati come quelle fornite da
	 * Hibernate, si possono avere delle relazioni reciproche di 
	 * Foreign Key rappresentate da Set, ma se noi sappiamo che
	 * nei dati la relazione &egrave; 1 a 1, possiamo essere
	 * sicuri che quel set conterr&agrave; soltanto un elemento.
	 * @param <T>
	 * @param s
	 * @return
	 * L'unico elemento contenuto dal Set, se esiste, null altrimenti
	 */
	public static <T> T getUniqueElement(Set<? extends T> s)
	{
		if (s != null && s.size() == 1)
			// TODO: verificare quale delle due seguenti soluzioni sia
			// piu' efficiente.
			return s.iterator().next();
			//return new ArrayList<T>(s).get(0);
		return null;
	}
	/**
	 * Restituisce l'elemento minore dell'insieme, confrontandoli tra loro utilizzando
	 * il metodo compareTo se il Set contiene oggetti che implementano
	 * l'interfaccia Comparable, altrimenti confronta la rappresentazione
	 * stringa degli oggetti tramite toString()
	 * @param <T>
	 * @param s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getLessElement(Set<? extends T> s)
	{
		if (s == null)
			return null;

		T lessItem = null;
		for (T item : s)
		{
			if (lessItem == null)
				lessItem = item;
			
			if (item != null)
			{
				if (item instanceof Comparable)
				{
					if (((Comparable) item).compareTo(lessItem) < 0)
						lessItem = item;
				}
				else
				{
					if (item.toString().compareTo(lessItem.toString()) < 0)
						lessItem = item;
				}				
			}
		}
		return lessItem;
	}
	/**
	 * Restituisce l'elemento maggiore dell'insieme, confrontandoli tra loro utilizzando
	 * il metodo compareTo se il Set contiene oggetti che implementano
	 * l'interfaccia Comparable, altrimenti confronta la rappresentazione
	 * stringa degli oggetti tramite toString()
	 * @param <T>
	 * @param s
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getGreaterElement(Set<? extends T> s)
	{
		if (s == null)
			return null;

		T greaterItem = null;
		for (T item : s)
		{
			if (greaterItem == null)
				greaterItem = item;
			
			if (item != null)
			{
				if (item instanceof Comparable)
				{
					if (((Comparable) item).compareTo(greaterItem) > 0)
						greaterItem = item;
				}
				else
				{
					if (item.toString().compareTo(greaterItem.toString()) > 0)
						greaterItem = item;
				}				
			}
		}
		return greaterItem;
	}
	
	/**
	 * @param c
	 * @return
	 * <code>true</code> se la {@link Collection}
	 * passata &egrave; <code>null</code> o vuota
	 * (c.isEmpty()).
	 */
	public static boolean isEmpty(Collection c)
	{
		return c == null || c.isEmpty();
	}
	
	public static void main (String args[])
	throws Throwable
	{
		Set<String> set1 = new HashSet<String>();
		Set<String> set2 = new HashSet<String>();
		Set<String> set3 = new HashSet<String>();
		set1.add("pippo");
		set1.add("pluto");
		set1.add("paperino");
		set1.add("banda bassotti");
		set2.add("pluto");
		set2.add("gastone");
		set2.add("paperoga");
		set2.add("banda bassotti");
		set3.add("minni");
		set3.add("zio paperone");
		set3.add("gastone");
		System.out.println("INSIEME A: " + set1);
		System.out.println("INSIEME B: " + set2);
		System.out.println("INSIEME C: " + set3);
		System.out.println("A \u2229 B:     " + intersect(set1, set2));
		System.out.println("A \u2229 C:     " + intersect(set1, set3));
		System.out.println("B \u2229 C:     " + intersect(set2, set3));
		System.out.println("A \u2229 B \u2229 C: " + intersect(set1, intersect(set2, set3)));
		System.out.println("A \u222A B:     " + union(set1, set2));
		System.out.println("A \u222A C:     " + union(set1, set3));
		System.out.println("B \u222A C:     " + union(set2, set3));
		System.out.println("A \u222A B \u222A C: " + union(set1, union(set2, set3)));
	}
}
