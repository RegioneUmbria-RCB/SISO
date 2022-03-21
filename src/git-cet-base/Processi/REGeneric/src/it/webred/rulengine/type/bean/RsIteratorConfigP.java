package it.webred.rulengine.type.bean;

/**
 * Bean che rappresenta il tag param dell'xml di configurazione dell'RsIterator
 * Viene usato dal Type RsIteratorConfig
 * 
 * @author sisani
 * @version $Revision: 1.1 $ $Date: 2007/05/16 07:22:24 $
 */
public class RsIteratorConfigP
{
	private String id;
	private String value;
	private String dest;
	private String type;

	/**
	 * 
	 */
	public RsIteratorConfigP()
	{
	}

	/**
	 * @return
	 */
	public String getDest()
	{
		return dest;
	}

	/**
	 * @param dest
	 */
	public void setDest(String dest)
	{
		this.dest = dest;
	}

	/**
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(String type)
	{
		this.type = type;
	}

}

