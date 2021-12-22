package it.webred.cs.csa.web.manbean.fascicolo.initialize.bean;

import it.webred.cs.data.model.CsAComponente;
import it.webred.ct.data.model.anagrafe.SitDPersona;

import java.util.List;

public class InitAltriSoggettiBean {

	List<CsAComponente> listaComponenti;
	
	List<SitDPersona> listaFamiglia_anagrafe;

	public List<CsAComponente> getListaComponenti() {
		return listaComponenti;
	}

	public void setListaComponenti(List<CsAComponente> listaComponenti) {
		this.listaComponenti = listaComponenti;
	}

	public List<SitDPersona> getListaFamiglia_anagrafe() {
		return listaFamiglia_anagrafe;
	}

	public void setListaFamiglia_anagrafe(List<SitDPersona> listaFamiglia_anagrafe) {
		this.listaFamiglia_anagrafe = listaFamiglia_anagrafe;
	}
	

	
	
}
