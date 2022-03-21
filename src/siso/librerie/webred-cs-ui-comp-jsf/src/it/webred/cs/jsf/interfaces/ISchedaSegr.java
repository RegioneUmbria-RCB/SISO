package it.webred.cs.jsf.interfaces;

import java.util.List;

import it.webred.cs.json.ISchedaValutazione;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ss.ejb.dto.SchedaUdcDTO;

public interface ISchedaSegr {

  	public Object getSsScheda();
	
	public Object getSsSchedaSegnalato();
	
	public AmTabComuni getComuneSegnalante();
	
	public String getStatoCivileSegnalante();
	
	public String getStatoCivileRiferimento();
	
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

	public SchedaUdcDTO getScheda();

}
