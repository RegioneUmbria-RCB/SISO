package it.webred.cs.json.abitazione;

import it.webred.cs.json.ISchedaValutazione;

public interface IAbitazione extends ISchedaValutazione {
	
	public String getTipoAbitazione();
	public String getNumVani();
	public String getTitoloGodimento();
	public String getNote();
	public String getProprietarioGestore();
	//#ROMACAPITALE
	public String getStruttura();
	public void setStruttura(String struttura);
	public String getArea();
	public void setArea(String area);
	public String getUnitaAbitativa();
	public void setUnitaAbitativa(String unitaAbitativa);
	public boolean isVisualizzaStruttura();
}

