package it.webred.ct.data.access.basic.docfa.dto;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;
import it.webred.ct.support.datarouter.CeTBaseObject;

public class DocfaSearchCriteria extends CeTBaseObject{

	private static final long serialVersionUID = 1L;
	private RicercaOggettoDocfaDTO ricercaOggetto;
	private RicercaSoggettoDTO ricercaDichiarante;
	
	public RicercaOggettoDocfaDTO getRicercaOggetto() {
		return ricercaOggetto;
	}
	public void setRicercaOggetto(RicercaOggettoDocfaDTO ricercaOggetto) {
		this.ricercaOggetto = ricercaOggetto;
	}
	public RicercaSoggettoDTO getRicercaDichiarante() {
		return ricercaDichiarante;
	}
	public void setRicercaDichiarante(RicercaSoggettoDTO ricercaDichiarante) {
		this.ricercaDichiarante = ricercaDichiarante;
	}
}
