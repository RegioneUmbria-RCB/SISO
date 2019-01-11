package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsDValutazione;

public interface IListaValSinba {

	public void esportaStampa(CsDValutazione row) throws Exception;
	public boolean isReadOnly();
}
