package it.webred.cs.csa.web.manbean.scheda.operatori;

import it.webred.cs.csa.web.manbean.scheda.SchedaValiditaCompUtils;
//SISO-812
public class SecondoLivelloComp extends SchedaValiditaCompUtils{

	private String organizzazione;
	private String settore;
	
	public String getOrganizzazione() {
		return organizzazione;
	}
	public String getSettore() {
		return settore;
	}
	public void setOrganizzazione(String organizzazione) {
		this.organizzazione = organizzazione;
	}
	public void setSettore(String settore) {
		this.settore = settore;
	}	
	
}
