package it.webred.rulengine.dwh.def;

public class TipoXml implements Campo
{

	private String xml;
	
	public Object getValore()
	{
		return xml;
	}

	public void setValore(Object o)
	{
		
		if (o.getClass().equals(byte[].class)) {
			byte[] b = (byte[])	o;		
			xml = new String(b);	
		}
		else if (o instanceof String)
			xml = (String) o;
		else
			xml = null;
	}

}
