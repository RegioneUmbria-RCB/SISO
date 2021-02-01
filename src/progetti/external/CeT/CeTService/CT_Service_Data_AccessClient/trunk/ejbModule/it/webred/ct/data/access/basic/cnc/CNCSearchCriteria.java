package it.webred.ct.data.access.basic.cnc;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class CNCSearchCriteria extends CeTBaseObject implements Serializable {
	
	private int executeSearch;

	
	
	public int getExecuteSearch() {
		return executeSearch;
	}

	public void setExecuteSearch(int executeSearch) {
		this.executeSearch = executeSearch;
	}
	
	
	public boolean executeSearch() {
		return executeSearch == 1;
	}
}
