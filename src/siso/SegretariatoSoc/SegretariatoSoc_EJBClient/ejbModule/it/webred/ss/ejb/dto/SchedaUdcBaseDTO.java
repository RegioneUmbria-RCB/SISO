package it.webred.ss.ejb.dto;

import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaSegnalato;

import java.io.Serializable;

public class SchedaUdcBaseDTO implements Serializable  {

	private SsScheda scheda;
	private SsSchedaSegnalato segnalato;
	private Boolean privacySottoscritta;
	
	public SsScheda getScheda() {
		return scheda;
	}
	public void setScheda(SsScheda scheda) {
		this.scheda = scheda;
	}
	public SsSchedaSegnalato getSegnalato() {
		return segnalato;
	}
	public void setSegnalato(SsSchedaSegnalato segnalato) {
		this.segnalato = segnalato;
	}
	public String getUfficioPuntoContatto(){
		return scheda.getAccesso().getSsRelUffPcontOrg().getSsUfficio() != null ? scheda.getAccesso().getSsRelUffPcontOrg().getSsUfficio().getNome() : null;	
	}
	public String getCfSegnalato(){
		return segnalato.getAnagrafica().getCf();
	}
	public Boolean getPrivacySottoscritta() {
		return privacySottoscritta;
	}
	public void setPrivacySottoscritta(Boolean privacySottoscritta) {
		this.privacySottoscritta = privacySottoscritta;
	}
}
