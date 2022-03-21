package it.webred.rulengine.brick.loadDwh.load.insertDwh.bean;

import java.util.LinkedHashMap;

public class RigaToInsert {
	private LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
	
	public RigaToInsert(LinkedHashMap<String, Object> params) {
		this.params = params;
	}

	public LinkedHashMap<String, Object> getParams() {
		return params;
	}

	
}
