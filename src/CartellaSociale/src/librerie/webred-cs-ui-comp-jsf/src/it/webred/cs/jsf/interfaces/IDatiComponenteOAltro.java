package it.webred.cs.jsf.interfaces;

import java.util.ArrayList;

import javax.faces.model.SelectItem;

public interface IDatiComponenteOAltro {

	public Long getSoggettoId();
	
	public ArrayList<SelectItem> getLstParenti();
	public void aggiornaComponente();
	
}
