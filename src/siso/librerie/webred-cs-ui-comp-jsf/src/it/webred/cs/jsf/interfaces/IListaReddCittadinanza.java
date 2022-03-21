package it.webred.cs.jsf.interfaces;

import org.primefaces.model.LazyDataModel;

public interface IListaReddCittadinanza {

	public boolean isRenderNuovaCartella();

	public String getFonte();

	public LazyDataModel<?> getLazyListaReddCittadinanzaModel();
}
