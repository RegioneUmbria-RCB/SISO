package it.webred.rulengine.dwh.def;

public class ChiaveOriginale implements Campo
{
	private String valore;
	
	public String getValore()
	{
		return valore;
	}

	public void setValore(String chiaveOriginale) 
	{
		this.valore = chiaveOriginale;
	}

	public void setValore(Object o)
	{
		valore = (String) o;
		
	}
	

}
