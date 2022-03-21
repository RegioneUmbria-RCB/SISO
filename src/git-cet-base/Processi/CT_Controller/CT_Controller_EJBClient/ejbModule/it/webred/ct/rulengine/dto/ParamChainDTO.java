package it.webred.ct.rulengine.dto;

import java.io.Serializable;

public class ParamChainDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String nomeParam;
	private String valoreParam;
	
	public ParamChainDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public ParamChainDTO(String nomeParam, String valoreParam) {
		super();
		this.nomeParam = nomeParam;
		this.valoreParam = valoreParam;
	}



	public String getNomeParam() {
		return nomeParam;
	}

	public void setNomeParam(String nomeParam) {
		this.nomeParam = nomeParam;
	}

	public String getValoreParam() {
		return valoreParam;
	}

	public void setValoreParam(String valoreParam) {
		this.valoreParam = valoreParam;
	}
	
	
}
