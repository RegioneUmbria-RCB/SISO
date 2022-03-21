package it.webred.rulengine.dwh.table;

public class SitDFrazione extends TabellaDwh implements EseguiQueryInDisabilitaStorico
{

	public String frades;
	
	public String getFrades()
	{
		return frades;
	}

	public void setFrades(String frades)
	{
		this.frades = frades;
	}

	@Override
	public String getValueForCtrHash()
	{
		
		return frades;
	}

}
