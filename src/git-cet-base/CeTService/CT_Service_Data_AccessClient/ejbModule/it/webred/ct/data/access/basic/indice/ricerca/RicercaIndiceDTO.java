package it.webred.ct.data.access.basic.indice.ricerca;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class RicercaIndiceDTO extends CeTBaseObject implements Serializable {

	private Object obj;
	private String progressivoEs;
	
	
	private String destFonte;
	private String destProgressivoEs;
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public String getProgressivoEs() {
		return progressivoEs;
	}

	public void setProgressivoEs(String progressivoEs) {
		this.progressivoEs = progressivoEs;
	}

	public String getDestFonte() {
		return destFonte;
	}

	public void setDestFonte(String destFonte) {
		this.destFonte = destFonte;
	}

	public String getDestProgressivoEs() {
		return destProgressivoEs;
	}

	public void setDestProgressivoEs(String destProgressivoEs) {
		this.destProgressivoEs = destProgressivoEs;
	}
	
	
	
	
}
