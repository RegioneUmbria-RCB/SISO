package it.webred.ct.rulengine.dto;

import java.io.Serializable;
import java.util.List;

import it.webred.ct.config.model.AmComune;

public class AbComandiDTO implements Serializable {
	
	private AmComune comune;
	private List<EnteEsclusioniDTO> listaEsclusioni;
	
	public AmComune getComune() {
		return comune;
	}
	public void setComune(AmComune comune) {
		this.comune = comune;
	}
	public List<EnteEsclusioniDTO> getListaEsclusioni() {
		return listaEsclusioni;
	}
	public void setListaEsclusioni(List<EnteEsclusioniDTO> listaEsclusioni) {
		this.listaEsclusioni = listaEsclusioni;
	}
	
}
