package it.webred.rulengine.brick.reperimento.executor.bean;

import java.io.Serializable;

public class Listap implements Serializable {
	
	private String codLista;
	private String fkAmComune;
	private Long fkAmFonte;
	
	private boolean execSinProc;
	
	
	
	public String getCodLista() {
		return codLista;
	}

	public void setCodLista(String codLista) {
		this.codLista = codLista;
	}

	public String getFkAmComune() {
		return fkAmComune;
	}

	public void setFkAmComune(String fkAmComune) {
		this.fkAmComune = fkAmComune;
	}

	public Long getFkAmFonte() {
		return fkAmFonte;
	}

	public void setFkAmFonte(Long fkAmFonte) {
		this.fkAmFonte = fkAmFonte;
	}

	public boolean isExecSinProc() {
		return execSinProc;
	}

	public void setExecSinProc(boolean execSinProc) {
		this.execSinProc = execSinProc;
	}

	
	
	
}
