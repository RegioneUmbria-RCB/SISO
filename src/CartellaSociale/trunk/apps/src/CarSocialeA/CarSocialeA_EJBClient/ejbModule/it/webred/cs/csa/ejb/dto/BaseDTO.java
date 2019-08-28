package it.webred.cs.csa.ejb.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;


public class BaseDTO extends CeTBaseObject  {
	private static final long serialVersionUID = 1L;
	
	private Object obj;
	private Object obj2;
	private Object obj3;
	private Object obj4;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public Object getObj2() {
		return obj2;
	}

	public void setObj2(Object obj2) {
		this.obj2 = obj2;
	}

	public Object getObj3() {
		return obj3;
	}

	public void setObj3(Object obj3) {
		this.obj3 = obj3;
	}

	public Object getObj4() {
		return obj4;
	}

	public void setObj4(Object obj4) {
		this.obj4 = obj4;
	}
	
}
