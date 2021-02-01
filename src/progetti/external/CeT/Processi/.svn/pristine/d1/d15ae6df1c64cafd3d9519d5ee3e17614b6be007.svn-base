package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;

public class SitDPersFam extends TabellaDwh
{

	private String relazPar;
	private Relazione idExtDFamiglia = new Relazione(SitDPersona.class,new ChiaveEsterna());
	private Relazione idExtDPersona = new Relazione(SitDPersFam.class,new ChiaveEsterna());
	
	
	@Override
	public String getValueForCtrHash()
	{
		return relazPar + idExtDFamiglia.getRelazione().getValore() + idExtDPersona.getRelazione().getValore();
	}


	public String getRelazPar()
	{
		return relazPar;
	}


	public void setRelazPar(String relazPar)
	{
		this.relazPar = relazPar;
	}


	public Relazione getIdExtDFamiglia()
	{
		return idExtDFamiglia;
	}


	public Relazione getIdExtDPersona()
	{
		return idExtDPersona;
	}

	public void setIdExtDFamiglia(ChiaveEsterna IdExtDFamiglia)
	{
		Relazione r = new Relazione(SitDPersona.class,IdExtDFamiglia);
		this.idExtDFamiglia = r;	
	}

	public void setIdExtDPersona(ChiaveEsterna IdExtDPersona)
	{
		Relazione r = new Relazione(SitDPersona.class,IdExtDPersona);
		this.idExtDPersona = r;	
	}
	
}
