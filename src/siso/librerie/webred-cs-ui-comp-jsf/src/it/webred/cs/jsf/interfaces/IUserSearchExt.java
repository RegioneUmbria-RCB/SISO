package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.cs.jsf.bean.DatiUserSearchBean;

public interface IUserSearchExt {

	public List<?> getLstSoggetti() ;
	
	public void loadListaSoggetti();

	public void clearParameters();
	
	public DatiUserSearchBean getSelSoggetto();
}
