package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.Relazione;


public class SitComune extends TabellaDwh implements EseguiQueryInDisabilitaStorico
{

	private String descrizione;
	private String belfiore;
	private Relazione idExtDProvincia = new Relazione(SitProvincia.class,new ChiaveEsterna());;
	
	public String getDescrizione()
	{
		return descrizione;
	}

	public void setDescrizione(String descrizione)
	{
		this.descrizione = descrizione;
	}

	
	public String getValueForCtrHash()
	{
		
		return descrizione+belfiore+idExtDProvincia.getRelazione().getValore();
	}

	public String getBelfiore()
	{
		return belfiore;
	}

	public void setBelfiore(String belfiore)
	{
		this.belfiore = belfiore;
	}

	public Relazione getIdExtDProvincia()
	{
		return idExtDProvincia;
	}

	public void setIdExtDProvincia(ChiaveEsterna idExtDProvincia)
	{
		Relazione r = new Relazione(SitProvincia.class, idExtDProvincia);
		this.idExtDProvincia = r;
	}
	
}
