package it.webred.cs.csa.ejb.dto;

import it.webred.cs.data.model.CsDPai;

public class PaiDTOExt extends BaseDTO{
	private CsDPai pai;
	private boolean containsDoc;
	private int countDoc;
	
	public PaiDTOExt(CsDPai pai) {
		this.pai = pai;
		this.containsDoc = pai.getCsDDiario().getCsDDiarioDocs().size() > 0;
		this.countDoc = pai.getCsDDiario().getCsDDiarioDocs().size();
	}

	public CsDPai getPai() {
		return pai;
	}

	public boolean isContainsDoc() {
		return containsDoc;
	}

	public int getCountDoc() {
		return countDoc;
	}
	
}

