package it.webred.ct.aggregator.ejb.imu.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

public class ImuRichiestaDatiDTO extends CeTBaseObject {
	
	private static final long serialVersionUID = 1L;
	private String codfisc;
	
	public String getCodfisc() {
		return codfisc;
	}
	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}
  
}
