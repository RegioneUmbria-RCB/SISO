package it.webred.rulengine.dwh.table;

public class SitDVia extends TabellaDwh implements EseguiQueryInDisabilitaStorico
{

	private String viasedime;
	private String descrizione;
	
	public String getDescrizione()
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	public String getViasedime()
	{
		return viasedime;
	}

	public void setViasedime(String viasedime)
	{
		this.viasedime = viasedime;
	}

	@Override
	public String getValueForCtrHash()
	{
		return  viasedime + descrizione;
	}
	

}
