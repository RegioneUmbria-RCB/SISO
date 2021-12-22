package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;


public interface IListaSchedeSegr {

	public boolean isRenderNuovaCartella();
	
	public boolean isRenderCaricaCartella();

	public boolean isRenderVista();

	public boolean isRenderRespinta();

	public List<SelectItem> getLstProvenienza();

	public void respingiScheda();

	public void vediScheda();
}
