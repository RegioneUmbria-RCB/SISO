package it.webred.utilities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Sorts a collection of data transfer objects (DTO's) based on the name of the
 * getter of the field to sort on. For example: the DTO MyDto has three fields
 * ID, Name and Value, all with appropriate getters and setters. If you want to
 * sort on the field Name, which has a getter called "getName", then invoke the
 * sorting as follows:
 * 
 * <pre>
 * Collections.sort(myDtoList, new DtoComparator(&quot;getName&quot;, true, true));
 * </pre>
 * 
 * The following call is also supported. The "get" will be prefixed
 * automatically if not present:
 * 
 * <pre>
 * Collections.sort(myDtoList, new DtoComparator(&quot;name&quot;, true, true));
 * </pre>
 * 
 * You can use "deep" getters when a DTO contains one or more nested DTO's. For
 * example: MyDto1 contains a MyDto2 property which contains a String property.
 * If you want to sort myDto1List on the "name" property of MyDto2, then
 * separate the getters by dots and invoke the sorting as follows:
 * 
 * <pre>
 * Collections.sort(myDto1List, new DtoComparator(&quot;myDto2.name&quot;, true, true));
 * </pre>
 * 
 * The boolean 2nd parameter indicates the sort order. If true, then the
 * collection will be sorted ascending at natural sort order. If false, then the
 * collection will be sorted descending at natural sort order. The default value
 * is true.
 * <p>
 * Very useful for lists of DTO's used in JSF datatables.
 * 
 * @author BalusC
 * @link http://balusc.blogspot.com/2006/06/using-datatables.html
 */
public class DTOComparator implements Comparator<Object> {

	// Init
	// ---------------------------------------------------------------------------------------

	private List<String> getters;
	private boolean ascending;

	// Constructor
	// --------------------------------------------------------------------------------

	/**
	 * @param getter
	 *            The name of the getter of the field to sort on.
	 * @param ascending
	 *            The sort order: true = ascending, false = descending.
	 */
	public DTOComparator(String getter, boolean ascending) {
		getters = new ArrayList<String>();
		for (String name : getter.split("\\.")) {
			if (!name.startsWith("get")) {
				name = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
			}
			getters.add(name);
		}
		this.ascending = ascending;
	}

	/**
	 * @param getter
	 *            The name of the getter of the field to sort on.
	 */
	public DTOComparator(String getter) {
		this(getter, true);
	}

	// Actions
	// ------------------------------------------------------------------------------------

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public int compare(Object o1, Object o2) {
		try {
			Iterator<String> iter = getters.iterator();
			while (o1 != null && o2 != null && iter.hasNext()) {
				String getter = iter.next();
				o1 = o1.getClass().getMethod(getter, new Class[0]).invoke(o1, new Object[0]);
				o2 = o2.getClass().getMethod(getter, new Class[0]).invoke(o2, new Object[0]);
			}
		}
		catch (Exception e) {
			// If this exception occurs, then it is usually a fault of the DTO
			// developer.
			throw new RuntimeException("Cannot compare " + o1 + " with " + o2 + " on " + getters, e);
		}

		if (ascending) {
			return (o1 == null) ? -1 : ((o2 == null) ? 1 : ((Comparable<Object>) o1).compareTo(o2));
		}
		else {
			return (o1 == null) ? 1 : ((o2 == null) ? -1 : ((Comparable<Object>) o2).compareTo(o1));
		}
	}

}