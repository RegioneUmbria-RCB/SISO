package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.cs.json.ISchedaValutazione;
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
    
    @SuppressWarnings("rawtypes")
    public List getListaRiferimenti();
	
	public String getRelazioneSegnalante();
	
	public String getEnteSegnalante();

	public String getRelazioneRiferimento();

	public String getInviatoDaAccesso();
	
	public boolean isHideSegnalante();

	public String getLabelChiusura();

	public String getLabelInterventi();

	public String getLabelMotivazione();

	public String getLabelRiferimento();

	public String getLabelSegnalato();

	public String getLabelSegnalante();

	public String getLabelAccesso();

	public boolean servizioRendered(ISchedaValutazione schedaValutazione, String tipo);

}
