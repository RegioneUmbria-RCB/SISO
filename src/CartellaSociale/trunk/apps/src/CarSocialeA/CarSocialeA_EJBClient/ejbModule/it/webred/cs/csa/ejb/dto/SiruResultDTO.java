package it.webred.cs.csa.ejb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import it.webred.cs.data.model.CsIInterventoPrFseSiru;

public class SiruResultDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private CsIInterventoPrFseSiru siru;
	private List<String> errori = new ArrayList<String>();
	public CsIInterventoPrFseSiru getSiru() {
		return siru;
	}
	public List<String> getErrori() {
		return errori;
	}
	public void setSiru(CsIInterventoPrFseSiru siru) {
		this.siru = siru;
	}
	public void setErrori(List<String> errori) {
		this.errori = errori;
	}
	
}
