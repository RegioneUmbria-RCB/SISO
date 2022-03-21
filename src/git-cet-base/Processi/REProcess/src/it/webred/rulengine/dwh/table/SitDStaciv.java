package it.webred.rulengine.dwh.table;

public class SitDStaciv extends TabellaDwh implements EseguiQueryInDisabilitaStorico {

	private String descrizione;
	
	public String getDescrizione()
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	@Override
	public String getValueForCtrHash()
	{
		return descrizione;
	}
	
}
