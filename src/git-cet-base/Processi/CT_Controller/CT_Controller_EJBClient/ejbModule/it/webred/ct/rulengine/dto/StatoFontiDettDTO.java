package it.webred.ct.rulengine.dto;

import it.webred.ct.data.model.processi.SitSintesiProcessi;
import it.webred.ct.rulengine.controller.model.RTracciaForniture;
import it.webred.ct.rulengine.controller.model.RTracciaStati;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StatoFontiDettDTO implements Serializable {
	
	private RTracciaStati rTracciaStati;
	private String dataAgg;
	private List<SitSintesiProcessi> listaSintesiProcessi;
	private List<RTracciaForniture> listaForniture;
	
	public RTracciaStati getrTracciaStati() {
		return rTracciaStati;
	}
	public void setrTracciaStati(RTracciaStati rTracciaStati) {
		this.rTracciaStati = rTracciaStati;
	}
	public List<SitSintesiProcessi> getListaSintesiProcessi() {
		return listaSintesiProcessi;
	}
	public void setListaSintesiProcessi(
			List<SitSintesiProcessi> listaSintesiProcessi) {
		this.listaSintesiProcessi = listaSintesiProcessi;
	}
	public String getDataAgg() {
		return dataAgg;
	}
	public void setDataAgg(String dataAgg) {
		this.dataAgg = dataAgg;
	}
	public List<RTracciaForniture> getListaForniture() {
		return listaForniture;
	}
	public void setListaForniture(List<RTracciaForniture> listaForniture) {
		this.listaForniture = listaForniture;
	}
	
}
