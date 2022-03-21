package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.cs.data.model.CsASoggettoLAZY;

public interface IAltriSoggetti {
	
	public void setListaAltriSoggetti(List<CsASoggettoLAZY> listaAltriSoggetti);
	public String getLabelSoggetto();
	public boolean isShowPanel();

}
