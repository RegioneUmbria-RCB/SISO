package it.webred.ct.data.access.basic.c336.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class RicercaStatisticheDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;
	
	private Boolean supervisore;

	
	public void setSupervisore(Boolean supervisore) {
		this.supervisore = supervisore;
	
	}

	public Boolean getSupervisore() {
		return supervisore;
	}
	
}
