package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.ct.config.model.AmTabComuni;

public interface ISchedaSegr {

	public Object getSsScheda();
	
	public Object getSsSchedaSegnalato();
	
	public AmTabComuni getComuneSegnalante();
	
	public String getStatoCivileSegnalante();
	
	public String getStatoCivileRiferimento();
	
	@SuppressWarnings("rawtypes")
	public List getListaMotivazioni();
	
	@SuppressWarnings("rawtypes")
	public List getListaDiari();
	
	@SuppressWarnings("rawtypes")
	public List getListaInterventi();
	
	@SuppressWarnings("rawtypes")
	public List getListaInterventiEcon();

	public String getRelazioneSegnalante();
	
	public String getEnteSegnalante();

	public String getRelazioneRiferimento();

	public String getInviatoDaAccesso();
	
	public boolean isHideSegnalante();
}
