package it.webred.utils.type;

import it.webred.utils.type.def.BaseType;



/**
 * Rappresenta il tipo tramite il quale si usa un array di byte
 * @author marcoric
 * @version $Revision: 1.1 $ $Date: 2009/02/06 14:51:25 $
 */
public class ArrayOfByte extends BaseType
{
	private byte[] array;
	
	public byte[] getArray()
	{
		return array;
	}

	private void setArray(byte[] array)
	{
		this.array = array;
	}

	protected ArrayOfByte(String config)
	{
		super(config);
		
	}
	
	
	public ArrayOfByte(byte[] b)
	{
		super();
		this.setArray(b);
	}

	
}
