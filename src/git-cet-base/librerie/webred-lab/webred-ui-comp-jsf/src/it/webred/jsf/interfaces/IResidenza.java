package it.webred.jsf.interfaces;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public interface IResidenza {
	
	public List<?> getLstIndirizziForKey(String key);
	public void salvaIndirizzi();
	public ArrayList<SelectItem> getLstTipiIndirizzo();
	public void annullaIndirizzo();
	public void eliminaIndirizzo();

}
