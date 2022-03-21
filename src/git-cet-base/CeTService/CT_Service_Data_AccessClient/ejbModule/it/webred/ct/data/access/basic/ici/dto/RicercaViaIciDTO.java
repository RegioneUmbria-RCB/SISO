package it.webred.ct.data.access.basic.ici.dto;

import it.webred.ct.data.model.ici.SitTIciVia;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class RicercaViaIciDTO extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private SitTIciVia datiVia;
	public SitTIciVia getDatiVia() {
		return datiVia;
	}
	public void setDatiVia(SitTIciVia datiVia) {
		this.datiVia = datiVia;
	}
	
}
   