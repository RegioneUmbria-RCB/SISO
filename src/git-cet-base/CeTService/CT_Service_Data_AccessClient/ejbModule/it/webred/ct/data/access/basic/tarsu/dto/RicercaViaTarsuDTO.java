package it.webred.ct.data.access.basic.tarsu.dto;

import it.webred.ct.data.model.tarsu.SitTTarVia;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class RicercaViaTarsuDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private SitTTarVia datiVia;
	
	public RicercaViaTarsuDTO(){
		datiVia = new SitTTarVia();
	}
	
	public SitTTarVia getDatiVia() {
		return datiVia;
	}
	
	public void setDatiVia(SitTTarVia datiVia) {
		this.datiVia = datiVia;
	}

}
