package it.webred.ct.config.parameters.dto;

import java.io.Serializable;
import java.util.List;

import javax.faces.model.SelectItem;

public class AssistedValueDTO implements Serializable{

	private String label;
	private List<SelectItem> listaItem;
	private String selected;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<SelectItem> getListaItem() {
		return listaItem;
	}
	public void setListaItem(List<SelectItem> listaItem) {
		this.listaItem = listaItem;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
}
