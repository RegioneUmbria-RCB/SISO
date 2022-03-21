package it.webred.rulengine.dwh.table;

import it.webred.rulengine.dwh.def.Identificativo;
import it.webred.rulengine.dwh.def.Tabella;

public class SitEnteSorgente implements Tabella
{
	private Identificativo id = new Identificativo();



	public Identificativo getId()
	{
		return id;
	}



	public void setId(Identificativo id)
	{
		this.id = id;
	}






	

}
