package it.webred.ss.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class BaseDTO extends CeTBaseObject {

	private Object obj;
	private Long organizzazione;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Long getOrganizzazione() {
		return organizzazione;
	}

	public void setOrganizzazione(Long organizzazione) {
		this.organizzazione = organizzazione;
	}

}
