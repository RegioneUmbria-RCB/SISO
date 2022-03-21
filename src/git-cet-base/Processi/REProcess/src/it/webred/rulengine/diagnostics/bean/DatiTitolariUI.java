package it.webred.rulengine.diagnostics.bean;

import java.io.Serializable;
import java.util.List;

public class DatiTitolariUI extends DatiUiBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<String> listaIdCiv;
	private List<TitolareBean> listaTitolari;
	private boolean unTitolareRisiede;
	private boolean esisteTitolarePersonaFisica;
	private Long numFamiglieTitRes;
	
	
	public List<String> getListaIdCiv() {
		return listaIdCiv;
	}
	public void setListaIdCiv(List<String> listaIdCiv) {
		this.listaIdCiv = listaIdCiv;
	}
	public List<TitolareBean> getListaTitolari() {
		return listaTitolari;
	}
	public void setListaTitolari(List<TitolareBean> listaTitolari) {
		this.listaTitolari = listaTitolari;
	}
	public boolean isUnTitolareRisiede() {
		return unTitolareRisiede;
	}
	public void setUnTitolareRisiede(boolean unTitolareRisiede) {
		this.unTitolareRisiede = unTitolareRisiede;
	}
	public boolean isEsisteTitolarePersonaFisica() {
		return esisteTitolarePersonaFisica;
	}
	public void setEsisteTitolarePersonaFisica(boolean esisteTitolarePersonaFisica) {
		this.esisteTitolarePersonaFisica = esisteTitolarePersonaFisica;
	}
	public Long getNumFamiglieTitRes() {
		return numFamiglieTitRes;
	}
	public void setNumFamiglieTitRes(Long numFamiglieTitRes) {
		this.numFamiglieTitRes = numFamiglieTitRes;
	}
	

}
