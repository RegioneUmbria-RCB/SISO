package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiComponenteOAltro {

	public Long getSoggettoId();
	
	public List<SelectItem> getLstParenti();
	public void aggiornaComponente();
	
}
