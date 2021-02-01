package it.webred.rulengine.impl;

import it.webred.rulengine.Context;
import it.webred.rulengine.Costants;
import it.webred.rulengine.db.RulesConnection;
import it.webred.rulengine.exception.ContextException;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;
import it.webred.rulengine.type.def.DeclarativeType;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * <p>
 * Convenience base class for {@link Context} implementations.
 * </p>
 * <p>
 * In addition to the minimal functionality required by the {@link Context}
 * interface, this class implements the recommended support for
 * <em>Attribute-Property Transparency</p>.  This is implemented by
 * analyzing the available JavaBeans properties of this class (or its
 * subclass), exposes them as key-value pairs in the <code>Map</code>,
 * with the key being the name of the property itself.</p>
 * <p>
 * L'oggetto possiede una HashMap che contiene eventuali valori di tipo DeclarativeType
 * presenti nel contesto.
 * <p>
 * <p><strong>IMPLEMENTATION NOTE</strong> - Because <code>empty</code> is a
 * read-only property defined by the <code>Map</code> interface, it may not
 * be utilized as an attribute key or property name.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.4 $ $Date: 2008/09/05 09:29:20 $
 */

public class ContextBase extends HashMap implements Context
{
	
	private static Logger log = Logger.getLogger(ContextBase.class.getName());
	
	private String processID = null;
	private String belfiore = null;
	private List parametriConfigurazioneEnte = null;
	private List fonteDatiEnte = null;
	private Long idFonte = null;
	private Long idSched;
	private Map jellyParams = null;
	private Map reverseObjects = null;
	
	private HashMap<String,DeclarativeType> declarativeType = new HashMap<String,DeclarativeType>();
	private HashMap<String,Connection> connections = new HashMap<String,Connection>();
	private HashMap<Integer,BeanEnteSorgente> sorgentiDati = new HashMap<Integer,BeanEnteSorgente>();
	// ------------------------------------------------------------ Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 3690284676206502872L;

	/**
	 * Default, no argument constructor.
	 */
	public ContextBase() {
		super();
		initialize();
	}

	/**
	 * <p>
	 * Initialize the contents of this {@link Context} by copying the values
	 * from the specified <code>Map</code>. Any keys in <code>map</code>
	 * that correspond to local properties will cause the setter method for that
	 * property to be called.
	 * </p>
	 * 
	 * @param map
	 *            Map whose key-value pairs are added
	 * @exception IllegalArgumentException
	 *                if an exception is thrown writing a local property value
	 * @exception UnsupportedOperationException
	 *                if a local property does not have a write method.
	 */
	@SuppressWarnings("unchecked")
	public ContextBase(Map map)
	{
		super(map);
		initialize();
		putAll(map);

	}

	// ------------------------------------------------------ Instance Variables

	/**
	 * <p>
	 * The <code>PropertyDescriptor</code>s for all JavaBeans properties of
	 * this {@link Context} implementation class, keyed by property name. This
	 * collection is allocated only if there are any JavaBeans properties.
	 * </p>
	 */
	private Map descriptors = null;

	/**
	 * <p>
	 * The same <code>PropertyDescriptor</code>s as an array.
	 * </p>
	 */
	private PropertyDescriptor[] pd = null;

	/**
	 * <p>
	 * Distinguished singleton value that is stored in the map for each key that
	 * is actually a property. This value is used to ensure that
	 * <code>equals()</code> comparisons will always fail.
	 * </p>
	 */
	private static Object singleton;

	static
	{
		singleton = new Object() {
			public boolean equals(Object object)
			{
				return (false);
			}
		};

	}

	/**
	 * <p>
	 * Zero-length array of parameter values for calling property getters.
	 * </p>
	 */
	private static Object[] zeroParams = new Object[0];

