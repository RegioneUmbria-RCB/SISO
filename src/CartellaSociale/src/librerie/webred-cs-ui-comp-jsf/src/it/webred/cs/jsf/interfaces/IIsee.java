package it.webred.cs.jsf.interfaces;

import it.webred.cs.json.dto.JsonBaseBean;
import it.webred.cs.json.isee.IIseeJson;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IIsee {
		
	public void initializeData();
	public void nuovo();
	public void carica();
	public void salva();
	public void elimina();
	public List<IIseeJson> getListaIsee();
	public List<SelectItem> getListaTipologie();
	public <T extends JsonBaseBean> T getIsee();
	public int getIdxSelected();
}
