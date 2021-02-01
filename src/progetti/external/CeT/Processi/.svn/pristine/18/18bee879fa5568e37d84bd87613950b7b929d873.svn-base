package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.Relazione;
import it.webred.rulengine.dwh.def.TipoXml;

import java.sql.Blob;

public class SitDUnione extends TabellaDwh
{

	private String generUnione;
	private DataDwh dataUnione;
	private Relazione idExtComune = new Relazione(SitComune.class,new ChiaveEsterna());
	private Relazione idExtProvincia = new Relazione(SitProvincia.class,new ChiaveEsterna());
	private Relazione idExtDPersona1 = new Relazione(SitDPersona.class,new ChiaveEsterna());
	private Relazione idExtDPersona2 = new Relazione(SitDPersona.class,new ChiaveEsterna());
	
	




	public Relazione getIdExtDPersona2() {
		return idExtDPersona2;
	}


	public void setIdExtDPersona2(ChiaveEsterna idExtDPersona2)
	{
		Relazione r = new Relazione(SitDPersona.class,idExtDPersona2);
		this.idExtDPersona2 = r;
	}


	public void setIdExtComune(Relazione idExtComune) {
		this.idExtComune = idExtComune;
	}


	public void setIdExtProvincia(Relazione idExtProvincia) {
		this.idExtProvincia = idExtProvincia;
	}


	public DataDwh getDataUnione() {
		return dataUnione;
	}


	public void setDataUnione(DataDwh dataUnione) {
		this.dataUnione = dataUnione;
	}


	public String getGenerUnione() {
		return generUnione;
	}


	public void setGenerUnione(String generUnione) {
		this.generUnione = generUnione;
	}


	@Override
	public String getValueForCtrHash()
	{
		return ""+idExtDPersona1.getRelazione().getValore()+idExtDPersona2.getRelazione().getValore();
	}


	public Relazione getIdExtComune()
	{
		return idExtComune;
	}

	public void setIdExtComune(ChiaveEsterna idExtComune)
	{
		Relazione r = new Relazione(SitComune.class,idExtComune);
		this.idExtComune = r;
	}

	public Relazione getIdExtProvincia()
	{
		return idExtProvincia;
	}

	public void setIdExtProvincia(ChiaveEsterna idExtProvincia)
	{
		Relazione r = new Relazione(SitProvincia.class,idExtProvincia);
		this.idExtProvincia = r;
	}





	public Relazione getIdExtDPersona1() {
		return idExtDPersona1;
	}


	public void setIdExtDPersona1(ChiaveEsterna idExtDPersona1)
	{
		Relazione r = new Relazione(SitDPersona.class,idExtDPersona1);
		this.idExtDPersona1 = r;
	}


}
