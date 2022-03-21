package it.webred.ct.data.access.basic.redditi.dto;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;


public class KeySoggDTO extends CeTBaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ideTelematico;
	private String codFis;
	private String anno;
	
	public String getCodFis() {
		return codFis;
	}
	public void setCodFis(String codFis) {
		this.codFis = codFis;
	}
	public String getIdeTelematico() {
		return ideTelematico;
	}
	public void setIdeTelematico(String ideTelematico) {
		this.ideTelematico = ideTelematico;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	
}
