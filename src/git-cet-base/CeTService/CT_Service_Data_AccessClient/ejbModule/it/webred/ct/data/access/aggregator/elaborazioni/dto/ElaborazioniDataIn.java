package it.webred.ct.data.access.aggregator.elaborazioni.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class ElaborazioniDataIn extends CeTBaseObject implements Serializable{
	private static final long serialVersionUID = 1L;
	private Object obj;
	private Object obj1;
	private Object obj2;
	private Object obj3;

	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public void setObj1(Object obj1) {
		this.obj1 = obj1;
	}

	public Object getObj1() {
		return obj1;
	}

	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}

	public Object getObj3() {
		return obj3;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public Object getObj2() {
		return obj2;
	}
	
}
