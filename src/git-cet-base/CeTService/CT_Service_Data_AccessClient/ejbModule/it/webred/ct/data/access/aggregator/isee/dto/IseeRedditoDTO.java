package it.webred.ct.data.access.aggregator.isee.dto;

import it.webred.ct.data.model.redditi.RedRedditiDichiarati;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class IseeRedditoDTO extends CeTBaseObject implements Serializable {
		
	private RedRedditiDichiarati reddito;
	private String descrizione;
	private String tipoModello;
	
	public IseeRedditoDTO() {
		super();
	}

	public RedRedditiDichiarati getReddito() {
		return reddito;
	}

	public void setReddito(RedRedditiDichiarati reddito) {
		this.reddito = reddito;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTipoModello() {
		return tipoModello;
	}

	public void setTipoModello(String tipoModello) {
		this.tipoModello = tipoModello;
	}

}
