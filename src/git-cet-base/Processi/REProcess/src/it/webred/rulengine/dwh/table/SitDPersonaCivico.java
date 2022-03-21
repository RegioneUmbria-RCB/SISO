package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;

public class SitDPersonaCivico extends TabellaDwh
{

	private Relazione idExtDPersona = new Relazione(SitDPersona.class,new ChiaveEsterna());
	private Relazione idExtDCivico = new Relazione(SitDCivico.class,new ChiaveEsterna());
	
	
	
	@Override
	public String getValueForCtrHash()
	{
		return ""+idExtDPersona.getRelazione().getValore()+idExtDCivico.getRelazione().getValore();
	}



	public Relazione getIdExtDCivico()
	{
		return idExtDCivico;
	}

	public void setIdExtDCivico(ChiaveEsterna IdExtDCivico)
	{
		Relazione r = new Relazione(SitDCivico.class,IdExtDCivico);
		this.idExtDCivico = r;	
	}


	public Relazione getIdExtDPersona()
	{
		return idExtDPersona;
	}

	public void setIdExtDPersona(ChiaveEsterna IdExtDPersona)
	{
		Relazione r = new Relazione(SitDPersona.class,IdExtDPersona);
		this.idExtDPersona = r;	
	}
	
}
