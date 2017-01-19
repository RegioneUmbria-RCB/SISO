package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StatoCartellaDTO extends CeTBaseObject  {
	private static final long serialVersionUID = 1L;
	
	private String nomeStato;
	private Date dataPassaggioStato;
	private String uNameResponsabile;
	private List<String> areeTarget;
	
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
	
}
