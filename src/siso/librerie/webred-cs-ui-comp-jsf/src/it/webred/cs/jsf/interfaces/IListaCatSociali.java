package it.webred.cs.jsf.interfaces;

import it.webred.cs.csa.ejb.dto.prospettoSintesi.CasiCatSocialeBean;
import it.webred.cs.csa.ejb.dto.prospettoSintesi.CasiOperatoreBean;

import java.util.List;


public interface IListaCatSociali {
	
	public void caricaCaricoLavoro();
	
	public int getIdxSelected();
	
	public boolean isVisualCaricoLavoro();
	
	public String getZonaSocialeLabel();
	
	public List<CasiCatSocialeBean> getLstCasiCatSociale();
	
	public List<CasiOperatoreBean> getLstCasiOperatore();

}
