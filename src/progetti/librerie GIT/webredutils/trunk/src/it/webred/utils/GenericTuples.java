package it.webred.utils;

import java.io.Serializable;


/**
 * 
 * @author marcoric
 * CLASSE CHE RAPPRESENTA IN JAVA 5 DELLE TUPLE GENERICHE DI OGGETTI
 */
public class GenericTuples implements Serializable
{
	public static class T2<E1, E2> implements Serializable
	{
		public final E1 firstObj;
		public final E2 secondObj;

		public T2(final E1 e1, final E2 e2)
		{
			this.firstObj = e1;
			this.secondObj = e2;
		}
	}

	public static class T3<E1, E2, E3> implements Serializable
	{
		public final E1 firstObj;
		public final E2 secondObj;
		public final E3 thirdObj;

		public T3(final E1 e1, final E2 e2, final E3 e3)
		{
			this.firstObj = e1;
			this.secondObj = e2;
			this.thirdObj = e3;
		}
	}
	
	public static <E1, E2> T2<E1, E2> t2(final E1 e1, final E2 e2)
	{
		return new T2<E1, E2>(e1, e2);
	}
	
	public static <E1, E2, E3> T3<E1, E2, E3> t3(final E1 e1, final E2 e2, final E3 e3)
	{
		return new T3<E1, E2, E3>(e1, e2, e3);
	}

}
	    	
	    	
	  
	  
