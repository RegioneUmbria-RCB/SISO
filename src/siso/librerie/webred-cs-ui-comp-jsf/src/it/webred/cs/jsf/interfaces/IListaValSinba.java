package it.webred.cs.jsf.interfaces;

import it.webred.cs.data.model.CsDSinba;

public interface IListaValSinba {

	public void esportaStampa(CsDSinba row) throws Exception;
	public boolean isReadOnly();
	public Boolean getEsisteStorico();
}
