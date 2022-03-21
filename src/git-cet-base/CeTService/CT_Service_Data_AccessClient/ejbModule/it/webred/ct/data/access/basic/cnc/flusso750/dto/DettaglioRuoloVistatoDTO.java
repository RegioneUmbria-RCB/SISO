package it.webred.ct.data.access.basic.cnc.flusso750.dto;

import it.webred.ct.data.model.cnc.flusso750.VArticolo;
import it.webred.ct.data.model.cnc.flusso750.VCartella;
import it.webred.ct.data.model.cnc.flusso750.VUlterioriBeneficiari;

import java.io.Serializable;
import java.util.List;

public class DettaglioRuoloVistatoDTO implements Serializable {

	private AnagraficaSoggettiDTO anagrafica;
	private VCartella cartella;	
	private List<VArticolo> articoliList;
	private List<VUlterioriBeneficiari> ulterioriBeneficiari;

	public AnagraficaSoggettiDTO getAnagrafica() {
		return anagrafica;
	}

	public void setAnagrafica(AnagraficaSoggettiDTO anagrafica) {
		this.anagrafica = anagrafica;
	}

	public List<VArticolo> getArticoliList() {
		return articoliList;
	}

	public void setArticoliList(List<VArticolo> articoliList) {
		this.articoliList = articoliList;
	}

	public VCartella getCartella() {
		return cartella;
	}

	public void setCartella(VCartella cartella) {
		this.cartella = cartella;
	}

	public List<VUlterioriBeneficiari> getUlterioriBeneficiari() {
		return ulterioriBeneficiari;
	}

	public void setUlterioriBeneficiari(
			List<VUlterioriBeneficiari> ulterioriBeneficiari) {
		this.ulterioriBeneficiari = ulterioriBeneficiari;
	}
	
	  
}
