package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StatoCartellaDTO extends CeTBaseObject  {
	private static final long serialVersionUID = 1L;
	
	private String denominazione;
	private Long codStato;
	private String nomeStato;
	private Date dataPassaggioStato;
	private String uNameResponsabile;
	private List<String> areeTarget;
	private String organizzazione;
	private String settore;
	
	public String getNomeStato() {
		return nomeStato;
	}
	public Date getDataPassaggioStato() {
		return dataPassaggioStato;
	}
	public void setNomeStato(String nomeStato) {
		this.nomeStato = nomeStato;
	}
	public void setDataPassaggioStato(Date dataPassaggioStato) {
		this.dataPassaggioStato = dataPassaggioStato;
	}
	public String getuNameResponsabile() {
		return uNameResponsabile;
	}
	public void setuNameResponsabile(String uNameResponsabile) {
		this.uNameResponsabile = uNameResponsabile;
	}
	public void addAreaTarget(String area){
		if(areeTarget==null)
			areeTarget = new ArrayList<String>();
		areeTarget.add(area);
	}
	public List<String> getAreeTarget() {
		return areeTarget;
	}
	public void setAreeTarget(List<String> areeTarget) {
		this.areeTarget = areeTarget;
	}
	public Long getCodStato() {
		return codStato;
	}
	public void setCodStato(Long codStato) {
		this.codStato = codStato;
	}
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
	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	
}
