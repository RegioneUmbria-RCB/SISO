package it.webred.utils;

import java.util.Stack;

/**
 * LIFO basato interamente su java.util.Stack, 
 * con l'unica <i>feature</i> in pi&ugrave; di fornire
 * la possibilit&agrave; di specificare una dimensione
 * massima e di conservare il primo valore aggiunto. 
 * Quando viene aggiunto un'elemento che
 * faccia crescere lo <i>stack</i> oltre la massima
 * dimensione specificata, l'elemento in fondo allo
 * <i>stack</i>, ovvero quello che &egrave; stato inserito
 * nel punto pi&ugrave; lontano del tempo, viene eliminato.
 * @author Giulio Quaresima
 * @param <T>
 */
public class DimensionableStack<T> extends Stack<T>
{
	private static final long serialVersionUID = 3753825940724791361L;
	
	private T firstPushed;
	private int maxSize = Integer.MAX_VALUE;
	
	/**
	 * Inizializza semplicemente lo <i>stack</i> ad una dimensione
	 * massima di java.lang.Integer.MAX_VALUE.
	 */
	public DimensionableStack() {super();}
	/**
	 * Inizializza lo <i>stack</i> alla dimensione specificata
	 * dal parametro.
	 * @param maxSize
	 * La dimensione massima, che deve essere comunque
	 * superiore a 0.
	 * @throws IllegalArgumentException
	 * Se la dimensione passata è inferiore a 1
	 */
	public DimensionableStack(int maxSize) throws IllegalArgumentException
	{
		this();
		setMaxSize(maxSize);
	}

	/**
	 * Se con questa operazione la dimensione massima dello
	 * <i>stack</i> venisse superata, si provvede ad eliminare
	 * l'ultimo elemento in fondo allo <i>stack</i> stesso,
	 * ovvero quello che &egrave; stato inserito
	 * nel punto pi&ugrave; lontano del tempo.
	 * @param item
	 * L'oggetto da inserire
	 * @return
	 */
	@Override
	public T push(T item)
	{
		if (getFirstPushed() == null)
			setFirstPushed(item);
		while (size() > getMaxSize())
			remove(0);
		return super.push(item);
	}
	
	/**
	 * Restituisce il primo oggetto inserito nello
	 * stack, se esiste, altrimenti restituisce null.
	 * @return
	 */
	public T getFirstPushed()
	{
		return firstPushed;
	}
	private void setFirstPushed(T firstPushed)
	{
		this.firstPushed = firstPushed;
	}
	/**
	 * @return
	 * La dimensione massima attuale di questo <i>stack</i>
	 */
	public int getMaxSize()
	{
		return maxSize;
	}
	/**
	 * @param maxSize
	 * La dimensione massima che dovr&agrave; avere questo <i>stack</i>,
	 * che deve essere comunque superiore a 0.
	 * Alla chiamata di questo metodo, se la dimensione passata
	 * &egrave; minore di quella precedente, lo Stack verr&agrave;
	 * ridimensionato di conseguenza
 	 * @throws IllegalArgumentException
	 * Se la dimensione passata &egrave; inferiore a 1
*/
	public void setMaxSize(int maxSize) throws IllegalArgumentException
	{
		if (maxSize < 1)
			throw new IllegalArgumentException("La dimensione minima dello Stack non pu\u00F2 essere inferiore ad 1");
		while (size() > maxSize)
			remove(0);
		this.maxSize = maxSize;
	}
}
