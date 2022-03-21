package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.TipoXml;

import java.sql.Blob;

public class SitDCivico extends TabellaDwh implements EseguiQueryInDisabilitaStorico
{

	private TipoXml civicoComposto;
	private String cap;
	private Relazione idExtDSezioneElettorale = new Relazione(SitDSezioneElettorale.class,new ChiaveEsterna());
	private Relazione idExtDVia = new Relazione(SitDVia.class,new ChiaveEsterna());
	private Relazione idExtDFrazione = new Relazione(SitDFrazione.class,new ChiaveEsterna());
	
	
	public String getCap()
	{
		return cap;
	}

	public void setCap(String cap)
	{
		this.cap = cap;
	}



	@Override
	public String getValueForCtrHash()
	{
		return cap+idExtDSezioneElettorale.getRelazione().getValore()+idExtDFrazione.getRelazione().getValore()+idExtDVia.getRelazione().getValore();
	}


	public Relazione getIdExtDFrazione()
	{
		return idExtDFrazione;
	}

	public Relazione getIdExtDSezioneElettorale()
	{
		return idExtDSezioneElettorale;
	}

	public Relazione getIdExtDVia()
	{
		return idExtDVia;
	}

	public void setIdExtDFrazione(ChiaveEsterna idExtDFrazione)
	{
		Relazione r = new Relazione(SitDFrazione.class,idExtDFrazione);
		this.idExtDFrazione = r;
	}

	public void setIdExtDSezioneElettorale(ChiaveEsterna idExtDSezioneElettorale)
	{
		Relazione r = new Relazione(SitDSezioneElettorale.class,idExtDSezioneElettorale);
		this.idExtDSezioneElettorale = r;	
	}

	public void setIdExtDVia(ChiaveEsterna idExtDVia)
	{
		Relazione r = new Relazione(SitDVia.class,idExtDVia);
		this.idExtDVia = r;	
	}

	public TipoXml getCivicoComposto()
	{
		return civicoComposto;
	}

	public void setCivicoComposto(TipoXml civicoComposto)
	{
		this.civicoComposto = civicoComposto;
	}



}
