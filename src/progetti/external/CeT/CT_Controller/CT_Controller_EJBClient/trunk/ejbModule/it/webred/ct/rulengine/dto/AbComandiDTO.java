package it.webred.ct.rulengine.dto;

import it.webred.ct.config.model.AmComune;
import it.webred.ct.rulengine.controller.model.RCommand;
import it.webred.ct.rulengine.controller.model.REnteEsclusioni;

import java.io.Serializable;
import java.util.List;

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