	// ------------------------------------------------------------- Map Methods

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to clear all keys and
	 * values except those corresponding to JavaBeans properties.
	 * </p>
	 */
	public void clear()
	{

		if (descriptors == null)
		{
			super.clear();
		}
		else
		{
			Iterator keys = keySet().iterator();
			while (keys.hasNext())
			{
				Object key = keys.next();
				if (!descriptors.containsKey(key))
				{
					keys.remove();
				}
			}
		}

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to return
	 * <code>true</code> if the specified value is present in either the
	 * underlying <code>Map</code> or one of the local property values.
	 * </p>
	 * 
	 * @exception IllegalArgumentException
	 *                if a property getter throws an exception
	 */
	public boolean containsValue(Object value)
	{

		// Case 1 -- no local properties
		if (descriptors == null)
		{
			return (super.containsValue(value));
		}

		// Case 2 -- value found in the underlying Map
		else if (super.containsValue(value))
		{
			return (true);
		}

		// Case 3 -- check the values of our readable properties
		for (int i = 0; i < pd.length; i++)
		{
			if (pd[i].getReadMethod() != null)
			{
				Object prop = readProperty(pd[i]);
				if (value == null)
				{
					if (prop == null)
					{
						return (true);
					}
				}
				else if (value.equals(prop))
				{
					return (true);
				}
			}
		}
		return (false);

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to return a
	 * <code>Set</code> that meets the specified default behavior except for
	 * attempts to remove the key for a property of the {@link Context}
	 * implementation class, which will throw
	 * <code>UnsupportedOperationException</code>.
	 * </p>
	 */
	public Set entrySet()
	{

		return (new EntrySetImpl());

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to return the value of a
	 * local property if the specified key matches a local property name.
	 * </p>
	 * <p>
	 * <strong>IMPLEMENTATION NOTE</strong> - If the specified <code>key</code>
	 * identifies a write-only property, <code>null</code> will arbitrarily be
	 * returned, in order to avoid difficulties implementing the contracts of
	 * the <code>Map</code> interface.
	 * </p>
	 * 
	 * @param key
	 *            Key of the value to be returned
	 * @exception IllegalArgumentException
	 *                if an exception is thrown reading this local property
	 *                value
	 * @exception UnsupportedOperationException
	 *                if this local property does not have a read method.
	 */
	public Object get(Object key)
	{

		// Case 1 -- no local properties
		if (descriptors == null)
		{
			return (super.get(key));
		}

		// Case 2 -- this is a local property
		if (key != null)
		{
			PropertyDescriptor descriptor = (PropertyDescriptor) descriptors.get(key);
			if (descriptor != null)
			{
				if (descriptor.getReadMethod() != null) {					
					return (readProperty(descriptor));
				}
				else	{
					return (null);
				}
			}
		}

		// Case 3 -- retrieve value from our underlying Map
		return (super.get(key));

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to return
	 * <code>true</code> if the underlying <code>Map</code> only contains
	 * key-value pairs for local properties (if any).
	 * </p>
	 */
	public boolean isEmpty()
	{

		// Case 1 -- no local properties
		if (descriptors == null)
		{
			return (super.isEmpty());
		}

		// Case 2 -- compare key count to property count
		return (super.size() <= descriptors.size());

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to return a
	 * <code>Set</code> that meets the specified default behavior except for
	 * attempts to remove the key for a property of the {@link Context}
	 * implementation class, which will throw
	 * <code>UnsupportedOperationException</code>.
	 * </p>
	 */
	public Set keySet()
	{

		return (super.keySet());

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to set the value of a
	 * local property if the specified key matches a local property name.
	 * </p>
	 * 
	 * @param key
	 *            Key of the value to be stored or replaced
	 * @param value
	 *            New value to be stored
	 * @exception IllegalArgumentException
	 *                if an exception is thrown reading or wrting this local
	 *                property value
	 * @exception UnsupportedOperationException
	 *                if this local property does not have both a read method
	 *                and a write method
	 */
	@SuppressWarnings("unchecked")
	public Object put(Object key, Object value)
	{
		Object valueBis = null;
		// se è una dichiarazione di variabile o costante allora le aggiungo
		// alle mappe corrispondenti
		if (value instanceof DeclarativeType)
		{
			DeclarativeType dt = (DeclarativeType) value;
			if(declarativeType.get(dt.getName())==null)
				declarativeType.put(dt.getName(), dt);
			// nel caso di declarativetype metto nel contesto il suo valore e
			// non l'oggetto
			valueBis = dt.getValue();
		}
		else
		{
			valueBis = value;
		}

		// Case 1 -- no local properties
		if (descriptors == null)
		{
			return (super.put(key, valueBis));
		}

		// Case 2 -- this is a local property
		if (key != null)
		{
			PropertyDescriptor descriptor = (PropertyDescriptor) descriptors.get(key);
			if (descriptor != null)
			{
				Object previous = null;
				if (descriptor.getReadMethod() != null)
				{
					previous = readProperty(descriptor);
				}
				writeProperty(descriptor, valueBis);
				return (previous);
			}
		}

		// Case 3 -- store or replace value in our underlying map
		return (super.put(key, valueBis));

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to call the
	 * <code>put()</code> method individually for each key-value pair in the
	 * specified <code>Map</code>.
	 * </p>
	 * 
	 * @param map
	 *            <code>Map</code> containing key-value pairs to store (or
	 *            replace)
	 * @exception IllegalArgumentException
	 *                if an exception is thrown reading or wrting a local
	 *                property value
	 * @exception UnsupportedOperationException
	 *                if a local property does not have both a read method and a
	 *                write method
	 */
	public void putAll(Map map)
	{

		Iterator pairs = map.entrySet().iterator();
		while (pairs.hasNext())
		{
			Map.Entry pair = (Map.Entry) pairs.next();
			
			// se è una dichiarazione di variabile o costante allora le aggiungo alle
			// mappe corrispondenti
			if (pair.getValue() instanceof DeclarativeType ) {
				DeclarativeType dt = (DeclarativeType) pair.getValue();
				if(declarativeType.get(dt.getName())==null)
					declarativeType.put(dt.getName(),dt);

				put(pair.getKey(), dt.getValue());
			} else {

				put(pair.getKey(), pair.getValue());
			}
		}

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to throw
	 * <code>UnsupportedOperationException</code> on any attempt to remove a
	 * key that is the name of a local property.
	 * </p>
	 * 
	 * @param key
	 *            Key to be removed
	 * @exception UnsupportedOperationException
	 *                if the specified <code>key</code> matches the name of a
	 *                local property
	 */
	public Object remove(Object key)
	{

		// Case 1 -- no local properties
		if (descriptors == null)
		{
			return (super.remove(key));
		}

		// Case 2 -- this is a local property
		if (key != null)		{
			PropertyDescriptor descriptor = (PropertyDescriptor) descriptors.get(key);
			if (descriptor != null)
			{
				throw new UnsupportedOperationException("Local property '" + key + "' cannot be removed");
			}
		}

		// Case 3 -- remove from underlying Map
		return (super.remove(key));

	}

	/**
	 * <p>
	 * Override the default <code>Map</code> behavior to return a
	 * <code>Collection</code> that meets the specified default behavior
	 * except for attempts to remove the key for a property of the
	 * {@link Context} implementation class, which will throw
	 * <code>UnsupportedOperationException</code>.
	 * </p>
	 */
	public Collection values()
	{

		return (new ValuesImpl());

	}

	// --------------------------------------------------------- Private Methods

	/**
	 * <p>
	 * Eliminate the specified property descriptor from the list of property
	 * descriptors in <code>pd</code>.
	 * </p>
	 * 
	 * @param name
	 *            Name of the property to eliminate
	 * @exception IllegalArgumentException
	 *                if the specified property name is not present
	 */
	private void eliminate(String name)
	{

		int j = -1;
		for (int i = 0; i < pd.length; i++)
		{
			if (name.equals(pd[i].getName()))
			{
				j = i;
				break;
			}
		}
		if (j < 0)
		{
			throw new IllegalArgumentException("Property '" + name + "' is not present");
		}
		PropertyDescriptor[] results = new PropertyDescriptor[pd.length - 1];
		System.arraycopy(pd, 0, results, 0, j);
		System.arraycopy(pd, j + 1, results, j, pd.length - (j + 1));
		pd = results;

	}

	/**
	 * <p>
	 * Return an <code>Iterator</code> over the set of <code>Map.Entry</code>
	 * objects representing our key-value pairs.
	 * </p>
	 */
	private Iterator entriesIterator()
	{

		return (new EntrySetIterator());

	}

	/**
	 * <p>
	 * Return a <code>Map.Entry</code> for the specified key value, if it is
	 * present; otherwise, return <code>null</code>.
	 * </p>
	 * 
	 * @param key
	 *            Attribute key or property name
	 */
	private Map.Entry entry(Object key)
	{

		if (containsKey(key))
		{
			return (new MapEntryImpl(key, get(key)));
		}
		else
		{
			return (null);
		}

	}

	/**
	 * <p>
	 * Customize the contents of our underlying <code>Map</code> so that it
	 * contains keys corresponding to all of the JavaBeans properties of the
	 * {@link Context} implementation class.
	 * </p>
	 * 
	 * @exception IllegalArgumentException
	 *                if an exception is thrown writing this local property
	 *                value
	 * @exception UnsupportedOperationException
	 *                if this local property does not have a write method.
	 */
	@SuppressWarnings("unchecked")
	private void initialize()
	{

		// Retrieve the set of property descriptors for this Context class
		try
		{
			pd = Introspector.getBeanInfo(getClass()).getPropertyDescriptors();
		}
		catch (IntrospectionException e)
		{
			pd = new PropertyDescriptor[0]; // Should never happen
		}
		eliminate("class"); // Because of "getClass()"
		eliminate("empty"); // Because of "isEmpty()"

		// Initialize the underlying Map contents
		if (pd.length > 0)
		{
			descriptors = new HashMap();
			for (int i = 0; i < pd.length; i++)
			{
				descriptors.put(pd[i].getName(), pd[i]);
				super.put(pd[i].getName(), singleton);
			}
		}

	}

	/**
	 * <p>
	 * Get and return the value for the specified property.
	 * </p>
	 * 
	 * @param descriptor
	 *            <code>PropertyDescriptor</code> for the specified property
	 * @exception IllegalArgumentException
	 *                if an exception is thrown reading this local property
	 *                value
	 * @exception UnsupportedOperationException
	 *                if this local property does not have a read method.
	 */
	private Object readProperty(PropertyDescriptor descriptor)
	{

		try
		{
			Method method = descriptor.getReadMethod();
			if (method == null)
			{
				throw new UnsupportedOperationException("Property '" + descriptor.getName() + "' is not readable");
			}
			return (method.invoke(this, zeroParams));
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException("Exception reading property '" + descriptor.getName() + "': " + e.getMessage());
		}

	}

	/**
	 * <p>
	 * Remove the specified key-value pair, if it exists, and return
	 * <code>true</code>. If this pair does not exist, return
	 * <code>false</code>.
	 * </p>
	 * 
	 * @param entry
	 *            Key-value pair to be removed
	 * @exception UnsupportedOperationException
	 *                if the specified key identifies a property instead of an
	 *                attribute
	 */
	private boolean remove(Map.Entry entry)
	{

		Map.Entry actual = entry(entry.getKey());
		if (actual == null)
		{
			return (false);
		}
		else if (!entry.equals(actual))
		{
			return (false);
		}
		else
		{
			remove(entry.getKey());
			return (true);
		}

	}

	/**
	 * <p>
	 * Return an <code>Iterator</code> over the set of values in this
	 * <code>Map</code>.
	 * </p>
	 */
	private Iterator valuesIterator()
	{

		return (new ValuesIterator());

	}

	/**
	 * <p>
	 * Set the value for the specified property.
	 * </p>
	 * 
	 * @param descriptor
	 *            <code>PropertyDescriptor</code> for the specified property
	 * @param value
	 *            The new value for this property (must be of the correct type)
	 * @exception IllegalArgumentException
	 *                if an exception is thrown writing this local property
	 *                value
	 * @exception UnsupportedOperationException
	 *                if this local property does not have a write method.
	 */
	private void writeProperty(PropertyDescriptor descriptor, Object value)
	{

		try
		{
			Method method = descriptor.getWriteMethod();
			if (method == null)
			{
				throw new UnsupportedOperationException("Property '" + descriptor.getName() + "' is not writeable");
			}
			method.invoke(this, new Object[]
			{ value });
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException("Exception writing property '" + descriptor.getName() + "': " + e.getMessage());
		}

	}

	// --------------------------------------------------------- Private Classes

	/**
	 * <p>
	 * Private implementation of <code>Set</code> that implements the
	 * semantics required for the value returned by <code>entrySet()</code>.
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	private class EntrySetImpl extends AbstractSet
	{

		public void clear()
		{
			ContextBase.this.clear();
		}

		public boolean contains(Object obj)
		{
			if (!(obj instanceof Map.Entry))
			{
				return (false);
			}
			Map.Entry entry = (Map.Entry) obj;
			Entry actual = ContextBase.this.entry(entry.getKey());
			if (actual != null)
			{
				return (actual.equals(entry));
			}
			else
			{
				return (false);
			}
		}

		public boolean isEmpty()
		{
			return (ContextBase.this.isEmpty());
		}

		public Iterator iterator()
		{
			return (ContextBase.this.entriesIterator());
		}

		public boolean remove(Object obj)
		{
			if (obj instanceof Map.Entry)
			{
				return (ContextBase.this.remove((Map.Entry) obj));
			}
			else
			{
				return (false);
			}
		}

		public int size()
		{
			return (ContextBase.this.size());
		}

	}

	/**
	 * <p>
	 * Private implementation of <code>Iterator</code> for the
	 * <code>Set</code> returned by <code>entrySet()</code>.
	 * </p>
	 */
	private class EntrySetIterator implements Iterator
	{

		Map.Entry entry = null;

		private Iterator keys = ContextBase.this.keySet().iterator();

		public boolean hasNext()
		{
			return (keys.hasNext());
		}

		public Object next()
		{
			entry = ContextBase.this.entry(keys.next());
			return (entry);
		}

		public void remove()
		{
			ContextBase.this.remove(entry);
		}

	}

	/**
	 * <p>
	 * Private implementation of <code>Map.Entry</code> for each item in
	 * <code>EntrySetImpl</code>.
	 * </p>
	 */
	private class MapEntryImpl implements Map.Entry
	{

		MapEntryImpl(Object key, Object value)
		{
			this.key = key;
			this.value = value;
		}

		private Object key;

		private Object value;

		public boolean equals(Object obj)
		{
			if (obj == null)
			{
				return (false);
			}
			else if (!(obj instanceof Map.Entry))
			{
				return (false);
			}
			Map.Entry entry = (Map.Entry) obj;
			if (key == null)
			{
				return (entry.getKey() == null);
			}
			if (key.equals(entry.getKey()))
			{
				if (value == null)
				{
					return (entry.getValue() == null);
				}
				else
				{
					return (value.equals(entry.getValue()));
				}
			}
			else
			{
				return (false);
			}
		}

		public Object getKey()
		{
			return (this.key);
		}

		public Object getValue()
		{
			return (this.value);
		}

		public int hashCode()
		{
			return (((key == null) ? 0 : key.hashCode()) ^ ((value == null) ? 0 : value.hashCode()));
		}

		public Object setValue(Object value)
		{
			Object previous = this.value;
			ContextBase.this.put(this.key, value);
			this.value = value;
			return (previous);
		}

	}

	/**
	 * <p>
	 * Private implementation of <code>Collection</code> that implements the
	 * semantics required for the value returned by <code>values()</code>.
	 * </p>
	 */
	private class ValuesImpl extends AbstractCollection
	{

		public void clear()
		{
			ContextBase.this.clear();
		}

		public boolean contains(Object obj)
		{
			if (!(obj instanceof Map.Entry))
			{
				return (false);
			}
			Map.Entry entry = (Map.Entry) obj;
			return (ContextBase.this.containsValue(entry.getValue()));
		}

		public boolean isEmpty()
		{
			return (ContextBase.this.isEmpty());
		}

		public Iterator iterator()
		{
			return (ContextBase.this.valuesIterator());
		}

		public boolean remove(Object obj)
		{
			if (obj instanceof Map.Entry)
			{
				return (ContextBase.this.remove((Map.Entry) obj));
			}
			else
			{
				return (false);
			}
		}

		public int size()
		{
			return (ContextBase.this.size());
		}

	}

	/**
	 * <p>
	 * Private implementation of <code>Iterator</code> for the
	 * <code>Collection</code> returned by <code>values()</code>.
	 * </p>
	 */
	private class ValuesIterator implements Iterator
	{

		Map.Entry entry = null;

		private Iterator keys = ContextBase.this.keySet().iterator();

		public boolean hasNext()
		{
			return (keys.hasNext());
		}

		public Object next()
		{
			entry = ContextBase.this.entry(keys.next());
			return (entry.getValue());
		}

		public void remove()
		{
			ContextBase.this.remove(entry);
		}

	}

	public String getProcessID()
	{
		return processID;
	}

	public void setProcessID(String processID)
	{
		this.processID = processID;

	}
	
	public List getParametriConfigurazioneEnte() {
		return parametriConfigurazioneEnte;
	}

	public void setParametriConfigurazioneEnte(List parametriConfigurazioneEnte) {
		this.parametriConfigurazioneEnte = parametriConfigurazioneEnte;
	}

	public List getFonteDatiEnte() {
		return fonteDatiEnte;
	}

	public void setFonteDatiEnte(List fonteDatiEnte) {
		this.fonteDatiEnte = fonteDatiEnte;
	}

	public String getBelfiore() {
		return belfiore;
	}

	public void setBelfiore(String belfiore) {
		this.belfiore = belfiore;
	}

	public Long getIdFonte() {
		return idFonte;
	}

	public void setIdFonte(Long idFonte) {
		this.idFonte = idFonte;
	}

	/**
	 * @return HashMap delle vatiabili e costanti che sono state messe nel DontextBase
	 * La chiave è il nome della variabile o costante
	 */
	public HashMap<String, DeclarativeType> getDeclarativeType()
	{
		return declarativeType;
	}

	public void addDeclarativeType(String name, DeclarativeType dt)
	{
		if(declarativeType.get(name)==null) {
			declarativeType.put(name,dt);
		}
	
		if("RULENGINE.CODENTE".equals(name)) {
				Costants.ente.set(dt.getValue());
		}
	}
	
	/**
	 * @param name - nome della variabile/ Costante  da recuperare
	 * @return
	 */
	public DeclarativeType getDeclarativeType(String name)
	{
		return declarativeType.get(name);
	}


	private void cacheConnection(String connectionName, Connection conn) throws SQLException
	{
		if (connections.get(connectionName)==null) 
		{
			connections.put(connectionName,conn);
		}

	}

	
	
	/**
	 * Metodo che controlla la presenza della connessione e eventualmente ne crea una per 
	 * il nome passato
	 * @param name - nome della conenssione
	 * @return
	 */
	public Connection getConnection(String connectionName)
	{
		Connection conn = null;
		try
		{
			if(connections!=null && connections.size()>0)
				conn = connections.get(connectionName);
			if(conn ==null)
			{
				conn = RulesConnection.getConnection(connectionName);

				DatabaseMetaData dmd = conn.getMetaData();
			    int transLevelIsolation = 0;
			    if( dmd.supportsTransactionIsolationLevel( Connection.TRANSACTION_READ_UNCOMMITTED) )
			        transLevelIsolation = Connection.TRANSACTION_READ_UNCOMMITTED;
			    
				
			    if (transLevelIsolation != 0 )
			    	conn.setTransactionIsolation(transLevelIsolation);
		

				this.cacheConnection(connectionName, conn);
				conn.setAutoCommit(false);
			}

			return conn;
		}
		catch (Exception e)
		{
			throw new UnsupportedOperationException("Errore cercando di ottenere una connessione: " + connectionName + e.getMessage());
		}
	}
	
	public HashMap<String, Connection> getConnections()
	{
		return connections;
	}

	public void addConnectionNoTransactionIsolation(String name, Connection conn) throws SQLException
	{
		cacheConnection(name,conn);
			
	}

	public void addEnteSorgente(BeanEnteSorgente bes) {
		sorgentiDati.put(bes.getId(), bes);
		
	}

	public BeanEnteSorgente getEnteSorgenteById(Integer id) {
		return (BeanEnteSorgente)sorgentiDati.get(id);
	}

	public HashMap<Integer, BeanEnteSorgente> getListaEnteSrgente() {
		return sorgentiDati;
	}

	public void setListaEnteSorgente(HashMap<Integer, BeanEnteSorgente> lbes) {
		sorgentiDati = lbes;
	}

	@SuppressWarnings("unchecked")
	public void copiaAttributi(Context ctx) throws ContextException {
		// passo il processid da ctx a ctx1
		this.setProcessID(ctx.getProcessID());
		
		//impostazione belfiore
		this.setBelfiore(ctx.getBelfiore());
		
		//impostazione cfg ente
		this.setParametriConfigurazioneEnte(ctx.getParametriConfigurazioneEnte());
		this.setFonteDatiEnte(ctx.getFonteDatiEnte());
		
		//impostazione idFonte
		this.setIdFonte(ctx.getIdFonte());
		
		//viene anche riportata la lista degli ente_sorgente
		this.setListaEnteSorgente(ctx.getListaEnteSrgente());
		
		//passaggio eventuali parametri jelly
		this.setJellyParams(ctx.getJellyParams());
		
		//parametri per operazioni di reverse da tmp a dwh in fase di acquisizione (A) [R-RIVERSA]
		//la map contine 2 liste: tabelleDWH e tabs
		this.riempiReverseObjects(ctx.getReverseObjects());
		
		// Prima di eseguire un command riverso anche la hash dei
		// DeclarativeType e le connessioni utilizzate nel contesto
		for (Map.Entry<String, DeclarativeType> item : ctx.getDeclarativeType().entrySet())
		{
			String key = item.getKey();
			DeclarativeType dt = item.getValue();
			this.addDeclarativeType(key, dt);
		}
		
		//copia connessioni
		for (Map.Entry<String, Connection> itemConnection : ctx.getConnections().entrySet())
		{
			String key = itemConnection.getKey();
			Connection conn = itemConnection.getValue();
			try {
				this.addConnectionNoTransactionIsolation(key, conn);
			} catch (SQLException e) {
				throw new ContextException(e);
			}
		}
		
	}

	public void riempiReverseObjects(Map reverseObjects2) {
		this.reverseObjects = reverseObjects2;
	}

	public Long getIdSched() {
		return idSched;
	}

	public void setIdSched(Long idSched) {
		this.idSched = idSched;
	}

		public Map getJellyParams() {
		return jellyParams;
	}

	public void setJellyParams(Map jellyParams) {
		this.jellyParams = jellyParams;
	}

	public Map getReverseObjects() {
		return reverseObjects;
	}

	public void addReverseObjects(Map reverseObjects) {
		if (this.reverseObjects ==null)  
			this.reverseObjects = reverseObjects!=null?reverseObjects: new HashMap();
		else {
			if (reverseObjects.get("reverse.tabelleDWH")!=null) {
				// può essere null per quei caricatori che per definizione non sono in replace e dunque non e' sttato specificato nulla in getTabelleFinaliDWH 
				((ArrayList<String>) this.reverseObjects.get("reverse.tabelleDWH")).addAll((ArrayList)reverseObjects.get("reverse.tabelleDWH"));
				((LinkedHashMap<String, String>) this.reverseObjects.get("reverse.tabs")).putAll((LinkedHashMap<String, String>)reverseObjects.get("reverse.tabs"));
			}
		}
		


		
	}


	
}
