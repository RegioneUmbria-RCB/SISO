package it.webred.ct.data.access.basic.versamenti.iciDM.dto;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class IciDmDataIn extends CeTBaseObject implements Serializable{

	//cod fiscale
	private String cf;
	private String id;
	private String anno;

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

}
