package it.webred.cs.json.abitazione;

import it.webred.cs.json.ISchedaValutazione;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IAbitazione extends ISchedaValutazione {
	
	public String getTipoAbitazione();
	public String getNumVani();
	public String getTitoloGodimento();
	public String getNote();
	public String getProprietarioGestore();
	
}

