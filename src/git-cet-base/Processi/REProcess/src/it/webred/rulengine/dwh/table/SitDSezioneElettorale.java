package it.webred.rulengine.dwh.table;

public class SitDSezioneElettorale extends TabellaDwh implements EseguiQueryInDisabilitaStorico
{

	private String sedime;
	private String via;
	private String tipo;
	
	@Override
	public String getValueForCtrHash()
	{
		return sedime+via+tipo;
	}

	public String getSedime()
	{
		return sedime;
	}

	public void setSedime(String sedime)
	{
		this.sedime = sedime;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public String getVia()
	{
		return via;
	}

	public void setVia(String via)
	{
		this.via = via;
	}

}
