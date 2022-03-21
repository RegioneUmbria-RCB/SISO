package it.webred.cs.csa.web.manbean.fascicolo.initialize.bean;

import it.webred.cs.data.model.CsAComponente;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;

import java.util.List;

public class InitAltriSoggettiBean {

	List<CsAComponente> listaComponenti;
	
	List<FamiliareDettaglio> listaFamiglia_anagrafe;

	public List<CsAComponente> getListaComponenti() {
		return listaComponenti;
	}

	public void setListaComponenti(List<CsAComponente> listaComponenti) {
		this.listaComponenti = listaComponenti;
	}

	public List<FamiliareDettaglio> getListaFamiglia_anagrafe() {
		return listaFamiglia_anagrafe;
	}

	public void setListaFamiglia_anagrafe(List<FamiliareDettaglio> listaFamiglia_anagrafe) {
		this.listaFamiglia_anagrafe = listaFamiglia_anagrafe;
	}
	

	
	
}
