package it.webred.ct.data.access.basic.cosap.dto;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;

import java.io.Serializable;

public class RicercaSoggettoCosapDTO extends RicercaSoggettoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String idSoggCosap;
	
	private String idExtSoggCosap;
	
	public String getIdSoggCosap() {
		return idSoggCosap;
	}
	public void setIdSoggCosap(String idSoggCosap) {
		this.idSoggCosap = idSoggCosap;
	}
	public String getIdExtSoggCosap() {
		return idExtSoggCosap;
	}
	public void setIdExtSoggCosap(String idExtSoggCosap) {
		this.idExtSoggCosap = idExtSoggCosap;
	}
		
		
}
