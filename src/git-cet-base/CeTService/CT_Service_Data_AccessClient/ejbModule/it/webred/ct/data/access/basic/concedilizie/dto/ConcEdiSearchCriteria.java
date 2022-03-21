package it.webred.ct.data.access.basic.concedilizie.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ConcEdiSearchCriteria extends CeTBaseObject{

	private static final long serialVersionUID = 1L;
	private RicercaConcEdilizieDTO ricercaOggetto;
	private RicercaSoggettoConcEdilizieDTO ricercaSoggetto;
	public RicercaConcEdilizieDTO getRicercaOggetto() {
		return ricercaOggetto;
	}
	public void setRicercaOggetto(RicercaConcEdilizieDTO ricercaOggetto) {
		this.ricercaOggetto = ricercaOggetto;
	}
	public RicercaSoggettoConcEdilizieDTO getRicercaSoggetto() {
		return ricercaSoggetto;
	}
	public void setRicercaSoggetto(RicercaSoggettoConcEdilizieDTO ricercaSoggetto) {
		this.ricercaSoggetto = ricercaSoggetto;
	}
	
	
}
