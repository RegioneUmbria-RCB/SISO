package it.webred.ct.data.access.basic.ici.dto;

import it.webred.ct.data.access.basic.common.dto.RicercaSoggettoDTO;

import java.io.Serializable;
import java.util.Date;

public class RicercaSoggettoIciDTO extends RicercaSoggettoDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idSoggIci;
	
	public RicercaSoggettoIciDTO() {
		super();
	}
		
	public String getIdSoggIci() {
		return idSoggIci;
	}

	public void setIdSoggIci(String idSoggIci) {
		this.idSoggIci = idSoggIci;
	}

	
	
	
}
