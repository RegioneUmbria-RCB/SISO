package it.webred.ct.data.access.basic.c336.dto;

import it.webred.ct.data.model.c336.C336GridAttribCatA2;
import it.webred.ct.data.model.c336.C336TabValIncrClsA5A6;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;

public class C336GridAttribCatA2DTO extends CeTBaseObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private C336GridAttribCatA2 griglia;

	public void setGriglia(C336GridAttribCatA2 griglia) {
		this.griglia = griglia;
	}

	public C336GridAttribCatA2 getGriglia() {
		return griglia;
	}



}
