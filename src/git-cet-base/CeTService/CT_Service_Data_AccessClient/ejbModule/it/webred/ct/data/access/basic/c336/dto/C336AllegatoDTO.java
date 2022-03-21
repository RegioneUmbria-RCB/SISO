package it.webred.ct.data.access.basic.c336.dto;

import it.webred.ct.data.model.c336.C336Allegato;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class C336AllegatoDTO  extends CeTBaseObject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private C336Allegato allegato;
	
	public C336Allegato getAllegato() {
		return allegato;
	}
	public void setAllegato(C336Allegato allegato) {
		this.allegato = allegato;
	}
	
}
