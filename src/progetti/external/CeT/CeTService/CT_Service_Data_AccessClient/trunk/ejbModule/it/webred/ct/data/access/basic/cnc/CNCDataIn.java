package it.webred.ct.data.access.basic.cnc;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class CNCDataIn  extends CeTBaseObject implements Serializable {

	private Object obj;

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
}
