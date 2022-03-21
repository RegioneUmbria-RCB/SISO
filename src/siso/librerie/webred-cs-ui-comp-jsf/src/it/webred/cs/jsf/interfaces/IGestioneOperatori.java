package it.webred.cs.jsf.interfaces;

import it.webred.cs.jsf.bean.DatiOperatore;

import java.util.List;

public interface IGestioneOperatori {
	
	public String getWidgetVar();
	
	public void ricercaOperatore();
	
	public void salvaOperatore();
	
	public void aggiungiOperatoreSettore();
	
	public void eliminaOperatoreSettore();
	
	public void disattivaOperatoreSettore();

	public void attivaOperatoreSettore();
	
	public List<DatiOperatore> getLstOperatoriFiltrati();

}
