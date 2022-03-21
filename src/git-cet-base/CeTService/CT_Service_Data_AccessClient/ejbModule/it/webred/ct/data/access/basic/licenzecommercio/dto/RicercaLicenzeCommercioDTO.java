package it.webred.ct.data.access.basic.licenzecommercio.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaLicenzeCommercioDTO extends CeTBaseObject{
	private String id;
	private String idExtVia;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdExtVia() {
		return idExtVia;
	}

	public void setIdExtVia(String idExtVia) {
		this.idExtVia = idExtVia;
	}
	
}
