package it.webred.rulengine.impl.bean;

/**
 * Bean che viene utilizzato insieme alla HibernateSession.
 * all'interno del bean ci sono due campi che sono:
 * - il firstObj che è la sessione,<br>
 * - il secondObj che è l'owner.<br>
 * 
 * @author sisani
 * @version $Revision: 1.2 $ $Date: 2009/05/06 14:28:19 $
 */
public class GenericTuples
{
	public static class T2<E1, E2>
	{
		public final E1 firstObj;
		public final E2 secondObj;

		public T2(final E1 e1, final E2 e2)
		{
			this.firstObj = e1;
			this.secondObj = e2;
		}
	}
	
	public static class T1<E1>
	{
		public final E1 firstObj;

		public T1(final E1 e1)
		{
			this.firstObj = e1;
		}
	}

	public static <E1, E2> T2<E1, E2> t2(final E1 e1, final E2 e2)
	{
		return new T2<E1, E2>(e1, e2);
	}

}
