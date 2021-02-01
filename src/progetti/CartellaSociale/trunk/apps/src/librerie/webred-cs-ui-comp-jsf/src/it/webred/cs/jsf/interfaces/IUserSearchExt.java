package it.webred.cs.jsf.interfaces;

import java.util.List;

public interface IUserSearchExt extends IUserSearch {

	
	public List<?> getLstSoggetti() ;
	
	void loadListaSoggetti();

	void clearParameters();

}
