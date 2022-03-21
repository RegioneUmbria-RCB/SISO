package it.webred.ct.data.access.basic.fonti;

import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FontiDataIn extends CeTBaseObject implements Serializable{
	
	private String IdFonte;

	public String getIdFonte() {
		return IdFonte;
	}

	public void setIdFonte(String idFonte) {
		IdFonte = idFonte;
	}
	
}
