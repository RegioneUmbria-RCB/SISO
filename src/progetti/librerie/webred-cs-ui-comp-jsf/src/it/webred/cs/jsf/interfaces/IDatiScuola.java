package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.fascicolo.scuola.ListaDatiScuolaDTO;
import it.webred.cs.data.model.CsDScuola;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiScuola {
	
	public void initializeData();
	public void nuovo();
	public void carica();
	public void salva();
	public void elimina();
	public Long getIdCaso();
	public String getComune();
	public int getIdxSelected();
	public List<ListaDatiScuolaDTO> getListaScuole();
	public List<SelectItem> getListaAnni();
	public List<SelectItem> getListaTipoScuole();
	public List<SelectItem> getListaNomi();
	public List<SelectItem> getListaGradi();
	public List<SelectItem> getListaProgetti();
	public CsDScuola getScuola();
	public boolean isRenderScuole();
	public List<SelectItem> getListaComuni();
	public void aggiornaAltroNome();
}
