package it.webred.ct.data.access.aggregator.elaborazioni.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ElaborazioniDataOut extends CeTBaseObject{
	
	private static final long serialVersionUID = 1L;
	private Object obj1;
	private Object obj2;

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}

	public Object getObj1() {
		return obj1;
	}
	
	

}
