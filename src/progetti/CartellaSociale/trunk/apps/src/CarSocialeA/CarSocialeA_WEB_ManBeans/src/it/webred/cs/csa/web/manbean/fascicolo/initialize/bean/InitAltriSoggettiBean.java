package it.webred.cs.csa.web.manbean.fascicolo.initialize.bean;

import java.util.List;

import it.webred.cs.data.model.CsAFamigliaGruppo;
import it.webred.ct.data.model.anagrafe.SitDPersona;

public class InitAltriSoggettiBean {

	CsAFamigliaGruppo famigliaGruppo;
	
	List<SitDPersona> listaFamiglia_anagrafe;

	public CsAFamigliaGruppo getFamigliaGruppo() {
		return famigliaGruppo;
	}

	public void setFamigliaGruppo(CsAFamigliaGruppo famigliaGruppo) {
		this.famigliaGruppo = famigliaGruppo;
	}

	public List<SitDPersona> getListaFamiglia_anagrafe() {
		return listaFamiglia_anagrafe;
	}

	public void setListaFamiglia_anagrafe(List<SitDPersona> listaFamiglia_anagrafe) {
		this.listaFamiglia_anagrafe = listaFamiglia_anagrafe;
	}
	

	
	
}
