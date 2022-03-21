package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsDValutazione;

public interface IListaValMultidimensionale {

	public void onViewBarthel(CsDValutazione scheda) throws Exception;
	public void esportaStampa(CsDValutazione row) throws Exception;
	public boolean isReadOnly();
}
