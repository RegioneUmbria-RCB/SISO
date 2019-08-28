package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.bean.CasiCatSocialeBean;
import it.webred.cs.jsf.bean.CasiOperatoreBean;

import java.util.List;


public interface IListaCatSociali {
	
	public void caricaCaricoLavoro();
	
	public int getIdxSelected();
	
	public boolean isVisualCaricoLavoro();
	
	public List<CasiCatSocialeBean> getLstCasiCatSociale();
	
	public List<CasiOperatoreBean> getLstCasiOperatore();

}
