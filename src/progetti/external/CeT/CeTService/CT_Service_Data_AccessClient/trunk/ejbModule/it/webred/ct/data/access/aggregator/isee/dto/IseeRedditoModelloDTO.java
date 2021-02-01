package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.util.List;

public class IseeRedditoModelloDTO extends CeTBaseObject implements Serializable {
		
	private List<IseeRedditoDTO> listaRedditi;
	private String descrizione;
	
	public IseeRedditoModelloDTO() {
		super();
	}

	public List<IseeRedditoDTO> getListaRedditi() {
		return listaRedditi;
	}

	public void setListaRedditi(List<IseeRedditoDTO> listaRedditi) {
		this.listaRedditi = listaRedditi;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
