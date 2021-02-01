package it.webred.ss.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class BaseDTO extends CeTBaseObject {

	private Object obj;
	private Object obj2;
	private Object obj3;
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

	public Object getObj2() {
		return obj2;
	}

	public Object getObj3() {
		return obj3;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}

}
