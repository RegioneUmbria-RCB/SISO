package it.umbriadigitale.soclav.wsclient;

import java.io.Serializable;

public class EsitoOp extends EsitoType implements Serializable {

	
	private Exception ex;


	private String contenutoSap;
	
	
	public Exception getEx() {
		return ex;
	}

	public void setEx(Exception ex) {
		this.ex = ex;
	}

	public String getContenutoSap() {
		return contenutoSap;
	}

	public void setContenutoSap(String contenutoSap) {
		this.contenutoSap = contenutoSap;
	}

	
	
}
