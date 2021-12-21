package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;

public interface IDatiPor {
	
	public List<SelectItem> getLstProgetti();
	
	public List<SelectItem> getLstSottocorsi();

	public void onChangeProgetto();

	public void elimina();

	//public boolean salva(Long id);
	
}
