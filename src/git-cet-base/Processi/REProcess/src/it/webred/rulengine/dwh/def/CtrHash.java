package it.webred.rulengine.dwh.def;

public class CtrHash implements Campo
{
	private String valore;
	
	public void setValore (String valore)
	{
		this.valore = valore;
	}

	public String getValore()
	{
		return valore;
	}

	public void setValore(Object o)
	{
		valore =(String) o;
		
	}

	
}
