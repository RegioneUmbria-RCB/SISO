package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;

public class SitDFamiglia extends TabellaDwh implements EseguiQueryInDisabilitaStorico
{

	private String tipofami;
	private String denominazione;
	private Relazione idExtDCivico = new Relazione(SitDCivico.class,new ChiaveEsterna());
	
	public String getDenominazione()
	{
		return denominazione;
	}


	public void setDenominazione(String denominazione)
	{
		this.denominazione = denominazione;
	}


	public String getTipofami()
	{
		return tipofami;
	}


	public void setTipofami(String tipofami)
	{
		this.tipofami = tipofami;
	}


	@Override
	public String getValueForCtrHash()
	{
		return tipofami + denominazione + idExtDCivico.getRelazione().getValore();
	}


	public Relazione getIdExtDCivico()
	{
		return idExtDCivico;
	}
	
	public void setIdExtDCivico(ChiaveEsterna IdExtDCivico)
	{
		Relazione r = new Relazione(SitDPersona.class,IdExtDCivico);
		this.idExtDCivico = r;	
	}
}
